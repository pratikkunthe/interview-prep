# Database and JPA/Hibernate

## JPA and Hibernate

### 1. How jpa is configured?

**Answer:**

**JPA Configuration** = Setting up JPA in Spring Boot

**Step 1: Add Dependencies**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>

<!-- Or for MySQL -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

**Step 2: Configure Database**

```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true
```

**Step 3: Create Entity**

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "email", unique = true)
    private String email;
    
    // Getters and setters
}
```

**Step 4: Create Repository**

```java
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
    Optional<User> findByEmail(String email);
}
```

**Step 5: Use Repository**

```java
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    public User findUser(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }
}
```

**Complete Configuration:**

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

// Entity
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
}

// Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

// Service
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
```

**Key Configuration Properties:**

| Property | Purpose |
|----------|---------|
| spring.datasource.url | Database URL |
| spring.datasource.username | Database username |
| spring.datasource.password | Database password |
| spring.jpa.hibernate.ddl-auto | Schema generation (create, update, validate, none) |
| spring.jpa.show-sql | Show SQL queries |

**Simple rule:** Add dependency → Configure datasource → Create entities → Use repositories!

---

### 2. How does JPA differ from Hibernate

**Answer:**

**JPA** = Specification (interface/standard)
**Hibernate** = Implementation (actual code)

**Think of it like:**
- **JPA** = Recipe (what to do)
- **Hibernate** = Actual cooking (how to do it)

**JPA (Java Persistence API):**
- Specification/standard
- Defines how to interact with database
- Interface only, no implementation
- Part of Java EE

**Hibernate:**
- Implementation of JPA
- Actual code that does the work
- Most popular JPA implementation
- Can be used standalone (without JPA)

**Relationship:**

```
JPA (Specification)
    ↓
Hibernate (Implementation)
```

**Example:**

```java
// Using JPA annotations (specification)
@Entity  // JPA annotation
@Table(name = "users")  // JPA annotation
public class User {
    @Id  // JPA annotation
    @GeneratedValue  // JPA annotation
    private Long id;
}

// Hibernate implements JPA
// When you use JPA, Hibernate is doing the work under the hood
```

**Key Differences:**

| Aspect | JPA | Hibernate |
|--------|-----|-----------|
| Type | Specification | Implementation |
| Purpose | Define standard | Implement standard |
| Annotations | @Entity, @Id, etc. | Same + more |
| Can use alone | ❌ No | ✅ Yes |

**Using Hibernate without JPA:**

```java
// Pure Hibernate (old way, before JPA)
Session session = sessionFactory.openSession();
Transaction tx = session.beginTransaction();
User user = new User();
session.save(user);
tx.commit();
session.close();
```

**Using JPA (with Hibernate as implementation):**

```java
// JPA way (Hibernate implements it)
@Autowired
private EntityManager entityManager;

public void save(User user) {
    entityManager.persist(user);
}

// Or using Spring Data JPA
@Autowired
private UserRepository userRepository;

public void save(User user) {
    userRepository.save(user);
}
```

**In Spring Boot:**

```xml
<!-- This includes Hibernate as JPA implementation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

**Key Points:**
- JPA = What to do (specification)
- Hibernate = How to do it (implementation)
- Spring Boot uses Hibernate as JPA implementation by default
- You use JPA annotations, Hibernate executes them

**Simple rule:** JPA = Rules, Hibernate = Player following the rules. You use JPA, Hibernate does the work!

---

### 3. Why you are using @Query annotation

**Answer:**

**@Query** = Write custom SQL/JPQL queries

**Why use it:**
- Complex queries not possible with method names
- Need custom logic
- Performance optimization
- Native SQL queries

**Example:**

```java
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Method name query (simple)
    List<User> findByName(String name);
    
    // Custom JPQL query
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);
    
    // Custom query with multiple conditions
    @Query("SELECT u FROM User u WHERE u.name LIKE %?1% AND u.active = true")
    List<User> findActiveUsersByName(String name);
    
    // Native SQL query
    @Query(value = "SELECT * FROM users WHERE age > ?1", nativeQuery = true)
    List<User> findUsersOlderThan(int age);
    
    // Query with parameters
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.active = :active")
    Optional<User> findByEmailAndActive(@Param("email") String email, 
                                       @Param("active") boolean active);
    
    // Update query
    @Modifying
    @Query("UPDATE User u SET u.active = false WHERE u.lastLoginDate < :date")
    void deactivateInactiveUsers(@Param("date") LocalDate date);
    
    // Delete query
    @Modifying
    @Query("DELETE FROM User u WHERE u.active = false")
    void deleteInactiveUsers();
}
```

**When to use @Query:**

**1. Complex Queries**
```java
@Query("SELECT u FROM User u JOIN u.orders o WHERE o.total > :amount")
List<User> findUsersWithOrdersAbove(@Param("amount") BigDecimal amount);
```

**2. Performance Optimization**
```java
@Query("SELECT u.id, u.name FROM User u")  // Select only needed fields
List<Object[]> findUserIdsAndNames();
```

**3. Native SQL**
```java
@Query(value = "SELECT * FROM users WHERE DATE(created_date) = CURRENT_DATE", 
       nativeQuery = true)
