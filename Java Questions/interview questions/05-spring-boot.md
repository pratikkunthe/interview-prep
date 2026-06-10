# Spring Boot Interview Questions

## 1. What is Spring Boot and why use it?

**Answer:**

Spring Boot simplifies Spring application development by providing auto-configuration, embedded servers, and production-ready features.

**Benefits:**
1. **Auto-configuration** - Automatically configures beans based on classpath
2. **Embedded servers** - No need for external Tomcat/Jetty
3. **Starter dependencies** - Pre-configured dependency sets
4. **Production-ready** - Metrics, health checks, monitoring
5. **No XML configuration** - Convention over configuration

**Traditional Spring:**
```xml
<!-- Lots of XML configuration -->
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://localhost:3306/mydb"/>
</bean>
```

**Spring Boot:**
```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=password
```
Auto-configured!

---

## 2. What is @SpringBootApplication annotation?

**Answer:**

`@SpringBootApplication` is a combination of three annotations:

```java
@SpringBootApplication
// Equivalent to:
// @Configuration + @EnableAutoConfiguration + @ComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

**Breaking it down:**

**1. @Configuration** - Marks class as source of bean definitions
```java
@Configuration
public class AppConfig {
    @Bean
    public MyService myService() {
        return new MyService();
    }
}
```

**2. @EnableAutoConfiguration** - Auto-configures beans based on classpath
```java
// If H2 is in classpath, auto-configures H2 database
// If Tomcat is in classpath, auto-configures embedded Tomcat
```

**3. @ComponentScan** - Scans for components in current package and sub-packages
```java
// Scans com.example and all sub-packages
package com.example;

@SpringBootApplication
public class Application { }
```

---

## 3. What are Spring Boot Starters?

**Answer:**

Starters are pre-configured dependency descriptors that bring in all related dependencies.

**Common Starters:**

| Starter | Purpose | Dependencies Included |
|---------|---------|---------------------|
| spring-boot-starter-web | Web applications | Spring MVC, Tomcat, Jackson |
| spring-boot-starter-data-jpa | JPA/Hibernate | Hibernate, Spring Data JPA |
| spring-boot-starter-security | Security | Spring Security |
| spring-boot-starter-test | Testing | JUnit, Mockito, AssertJ |
| spring-boot-starter-actuator | Monitoring | Health checks, metrics |

**Example - Web Starter:**
```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

This single dependency brings in:
- Spring MVC
- Embedded Tomcat
- Jackson (JSON)
- Validation
- Logging

---

## 4. How does Auto-configuration work in Spring Boot?

**Answer:**

Spring Boot looks at your classpath and automatically configures beans.

**How it works:**
1. `@EnableAutoConfiguration` scans classpath
2. Finds auto-configuration classes in `spring.factories` file
3. Conditionally creates beans based on `@Conditional` annotations

**Example:**
```java
@Configuration
@ConditionalOnClass(DataSource.class)  // Only if DataSource is in classpath
@ConditionalOnMissingBean(DataSource.class)  // Only if no DataSource bean exists
public class DataSourceAutoConfiguration {
    
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource();  // Auto-configured DataSource
    }
}
```

**Common Conditional Annotations:**
- `@ConditionalOnClass` - If class is present
- `@ConditionalOnMissingBean` - If bean is not already defined
- `@ConditionalOnProperty` - If property is set
- `@ConditionalOnWebApplication` - If it's a web application

**Disabling Auto-configuration:**
```java
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application { }
```

---

## 5. What is application.properties vs application.yml?

**Answer:**

Both are configuration files, but different syntax.

**application.properties:**
```properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=secret
spring.jpa.hibernate.ddl-auto=update
logging.level.root=INFO
```

**application.yml (YAML - more readable):**
```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: root
    password: secret
  jpa:
    hibernate:
      ddl-auto: update

logging:
  level:
    root: INFO
```

**Profile-specific configuration:**
- `application-dev.properties` (for development)
- `application-prod.properties` (for production)

```properties
# application-dev.properties
server.port=8080
spring.datasource.url=jdbc:h2:mem:testdb
```

```properties
# application-prod.properties
server.port=80
spring.datasource.url=jdbc:mysql://prod-server:3306/mydb
```

**Activating profile:**
```bash
java -jar app.jar --spring.profiles.active=prod
```

---

## 6. What is @RestController vs @Controller?

**Answer:**

| @Controller | @RestController |
|-------------|----------------|
| Returns view (HTML) | Returns data (JSON/XML) |
| Used in MVC apps | Used in REST APIs |
| Needs @ResponseBody | Has @ResponseBody built-in |

