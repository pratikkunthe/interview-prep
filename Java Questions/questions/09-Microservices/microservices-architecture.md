# Microservices Architecture

## Microservices Basics

### 1. What is Microservices Architecture? How is it different from Monolithic?

**Answer:**

**Microservices Architecture** = Building application as collection of small, independent services

**Monolithic Architecture** = Single, unified application

**Think of it like:**
- **Monolithic** = One big building (all rooms together)
- **Microservices** = Multiple small buildings (each building has specific purpose)

**Monolithic:**

```
┌─────────────────────────────┐
│   Single Application        │
│  ┌──────┐ ┌──────┐ ┌──────┐ │
│  │User  │ │Order │ │Payment││
│  │Module│ │Module│ │Module││
│  └──────┘ └──────┘ └──────┘ │
│      Shared Database        │
└─────────────────────────────┘
```

**Microservices:**

```
┌──────────┐  ┌──────────┐  ┌──────────┐
│   User   │  │  Order   │  │ Payment  │
│ Service  │  │ Service  │  │ Service │
│          │  │          │  │          │
│  ┌────┐  │  │  ┌────┐  │  │  ┌────┐  │
│  │ DB │  │  │  │ DB │  │  │  │ DB │  │
│  └────┘  │  │  └────┘  │  │  └────┘  │
└──────────┘  └──────────┘  └──────────┘
```

**Key Differences:**

| Aspect | Monolithic | Microservices |
|--------|------------|---------------|
| Structure | Single application | Multiple services |
| Database | Shared database | Database per service |
| Deployment | Deploy entire app | Deploy services independently |
| Scaling | Scale entire app | Scale individual services |
| Technology | One tech stack | Different tech stacks possible |
| Communication | In-process calls | Network calls (HTTP/gRPC) |
| Failure | Entire app fails | One service fails, others work |

**Example:**

**Monolithic:**
```java
// All in one application
@RestController
public class UserController {
    // User management
}

@RestController
public class OrderController {
    // Order management
}

@RestController
public class PaymentController {
    // Payment processing
}
// All deployed together
```

**Microservices:**
```java
// User Service (separate application)
@RestController
public class UserController {
    // Only user-related code
}

// Order Service (separate application)
@RestController
public class OrderController {
    // Only order-related code
}

// Payment Service (separate application)
@RestController
public class PaymentController {
    // Only payment-related code
}
// Each deployed independently
```

**When to use:**

**Use Monolithic when:**
- Small application
- Simple requirements
- Team is small
- Fast development needed

**Use Microservices when:**
- Large, complex application
- Different teams
- Need independent scaling
- Different technologies needed

**Simple rule:** Monolithic = One big app, Microservices = Many small apps working together!

---

### 2. How do microservices differ from Monolithic Architecture

**Answer:**

**Microservices** = Multiple small services
**Monolithic** = Single large application

**Main Differences:**

**1. Structure**

**Monolithic:**
- One codebase
- One deployment unit
- All modules together

**Microservices:**
- Multiple codebases
- Multiple deployment units
- Each service independent

**2. Database**

**Monolithic:**
```java
// Shared database
@Repository
public class UserRepository {
    // All services use same database
}
```

**Microservices:**
```java
// Each service has own database
// User Service
@Repository
public class UserRepository {
    // User database only
}

// Order Service
@Repository
public class OrderRepository {
    // Order database only
}
```

**3. Communication**

**Monolithic:**
```java
// Direct method calls (in-process)
@Service
public class OrderService {
    @Autowired
    private UserService userService;  // Direct call
    
    public void createOrder() {
        User user = userService.getUser(id);  // Fast, in-process
    }
}
```

**Microservices:**
```java
// Network calls (HTTP/gRPC)
@Service
public class OrderService {
    @Autowired
    private RestTemplate restTemplate;
    
    public void createOrder() {
        // HTTP call to User Service
        User user = restTemplate.getForObject(
            "http://user-service/api/users/{id}", 
            User.class, 
            id
        );
    }
}
```

**4. Deployment**

**Monolithic:**
```bash
# Deploy entire application
java -jar monolithic-app.jar
```

**Microservices:**
```bash
# Deploy each service separately
java -jar user-service.jar
java -jar order-service.jar
java -jar payment-service.jar
```

**5. Scaling**

**Monolithic:**
- Scale entire application
- All components scale together

**Microservices:**
- Scale individual services
- Scale only what's needed

**6. Technology**

**Monolithic:**
- One technology stack
- All modules use same tech

**Microservices:**
- Different tech stacks possible
- Each service can use different language/framework

**Comparison Table:**

