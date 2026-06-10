# Spring Framework - Core Concepts

## Spring Basics

### 1. Advantages of spring framework

**Answer:**

Spring Framework makes Java development easier and better. Here are the main advantages:

**1. Dependency Injection (DI)**
- Loose coupling between classes
- Easy to test (can inject mock objects)
- Better code organization

```java
// Without Spring - Tight coupling
class OrderService {
    private PaymentService paymentService = new PaymentService();  // Hard to test!
}

// With Spring - Loose coupling
class OrderService {
    @Autowired
    private PaymentService paymentService;  // Spring injects it!
}
```

**2. Inversion of Control (IoC)**
- Spring manages object creation
- You don't create objects, Spring does
- Less boilerplate code

**3. Aspect-Oriented Programming (AOP)**
- Cross-cutting concerns (logging, security) separated
- Cleaner business logic

```java
@Transactional  // Spring handles transaction automatically
public void transferMoney() {
    // Business logic only, no transaction code!
}
```

**4. Lightweight**
- No need for heavy application servers
- Can run in simple servlet containers

**5. Modular**
- Use only what you need
- Many modules (Spring MVC, Spring Data, Spring Security, etc.)

**6. Easy Testing**
- Can inject mock objects easily
- Test in isolation

**7. Transaction Management**
- Declarative transaction management
- No need to write transaction code manually

**8. Exception Handling**
- Unified exception handling
- `@ControllerAdvice` for global exception handling

**9. Integration**
- Easy integration with other frameworks (Hibernate, JPA, etc.)

**10. Active Community**
- Well-documented
- Large community support

**Simple analogy:** Spring = Smart assistant that manages your objects, handles dependencies, and takes care of common tasks so you can focus on business logic!

**Key Benefits Summary:**

| Benefit | Description |
|---------|-------------|
| DI/IoC | Loose coupling, easy testing |
| AOP | Separation of concerns |
| Lightweight | No heavy server needed |
| Modular | Use only what you need |
| Testing | Easy to test |
| Transactions | Declarative management |

---

### 2. What is the difference between Spring and Spring Boot?

**Answer:**

**Spring Framework** = Core framework with many modules
**Spring Boot** = Extension of Spring that makes it easier to use

**Think of it like:**
- **Spring** = Building blocks (you assemble everything yourself)
- **Spring Boot** = Pre-built house (everything ready, just move in!)

**Key Differences:**

| Feature | Spring | Spring Boot |
|---------|--------|-------------|
| Configuration | Manual XML/Java config | Auto-configuration |
| Setup | Complex, many steps | Simple, minimal setup |
| Embedded Server | Need external server | Built-in (Tomcat) |
| Dependencies | Manual management | Starter dependencies |
| Deployment | WAR file to server | JAR file, standalone |
| XML Config | Often needed | Rarely needed |

**Spring Framework example:**

```java
// Need to configure everything manually
@Configuration
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        // Manual configuration
        return new DriverManagerDataSource(...);
    }
    
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        // More manual configuration
        return new LocalContainerEntityManagerFactoryBean(...);
    }
}
```

**Spring Boot example:**

```java
// Just add annotation - Spring Boot configures everything!
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

// That's it! Auto-configuration handles the rest.
```

**When to use what:**

**Use Spring Framework when:**
- Need fine-grained control
- Custom configurations
- Legacy applications
- Specific requirements

**Use Spring Boot when:**
- Quick development
- Microservices
- REST APIs
- Modern applications
- Want convention over configuration

**Simple rule:** Spring Boot = Spring Framework + Auto-configuration + Embedded Server + Starter Dependencies. It's Spring made easy!

---

### 3. Why to use spring boot?

**Answer:**

**Spring Boot** makes Spring development super fast and easy!

**Main Reasons:**

**1. Auto-Configuration**
- Spring Boot configures everything automatically
- No manual XML/Java configuration needed
- Just add dependencies and it works!

```java
// Just this - Spring Boot configures database, JPA, etc. automatically!
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```

**2. Embedded Server**
- No need for external Tomcat/Jetty
- Run as standalone JAR
- `java -jar app.jar` and it runs!

**3. Starter Dependencies**
- Pre-configured dependency groups
- Add one dependency, get everything needed

```xml
<!-- Just add this - gets Spring MVC, Tomcat, Jackson, etc. -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

**4. Production Ready**
- Actuator for monitoring
- Health checks
- Metrics
- Built-in features

**5. Opinionated Defaults**
- Sensible defaults
- Less decisions to make
- Faster development

**6. No XML Configuration**
- Pure Java/annotation based
- Modern approach

**7. Easy Testing**
- `@SpringBootTest` for integration tests
- Test slices (`@WebMvcTest`, `@DataJpaTest`)

**Real-world example:**

**Without Spring Boot (Spring Framework):**
```java
// Need to configure:
// 1. DispatcherServlet
// 2. ViewResolver
// 3. Component scanning
// 4. Database configuration
// 5. Transaction management
// ... many more configurations
```

**With Spring Boot:**
```java
@SpringBootApplication
public class MyApp {
    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }
}
// Everything configured automatically!
```

**Benefits Summary:**

| Benefit | Impact |
|---------|--------|
| Faster Development | Less configuration, more coding |
| Less Boilerplate | Auto-configuration |
| Standalone | No external server needed |
| Easy Deployment | Single JAR file |
| Production Ready | Built-in monitoring |

**Simple analogy:** Spring Boot = Smartphone (everything built-in, easy to use), Spring Framework = Desktop computer (powerful but need to set up everything)

**Key Point:** Use Spring Boot for faster development, less configuration, and modern Java applications!

---

### 4. How spring boot works internally?

**Answer:**

Spring Boot uses **auto-configuration** and **starter dependencies** to work internally.

**How it works:**

**1. @SpringBootApplication**
```java
@SpringBootApplication
public class Application {
    // This annotation is actually 3 annotations combined:
    // @Configuration - Marks as configuration class
    // @EnableAutoConfiguration - Enables auto-configuration
    // @ComponentScan - Scans for components
}
```

**2. Auto-Configuration Process**

```
Application Starts
       ↓
@SpringBootApplication detected
       ↓
@EnableAutoConfiguration triggers
       ↓
Spring Boot scans classpath
       ↓
Finds starter dependencies
       ↓
Auto-configuration classes activated
       ↓
Beans created automatically
       ↓
