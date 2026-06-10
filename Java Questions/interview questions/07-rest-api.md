# REST API Interview Questions

## 1. What is REST API?

**Answer:**

REST (Representational State Transfer) is an architectural style for building web services. RESTful APIs use HTTP methods to perform CRUD operations.

**Key Principles:**
1. **Stateless** - Each request contains all necessary information
2. **Client-Server** - Separation of concerns
3. **Cacheable** - Responses can be cached
4. **Uniform Interface** - Standardized way to communicate
5. **Layered System** - Client doesn't know if connected to end server

**HTTP Methods:**

| Method | Purpose | Example |
|--------|---------|---------|
| GET | Retrieve data | `GET /users` |
| POST | Create data | `POST /users` |
| PUT | Update (full) | `PUT /users/1` |
| PATCH | Update (partial) | `PATCH /users/1` |
| DELETE | Delete data | `DELETE /users/1` |

---

## 2. What are HTTP Status Codes?

**Answer:**

| Code | Category | Meaning | When to Use |
|------|----------|---------|-------------|
| 200 | Success | OK | Successful GET, PUT, PATCH |
| 201 | Success | Created | Successful POST |
| 204 | Success | No Content | Successful DELETE |
| 400 | Client Error | Bad Request | Invalid input |
| 401 | Client Error | Unauthorized | Not authenticated |
| 403 | Client Error | Forbidden | Authenticated but no permission |
| 404 | Client Error | Not Found | Resource doesn't exist |
| 409 | Client Error | Conflict | Duplicate resource |
| 500 | Server Error | Internal Server Error | Server crashed |
| 503 | Server Error | Service Unavailable | Server down |

**Example:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404
        }
        
        return ResponseEntity.ok(user);  // 200
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);  // 201
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();  // 204
    }
}
```

---

## 3. What is the difference between PUT and PATCH?

**Answer:**

**PUT:**
- Replaces entire resource
- Send all fields (even unchanged)
- Idempotent

```java
@PutMapping("/users/{id}")
public User updateUser(@PathVariable Long id, @RequestBody User user) {
    // Replace entire user
    user.setId(id);
    return userService.save(user);  // Saves all fields
}

// Request:
// PUT /users/1
// {
//   "name": "John Updated",
//   "email": "john@example.com",
//   "age": 30
// }
// All fields must be present!
```

**PATCH:**
- Updates specific fields
- Send only changed fields
- Idempotent

```java
@PatchMapping("/users/{id}")
public User patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
    User user = userService.findById(id);
    
    // Update only provided fields
    if (updates.containsKey("name")) {
        user.setName((String) updates.get("name"));
    }
    if (updates.containsKey("email")) {
        user.setEmail((String) updates.get("email"));
    }
    
    return userService.save(user);
}

// Request:
// PATCH /users/1
// {
//   "name": "John Updated"
// }
// Only name is updated, other fields remain unchanged
```

---

## 4. What is RESTful API Best Practices?

**Answer:**

**1. Use Nouns, Not Verbs:**
```
✅ GET /users
✅ POST /users
✅ GET /users/1

❌ GET /getUsers
❌ POST /createUser
❌ GET /getUserById/1
```

**2. Use Plural Nouns:**
```
✅ GET /users
✅ GET /products

❌ GET /user
❌ GET /product
```

**3. Use HTTP Methods Correctly:**
```
✅ GET /users         (retrieve)
✅ POST /users        (create)
✅ PUT /users/1       (update)
✅ DELETE /users/1    (delete)

