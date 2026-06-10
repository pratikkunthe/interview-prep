# Simple Interview Answers (Plain Language)

**Use this for quick revision. One question = one straight answer. No jargon.**

---

## JAVA BASICS

**What is Java?**  
A programming language. You write code once, it runs on any machine (Windows, Mac, Linux) because it runs inside something called JVM.

**JDK vs JRE vs JVM?**  
- **JVM** = Runs your Java code (the engine).  
- **JRE** = JVM + libraries needed to *run* Java apps.  
- **JDK** = JRE + tools to *write* Java (compiler, etc.). So: to code → use JDK; to only run → JRE is enough.

**== vs equals()?**  
- **==** checks if two things are the *same object* (same memory).  
- **equals()** checks if two things have the *same value*. For Strings, always use equals() to compare text.

**What is OOP? (4 pillars)**  
1. **Encapsulation** – Hide data inside the class, expose only through methods.  
2. **Inheritance** – Child class gets stuff from parent (code reuse).  
3. **Polymorphism** – Same method name, different behavior (e.g. Dog barks, Cat meows).  
4. **Abstraction** – Show only what’s needed, hide how it’s done.

**Abstract class vs Interface?**  
- **Abstract class** – Can have normal methods + abstract methods; one class can extend only one abstract class.  
- **Interface** – Mainly “contract” (what to do); a class can implement many interfaces. Use abstract when things are closely related; use interface when you want “can do this” behavior.

**What is an exception?**  
Something that goes wrong while the program is running (e.g. divide by zero, file not found). Without handling it, the program crashes.

**How do you handle exceptions?**  
Put risky code in **try**, handle the problem in **catch**, and use **finally** for cleanup (e.g. closing files). You can also use **throws** to say “this method might throw this exception.”

**Error vs Exception?**  
- **Exception** – Can be caught and handled (e.g. NullPointerException).  
- **Error** – Serious, usually not meant to be caught (e.g. OutOfMemoryError).

**String vs StringBuilder vs StringBuffer?**  
- **String** – Immutable. Every change creates a new String.  
- **StringBuilder** – Mutable, fast, not thread-safe. Use when you change the text a lot in one thread.  
- **StringBuffer** – Like StringBuilder but thread-safe. Use when many threads change the same text.

**What is Garbage Collection?**  
JVM automatically frees memory by removing objects that are no longer used. You don’t delete them manually.

---

## COLLECTIONS

**ArrayList vs LinkedList?**  
- **ArrayList** – Like a resizable array. Fast to get by index, slower to add/remove in the middle.  
- **LinkedList** – Like a chain of nodes. Fast to add/remove at start/end, slower to get by index. Use ArrayList when you read a lot; LinkedList when you add/remove at ends a lot.

**HashSet vs TreeSet vs LinkedHashSet?**  
- **HashSet** – No order, no duplicates, fast.  
- **LinkedHashSet** – No duplicates, keeps *insertion* order.  
- **TreeSet** – No duplicates, *sorted* order. Use HashSet for “unique only”; TreeSet when you need sorting.

**HashMap vs Hashtable?**  
- **HashMap** – Not thread-safe, allows one null key.  
- **Hashtable** – Thread-safe, no null key. Prefer HashMap; for thread-safe use ConcurrentHashMap.

**How does HashMap work (in simple words)?**  
It stores key-value pairs. For a key it computes a “hash,” uses that to find a bucket, and stores the value there. If two keys land in same bucket (collision), it uses a list (or tree in Java 8+) there.

**Fail-fast vs Fail-safe?**  
- **Fail-fast** – If you change the collection while iterating (e.g. with for-loop), it throws exception immediately.  
- **Fail-safe** – Iterates on a copy, so it doesn’t throw even if the original changes (e.g. ConcurrentHashMap).

---

## JAVA 8

**What is a Lambda?**  
A short way to write a small function without writing a full method. Example: `(a, b) -> a + b` instead of a method that adds two numbers.

**What is Stream API?**  
A way to process a list of items in a pipeline: filter, map, sort, etc., without writing loops. Example: get all even numbers, double them, then sum.

**What is Optional?**  
A box that may or may not have a value. Used to avoid NullPointerException. You check with `isPresent()` or use `orElse()` to give a default.