Application ready!
```

**3. How Auto-Configuration Works:**

Spring Boot uses `spring.factories` file:
```properties
# Auto-configuration classes
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
...
```

**4. Conditional Configuration:**

```java
@Configuration
@ConditionalOnClass(DataSource.class)  // Only if DataSource exists
@ConditionalOnProperty(name = "spring.datasource.url")
public class DataSourceAutoConfiguration {
    @Bean
    public DataSource dataSource() {
        // Auto-configured DataSource
    }
}
```

**5. Starter Dependencies:**

```xml
<!-- spring-boot-starter-web includes: -->
<!-- - spring-webmvc -->
<!-- - spring-web -->
<!-- - tomcat-embed -->
<!-- - jackson -->
<!-- - validation -->
```

**Complete Flow:**

```java
// 1. Application starts
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

// 2. SpringApplication.run() does:
//    - Creates ApplicationContext
//    - Scans for @Component, @Service, @Repository
//    - Loads auto-configuration classes
//    - Creates beans based on conditions
//    - Starts embedded server (if web starter present)

// 3. Auto-configuration checks:
//    - Is DataSource class present? → Configure DataSource
//    - Is JPA present? → Configure JPA
//    - Is Spring MVC present? → Configure DispatcherServlet
//    - etc.

// 4. Application ready to use!
```

**Key Components:**

1. **Auto-Configuration Classes** - Configure beans automatically
2. **Conditional Annotations** - Only configure if conditions met
3. **Starter Dependencies** - Bring in required dependencies
4. **Application Properties** - Override defaults via `application.properties`

**Example:**

```java
// You just write this:
@RestController
public class MyController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }
}

// Spring Boot internally:
// 1. Detects @RestController
// 2. Auto-configures DispatcherServlet
// 3. Auto-configures Jackson for JSON
// 4. Auto-configures error handling
// 5. Maps /hello to hello() method
// All automatically!
```

**Simple explanation:** Spring Boot = Smart assistant that looks at your dependencies, checks what you need, and automatically configures everything. You just write business code!

---

## Dependency Injection

### 1. What are the different types of dependency injections in Spring?

**Answer:**

**Dependency Injection (DI)** = Spring provides dependencies to your classes

Think of it like ordering food:
- **Without DI** = You go to kitchen and cook yourself (create dependencies yourself)
- **With DI** = Waiter brings food to you (Spring brings dependencies)

**Three Types of DI:**

**1. Constructor Injection** ✅ (Recommended)

```java
@Service
public class OrderService {
    private PaymentService paymentService;
    
    // Spring injects via constructor
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

**Benefits:**
- ✅ Immutable (can make fields final)
- ✅ Required dependencies (can't create without them)
- ✅ Easy to test
- ✅ Recommended by Spring

**2. Setter Injection**

```java
@Service
public class OrderService {
    private PaymentService paymentService;
    
    // Spring injects via setter
    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

**Benefits:**
- ✅ Optional dependencies
- ✅ Can change after creation
- ❌ Not immutable

**3. Field Injection** (Not recommended)

```java
@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService;  // Direct field injection
}
```

**Benefits:**
- ✅ Less code
- ❌ Hard to test (can't inject mocks easily)
- ❌ Not recommended

**Comparison:**

| Type | Immutable | Testable | Recommended |
|------|-----------|----------|-------------|
| Constructor | ✅ Yes | ✅ Easy | ✅ Yes |
| Setter | ❌ No | ✅ Easy | ⚠️ Sometimes |
| Field | ❌ No | ❌ Hard | ❌ No |

**Complete Example:**

```java
// Dependency
@Service
public class PaymentService {
    public void processPayment() {
        System.out.println("Processing payment");
    }
}

// Constructor Injection (Best)
@Service
public class OrderService {
    private final PaymentService paymentService;
    
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    
    public void createOrder() {
        paymentService.processPayment();
    }
}

// Setter Injection
@Service
public class OrderService2 {
    private PaymentService paymentService;
    
    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}

// Field Injection (Not recommended)
@Service
public class OrderService3 {
    @Autowired
    private PaymentService paymentService;
}
```

**Simple rule:** Use Constructor Injection - it's the best practice! Spring automatically injects dependencies when creating beans.

---

### 2. Explain IOC Container

**Answer:**

**IoC Container** = Spring container that manages objects (beans) and their dependencies

**IoC = Inversion of Control**
- **Normal way:** You create objects yourself
- **IoC way:** Container creates objects for you (control is inverted!)

**Think of it like:**
- **Without IoC:** You're the chef (create everything yourself)
- **With IoC:** You're the customer (container serves you everything)

**How IoC Container Works:**

```
1. You define beans (@Component, @Service, etc.)
       ↓
2. Spring scans and finds them
       ↓
3. Spring creates instances (beans)
       ↓
4. Spring manages their lifecycle
       ↓
5. Spring injects dependencies
       ↓
6. You use the beans
```

**Two Types of IoC Containers:**

**1. BeanFactory** (Basic)
- Lazy loading (creates beans when requested)
- Lightweight
- Less features

**2. ApplicationContext** (Advanced) ✅ (Most used)
- Eager loading (creates beans at startup)
- More features (AOP, events, i18n)
- Used in Spring Boot

```java
// ApplicationContext (Spring Boot uses this)
@SpringBootApplication
public class App {
    // ApplicationContext is created automatically
}
```

**What IoC Container Does:**

1. **Creates Objects**
```java
@Service
public class UserService {
    // Spring creates this object automatically
}
```

2. **Manages Lifecycle**
```java
@Service
public class UserService {
    @PostConstruct
    public void init() {
        // Called after bean creation
    }
    
    @PreDestroy
    public void cleanup() {
        // Called before bean destruction
    }
}
```

3. **Injects Dependencies**
```java
@Service
public class OrderService {
    private UserService userService;  // Spring injects this
    
    public OrderService(UserService userService) {
        this.userService = userService;
    }
}
```

4. **Manages Scope**
```java
@Service
@Scope("prototype")  // New instance each time
public class PrototypeService {
}

@Service  // Singleton (default) - one instance
public class SingletonService {
}
```

**Benefits:**
- ✅ Loose coupling
- ✅ Easy testing
- ✅ Centralized configuration
- ✅ Lifecycle management

**Simple analogy:** IoC Container = Smart factory manager who creates, manages, and connects all the parts (beans) for you. You just tell it what you need, and it delivers!

---

### 3. @Bean @Autowired in spring boot?

**Answer:**

**@Bean** = Method-level annotation to create beans
**@Autowired** = Injects dependencies automatically

**@Bean:**

Used in `@Configuration` classes to create beans:

```java
@Configuration
public class AppConfig {
    
    // Creates a bean of type DataSource
    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource("jdbc:h2:mem:testdb");
    }
    
