# Kafka & Redis Interview Questions

## 1. What is Apache Kafka?

**Answer:**

Kafka is a distributed event streaming platform for building real-time data pipelines and streaming applications.

**Key Concepts:**
- **Producer** - Sends messages to Kafka
- **Consumer** - Reads messages from Kafka
- **Topic** - Category/feed where messages are published
- **Partition** - Topic divided into partitions for scalability
- **Broker** - Kafka server
- **Consumer Group** - Group of consumers that share workload

**Architecture:**
```
Producer → Kafka Broker (Topic: orders)
                └── Partition 0
                └── Partition 1
                └── Partition 2
           ↓
Consumer Group
  └── Consumer 1 (reads Partition 0, 1)
  └── Consumer 2 (reads Partition 2)
```

---

## 2. How to implement Kafka Producer in Spring Boot?

**Answer:**

**Add Dependency:**
```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
```

**Configuration:**
```properties
# application.properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
```

**Producer:**
```java
@Service
public class OrderProducer {
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    public void sendOrder(Order order) {
        String message = "Order: " + order.getId();
        kafkaTemplate.send("order-topic", message);
        System.out.println("Order sent: " + message);
    }
    
    // With callback
    public void sendOrderWithCallback(Order order) {
        String message = "Order: " + order.getId();
        
        ListenableFuture<SendResult<String, String>> future = 
            kafkaTemplate.send("order-topic", message);
        
        future.addCallback(
            result -> System.out.println("Sent: " + message),
            ex -> System.out.println("Failed: " + ex.getMessage())
        );
    }
}
```

**Send JSON:**
```java
@Service
public class OrderProducer {
    
    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;
    
    public void sendOrder(Order order) {
        kafkaTemplate.send("order-topic", order);
    }
}
```

**JSON Configuration:**
```properties
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
```

---

## 3. How to implement Kafka Consumer?

**Answer:**

**Configuration:**
```properties
spring.kafka.consumer.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=order-consumer-group
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.auto-offset-reset=earliest
```

**Consumer:**
```java
@Service
public class OrderConsumer {
    
    @KafkaListener(topics = "order-topic", groupId = "order-consumer-group")
    public void consumeOrder(String message) {
        System.out.println("Received: " + message);
        // Process order
    }
}
```

**Consume JSON:**
```java
@Service
public class OrderConsumer {
    
    @KafkaListener(topics = "order-topic", groupId = "order-consumer-group")
    public void consumeOrder(Order order) {
        System.out.println("Received order: " + order.getId());
        // Process order
    }
}
```

**JSON Configuration:**
```properties
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.consumer.properties.spring.json.trusted.packages=*
```

**Multiple Topics:**
```java
@KafkaListener(topics = {"order-topic", "payment-topic"}, groupId = "order-consumer-group")
public void consumeMultiple(String message) {
    System.out.println("Received: " + message);
}
```

**With Headers:**
```java
@KafkaListener(topics = "order-topic", groupId = "order-consumer-group")
public void consumeOrder(
    @Payload Order order,
    @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
    @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition
) {
    System.out.println("Topic: " + topic + ", Partition: " + partition);
    System.out.println("Order: " + order.getId());
}
```

---

## 4. What is Consumer Group in Kafka?

**Answer:**

Consumer Group allows multiple consumers to share workload. Each message is consumed by only one consumer in the group.

**Example:**

**Scenario 1: Single Consumer**
```
Topic (3 partitions)
  Partition 0 → Consumer A
  Partition 1 → Consumer A
  Partition 2 → Consumer A
```
One consumer handles all partitions (slow).

**Scenario 2: Consumer Group**
```
Topic (3 partitions)
  Partition 0 → Consumer A
  Partition 1 → Consumer B
  Partition 2 → Consumer C
```
Work distributed among consumers (fast, parallel processing).

**Implementation:**
```java
// Consumer Group 1 (Order Processing)
@KafkaListener(topics = "order-topic", groupId = "order-processing")
public void processOrder(Order order) {
    // Process order
}

// Consumer Group 2 (Email Notification)
@KafkaListener(topics = "order-topic", groupId = "email-notification")
public void sendEmail(Order order) {
    // Send email
}

// Both groups receive all messages independently
```

---

## 5. What is Redis?

**Answer:**

Redis is an in-memory data store used as cache, database, and message broker.

**Key Features:**
- **Fast** - In-memory (microsecond latency)
- **Data Structures** - Strings, Lists, Sets, Hashes, Sorted Sets
- **Persistence** - Can save data to disk
- **Pub/Sub** - Message broadcasting
- **Expiration** - Auto-delete keys after timeout

**Use Cases:**
- Caching (session, API responses)
- Real-time analytics
- Leaderboards
- Rate limiting
- Message queue

---

## 6. How to use Redis in Spring Boot?

**Answer:**

**Add Dependency:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

**Configuration:**
```properties
# application.properties
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=
```

**String Operations:**
```java
@Service
public class RedisService {
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    // Set value
    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }
    
    // Get value
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    
    // Set with expiration
    public void setValueWithExpiry(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }
    
    // Delete
    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }
    
    // Check existence
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
```