**@Controller (MVC):**
```java
@Controller
public class HomeController {
    
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Hello");
        return "home";  // Returns home.html view
    }
    
    @GetMapping("/api/users")
    @ResponseBody  // Need this to return JSON
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
}
```

**@RestController (REST API):**
```java
@RestController
@RequestMapping("/api")
public class UserController {
    
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();  // Automatically converts to JSON
    }
    
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }
}
```

**@RestController = @Controller + @ResponseBody**

---

## 7. What are the annotations for REST endpoints?

**Answer:**

**HTTP Method Annotations:**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    // GET - Retrieve data
    @GetMapping  // GET /api/users
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    
    @GetMapping("/{id}")  // GET /api/users/123
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }
    
    // POST - Create data
    @PostMapping  // POST /api/users
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }
    
    // PUT - Update data (full)
    @PutMapping("/{id}")  // PUT /api/users/123
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }
    
    // PATCH - Update data (partial)
    @PatchMapping("/{id}")  // PATCH /api/users/123
    public User patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return userService.partialUpdate(id, updates);
    }
    
    // DELETE - Delete data
    @DeleteMapping("/{id}")  // DELETE /api/users/123
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
```

**Parameter Annotations:**

```java
@RestController
public class UserController {
    
    // @PathVariable - Extract from URL path
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }
    
    // @RequestParam - Extract from query string
    @GetMapping("/users")  // GET /users?page=1&size=10
    public List<User> getUsers(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return userService.findAll(page, size);
    }
    
    // @RequestBody - Extract from request body
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }
    
    // @RequestHeader - Extract from headers
    @GetMapping("/users")
    public List<User> getUsers(@RequestHeader("Authorization") String token) {
        // verify token
        return userService.findAll();
    }
}
```

---

## 8. What is Spring Boot Actuator?

**Answer:**

Actuator provides production-ready features for monitoring and managing your application.

**Add Dependency:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

**Built-in Endpoints:**

| Endpoint | Purpose |
|----------|---------|
| /actuator/health | Application health status |
| /actuator/info | Application information |
| /actuator/metrics | Application metrics |
| /actuator/env | Environment properties |
| /actuator/loggers | Logger configuration |
| /actuator/beans | All Spring beans |
| /actuator/mappings | All request mappings |

**Configuration:**
```properties
# Enable all endpoints
management.endpoints.web.exposure.include=*

# Or specific endpoints
management.endpoints.web.exposure.include=health,info,metrics

# Base path
management.endpoints.web.base-path=/actuator
```

**Health Check:**
```bash
GET /actuator/health

Response:
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP"
    },
    "diskSpace": {
      "status": "UP"
    }
  }
}
```

**Custom Health Indicator:**
```java
@Component
public class CustomHealthIndicator implements HealthIndicator {
    
    @Override
    public Health health() {
        boolean healthy = checkExternalService();
        
        if (healthy) {
            return Health.up().withDetail("service", "available").build();
        } else {
            return Health.down().withDetail("service", "unavailable").build();
        }
    }
}
```

---

## 9. What is @ConfigurationProperties?

**Answer:**

`@ConfigurationProperties` binds external configuration (properties file) to a POJO.

**application.yml:**
```yaml
app:
  name: MyApp
  version: 1.0
  database:
    host: localhost
    port: 3306
    name: mydb
```

**Configuration Class:**
```java
@Configuration
@ConfigurationProperties(prefix = "app")
@Data  // Lombok - generates getters/setters
public class AppProperties {
    
    private String name;
    private String version;
    private Database database;
    
    @Data
    public static class Database {
        private String host;
        private int port;
        private String name;
    }
}
```

**Usage:**
```java
@Service
public class AppService {
    
    private final AppProperties appProperties;
    
    @Autowired
    public AppService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }
    
    public void printConfig() {
        System.out.println("App: " + appProperties.getName());
        System.out.println("DB Host: " + appProperties.getDatabase().getHost());
    }
}
```

**Benefits:**
- Type-safe
- Validation support
- Better than multiple @Value annotations

---

## 10. What is Exception Handling in Spring Boot?

**Answer:**

**Method 1: @ExceptionHandler (Controller-level):**
```java
@RestController
public class UserController {
    
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);  // may throw UserNotFoundException
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
```

**Method 2: @ControllerAdvice (Global):**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
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

@Data
@AllArgsConstructor
class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
```

**Custom Exception:**
```java
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
```

---

## 11. What is Spring Boot DevTools?

**Answer:**

DevTools provides features for rapid development and debugging.

**Add Dependency:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

**Features:**

**1. Automatic Restart:**
- Restarts application when files change
- Much faster than manual restart

**2. LiveReload:**
- Auto-refresh browser when resources change
- No need to manually refresh