    // Creates a bean of type RestTemplate
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

**When to use @Bean:**
- Third-party classes (you can't add @Component)
- Need custom configuration
- Want to control bean creation

**@Autowired:**

Injects dependencies automatically:

```java
@Service
public class OrderService {
    // Spring automatically finds PaymentService and injects it
    @Autowired
    private PaymentService paymentService;
    
    // Or via constructor (better)
    private PaymentService paymentService;
    
    @Autowired
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

**Complete Example:**

```java
// Configuration class
@Configuration
public class Config {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService();
    }
}

// Service using @Autowired
@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService;  // Injected automatically
    
    public void processOrder() {
        paymentService.processPayment();
    }
}
```

**Key Points:**
- `@Bean` = "Create this object as a bean"
- `@Autowired` = "Inject this dependency here"
- Spring automatically wires them together

**Simple rule:** `@Bean` creates beans, `@Autowired` uses them!

---

### 4. What is the difference between @Bean and @Component?

**Answer:**

Both create beans, but used in different scenarios:

**@Component** = Class-level annotation (and its specializations)
**@Bean** = Method-level annotation in @Configuration class

**@Component:**

```java
// Mark class as component - Spring creates bean automatically
@Component
public class UserService {
    // Spring creates instance of this class
}

// Specializations of @Component:
@Service  // Same as @Component (semantic meaning)
@Repository  // Same as @Component (semantic meaning)
@Controller  // Same as @Component (semantic meaning)
```

**@Bean:**

```java
@Configuration
public class AppConfig {
    // Method creates bean - you control how it's created
    @Bean
    public DataSource dataSource() {
        // Custom creation logic
        return new DriverManagerDataSource("...");
    }
}
```

**Key Differences:**

| Feature | @Component | @Bean |
|---------|------------|-------|
| Level | Class | Method |
| Control | Spring creates | You create |
| Use for | Your own classes | Third-party classes |
| Customization | Limited | Full control |

**When to use what:**

**Use @Component when:**
- Your own classes
- Simple bean creation
- Spring can create it automatically

```java
@Service  // Your class
public class UserService {
    // Spring creates this automatically
}
```

**Use @Bean when:**
- Third-party classes (can't modify)
- Need custom configuration
- Complex creation logic

```java
@Configuration
public class Config {
    @Bean
    public RestTemplate restTemplate() {
        // Third-party class, need custom setup
        RestTemplate rt = new RestTemplate();
        rt.setConnectTimeout(5000);
        return rt;
    }
}
```

**Example:**

```java
// @Component - Your class
@Service
public class UserService {
    public void saveUser() {
        // Your code
    }
}

// @Bean - Third-party or custom configuration
@Configuration
public class AppConfig {
    @Bean
    public ObjectMapper objectMapper() {
        // Third-party class, custom configuration
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
```

**Simple rule:** 
- `@Component` = "This is my class, make it a bean"
- `@Bean` = "Create this object as a bean with my custom logic"

---

### 5. @Autowired vs @Qualifier

**Answer:**

**@Autowired** = Injects dependency automatically
**@Qualifier** = Specifies WHICH bean to inject when multiple beans of same type exist

**Problem @Qualifier solves:**

```java
// Two implementations of PaymentService
@Service("creditCardPayment")
public class CreditCardPaymentService implements PaymentService {
    // ...
}

@Service("paypalPayment")
public class PayPalPaymentService implements PaymentService {
    // ...
}

// Which one to inject?
@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService;  // ERROR! Which one?
}
```

**Solution with @Qualifier:**

```java
@Service
public class OrderService {
    // Specify which bean to inject
    @Autowired
    @Qualifier("creditCardPayment")
    private PaymentService paymentService;  // Injects CreditCardPaymentService
}
```

**Complete Example:**

```java
// Interface
public interface PaymentService {
    void processPayment();
}

// Implementation 1
@Service("creditCard")
public class CreditCardService implements PaymentService {
    @Override
    public void processPayment() {
        System.out.println("Credit card payment");
    }
}

// Implementation 2
@Service("paypal")
public class PayPalService implements PaymentService {
    @Override
    public void processPayment() {
        System.out.println("PayPal payment");
    }
}

// Usage with @Qualifier
@Service
public class OrderService {
    @Autowired
    @Qualifier("creditCard")  // Specify which one
    private PaymentService paymentService;
    
    public void processOrder() {
        paymentService.processPayment();  // Uses CreditCardService
    }
}
```

**Comparison:**

| Annotation | Purpose | Use When |
|------------|---------|----------|
| @Autowired | Inject dependency | Single bean of type |
| @Qualifier | Specify which bean | Multiple beans of same type |

**Simple rule:** Use `@Qualifier` when you have multiple beans of the same type and need to specify which one!

---

### 6. @Primary vs @Qualifier

**Answer:**

Both solve the same problem (multiple beans), but differently:

**@Primary** = Mark one bean as default (preferred)
**@Qualifier** = Specify exact bean by name

**@Primary Example:**

```java
// Mark as primary (default choice)
@Service
@Primary
public class CreditCardPaymentService implements PaymentService {
    // ...
}

@Service
public class PayPalPaymentService implements PaymentService {
    // ...
}

// No need to specify - uses @Primary one
@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService;  // Uses CreditCardPaymentService (primary)
}
```

**@Qualifier Example:**

```java
@Service("creditCard")
public class CreditCardPaymentService implements PaymentService {
    // ...
}

@Service("paypal")
public class PayPalPaymentService implements PaymentService {
    // ...
}

// Specify exact bean
@Service
public class OrderService {
    @Autowired
    @Qualifier("paypal")  // Explicitly choose PayPal
    private PaymentService paymentService;
}
```

**Comparison:**

| Feature | @Primary | @Qualifier |
|---------|----------|------------|
| Usage | One default bean | Specify exact bean |
| Flexibility | Less (one default) | More (choose any) |
| When | Most common case | Specific needs |

**When to use what:**

**Use @Primary when:**
- One implementation is most common
- Want default behavior
- Don't want to specify everywhere

**Use @Qualifier when:**
- Need specific bean
- Different classes need different beans
- More explicit control

**Example:**

```java
// Primary for default
@Service
@Primary
public class MySQLDataSource implements DataSource {
    // Default database
}

@Service
public class PostgreSQLDataSource implements DataSource {
    // Alternative database
}

// Most services use primary
@Service
public class UserService {
    @Autowired
    private DataSource dataSource;  // Uses MySQL (primary)
}