❌ POST /deleteUser/1
❌ GET /createUser
```

**4. Nested Resources:**
```
✅ GET /users/1/orders          (get orders for user 1)
✅ POST /users/1/orders         (create order for user 1)
✅ GET /users/1/orders/5        (get order 5 for user 1)
```

**5. Filtering, Sorting, Pagination:**
```
GET /users?age=25&city=NYC          (filtering)
GET /users?sort=name,asc            (sorting)
GET /users?page=2&size=10           (pagination)
```

**6. Versioning:**
```
✅ /api/v1/users
✅ /api/v2/users
```

**7. Error Handling:**
```json
{
  "status": 400,
  "message": "Invalid email format",
  "timestamp": "2024-01-15T10:30:00",
  "path": "/api/users"
}
```

---

## 5. How to implement Pagination in REST API?

**Answer:**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping
    public ResponseEntity<Page<User>> getUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<User> users = userService.findAll(pageable);
        
        return ResponseEntity.ok(users);
    }
}

// Request: GET /api/users?page=0&size=10&sortBy=name
// Response:
// {
//   "content": [...],           // Array of users
//   "totalElements": 100,       // Total users
//   "totalPages": 10,           // Total pages
//   "size": 10,                 // Page size
//   "number": 0,                // Current page
//   "first": true,              // Is first page?
//   "last": false               // Is last page?
// }
```

**Custom Response:**
```java
@GetMapping
public ResponseEntity<Map<String, Object>> getUsers(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
) {
    Page<User> pageResult = userService.findAll(PageRequest.of(page, size));
    
    Map<String, Object> response = new HashMap<>();
    response.put("users", pageResult.getContent());
    response.put("currentPage", pageResult.getNumber());
    response.put("totalItems", pageResult.getTotalElements());
    response.put("totalPages", pageResult.getTotalPages());
    
    return ResponseEntity.ok(response);
}
```

---

## 6. How to implement Filtering and Sorting?

**Answer:**

**Filtering:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping
    public List<User> getUsers(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Integer age,
        @RequestParam(required = false) String city
    ) {
        return userService.findByFilters(name, age, city);
    }
}

// Service
@Service
public class UserService {
    
    public List<User> findByFilters(String name, Integer age, String city) {
        return userRepository.findAll(Specification.where(
            UserSpecification.hasName(name)
                .and(UserSpecification.hasAge(age))
                .and(UserSpecification.hasCity(city))
        ));
    }
}

// Specification
public class UserSpecification {
    
    public static Specification<User> hasName(String name) {
        return (root, query, cb) -> 
            name == null ? null : cb.equal(root.get("name"), name);
    }
    
    public static Specification<User> hasAge(Integer age) {
        return (root, query, cb) -> 
            age == null ? null : cb.equal(root.get("age"), age);
    }
}
```

**Sorting:**
```java
@GetMapping
public List<User> getUsers(
    @RequestParam(defaultValue = "id") String sortBy,
    @RequestParam(defaultValue = "asc") String order
) {
    Sort sort = order.equals("asc") 
        ? Sort.by(sortBy).ascending() 
        : Sort.by(sortBy).descending();
    
    return userService.findAll(sort);
}

// Request: GET /api/users?sortBy=name&order=desc
```

---

## 7. How to validate Request Body?

**Answer:**

```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

**DTO with Validation:**
```java
public class UserDTO {
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    
    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be less than 100")
    private Integer age;
    
    @Pattern(regexp = "^\\d{10}$", message = "Phone must be 10 digits")
    private String phone;
}
```

**Controller:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.create(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
```

**Exception Handler:**
```java
@RestControllerAdvice
public class ValidationExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
        MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        
        return ResponseEntity.badRequest().body(errors);
    }
}

// Response:
// {
//   "name": "Name is required",
//   "email": "Invalid email format",
//   "age": "Age must be at least 18"
// }
```

---

## 8. What is HATEOAS?

**Answer:**

HATEOAS (Hypermedia As The Engine Of Application State) provides links to related resources in API responses.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```

**Without HATEOAS:**
```json
{
  "id": 1,
  "name": "John",
  "email": "john@example.com"
}
```

**With HATEOAS:**
```json
{
  "id": 1,
  "name": "John",
  "email": "john@example.com",
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/users/1"
    },
    "orders": {
      "href": "http://localhost:8080/api/users/1/orders"
    },
    "update": {
      "href": "http://localhost:8080/api/users/1"
    },
    "delete": {
      "href": "http://localhost:8080/api/users/1"
    }
  }
}
```