| Feature | Monolithic | Microservices |
|--------|------------|---------------|
| Codebase | Single | Multiple |
| Database | Shared | Per service |
| Communication | In-process | Network |
| Deployment | One unit | Multiple units |
| Scaling | All together | Individual |
| Failure | Entire app | One service |
| Technology | One stack | Multiple stacks |

**Simple rule:** Monolithic = One big thing, Microservices = Many small things!

---

### 3. What are the advantages and challenges of Microservices?

**Answer:**

**Advantages:**

**1. Independent Deployment**
- Deploy services independently
- Faster releases
- Less risk

**2. Technology Diversity**
- Use best technology for each service
- Java for one, Node.js for another

**3. Scalability**
- Scale only what's needed
- Order service gets more traffic? Scale only that!

**4. Fault Isolation**
- One service fails, others continue
- Better resilience

**5. Team Autonomy**
- Different teams work on different services
- Faster development

**6. Smaller Codebase**
- Easier to understand
- Easier to maintain

**Challenges:**

**1. Complexity**
- More services = more complexity
- Network calls, service discovery, etc.

**2. Distributed System Issues**
- Network latency
- Partial failures
- Data consistency

**3. Testing**
- Harder to test
- Need to test service interactions

**4. Data Management**
- Data distributed across services
- Transactions across services are hard

**5. Operational Overhead**
- More services to monitor
- More deployments
- More infrastructure

**6. Communication**
- Network calls slower than in-process
- Need to handle failures

**Advantages Summary:**

| Advantage | Benefit |
|-----------|---------|
| Independent Deployment | Faster releases |
| Technology Diversity | Best tool for job |
| Scalability | Scale what's needed |
| Fault Isolation | Better resilience |
| Team Autonomy | Faster development |

**Challenges Summary:**

| Challenge | Impact |
|-----------|-------|
| Complexity | Harder to manage |
| Distributed Issues | Network problems |
| Testing | More complex |
| Data Management | Consistency issues |
| Operational Overhead | More to manage |

**Simple rule:** Microservices = More flexibility and scalability, but more complexity to manage!

---

### 4. Design pattern of Microservices

**Answer:**

Common design patterns used in microservices:

**1. API Gateway Pattern**
- Single entry point for all clients
- Routes requests to appropriate services
- Handles cross-cutting concerns (auth, logging)

```
Client → API Gateway → Services
```

**2. Service Discovery Pattern**
- Services register themselves
- Other services find them dynamically
- Examples: Eureka, Consul

**3. Circuit Breaker Pattern**
- Prevents cascading failures
- Stops calling failing service
- Returns fallback response

**4. Saga Pattern**
- Manages distributed transactions
- Each service has its own transaction
- Compensating actions if failure

**5. Database per Service Pattern**
- Each service has own database
- No shared database
- Better isolation

**6. CQRS (Command Query Responsibility Segregation)**
- Separate read and write models
- Optimize for each operation

**7. Event-Driven Pattern**
- Services communicate via events
- Loose coupling
- Asynchronous

**8. Bulkhead Pattern**
- Isolate resources
- Failure in one area doesn't affect others

**9. Sidecar Pattern**
- Deploy helper component alongside service
- Handles cross-cutting concerns

**10. Strangler Pattern**
- Gradually replace monolithic app
- Migrate piece by piece

**Example - API Gateway:**

```java
// API Gateway routes requests
@RestController
public class ApiGateway {
    
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return restTemplate.getForObject(
            "http://user-service/users/" + id, 
            User.class
        );
    }
    
    @GetMapping("/orders/{id}")
    public Order getOrder(@PathVariable Long id) {
        return restTemplate.getForObject(
            "http://order-service/orders/" + id, 
            Order.class
        );
    }
}
```

**Example - Circuit Breaker:**

```java
@Service
public class OrderService {
    
    @CircuitBreaker(name = "payment-service", fallbackMethod = "fallbackPayment")
    public PaymentResult processPayment(Order order) {
        return paymentService.charge(order);
    }
    
    public PaymentResult fallbackPayment(Order order, Exception e) {
        return new PaymentResult("Payment service unavailable");
    }
}
```

**Key Patterns:**

| Pattern | Purpose |
|--------|---------|
| API Gateway | Single entry point |
| Service Discovery | Find services |
| Circuit Breaker | Handle failures |
| Saga | Distributed transactions |
| Database per Service | Data isolation |

**Simple rule:** Use patterns to solve common microservices problems - gateway, discovery, circuit breaker, etc.!

---

## Microservices Communication

### 1. What are the endpoints in Microservices

**Answer:**

**Endpoints** = URLs where microservices expose their functionality

**Think of it like:** Addresses where services can be reached

**Example Endpoints:**

**User Service:**
```
GET    /api/users           - Get all users
GET    /api/users/{id}      - Get user by ID
POST   /api/users           - Create user
PUT    /api/users/{id}      - Update user
DELETE /api/users/{id}     - Delete user
```

