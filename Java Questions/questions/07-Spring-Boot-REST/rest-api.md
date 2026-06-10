# Spring Boot - REST API

## REST Endpoints

### 1. How do you create a RESTful API using Spring Boot?

**Answer:**

Creating REST API in Spring Boot is super easy! Here's how:

**Step 1: Add Web Starter**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

**Step 2: Create REST Controller**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // GET - Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    
    // GET - Get user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }
    
    // POST - Create user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }
    
    // PUT - Update user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }
    
    // DELETE - Delete user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
```

**Step 3: Create Entity**

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    
    // Getters and setters
}
```

**Step 4: Create Service**

```java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }
    
    public User save(User user) {
        return userRepository.save(user);
    }
    
    public User update(Long id, User user) {
        User existing = findById(id);
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        return userRepository.save(existing);
    }
    
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
```

**Complete Example:**

```java
@SpringBootApplication
public class RestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }
}

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.update(id, user));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

**Key Points:**
- `@RestController` = `@Controller` + `@ResponseBody`
- HTTP methods map to annotations (`@GetMapping`, `@PostMapping`, etc.)
- `@RequestBody` = Request body (JSON)
- `@PathVariable` = URL path variable
- `ResponseEntity` = Control HTTP response

**Simple rule:** Add `@RestController`, use HTTP method annotations, and Spring Boot handles the rest!

---

### 2. What is the difference between @GetMapping, @PostMapping, @RequestMapping?

**Answer:**

All map HTTP requests, but at different levels:

**@RequestMapping** = Generic mapping (can specify method)
**@GetMapping** = Shortcut for GET requests
**@PostMapping** = Shortcut for POST requests

**@RequestMapping:**

```java
// Can specify method
@RequestMapping(value = "/users", method = RequestMethod.GET)
public List<User> getUsers() {
    // ...
}

@RequestMapping(value = "/users", method = RequestMethod.POST)
public User createUser(@RequestBody User user) {
    // ...
}

// Can map multiple methods
@RequestMapping(value = "/users", method = {RequestMethod.GET, RequestMethod.POST})
public Object handleUsers() {
    // ...
}
```

**@GetMapping (Shortcut):**

```java
// Same as @RequestMapping(method = RequestMethod.GET)
@GetMapping("/users")
public List<User> getUsers() {
    // ...
}
```

**@PostMapping (Shortcut):**

```java
// Same as @RequestMapping(method = RequestMethod.POST)
@PostMapping("/users")
public User createUser(@RequestBody User user) {
    // ...
}
```

**Comparison:**

| Annotation | HTTP Method | Shorthand For |
|------------|-------------|---------------|
| @GetMapping | GET | @RequestMapping(method = GET) |
| @PostMapping | POST | @RequestMapping(method = POST) |
| @PutMapping | PUT | @RequestMapping(method = PUT) |
| @DeleteMapping | DELETE | @RequestMapping(method = DELETE) |
| @RequestMapping | Any (specify) | Base annotation |

**Complete Example:**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    // All equivalent:
    
    // Method 1: @RequestMapping
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser1(@PathVariable Long id) {
        return userService.findById(id);
    }
    
    // Method 2: @GetMapping (preferred - cleaner)
    @GetMapping("/{id}")
    public User getUser2(@PathVariable Long id) {
        return userService.findById(id);
    }
    
    // POST examples
    @RequestMapping(value = "", method = RequestMethod.POST)
    public User createUser1(@RequestBody User user) {
        return userService.save(user);
    }
    
    @PostMapping("")  // Preferred
    public User createUser2(@RequestBody User user) {
        return userService.save(user);
    }
}
```

**When to use:**

**Use @GetMapping/@PostMapping when:**
- ✅ Single HTTP method
- ✅ Cleaner code
- ✅ Most common case

**Use @RequestMapping when:**
- Multiple HTTP methods
- Need more control
- Class-level mapping

```java
// Class-level mapping
@RestController
@RequestMapping("/api/users")  // Applied to all methods
public class UserController {
    
    @GetMapping  // Maps to GET /api/users
    public List<User> getAll() {
        // ...
    }
    
    @GetMapping("/{id}")  // Maps to GET /api/users/{id}
    public User getById(@PathVariable Long id) {
        // ...
    }
}
```

**Simple rule:** Use `@GetMapping`/`@PostMapping` for single methods (cleaner), use `@RequestMapping` for class-level or multiple methods!