List<User> findUsersCreatedToday();
```

**4. Aggregations**
```java
@Query("SELECT COUNT(u) FROM User u WHERE u.active = true")
Long countActiveUsers();

@Query("SELECT AVG(u.age) FROM User u")
Double getAverageAge();
```

**5. Joins**
```java
@Query("SELECT u FROM User u JOIN FETCH u.orders WHERE u.id = :id")
Optional<User> findUserWithOrders(@Param("id") Long id);
```

**Key Points:**
- Use for complex queries
- JPQL (Java Persistence Query Language) or Native SQL
- Better control over query
- Performance optimization

**Simple rule:** Use @Query when method names aren't enough or you need custom SQL!

---

### 4. For searching from DB what we have to use

**Answer:**

Ways to search from database in Spring Data JPA:

**1. Method Name Queries** (Simple searches)

```java
public interface UserRepository extends JpaRepository<User, Long> {
    // Find by exact match
    User findByName(String name);
    
    // Find by multiple fields
    User findByNameAndEmail(String name, String email);
    
    // Find with LIKE
    List<User> findByNameContaining(String name);
    List<User> findByNameLike(String pattern);
    
    // Find with comparison
    List<User> findByAgeGreaterThan(int age);
    List<User> findByAgeBetween(int min, int max);
    
    // Find with sorting
    List<User> findByNameOrderByAgeDesc(String name);
    
    // Find with limit
    List<User> findTop10ByOrderByCreatedDateDesc();
}
```

**2. @Query Annotation** (Custom queries)

```java
@Query("SELECT u FROM User u WHERE u.name LIKE %:name%")
List<User> searchUsers(@Param("name") String name);

@Query(value = "SELECT * FROM users WHERE name LIKE %?1%", nativeQuery = true)
List<User> searchUsersNative(String name);
```

**3. Specification** (Dynamic queries)

```java
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}

// Usage
Specification<User> spec = (root, query, cb) -> {
    return cb.like(root.get("name"), "%" + searchTerm + "%");
};
List<User> users = userRepository.findAll(spec);
```

**4. Example Matcher** (Simple dynamic queries)

```java
public List<User> searchUsers(String name, String email) {
    ExampleMatcher matcher = ExampleMatcher.matching()
        .withMatcher("name", contains().ignoreCase())
        .withMatcher("email", contains().ignoreCase());
    
    User probe = new User();
    probe.setName(name);
    probe.setEmail(email);
    
    Example<User> example = Example.of(probe, matcher);
    return userRepository.findAll(example);
}
```

**Complete Example:**

```java
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // Simple search
    public List<User> searchByName(String name) {
        return userRepository.findByNameContaining(name);
    }
    
    // Custom query search
    public List<User> advancedSearch(String name, String email) {
        return userRepository.findByNameContainingAndEmailContaining(name, email);
    }
    
    // Native SQL search
    public List<User> searchNative(String term) {
        return userRepository.searchUsersNative(term);
    }
    
    // Dynamic search
    public List<User> dynamicSearch(UserSearchCriteria criteria) {
        Specification<User> spec = Specification.where(null);
        
        if(criteria.getName() != null) {
            spec = spec.and((root, query, cb) -> 
                cb.like(root.get("name"), "%" + criteria.getName() + "%"));
        }
        
        if(criteria.getEmail() != null) {
            spec = spec.and((root, query, cb) -> 
                cb.like(root.get("email"), "%" + criteria.getEmail() + "%"));
        }
        
        return userRepository.findAll(spec);
    }
}
```

**Key Methods for Searching:**

| Method Pattern | SQL Equivalent |
|---------------|----------------|
| `findByName` | `WHERE name = ?` |
| `findByNameContaining` | `WHERE name LIKE %?%` |
| `findByNameLike` | `WHERE name LIKE ?` |
| `findByAgeGreaterThan` | `WHERE age > ?` |
| `findByAgeBetween` | `WHERE age BETWEEN ? AND ?` |
| `findByNameAndEmail` | `WHERE name = ? AND email = ?` |
| `findByNameOrEmail` | `WHERE name = ? OR email = ?` |

**Simple rule:** Use method names for simple searches, @Query for complex searches, Specification for dynamic searches!

---

## SQL Queries

### 1. Delete, Drop and Truncate Diff

**Answer:**

Three ways to remove data/table in SQL:

**DELETE** = Remove rows from table (keeps table structure)

```sql
-- Delete specific rows
DELETE FROM users WHERE age < 18;

