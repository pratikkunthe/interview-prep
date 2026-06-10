# Design Patterns

## Creational Patterns

### 1. Singleton Design pattern

**Answer:**

**Singleton Pattern** = Ensures only ONE instance of a class exists

**Think of it like:** Only one manager in a company - everyone uses the same one

**Why use it:**
- When you need exactly one instance
- Shared resource (database connection, logger)
- Expensive object creation

**Implementation:**

**Method 1: Eager Initialization**

```java
public class Singleton {
    // Create instance immediately
    private static final Singleton instance = new Singleton();
    
    // Private constructor - can't create from outside
    private Singleton() {
    }
    
    // Get the only instance
    public static Singleton getInstance() {
        return instance;
    }
}
```

**Method 2: Lazy Initialization**

```java
public class Singleton {
    private static Singleton instance;
    
    private Singleton() {
    }
    
    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

**Method 3: Thread-Safe (Synchronized)**

```java
public class Singleton {
    private static Singleton instance;
    
    private Singleton() {
    }
    
    // Thread-safe but slow
    public static synchronized Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

**Method 4: Double-Checked Locking**

```java
public class Singleton {
    private static volatile Singleton instance;
    
    private Singleton() {
    }
    
    public static Singleton getInstance() {
        if(instance == null) {
            synchronized(Singleton.class) {
                if(instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

**Method 5: Bill Pugh Solution** ✅ (Best)

```java
public class Singleton {
    private Singleton() {
    }
    
    // Inner class - loaded only when getInstance() is called
    private static class SingletonHelper {
        private static final Singleton INSTANCE = new Singleton();
    }
    
