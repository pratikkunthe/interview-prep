# Microservices Interview Questions

## 1. What are Microservices?

**Answer:**

Microservices is an architectural style where an application is built as a collection of small, independent services that communicate over a network.

**Monolithic vs Microservices:**

**Monolithic:**
```
Single Application
├── User Module
├── Order Module
├── Payment Module
└── Inventory Module
```
- Single codebase
- Single deployment
- Tightly coupled

**Microservices:**
```
User Service (Port 8081)
Order Service (Port 8082)
Payment Service (Port 8083)
Inventory Service (Port 8084)
```
- Separate codebases
- Independent deployment
- Loosely coupled

**Benefits:**
- Independent scaling
- Technology diversity
- Faster development
- Fault isolation
- Easy to understand and maintain

**Challenges:**
- Distributed system complexity
- Network latency
- Data consistency
- Testing complexity

---

## 2. What is Service Discovery?

**Answer:**

Service Discovery automatically detects services in a network so they can communicate without hardcoding URLs.

**Problem without Service Discovery:**
```java
// Hardcoded URL - what if service moves or scales?
String url = "http://localhost:8082/orders";
```

**Solution: Eureka (Netflix Service Discovery):**

**Eureka Server:**
```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

```properties
# application.properties
server.port=8761
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

**Eureka Client (Service):**
```java
@SpringBootApplication
@EnableDiscoveryClient
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
```

```properties
# application.properties
spring.application.name=order-service
server.port=8082
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

**Service Communication:**
```java
@Service
public class UserService {
    
    @Autowired
    private DiscoveryClient discoveryClient;
    
    public void callOrderService() {
        // Get service instance from Eureka
        List<ServiceInstance> instances = discoveryClient.getInstances("order-service");
        ServiceInstance instance = instances.get(0);
        
        String url = instance.getUri() + "/orders";
        // Make REST call
    }
}
```

---

## 3. What is API Gateway?

**Answer:**

API Gateway is a single entry point for all client requests. It routes requests to appropriate microservices.

**Without API Gateway:**
```
Client → User Service (8081)
Client → Order Service (8082)
Client → Payment Service (8083)
```
Client needs to know all service URLs!

**With API Gateway:**
```
Client → API Gateway (8080) → User Service (8081)
                           → Order Service (8082)
                           → Payment Service (8083)
