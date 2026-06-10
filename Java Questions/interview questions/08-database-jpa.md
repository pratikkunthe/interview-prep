# Database & JPA Interview Questions

## 1. What is JPA (Java Persistence API)?

**Answer:**

JPA is a specification for Object-Relational Mapping (ORM) in Java. It maps Java objects to database tables.

**Key Components:**
- **Entity** - Java class mapped to database table
- **EntityManager** - Manages entity lifecycle
- **JPQL** - Java Persistence Query Language
- **Hibernate** - Most popular JPA implementation

**Entity Example:**
```java
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_name", nullable = false, length = 50)
    private String name;
    
    @Column(unique = true)
    private String email;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
```

**Repository:**
```java
public interface UserRepository extends JpaRepository<User, Long> {
    // No code needed - JPA provides CRUD methods
}

// Usage
userRepository.save(user);          // CREATE
userRepository.findById(1L);        // READ
userRepository.findAll();           // READ ALL
userRepository.deleteById(1L);      // DELETE
```

---

## 2. What are JPA Relationships?

**Answer:**

**1. One-to-One:**
```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private UserProfile profile;
}

@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String bio;
    private String website;
}
```

**2. One-to-Many / Many-to-One:**
```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees;
}

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
```

**3. Many-to-Many:**
```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;
}

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;
}
```

---

## 3. What is the difference between CascadeType options?

**Answer:**

| CascadeType | Description |
|-------------|-------------|
| ALL | All operations cascade |
| PERSIST | Save operation cascades |
| MERGE | Update operation cascades |
| REMOVE | Delete operation cascades |
| REFRESH | Refresh operation cascades |
| DETACH | Detach operation cascades |

**Example:**
```java
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> items;
}

// Usage
Order order = new Order();
order.setItems(Arrays.asList(
    new OrderItem("Product 1", 100),
    new OrderItem("Product 2", 200)
));

orderRepository.save(order);  // Saves order AND all items (because of CascadeType.ALL)

orderRepository.delete(order);  // Deletes order AND all items
```

**Without Cascade:**
```java
@OneToMany(mappedBy = "order")  // No cascade
private List<OrderItem> items;

// Must save manually
Order order = new Order();
orderRepository.save(order);

OrderItem item = new OrderItem();
item.setOrder(order);
itemRepository.save(item);  // Must save separately
```

---

## 4. What is FetchType.LAZY vs FetchType.EAGER?

**Answer:**

**LAZY (Default for @OneToMany, @ManyToMany):**
- Loads related entities only when accessed
- Better performance (avoids unnecessary queries)

```java
@Entity
public class User {
    @Id
    private Long id;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders;  // Not loaded until accessed
}

// Usage
User user = userRepository.findById(1L);  // SELECT * FROM users WHERE id = 1
// orders not loaded yet

user.getOrders();  // NOW executes: SELECT * FROM orders WHERE user_id = 1
```

**EAGER (Default for @OneToOne, @ManyToOne):**
- Loads related entities immediately
- Can cause performance issues (N+1 problem)

```java
@Entity
public class Order {
    @Id
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;  // Loaded immediately
}

// Usage
Order order = orderRepository.findById(1L);
// Executes: SELECT * FROM orders o JOIN users u WHERE o.id = 1
// User is already loaded
```

**Best Practice: Use LAZY by default**

---

## 5. What is the N+1 Query Problem?

**Answer:**

N+1 problem occurs when loading a collection executes 1 query + N additional queries (one for each item).

**Problem:**
```java
@Entity
public class User {
    @Id
    private Long id;
    
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}

// Code
List<User> users = userRepository.findAll();  // 1 query

for (User user : users) {
    user.getOrders();  // N queries (one per user)
}

// Total: 1 + N queries!
```

**Solution 1: @EntityGraph**
```java
public interface UserRepository extends JpaRepository<User, Long> {
    
    @EntityGraph(attributePaths = {"orders"})
    List<User> findAll();  // Single query with JOIN
}

// Executes: SELECT u.*, o.* FROM users u LEFT JOIN orders o ON u.id = o.user_id
```