// Some services need specific one
@Service
public class ReportService {
    @Autowired
    @Qualifier("postgreSQLDataSource")
    private DataSource dataSource;  // Uses PostgreSQL
}
```

**Simple rule:** 
- `@Primary` = "This is the default, use it unless specified otherwise"
- `@Qualifier` = "Use this specific one"

---

### 7. Autowired - Explain

**Answer:**

**@Autowired** = Spring automatically finds and injects dependencies

**Think of it like:**
- **Without @Autowired:** You go shopping yourself (create dependencies)
- **With @Autowired:** Delivery service brings items to you (Spring injects dependencies)

**How it works:**

```java
// 1. Spring finds all beans
@Service
public class PaymentService {
    // Spring creates this bean
}

// 2. Spring sees @Autowired
@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService;  // Spring finds PaymentService and injects it
}
```

**Ways to use @Autowired:**

**1. Field Injection** (Not recommended)
```java
@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService;
}
```

**2. Constructor Injection** ✅ (Recommended)
```java
@Service
public class OrderService {
    private PaymentService paymentService;
    
    @Autowired  // Optional in Spring 4.3+
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

**3. Setter Injection**
```java
@Service
public class OrderService {
    private PaymentService paymentService;
    
    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

**What Spring does:**

1. Scans for beans (`@Component`, `@Service`, etc.)
2. Creates bean instances
3. Sees `@Autowired`
4. Finds matching bean by type
5. Injects it

**Example:**

```java
// Dependency
@Service
public class EmailService {
    public void sendEmail() {
        System.out.println("Sending email");
    }
}

// Using @Autowired
@Service
public class UserService {
    @Autowired
    private EmailService emailService;  // Spring injects EmailService
    
    public void registerUser() {
        emailService.sendEmail();
    }
}
```

**Benefits:**
- ✅ No manual object creation
- ✅ Loose coupling
- ✅ Easy to test (can inject mocks)
- ✅ Less boilerplate code

**Simple explanation:** `@Autowired` = "Hey Spring, I need this dependency. Find it and give it to me!"

---

## Annotations

### 1. Annotations of spring and spring boot

**Answer:**

Here are the most important Spring and Spring Boot annotations:

**Core Spring Annotations:**

**1. @Component** - Generic component
```java
@Component
public class MyComponent {
}
```

**2. @Service** - Service layer
```java
@Service
public class UserService {
}
```

**3. @Repository** - Data access layer
```java
@Repository
public class UserRepository {
}
```

**4. @Controller** - Web controller
```java
@Controller
public class UserController {
}
```

**5. @RestController** - REST controller (Controller + ResponseBody)
```java
@RestController
public class UserController {
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findAll();
    }
}
```

**6. @Autowired** - Dependency injection
```java
@Autowired
private UserService userService;
```

**7. @Qualifier** - Specify bean name
```java
@Autowired
@Qualifier("userService")
private Service service;
```

**8. @Primary** - Default bean
```java
@Service
@Primary
public class DefaultService {
}
```

**9. @Configuration** - Configuration class
```java
@Configuration
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        return new DataSource();
    }
}
```

**10. @Bean** - Create bean
```java
@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}
```

**11. @Value** - Inject property value
```java
@Value("${app.name}")
private String appName;
```

**12. @Scope** - Bean scope
```java
@Service
@Scope("prototype")
public class PrototypeService {
}
```

**Spring Boot Annotations:**

**1. @SpringBootApplication** - Main annotation
```java
@SpringBootApplication
public class Application {
}
```

**2. @EnableAutoConfiguration** - Enable auto-config
```java
@EnableAutoConfiguration
public class App {
}
```

**3. @ComponentScan** - Scan for components
```java
@ComponentScan(basePackages = "com.example")
public class App {
}
```

**Web Annotations:**

**1. @RequestMapping** - Map request
```java
@RequestMapping("/api/users")
public class UserController {
}
```

**2. @GetMapping** - GET request
```java
@GetMapping("/users")
public List<User> getUsers() {
}
```

**3. @PostMapping** - POST request
```java
@PostMapping("/users")
public User createUser(@RequestBody User user) {
}
```

**4. @PutMapping** - PUT request
```java
@PutMapping("/users/{id}")
public User updateUser(@PathVariable Long id, @RequestBody User user) {
}
```

**5. @DeleteMapping** - DELETE request
```java
@DeleteMapping("/users/{id}")
public void deleteUser(@PathVariable Long id) {
}
```

**6. @PathVariable** - Path variable
```java
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
}
```

**7. @RequestParam** - Query parameter
```java
@GetMapping("/users")
public List<User> getUsers(@RequestParam String name) {
}
```

**8. @RequestBody** - Request body
```java
@PostMapping("/users")
public User createUser(@RequestBody User user) {
}
```

**9. @ResponseBody** - Response body
```java
@ResponseBody
public String hello() {
    return "Hello";
}
```

**Transaction Annotations:**

**1. @Transactional** - Transactional method
```java
@Transactional
public void transferMoney() {
    // Transactional code
}
```

**Testing Annotations:**

**1. @SpringBootTest** - Integration test
```java
@SpringBootTest
class UserServiceTest {
}
```

**2. @WebMvcTest** - Web layer test
```java
@WebMvcTest(UserController.class)
class UserControllerTest {
}
```

**Summary Table:**

| Category | Annotations |
|----------|-------------|
| Component | @Component, @Service, @Repository, @Controller |
| Dependency | @Autowired, @Qualifier, @Primary |
| Configuration | @Configuration, @Bean, @Value |
| Web | @RestController, @GetMapping, @PostMapping |
| Boot | @SpringBootApplication |

**Key Point:** These annotations tell Spring what to do - create beans, inject dependencies, handle web requests, etc.!

---

### 2. Springboot Annotation

**Answer:**

**@SpringBootApplication** is the main Spring Boot annotation!

**What it does:**

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

**@SpringBootApplication is actually 3 annotations combined:**

```java
@Configuration          // Marks as configuration class
@EnableAutoConfiguration // Enables auto-configuration
@ComponentScan          // Scans for components
public class Application {
}
```

**What each does:**

**1. @Configuration**
- Marks class as configuration
- Can define `@Bean` methods

**2. @EnableAutoConfiguration**
- Enables Spring Boot auto-configuration
- Automatically configures based on classpath

**3. @ComponentScan**
- Scans current package and sub-packages
- Finds `@Component`, `@Service`, `@Repository`, etc.

**Complete Example:**

```java
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        // This starts Spring Boot application
        SpringApplication.run(MyApplication.class, args);
    }
}

