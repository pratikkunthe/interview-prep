# Multithreading & Concurrency Interview Questions

## 1. What is a Thread in Java?

**Answer:**

A thread is the smallest unit of execution in a program. It allows multiple tasks to run concurrently.

**Creating Threads:**

**Method 1: Extend Thread class**
```java
class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }
}

// Usage
MyThread thread1 = new MyThread();
MyThread thread2 = new MyThread();
thread1.start();  // Starts thread
thread2.start();
```

**Method 2: Implement Runnable interface (Better)**
```java
class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }
}

// Usage
Thread thread1 = new Thread(new MyRunnable());
Thread thread2 = new Thread(new MyRunnable());
thread1.start();
thread2.start();
```

**Method 3: Lambda (Java 8)**
```java
Thread thread = new Thread(() -> {
    for (int i = 0; i < 5; i++) {
        System.out.println(Thread.currentThread().getName() + ": " + i);
    }
});
thread.start();
```

---

## 2. What is the difference between start() and run()?

**Answer:**

**start():**
- Creates new thread
- Calls run() method in new thread
- Can only be called once

```java
Thread thread = new Thread(() -> {
    System.out.println("Thread: " + Thread.currentThread().getName());
});

thread.start();  // Creates new thread (e.g., Thread-0)
// Output: Thread: Thread-0
```

**run():**
- Does NOT create new thread
- Executes in current thread
- Can be called multiple times

```java
Thread thread = new Thread(() -> {
    System.out.println("Thread: " + Thread.currentThread().getName());
});

thread.run();  // Executes in current thread (main)
// Output: Thread: main
```

---

## 3. What are Thread States?

**Answer:**

**Thread Lifecycle:**
```
NEW → RUNNABLE → RUNNING → TERMINATED
         ↓           ↓
      BLOCKED    WAITING
```

**1. NEW** - Thread created but not started
```java
Thread thread = new Thread(() -> {});
// State: NEW
```

**2. RUNNABLE** - Thread ready to run
```java
thread.start();
// State: RUNNABLE
```

**3. RUNNING** - Thread executing
```java
// Thread is executing run() method
```

**4. BLOCKED** - Waiting for lock
```java
synchronized(lock) {
    // Another thread holds lock
    // State: BLOCKED
}
```

**5. WAITING** - Waiting indefinitely
```java
thread.wait();  // State: WAITING
```

**6. TIMED_WAITING** - Waiting for specific time
```java
Thread.sleep(1000);  // State: TIMED_WAITING
```

**7. TERMINATED** - Thread completed
```java
// After run() method completes
// State: TERMINATED
```

---

## 4. What is synchronization?

**Answer:**

Synchronization prevents multiple threads from accessing shared resources simultaneously.

**Problem without synchronization:**
```java
class Counter {
    private int count = 0;
    
    public void increment() {
        count++;  // Not thread-safe!
    }
    
    public int getCount() {
        return count;
    }
}

// Two threads increment simultaneously
Counter counter = new Counter();
Thread t1 = new Thread(() -> {
    for (int i = 0; i < 1000; i++) counter.increment();
});
Thread t2 = new Thread(() -> {
    for (int i = 0; i < 1000; i++) counter.increment();
});
t1.start();
t2.start();
t1.join();
t2.join();

System.out.println(counter.getCount());  // Expected: 2000, Actual: < 2000
```

**Solution: Synchronized method**
```java
class Counter {
    private int count = 0;
    
    public synchronized void increment() {
        count++;  // Thread-safe!
    }
    
    public synchronized int getCount() {
        return count;
    }
}

// Now: count = 2000 (correct)
```

**Synchronized block (better performance):**
```java
class Counter {
    private int count = 0;
    private Object lock = new Object();
    
    public void increment() {
        synchronized(lock) {
            count++;
        }
    }
}
```

---

## 5. What is the difference between wait() and sleep()?

**Answer:**

| wait() | sleep() |
|--------|---------|
| Defined in Object class | Defined in Thread class |
| Releases lock | Does NOT release lock |
| Must be called inside synchronized block | Can be called anywhere |
| Woken up by notify()/notifyAll() | Wakes up after time expires |