**Implementation:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/{id}")
    public EntityModel<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        
        // Add links
        EntityModel<User> resource = EntityModel.of(user);
        
        resource.add(linkTo(methodOn(UserController.class).getUser(id)).withSelfRel());
        resource.add(linkTo(methodOn(UserController.class).getOrders(id)).withRel("orders"));
        resource.add(linkTo(methodOn(UserController.class).updateUser(id, null)).withRel("update"));
        resource.add(linkTo(methodOn(UserController.class).deleteUser(id)).withRel("delete"));
        
        return resource;
    }
}
```

---

## 9. How to implement File Upload/Download in REST API?

**Answer:**

**File Upload:**
```java
@RestController
@RequestMapping("/api/files")
public class FileController {
    
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
        @RequestParam("file") MultipartFile file
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        
        try {
            // Save file
            String filename = file.getOriginalFilename();
            Path path = Paths.get("uploads/" + filename);
            Files.write(path, file.getBytes());
            
            return ResponseEntity.ok("File uploaded: " + filename);
            
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Upload failed");
        }
    }
}

// Request (Postman/Form):
// POST /api/files/upload
// Form-data: file = [select file]
```

**File Download:**
```java
@GetMapping("/download/{filename}")
public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
    try {
        Path path = Paths.get("uploads/" + filename);
        Resource resource = new UrlResource(path.toUri());
        
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment; filename=\"" + filename + "\"")
                .body(resource);
                
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
```

**Configuration:**
```properties
# application.properties
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

---

## 10. What is Content Negotiation?

**Answer:**

Content Negotiation allows the same endpoint to return different formats (JSON, XML) based on the client's request.

**Configuration:**
```xml
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```

**Controller:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<User> getUsers() {
        return userService.findAll();
    }
}
```

**Client Requests:**

**Request JSON:**
```
GET /api/users
Accept: application/json

Response:
[
  {"id": 1, "name": "John"},
  {"id": 2, "name": "Jane"}
]
```

**Request XML:**
```
GET /api/users
Accept: application/xml

Response:
<List>
  <User>
    <id>1</id>
    <name>John</name>
  </User>
  <User>
    <id>2</id>
    <name>Jane</name>
  </User>
</List>
```

---

## 11. How to implement API Versioning?

**Answer:**

**Method 1: URI Versioning (Most common):**
```java
@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {
    
    @GetMapping
    public List<UserV1> getUsers() {
        return userService.findAllV1();
    }
}

@RestController
@RequestMapping("/api/v2/users")
public class UserControllerV2 {
    
    @GetMapping
    public List<UserV2> getUsers() {
        return userService.findAllV2();
    }
}
```

**Method 2: Header Versioning:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping(headers = "API-Version=1")
    public List<UserV1> getUsersV1() {
        return userService.findAllV1();
    }
    
    @GetMapping(headers = "API-Version=2")
    public List<UserV2> getUsersV2() {
        return userService.findAllV2();
    }
}

// Request: GET /api/users
// Header: API-Version: 1
```

**Method 3: Request Parameter:**
```java
@GetMapping(params = "version=1")
public List<UserV1> getUsersV1() {
    return userService.findAllV1();
}

@GetMapping(params = "version=2")
public List<UserV2> getUsersV2() {
    return userService.findAllV2();
}

// Request: GET /api/users?version=1
```

---

## 12. How to implement Rate Limiting?

**Answer:**

Rate limiting restricts the number of API requests a client can make.

**Using Bucket4j:**
```xml
<dependency>
    <groupId>com.github.vladimir-bukhtoyarov</groupId>
    <artifactId>bucket4j-core</artifactId>
    <version>7.6.0</version>
</dependency>
```

**Rate Limiter:**
```java
@Component
public class RateLimiter {
    
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();
    
    public Bucket resolveBucket(String apiKey) {
        return cache.computeIfAbsent(apiKey, k -> createNewBucket());
    }
    
    private Bucket createNewBucket() {
        // 100 requests per minute
        Bandwidth limit = Bandwidth.classic(100, Refill.intervally(100, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }
}
```

