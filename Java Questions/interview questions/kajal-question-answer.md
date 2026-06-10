# Kajal – Simple Interview Q&A (Plain Language)

**All answers in simple, straight language. One question = one clear answer (+ code where needed).**

---

## STREAM API

**What are intermediate and terminal operations in Stream API?**  
- **Intermediate** – Return a new stream; you can chain them (filter, map, sorted, distinct, limit). They don’t run until a terminal runs.  
- **Terminal** – Run the pipeline and give a result (collect, forEach, reduce, count, findFirst). After this, the stream is “consumed.”

**Write code using Stream API to find occurrences of a first name in a given string ArrayList.**  
```java
List<String> names = Arrays.asList("John Doe", "Jane Smith", "John Smith", "John Lee");
String firstName = "John";
long count = names.stream()
    .map(s -> s.split(" ")[0])   // get first name
    .filter(f -> f.equals(firstName))
    .count();
System.out.println(firstName + " appears " + count + " times");  // 3
```

**Make a list, find greatest element, cube it, filter if cube > 50, print that element.**  
```java
List<Integer> list = Arrays.asList(2, 5, 3, 8, 4);
int max = list.stream().max(Integer::compareTo).orElse(0);
int cube = max * max * max;
if (cube > 50) System.out.println(max);  // 8
// Or in one pipeline:
list.stream()
    .max(Integer::compareTo)
    .map(n -> n * n * n)
    .filter(c -> c > 50)
    .ifPresent(c -> System.out.println((int)Math.cbrt(c)));  // prints 8
```

**How do you handle null values in Stream API?**  
Filter them out: `list.stream().filter(Objects::nonNull)` or use Optional and `flatMap(Optional::stream)` for Optional elements.

**What is the difference between map() and flatMap()?**  
- **map()** – One input → one output (e.g. String → length).  
- **flatMap()** – One input → many outputs, then “flatten” into one stream (e.g. each sentence → stream of words → one stream of all words).

**Use cases of Stream.**  
Filter collections, transform (map), sort, remove duplicates, count, find min/max, group by, reduce (sum, concat), pagination (skip/limit).

**How does the collect() method in Stream work?**  
It’s a terminal operation that “collects” stream elements into a structure: List (toList()), Set (toSet()), Map (toMap()), or a custom collector (e.g. joining strings, groupingBy).

**Second/third highest element using Stream and traditional approach.**  
```java
// Stream – 2nd highest
List<Integer> list = Arrays.asList(10, 20, 30, 40);
Integer second = list.stream().sorted(Comparator.reverseOrder()).skip(1).findFirst().orElse(null);

// Traditional – sort then get index 1 (2nd) or 2 (3rd)
Collections.sort(list, Collections.reverseOrder());
int secondMax = list.get(1);
```

---

## LINKED LIST & BST

**Write code to create a traditional LinkedList and define add method.**  
```java
class Node {
    int data;
    Node next;
    Node(int data) { this.data = data; }
}

class MyLinkedList {
    Node head;
    void add(int data) {
        Node newNode = new Node(data);
        if (head == null) { head = newNode; return; }
        Node curr = head;
        while (curr.next != null) curr = curr.next;
        curr.next = newNode;
    }
}
```

**Write code for Binary Search Tree.**  
```java
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class BST {
    TreeNode root;
    void insert(int val) {
        root = insertRec(root, val);
    }
    TreeNode insertRec(TreeNode node, int val) {
        if (node == null) return new TreeNode(val);
        if (val < node.val) node.left = insertRec(node.left, val);
        else if (val > node.val) node.right = insertRec(node.right, val);
        return node;
    }
}
```

---

## FUNCTIONAL INTERFACE

**What is a functional interface? Write code to implement it.**  
An interface with exactly one abstract method. Lambdas and method references can be used where that type is expected.

```java
@FunctionalInterface
interface Calculator {
    int operate(int a, int b);
}

// Usage
Calculator add = (a, b) -> a + b;
Calculator sub = (a, b) -> a - b;
System.out.println(add.operate(5, 3));  // 8
```

**Can we have multiple methods in a functional interface?**  
Only one abstract method. You can have many default and static methods; it’s still a functional interface.