---

## 7. How to cache data using Redis?

**Answer:**

**Enable Caching:**
```java
@SpringBootApplication
@EnableCaching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

**Cache Configuration:**
```java
@Configuration
public class RedisConfig {
    
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))  // Cache for 10 minutes
            .disableCachingNullValues();
        
        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(config)
            .build();
    }
}
```

**Service with Caching:**
```java
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        System.out.println("Fetching from database: " + id);
        return userRepository.findById(id).orElse(null);
    }
    
    @CachePut(value = "users", key = "#user.id")
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    @CacheEvict(value = "users", allEntries = true)
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}

// First call: Fetches from database and caches
// Subsequent calls: Returns from cache (fast!)
```

---

## 8. How to store Objects in Redis?

**Answer:**

**Configuration:**
```java
@Configuration
public class RedisConfig {
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        
        // JSON serialization
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);
        
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        
        return template;
    }
}
```

**Usage:**
```java
@Service
public class UserCacheService {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    public void saveUser(User user) {
        redisTemplate.opsForValue().set("user:" + user.getId(), user);
    }
    
    public User getUser(Long id) {
        return (User) redisTemplate.opsForValue().get("user:" + id);
    }
    
    // Hash operations (store user as fields)
    public void saveUserAsHash(User user) {
        redisTemplate.opsForHash().put("user:" + user.getId(), "name", user.getName());
        redisTemplate.opsForHash().put("user:" + user.getId(), "email", user.getEmail());
        redisTemplate.opsForHash().put("user:" + user.getId(), "age", user.getAge());
    }
    
    public String getUserName(Long id) {
        return (String) redisTemplate.opsForHash().get("user:" + id, "name");
    }
}
```

---

## 9. What are Redis Data Structures?

**Answer:**

**1. String:**
```java
redisTemplate.opsForValue().set("name", "John");
String name = redisTemplate.opsForValue().get("name");
```

**2. List:**
```java
// Push to list
redisTemplate.opsForList().rightPush("queue", "task1");
redisTemplate.opsForList().rightPush("queue", "task2");

// Pop from list
String task = redisTemplate.opsForList().leftPop("queue");
```

**3. Set:**
```java
// Add to set
redisTemplate.opsForSet().add("tags", "java", "spring", "redis");

// Get all members
Set<String> tags = redisTemplate.opsForSet().members("tags");

// Check membership
boolean exists = redisTemplate.opsForSet().isMember("tags", "java");
```

**4. Hash:**
```java
// Set hash fields
redisTemplate.opsForHash().put("user:1", "name", "John");
redisTemplate.opsForHash().put("user:1", "email", "john@example.com");

// Get field
String name = (String) redisTemplate.opsForHash().get("user:1", "name");

// Get all fields
Map<Object, Object> user = redisTemplate.opsForHash().entries("user:1");
```

**5. Sorted Set:**
```java
// Add with score (leaderboard)
redisTemplate.opsForZSet().add("leaderboard", "player1", 100);
redisTemplate.opsForZSet().add("leaderboard", "player2", 200);
redisTemplate.opsForZSet().add("leaderboard", "player3", 150);

// Get top 10
Set<String> top10 = redisTemplate.opsForZSet().reverseRange("leaderboard", 0, 9);

// Get rank
Long rank = redisTemplate.opsForZSet().reverseRank("leaderboard", "player2");
```

---

## 10. How to implement Rate Limiting using Redis?

**Answer:**

```java
@Service
public class RateLimiterService {
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    private static final int MAX_REQUESTS = 10;  // 10 requests
    private static final int TIME_WINDOW = 60;   // per minute
    
    public boolean isAllowed(String userId) {
        String key = "rate_limit:" + userId;
        
        // Get current count
        String countStr = redisTemplate.opsForValue().get(key);
        int count = (countStr != null) ? Integer.parseInt(countStr) : 0;
        
        if (count >= MAX_REQUESTS) {
            return false;  // Rate limit exceeded
        }
        
        // Increment count
        redisTemplate.opsForValue().increment(key);
        
        // Set expiration on first request
        if (count == 0) {
            redisTemplate.expire(key, TIME_WINDOW, TimeUnit.SECONDS);
        }
        
        return true;
    }
}
```

**Controller:**
```java
@RestController
@RequestMapping("/api")
public class ApiController {
    
    @Autowired
    private RateLimiterService rateLimiter;
    
    @GetMapping("/data")
    public ResponseEntity<String> getData(@RequestHeader("User-Id") String userId) {
        if (!rateLimiter.isAllowed(userId)) {
            return ResponseEntity.status(429).body("Too many requests");
        }
        
        return ResponseEntity.ok("Data");
    }
}
```

---

## 11. What is Pub/Sub in Redis?

**Answer:**

Pub/Sub allows publishing messages to channels and subscribing to them.

**Publisher:**
```java
@Service
public class NotificationPublisher {
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    public void publishNotification(String message) {
        redisTemplate.convertAndSend("notifications", message);
    }
}
```

**Subscriber:**
```java
@Service
public class NotificationSubscriber {
    
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListener(), new PatternTopic("notifications"));
        return container;
    }
    
    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new MessageSubscriber());
    }
}