**Interceptor:**
```java
@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    
    @Autowired
    private RateLimiter rateLimiter;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String apiKey = request.getHeader("X-API-Key");
        Bucket bucket = rateLimiter.resolveBucket(apiKey);
        
        if (bucket.tryConsume(1)) {
            return true;  // Allow request
        }
        
        response.setStatus(429);  // Too Many Requests
        return false;  // Block request
    }
}
```

**Configuration:**
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Autowired
    private RateLimitInterceptor rateLimitInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor);
    }
}
```

---

## 13. What is Idempotency in REST API?

**Answer:**

Idempotent operations produce the same result no matter how many times they are executed.

**Idempotent Methods:**
- **GET** - Reading data multiple times returns same result
- **PUT** - Updating same data multiple times produces same result
- **DELETE** - Deleting same resource multiple times produces same result

**Non-Idempotent:**
- **POST** - Creating resource multiple times creates multiple resources

**Example:**
```java
// Idempotent - PUT
@PutMapping("/users/{id}")
public User updateUser(@PathVariable Long id, @RequestBody User user) {
    user.setId(id);
    return userService.save(user);  // Same result every time
}

// Non-Idempotent - POST
@PostMapping("/users")
public User createUser(@RequestBody User user) {
    return userService.save(user);  // Creates new user each time
}

// Make POST idempotent using idempotency key
@PostMapping("/orders")
public Order createOrder(
    @RequestBody Order order,
    @RequestHeader("Idempotency-Key") String idempotencyKey
) {
    // Check if order with this key already exists
    Order existing = orderService.findByIdempotencyKey(idempotencyKey);
    if (existing != null) {
        return existing;  // Return existing order
    }
    
    order.setIdempotencyKey(idempotencyKey);
    return orderService.save(order);  // Create new order
}
```

---

## 14. What is the difference between @RequestParam and @PathVariable?

**Answer:**

**@PathVariable:**
- Extract value from URL path
- Required by default

```java
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
    return userService.findById(id);
}

// Request: GET /users/123
// id = 123
```

**@RequestParam:**
- Extract value from query string
- Optional (can provide default value)

```java
@GetMapping("/users")
public List<User> getUsers(
    @RequestParam(required = false) String name,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
) {
    return userService.findByName(name, page, size);
}

// Request: GET /users?name=John&page=0&size=10
// name = "John", page = 0, size = 10
```

**Combined:**
```java
@GetMapping("/users/{id}/orders")
public List<Order> getUserOrders(
    @PathVariable Long id,
    @RequestParam(defaultValue = "PENDING") String status
) {
    return orderService.findByUserIdAndStatus(id, status);
}

// Request: GET /users/123/orders?status=COMPLETED
// id = 123, status = "COMPLETED"
```

---

## 15. How to implement Search functionality?

**Answer:**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    // Simple search
    @GetMapping("/search")
    public List<User> search(@RequestParam String keyword) {
        return userService.search(keyword);
    }
    
    // Advanced search with multiple filters
    @GetMapping("/advanced-search")
    public List<User> advancedSearch(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) Integer minAge,
        @RequestParam(required = false) Integer maxAge,
        @RequestParam(required = false) String city
    ) {
        return userService.advancedSearch(name, email, minAge, maxAge, city);
    }
}
```

**Service with JPA Specification:**
```java
@Service
public class UserService {
    
    public List<User> search(String keyword) {
        return userRepository.findByNameContainingOrEmailContaining(keyword, keyword);
    }
    
    public List<User> advancedSearch(String name, String email, Integer minAge, Integer maxAge, String city) {
        return userRepository.findAll(
            Specification.where(
                hasName(name)
                    .and(hasEmail(email))
                    .and(hasMinAge(minAge))
                    .and(hasMaxAge(maxAge))
                    .and(hasCity(city))
            )
        );
    }
}
```

**Repository:**
```java
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    List<User> findByNameContainingOrEmailContaining(String name, String email);
}
```
