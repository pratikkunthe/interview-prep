# Messaging and Caching

## Kafka

### 1. Kafka utility

**Answer:**

**Apache Kafka** = Distributed streaming platform (messaging system)

**Think of it like:** Message bus that connects services - producers send messages, consumers receive them

**What it does:**

**1. Messaging**
- Send messages between services
- Decouple services
- Asynchronous communication

**2. Event Streaming**
- Stream events in real-time
- Process events as they happen
- High throughput

**3. Log Aggregation**
- Collect logs from multiple sources
- Centralized logging
- Historical data

**Key Concepts:**

**1. Producer** - Sends messages
```java
@Autowired
private KafkaTemplate<String, String> kafkaTemplate;

public void sendMessage(String topic, String message) {
    kafkaTemplate.send(topic, message);
}
```

**2. Consumer** - Receives messages
```java
@KafkaListener(topics = "orders", groupId = "order-processor")
public void consume(String message) {
    System.out.println("Received: " + message);
    // Process message
}
```

**3. Topic** - Category/stream of messages
- Like a channel
- Messages organized by topic

**4. Broker** - Kafka server
- Stores messages
- Handles requests

**5. Partition** - Topic divided into partitions
- Parallel processing
- Scalability

**Use Cases:**

**1. Event-Driven Architecture**
```
Order Service → Kafka → Payment Service
                  ↓
              Shipping Service
```

**2. Real-Time Processing**
```
User Actions → Kafka → Analytics Service
```

**3. Log Aggregation**
```
Applications → Kafka → Log Storage
```

**Example:**

```java
// Producer
@Service
public class OrderProducer {
    
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    
    public void publishOrderCreated(Order order) {
        OrderEvent event = new OrderEvent(order.getId(), "CREATED");
        kafkaTemplate.send("order-events", event);
    }
}

// Consumer
@Component
public class OrderConsumer {
    
    @KafkaListener(topics = "order-events", groupId = "payment-service")
    public void handleOrderCreated(OrderEvent event) {
        if("CREATED".equals(event.getStatus())) {
            paymentService.processPayment(event.getOrderId());
        }
    }
}
```

**Benefits:**
- ✅ High throughput
- ✅ Scalable
- ✅ Durable (messages persisted)
- ✅ Decoupled services

**Simple rule:** Kafka = Message bus for services. Producers send, consumers receive, topics organize messages!

---

## Caching

### 1. Caching - Implementation and concepts

**Answer:**

**Caching** = Store frequently accessed data in fast storage (memory) for quick access

**Think of it like:** Keeping frequently used items on your desk instead of going to storage every time

**Why use caching:**
- ✅ Faster access (memory vs database)
- ✅ Reduce database load
- ✅ Better performance
- ✅ Lower costs

**Types of Caching:**

**1. In-Memory Cache** (Local)
- Cache in application memory
- Fast but limited
- Lost on restart

**2. Distributed Cache** (Redis, Hazelcast)
- Shared cache across services
- Persistent
- Scalable

**Spring Cache Implementation:**

**Step 1: Enable Caching**

```java
@SpringBootApplication
@EnableCaching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

**Step 2: Configure Cache**

```java
@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("users", "orders");
    }
    
    // Or use Redis
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10));
        
        return RedisCacheManager.builder(factory)
            .cacheDefaults(config)
            .build();
    }
}
```

**Step 3: Use Cache Annotations**

```java
@Service
public class UserService {
    
    // Cache result
    @Cacheable(value = "users", key = "#id")
    public User getUser(Long id) {
        System.out.println("Fetching from database");
        return userRepository.findById(id).orElse(null);
    }
    
    // Update cache
    @CachePut(value = "users", key = "#user.id")
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    // Remove from cache
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    // Clear entire cache
    @CacheEvict(value = "users", allEntries = true)
    public void clearCache() {
    }
}
```

**Cache Annotations:**

**1. @Cacheable** - Cache result
```java
@Cacheable("users")
public User getUser(Long id) {
    // First call: Executes method, caches result
    // Next calls: Returns from cache, doesn't execute method
    return userRepository.findById(id);
}
```

**2. @CachePut** - Update cache
```java
@CachePut(value = "users", key = "#user.id")
public User saveUser(User user) {
    // Always executes method AND updates cache
    return userRepository.save(user);
}
```

**3. @CacheEvict** - Remove from cache
```java
@CacheEvict(value = "users", key = "#id")
public void deleteUser(Long id) {
    // Removes from cache when user deleted
    userRepository.deleteById(id);
}
```

**4. @Caching** - Multiple cache operations
```java
@Caching(
    evict = {
        @CacheEvict(value = "users", key = "#user.id"),
        @CacheEvict(value = "user-list", allEntries = true)
    }
)
public User updateUser(User user) {
    return userRepository.save(user);
}
```

**Redis Cache Example:**

```java
// Configuration
@Configuration
@EnableCaching
public class RedisConfig {
    
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
    
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))
            .serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer()));
        
        return RedisCacheManager.builder(factory)
            .cacheDefaults(config)
            .withCacheConfiguration("users", 
                config.entryTtl(Duration.ofMinutes(5)))
            .build();
    }
}

// Usage
@Service
public class UserService {
    
    @Cacheable(value = "users", key = "#id")
    public User getUser(Long id) {
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
}
```

**Cache Strategies:**

**1. Cache-Aside (Lazy Loading)**
```java
public User getUser(Long id) {
    // Check cache first
    User user = cache.get("user:" + id);
    if(user == null) {
        // Not in cache, get from database
        user = userRepository.findById(id);
        // Store in cache
        cache.put("user:" + id, user);
    }
    return user;
}
```

**2. Write-Through**
```java
public User saveUser(User user) {
    // Save to database
    user = userRepository.save(user);
    // Update cache
    cache.put("user:" + user.getId(), user);
    return user;
}
```

**3. Write-Behind (Write-Back)**
```java
public void saveUser(User user) {
    // Write to cache immediately
    cache.put("user:" + user.getId(), user);
    // Write to database asynchronously later
    asyncWriteToDatabase(user);
}
```

**Cache Considerations:**

**1. Cache Invalidation**
- When to clear cache
- What to clear
- How to handle stale data

**2. Cache Key Design**
- Meaningful keys
- Include relevant parameters
- Avoid collisions

**3. TTL (Time To Live)**
- How long data stays in cache
- Balance freshness vs performance

**4. Cache Size**
- Memory limits
- Eviction policies (LRU, LFU)

**Benefits:**
- ✅ Faster response times
- ✅ Reduced database load
- ✅ Better scalability
- ✅ Lower costs

**Drawbacks:**
- ❌ Stale data risk
- ❌ Memory usage
- ❌ Cache invalidation complexity

**Simple rule:** Caching = Store frequently used data in fast memory. Use @Cacheable to cache, @CacheEvict to clear!