**3. Property Defaults:**
- Disables caching in development
- Enables debug logging

**4. Remote Debugging:**
- Debug application running on remote server

**Configuration:**
```properties
# Disable restart for specific directories
spring.devtools.restart.exclude=static/**,public/**

# Enable/disable automatic restart
spring.devtools.restart.enabled=true
```

---

## 12. What is @Async in Spring Boot?

**Answer:**

`@Async` executes methods asynchronously in a separate thread.

**Enable Async:**
```java
@SpringBootApplication
@EnableAsync
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

**Async Method:**
```java
@Service
public class EmailService {
    
    @Async
    public void sendEmail(String to, String message) {
        // Send email (takes time)
        System.out.println("Sending email to " + to);
        Thread.sleep(5000);  // Simulate delay
        System.out.println("Email sent");
    }
}
```

**Usage:**
```java
@RestController
public class UserController {
    
    @Autowired
    private EmailService emailService;
    
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        userService.save(user);
        
        emailService.sendEmail(user.getEmail(), "Welcome!");  // Async - doesn't block
        
        return "User registered";  // Returns immediately
    }
}
```

**With Return Value (CompletableFuture):**
```java
@Service
public class DataService {
    
    @Async
    public CompletableFuture<String> fetchData() {
        // Fetch data (takes time)
        Thread.sleep(5000);
        return CompletableFuture.completedFuture("Data fetched");
    }
}

// Usage
CompletableFuture<String> future = dataService.fetchData();
String result = future.get();  // Wait for result
```

---

## 13. What is @Scheduled in Spring Boot?

**Answer:**

`@Scheduled` runs methods at fixed intervals or specific times.

**Enable Scheduling:**
```java
@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

**Fixed Rate (runs every X milliseconds):**
```java
@Component
public class ScheduledTasks {
    
    @Scheduled(fixedRate = 5000)  // Every 5 seconds
    public void reportStatus() {
        System.out.println("Status check at " + LocalDateTime.now());
    }
}
```

**Fixed Delay (X milliseconds after previous execution completes):**
```java
@Scheduled(fixedDelay = 5000)  // 5 seconds after previous execution ends
public void processData() {
    System.out.println("Processing data");
    Thread.sleep(3000);  // Takes 3 seconds
    // Next execution starts 5 seconds after this completes
}
```

**Cron Expression (specific times):**
```java
@Scheduled(cron = "0 0 9 * * MON-FRI")  // Every weekday at 9 AM
public void sendDailyReport() {
    System.out.println("Sending daily report");
}

@Scheduled(cron = "0 */15 * * * *")  // Every 15 minutes
public void cleanupTempFiles() {
    System.out.println("Cleaning temp files");
}
```

**Cron Format:** `second minute hour day month weekday`

---

## 14. What is @Cacheable in Spring Boot?

**Answer:**

`@Cacheable` caches method results to improve performance.

**Add Dependencies:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

**Enable Caching:**
```java
@SpringBootApplication
@EnableCaching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

**Cache Method Result:**
```java
@Service
public class UserService {
    
    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        System.out.println("Fetching from database: " + id);
        // Expensive database call
        return userRepository.findById(id);
    }
    
    // First call: Fetches from DB, stores in cache
    // Subsequent calls: Returns from cache (no DB call)
}
```

**Clear Cache:**
```java
@CacheEvict(value = "users", key = "#id")
public void deleteUser(Long id) {
    userRepository.deleteById(id);
}

@CacheEvict(value = "users", allEntries = true)
public void deleteAllUsers() {
    userRepository.deleteAll();
}
```

**Update Cache:**
```java
@CachePut(value = "users", key = "#user.id")
public User updateUser(User user) {
    return userRepository.save(user);
}
```

---

## 15. What is the difference between @RequestBody and @ResponseBody?

**Answer:**

**@RequestBody:**
- Converts HTTP request body (JSON) to Java object
- Used in POST/PUT/PATCH methods

```java
@PostMapping("/users")
public User createUser(@RequestBody User user) {
    // JSON from request body is converted to User object
    return userService.save(user);
}

// Request:
// POST /users
// Body: {"name": "John", "age": 30}
```

**@ResponseBody:**
- Converts Java object to HTTP response body (JSON)
- Not needed in @RestController (automatically applied)

```java
@Controller
public class UserController {
    
    @GetMapping("/users")
    @ResponseBody  // Converts List<User> to JSON
    public List<User> getUsers() {
        return userService.findAll();
    }
}

// Response:
// [{"id": 1, "name": "John", "age": 30}, ...]
```

**@RestController = @Controller + @ResponseBody (applied to all methods)**