-- Delete all rows (table structure remains)
DELETE FROM users;

-- Can rollback
BEGIN TRANSACTION;
DELETE FROM users WHERE id = 1;
ROLLBACK;  -- Undo delete
```

**TRUNCATE** = Remove all rows quickly (keeps table structure)

```sql
-- Remove all rows (faster than DELETE)
TRUNCATE TABLE users;

-- Cannot use WHERE clause
-- Cannot rollback (in most databases)
-- Resets auto-increment
```

**DROP** = Remove entire table (structure and data)

```sql
-- Remove table completely
DROP TABLE users;

-- Table is gone forever
-- Cannot rollback
```

**Comparison:**

| Operation | What it does | Can rollback | Speed | Use When |
|-----------|-------------|--------------|-------|----------|
| DELETE | Removes rows | ✅ Yes | Slow | Remove specific rows |
| TRUNCATE | Removes all rows | ❌ No | Fast | Remove all data quickly |
| DROP | Removes table | ❌ No | Fast | Remove table completely |

**Examples:**

```sql
-- DELETE - Remove specific users
DELETE FROM users WHERE status = 'inactive';
-- Table structure remains, only rows deleted

-- TRUNCATE - Remove all users
TRUNCATE TABLE users;
-- Table structure remains, all rows deleted, faster

-- DROP - Remove users table
DROP TABLE users;
-- Table completely removed
```

**In JPA:**

```java
// DELETE
@Modifying
@Query("DELETE FROM User u WHERE u.active = false")
void deleteInactiveUsers();

// Or
userRepository.deleteById(id);

// TRUNCATE (native query)
@Modifying
@Query(value = "TRUNCATE TABLE users", nativeQuery = true)
void truncateUsers();

// DROP (rarely used in application code)
@Modifying
@Query(value = "DROP TABLE users", nativeQuery = true)
void dropUsersTable();
```

**Simple rule:** 
- DELETE = Remove rows (can undo)
- TRUNCATE = Remove all rows fast (can't undo)
- DROP = Remove table completely (can't undo)

---

### 2. DDL and DML diff

**Answer:**

**DDL (Data Definition Language)** = Define database structure
**DML (Data Manipulation Language)** = Manipulate data

**DDL Commands:**

```sql
-- Create table
CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100)
);

-- Alter table
ALTER TABLE users ADD COLUMN email VARCHAR(100);

-- Drop table
DROP TABLE users;

-- Create index
CREATE INDEX idx_name ON users(name);
```

**DML Commands:**

```sql
-- Insert data
INSERT INTO users (id, name) VALUES (1, 'John');

-- Update data
UPDATE users SET name = 'Jane' WHERE id = 1;

-- Delete data
DELETE FROM users WHERE id = 1;

-- Select data
SELECT * FROM users;
```

**Comparison:**

| Aspect | DDL | DML |
|--------|-----|-----|
| Purpose | Define structure | Manipulate data |
| Commands | CREATE, ALTER, DROP | INSERT, UPDATE, DELETE, SELECT |
| Auto-commit | Usually auto-commits | Can rollback |
| Use | Schema changes | Data operations |

**DDL Examples:**

```sql
CREATE TABLE orders (
    id INT PRIMARY KEY,
    user_id INT,
    total DECIMAL(10,2)
);

ALTER TABLE orders ADD COLUMN status VARCHAR(20);

DROP TABLE orders;

CREATE INDEX idx_user ON orders(user_id);
```

**DML Examples:**

```sql
INSERT INTO orders (id, user_id, total) VALUES (1, 100, 99.99);

UPDATE orders SET total = 149.99 WHERE id = 1;

DELETE FROM orders WHERE id = 1;

SELECT * FROM orders WHERE user_id = 100;
```

**In JPA:**

**DDL:**
```java
@Entity
@Table(name = "users")  // DDL - defines table
public class User {
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "name")  // DDL - defines column
    private String name;
}
```

**DML:**
```java
// DML operations
userRepository.save(user);  // INSERT
userRepository.save(updatedUser);  // UPDATE
userRepository.deleteById(id);  // DELETE
userRepository.findAll();  // SELECT
```

**Simple rule:** 
- DDL = Structure (CREATE, ALTER, DROP)
- DML = Data (INSERT, UPDATE, DELETE, SELECT)
