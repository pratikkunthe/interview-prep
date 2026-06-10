# Spring Framework Interview Questions

## 1. What is Spring Framework?

**Answer:**

Spring is a lightweight, open-source Java framework for building enterprise applications. It provides infrastructure support so you can focus on business logic.

**Core Modules:**
1. **Spring Core** - Dependency Injection (DI) and Inversion of Control (IoC)
2. **Spring MVC** - Web applications
3. **Spring Boot** - Auto-configuration and embedded servers
4. **Spring Data** - Database access
5. **Spring Security** - Authentication and authorization
6. **Spring AOP** - Aspect-Oriented Programming

**Benefits:**
- Reduces boilerplate code
- Easy to test
- Loose coupling (using DI)
- Modular architecture

---

## 2. What is Dependency Injection (DI)?

**Answer:**

Dependency Injection is a design pattern where objects don't create their dependencies - they receive them from outside.

**Without DI:**
```java
public class UserService {
    private UserRepository repository = new UserRepository();  // tight coupling
    
    public void saveUser(User user) {
        repository.save(user);
    }
}
```

**With DI (Spring):**
```java
@Service
public class UserService {
    private final UserRepository repository;
    
    @Autowired  // Spring injects dependency
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    public void saveUser(User user) {
        repository.save(user);
    }
}
```

**Benefits:**
- Loose coupling
- Easy to test (can inject mock objects)
- Reusable code

---

## 3. What is Inversion of Control (IoC)?

**Answer:**

IoC is a principle where the control of object creation and lifecycle is transferred from the application to a container (Spring IoC Container).

**Traditional Approach:**
```java
// You control object creation
UserService service = new UserService();
service.saveUser(user);
```

**Spring IoC:**
```java
// Spring creates and manages objects
@Autowired
private UserService service;  // Spring injects it

service.saveUser(user);
```

**IoC Container:**
- Creates objects (beans)
- Manages lifecycle
- Injects dependencies
- Handles configuration

---

## 4. What are the types of Dependency Injection?

**Answer:**

**1. Constructor Injection (Recommended):**
```java
@Service
public class UserService {
    private final UserRepository repository;
    
    @Autowired  // Optional in Spring 4.3+ if only one constructor
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

**Benefits:** Immutable, required dependencies, easy to test

**2. Setter Injection:**
```java
@Service
public class UserService {
    private UserRepository repository;
    
    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }
}
```

**Benefits:** Optional dependencies, can change dependencies at runtime

**3. Field Injection (Not recommended):**
```java
@Service
public class UserService {
    @Autowired
    private UserRepository repository;  // Hard to test
}
```

**Benefits:** Less code, but hard to test and violates immutability

---

## 5. What is the difference between @Component, @Service, @Repository, and @Controller?

**Answer:**

All are specializations of `@Component` for semantic clarity.

| Annotation | Purpose | Layer |
|------------|---------|-------|
| @Component | Generic component | Any |
| @Service | Business logic | Service |
| @Repository | Data access | Persistence |
| @Controller | Web requests | Presentation |

```java
@Component
public class GenericComponent {
    // Generic bean
}

@Service
public class UserService {
    // Business logic
    public void processUser() { }
}

@Repository
public class UserRepository {
    // Database operations
    public void save(User user) { }
}

@Controller
public class UserController {
    // Handle HTTP requests
    @GetMapping("/users")
    public String getUsers() { }
}
```

**Why use specific annotations?**
- Better readability
- `@Repository` provides automatic exception translation
- Spring can apply different behaviors to different stereotypes

---

## 6. What is the difference between @Component and @Bean?

**Answer:**

| @Component | @Bean |
|------------|-------|
| Class-level | Method-level |
| Used on your classes | Used on third-party classes |
| Auto-detected by component scan | Manual bean definition |
| No customization needed | Can customize bean creation |

**@Component:**
```java
@Component
public class EmailService {
    public void sendEmail(String message) {
        // send email
    }
}
```

**@Bean:**
```java
@Configuration
public class AppConfig {
    
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        // customize configuration
        template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return template;
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();  // third-party class
    }
}
```

**When to use:**
- `@Component`: Your own classes
- `@Bean`: Third-party libraries, complex bean creation

---

## 7. What is @Autowired and how does it work?

**Answer:**

`@Autowired` tells Spring to inject a dependency automatically.

**How it works:**
1. Spring scans for beans
2. Finds matching bean by type
3. Injects it

```java
@Service
public class UserService {
    