// Equivalent to:
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.example")
public class MyApplication {
    // ...
}
```

**Key Points:**
- One annotation does everything
- Enables auto-configuration
- Scans for components
- Starts embedded server (if web starter present)

**Simple rule:** `@SpringBootApplication` = "This is a Spring Boot app, configure everything automatically!"

---

### 3. Difference between @component @repository @service

**Answer:**

All three are **specializations of @Component**, but have semantic meaning:

**@Component** = Generic component (base annotation)
**@Service** = Business logic layer
**@Repository** = Data access layer

**They all do the same thing** (create beans), but indicate different layers:

```java
// All create beans - functionally same
@Component
public class MyComponent {
}

@Service
public class MyService {
}

@Repository
public class MyRepository {
}
```

**Semantic Differences:**

| Annotation | Layer | Purpose | Special Behavior |
|------------|-------|---------|-----------------|
| @Component | Any | Generic component | None |
| @Service | Business | Business logic | None |
| @Repository | Data | Data access | Exception translation |

**@Repository Special Behavior:**

```java
@Repository
public class UserRepository {
    // Spring automatically translates exceptions:
    // - SQLException → DataAccessException
    // - Makes exception handling consistent
}
```

**When to use what:**

**Use @Component:**
- Generic utility classes
- Not clearly service or repository

**Use @Service:**
- Business logic
- Service layer
- Orchestrates multiple repositories

**Use @Repository:**
- Data access
- Database operations
- JPA repositories

**Example:**

```java
// Repository - Data access
@Repository
public class UserRepository {
    public User findById(Long id) {
        // Database access
    }
}

// Service - Business logic
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public User getUser(Long id) {
        // Business logic
        return userRepository.findById(id);
    }
}

// Component - Utility
@Component
public class EmailUtil {
    public void sendEmail() {
        // Utility method
    }
}
```

**Key Point:** They're functionally the same, but use them to indicate the layer/purpose of your class. It's like labeling boxes - same box, different labels for organization!

---

### 4. What is the difference between @Component, @Service, and @Repository?

**Answer:**

All three create beans, but indicate different architectural layers:

**@Component** = Generic component (base)
**@Service** = Business/service layer
**@Repository** = Data access layer

**Functionally:** They're the same - all create Spring beans
**Semantically:** They indicate different layers

**Comparison:**

| Annotation | Layer | Use For | Special Feature |
|------------|-------|---------|----------------|
| @Component | Any | Generic classes | None |
| @Service | Business | Business logic | None |
| @Repository | Data | Database access | Exception translation |

**@Repository Exception Translation:**

```java
@Repository
public class UserRepository {
    public User findById(Long id) {
        // If SQLException occurs, Spring translates it to
        // DataAccessException automatically
    }
}
```

**Architecture:**

```
┌─────────────────┐
│   @Controller   │ ← Presentation layer
└────────┬────────┘
         │
┌────────▼────────┐
│    @Service     │ ← Business layer
└────────┬────────┘
         │
┌────────▼────────┐
│  @Repository    │ ← Data layer
└─────────────────┘
```

**Example:**

```java
// Repository - Data layer
@Repository
public class UserRepository {
    public User findById(Long id) {
        // Database query
    }
}

// Service - Business layer
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id);
        // Business logic here
        return convertToDTO(user);
    }
}

// Component - Utility
@Component
public class EmailSender {
    public void send(String email) {
        // Utility method
    }
}
```

**Simple rule:** Use them to organize code by layers - @Repository for data, @Service for business logic, @Component for utilities!

---

### 5. Difference between component and configuration annotation

**Answer:**

**@Component** = Marks class as Spring component (creates bean)
**@Configuration** = Marks class as configuration class (can define beans)

**@Component:**

```java
@Component
public class UserService {
    // Spring creates instance of this class as bean
    public void doSomething() {
        // ...
    }
}
```

**@Configuration:**

```java
@Configuration
public class AppConfig {
    // This class configures beans
    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource();
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

**Key Differences:**

| Feature | @Component | @Configuration |
|---------|------------|----------------|
| Purpose | Mark as component | Mark as configuration |
| Bean Creation | Class itself becomes bean | Methods create beans |
| Use For | Business classes | Configuration classes |
| Can have @Bean | ❌ No | ✅ Yes |

**When to use:**

**Use @Component:**
- Business logic classes
- Service classes
- Utility classes
- Classes that ARE the bean

**Use @Configuration:**
- Configuration classes
- Classes that CREATE beans
- Setup/initialization classes

**Example:**

```java
// @Component - This class IS the bean
@Component
public class PaymentService {
    public void processPayment() {
        // Business logic
    }
}

// @Configuration - This class CREATES beans
@Configuration
public class DatabaseConfig {
    @Bean
    public DataSource dataSource() {
        // Creates and returns DataSource bean
        return new DriverManagerDataSource("...");
    }
    
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        // Creates another bean
        return new LocalContainerEntityManagerFactoryBean();
    }
}
```

**Note:** `@Configuration` is also a `@Component`, so configuration classes are also beans!

**Simple rule:** 
- `@Component` = "I am a bean"
- `@Configuration` = "I create beans"

---

### 6. @SpringBootApplication vs @Configuration

**Answer:**

**@SpringBootApplication** = Main Spring Boot annotation (includes @Configuration)
**@Configuration** = Marks class as configuration class

**@SpringBootApplication:**

```java
@SpringBootApplication  // Actually includes @Configuration!
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

**@SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan**

**@Configuration:**

```java
@Configuration
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        return new DataSource();
    }
}
```

**Key Differences:**

| Feature | @SpringBootApplication | @Configuration |
|---------|------------------------|----------------|
| Includes | @Configuration + more | Just @Configuration |
| Auto-config | ✅ Yes | ❌ No |
| Component Scan | ✅ Yes | ❌ No |
| Use For | Main application class | Configuration classes |
| Can start app | ✅ Yes | ❌ No |

**When to use:**

**Use @SpringBootApplication:**
- Main application class
- Entry point of Spring Boot app
- Want auto-configuration

**Use @Configuration:**
- Additional configuration classes
- Custom bean definitions
- Not the main class

**Example:**

```java
// Main class - uses @SpringBootApplication
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