**Solution 2: JPQL with JOIN FETCH**
```java
@Query("SELECT u FROM User u LEFT JOIN FETCH u.orders")
List<User> findAllWithOrders();
```

**Solution 3: @BatchSize**
```java
@Entity
public class User {
    @OneToMany(mappedBy = "user")
    @BatchSize(size = 10)  // Load orders in batches of 10
    private List<Order> orders;
}
```

---

## 6. What is the difference between save() and saveAll()?

**Answer:**

**save()** - Saves single entity
```java
User user = new User("John", "john@example.com");
userRepository.save(user);

// Executes: INSERT INTO users (name, email) VALUES ('John', 'john@example.com')
```

**saveAll()** - Saves multiple entities (more efficient)
```java
List<User> users = Arrays.asList(
    new User("John", "john@example.com"),
    new User("Jane", "jane@example.com"),
    new User("Bob", "bob@example.com")
);

userRepository.saveAll(users);

// Executes batch insert (single database call)
```

**Performance Comparison:**
```java
// Bad - Multiple database calls
for (User user : users) {
    userRepository.save(user);  // N database calls
}

// Good - Single batch insert
userRepository.saveAll(users);  // 1 database call
```

---

## 7. What are JPQL and Native Queries?

**Answer:**

**JPQL (Java Persistence Query Language):**
- Object-oriented query language
- Uses entity names and properties
- Database-independent

```java
public interface UserRepository extends JpaRepository<User, Long> {
    
    // JPQL - uses entity name (User) not table name (users)
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);
    
    @Query("SELECT u FROM User u WHERE u.age > :age")
    List<User> findByAgeGreaterThan(@Param("age") int age);
    
    @Query("SELECT u FROM User u WHERE u.name LIKE %:name%")
    List<User> searchByName(@Param("name") String name);
}
```

**Native Query:**
- Uses actual SQL
- Database-specific
- Better for complex queries

```java
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    User findByEmailNative(@Param("email") String email);
    
    @Query(value = "SELECT u.*, COUNT(o.id) as order_count " +
                   "FROM users u LEFT JOIN orders o ON u.id = o.user_id " +
                   "GROUP BY u.id", 
           nativeQuery = true)
    List<Object[]> getUsersWithOrderCount();
}
```

**When to use:**
- JPQL: Default choice (database-independent)
- Native: Complex joins, database-specific features

---

## 8. What are Derived Query Methods?

**Answer:**

Spring Data JPA automatically generates queries from method names.

```java
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Automatically generates: SELECT * FROM users WHERE name = ?
    User findByName(String name);
    
    // SELECT * FROM users WHERE email = ?
    User findByEmail(String email);
    
    // SELECT * FROM users WHERE age > ?
    List<User> findByAgeGreaterThan(int age);
    
    // SELECT * FROM users WHERE age < ?
    List<User> findByAgeLessThan(int age);
    
    // SELECT * FROM users WHERE name LIKE %?%
    List<User> findByNameContaining(String name);
    
    // SELECT * FROM users WHERE name = ? AND email = ?
    User findByNameAndEmail(String name, String email);
    
    // SELECT * FROM users WHERE name = ? OR email = ?
    List<User> findByNameOrEmail(String name, String email);
    
    // SELECT * FROM users ORDER BY name ASC
    List<User> findAllByOrderByNameAsc();
    
    // SELECT * FROM users WHERE created_at BETWEEN ? AND ?
    List<User> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    // SELECT * FROM users WHERE city IN (?, ?, ?)
    List<User> findByCityIn(List<String> cities);
    
    // DELETE FROM users WHERE name = ?
    void deleteByName(String name);
    
    // SELECT COUNT(*) FROM users WHERE age > ?
    long countByAgeGreaterThan(int age);
    
    // SELECT EXISTS (SELECT 1 FROM users WHERE email = ?)
    boolean existsByEmail(String email);
}
```

**Keywords:**

