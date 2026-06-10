# Java Backend Interview Questions - Complete Guide

Welcome to the comprehensive Java Backend & Spring Boot interview questions repository! This guide covers all essential topics with clear explanations and practical code examples.

## ⚡ Quick revision: [SIMPLE-INTERVIEW-ANSWERS.md](./SIMPLE-INTERVIEW-ANSWERS.md)
**Plain-language, short answers for interviews.** One question = one straight answer. Use it for last-minute prep.

---

## 📚 Table of Contents

### Core Java
1. **[Java Basics](./01-java-basics.md)**
   - JDK, JRE, JVM
   - OOP Principles
   - Abstract Classes vs Interfaces
   - Exception Handling
   - String, StringBuilder, StringBuffer
   - Garbage Collection
   - Access Modifiers

2. **[Collections Framework](./02-collections-framework.md)**
   - List (ArrayList vs LinkedList)
   - Set (HashSet vs TreeSet vs LinkedHashSet)
   - Map (HashMap vs Hashtable vs ConcurrentHashMap)
   - HashMap Internal Working
   - Iterator, Comparable, Comparator
   - Fail-fast vs Fail-safe

3. **[Java 8 Features](./03-java8-features.md)**
   - Lambda Expressions
   - Functional Interfaces
   - Stream API (filter, map, reduce)
   - Optional Class
   - Method References
   - Default & Static Methods in Interfaces
   - Date/Time API

### Spring Framework
4. **[Spring Framework](./04-spring-framework.md)**
   - Dependency Injection (DI)
   - Inversion of Control (IoC)
   - @Component, @Service, @Repository
   - @Autowired, @Qualifier
   - Bean Scopes & Lifecycle
   - Spring AOP
   - @Transactional

5. **[Spring Boot](./05-spring-boot.md)**
   - Auto-configuration
   - Spring Boot Starters
   - @SpringBootApplication
   - application.properties vs yml
   - @RestController vs @Controller
   - REST Annotations (@GetMapping, @PostMapping, etc.)
   - Spring Boot Actuator
   - Exception Handling

### Architecture & Design
6. **[Microservices](./06-microservices.md)**
   - Microservices Architecture
   - Service Discovery (Eureka)
   - API Gateway
   - Load Balancing
   - Circuit Breaker (Resilience4j)
   - Distributed Tracing (Sleuth & Zipkin)
   - Saga Pattern
   - Configuration Management
   - Service Mesh

7. **[REST API](./07-rest-api.md)**
   - REST Principles
   - HTTP Methods & Status Codes
   - PUT vs PATCH
   - API Best Practices
   - Pagination, Filtering, Sorting
   - Validation
   - HATEOAS
   - File Upload/Download
   - API Versioning
   - Rate Limiting

### Database
8. **[Database & JPA](./08-database-jpa.md)**
   - JPA Basics
   - Entity Relationships (@OneToOne, @OneToMany, @ManyToMany)
   - CascadeType
   - FetchType (LAZY vs EAGER)
   - N+1 Query Problem
   - JPQL vs Native Queries
   - Derived Query Methods
   - @Transactional
   - Auditing
   - Optimistic vs Pessimistic Locking

### Security
9. **[Spring Security](./09-spring-security.md)**
   - Authentication vs Authorization
   - Basic Authentication
   - Database Authentication
   - JWT (JSON Web Token)
   - Roles & Authorities
   - OAuth2 Login
   - CSRF Protection
   - Password Encryption
   - Refresh Token
   - Remember Me
   - @PreAuthorize, @Secured

### Concurrency
10. **[Multithreading & Concurrency](./10-multithreading.md)**
    - Thread Creation (Thread, Runnable, Lambda)
    - start() vs run()
    - Thread States
    - Synchronization
    - wait() vs sleep()
    - Producer-Consumer Problem
    - Deadlock
    - ExecutorService & Thread Pools
    - Callable vs Runnable
    - Future & CompletableFuture
    - ThreadLocal
    - CountDownLatch, CyclicBarrier
    - Concurrent Collections

### Messaging & Caching
11. **[Kafka & Redis](./11-kafka-redis.md)**
    - Apache Kafka Basics
    - Producer & Consumer
    - Consumer Groups
    - Redis Data Structures
    - Caching with Redis
    - Pub/Sub
    - Rate Limiting
    - Distributed Locking
    - Cache Eviction Strategies

---

## 🎯 How to Use This Guide

### For Interview Preparation
1. **Start with Basics** - Begin with Java Basics and Collections
2. **Move to Frameworks** - Spring Framework and Spring Boot
3. **Learn Architecture** - Microservices and REST API
4. **Master Advanced Topics** - Security, Multithreading, Kafka/Redis

### For Quick Review
- Each file is organized with **question-answer format**
- **Code examples** for every concept
- **Simple explanations** without jargon

### Practice Approach
1. **Read the question** - Try to answer it yourself first
2. **Check the answer** - Compare with the provided explanation
3. **Run the code** - Practice the code examples
4. **Explain to someone** - Teaching is the best way to learn

---

## 💡 Key Topics You Must Know