// Additional configuration - uses @Configuration
@Configuration
public class DatabaseConfig {
    @Bean
    public DataSource customDataSource() {
        // Custom configuration
        return new DataSource();
    }
}
```

**Simple rule:** 
- `@SpringBootApplication` = Main class (does everything)
- `@Configuration` = Additional config classes

---

### 7. What is Stereotype annotation

**Answer:**

**Stereotype Annotations** = Special annotations that mark classes as Spring components

They're called "stereotypes" because they indicate the "type" or "role" of the class.

**Stereotype Annotations:**

1. **@Component** - Generic component
2. **@Service** - Service layer component
3. **@Repository** - Data access component
4. **@Controller** - Web controller component
5. **@RestController** - REST controller (meta-annotation)

**All are specializations of @Component:**

```
@Component (base)
    ├── @Service
    ├── @Repository
    ├── @Controller
    └── @RestController (@Controller + @ResponseBody)
```

**Example:**

```java
// All are stereotype annotations
@Component
public class MyComponent {
}

@Service
public class UserService {
}

@Repository
public class UserRepository {
}

@Controller
public class UserController {
}

@RestController
public class ApiController {
}
```

**What they do:**

1. Mark class as Spring-managed component
2. Enable component scanning
3. Create beans automatically
4. Indicate architectural layer (semantic meaning)

**How Spring uses them:**

```java
@ComponentScan(basePackages = "com.example")
public class App {
    // Spring scans and finds:
    // - @Component classes
    // - @Service classes
    // - @Repository classes
    // - @Controller classes
    // Creates beans for all of them
}
```

**Custom Stereotype:**

You can create your own:

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface MyCustomStereotype {
    // Now @MyCustomStereotype works like @Component
}

@MyCustomStereotype
public class MyClass {
    // Treated as @Component
}
```

**Key Points:**
- Stereotype = Type/role indicator
- All create Spring beans
- Help organize code by layers
- Enable component scanning

**Simple explanation:** Stereotype annotations = Labels that tell Spring "This class is a component, and here's what type it is (service, repository, etc.)"

---

## Bean Lifecycle

### 1. Bean lifecycle

**Answer:**

**Bean Lifecycle** = Stages a Spring bean goes through from creation to destruction

**Lifecycle Stages:**

```
1. Instantiation (Create object)
       ↓
2. Populate properties (@Autowired, @Value)
       ↓
3. BeanNameAware.setBeanName()
       ↓
4. BeanFactoryAware.setBeanFactory()
       ↓
5. ApplicationContextAware.setApplicationContext()
       ↓
6. @PostConstruct method
       ↓
7. InitializingBean.afterPropertiesSet()
       ↓
8. Custom init method (@Bean(initMethod = "..."))
       ↓
9. Bean Ready to Use ✅
       ↓
10. @PreDestroy method
       ↓
11. DisposableBean.destroy()
       ↓
12. Custom destroy method (@Bean(destroyMethod = "..."))
       ↓
13. Bean Destroyed
```

**Example:**

```java
@Service
public class UserService implements BeanNameAware, InitializingBean, DisposableBean {
    
    @Autowired
    private UserRepository userRepository;
    
    // 1. Constructor called (instantiation)
    public UserService() {
        System.out.println("1. Constructor called");
    }
    
    // 3. BeanNameAware
    @Override
    public void setBeanName(String name) {
        System.out.println("3. Bean name: " + name);
    }
    
    // 6. @PostConstruct
    @PostConstruct
    public void init() {
        System.out.println("6. @PostConstruct called");
    }
    
    // 7. InitializingBean
    @Override
    public void afterPropertiesSet() {
        System.out.println("7. afterPropertiesSet called");
    }
    
    // 10. @PreDestroy
    @PreDestroy
    public void cleanup() {
        System.out.println("10. @PreDestroy called");
    }
    
    // 11. DisposableBean
    @Override
    public void destroy() {
        System.out.println("11. destroy called");
    }
}
```

**Common Lifecycle Methods:**

**1. @PostConstruct** - After bean creation and dependency injection
```java
@PostConstruct
public void init() {
    // Initialize resources
}
```

**2. @PreDestroy** - Before bean destruction
```java
@PreDestroy
public void cleanup() {
    // Cleanup resources
}
```

**3. InitializingBean** - Interface for initialization
```java
public class MyService implements InitializingBean {
    @Override
    public void afterPropertiesSet() {
        // Initialize
    }
}
```

**4. DisposableBean** - Interface for cleanup
```java
public class MyService implements DisposableBean {
    @Override
    public void destroy() {
        // Cleanup
    }
}
```

**Simple Example:**

```java
@Service
public class DatabaseService {
    private Connection connection;
    
    @PostConstruct
    public void connect() {
        System.out.println("Connecting to database...");
        // connection = DriverManager.getConnection(...);
    }
    
    @PreDestroy
    public void disconnect() {
        System.out.println("Disconnecting from database...");
        // connection.close();
    }
}
```

**Key Points:**
- Beans go through creation → initialization → use → destruction
- Use `@PostConstruct` for initialization
- Use `@PreDestroy` for cleanup
- Spring manages the lifecycle automatically

**Simple analogy:** Bean lifecycle = Human lifecycle: Born (created) → Grow up (initialized) → Live (used) → Die (destroyed)

---

### 2. What is Spring Bean Lifecycle?

**Answer:**

**Spring Bean Lifecycle** = Complete journey of a bean from creation to destruction

**Main Phases:**

**1. Creation Phase**
- Spring creates bean instance
- Calls constructor

**2. Dependency Injection Phase**
- Spring injects dependencies (@Autowired)
- Sets properties (@Value)

**3. Initialization Phase**
- `@PostConstruct` methods called
- Custom initialization

**4. Usage Phase**
- Bean is ready to use
- Methods can be called

**5. Destruction Phase**
- `@PreDestroy` methods called
- Cleanup resources

**Visual Flow:**

```
┌──────────────┐
│  Creation    │ ← Constructor called
└──────┬───────┘
       ↓
┌──────────────┐
│  Dependency  │ ← @Autowired, @Value injected
│  Injection   │
└──────┬───────┘
       ↓
┌──────────────┐
│Initialization│ ← @PostConstruct called
└──────┬───────┘
       ↓
┌──────────────┐
│    Usage     │ ← Bean ready!
└──────┬───────┘
       ↓
┌──────────────┐
│  Destruction │ ← @PreDestroy called
└──────────────┘
```

**Example:**

```java
@Service
public class MyService {
    @Autowired
    private Dependency dependency;
    
    // 1. Creation
    public MyService() {
        System.out.println("Bean created");
    }
    
    // 2. Dependency injection happens automatically
    // dependency is injected here
    
    // 3. Initialization
    @PostConstruct
    public void init() {
        System.out.println("Bean initialized");
        // Setup work
    }
    
    // 4. Usage
    public void doWork() {
        System.out.println("Doing work");
    }
    
    // 5. Destruction
    @PreDestroy
    public void cleanup() {
        System.out.println("Bean destroyed");
        // Cleanup work
    }
}
```