**Explain lambda expression.**  
A short way to write a small function: `(a, b) -> a + b`. Used where a functional interface is expected (e.g. Runnable, Comparator).

---

## SPRING BOOT – CONTROLLER & ENDPOINTS

**Define a get billing details method in the controller with endpoints.**  
```java
@RestController
@RequestMapping("/api/billing")
public class BillingController {

    @GetMapping("/details")
    public ResponseEntity<BillingDetails> getBillingDetails() {
        BillingDetails details = billingService.getBillingDetails();
        return ResponseEntity.ok(details);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<BillingDetails> getBillingDetailsById(@PathVariable Long id) {
        return ResponseEntity.ok(billingService.getBillingDetailsById(id));
    }
}
```
Endpoints: `GET /api/billing/details` and `GET /api/billing/details/{id}`.

---

## SPRING ACTUATORS

**What are Spring Actuators? How to enable them? What are its endpoints?**  
Actuators add production-ready endpoints to your app (health, metrics, info).

**Enable:** Add dependency and (if needed) expose in config:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
```

**Common endpoints:**  
- `/actuator/health` – Is app up?  
- `/actuator/info` – App info.  
- `/actuator/metrics` – Metrics (memory, HTTP, etc.).  
- `/actuator/env` – Environment properties.

---

## MICROSERVICES

**What are the endpoints in Microservices?**  
Each microservice exposes its own REST (or gRPC) endpoints. There’s no single “list”; e.g. User Service has `/users`, Order Service has `/orders`. API Gateway often exposes one entry URL and routes to these.

**What is Saga design pattern?**  
When one business flow spans multiple services and DBs, Saga keeps data consistent by either: (1) Choreography – each service does its part and publishes events, or (2) Orchestration – one coordinator calls each service and compensates (undo) if something fails.

**What is Circuit Breaker design pattern?**  
If a downstream service keeps failing, stop calling it for a while (circuit “open”), return a fallback. After some time, try again (half-open). Prevents one failing service from bringing down others.

**How can we communicate between two microservices?**  
- **Sync:** REST (HTTP), gRPC – caller waits for response.  
- **Async:** Message queues (Kafka, RabbitMQ) – send message and don’t wait.  
- API Gateway can route external calls to the right service.

**How do microservices differ from Monolithic?**  
Monolith = one big app and one DB. Microservices = many small apps, each with its own responsibility (and often DB); they talk over network. Microservices give independent deploy and scale but add complexity (network, consistency).

**How do you ensure communication between Microservices?**  
Use REST/gRPC for sync; Kafka/RabbitMQ for async. Use service discovery (e.g. Eureka) so services find each other by name. API Gateway as single entry point.

**What is API Gateway?**  
Single entry for clients. It routes requests to the right microservice, and can do auth, rate limiting, and load balancing.

**How do microservices communicate in your project?**  
(Answer with what you use: e.g. REST for sync, Kafka for events; Eureka for discovery; Zuul/Spring Cloud Gateway as gateway.)

**How to handle when API of one microservice is giving error?**  
Use Circuit Breaker (e.g. Resilience4j): stop calling failing service, return fallback. Use retries with backoff. Log and monitor; fix the failing service.

**Design patterns of Microservices.**  
API Gateway, Service Discovery, Circuit Breaker, Saga (distributed transactions), CQRS, Event Sourcing, BFF (Backend for Frontend).

**Synchronous vs asynchronous communication in Microservices?**  
- **Sync (REST, gRPC):** Call and wait for response. Simple; if one service is down, caller can fail.  
- **Async (Kafka, RabbitMQ):** Send message and continue. Loose coupling; need to handle eventual consistency and retries.

**How microservices authorize with JWT – how do you check if user is authorized?**  
Client sends JWT in header (e.g. `Authorization: Bearer <token>`). Each service (or API Gateway) validates the token (signature, expiry), then reads roles/claims from JWT and checks permissions (e.g. @PreAuthorize, or manual check). No need to call Auth Service on every request if token is valid.

---

## HASHMAP & CONCURRENT

**Difference between HashMap and ConcurrentHashMap?**  
- **HashMap** – Not thread-safe. Multiple threads can corrupt it.  
- **ConcurrentHashMap** – Thread-safe for concurrent reads and writes; uses locking at segment/bucket level so full map isn’t locked.

**Synchronized HashMap vs ConcurrentHashMap?**  
- **Collections.synchronizedMap(hashMap)** – Every method locks the whole map. Safe but less concurrent.  
- **ConcurrentHashMap** – Fine-grained locking (per bucket), better for high concurrency. Prefer ConcurrentHashMap when you need thread-safe map.

**Internal working of HashMap (and enhancement after Java 8)?**  
Stores key-value in buckets using `hashCode()`. Collisions: same bucket stores multiple entries. Before Java 8: linked list in bucket. After Java 8: if list size > 8, convert to tree (red-black) for better performance. Key point: hash → bucket index → list/tree in that bucket.

**Difference between HashMap, Hashtable, and ConcurrentHashMap?**  
- **HashMap** – No null key in recent Java; not thread-safe.  
- **Hashtable** – Thread-safe (every method synchronized); no null key/value.  
- **ConcurrentHashMap** – Thread-safe with better concurrency; no null key/value in most versions.

**HashMap vs HashSet?**  
- **HashMap** – Key-value pairs.  
- **HashSet** – Only unique elements (internally it’s a HashMap with value as constant). Use HashMap when you need key→value; HashSet when you need only unique set of elements.

**We have 2 maps; return a map with key of first map and value of second map.**  
(Assuming same keys in both, or you define rule for missing keys.)
```java
Map<K, V1> map1 = ...;
Map<K, V2> map2 = ...;
Map<K, V2> result = new HashMap<>();
for (K key : map1.keySet()) {
    if (map2.containsKey(key)) result.put(key, map2.get(key));
}
// Or with Stream: map1.keySet().stream().filter(map2::containsKey).collect(toMap(k->k, map2::get));
```

**Sort the map using values.**  
```java
map.entrySet().stream()
   .sorted(Map.Entry.comparingByValue())
   .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b)->a, LinkedHashMap::new));