class MessageSubscriber {
    public void handleMessage(String message) {
        System.out.println("Received: " + message);
        // Process notification
    }
}
```

---

## 12. What is Kafka vs Redis?

**Answer:**

| Feature | Kafka | Redis |
|---------|-------|-------|
| Type | Message Queue | In-memory Cache |
| Persistence | Durable (disk) | Optional (memory) |
| Use Case | Event streaming, logs | Caching, session |
| Message Retention | Long-term | Short-term |
| Speed | High throughput | Very fast (in-memory) |
| Ordering | Guaranteed (per partition) | No guarantee |
| Data Structures | Messages | Strings, Lists, Sets, Hashes |

**When to use:**
- **Kafka**: Event streaming, audit logs, microservices communication
- **Redis**: Caching, session storage, real-time analytics

---

## 13. How to implement Distributed Locking with Redis?

**Answer:**

```java
@Service
public class DistributedLockService {
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    public boolean acquireLock(String lockKey, String value, long timeout) {
        Boolean success = redisTemplate.opsForValue()
            .setIfAbsent(lockKey, value, timeout, TimeUnit.SECONDS);
        
        return success != null && success;
    }
    
    public void releaseLock(String lockKey, String value) {
        String currentValue = redisTemplate.opsForValue().get(lockKey);
        
        // Only release if we own the lock
        if (value.equals(currentValue)) {
            redisTemplate.delete(lockKey);
        }
    }
}

// Usage
@Service
public class OrderService {
    
    @Autowired
    private DistributedLockService lockService;
    
    public void processOrder(Long orderId) {
        String lockKey = "order:lock:" + orderId;
        String lockValue = UUID.randomUUID().toString();
        
        try {
            // Acquire lock
            if (lockService.acquireLock(lockKey, lockValue, 30)) {
                // Process order (only one instance processes this order)
                System.out.println("Processing order: " + orderId);
                Thread.sleep(5000);
            } else {
                System.out.println("Could not acquire lock for order: " + orderId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Release lock
            lockService.releaseLock(lockKey, lockValue);
        }
    }
}
```

---

## 14. What is Cache Eviction Strategy?

**Answer:**

**Strategies:**

**1. LRU (Least Recently Used)** - Removes least recently accessed items
```properties
spring.cache.redis.time-to-live=600000  # 10 minutes
```

**2. LFU (Least Frequently Used)** - Removes least frequently accessed items

**3. TTL (Time To Live)** - Auto-expire after timeout
```java
@Cacheable(value = "users", key = "#id")
@CacheConfig(cacheNames = "users")
public User getUser(Long id) {
    return userRepository.findById(id);
}
```

**Manual Eviction:**
```java
@CacheEvict(value = "users", key = "#id")
public void deleteUser(Long id) {
    userRepository.deleteById(id);
}

@CacheEvict(value = "users", allEntries = true)
public void clearCache() {
    // Clears all cache entries
}

@Scheduled(fixedRate = 3600000)  // Every hour
@CacheEvict(value = "users", allEntries = true)
public void clearCacheScheduled() {
    System.out.println("Cache cleared");
}
```

---

## 15. How to handle Kafka Consumer Failure?

**Answer:**

**Error Handling:**
```java
@Service
public class OrderConsumer {
    
    @KafkaListener(topics = "order-topic", groupId = "order-consumer-group")
    public void consumeOrder(Order order) {
        try {
            processOrder(order);
        } catch (Exception e) {
            // Log error
            System.err.println("Error processing order: " + order.getId());
            
            // Send to Dead Letter Queue (DLQ)
            kafkaTemplate.send("order-dlq", order);
        }
    }
}
```

**Retry Configuration:**
```properties
spring.kafka.consumer.enable-auto-commit=false
spring.kafka.listener.ack-mode=manual
```

**Manual Commit with Retry:**
```java
@KafkaListener(topics = "order-topic", groupId = "order-consumer-group")
public void consumeOrder(Order order, Acknowledgment acknowledgment) {
    int retries = 0;
    int maxRetries = 3;
    
    while (retries < maxRetries) {
        try {
            processOrder(order);
            acknowledgment.acknowledge();  // Commit offset
            return;
        } catch (Exception e) {
            retries++;
            System.err.println("Retry " + retries + " for order: " + order.getId());
            
            if (retries >= maxRetries) {
                // Send to DLQ
                kafkaTemplate.send("order-dlq", order);
                acknowledgment.acknowledge();
            }
        }
    }
}
```

**Dead Letter Queue Consumer:**
```java
@KafkaListener(topics = "order-dlq", groupId = "dlq-consumer")
public void consumeDLQ(Order order) {
    System.out.println("Processing failed order from DLQ: " + order.getId());
    // Manual investigation or alternative processing
}
```