    @Autowired
    private UserRepository repository;  // Field injection
    
    @Autowired
    public UserService(UserRepository repository) {  // Constructor injection
        this.repository = repository;
    }
    
    @Autowired
    public void setRepository(UserRepository repository) {  // Setter injection
        this.repository = repository;
    }
}
```

**Autowiring by Type:**
```java
@Autowired
private UserRepository repository;  // Spring finds bean of type UserRepository
```

**Autowiring with @Qualifier (when multiple beans of same type):**
```java
@Autowired
@Qualifier("mysqlRepository")
private UserRepository repository;
```

**Optional Dependencies:**
```java
@Autowired(required = false)
private OptionalService service;  // Won't fail if bean not found
```

---

## 8. What is Bean Scope in Spring?

**Answer:**

Bean scope defines the lifecycle and visibility of a bean.

| Scope | Description | Instances |
|-------|-------------|-----------|
| Singleton (default) | One instance per Spring container | 1 |
| Prototype | New instance every time | Many |
| Request | One instance per HTTP request | Many (web) |
| Session | One instance per HTTP session | Many (web) |
| Application | One instance per ServletContext | 1 (web) |

**Singleton (default):**
```java
@Component
public class UserService {
    // Only one instance created
}
```

**Prototype:**
```java
@Component
@Scope("prototype")
public class UserService {
    // New instance every time it's requested
}

@Autowired
private UserService service1;  // instance 1

@Autowired
private UserService service2;  // instance 2 (different from service1)
```

**Request Scope (Web):**
```java
@Component
@Scope("request")
public class UserPreference {
    // New instance for each HTTP request
}
```

---

## 9. What is the Bean lifecycle in Spring?

**Answer:**

**Lifecycle Phases:**
1. Instantiation
2. Populate properties
3. Call `setBeanName()` (BeanNameAware)
4. Call `setBeanFactory()` (BeanFactoryAware)
5. Pre-initialization (BeanPostProcessor)
6. Call `afterPropertiesSet()` (InitializingBean)
7. Call custom init method (`@PostConstruct` or `init-method`)
8. Post-initialization (BeanPostProcessor)
9. Bean ready to use
10. Call `destroy()` (DisposableBean)
11. Call custom destroy method (`@PreDestroy` or `destroy-method`)

**Practical Example:**
```java
@Component
public class DatabaseConnection {
    
    @PostConstruct
    public void init() {
        System.out.println("Opening database connection");
        // Initialize connection
    }
    
    @PreDestroy
    public void cleanup() {
        System.out.println("Closing database connection");
        // Close connection
    }
}
```

**Using InitializingBean and DisposableBean:**
```java
@Component
public class DatabaseConnection implements InitializingBean, DisposableBean {
    
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Bean initialized");
    }
    
    @Override
    public void destroy() throws Exception {
        System.out.println("Bean destroyed");
    }
}
```

---

## 10. What is @Configuration and how is it different from @Component?

**Answer:**

**@Configuration:**
- Class-level annotation for configuration classes
- Contains `@Bean` methods
- Methods are proxied to ensure singleton behavior

```java
@Configuration
public class AppConfig {
    
    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }
    
    @Bean
    public UserRepository userRepository() {
        return new UserRepository();
    }
}
```

**@Component:**
- Generic component
- No `@Bean` methods
- Not proxied

```java
@Component
public class EmailService {
    public void sendEmail(String message) {
        // send email
    }
}
```

**Key Difference - Proxying:**
```java
@Configuration
public class Config {
    
    @Bean
    public Service service1() {
        return new Service(helper());  // calls helper()
    }
    
    @Bean
    public Service service2() {
        return new Service(helper());  // returns SAME helper bean
    }
    
    @Bean
    public Helper helper() {
        return new Helper();
    }
}
```

With `@Configuration`, both services get the same `Helper` instance (singleton).
With `@Component`, each call to `helper()` creates a new instance.

---

## 11. What is @Qualifier and when to use it?

**Answer:**

`@Qualifier` resolves ambiguity when multiple beans of the same type exist.

**Problem:**
```java
@Component
public class MySQLDatabase implements Database { }

@Component
public class MongoDatabase implements Database { }