```

---

## BEAN LIFECYCLE & ANNOTATIONS

**Bean lifecycle.**  
Spring creates bean → injects dependencies → (optional) @PostConstruct → bean ready → (optional) @PreDestroy → container shuts down. You can also use InitializingBean/DisposableBean or custom BeanPostProcessor for more control.

**Difference between @Component, @Repository, @Service.**  
All are stereotype annotations; Spring creates a bean for the class.  
- **@Component** – Generic.  
- **@Repository** – For DAO/DB layer; adds exception translation.  
- **@Service** – For business logic layer. Use them for clarity and layer-specific behavior.

**@Bean vs @Component?**  
- **@Component** – On class; Spring scans and creates one bean per class.  
- **@Bean** – On a method (usually in @Configuration); you create the object yourself and return it; use when you need custom creation (e.g. third-party class, conditional logic).

**@Autowired vs @Qualifier?**  
- **@Autowired** – “Inject a bean of this type.” If multiple beans exist, Spring may fail or pick one (e.g. @Primary).  
- **@Qualifier** – “Inject the bean with this name.” Use when you have multiple beans of same type: `@Qualifier("beanName")`.

**@Primary vs @Qualifier?**  
- **@Primary** – When multiple beans of same type exist, prefer this one by default.  
- **@Qualifier** – Explicitly choose which bean by name. Qualifier overrides Primary when both are used.

**What is stereotype annotation?**  
Annotations that mark a class as a Spring-managed component: @Component, @Service, @Repository, @Controller. They’re discovered by component scan.

**Difference between @Component and @Configuration?**  
- **@Component** – Just a bean.  
- **@Configuration** – Tells Spring that this class has @Bean methods and that method calls should be intercepted so “same bean” is returned (no new instance per call). Use @Configuration for Java-based config with @Bean methods.

**@SpringBootApplication vs @Configuration?**  
@SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan. So the main class is also a configuration; @Configuration alone is only “this class can define @Bean.”

---

## CONTROLLER & REST

**@RestController vs @Controller?**  
- **@Controller** – For web MVC; methods return view names (or ModelAndView).  
- **@RestController** – For REST APIs; = @Controller + @ResponseBody; methods return data (JSON/XML) directly.

**@GetMapping, @PostMapping, @RequestMapping?**  
- **@GetMapping** – GET request.  
- **@PostMapping** – POST.  
- **@RequestMapping** – Generic; you can set method = RequestMethod.GET etc. and path. Get/PostMapping are shortcuts for method + path.

**How to create a RESTful API using Spring Boot?**  
Create @RestController, use @GetMapping/@PostMapping etc. on methods, return DTOs/entities (JSON by default). Use @PathVariable, @RequestParam, @RequestBody. Optionally use service layer and JPA for DB.

**How do you handle exceptions in Spring Boot? (@ControllerAdvice)**  
Use @ControllerAdvice with @ExceptionHandler. One class handles exceptions for all controllers and returns consistent error response (e.g. JSON with message and status code).

**What are the different HTTP status codes?**  
200 OK, 201 Created, 204 No Content, 400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error.

---

## SPRING CORE & BOOT

**How does Spring Boot work internally?**  
Starts with main class annotated with @SpringBootApplication. Component scan finds your beans; auto-configuration reads classpath and application.properties/yml and creates default beans (e.g. DataSource, Tomcat). Embedded server starts and deploys your app.

**Why use Spring Boot?**  
Less config (no XML), embedded server, starters for quick setup, auto-configuration, production features (actuator). You focus on business code.

**How is JPA configured?**  
Add spring-boot-starter-data-jpa; set datasource in application.properties/yml (url, username, password, driver). Spring Boot auto-configures EntityManagerFactory and TransactionManager. Create @Entity classes and repositories extending JpaRepository.

**What are starters in Spring Boot?**  
Pre-packaged dependencies. One starter brings all jars for a feature (e.g. spring-boot-starter-web brings Spring MVC, Tomcat, Jackson). Reduces version and dependency management.

**How does Spring Boot auto-configuration work?**  
Spring Boot looks at classpath and existing beans. Conditional classes (e.g. @ConditionalOnClass) create beans only when certain classes are present. application.properties/yml override defaults.

**How to externalize configuration in Spring Boot?**  
Use application.properties or application.yml (in classpath or same folder as jar). Override with env variables or command-line args. Use @Value or @ConfigurationProperties to read. Use profiles (application-dev.yml) for different environments.

**What is Spring profile?**  
A way to have different config for dev, test, prod. Activate with `spring.profiles.active=dev`. You can have application-dev.yml, application-prod.yml and different beans with @Profile("dev").

**Explain IOC Container.**  
Container that creates and manages objects (beans) and injects dependencies. You don’t do `new` for beans; the container does. Control of object creation is “inverted” to the container.

**What are the different types of dependency injection in Spring?**  
Constructor (preferred), setter, field. Constructor: dependencies final and required; setter/field: optional or mutable.

**Spring Boot and JDK version?**  
Spring Boot 3.x needs JDK 17+. Spring Boot 2.x works with JDK 8+. Check docs for exact matrix.

**What is the difference between Spring and Spring Boot?**  
Spring = core framework (IoC, DI, AOP, etc.). Spring Boot = opinionated setup on top of Spring: auto-config, embedded server, starters, less config. Same Spring, easier start.

---

## JPA & DATABASE

**How does JPA differ from Hibernate?**  
JPA is a specification (API); Hibernate is an implementation of that spec. You code to JPA; Spring often uses Hibernate under the hood. So: JPA = contract, Hibernate = one implementation.

**Why do you use @Query annotation?**  
To write custom JPQL or native SQL when derived method names or default methods aren’t enough. E.g. complex conditions, joins, or native queries for DB-specific features.

**For searching from DB what do we have to use?**  
JPA: method names (findByName), @Query (JPQL/native), Criteria API, or Querydsl. In Spring Data: mainly repository methods and @Query.

**Delete, Drop, and Truncate difference?**  
- **DELETE** – Removes rows; can have WHERE; triggers fire; can rollback; keeps structure.  
- **TRUNCATE** – Removes all rows; no WHERE; usually faster; resets identity; table structure stays.  
- **DROP** – Removes table (and data). DDL.

**DDL vs DML?**  
- **DDL** – Define structure: CREATE, ALTER, DROP (tables, columns).  
- **DML** – Manipulate data: SELECT, INSERT, UPDATE, DELETE.

---

## SECURITY

**What is Spring Security?**  
Framework for authentication (who are you?) and authorization (what can you do?) in Spring apps. Handles login, sessions, roles, CSRF, etc.

**How do you implement authentication and authorization in Spring Boot?**  
Add spring-boot-starter-security. Configure SecurityFilterChain: which URLs are public, which need auth. Use UserDetailsService for loading user; use roles and @PreAuthorize or config for authorization.

**What is JWT and how does it work?**  
JSON Web Token: signed token containing claims (e.g. user id, roles). After login, server returns JWT. Client sends it in header. Server verifies signature and reads claims; no server-side session. Good for stateless APIs.

**How do you secure REST APIs in Spring Boot?**  
Use Spring Security: require authentication (e.g. JWT or session), authorize by role (@PreAuthorize), HTTPS, validate input, avoid exposing sensitive data.

**Spring Security annotations.**  
@PreAuthorize, @PostAuthorize (method-level access), @Secured (role-based). Use @PreAuthorize("hasRole('ADMIN')") to restrict by role.

---

## MULTITHREADING

**Callable vs Runnable?**  
- **Runnable** – run() returns void. Used with Thread or ExecutorService.  
- **Callable** – call() returns a value and can throw. Used with ExecutorService; you get Future to get result.

**invoke and invokeAll?**  
- **invokeAny(tasks)** – Run all, return result of first that completes (others may be cancelled).  
- **invokeAll(tasks)** – Run all, return list of Futures when all complete.

**One example of invoke.**  
```java
ExecutorService es = Executors.newFixedThreadPool(2);
List<Callable<String>> tasks = List.of(() -> "A", () -> "B", () -> "C");
String first = es.invokeAny(tasks);  // e.g. "A" or "B" or "C" – whoever finishes first
```

**Executor vs ExecutorService?**  
- **Executor** – Only execute(Runnable).  
- **ExecutorService** – Extends Executor; adds submit (returns Future), invokeAll, invokeAny, shutdown. Prefer ExecutorService for task execution.

**ThreadPool?**  
A pool of worker threads. You submit tasks; they’re queued and executed by pool threads. Reuses threads instead of creating new ones for each task. E.g. Executors.newFixedThreadPool(10).

**Which is the preferable way to create thread? ExecutorService.**  
Yes. Don’t create new Thread() for each task. Use ExecutorService (e.g. thread pool) so you control number of threads, get Futures, and manage lifecycle (shutdown).

**Can two threads call two different objects synchronously?**  
Yes. synchronized instance method locks on that object. Thread 1 on object A and Thread 2 on object B don’t block each other. They only block if both use the same object’s lock.

**Explain Notify.**  
Used with wait(). wait() releases lock and waits. Another thread calls notify() on same object to wake one waiting thread (or notifyAll() for all). Woken thread reacquires lock and continues. Used for producer-consumer style coordination.

**Diff between Volatile and Synchronized?**  
- **volatile** – Guarantees visibility (read/write from main memory). No compound atomicity (e.g. i++ still not safe).  
- **synchronized** – Locks; gives visibility + atomicity for block. Use volatile for single read/write flag; synchronized for multiple operations.

**Types of multi-threading?**  
Creating threads: extend Thread, implement Runnable, or use ExecutorService. Concurrency tools: synchronized, Lock, volatile, wait/notify, concurrent collections, ExecutorService, CompletableFuture.

---

## STRING & IMMUTABILITY

**String immutable with example.**  
Once created, value can’t change. Any “change” creates a new String.
```java
String s = "hello";
s.concat(" world");   // s still "hello"
String s2 = s.concat(" world");  // s2 = "hello world"
```

**String replace.**  
`str.replace("old","new")` – returns new string with replacement; original unchanged. replaceAll/replaceFirst use regex.

**Find duplicates from the string.**  
```java
Map<Character, Long> freq = str.chars().mapToObj(c -> (char)c)
    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