### For Junior Developer (0-2 years)
- ✅ Java Basics (OOP, Collections, Exception Handling)
- ✅ Spring Core (DI, IoC, Beans)
- ✅ Spring Boot (REST APIs, JPA)
- ✅ Database (SQL, JPA relationships)
- ✅ Git & Maven/Gradle

### For Mid-Level Developer (2-5 years)
- ✅ All Junior topics
- ✅ Microservices architecture
- ✅ Spring Security (JWT, OAuth2)
- ✅ Kafka or RabbitMQ
- ✅ Redis caching
- ✅ Unit testing (JUnit, Mockito)
- ✅ Docker basics

### For Senior Developer (5+ years)
- ✅ All Mid-level topics
- ✅ System design
- ✅ Design patterns
- ✅ Cloud (AWS/Azure)
- ✅ CI/CD pipelines
- ✅ Performance optimization
- ✅ Kubernetes

---

## 📖 Common Interview Questions by Company Type

### Service-Based Companies (TCS, Infosys, Wipro, Cognizant)
Focus on:
- Java basics & OOP
- Collections framework
- Spring Boot REST APIs
- JPA & database queries
- Basic Spring Security

### Product Companies (Amazon, Google, Microsoft)
Focus on:
- Data structures & algorithms
- System design
- Microservices
- Scalability & performance
- Design patterns
- Coding problems

### Startups
Focus on:
- Full-stack knowledge
- Quick development (Spring Boot)
- Cloud deployment
- Problem-solving
- Multiple technologies

---

## 🚀 Quick Tips for Interviews

### Technical Interview
1. **Understand the question** - Ask clarifying questions
2. **Think out loud** - Explain your thought process
3. **Start simple** - Basic solution first, then optimize
4. **Write clean code** - Proper naming, formatting
5. **Test your code** - Think about edge cases

### Coding Round
1. **Practice on LeetCode/HackerRank**
2. **Know time/space complexity**
3. **Use proper data structures**
4. **Optimize your solution**
5. **Explain your approach**

### System Design Round
1. **Ask requirements** - Functional & non-functional
2. **Start high-level** - Components, data flow
3. **Go into details** - Database schema, APIs
4. **Discuss trade-offs** - Pros and cons
5. **Consider scalability** - Load balancing, caching

---

## 🔗 Additional Resources

### Online Learning
- **Spring Official Docs**: https://spring.io/guides
- **Baeldung**: https://www.baeldung.com
- **Java Documentation**: https://docs.oracle.com/javase

### Practice Platforms
- **LeetCode**: Coding problems
- **HackerRank**: Java & SQL
- **InterviewBit**: Interview preparation

### YouTube Channels
- **Java Brains**: Spring Boot tutorials
- **Telusko**: Java & Spring
- **Code With Durgesh**: Hindi tutorials

---

## 📝 Sample Interview Questions

### Beginner Level
1. What is the difference between JDK, JRE, and JVM?
2. Explain OOP principles with examples
3. What is dependency injection?
4. How to create a REST API in Spring Boot?
5. What is the difference between @Component and @Service?

### Intermediate Level
1. How does HashMap work internally?
2. Explain N+1 query problem and solutions
3. What is JWT and how to implement it?
4. Difference between @Transactional(REQUIRED) and REQUIRES_NEW?
5. How to handle concurrent requests in Spring Boot?

### Advanced Level
1. Design a URL shortener like bit.ly
2. How to implement rate limiting in microservices?
3. Explain saga pattern for distributed transactions
4. How to handle millions of concurrent users?
5. Design an e-commerce system with microservices

---

## 🎓 Contributing

Found an error or want to add more questions? Feel free to:
1. Create an issue
2. Submit a pull request
3. Suggest improvements

---

## ⭐ Best Practices

### Code Quality
- Write clean, readable code
- Use meaningful variable names
- Add comments for complex logic
- Follow SOLID principles

### Spring Boot
- Use constructor injection over field injection
- Keep controllers thin, services fat
- Use DTOs for API requests/responses
- Handle exceptions globally with @ControllerAdvice
- Use validation annotations

### Database
- Use indexes for frequently queried columns
- Avoid N+1 queries
- Use pagination for large datasets
- Use connection pooling
- Cache frequently accessed data

### Security
- Never store passwords in plain text
- Use HTTPS in production
- Validate all user inputs
- Implement rate limiting
- Use JWT with short expiration times

---

## 🏆 Success Tips

1. **Practice Daily** - Code every day, even 30 minutes
2. **Build Projects** - Real projects teach more than theory
3. **Read Documentation** - Official docs are the best resource
4. **Join Communities** - Stack Overflow, Reddit, Discord
5. **Stay Updated** - Follow Java and Spring releases
6. **Mock Interviews** - Practice with friends or online platforms
7. **Stay Calm** - Confidence matters in interviews

---

## 📧 Contact & Feedback

If you find this guide helpful, please ⭐ star the repository!

**Good Luck with Your Interviews! 🚀**

Remember: *Consistency is key. Keep learning, keep practicing!*

---

**Last Updated**: January 2026

**Note**: All code examples are tested with:
- Java 17+
- Spring Boot 3.x
- Maven/Gradle