**Order Service:**
```
GET    /api/orders          - Get all orders
GET    /api/orders/{id}     - Get order by ID
POST   /api/orders          - Create order
PUT    /api/orders/{id}     - Update order
GET    /api/orders/user/{userId} - Get user's orders
```

**Payment Service:**
```
POST   /api/payments        - Process payment
GET    /api/payments/{id}   - Get payment status
POST   /api/payments/refund - Refund payment
```

**Implementation:**

```java
// User Service
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }
    
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }
}

// Order Service
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }
    
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.findById(id);
    }
    
    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return orderService.findByUserId(userId);
    }
}
```

**Key Points:**
- Each service exposes REST endpoints
- Endpoints are service-specific
- Use standard HTTP methods (GET, POST, PUT, DELETE)
- RESTful naming conventions

**Simple rule:** Endpoints = Service addresses. Each service has its own set of endpoints!

---

### 2. How can we communicate in two microservices

**Answer:**

Ways microservices communicate:

**1. Synchronous Communication (REST)**

```java
// Order Service calls User Service
@Service
public class OrderService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public Order createOrder(OrderRequest request) {
        // Call User Service
        User user = restTemplate.getForObject(
            "http://user-service/api/users/" + request.getUserId(),
            User.class
        );
        
        // Create order
        Order order = new Order();
        order.setUser(user);
        return orderRepository.save(order);
    }
}
```

**2. Asynchronous Communication (Messaging)**

```java
// Order Service publishes event
@Service
public class OrderService {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void createOrder(Order order) {
        orderRepository.save(order);
        
        // Publish event
        OrderCreatedEvent event = new OrderCreatedEvent(order.getId());
        rabbitTemplate.convertAndSend("order-exchange", "order.created", event);
    }
}

// Payment Service subscribes to event
@Component
public class PaymentListener {
    
    @RabbitListener(queues = "order-created-queue")
    public void handleOrderCreated(OrderCreatedEvent event) {
        // Process payment for order
        paymentService.processPayment(event.getOrderId());
    }
}
```

**3. Service Discovery**

```java
// Using Eureka
@Service
public class OrderService {
    
    @Autowired
    private DiscoveryClient discoveryClient;
    
    public User getUser(Long userId) {
        // Find User Service
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
        String url = instances.get(0).getUri().toString();
        
        // Call service
        return restTemplate.getForObject(url + "/api/users/" + userId, User.class);
    }
}
```

**4. Feign Client (Declarative REST)**

```java
// Feign Client interface
@FeignClient(name = "user-service")
public interface UserServiceClient {
    
    @GetMapping("/api/users/{id}")
    User getUser(@PathVariable Long id);
    
    @PostMapping("/api/users")
    User createUser(@RequestBody User user);
}

// Usage
@Service
public class OrderService {
    
    @Autowired
    private UserServiceClient userServiceClient;
    
    public Order createOrder(OrderRequest request) {
        // Call via Feign (like calling local method)
        User user = userServiceClient.getUser(request.getUserId());
        // ...
    }
}
```

**Complete Example:**

```java
// User Service
@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }
}

// Order Service calling User Service
@Service
public class OrderService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public Order createOrder(Long userId, OrderDetails details) {
        // Synchronous call
        User user = restTemplate.getForObject(
            "http://user-service/api/users/" + userId,
            User.class
        );
        
        Order order = new Order();
        order.setUser(user);
        order.setDetails(details);
        return orderRepository.save(order);
    }
}
```

**Key Points:**
- Synchronous = REST calls (wait for response)
- Asynchronous = Messaging (fire and forget)
- Use service discovery to find services
- Feign makes REST calls easier

**Simple rule:** Services communicate via REST (synchronous) or messaging (asynchronous)!

---

### 3. How do microservices communicate with each other? (REST, gRPC, Messaging)

**Answer:**

Three main ways microservices communicate:

**1. REST (HTTP/JSON)** ✅ (Most common)

```java
// Service A calls Service B via REST
@Service
public class OrderService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public Order createOrder(OrderRequest request) {
        // REST call to User Service
        User user = restTemplate.getForObject(
            "http://user-service/api/users/" + request.getUserId(),
            User.class
        );
        
        Order order = new Order();
        order.setUser(user);
        return orderRepository.save(order);
    }
}
```

**Pros:**
- ✅ Simple, widely used
- ✅ Language agnostic
- ✅ Easy to debug

**Cons:**
- ❌ Slower than gRPC
- ❌ More overhead

**2. gRPC (Remote Procedure Call)**