---

### 3. Difference between @restcontroller @controller

**Answer:**

**@Controller** = Traditional Spring MVC controller (returns view name)
**@RestController** = REST controller (returns data directly, no view)

**@Controller:**

```java
@Controller
public class UserController {
    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";  // Returns view name (users.jsp, users.html)
    }
}
```

**@RestController:**

```java
@RestController
public class UserController {
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findAll();  // Returns JSON directly
    }
}
```

**Key Differences:**

| Feature | @Controller | @RestController |
|---------|-------------|-----------------|
| Returns | View name | Data (JSON/XML) |
| Use For | Web pages (HTML) | REST API (JSON) |
| @ResponseBody | Need to add | Included automatically |
| Response | HTML page | JSON/XML response |

**@RestController = @Controller + @ResponseBody**

```java
// @RestController is equivalent to:
@Controller
@ResponseBody  // This makes it return data instead of view
public class UserController {
    // ...
}
```

**Example:**

```java
// @Controller - Returns HTML view
@Controller
public class WebController {
    @GetMapping("/home")
    public String home() {
        return "index";  // Returns index.html
    }
}

// @RestController - Returns JSON
@RestController
public class ApiController {
    @GetMapping("/api/users")
    public List<User> getUsers() {
        return Arrays.asList(new User("John"), new User("Jane"));
        // Returns: [{"name":"John"},{"name":"Jane"}]
    }
}

// @Controller with @ResponseBody - Also returns JSON
@Controller
public class ApiController2 {
    @GetMapping("/api/users")
    @ResponseBody  // Needed to return JSON
    public List<User> getUsers() {
        return userService.findAll();
    }
}
```

**When to use:**

**Use @Controller when:**
- Building web pages (HTML)
- Need to return views
- Traditional MVC application

**Use @RestController when:**
- Building REST API
- Returning JSON/XML
- Microservices
- Mobile app backend

**Simple rule:** 
- `@Controller` = Web pages (HTML)
- `@RestController` = REST API (JSON)

---

### 4. Define a get billing details method in the controller with the endpoints

**Answer:**

Here's how to create a GET endpoint for billing details:

```java
@RestController
@RequestMapping("/api/billing")
public class BillingController {
    
    @Autowired
    private BillingService billingService;
    
    // GET /api/billing/details/{id}
    @GetMapping("/details/{id}")
    public ResponseEntity<BillingDetails> getBillingDetails(@PathVariable Long id) {
        BillingDetails details = billingService.getBillingDetails(id);
        return ResponseEntity.ok(details);
    }
    
    // Alternative: GET /api/billing/{id}
    @GetMapping("/{id}")
    public ResponseEntity<BillingDetails> getBillingById(@PathVariable Long id) {
        BillingDetails details = billingService.getBillingDetails(id);
        return ResponseEntity.ok(details);
    }
    
    // With query parameters: GET /api/billing/details?userId=123&month=2024-01
    @GetMapping("/details")
    public ResponseEntity<BillingDetails> getBillingByParams(
            @RequestParam Long userId,
            @RequestParam String month) {
        BillingDetails details = billingService.getBillingDetails(userId, month);
        return ResponseEntity.ok(details);
    }
}
```

**Complete Example:**

```java
@RestController
@RequestMapping("/api/billing")
public class BillingController {
    
    @Autowired
    private BillingService billingService;
    
    // GET /api/billing/details/{billingId}
    @GetMapping("/details/{billingId}")
    public ResponseEntity<BillingDetailsDTO> getBillingDetails(
            @PathVariable("billingId") Long id) {
        
        BillingDetailsDTO details = billingService.getBillingDetails(id);
        
        if(details == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(details);
    }
    
    // GET /api/billing/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BillingDetailsDTO>> getUserBillingDetails(
            @PathVariable Long userId) {
        List<BillingDetailsDTO> details = billingService.getUserBillingDetails(userId);
        return ResponseEntity.ok(details);
    }
}

// DTO
public class BillingDetailsDTO {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private LocalDate billingDate;
    private String status;
    
    // Getters and setters
}

// Service
@Service
public class BillingService {
    @Autowired
    private BillingRepository billingRepository;
    
    public BillingDetailsDTO getBillingDetails(Long id) {
        Billing billing = billingRepository.findById(id)
            .orElseThrow(() -> new BillingNotFoundException(id));
        return convertToDTO(billing);
    }
    
    public List<BillingDetailsDTO> getUserBillingDetails(Long userId) {
        return billingRepository.findByUserId(userId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private BillingDetailsDTO convertToDTO(Billing billing) {
        BillingDetailsDTO dto = new BillingDetailsDTO();
        dto.setId(billing.getId());
        dto.setUserId(billing.getUserId());
        dto.setAmount(billing.getAmount());
        dto.setBillingDate(billing.getBillingDate());
        dto.setStatus(billing.getStatus());
        return dto;
    }
}
```