freq.entrySet().stream().filter(e -> e.getValue() > 1).forEach(...);
```

**StringBuffer vs StringBuilder?**  
Both mutable. StringBuilder not thread-safe, faster. StringBuffer thread-safe (synchronized). Use StringBuilder unless multiple threads share the same buffer.

---

## JAVA BASICS

**Array vs List?**  
- **Array** – Fixed size; primitive or object; built-in.  
- **List** – Interface (ArrayList, LinkedList); dynamic size; only objects; rich API (add, remove, stream).

**Interface vs Abstract class?**  
- **Interface** – Contract (what); multiple inheritance; from Java 8 can have default/static methods.  
- **Abstract class** – Can have state, constructors, and partial implementation; single inheritance. Use interface for “can do”; abstract for “is a” with shared code.

**Final vs Static?**  
- **final** – Variable: can’t reassign. Method: can’t override. Class: can’t extend.  
- **static** – Belongs to class, not instance. One copy shared.

**Checked vs Unchecked exceptions?**  
- **Checked** – Must declare (throws) or handle (try-catch). E.g. IOException.  
- **Unchecked** – RuntimeException and subclasses; no must-handle. E.g. NPE, IllegalArgumentException.

**throw vs throws?**  
- **throw** – You throw an exception inside method: `throw new Exception();`  
- **throws** – Declares that method may throw that exception: `void m() throws IOException`.

**Try-catch and finally?**  
try = risky code. catch = handle exception. finally = runs always (cleanup). finally runs even if you return in try/catch (except System.exit).

**Finally vs Finalize?**  
- **finally** – Block that always runs after try/catch.  
- **finalize()** – Old method on Object called by GC before collection. Deprecated; don’t rely on it.

**Custom exception?**  
Extend Exception (checked) or RuntimeException (unchecked). Add constructor and use throw new MyException("msg"). Use when you want a specific, meaningful exception type.

**ArrayList vs LinkedList?**  
ArrayList: array-based; fast get by index; slower add/remove in middle. LinkedList: node-based; fast add/remove at ends; slower get by index. Use ArrayList by default; LinkedList when you do lots of insert/delete at start/end.

---

## OOPS & SOLID

**OOP concepts and their disadvantages?**  
- **Encapsulation** – Hide data. Disadvantage: more boilerplate (getters/setters).  
- **Inheritance** – Reuse. Disadvantage: tight coupling; fragile base class.  
- **Polymorphism** – Same interface, different behavior. Disadvantage: can be harder to trace.  
- **Abstraction** – Hide details. Disadvantage: extra layers.

**Explain method overloading vs overriding.**  
- **Overloading** – Same method name, different parameters (same class or subclass). Compile-time.  
- **Overriding** – Subclass redefines method from parent (same signature). Runtime polymorphism.

**Abstraction vs encapsulation?**  
- **Abstraction** – Hide complexity; show only relevant behavior (e.g. interface).  
- **Encapsulation** – Hide data; expose through methods (private fields, public getters/setters).

**What are SOLID principles?**  
S – Single Responsibility (one reason to change). O – Open/Closed (open for extension, closed for modification). L – Liskov Substitution (subtype replaceable). I – Interface Segregation (small interfaces). D – Dependency Inversion (depend on abstractions).

**What is immutable class?**  
Class whose instances can’t be changed after creation: final class, final fields, no setters, copy defensive in getters if you return mutable objects. Example: String, Integer.

---

## JAVA 8 & OPTIONAL

**What are main Java 8 features?**  
Lambdas, Stream API, Optional, default/static in interfaces, new Date/Time API (java.time), CompletableFuture, method references.

**What is Optional class?**  
A container that may or may not hold a value. Avoids NPE; use orElse(), orElseGet(), ifPresent(), map(). Don’t use as field or method parameter everywhere; mainly for return type.

---

## DESIGN PATTERNS

**Singleton pattern?**  
Only one instance of class. Constructor private; static getInstance() returns same instance. Thread-safe variant: double-check locking or static holder.

**Singleton vs Prototype pattern (Spring)?**  
- **Singleton** – One bean per container (default).  
- **Prototype** – New instance every time bean is requested.

**Design pattern used in project?**  
(Answer with what you used: e.g. Singleton for config, Factory for creating types, Builder for DTOs, Repository for data access, Strategy for algorithms, etc.)

---

## CACHING, KAFKA, PERFORMANCE

**Caching?**  
Store frequently used data in fast storage (memory, Redis) so you don’t hit DB every time. Reduces latency and load. Use @Cacheable in Spring; set TTL and eviction policy.

**Kafka utility?**  
Message broker for high-throughput, distributed event streaming. Decouples producers and consumers; replay; scale by partitions and consumer groups. Use for events, logs, async processing.

**How to reduce API response time?**  
Cache (Redis, in-memory), async where possible, DB indexes, pagination, avoid N+1, connection pooling, CDN for static content, optimize queries and DTOs (return only needed fields).

---

## JUNIT & TESTING

**JUnit?**  
Framework for unit testing in Java. You write test methods (often @Test), assert results (assertEquals, assertTrue, etc.), and run them. Used with Mockito to mock dependencies.

---

## CODING (SHORT)

**Print list of numbers where 2 comes (e.g. 2, 12, 20, 21…).**  
```java
list.stream().filter(n -> String.valueOf(n).contains("2")).forEach(System.out::println);
```

**Filter employees with even Employee IDs and double salaries.**  
```java
list.stream()
    .filter(e -> e.getId() % 2 == 0)
    .peek(e -> e.setSalary(e.getSalary() * 2))
    .collect(Collectors.toList());