```java
// Define service in .proto file
service UserService {
    rpc GetUser(UserRequest) returns (UserResponse);
}

// Client code
@Service
public class OrderService {
    
    @Autowired
    private UserServiceGrpc.UserServiceBlockingStub userServiceStub;
    
    public Order createOrder(OrderRequest request) {
        // gRPC call
        UserResponse response = userServiceStub.getUser(
            UserRequest.newBuilder()
                .setUserId(request.getUserId())
                .build()
        );
        
        // Use response
        Order order = new Order();
        order.setUserId(response.getUserId());
        return orderRepository.save(order);
    }
}
```

**Pros:**
- ✅ Fast (binary protocol)
- ✅ Type-safe
- ✅ Streaming support

**Cons:**
- ❌ More complex
- ❌ Less flexible

**3. Messaging (Asynchronous)**

```java
// Publisher (Order Service)
@Service
public class OrderService {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void createOrder(Order order) {
        orderRepository.save(order);
        
        // Publish event
        OrderCreatedEvent event = new OrderCreatedEvent(order.getId());
        rabbitTemplate.convertAndSend("order-exchange", "order.created", event);
    }
}

// Subscriber (Payment Service)
@Component
public class PaymentListener {
    
    @RabbitListener(queues = "order-created-queue")
    public void handleOrderCreated(OrderCreatedEvent event) {
        paymentService.processPayment(event.getOrderId());
    }
}
```

**Pros:**
- ✅ Decoupled services
- ✅ Scalable
- ✅ Resilient

**Cons:**
- ❌ Eventual consistency
- ❌ More complex

**Comparison:**

| Method | Type | Speed | Use Case |
|--------|------|-------|----------|
| REST | Synchronous | Medium | Request-response |
| gRPC | Synchronous | Fast | High performance |
| Messaging | Asynchronous | Fast | Event-driven |

**When to use:**

**Use REST when:**
- Simple communication
- HTTP-based
- Standard APIs

**Use gRPC when:**
- High performance needed
- Internal service communication
- Type safety important

**Use Messaging when:**
- Asynchronous processing
- Event-driven architecture
- Decoupling needed

**Simple rule:** REST for simple, gRPC for fast, Messaging for async!

---

### 4. How microservices communicate in your project

**Answer:**

This is a project-specific question. Here's a typical answer structure:

**Example Answer:**

"In our project, microservices communicate using multiple methods:

**1. Synchronous Communication - REST API**
- Services use REST for request-response patterns
- We use Feign Client for declarative REST calls
- Example: Order Service calls User Service to get user details

```java
@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/api/users/{id}")
    User getUser(@PathVariable Long id);
}
```

**2. Asynchronous Communication - RabbitMQ**
- Services publish events for async processing
- Example: When order is created, Order Service publishes event, Payment Service processes it

**3. Service Discovery - Eureka**
- Services register with Eureka
- Other services discover them dynamically

**4. API Gateway - Spring Cloud Gateway**
- All external requests go through API Gateway
- Gateway routes to appropriate services"

**Key Points to Mention:**
- Synchronous vs Asynchronous
- Service discovery mechanism
- API Gateway usage
- Message broker (if used)
- Specific technologies (Feign, RestTemplate, etc.)

---

### 5. Microservices communication

**Answer:**

Microservices communicate in two main ways:

**1. Synchronous Communication**
- Request-response pattern
- Waits for response
- Examples: REST, gRPC

**2. Asynchronous Communication**
- Event-driven
- Fire and forget
- Examples: Messaging (RabbitMQ, Kafka)

**Synchronous Example:**

```java
// Order Service calls User Service
User user = restTemplate.getForObject(
    "http://user-service/api/users/123",
    User.class
);
// Waits for response before continuing
```

**Asynchronous Example:**

```java
// Order Service publishes event
rabbitTemplate.convertAndSend("order-created", event);
// Doesn't wait, continues immediately

// Payment Service receives event later
@RabbitListener(queues = "order-created")
public void handle(OrderCreatedEvent event) {
    // Process asynchronously
}
```

**Simple rule:** Synchronous = Wait for response, Asynchronous = Don't wait!

---

### 6. What is synchronous vs. asynchronous communication in Microservices?

**Answer:**

**Synchronous** = Wait for response before continuing
**Asynchronous** = Don't wait, continue immediately

**Synchronous Communication:**

```java
// Order Service
@Service
public class OrderService {
    
    public Order createOrder(OrderRequest request) {
        // Call User Service - WAITS for response
        User user = restTemplate.getForObject(
            "http://user-service/api/users/" + request.getUserId(),
            User.class
        );
        
        // Only continues after getting user
        Order order = new Order();
        order.setUser(user);
        return orderRepository.save(order);
    }
}
```

**Characteristics:**
- ✅ Immediate response
- ✅ Know if call succeeded
- ❌ Blocks until response
- ❌ Tight coupling