| Keyword | Example | SQL |
|---------|---------|-----|
| And | findByNameAndEmail | WHERE name = ? AND email = ? |
| Or | findByNameOrEmail | WHERE name = ? OR email = ? |
| Between | findByAgeBetween | WHERE age BETWEEN ? AND ? |
| LessThan | findByAgeLessThan | WHERE age < ? |
| GreaterThan | findByAgeGreaterThan | WHERE age > ? |
| Like | findByNameLike | WHERE name LIKE ? |
| Containing | findByNameContaining | WHERE name LIKE %?% |
| In | findByCityIn | WHERE city IN (?) |
| OrderBy | findByAgeOrderByNameAsc | ORDER BY name ASC |

---

## 9. What is @Transactional annotation?

**Answer:**

`@Transactional` manages database transactions. If an exception occurs, changes are rolled back.

```java
@Service
public class BankService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Transactional
    public void transferMoney(Long fromId, Long toId, double amount) {
        Account from = accountRepository.findById(fromId);
        Account to = accountRepository.findById(toId);
        
        from.setBalance(from.getBalance() - amount);  // Step 1
        accountRepository.save(from);
        
        // If exception occurs here, Step 1 is rolled back
        if (to == null) {
            throw new RuntimeException("Account not found");
        }
        
        to.setBalance(to.getBalance() + amount);  // Step 2
        accountRepository.save(to);
        
        // Both steps succeed or both are rolled back
    }
}
```

**Without @Transactional:**
```java
// BAD - No transaction management
public void transferMoney(Long fromId, Long toId, double amount) {
    Account from = accountRepository.findById(fromId);
    from.setBalance(from.getBalance() - amount);
    accountRepository.save(from);  // Committed to database
    
    // Exception occurs - from account already updated!
    throw new RuntimeException("Error");
}
```

**Propagation:**
```java
@Transactional(propagation = Propagation.REQUIRED)  // Default - join existing or create new
@Transactional(propagation = Propagation.REQUIRES_NEW)  // Always create new transaction
@Transactional(propagation = Propagation.MANDATORY)  // Must be called within transaction
```

**Isolation:**
```java
@Transactional(isolation = Isolation.READ_COMMITTED)
@Transactional(isolation = Isolation.REPEATABLE_READ)
@Transactional(isolation = Isolation.SERIALIZABLE)
```

---

## 10. What is the difference between findById() and getById()?

**Answer:**

| findById() | getById() (deprecated) / getReferenceById() |
|-----------|------------------------------------------|
| Returns Optional<Entity> | Returns Entity (proxy) |
| Executes query immediately | Lazy loading (query on access) |
| Returns empty if not found | Throws EntityNotFoundException |

**findById():**
```java
Optional<User> userOpt = userRepository.findById(1L);
// Query executed immediately

if (userOpt.isPresent()) {
    User user = userOpt.get();
    System.out.println(user.getName());
} else {
    System.out.println("User not found");
}
```

**getReferenceById() (JPA 3.0+) / getOne() (deprecated):**
```java
User user = userRepository.getReferenceById(1L);
// No query executed yet - returns proxy

System.out.println(user.getName());  // Query executed NOW
```

**When to use:**
- **findById()**: Need to check if entity exists
- **getReferenceById()**: Setting relationships (don't need entity data)

```java
@Transactional
public void createOrder(Long userId, List<Long> productIds) {
    User user = userRepository.getReferenceById(userId);  // Proxy - no query
    
    Order order = new Order();
    order.setUser(user);  // Only need user ID for foreign key
    
    orderRepository.save(order);
    // Only 1 query executed (INSERT order)
}
```

---

## 11. What is Auditing in JPA?

**Answer:**

Auditing automatically tracks who created/modified an entity and when.

**Enable Auditing:**
```java
@SpringBootApplication
@EnableJpaAuditing
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

**Base Entity:**
```java
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
    
    @LastModifiedBy
    private String updatedBy;
}
```

**Entity:**
```java
@Entity
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String email;
    
    // createdAt, updatedAt, createdBy, updatedBy are inherited
}
```

**Provide User Information:**
```java
@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    
    @Override
    public Optional<String> getCurrentAuditor() {
        // Get current user from SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        
        return Optional.of(authentication.getName());
    }
}
```

---

## 12. What is Optimistic Locking vs Pessimistic Locking?

**Answer:**

**Optimistic Locking:**
- Assumes conflicts are rare
- Uses version number
- Throws exception if conflict occurs

```java
@Entity
public class Product {
    @Id
    private Long id;
    