**Key Points:**
- Spring manages entire lifecycle
- You can hook into lifecycle with `@PostConstruct` and `@PreDestroy`
- Use lifecycle methods for resource management

**Simple explanation:** Bean lifecycle = Birth → Setup → Use → Cleanup → Death. Spring handles it all automatically!

---

## Spring Context

### 1. Springboot Context

**Answer:**

**Spring Context (ApplicationContext)** = Container that holds and manages all Spring beans

**Think of it like:**
- **Context** = Warehouse that stores all your beans
- You ask context for beans, it gives them to you

**In Spring Boot:**

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // This creates ApplicationContext
        ApplicationContext context = SpringApplication.run(Application.class, args);
        
        // Get bean from context
        UserService userService = context.getBean(UserService.class);
        userService.doSomething();
    }
}
```

**What ApplicationContext does:**

1. **Creates Beans**
```java
@Service
public class UserService {
    // Context creates this bean
}
```

2. **Manages Beans**
- Stores them
- Provides them when requested
- Manages lifecycle

3. **Injects Dependencies**
```java
@Service
public class OrderService {
    @Autowired
    private UserService userService;  // Context injects this
}
```

**Types of Context:**

**1. AnnotationConfigApplicationContext** - Annotation-based
```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
```

**2. ClassPathXmlApplicationContext** - XML-based
```java
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
```

**3. Spring Boot uses AnnotationConfigApplicationContext**

**Accessing Context:**

```java
// Method 1: Inject ApplicationContext
@Service
public class MyService {
    @Autowired
    private ApplicationContext context;
    
    public void doSomething() {
        UserService service = context.getBean(UserService.class);
    }
}

// Method 2: Implement ApplicationContextAware
@Service
public class MyService implements ApplicationContextAware {
    private ApplicationContext context;
    
    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }
}
```

**Key Points:**
- Context = Container for all beans
- Spring Boot creates it automatically
- You can access it to get beans programmatically
- Usually you don't need to access it directly (use @Autowired)

**Simple explanation:** Spring Context = Smart warehouse manager who creates, stores, and provides all your beans when you need them!

---

## Configuration

### 1. What is spring profile?

**Answer:**

**Spring Profile** = Way to have different configurations for different environments

**Think of it like:**
- **Profile** = Different settings for different situations
- Dev profile = Development settings
- Prod profile = Production settings

**Why use profiles:**

```java
// Development - use H2 database
@Profile("dev")
@Configuration
public class DevConfig {
    @Bean
    public DataSource dataSource() {
        return new H2DataSource();
    }
}

// Production - use MySQL database
@Profile("prod")
@Configuration
public class ProdConfig {
    @Bean
    public DataSource dataSource() {
        return new MySQLDataSource();
    }
}
```

**Activating Profiles:**

**1. application.properties:**
```properties
spring.profiles.active=dev
```

**2. application.yml:**
```yaml
spring:
  profiles:
    active: dev
```

**3. Command line:**
```bash
java -jar app.jar --spring.profiles.active=prod
```

**4. Environment variable:**
```bash
export SPRING_PROFILES_ACTIVE=prod
```

**Profile-specific Properties:**

```properties
# application-dev.properties
database.url=jdbc:h2:mem:testdb
logging.level=DEBUG

# application-prod.properties
database.url=jdbc:mysql://prod-server:3306/mydb
logging.level=INFO
```

**Using @Profile:**

```java
@Service
@Profile("dev")
public class DevEmailService {
    public void sendEmail() {
        System.out.println("Dev: Email sent (mock)");
    }
}

@Service
@Profile("prod")
public class ProdEmailService {
    public void sendEmail() {
        // Real email sending
    }
}
```

**Multiple Profiles:**

```properties
spring.profiles.active=dev,database,logging
```

**Example:**

```java
@Configuration
public class DatabaseConfig {
    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        return new H2DataSource();
    }
    
    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        return new MySQLDataSource();
    }
}
```

**Key Points:**
- Profiles = Different configs for different environments
- Activate with `spring.profiles.active`
- Use `@Profile` to conditionally create beans
- Profile-specific property files supported

**Simple rule:** Profiles = Different outfits for different occasions (dev, test, prod)!

---

### 2. How to externalize configuration in Spring Boot?

**Answer:**

**Externalize Configuration** = Move configuration outside code (to properties files)

**Why externalize:**
- ✅ Change config without recompiling
- ✅ Different configs for different environments
- ✅ Keep sensitive data out of code

**Ways to Externalize:**

**1. application.properties** (Most common)

```properties
# application.properties
app.name=My Application
app.version=1.0.0
database.url=jdbc:mysql://localhost:3306/mydb
database.username=admin
database.password=secret
```

**2. application.yml** (YAML format)

```yaml
app:
  name: My Application
  version: 1.0.0
database:
  url: jdbc:mysql://localhost:3306/mydb
  username: admin
  password: secret
```

**3. Profile-specific Properties**

```properties
# application-dev.properties
database.url=jdbc:h2:mem:testdb

# application-prod.properties
database.url=jdbc:mysql://prod-server:3306/mydb
```

**4. Environment Variables**

```bash
export DATABASE_URL=jdbc:mysql://localhost:3306/mydb
```

```java
@Value("${database.url}")
private String dbUrl;
```

**5. Command Line Arguments**

```bash
java -jar app.jar --database.url=jdbc:mysql://localhost:3306/mydb
```

**6. External File**

```bash
java -jar app.jar --spring.config.location=file:/path/to/config.properties
```

**Using @Value:**

```java
@Service
public class MyService {
    @Value("${app.name}")
    private String appName;
    
    @Value("${database.url}")
    private String dbUrl;
    
    @Value("${app.version:1.0.0}")  // Default value
    private String version;
}
```

**Using @ConfigurationProperties:**

```java
@ConfigurationProperties(prefix = "database")
public class DatabaseProperties {
    private String url;
    private String username;
    private String password;
    
    // Getters and setters
}

// Usage
@Autowired
private DatabaseProperties dbProps;
```

**Property Priority (Highest to Lowest):**

1. Command line arguments
2. Java system properties
3. Environment variables
4. Profile-specific properties (application-{profile}.properties)
5. application.properties
6. Default values

**Example:**

```properties
# application.properties
app.name=My App
server.port=8080

# application-dev.properties
app.name=My App (Dev)
server.port=8081