**Asynchronous Communication:**

```java
// Order Service
@Service
public class OrderService {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void createOrder(OrderRequest request) {
        // Save order
        Order order = orderRepository.save(new Order());
        
        // Publish event - DOESN'T WAIT
        OrderCreatedEvent event = new OrderCreatedEvent(order.getId());
        rabbitTemplate.convertAndSend("order-created", event);
        
        // Continues immediately, doesn't wait
    }
}

// Payment Service (receives event later)
@Component
public class PaymentListener {
    
    @RabbitListener(queues = "order-created")
    public void handleOrderCreated(OrderCreatedEvent event) {
        // Processes asynchronously
        paymentService.processPayment(event.getOrderId());
    }
}
```

**Characteristics:**
- ✅ Non-blocking
- ✅ Loose coupling
- ✅ Scalable
- ❌ Eventual consistency
- ❌ Don't know immediate result

**Comparison:**

| Aspect | Synchronous | Asynchronous |
|--------|-------------|--------------|
| Waiting | Waits for response | Doesn't wait |
| Coupling | Tight | Loose |
| Response Time | Immediate | Eventual |
| Failure Handling | Know immediately | Handle later |
| Use Case | Request-response | Event processing |

**When to use:**

**Use Synchronous when:**
- Need immediate response
- Request-response pattern
- Need to know result immediately

**Use Asynchronous when:**
- Can process later
- Event-driven
- Want to decouple services

**Simple rule:** Synchronous = Wait, Asynchronous = Don't wait!

---

## Spring Cloud

### 1. What is Spring Cloud?

**Answer:**

**Spring Cloud** = Tools for building microservices with Spring

**Think of it like:** Toolbox for microservices - provides everything you need!

**What it provides:**

**1. Service Discovery (Eureka)**
- Services register and discover each other
- No hardcoded URLs

**2. API Gateway (Spring Cloud Gateway)**
- Single entry point
- Routing, filtering, load balancing

**3. Configuration Server**
- Centralized configuration
- Dynamic updates

**4. Circuit Breaker (Resilience4j)**
- Handle failures gracefully
- Prevent cascading failures

**5. Load Balancing**
- Distribute requests across instances

**6. Distributed Tracing**
- Track requests across services

**Example:**

```java
// Service Discovery
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}

// Feign Client (with load balancing)
@FeignClient(name = "order-service")
public interface OrderServiceClient {
    @GetMapping("/api/orders/{id}")
    Order getOrder(@PathVariable Long id);
}

// Circuit Breaker
@Service
public class OrderService {
    
    @CircuitBreaker(name = "payment-service", fallbackMethod = "fallback")
    public PaymentResult processPayment(Order order) {
        return paymentService.charge(order);
    }
    
    public PaymentResult fallback(Order order, Exception e) {
        return new PaymentResult("Service unavailable");
    }
}
```

**Key Components:**

| Component | Purpose |
|-----------|---------|
| Eureka | Service Discovery |
| Gateway | API Gateway |
| Config Server | Configuration Management |
| Circuit Breaker | Failure Handling |
| Feign | Declarative REST |

**Simple rule:** Spring Cloud = Tools to build microservices easily with Spring!

---

## API Gateway

### 1. What is the role of API Gateway in Microservices?

**Answer:**

**API Gateway** = Single entry point for all client requests

**Think of it like:** Reception desk - all visitors go through one place

**Roles:**

**1. Single Entry Point**
- Clients don't call services directly
- All requests go through gateway

**2. Routing**
- Routes requests to appropriate service
- Load balancing

**3. Authentication/Authorization**
- Centralized security
- Validate tokens

**4. Rate Limiting**
- Control request rate
- Prevent abuse

**5. Logging/Monitoring**
- Centralized logging
- Request tracking

**6. Request/Response Transformation**
- Modify requests/responses
- Format conversion

**Example:**

```
Client → API Gateway → Services
                ↓
         ┌──────┴──────┐
         │            │
    User Service  Order Service
```

**Implementation:**

```java
@Configuration
public class GatewayConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user-service", r -> r
                .path("/api/users/**")
                .uri("lb://user-service"))
            .route("order-service", r -> r
                .path("/api/orders/**")
                .uri("lb://order-service"))
            .build();
    }
    
    @Bean
    public GlobalFilter customFilter() {
        return (exchange, chain) -> {
            // Add authentication, logging, etc.
            return chain.filter(exchange);
        };
    }
}
```

**Benefits:**
- ✅ Single entry point
- ✅ Centralized security
- ✅ Request routing
- ✅ Load balancing
- ✅ Monitoring

**Simple rule:** API Gateway = Front door to your microservices. All requests come through here!

---