**What are Functional Interfaces?**  
An interface with exactly one abstract method (e.g. Runnable, Comparator). Lambdas are used where a functional interface is expected.

---

## SPRING FRAMEWORK

**What is Spring?**  
A framework that helps you build Java applications by managing objects and their dependencies for you.

**What is Dependency Injection (DI)?**  
Instead of your class creating its dependencies (e.g. `new UserRepository()`), someone from outside (Spring) gives them to you. So your class “depends on” something that is “injected” in. Makes code loose and easy to test.

**What is IoC (Inversion of Control)?**  
Normally you create objects. In Spring, the *container* creates and manages objects. Control is “inverted” from you to the container.

**@Component vs @Service vs @Repository?**  
All make the class a “Spring bean.”  
- **@Component** – Generic.  
- **@Service** – Use for business logic layer.  
- **@Repository** – Use for database access layer. Service and Repository are more specific and add meaning (and @Repository adds some DB exception translation).

**@Autowired?**  
Tells Spring: “Inject the right bean here.” Spring finds a matching bean and puts it in that field or constructor.

**What is Spring AOP?**  
Aspect-Oriented Programming. You write “cross-cutting” stuff (logging, security, transactions) in one place (aspect) and apply it to many methods without repeating code.

**@Transactional?**  
Makes the method run inside a database transaction. If the method throws, the changes are rolled back; otherwise they are committed.

---

## SPRING BOOT

**What is Spring Boot?**  
Spring with defaults. It auto-configures things (DB, server, etc.) so you can start a runnable app with minimal config. No (or very little) XML.

**What does @SpringBootApplication do?**  
Three things in one: (1) This class can define beans (@Configuration), (2) Enable auto-configuration, (3) Scan for components in this package and below.

**What are Starters?**  
Pre-packaged dependencies. One starter brings everything you need for a feature. Example: `spring-boot-starter-web` brings web + Tomcat + JSON, etc.

**Auto-configuration?**  
Spring Boot looks at your classpath and configures beans automatically (e.g. if H2 is on classpath, it sets up a DB). You can override with your own config or properties.

**@Controller vs @RestController?**  
- **@Controller** – For web pages; methods return view names.  
- **@RestController** – For REST APIs; methods return data (JSON/XML) and it’s like @Controller + @ResponseBody.

**application.properties vs application.yml?**  
Same purpose: configuration. properties is key=value; yml is indentation-based and shorter for nested config. Use whichever you prefer.

**What is Actuator?**  
Adds endpoints to your app for health check, metrics, info, etc. So you can monitor your app (e.g. /actuator/health).

---

## REST API

**What is REST?**  
A style of building APIs using HTTP. You use URLs for resources and HTTP methods (GET, POST, PUT, DELETE) for actions. Data is often JSON.

**GET vs POST?**  
- **GET** – Get data; no body; can be cached; use for read.  
- **POST** – Send data (e.g. create); has body; use for create (and often for actions that change state when no other method fits).

**PUT vs PATCH?**  
- **PUT** – Replace the whole resource.  
- **PATCH** – Update only some fields. Use PUT for full update, PATCH for partial update.

**HTTP status codes (common)?**  
- 200 OK – Success.  
- 201 Created – Resource created.  
- 400 Bad Request – Invalid input.  
- 401 Unauthorized – Not logged in.  
- 403 Forbidden – Logged in but not allowed.  
- 404 Not Found – Resource not found.  
- 500 Internal Server Error – Server error.

---

## DATABASE & JPA

**What is JPA?**  
A standard (specification) for mapping Java objects to database tables. You work with objects; JPA (and Hibernate) talks to the database.

**What is an Entity?**  
A Java class that maps to a database table. You mark it with @Entity. Each object is a row.

**OneToOne, OneToMany, ManyToMany?**  
- **OneToOne** – One record in A to one in B (e.g. User – Profile).  
- **OneToMany** – One in A to many in B (e.g. Order – OrderItems).  
- **ManyToMany** – Many in A to many in B (e.g. Student – Course). Use join table for ManyToMany.