    private String name;
    private Double price;
    
    @Version  // Optimistic locking
    private Long version;
}

// Transaction 1
Product product = productRepository.findById(1L);  // version = 1
product.setPrice(100.0);
productRepository.save(product);  // version = 2

// Transaction 2 (concurrent)
Product product = productRepository.findById(1L);  // version = 1
product.setPrice(150.0);
productRepository.save(product);  // OptimisticLockException (version mismatch)
```

**Pessimistic Locking:**
- Locks database row
- Other transactions wait

```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Product findByIdWithLock(@Param("id") Long id);
}

// Transaction 1
Product product = productRepository.findByIdWithLock(1L);  // Row locked
product.setPrice(100.0);
productRepository.save(product);  // Lock released

// Transaction 2 (waits for lock to be released)
Product product = productRepository.findByIdWithLock(1L);  // Waits...
```

**When to use:**
- **Optimistic**: High read, low write (e.g., blog posts)
- **Pessimistic**: High contention (e.g., inventory, bank accounts)

---

## 13. What is @Embedded and @Embeddable?

**Answer:**

`@Embeddable` creates reusable components that don't have their own table.

```java
@Embeddable
public class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;
}

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Embedded
    private Address address;  // Embedded in users table
}

// Database: users table
// Columns: id, name, street, city, state, zip_code
```

**Multiple Embeddable of Same Type:**
```java
@Entity
public class Company {
    @Id
    private Long id;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "billing_street")),
        @AttributeOverride(name = "city", column = @Column(name = "billing_city"))
    })
    private Address billingAddress;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "shipping_street")),
        @AttributeOverride(name = "city", column = @Column(name = "shipping_city"))
    })
    private Address shippingAddress;
}
```

---

## 14. What is @Query vs Derived Query Methods?

**Answer:**

**Derived Query Methods (Simple queries):**
```java
public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByEmail(String email);
    List<User> findByAgeGreaterThan(int age);
    
    // Pros: No SQL needed, easy to read
    // Cons: Limited to simple queries
}
```

**@Query (Complex queries):**
```java
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u WHERE u.age > :age AND u.city = :city")
    List<User> findByAgeAndCity(@Param("age") int age, @Param("city") String city);
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.orders WHERE u.id = :id")
    User findByIdWithOrders(@Param("id") Long id);
    
    // Pros: Complex queries, joins, aggregations
    // Cons: Requires JPQL knowledge
}
```

**When to use:**
- **Derived**: Simple queries (single table, basic conditions)
- **@Query**: Complex queries (joins, aggregations, subqueries)

---

## 15. How to handle Database Connection Pooling?

**Answer:**

Connection pooling reuses database connections for better performance.

**HikariCP (Default in Spring Boot):**

```properties
# application.properties

# Connection pool settings
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000

# Database settings
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=password
```

**Why Connection Pooling?**

**Without Pooling:**
```
Request 1 → Open Connection → Query → Close Connection
Request 2 → Open Connection → Query → Close Connection (Slow!)
```

**With Pooling:**
```
Request 1 → Get Connection from Pool → Query → Return to Pool
Request 2 → Get Connection from Pool → Query → Return to Pool (Fast!)
```

**Configuration Explanation:**
- `maximum-pool-size`: Max connections in pool
- `minimum-idle`: Min idle connections
- `connection-timeout`: Wait time for connection
- `idle-timeout`: Time before closing idle connection
- `max-lifetime`: Max connection lifetime