```

**Find subset of integers that add up to target sum.**  
Backtracking or DP. Try include/exclude each number; when current sum == target, add to result. Classic subset-sum.

**Frequency of character using Java 8.**  
```java
str.chars().mapToObj(c -> (char)c)
   .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
```

**Non-repeating character using Java 8.**  
Find first character whose frequency is 1 (e.g. from frequency map above, or stream and filter count == 1).

**Check if string is palindrome.**  
```java
String rev = new StringBuilder(str).reverse().toString();
return str.equals(rev);
```

**Bubble sort implementation.**  
Compare adjacent elements; swap if wrong order. Repeat for n passes. O(n²).

---

## AWS & CLOUD

**AWS?**  
Amazon Web Services – cloud provider (compute, storage, DB, etc.). Common: EC2, S3, Lambda, RDS, API Gateway, CloudWatch.

**What is CloudWatch?**  
AWS service for monitoring: metrics, logs, alarms. You can track CPU, custom metrics, log groups, and set alerts.

**Features of Lambda and drawbacks?**  
- **Features:** Serverless, pay per use, auto-scale, no server management.  
- **Drawbacks:** Cold start, time limit, limited runtime and memory; not for long-running or stateful workloads.

---

## MISC

**What is Maven? Why is it used?**  
Build and dependency management tool. You declare dependencies in pom.xml; Maven downloads them and builds the project (compile, test, package). Standardizes project structure and lifecycle.

**Agile methodology?**  
Iterative development in small increments (sprints). Requirements and solutions evolve; daily standups, retrospectives; focus on working software and customer collaboration. Opposite of one big “waterfall” release.

**Scenario-based questions?**  
(Interviewer gives a situation; answer with: what you’d do, which tech you’d use, trade-offs. Use the concepts above: REST, DB, cache, security, microservices, etc.)

**Which last Java version you worked on?**  
(Your answer: e.g. Java 17 or 21.)

---

## JVM & MEMORY

**Explain JVM architecture.**  
JVM loads .class files, puts them in memory areas (class area, heap, stack, PC register, native method stack), executes bytecode via interpreter/JIT, and runs GC on the heap. You write Java; JVM runs it on any OS.

**What are the different memory areas in the JVM?**  
- **Heap** – Object instances; shared by threads.  
- **Stack** – Per-thread; local variables, method frames.  
- **Method area (Metaspace)** – Class metadata, static data.  
- **PC Register** – Current instruction per thread.  
- **Native method stack** – For native code.

**How does garbage collection work in Java?**  
GC finds objects that are no longer reachable (no references) and frees their memory. Different algorithms (e.g. G1, ZGC) do marking/sweep or copying. You don’t free manually; JVM does it.

---

## EXTRA CODING

**Two sorted lists – merge into one list; output list not sorted.**  
Just concatenate and don’t sort: `list1.addAll(list2)`. If they want merged and still sorted, use two pointers or merge sort merge step.

**What is Spring Cloud?**  
Set of tools for building microservices on Spring: service discovery (Eureka), config server, API Gateway, circuit breaker (Resilience4j), load balancing. Works with Spring Boot.

---

**Tip:** Keep answers to 2–3 sentences; add one line of code or comparison only when it helps. For “how in your project,” always tie to what you actually used.