**sleep():**
```java
public void process() {
    synchronized(lock) {
        try {
            System.out.println("Starting...");
            Thread.sleep(2000);  // Sleeps but holds lock
            System.out.println("Finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

**wait():**
```java
public void process() {
    synchronized(lock) {
        try {
            System.out.println("Waiting...");
            lock.wait();  // Releases lock, waits for notify()
            System.out.println("Notified");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public void notifyProcess() {
    synchronized(lock) {
        lock.notify();  // Wakes up waiting thread
    }
}
```

---

## 6. What is Producer-Consumer Problem?

**Answer:**

Producer produces data, Consumer consumes it. Need to synchronize so Consumer doesn't consume before Producer produces.

**Using wait() and notify():**
```java
class SharedResource {
    private Queue<Integer> queue = new LinkedList<>();
    private int capacity = 5;
    
    public synchronized void produce(int value) throws InterruptedException {
        while (queue.size() == capacity) {
            System.out.println("Queue full, producer waiting...");
            wait();  // Wait until space available
        }
        
        queue.add(value);
        System.out.println("Produced: " + value);
        notify();  // Notify consumer
    }
    
    public synchronized int consume() throws InterruptedException {
        while (queue.isEmpty()) {
            System.out.println("Queue empty, consumer waiting...");
            wait();  // Wait until data available
        }
        
        int value = queue.remove();
        System.out.println("Consumed: " + value);
        notify();  // Notify producer
        return value;
    }
}

// Producer thread
new Thread(() -> {
    for (int i = 0; i < 10; i++) {
        try {
            resource.produce(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}).start();

// Consumer thread
new Thread(() -> {
    for (int i = 0; i < 10; i++) {
        try {
            resource.consume();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}).start();
```

---

## 7. What is Deadlock?

**Answer:**

Deadlock occurs when two threads wait for each other's resources indefinitely.

**Deadlock Example:**
```java
Object lock1 = new Object();
Object lock2 = new Object();

// Thread 1
new Thread(() -> {
    synchronized(lock1) {
        System.out.println("Thread 1: Holding lock1...");
        
        try { Thread.sleep(100); } catch (Exception e) {}
        
        System.out.println("Thread 1: Waiting for lock2...");
        synchronized(lock2) {
            System.out.println("Thread 1: Holding lock1 & lock2");
        }
    }
}).start();

// Thread 2
new Thread(() -> {
    synchronized(lock2) {
        System.out.println("Thread 2: Holding lock2...");
        
        try { Thread.sleep(100); } catch (Exception e) {}
        
        System.out.println("Thread 2: Waiting for lock1...");
        synchronized(lock1) {
            System.out.println("Thread 2: Holding lock1 & lock2");
        }
    }
}).start();

// Deadlock! Thread 1 waits for lock2, Thread 2 waits for lock1
```

**Solution: Lock ordering**
```java
// Both threads acquire locks in same order
new Thread(() -> {
    synchronized(lock1) {
        synchronized(lock2) {
            // Safe
        }
    }
}).start();

new Thread(() -> {
    synchronized(lock1) {  // Same order
        synchronized(lock2) {
            // Safe
        }
    }
}).start();
```

---

## 8. What is ExecutorService?

**Answer:**

ExecutorService manages thread pool, avoiding manual thread creation.

**Thread Pool:**
```java
// Create thread pool with 5 threads
ExecutorService executor = Executors.newFixedThreadPool(5);

// Submit tasks
for (int i = 0; i < 10; i++) {
    int taskId = i;
    executor.submit(() -> {
        System.out.println("Task " + taskId + " executed by " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });
}

// Shutdown executor
executor.shutdown();
```

**Types of Thread Pools:**

**1. Fixed Thread Pool:**
```java
ExecutorService executor = Executors.newFixedThreadPool(5);
// Fixed number of threads
```

**2. Cached Thread Pool:**
```java
ExecutorService executor = Executors.newCachedThreadPool();
// Creates threads as needed, reuses idle threads
```

**3. Single Thread Executor:**
```java
ExecutorService executor = Executors.newSingleThreadExecutor();
// Only one thread, executes tasks sequentially
```

**4. Scheduled Thread Pool:**
```java
ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
executor.schedule(() -> {
    System.out.println("Task executed after 5 seconds");
}, 5, TimeUnit.SECONDS);

executor.scheduleAtFixedRate(() -> {
    System.out.println("Task executed every 10 seconds");
}, 0, 10, TimeUnit.SECONDS);
```

---

## 9. What is Callable vs Runnable?

**Answer:**

| Runnable | Callable |
|----------|----------|
| No return value (void) | Returns value |
| Cannot throw checked exception | Can throw exception |

**Runnable:**
```java
Runnable task = () -> {
    System.out.println("Task executed");
    // No return value
};

executor.submit(task);
```

**Callable:**
```java
Callable<Integer> task = () -> {
    Thread.sleep(2000);
    return 42;  // Returns value
};

Future<Integer> future = executor.submit(task);
Integer result = future.get();  // Waits for result
System.out.println("Result: " + result);  // 42
```

**Multiple Callables:**
```java
List<Callable<Integer>> tasks = Arrays.asList(
    () -> { Thread.sleep(1000); return 1; },
    () -> { Thread.sleep(2000); return 2; },
    () -> { Thread.sleep(3000); return 3; }
);

List<Future<Integer>> futures = executor.invokeAll(tasks);

for (Future<Integer> future : futures) {
    System.out.println(future.get());
}
```

---

## 10. What is Future?

**Answer:**

Future represents result of asynchronous computation.

```java
ExecutorService executor = Executors.newFixedThreadPool(2);

Future<Integer> future = executor.submit(() -> {
    System.out.println("Task started");
    Thread.sleep(3000);
    System.out.println("Task completed");
    return 42;
});

System.out.println("Doing other work...");

// Check if task completed
if (future.isDone()) {
    System.out.println("Task completed");
}

// Get result (blocks until task completes)
Integer result = future.get();
System.out.println("Result: " + result);

// Cancel task
future.cancel(true);

executor.shutdown();
```

**Timeout:**
```java
try {
    Integer result = future.get(2, TimeUnit.SECONDS);  // Wait max 2 seconds
} catch (TimeoutException e) {
    System.out.println("Task timed out");
}
```

---

## 11. What is CompletableFuture?

**Answer:**

CompletableFuture is a more powerful alternative to Future, supporting asynchronous callbacks.

**Basic Usage:**
```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    return "Hello";
});

// Non-blocking callback
future.thenAccept(result -> {
    System.out.println("Result: " + result);
});

System.out.println("Doing other work...");
```

**Chaining:**
```java
CompletableFuture.supplyAsync(() -> {
    return "Hello";
})
.thenApply(result -> {
    return result + " World";
})
.thenApply(result -> {
    return result + "!";
})
.thenAccept(result -> {
    System.out.println(result);  // Hello World!
});
```

**Combining Futures:**
```java
CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10);
CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 20);

CompletableFuture<Integer> combined = future1.thenCombine(future2, (a, b) -> a + b);
System.out.println(combined.get());  // 30
```

**Exception Handling:**
```java
CompletableFuture.supplyAsync(() -> {
    if (true) throw new RuntimeException("Error");
    return "Hello";
})
.exceptionally(ex -> {
    System.out.println("Exception: " + ex.getMessage());
    return "Default Value";
})
.thenAccept(result -> {
    System.out.println(result);  // Default Value
});
```

---

## 12. What is ThreadLocal?

**Answer:**

ThreadLocal provides thread-specific variables. Each thread has its own copy.

```java
public class ThreadLocalExample {
    
    private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);
    
    public static void main(String[] args) {
        Runnable task = () -> {
            int value = threadLocal.get();
            System.out.println(Thread.currentThread().getName() + " initial: " + value);
            
            threadLocal.set(value + 1);
            System.out.println(Thread.currentThread().getName() + " updated: " + threadLocal.get());
        };
        
        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        
        t1.start();
        t2.start();
    }
}

// Output:
// Thread-1 initial: 0
// Thread-1 updated: 1
// Thread-2 initial: 0  (separate copy)
// Thread-2 updated: 1
```

**Use Case: User Context:**
```java
public class UserContext {
    private static ThreadLocal<String> currentUser = new ThreadLocal<>();
    
    public static void setCurrentUser(String username) {
        currentUser.set(username);
    }
    
    public static String getCurrentUser() {
        return currentUser.get();
    }
    
    public static void clear() {
        currentUser.remove();  // Important: prevent memory leak
    }
}

// Usage in filter
@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
    String username = request.getParameter("username");
    UserContext.setCurrentUser(username);
    
    try {
        chain.doFilter(request, response);
    } finally {
        UserContext.clear();  // Clean up
    }
}

// Access anywhere
String user = UserContext.getCurrentUser();
```

---

## 13. What is CountDownLatch?

**Answer:**

CountDownLatch makes threads wait until a set of operations complete.

```java
public class CountDownLatchExample {
    
    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 3;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        
        for (int i = 0; i < numberOfThreads; i++) {
            int taskId = i + 1;
            new Thread(() -> {
                System.out.println("Task " + taskId + " started");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Task " + taskId + " completed");
                latch.countDown();  // Decrement count
            }).start();
        }
        
        System.out.println("Waiting for all tasks to complete...");
        latch.await();  // Wait until count reaches 0
        System.out.println("All tasks completed!");
    }
}
```

**Use Case: Service Startup:**
```java
public class Application {
    
    public void start() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        
        new Thread(() -> {
            initializeDatabase();
            latch.countDown();
        }).start();
        
        new Thread(() -> {
            initializeCache();
            latch.countDown();
        }).start();
        
        new Thread(() -> {
            initializeMessaging();
            latch.countDown();
        }).start();
        
        latch.await();  // Wait for all services to start
        System.out.println("Application ready!");
    }
}
```

---

## 14. What is CyclicBarrier?

**Answer:**

CyclicBarrier makes threads wait for each other at a barrier point.

```java
public class CyclicBarrierExample {
    
    public static void main(String[] args) {
        int numberOfThreads = 3;
        CyclicBarrier barrier = new CyclicBarrier(numberOfThreads, () -> {
            System.out.println("All threads reached barrier, continuing...");
        });
        
        for (int i = 0; i < numberOfThreads; i++) {
            int taskId = i + 1;
            new Thread(() -> {
                try {
                    System.out.println("Task " + taskId + " phase 1");
                    Thread.sleep(1000);
                    
                    barrier.await();  // Wait for all threads
                    
                    System.out.println("Task " + taskId + " phase 2");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

// Output:
// Task 1 phase 1
// Task 2 phase 1
// Task 3 phase 1
// All threads reached barrier, continuing...
// Task 1 phase 2
// Task 2 phase 2
// Task 3 phase 2
```

---

## 15. What are Concurrent Collections?

**Answer:**

Thread-safe collections that allow concurrent access without external synchronization.

**ConcurrentHashMap:**
```java
Map<String, Integer> map = new ConcurrentHashMap<>();

// Thread-safe operations
map.put("A", 1);
map.get("A");
map.putIfAbsent("B", 2);  // Atomic operation

// Compute operations
map.computeIfAbsent("C", key -> calculateValue(key));
map.computeIfPresent("A", (key, value) -> value + 1);
```

**CopyOnWriteArrayList:**
```java
List<String> list = new CopyOnWriteArrayList<>();

// Thread-safe
list.add("A");
list.add("B");

// Safe iteration (no ConcurrentModificationException)
for (String item : list) {
    System.out.println(item);
    list.add("C");  // Safe
}
```

**BlockingQueue:**
```java
BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

// Producer
new Thread(() -> {
    try {
        queue.put(1);  // Blocks if queue is full
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}).start();

// Consumer
new Thread(() -> {
    try {
        Integer value = queue.take();  // Blocks if queue is empty
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}).start();
```

**Concurrent Collections:**
- `ConcurrentHashMap` - Thread-safe HashMap
- `CopyOnWriteArrayList` - Thread-safe ArrayList
- `CopyOnWriteArraySet` - Thread-safe Set
- `ConcurrentLinkedQueue` - Thread-safe Queue
- `BlockingQueue` - Queue with blocking operations