    public static Singleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
```

**Spring Singleton (Different!):**

```java
@Service  // Spring creates one instance per application context
public class UserService {
    // Spring manages singleton, not Java singleton pattern
}
```

**Example:**

```java
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    private DatabaseConnection() {
        // Create database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb");
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static DatabaseConnection getInstance() {
        if(instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    public Connection getConnection() {
        return connection;
    }
}

// Usage
DatabaseConnection db1 = DatabaseConnection.getInstance();
DatabaseConnection db2 = DatabaseConnection.getInstance();
// db1 and db2 are the SAME instance
```

**Key Points:**
- Private constructor
- Static method to get instance
- Only one instance exists
- Thread-safe implementation needed for multi-threaded apps

**Simple rule:** Singleton = Only one instance allowed. Use when you need shared resource!

---

### 2. Singleton pattern vs Prototype pattern

**Answer:**

**Singleton** = One instance shared by all
**Prototype** = New instance each time

**Singleton:**

```java
@Service  // Spring default - singleton
public class UserService {
    // Same instance used everywhere
}

// Usage
UserService service1 = context.getBean(UserService.class);
UserService service2 = context.getBean(UserService.class);
// service1 == service2 (same instance)
```

**Prototype:**

```java
@Component
@Scope("prototype")  // New instance each time
public class UserService {
    // Different instance each time
}

// Usage
UserService service1 = context.getBean(UserService.class);
UserService service2 = context.getBean(UserService.class);
// service1 != service2 (different instances)
```

**Comparison:**

| Aspect | Singleton | Prototype |
|--------|-----------|-----------|
| Instances | One | Many |
| State | Shared | Independent |
| Memory | Less | More |
| Use When | Stateless or shared state | Stateful, need isolation |

**When to use:**

**Use Singleton when:**
- Stateless service
- Shared resource
- Expensive to create

**Use Prototype when:**
- Stateful object
- Need isolation
- Different state per usage

**Example:**

```java
// Singleton - Shared state
@Service
public class CounterService {
    private int count = 0;  // Shared!
    
    public void increment() {
        count++;
    }
    
    public int getCount() {
        return count;
    }
}

// Prototype - Independent state
@Component
@Scope("prototype")
public class UserSession {
    private String userId;
    private List<String> activities = new ArrayList<>();
    
    // Each instance has its own state
}
```

**Simple rule:** Singleton = One shared instance, Prototype = New instance each time!

---

## Microservices Patterns

### 1. What is saga design pattern

**Answer:**

**Saga Pattern** = Manages distributed transactions across microservices

**Problem:** Can't use ACID transactions across services

**Solution:** Sequence of local transactions with compensating actions

**Example:**

```java
// Order Saga
public class OrderSaga {
    
    public void createOrder(OrderRequest request) {
        try {
            // Step 1: Create order
            Order order = orderService.create(request);
            
            // Step 2: Reserve inventory
            inventoryService.reserve(order.getItems());
            
            // Step 3: Charge payment
            paymentService.charge(order.getId());
            
            // Step 4: Ship
            shippingService.ship(order.getId());
            
        } catch(Exception e) {
            // Compensate: Undo all steps
            compensate(orderId);
        }
    }
    
    private void compensate(Long orderId) {
        paymentService.refund(orderId);
        inventoryService.release(orderId);
        orderService.cancel(orderId);
    }
}
```

**Key Points:**
- Each step is local transaction
- If failure, compensate (undo)
- Two types: Choreography (event-driven) or Orchestration (centralized)

**Simple rule:** Saga = Multi-step transaction where each step can be undone!

---

### 2. What is circuit breaker design pattern

**Answer:**

**Circuit Breaker** = Stops calling failing service to prevent cascading failures

**States:**
- **Closed:** Normal operation
- **Open:** Service failing, return fallback
- **Half-Open:** Testing if service recovered

**Example:**

```java
@CircuitBreaker(name = "payment-service", fallbackMethod = "fallback")
public PaymentResult processPayment(Order order) {
    return paymentService.charge(order);
}

public PaymentResult fallback(Order order, Exception e) {
    return new PaymentResult("Service unavailable");
}
```

**Simple rule:** Circuit Breaker = Stop calling failing service, use fallback instead!

---

## Project Experience

### 1. Design pattern which used in project

**Answer:**

This is project-specific. Common patterns used:

**Example Answer:**

"In our project, we used several design patterns:

**1. Singleton Pattern**
- Database connection pool
- Configuration manager
- Logger instance

**2. Factory Pattern**
- Creating different payment processors
- Creating different notification services

**3. Strategy Pattern**
- Different pricing strategies
- Different discount calculation methods

**4. Repository Pattern**
- Data access abstraction
- Easy to switch databases

**5. Observer Pattern**
- Event-driven architecture
- Notifications when order status changes

**6. Builder Pattern**
- Complex object creation (Order, User)
- Fluent API for configuration"

**Key Points to Mention:**
- Specific patterns used
- Why you used them
- Real examples from project
- Benefits achieved

---

## SOLID Principles

### 1. What are SOLID Principles

**Answer:**

**SOLID** = Five principles for writing better object-oriented code

**S - Single Responsibility Principle**
- One class should have one reason to change
- One job per class

```java
// Bad - Multiple responsibilities
class User {
    void save() { }
    void sendEmail() { }
    void generateReport() { }
}

// Good - Single responsibility
class User {
    void save() { }
}

class EmailService {
    void sendEmail() { }
}

class ReportService {
    void generateReport() { }
}
```

**O - Open/Closed Principle**
- Open for extension, closed for modification
- Add new features without changing existing code

```java
// Bad - Need to modify existing code
class Shape {
    void draw() {
        if(type == "circle") {
            // draw circle
        } else if(type == "rectangle") {
            // draw rectangle
        }
    }
}

// Good - Extend without modifying
abstract class Shape {
    abstract void draw();
}

class Circle extends Shape {
    void draw() { /* draw circle */ }
}

class Rectangle extends Shape {
    void draw() { /* draw rectangle */ }
}
```

**L - Liskov Substitution Principle**
- Subtypes must be substitutable for their base types
- Child class should work wherever parent class works

```java
// Bad - Violates LSP
class Bird {
    void fly() { }
}

class Penguin extends Bird {
    void fly() {
        throw new UnsupportedOperationException();  // Can't fly!
    }
}

// Good - Follows LSP
abstract class Bird {
    abstract void move();
}

class Sparrow extends Bird {
    void move() { fly(); }
}

class Penguin extends Bird {
    void move() { swim(); }
}
```

**I - Interface Segregation Principle**
- Clients shouldn't depend on interfaces they don't use
- Many specific interfaces better than one general interface

```java
// Bad - One big interface
interface Worker {
    void work();
    void eat();
    void sleep();
}

class Robot implements Worker {
    void work() { }
    void eat() { }  // Robots don't eat!
    void sleep() { }  // Robots don't sleep!
}

// Good - Segregated interfaces
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class Robot implements Workable {
    void work() { }
}

class Human implements Workable, Eatable {
    void work() { }
    void eat() { }
}
```

**D - Dependency Inversion Principle**
- Depend on abstractions, not concretions
- High-level modules shouldn't depend on low-level modules

```java
// Bad - Depends on concrete class
class OrderService {
    private MySQLDatabase database = new MySQLDatabase();  // Concrete!
}

// Good - Depends on abstraction
class OrderService {
    private Database database;  // Interface!
    
    public OrderService(Database database) {
        this.database = database;
    }
}

interface Database {
    void save();
}

class MySQLDatabase implements Database {
    void save() { }
}

class PostgreSQLDatabase implements Database {
    void save() { }
}
```

**Summary:**

| Principle | Meaning | Example |
|-----------|---------|---------|
| S | One responsibility | One class, one job |
| O | Open for extension | Add features without changing |
| L | Substitutable subtypes | Child works like parent |
| I | Segregated interfaces | Many small interfaces |
| D | Depend on abstractions | Use interfaces, not classes |

**Benefits:**
- ✅ Maintainable code
- ✅ Flexible design
- ✅ Easy to test
- ✅ Less coupling

**Simple rule:** SOLID = Five rules for better code. Follow them for cleaner, more maintainable code!