```

**Spring Cloud Gateway Example:**

```java
@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
```

```yaml
# application.yml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://user-service  # lb = load balanced
          predicates:
            - Path=/users/**
        
        - id: order-service
          uri: lb://order-service
          predicates:
            - Path=/orders/**
        
        - id: payment-service
          uri: lb://payment-service
          predicates:
            - Path=/payments/**
```

**Benefits:**
- Single entry point
- Authentication/Authorization
- Rate limiting
- Load balancing
- Logging and monitoring

---

## 4. What is Load Balancing?

**Answer:**

Load balancing distributes incoming requests across multiple instances of a service.

**Types:**

**1. Server-side Load Balancing (Traditional):**
```
Client → Load Balancer → Service Instance 1
                      → Service Instance 2
                      → Service Instance 3
```

**2. Client-side Load Balancing (Microservices):**
```java
// Spring Cloud LoadBalancer
@Service
public class UserService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public List<Order> getOrders() {
        // RestTemplate automatically load balances between instances
        String url = "http://order-service/orders";
        return restTemplate.getForObject(url, List.class);
    }
}

@Configuration
public class Config {
    
    @Bean
    @LoadBalanced  // Enable client-side load balancing
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

**Load Balancing Algorithms:**
- **Round Robin** - Distributes requests sequentially
- **Random** - Random service instance
- **Least Connections** - Instance with fewest connections
- **Weighted** - Based on server capacity

---

## 5. What is Circuit Breaker Pattern?

**Answer:**

Circuit Breaker prevents cascading failures when a service is down. It's like an electrical circuit breaker.

**States:**
1. **CLOSED** - Requests flow normally
2. **OPEN** - Service is down, requests fail immediately (no waiting)
3. **HALF_OPEN** - After timeout, try a few requests to check if service recovered

**Resilience4j Circuit Breaker:**

```xml
<dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-spring-boot2</artifactId>
</dependency>
```

```java
@Service
public class UserService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @CircuitBreaker(name = "orderService", fallbackMethod = "getOrdersFallback")
    public List<Order> getOrders(Long userId) {
        String url = "http://order-service/orders/" + userId;
        return restTemplate.getForObject(url, List.class);
    }
    
    // Fallback method - called when circuit is OPEN
    public List<Order> getOrdersFallback(Long userId, Exception ex) {
        System.out.println("Order service is down! Returning empty list");
        return Collections.emptyList();
    }
}
```

```yaml
# application.yml
resilience4j:
  circuitbreaker:
    instances:
      orderService:
        sliding-window-size: 10
        failure-rate-threshold: 50  # Open circuit if 50% requests fail
        wait-duration-in-open-state: 10s  # Stay open for 10 seconds
        permitted-number-of-calls-in-half-open-state: 3
```

**Benefits:**
- Prevents cascading failures
- Fast failure (no waiting for timeout)
- Automatic recovery

---

## 6. What is Distributed Tracing?

**Answer:**

Distributed Tracing tracks requests across multiple microservices to debug issues and monitor performance.

**Problem:**
```
Client → API Gateway → User Service → Order Service → Payment Service
```
Which service is slow? Where did the request fail?

**Solution: Sleuth + Zipkin:**

**Add Dependencies:**
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-sleuth</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-sleuth-zipkin</artifactId>
</dependency>
```

**Configuration:**
```properties
spring.zipkin.base-url=http://localhost:9411
spring.sleuth.sampler.probability=1.0  # Sample 100% of requests
```

**Automatic Tracing:**
```java
@RestController
public class UserController {
    
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        // Sleuth automatically adds trace ID to logs
        log.info("Getting user: {}", id);  // [appname,traceId,spanId]
        
        User user = userService.getUser(id);
        
        // Call another service
        List<Order> orders = orderService.getOrders(id);
        
        return user;
    }
}
```

**Log Output:**
```
[user-service,abc123,xyz789] Getting user: 1
[order-service,abc123,pqr456] Getting orders for user: 1
[payment-service,abc123,mno789] Processing payment
```

**Zipkin UI:**
- Visualize request flow
- See latency for each service
- Find bottlenecks

---

## 7. What is Saga Pattern?

**Answer:**

Saga Pattern manages distributed transactions across multiple microservices. Each service executes a local transaction and publishes an event.

**Problem:**
```
Order Service → Payment Service → Inventory Service
```
What if Payment succeeds but Inventory fails? Need to rollback Payment!

**Solution: Choreography-based Saga:**

```java
// Order Service
@Service
public class OrderService {
    
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    
    public void createOrder(Order order) {
        // Step 1: Create order
        orderRepository.save(order);
        
        // Step 2: Publish event
        OrderEvent event = new OrderEvent(order.getId(), "ORDER_CREATED");
        kafkaTemplate.send("order-events", event);
    }
    
    // Listen for payment failure
    @KafkaListener(topics = "payment-events")
    public void handlePaymentFailed(PaymentEvent event) {
        if (event.getStatus().equals("PAYMENT_FAILED")) {
            // Compensating transaction - cancel order
            orderRepository.cancelOrder(event.getOrderId());
        }
    }
}

// Payment Service
@Service
public class PaymentService {
    
    @Autowired
    private KafkaTemplate<String, PaymentEvent> kafkaTemplate;
    
    @KafkaListener(topics = "order-events")
    public void handleOrderCreated(OrderEvent event) {
        try {
            // Process payment
            processPayment(event.getOrderId());
            
            // Publish success event
            PaymentEvent paymentEvent = new PaymentEvent(event.getOrderId(), "PAYMENT_SUCCESS");
            kafkaTemplate.send("payment-events", paymentEvent);
            
        } catch (Exception e) {
            // Publish failure event
            PaymentEvent paymentEvent = new PaymentEvent(event.getOrderId(), "PAYMENT_FAILED");
            kafkaTemplate.send("payment-events", paymentEvent);
        }
    }
}
```

**Types:**
1. **Choreography** - Services communicate through events (no central coordinator)
2. **Orchestration** - Central coordinator (Saga Orchestrator) manages workflow

---

## 8. What is Configuration Management in Microservices?

**Answer:**

Centralized configuration management for all microservices using Spring Cloud Config.

**Config Server:**

```java
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```

```properties
# application.properties
server.port=8888
spring.cloud.config.server.git.uri=https://github.com/myrepo/config-repo
```

**Git Repository Structure:**
```
config-repo/
├── application.yml           # Common config for all services
├── user-service.yml         # User service config
├── order-service.yml        # Order service config
└── payment-service.yml      # Payment service config
```

**Client (Microservice):**

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

```properties
# bootstrap.properties
spring.application.name=user-service
spring.cloud.config.uri=http://localhost:8888
```

**Refresh Configuration without restart:**

```java
@RestController
@RefreshScope  // Enable dynamic refresh
public class UserController {
    
    @Value("${app.message}")
    private String message;
    
    @GetMapping("/message")
    public String getMessage() {
        return message;
    }
}
```

```bash
# Trigger refresh
POST /actuator/refresh
```

---

## 9. What is the difference between Synchronous and Asynchronous communication?

**Answer:**

**Synchronous (REST API):**
- Request-Response model
- Caller waits for response
- Blocking

```java
@Service
public class UserService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public void createUser(User user) {
        userRepository.save(user);
        
        // Synchronous call - waits for response
        String response = restTemplate.postForObject(
            "http://email-service/send",
            new Email(user.getEmail(), "Welcome"),
            String.class
        );
        
        System.out.println("Email sent: " + response);
    }
}
```

**Asynchronous (Message Queue):**
- Fire and forget
- Caller doesn't wait for response
- Non-blocking

```java
@Service
public class UserService {
    
    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;
    
    public void createUser(User user) {
        userRepository.save(user);
        
        // Asynchronous - doesn't wait
        UserEvent event = new UserEvent(user.getId(), user.getEmail());
        kafkaTemplate.send("user-created", event);
        
        System.out.println("Event published, moving on");
    }
}

// Email Service listens to event
@Service
public class EmailService {
    
    @KafkaListener(topics = "user-created")
    public void handleUserCreated(UserEvent event) {
        sendEmail(event.getEmail(), "Welcome");
    }
}
```

**When to use:**
- **Synchronous**: Need immediate response (payment processing)
- **Asynchronous**: Don't need immediate response (sending emails, notifications)

---

## 10. What is Service Mesh?

**Answer:**

Service Mesh is an infrastructure layer that handles service-to-service communication (routing, load balancing, security, monitoring).

**Popular Service Mesh: Istio**

**Features:**
1. **Traffic Management** - Load balancing, retries, timeouts
2. **Security** - mTLS, authentication
3. **Observability** - Tracing, metrics, logging
4. **Resilience** - Circuit breaking, rate limiting

**Without Service Mesh:**
```java
// Each service needs to implement:
- Retries
- Circuit breaker
- Load balancing
- Metrics
- Security
```

**With Service Mesh:**
```java
// Service mesh handles all cross-cutting concerns
// Services only focus on business logic
```

**Architecture:**
```
Service A → Sidecar Proxy (Envoy) → Sidecar Proxy (Envoy) → Service B
```

Each service has a sidecar proxy that handles communication.

---

## 11. What is the difference between API Gateway and Load Balancer?

**Answer:**

| API Gateway | Load Balancer |
|-------------|---------------|
| Application layer (Layer 7) | Network layer (Layer 4) |
| Routing, authentication, transformation | Only distributes traffic |
| Single entry point for APIs | Distributes among instances |
| Can modify requests/responses | Cannot modify |

**API Gateway:**
```yaml
routes:
  - path: /users/**
    service: user-service
    filters:
      - Authentication
      - Rate Limiting
      - Request Transformation
```

**Load Balancer:**
```
Client → Load Balancer → User Service Instance 1
                      → User Service Instance 2
                      → User Service Instance 3
```

**Use both together:**
```
Client → API Gateway → Load Balancer → User Service Instances
                    → Load Balancer → Order Service Instances
```

---

## 12. What is Database per Service pattern?

**Answer:**

Each microservice has its own database. Services don't share databases.

**Shared Database (Monolithic - BAD in Microservices):**
```
User Service ─┐
Order Service ├── Single Database
Payment Service ─┘
```

**Database per Service (Microservices - GOOD):**
```
User Service → User Database
Order Service → Order Database
Payment Service → Payment Database
```

**Benefits:**
- Loose coupling
- Independent scaling
- Technology diversity (MySQL, MongoDB, PostgreSQL)
- Fault isolation

**Challenges:**
- Distributed transactions (solved by Saga pattern)
- Data duplication
- Joins across services

**Example:**
```java
// User Service
@Entity
public class User {
    private Long id;
    private String name;
    private String email;
}

// Order Service (has its own User data)
@Entity
public class Order {
    private Long id;
    private Long userId;  // Reference to User
    private String userName;  // Denormalized data
}
```

---

## 13. What is Event-Driven Architecture?

**Answer:**

Services communicate through events (asynchronous messages).

**Components:**
- **Event Producer** - Publishes events
- **Event Broker** - Kafka, RabbitMQ
- **Event Consumer** - Subscribes to events

**Example:**

```java
// Order Service (Producer)
@Service
public class OrderService {
    
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    
    public void createOrder(Order order) {
        orderRepository.save(order);
        
        // Publish event
        OrderEvent event = new OrderEvent(
            order.getId(),
            order.getUserId(),
            order.getAmount(),
            "ORDER_CREATED"
        );
        kafkaTemplate.send("order-events", event);
    }
}

// Email Service (Consumer)
@Service
public class EmailService {
    
    @KafkaListener(topics = "order-events")
    public void handleOrderCreated(OrderEvent event) {
        if (event.getStatus().equals("ORDER_CREATED")) {
            sendOrderConfirmation(event.getUserId());
        }
    }
}

// Inventory Service (Consumer)
@Service
public class InventoryService {
    
    @KafkaListener(topics = "order-events")
    public void handleOrderCreated(OrderEvent event) {
        if (event.getStatus().equals("ORDER_CREATED")) {
            updateInventory(event.getProductId());
        }
    }
}
```

**Benefits:**
- Loose coupling
- Scalability
- Resilience
- Real-time processing

---

## 14. What is CQRS Pattern?

**Answer:**

CQRS (Command Query Responsibility Segregation) separates read and write operations.

**Traditional (Single Model):**
```java
@Entity
public class Product {
    private Long id;
    private String name;
    private Double price;
}

// Same model for read and write
productRepository.save(product);    // Write
productRepository.findById(id);     // Read
```

**CQRS (Separate Models):**

```java
// Command Model (Write)
@Entity
public class ProductCommand {
    private Long id;
    private String name;
    private Double price;
    private LocalDateTime createdAt;
    private String createdBy;
}

// Query Model (Read)
@Entity
public class ProductQuery {
    private Long id;
    private String name;
    private Double price;
    // Optimized for reading
}

// Command Service (Write)
@Service
public class ProductCommandService {
    
    public void createProduct(Product product) {
        productCommandRepository.save(product);
        
        // Publish event
        productEventPublisher.publish(new ProductCreatedEvent(product));
    }
}

// Query Service (Read)
@Service
public class ProductQueryService {
    
    @EventListener
    public void handleProductCreated(ProductCreatedEvent event) {
        // Update read model
        ProductQuery query = new ProductQuery(event.getId(), event.getName(), event.getPrice());
        productQueryRepository.save(query);
    }
    
    public List<ProductQuery> getAllProducts() {
        return productQueryRepository.findAll();
    }
}
```

**Benefits:**
- Optimized read and write models
- Better performance
- Scalability

---

## 15. What are the best practices for Microservices?

**Answer:**

1. **Single Responsibility** - Each service does one thing well
2. **Database per Service** - Don't share databases
3. **API Gateway** - Single entry point
4. **Service Discovery** - Dynamic service registration
5. **Circuit Breaker** - Prevent cascading failures
6. **Distributed Tracing** - Track requests across services
7. **Centralized Logging** - ELK stack (Elasticsearch, Logstash, Kibana)
8. **Containerization** - Docker for consistent deployment
9. **Orchestration** - Kubernetes for container management
10. **CI/CD** - Automated testing and deployment
11. **Monitoring** - Prometheus, Grafana
12. **Security** - OAuth2, JWT, mTLS
13. **Version APIs** - `/api/v1/users`, `/api/v2/users`
14. **Idempotency** - Retrying same request produces same result
15. **Backward Compatibility** - Don't break existing clients