**Endpoints:**

- `GET /api/billing/details/{id}` - Get billing by ID
- `GET /api/billing/user/{userId}` - Get all billing for user
- `GET /api/billing/{id}` - Alternative endpoint

**Key Points:**
- Use `@GetMapping` for GET requests
- `@PathVariable` for path parameters
- `@RequestParam` for query parameters
- Return `ResponseEntity` for better control

---

## HTTP Status Codes

### 1. What are the different HTTP status codes?

**Answer:**

**HTTP Status Codes** = Numbers that indicate result of HTTP request

**Categories:**

**1xx - Informational** (Rare)
- 100 Continue
- 101 Switching Protocols

**2xx - Success** ✅

**200 OK** - Request successful
```java
@GetMapping("/users/{id}")
public ResponseEntity<User> getUser(@PathVariable Long id) {
    return ResponseEntity.ok(user);  // 200
}
```

**201 Created** - Resource created
```java
@PostMapping("/users")
public ResponseEntity<User> createUser(@RequestBody User user) {
    User created = userService.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);  // 201
}
```

**204 No Content** - Success but no content to return
```java
@DeleteMapping("/users/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();  // 204
}
```

**3xx - Redirection**

**301 Moved Permanently** - Resource moved permanently
**302 Found** - Temporary redirect
**304 Not Modified** - Resource not modified (caching)

**4xx - Client Error** ❌

**400 Bad Request** - Invalid request
```java
@PostMapping("/users")
public ResponseEntity<User> createUser(@RequestBody User user) {
    if(user.getName() == null) {
        return ResponseEntity.badRequest().build();  // 400
    }
    return ResponseEntity.ok(userService.save(user));
}
```

**401 Unauthorized** - Not authenticated
```java
// Spring Security returns 401 automatically
```

**403 Forbidden** - Authenticated but not authorized
```java
// Spring Security returns 403 automatically
```

**404 Not Found** - Resource not found
```java
@GetMapping("/users/{id}")
public ResponseEntity<User> getUser(@PathVariable Long id) {
    User user = userService.findById(id);
    if(user == null) {
        return ResponseEntity.notFound().build();  // 404
    }
    return ResponseEntity.ok(user);
}
```

**409 Conflict** - Resource conflict
```java
@PostMapping("/users")
public ResponseEntity<User> createUser(@RequestBody User user) {
    if(userService.exists(user.getEmail())) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();  // 409
    }
    return ResponseEntity.ok(userService.save(user));
}
```

**5xx - Server Error** ❌

**500 Internal Server Error** - Server error
```java
// Spring returns 500 automatically on exceptions
```

**502 Bad Gateway** - Gateway error
**503 Service Unavailable** - Service temporarily unavailable

**Common Status Codes:**

| Code | Meaning | Use When |
|------|---------|----------|
| 200 | OK | Successful GET/PUT |
| 201 | Created | Successful POST |
| 204 | No Content | Successful DELETE |
| 400 | Bad Request | Invalid input |
| 401 | Unauthorized | Not authenticated |
| 403 | Forbidden | Not authorized |
| 404 | Not Found | Resource doesn't exist |
| 409 | Conflict | Resource conflict |
| 500 | Server Error | Server exception |

**Using in Spring Boot:**

```java
@RestController
public class UserController {
    
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        
        if(user == null) {
            return ResponseEntity.notFound().build();  // 404
        }
        
        return ResponseEntity.ok(user);  // 200
    }
    
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if(user.getName() == null || user.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();  // 400
        }
        
        User created = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);  // 201
    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();  // 204
    }
}
```

**Simple rule:** 
- 2xx = Success
- 4xx = Client mistake
- 5xx = Server mistake

---

## Exception Handling

### 1. How do you handle exceptions in Spring Boot? (Using @ControllerAdvice)

**Answer:**

**@ControllerAdvice** = Global exception handler for all controllers

**Think of it like:** Central error handler that catches all exceptions