# application-prod.properties
app.name=My App (Prod)
server.port=8080
```

**Key Points:**
- Externalize = Move config outside code
- Use properties/yml files
- Support for profiles
- Multiple ways to override

**Simple rule:** Put configuration in properties files, not in code. Change config without changing code!

---

## Spring Boot Starters

### 1. What is Spring Boot Starter?

**Answer:**

**Spring Boot Starter** = Pre-configured dependency group that includes everything needed for a feature

**Think of it like:**
- **Starter** = Meal combo (includes main dish, sides, drink)
- Instead of ordering each item separately, get everything together!

**Example:**

**Without Starter (Old way):**
```xml
<!-- Need to add each dependency manually -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-web</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-core</artifactId>
</dependency>
<!-- ... many more -->
```

**With Starter (Spring Boot way):**
```xml
<!-- Just add one starter - gets everything! -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

**What spring-boot-starter-web includes:**
- Spring MVC
- Embedded Tomcat
- Jackson (JSON)
- Validation
- And more!

**Common Starters:**

**1. spring-boot-starter-web** - Web applications
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

**2. spring-boot-starter-data-jpa** - JPA/数据库
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

**3. spring-boot-starter-security** - Security
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

**4. spring-boot-starter-test** - Testing
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
</dependency>
```

**5. spring-boot-starter-actuator** - Monitoring
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

**Benefits:**
- ✅ One dependency = Everything needed
- ✅ Compatible versions (no conflicts)
- ✅ Less configuration
- ✅ Faster setup

**Simple rule:** Starter = All-in-one package. Add one dependency, get everything for that feature!

---

### 2. What are starters in Spring boot

**Answer:**

**Starters** = Dependency descriptors that bring in all dependencies needed for a feature

**How they work:**

```xml
<!-- Add starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- This starter internally includes:
     - spring-web
     - spring-webmvc
     - tomcat-embed
     - jackson
     - validation
     - etc.
-->
```

**Types of Starters:**

**1. Application Starters** (spring-boot-starter-*)
- For specific features
- Example: `spring-boot-starter-web`, `spring-boot-starter-data-jpa`

**2. Production Starters** (spring-boot-starter-*)
- For production features
- Example: `spring-boot-starter-actuator`

**3. Technical Starters** (spring-boot-starter-*)
- For technical features
- Example: `spring-boot-starter-logging`

**Common Starters:**

| Starter | Purpose | Includes |
|---------|---------|----------|
| spring-boot-starter-web | Web apps | MVC, Tomcat, Jackson |
| spring-boot-starter-data-jpa | Database | JPA, Hibernate |
| spring-boot-starter-security | Security | Spring Security |
| spring-boot-starter-test | Testing | JUnit, Mockito |
| spring-boot-starter-actuator | Monitoring | Health, metrics |

**Example:**

```xml
<!-- pom.xml -->
<dependencies>
    <!-- Web starter -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- JPA starter -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- That's it! Everything configured automatically -->
</dependencies>
```

**Key Points:**
- Starters = Pre-configured dependency groups
- One starter = Multiple dependencies
- Compatible versions guaranteed
- Less manual dependency management

**Simple explanation:** Starters = Smart shopping lists. Instead of listing each item, you say "I want web development" and get everything needed!

---

## Auto-configuration

### 1. How does Spring Boot auto-configuration work?

**Answer:**

**Auto-configuration** = Spring Boot automatically configures beans based on classpath and properties

**How it works:**

**1. @EnableAutoConfiguration**

```java
@SpringBootApplication  // Includes @EnableAutoConfiguration
public class App {
}
```

**2. Spring Boot scans for auto-configuration classes**

Looks in `META-INF/spring.factories`:

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,\
...
```

**3. Conditional Configuration**

```java
@Configuration
@ConditionalOnClass(DataSource.class)  // Only if DataSource exists
@ConditionalOnProperty(name = "spring.datasource.url")
public class DataSourceAutoConfiguration {
    @Bean
    public DataSource dataSource() {
        // Auto-configured DataSource
    }
}
```

**4. Conditions Checked**

- Is class present? (`@ConditionalOnClass`)
- Is property set? (`@ConditionalOnProperty`)
- Is bean missing? (`@ConditionalOnMissingBean`)

**Example Flow:**

```
1. Application starts
       ↓
2. @EnableAutoConfiguration triggered
       ↓
3. Spring Boot scans spring.factories
       ↓
4. Finds DataSourceAutoConfiguration
       ↓
5. Checks conditions:
   - Is DataSource class present? ✅
   - Is spring.datasource.url set? ✅
   - Does DataSource bean exist? ❌
       ↓
6. Creates DataSource bean automatically!
```

**Common Conditions:**

```java
@ConditionalOnClass(DataSource.class)  // Class must exist
@ConditionalOnProperty("spring.datasource.url")  // Property must exist
@ConditionalOnMissingBean(DataSource.class)  // Bean must not exist
@ConditionalOnWebApplication  // Must be web app
```

**Disabling Auto-configuration:**

```java
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class App {
}
```

**Key Points:**
- Auto-configuration = Automatic bean configuration
- Based on classpath and properties
- Uses conditional annotations
- Can be overridden

**Simple explanation:** Auto-configuration = Smart assistant that looks at what you have (dependencies) and automatically sets everything up!

---

## Spring Version

### 1. Spring boot and JDK version

**Answer:**

Spring Boot versions require specific JDK versions:

**Spring Boot 3.x:**
- Requires **JDK 17+** (minimum)
- Supports JDK 17, 19, 21
- Uses Java 17 features

**Spring Boot 2.x:**
- Requires **JDK 8+** (minimum)
- Supports JDK 8, 11, 17
- Most common: JDK 11 (LTS)

**Version Compatibility:**

| Spring Boot | JDK Minimum | JDK Recommended |
|-------------|-------------|-----------------|
| 3.x | JDK 17 | JDK 17 or 21 |
| 2.7.x | JDK 8 | JDK 11 |
| 2.6.x | JDK 8 | JDK 11 |

**Checking Version:**

```bash
# Check Java version
java -version

# Check Spring Boot version in pom.xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.1.0</version>  <!-- Requires JDK 17+ -->
</parent>
```

**Setting JDK Version:**

**In pom.xml:**
```xml
<properties>
    <java.version>17</java.version>
</properties>
```

**Key Points:**
- Spring Boot 3.x = JDK 17+
- Spring Boot 2.x = JDK 8+
- Use LTS versions (JDK 11, 17, 21) for production

**Simple rule:** Check Spring Boot version requirements before choosing JDK version!