### 2. What is API Gateway

**Answer:**

**API Gateway** = Single entry point that routes requests to microservices

**Without API Gateway:**

```
Client → User Service
Client → Order Service
Client → Payment Service
```

**With API Gateway:**

```
Client → API Gateway → User Service
                    → Order Service
                    → Payment Service
```

**What it does:**

1. **Routes requests** to correct service
2. **Handles authentication** centrally
3. **Load balances** across service instances
4. **Monitors** requests
5. **Rate limits** to prevent abuse

**Example:**

```java
// Spring Cloud Gateway
@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}

// Routes
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/api/users/**
        - id: order-service
          uri: lb://order-service
          predicates:
            - Path=/api/orders/**
```

**Key Points:**
- Single entry point
- Routes to services
- Handles cross-cutting concerns
- Load balancing

**Simple explanation:** API Gateway = Receptionist who directs visitors (requests) to the right department (service)!

---

## Error Handling

### 1. How to handle when api of one microservice is giving error

**Answer:**

Ways to handle errors when one microservice fails:

**1. Circuit Breaker Pattern**

```java
@Service
public class OrderService {
    
    @CircuitBreaker(name = "payment-service", fallbackMethod = "fallbackPayment")
    public PaymentResult processPayment(Order order) {
        // Call Payment Service
        return paymentService.charge(order);
    }
    
    // Fallback when Payment Service is down
    public PaymentResult fallbackPayment(Order order, Exception e) {
        // Save order for later processing
        order.setStatus("PENDING_PAYMENT");
        orderRepository.save(order);
        
        return new PaymentResult("Payment service unavailable, will retry later");
    }
}
```

**2. Retry Mechanism**

```java
@Service
public class OrderService {
    
    @Retryable(value = {Exception.class}, maxAttempts = 3)
    public User getUser(Long userId) {
        return userServiceClient.getUser(userId);
    }
    
    @Recover
    public User recover(Exception e, Long userId) {
        // Return cached user or default
        return getCachedUser(userId);
    }
}
```

**3. Timeout**

```java
@Service
public class OrderService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public User getUser(Long userId) {
        try {
            // Set timeout
            HttpComponentsClientHttpRequestFactory factory = 
                new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000);
            factory.setReadTimeout(5000);
            
            restTemplate.setRequestFactory(factory);
            
            return restTemplate.getForObject(
                "http://user-service/api/users/" + userId,
                User.class
            );
        } catch (Exception e) {
            // Handle timeout
            return getDefaultUser();
        }
    }
}
```

**4. Bulkhead Pattern**

```java
// Isolate resources
@Configuration
public class BulkheadConfig {
    
    @Bean
    public ThreadPoolTaskExecutor paymentExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        return executor;
    }
}
```

**5. Graceful Degradation**

```java
@Service
public class OrderService {
    
    public OrderDetails getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId);
        
        OrderDetails details = new OrderDetails();
        details.setOrder(order);
        
        try {
            // Try to get user details
            User user = userServiceClient.getUser(order.getUserId());
            details.setUser(user);
        } catch (Exception e) {
            // Service down, but continue with partial data
            details.setUser(null);
            log.warn("User service unavailable", e);
        }
        
        return details;
    }
}
```

**6. Global Exception Handler**

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException e) {
        if(e.status() == 503) {
            // Service unavailable
            return ResponseEntity.status(503)
                .body(new ErrorResponse("Service temporarily unavailable"));
        }
        return ResponseEntity.status(500)
            .body(new ErrorResponse("Internal server error"));
    }
}
```

**Complete Example:**

```java
@Service
public class OrderService {
    
    @Autowired
    private PaymentServiceClient paymentServiceClient;
    
    @Autowired
    private UserServiceClient userServiceClient;
    
    @CircuitBreaker(name = "payment-service", fallbackMethod = "fallbackPayment")
    @Retryable(maxAttempts = 3)
    public Order createOrder(OrderRequest request) {
        // Get user (with retry)
        User user = userServiceClient.getUser(request.getUserId());
        
        // Create order
        Order order = new Order();
        order.setUser(user);
        order = orderRepository.save(order);
        
        // Process payment (with circuit breaker)
        PaymentResult result = paymentServiceClient.processPayment(order.getId());
        order.setPaymentStatus(result.getStatus());
        
        return orderRepository.save(order);
    }
    