**Basic Setup:**

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    // Handle specific exception
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    // Handle generic exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "An error occurred",
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
```

**Complete Example:**

```java
// Custom Exception
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}

// Error Response DTO
public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    
    public ErrorResponse(int status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
    
    // Getters and setters
}

// Global Exception Handler
@ControllerAdvice
public class GlobalExceptionHandler {
    
    // Handle UserNotFoundException - 404
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    // Handle ValidationException - 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining(", "));
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            message,
            LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(error);
    }
    
    // Handle all other exceptions - 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal server error",
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

// Controller
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        // Throws UserNotFoundException if not found
        return userService.findById(id);
    }
}

// Service
@Service
public class UserService {
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }
}
```

**Multiple Exception Handlers:**

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        // Returns 404
    }
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
        // Returns 400
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        // Returns 403
    }
}
```

**Controller-specific Handler:**

```java
// Only handles exceptions from UserController
@ControllerAdvice(assignableTypes = UserController.class)
public class UserExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        // ...
    }
}
```

**Benefits:**
- ✅ Centralized exception handling
- ✅ Consistent error responses
- ✅ Clean controllers (no try-catch needed)
- ✅ Proper HTTP status codes

**Simple rule:** Use `@ControllerAdvice` to handle all exceptions globally. One place to handle all errors!

---

## Response Optimization

### 1. How to Reduce API response

**Answer:**

Ways to reduce API response size and improve performance:

**1. Return Only Required Fields (DTOs)**

```java
// Bad - Returns entire entity
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
    return userService.findById(id);  // Returns all fields
}

// Good - Returns only needed fields
@GetMapping("/users/{id}")
public UserDTO getUser(@PathVariable Long id) {
    User user = userService.findById(id);
    return new UserDTO(user.getId(), user.getName());  // Only id and name
}

// DTO
public class UserDTO {
    private Long id;
    private String name;
    // No password, email, etc.
}
```

**2. Pagination**

```java
@GetMapping("/users")
public ResponseEntity<Page<UserDTO>> getUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
    Page<UserDTO> users = userService.findAll(page, size);
    return ResponseEntity.ok(users);
}
```

**3. Filtering**

```java
@GetMapping("/users")
public List<UserDTO> getUsers(@RequestParam(required = false) String name) {
    if(name != null) {
        return userService.findByName(name);
    }
    return userService.findAll();
}
```

**4. Compression (GZIP)**

```properties
# application.properties
server.compression.enabled=true
server.compression.mime-types=text/html,text/xml,text/plain,application/json
```

**5. Use Projection**

```java
// Only return specific fields
public interface UserSummary {
    Long getId();
    String getName();
}

@GetMapping("/users")
List<UserSummary> getUsers() {
    return userRepository.findAllProjectedBy();
}
```

**6. Lazy Loading**

```java
@Entity
public class User {
    @OneToMany(fetch = FetchType.LAZY)  // Don't load immediately
    private List<Order> orders;
}
```

**7. Exclude Null Fields**

```java
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    // Null fields won't be in JSON
}
```

**8. Cache Responses**

```java
@GetMapping("/users/{id}")
@Cacheable("users")
public UserDTO getUser(@PathVariable Long id) {
    return userService.findById(id);
}
```

**Complete Example:**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // Optimized endpoint
    @GetMapping
    public ResponseEntity<Page<UserSummaryDTO>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name) {
        
        Page<UserSummaryDTO> users = userService.findUsers(page, size, name);
        return ResponseEntity.ok(users);
    }
}

// Lightweight DTO
public class UserSummaryDTO {
    private Long id;
    private String name;
    // Only essential fields
}

// Service with pagination
@Service
public class UserService {
    public Page<UserSummaryDTO> findUsers(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        
        if(name != null) {
            return userRepository.findByNameContaining(name, pageable)
                .map(this::toSummaryDTO);
        }
        
        return userRepository.findAll(pageable)
            .map(this::toSummaryDTO);
    }
    
    private UserSummaryDTO toSummaryDTO(User user) {
        return new UserSummaryDTO(user.getId(), user.getName());
    }
}
```

**Key Strategies:**

| Strategy | Benefit |
|----------|---------|
| DTOs | Smaller response size |
| Pagination | Less data per request |
| Filtering | Only needed data |
| Compression | Smaller network transfer |
| Caching | Faster responses |

**Simple rule:** Return only what's needed, use pagination, and compress responses!