@Service
public class UserService {
    @Autowired
    private Database database;  // ERROR: Which one to inject?
}
```

**Solution:**
```java
@Service
public class UserService {
    
    @Autowired
    @Qualifier("mySQLDatabase")
    private Database database;  // Injects MySQLDatabase
}
```

**Alternative - @Primary:**
```java
@Component
@Primary
public class MySQLDatabase implements Database { }

@Component
public class MongoDatabase implements Database { }

@Service
public class UserService {
    @Autowired
    private Database database;  // Injects MySQLDatabase (marked as @Primary)
}
```

---

## 12. What is Spring AOP (Aspect-Oriented Programming)?

**Answer:**

AOP separates cross-cutting concerns (logging, security, transactions) from business logic.

**Key Concepts:**
- **Aspect** - Cross-cutting concern (logging, security)
- **Advice** - Action taken (before, after, around)
- **Pointcut** - Where to apply advice (method expression)
- **Join Point** - Method execution point

**Example - Logging:**
```java
@Aspect
@Component
public class LoggingAspect {
    
    // Before advice
    @Before("execution(* com.example.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Before method: " + joinPoint.getSignature().getName());
    }
    
    // After advice
    @After("execution(* com.example.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("After method: " + joinPoint.getSignature().getName());
    }
    
    // Around advice
    @Around("execution(* com.example.service.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before method");
        Object result = joinPoint.proceed();  // execute actual method
        System.out.println("After method");
        return result;
    }
}
```

**Use Cases:**
- Logging
- Transaction management
- Security
- Performance monitoring

---

## 13. What is @Transactional?

**Answer:**

`@Transactional` manages database transactions automatically. If an exception occurs, it rolls back the transaction.

```java
@Service
public class BankService {
    
    @Transactional
    public void transferMoney(Account from, Account to, double amount) {
        from.withdraw(amount);  // Step 1
        to.deposit(amount);     // Step 2
        
        // If exception occurs, both operations are rolled back
    }
}
```

**Transaction Propagation:**
```java
@Transactional(propagation = Propagation.REQUIRED)  // Default
public void method1() { }

@Transactional(propagation = Propagation.REQUIRES_NEW)  // New transaction
public void method2() { }
```

**Isolation Levels:**
```java
@Transactional(isolation = Isolation.READ_COMMITTED)
public void method() { }
```

**Rollback:**
```java
@Transactional(rollbackFor = Exception.class)  // Rollback on any exception
public void method() { }

@Transactional(noRollbackFor = CustomException.class)  // Don't rollback
public void method() { }
```

---

## 14. What is the difference between ApplicationContext and BeanFactory?

**Answer:**

Both are IoC containers, but ApplicationContext is more feature-rich.

| Feature | BeanFactory | ApplicationContext |
|---------|-------------|-------------------|
| Lazy loading | Yes | No (eager by default) |
| i18n support | No | Yes |
| Event publication | No | Yes |
| AOP support | Manual | Automatic |
| Annotation support | No | Yes |

**BeanFactory (basic):**
```java
BeanFactory factory = new XmlBeanFactory(new ClassPathResource("beans.xml"));
MyBean bean = (MyBean) factory.getBean("myBean");
```

**ApplicationContext (advanced):**
```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
MyBean bean = context.getBean(MyBean.class);
```

**When to use:**
- **BeanFactory**: Memory-constrained environments (mobile)
- **ApplicationContext**: Enterprise applications (99% of cases)

---

## 15. What is @Value annotation?

**Answer:**

`@Value` injects values from properties files or environment variables.

**application.properties:**
```properties
app.name=MyApp
app.version=1.0
server.url=http://localhost:8080
```

**Usage:**
```java
@Component
public class AppConfig {
    
    @Value("${app.name}")
    private String appName;
    
    @Value("${app.version}")
    private String version;
    
    @Value("${server.url}")
    private String serverUrl;
    
    @Value("${app.timeout:5000}")  // Default value 5000 if not found
    private int timeout;
    
    public void printConfig() {
        System.out.println("App: " + appName);      // MyApp
        System.out.println("Version: " + version);  // 1.0
    }
}
```

**SpEL (Spring Expression Language):**
```java
@Value("#{systemProperties['user.name']}")
private String userName;

@Value("#{10 * 2}")
private int result;  // 20

@Value("#{'Hello ' + 'World'}")
private String message;  // Hello World
```