    // Fallback when payment service fails
    public Order fallbackPayment(OrderRequest request, Exception e) {
        User user = userServiceClient.getUser(request.getUserId());
        Order order = new Order();
        order.setUser(user);
        order.setStatus("PENDING_PAYMENT");  // Save for later
        return orderRepository.save(order);
    }
}
```

**Key Strategies:**

| Strategy | Purpose |
|----------|---------|
| Circuit Breaker | Stop calling failing service |
| Retry | Retry failed calls |
| Timeout | Don't wait forever |
| Fallback | Alternative response |
| Graceful Degradation | Continue with partial data |

**Simple rule:** Use Circuit Breaker to stop calling failing services, Retry for transient failures, Fallback for alternatives!

---

## Design Patterns

### 1. What is saga design pattern

**Answer:**

**Saga Pattern** = Manages distributed transactions across multiple microservices

**Problem:** In microservices, you can't use traditional ACID transactions across services

**Solution:** Saga = Sequence of local transactions, each with compensating action

**Think of it like:** Multi-step process where each step can be undone if something fails

**Types:**

**1. Choreography Saga** (Event-driven)
- Each service knows what to do next
- Services communicate via events

**2. Orchestration Saga** (Centralized)
- Orchestrator coordinates all steps
- Centralized control

**Choreography Example:**

```java
// Order Service
@Service
public class OrderService {
    
    public void createOrder(Order order) {
        // Step 1: Create order
        order.setStatus("CREATED");
        orderRepository.save(order);
        
        // Publish event
        eventPublisher.publish(new OrderCreatedEvent(order.getId()));
    }
}

// Payment Service (listens to OrderCreated)
@Component
public class PaymentListener {
    
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        try {
            // Step 2: Charge payment
            paymentService.charge(event.getOrderId());
            
            // Publish success event
            eventPublisher.publish(new PaymentSucceededEvent(event.getOrderId()));
        } catch (Exception e) {
            // Publish failure event
            eventPublisher.publish(new PaymentFailedEvent(event.getOrderId()));
        }
    }
}

// Order Service (listens to PaymentFailed)
@Component
public class OrderListener {
    
    @EventListener
    public void handlePaymentFailed(PaymentFailedEvent event) {
        // Compensating action: Cancel order
        Order order = orderRepository.findById(event.getOrderId());
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }
}
```

**Orchestration Example:**

```java
// Saga Orchestrator
@Service
public class OrderSagaOrchestrator {
    
    public void createOrder(OrderRequest request) {
        SagaContext context = new SagaContext();
        
        try {
            // Step 1: Create order
            Order order = orderService.createOrder(request);
            context.setOrderId(order.getId());
            
            // Step 2: Reserve inventory
            inventoryService.reserve(order.getItems());
            context.setInventoryReserved(true);
            
            // Step 3: Charge payment
            paymentService.charge(order.getId());
            context.setPaymentCharged(true);
            
            // Step 4: Ship order
            shippingService.ship(order.getId());
            
        } catch (Exception e) {
            // Compensate: Undo all steps
            compensate(context);
        }
    }
    
    private void compensate(SagaContext context) {
        if(context.isPaymentCharged()) {
            paymentService.refund(context.getOrderId());
        }
        if(context.isInventoryReserved()) {
            inventoryService.release(context.getOrderId());
        }
        if(context.getOrderId() != null) {
            orderService.cancel(context.getOrderId());
        }
    }
}
```

**Key Points:**
- Saga = Sequence of transactions
- Each step has compensating action
- Choreography = Event-driven
- Orchestration = Centralized

**Simple rule:** Saga = Multi-step transaction where each step can be undone if something fails!

---

### 2. What is circuit breaker design pattern

**Answer:**

**Circuit Breaker Pattern** = Prevents cascading failures by stopping calls to failing service

**Think of it like:** Electrical circuit breaker - stops current when there's a problem

**States:**

**1. Closed** (Normal)
- Calls go through
- Monitors failures

**2. Open** (Failing)
- Stops calling service
- Returns fallback immediately

**3. Half-Open** (Testing)
- Tries one call
- If succeeds → Closed
- If fails → Open

**Example:**

```java
@Service
public class OrderService {
    
    @CircuitBreaker(name = "payment-service", fallbackMethod = "fallbackPayment")
    public PaymentResult processPayment(Order order) {
        // Call Payment Service
        return paymentService.charge(order);
    }
    
    // Fallback when circuit is open
    public PaymentResult fallbackPayment(Order order, Exception e) {
        // Save for later processing
        order.setStatus("PENDING_PAYMENT");
        orderRepository.save(order);
        
        return new PaymentResult("Payment service unavailable");
    }
}
```

**Configuration:**

```java
@Configuration
public class CircuitBreakerConfig {
    
    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        return CircuitBreakerRegistry.of(
            CircuitBreakerConfig.custom()
                .failureRateThreshold(50)  // Open after 50% failures
                .waitDurationInOpenState(Duration.ofSeconds(30))  // Wait 30s before half-open
                .slidingWindowSize(10)  // Check last 10 calls
                .build()
        );
    }
}
```

**Benefits:**
- ✅ Prevents cascading failures
- ✅ Fast failure response
- ✅ Service recovery time
- ✅ Fallback mechanism

**Simple rule:** Circuit Breaker = Stop calling failing service, return fallback instead!

---

### 3. What is Circuit Breaker, and how does it work? (@HystrixCommand or Resilience4j)

**Answer:**

**Circuit Breaker** = Pattern to prevent cascading failures

**How it works:**

```
1. Service calls another service
       ↓