**LAZY vs EAGER loading?**  
- **EAGER** – When you load the entity, related entities are loaded too (can be slow and cause extra queries).  
- **LAZY** – Related entities are loaded only when you use them. Usually prefer LAZY and fetch when needed to avoid N+1.

**What is N+1 problem?**  
You load a list of entities (1 query), then for each one you load a relation (N extra queries). So 1 + N queries. Fix: use JOIN FETCH or EntityGraph so that related data is loaded in one (or fewer) queries.

**@Transactional?**  
Runs the method in a transaction. All DB operations in that method succeed together or roll back together on exception.

---

## SPRING SECURITY

**Authentication vs Authorization?**  
- **Authentication** – “Who are you?” (login: username/password, token).  
- **Authorization** – “What are you allowed to do?” (roles, permissions). First we authenticate, then we check authorization.

**What is JWT?**  
A token (string) that carries user info (e.g. user id, roles). Server signs it. Client sends it in the request (e.g. header). Server verifies and knows who the user is without storing session. Good for stateless APIs.

**What is OAuth2?**  
A way to let users log in using another provider (Google, GitHub, etc.) without giving your app their password. You get a token from the provider and use it to access their APIs.

---

## MULTITHREADING

**What is a Thread?**  
A separate path of execution inside the same program. Multiple threads can run at the same time (concurrently).

**How to create a thread?**  
Implement Runnable (or use lambda) and pass to Thread, or extend Thread. Prefer Runnable. Then call start(), not run() directly (start() starts new thread; run() runs in same thread).

**What is Synchronization?**  
Making sure only one thread at a time can run a piece of code (e.g. a method). Prevents race conditions when many threads touch the same data.

**What is Deadlock?**  
Two (or more) threads waiting for each other to release a lock, so none of them can proceed. Avoid by locking in same order everywhere or using timeouts.

**wait() vs sleep()?**  
- **sleep()** – Thread sleeps for some time; does not release lock.  
- **wait()** – Thread waits until notify(); it *releases* the lock. Used for coordination between threads.

---

## MICROSERVICES

**What are Microservices?**  
Instead of one big application, you build many small services. Each service does one thing, has its own database (often), and they talk over HTTP or messaging. Easier to scale and deploy independently.

**What is API Gateway?**  
Single entry point for clients. It routes requests to the right microservice, can do auth, rate limiting, and load balancing.

**What is Service Discovery (e.g. Eureka)?**  
Services register themselves (name + address). Other services find “where is User Service?” by name instead of hardcoding URLs.

**What is Circuit Breaker?**  
If a service keeps failing, stop calling it for a while (open circuit) and maybe return a fallback. Prevents cascade failures. After some time, try again (half-open).

---

## KAFKA & REDIS

**What is Kafka?**  
A messaging system. Producers send messages to topics; consumers read from topics. Messages are stored and can be replayed. Used for event streaming and decoupling services.

**Producer vs Consumer?**  
- **Producer** – Sends messages to a Kafka topic.  
- **Consumer** – Reads messages from a topic. Many consumers can read (e.g. in a consumer group for load sharing).

**What is Redis?**  
In-memory store. Used as cache (fast reads), session store, or simple key-value DB. Very fast because data is in RAM.

**Cache eviction?**  
When cache is full, which keys to remove? Common: LRU (Least Recently Used), TTL (expire after time), or FIFO.

---

## QUICK ONE-LINERS (Memorize these)

| Topic        | One-line answer |
|-------------|------------------|
| JDK         | To develop Java apps (has compiler + JRE). |
| JRE         | To run Java apps (JVM + libs). |
| JVM         | Runs bytecode; makes Java “write once run anywhere”. |
| Immutable   | Cannot be changed after creation (e.g. String). |
| DI          | Dependencies given from outside, not created inside. |
| IoC         | Container creates and manages objects, not you. |
| Bean        | Object created and managed by Spring. |
| REST        | API style using HTTP methods and URLs. |
| ORM         | Maps objects to database tables (e.g. JPA/Hibernate). |
| Transaction | Multiple DB operations succeed or fail together. |
| Stateless   | Server doesn’t store client state; each request has all needed info (e.g. JWT). |

---

**Tip for interview:** Answer in 2–3 sentences first (“In simple terms…”), then add one short example or comparison if they want more.