2. If failures exceed threshold → Circuit Opens
       ↓
3. Future calls return fallback immediately (no actual call)
       ↓
4. After timeout → Circuit Half-Open (test one call)
       ↓
5. If succeeds → Circuit Closed (normal)
   If fails → Circuit Open again
```

**Using Resilience4j:**

```java
@Service
public class OrderService {
    
    @Autowired
    private CircuitBreaker circuitBreaker;
    
    @Autowired
    private PaymentServiceClient paymentServiceClient;
    
    public PaymentResult processPayment(Order order) {
        return circuitBreaker.executeSupplier(() -> {
            return paymentServiceClient.charge(order.getId());
        });
    }
}
```

**Using @CircuitBreaker (Resilience4j):**

```java
@Service
public class OrderService {
    
    @CircuitBreaker(name = "payment-service", fallbackMethod = "fallback")
    public PaymentResult processPayment(Order order) {
        return paymentServiceClient.charge(order.getId());
    }
    
    public PaymentResult fallback(Order order, Exception e) {
        return new PaymentResult("Service unavailable");
    }
}
```

**Configuration:**

```yaml
resilience4j:
  circuitbreaker:
    instances:
      payment-service:
        failureRateThreshold: 50
        waitDurationInOpenState: 30s
        slidingWindowSize: 10
```

**Key Points:**
- Prevents calling failing service
- Returns fallback immediately
- Automatically recovers
- Configurable thresholds

**Simple rule:** Circuit Breaker = Smart switch that stops calling failing services and uses fallback!

---

## Monitoring

### 1. What are the spring actuators and how to enable them and what are its endpoints

**Answer:**

**Spring Boot Actuator** = Production-ready features for monitoring and managing application

**Think of it like:** Health check and monitoring dashboard for your application

**Enable Actuator:**

**1. Add Dependency:**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

**2. Configure:**

```properties
# application.properties
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
```

**Actuator Endpoints:**

**1. /actuator/health** - Application health
```json
{
  "status": "UP",
  "components": {
    "db": {"status": "UP"},
    "diskSpace": {"status": "UP"}
  }
}
```

**2. /actuator/info** - Application information
```json
{
  "app": {
    "name": "My Application",
    "version": "1.0.0"
  }
}
```

**3. /actuator/metrics** - Application metrics
```
GET /actuator/metrics
GET /actuator/metrics/jvm.memory.used
```

**4. /actuator/env** - Environment variables
```
GET /actuator/env
```

**5. /actuator/loggers** - Logger configuration
```
GET /actuator/loggers
POST /actuator/loggers/com.example (change level)
```

**6. /actuator/beans** - All Spring beans
```
GET /actuator/beans
```

**7. /actuator/mappings** - All request mappings
```
GET /actuator/mappings
```

**8. /actuator/threaddump** - Thread dump
```
GET /actuator/threaddump
```

**9. /actuator/heapdump** - Heap dump
```
GET /actuator/heapdump
```

**Custom Health Check:**

```java
@Component
public class CustomHealthIndicator implements HealthIndicator {
    
    @Override
    public Health health() {
        // Check something
        if(isServiceUp()) {
            return Health.up()
                .withDetail("service", "Available")
                .build();
        }
        return Health.down()
            .withDetail("service", "Unavailable")
            .build();
    }
    
    private boolean isServiceUp() {
        // Your check logic
        return true;
    }
}
```

**Custom Info:**

```properties
# application.properties
info.app.name=My Application
info.app.version=1.0.0
info.app.description=Spring Boot Application
```

**Security:**

```java
@Configuration
public class ActuatorSecurityConfig {
    
    @Bean
    public SecurityFilterChain actuatorSecurity(HttpSecurity http) throws Exception {
        http
            .requestMatchers("/actuator/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/health").permitAll()
                .anyRequest().hasRole("ADMIN")
            );
        return http.build();
    }
}
```

**Key Endpoints:**

| Endpoint | Purpose |
|---------|---------|
| /actuator/health | Health status |
| /actuator/info | Application info |
| /actuator/metrics | Metrics |
| /actuator/env | Environment |
| /actuator/loggers | Logger config |

**Simple rule:** Actuator = Monitoring dashboard. Enable it, expose endpoints, and monitor your application!
