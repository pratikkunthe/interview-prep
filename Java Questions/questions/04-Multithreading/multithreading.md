# Multithreading and Concurrency

## Thread Basics

### 1. Types of multi-threading

**Answer:**

**Multi-threading** = Running multiple threads (tasks) at the same time

Think of it like a restaurant:
- **Single-threaded** = One waiter serves all tables (slow!)
- **Multi-threaded** = Multiple waiters serve different tables (fast!)

**Types of Multi-threading:**

**1. Process-based Multi-threading**
- Each process has its own memory space
- Processes don't share memory
- Like separate restaurants with separate kitchens

**2. Thread-based Multi-threading** (What Java uses)
- Multiple threads share same memory space
- Threads can share data
- Like multiple waiters in same restaurant sharing the kitchen

**In Java, we have:**

**1. User Threads (Non-daemon threads)**
- Main program threads
- JVM waits for these to finish
- Example: Main thread, your application threads

```java
Thread userThread = new Thread(() -> {
    System.out.println("User thread running");
});
userThread.start();  // JVM waits for this
```

**2. Daemon Threads**
- Background threads
- JVM doesn't wait for these
- Example: Garbage collector thread

```java
Thread daemonThread = new Thread(() -> {
    while(true) {
        // Background work
    }
});
daemonThread.setDaemon(true);  // Mark as daemon
daemonThread.start();  // JVM won't wait for this
```

**Simple classification:**

| Type | Description | Example |
|------|-------------|---------|
| User Thread | Main threads, JVM waits | Your application code |
| Daemon Thread | Background, JVM doesn't wait | GC thread, timer threads |

**Key Point:** Multi-threading lets your program do multiple things at once, like a chef cooking multiple dishes simultaneously!

---

### 2. Which is the preferable way to create thread -> ExecutorService

**Answer:**

**ExecutorService** is the BEST and recommended way to create and manage threads!

**Why ExecutorService is better:**

**Old way (NOT recommended):**
```java
// Direct thread creation - problems:
// 1. Creates new thread each time (expensive)
// 2. No thread reuse
// 3. Hard to manage
Thread thread = new Thread(() -> {
    System.out.println("Doing work");
});
thread.start();
```

**New way (RECOMMENDED):**
```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Create thread pool (reuses threads)
ExecutorService executor = Executors.newFixedThreadPool(5);

// Submit tasks
executor.submit(() -> {
    System.out.println("Task 1");
});

executor.submit(() -> {
    System.out.println("Task 2");
});

// Shutdown when done
executor.shutdown();
```

**Benefits of ExecutorService:**

1. **Thread Pool** - Reuses threads (efficient!)
2. **Better Management** - Control number of threads
3. **Task Queue** - Handles task scheduling
4. **Lifecycle Management** - Easy start/stop

**Types of ExecutorService:**

```java
// Fixed thread pool (5 threads)
ExecutorService fixed = Executors.newFixedThreadPool(5);

// Cached thread pool (creates as needed)
ExecutorService cached = Executors.newCachedThreadPool();

// Single thread executor (one thread, sequential)
ExecutorService single = Executors.newSingleThreadExecutor();

// Scheduled executor (for delayed/periodic tasks)
ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(3);
```

**Complete example:**

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceExample {
    public static void main(String[] args) {
        // Create thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // Submit 10 tasks
        for(int i = 1; i <= 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " running on " + 
                    Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        
        // Shutdown (won't accept new tasks)
        executor.shutdown();
        
        try {
            // Wait for tasks to complete (max 60 seconds)
            if(!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();  // Force shutdown
            }
        } catch(InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
```

**Key Points:**
- ✅ Use ExecutorService (thread pool)
- ❌ Don't create Thread directly
- ✅ Reuses threads (efficient)
- ✅ Better resource management

**Simple rule:** Always use ExecutorService - it's like having a smart manager who efficiently assigns workers to tasks!

---

## Thread Synchronization

### 1. Can two threads call two different objects synchronously

**Answer:**

**Yes!** Two threads can call synchronized methods on DIFFERENT objects at the same time.

**Key concept:**
- `synchronized` locks the **object**, not the method
- Each object has its own lock
- Different objects = different locks = can run simultaneously

**Example:**

```java
class Counter {
    private int count = 0;
    
    public synchronized void increment() {
        count++;
        System.out.println(Thread.currentThread().getName() + 
            " incremented to " + count);
    }
}

public class SynchronizedExample {
    public static void main(String[] args) {
        // Two different objects
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();
        
        // Thread 1 uses counter1
        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 5; i++) {
                counter1.increment();  // Locks counter1
                try { Thread.sleep(100); } catch(Exception e) {}
            }
        });
        
        // Thread 2 uses counter2
        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 5; i++) {
                counter2.increment();  // Locks counter2 (different lock!)
                try { Thread.sleep(100); } catch(Exception e) {}
            }
        });
        
        t1.start();
        t2.start();
        
        // Both threads run SIMULTANEOUSLY because they use different objects!
    }
}
```

**What happens:**
- Thread 1 locks `counter1` object
- Thread 2 locks `counter2` object (different lock!)
- They run at the same time ✅

**If same object:**

```java
Counter counter = new Counter();  // Same object

Thread t1 = new Thread(() -> counter.increment());
Thread t2 = new Thread(() -> counter.increment());

t1.start();
t2.start();

// Now threads WAIT for each other because same object = same lock!
```

**Key Points:**

| Scenario | Result |
|----------|--------|
| Different objects | ✅ Can run simultaneously |
| Same object | ❌ One waits for other |
| Static synchronized | ❌ All wait (class-level lock) |

**Simple analogy:** 
- Different objects = Different keys = Can open different doors at same time
- Same object = Same key = Must wait for key to be available

---

### 2. Difference between Volatile and synchronized

**Answer:**

**Volatile** = Makes variable visible to all threads (visibility guarantee)
**Synchronized** = Makes code thread-safe (visibility + atomicity)

**Simple analogy:**
- **Volatile** = Public notice board (everyone sees updates immediately)
- **Synchronized** = Single-person bathroom (only one person at a time)

**Volatile:**

```java
class SharedData {
    volatile boolean flag = false;  // All threads see this immediately
    
    void setFlag() {
        flag = true;  // Change visible to all threads
    }
    
    void checkFlag() {
        while(!flag) {
            // Will see the change from other thread
        }
    }
}
```

**Limitations of Volatile:**
- ✅ Ensures visibility
- ❌ Does NOT ensure atomicity
- ❌ Not safe for compound operations

```java
volatile int count = 0;

// NOT SAFE! Multiple threads can cause issues
count++;  // This is: read → increment → write (3 steps, not atomic!)
```

**Synchronized:**

```java
class Counter {
    private int count = 0;
    
    synchronized void increment() {
        count++;  // Safe! Only one thread at a time
    }
    
    synchronized int getCount() {
        return count;  // Safe read
    }
}
```

**Benefits of Synchronized:**
- ✅ Ensures visibility
- ✅ Ensures atomicity
- ✅ Safe for compound operations

**Comparison:**

| Feature | Volatile | Synchronized |
|--------|----------|--------------|
| Visibility | ✅ Yes | ✅ Yes |
| Atomicity | ❌ No | ✅ Yes |
| Performance | ⚡ Fast | 🐌 Slower |
| Use case | Simple flags | Complex operations |
| Lock | No lock | Object/class lock |

**When to use what:**

**Use Volatile when:**
- Simple flag/status variable
- Only one thread writes, others read
- No compound operations

```java
volatile boolean running = true;  // Perfect use case!

// Thread 1
running = false;  // Signal to stop

// Thread 2
while(running) {
    // Do work
}
```

**Use Synchronized when:**
- Multiple threads read/write
- Compound operations (like count++)
- Need atomicity

```java
synchronized void transfer(Account from, Account to, int amount) {
    from.balance -= amount;  // Must be atomic!
    to.balance += amount;
}
```

**Key Point:** Volatile = "Everyone sees the latest value", Synchronized = "Only one person can change it at a time"

---

## Thread Interfaces

### 1. Callable and Runnable interface

**Answer:**

Both are used to define tasks for threads, but with differences:

**Runnable** = Task that doesn't return value (void)
**Callable** = Task that returns value (can throw exception)

**Runnable:**

```java
@FunctionalInterface
interface Runnable {
    void run();  // No return value, no exception
}

// Usage
Runnable task = () -> {
    System.out.println("Doing work");
    // No return value
};

Thread thread = new Thread(task);
thread.start();
```

**Callable:**

```java
@FunctionalInterface
interface Callable<V> {
    V call() throws Exception;  // Returns value, can throw exception
}

// Usage
Callable<String> task = () -> {
    System.out.println("Doing work");
    return "Result";  // Returns value
};

// Must use ExecutorService (not Thread directly)
ExecutorService executor = Executors.newSingleThreadExecutor();
Future<String> future = executor.submit(task);
String result = future.get();  // Get the result
```

**Key Differences:**

| Feature | Runnable | Callable |
|---------|----------|----------|
| Return value | ❌ No (void) | ✅ Yes |
| Exception | ❌ Cannot throw | ✅ Can throw |
| Use with | Thread, ExecutorService | ExecutorService only |
| Get result | ❌ No way | ✅ Future.get() |

**Complete example:**

```java
import java.util.concurrent.*;

public class RunnableVsCallable {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        // Runnable - no return value
        Runnable runnableTask = () -> {
            System.out.println("Runnable task executing");
            // No return
        };
        
        Future<?> future1 = executor.submit(runnableTask);
        future1.get();  // Waits for completion, returns null
        
        // Callable - returns value
        Callable<String> callableTask = () -> {
            System.out.println("Callable task executing");
            Thread.sleep(1000);
            return "Task completed!";  // Returns value
        };
        
        Future<String> future2 = executor.submit(callableTask);
        String result = future2.get();  // Gets the result
        System.out.println("Result: " + result);
        
        executor.shutdown();
    }
}
```

**When to use:**

**Use Runnable when:**
- Task doesn't need to return value
- Simple fire-and-forget tasks
- Using with Thread class

**Use Callable when:**
- Need return value from task
- Need to handle exceptions
- Using with ExecutorService
- Need Future to check status/get result

**Simple rule:** Runnable = "Do this", Callable = "Do this and tell me the result"

---

### 2. What is the difference between Callable and Runnable?

**Answer:**

**Runnable** = Task without return value
**Callable** = Task with return value

**Quick comparison:**

```java
// Runnable - void, no exception
Runnable r = () -> {
    System.out.println("Hello");
    // No return
};

// Callable - returns value, can throw exception
Callable<String> c = () -> {
    System.out.println("Hello");
    return "Done";  // Returns value
};
```

**Main differences:**

1. **Return value**
   - Runnable: `void run()` - nothing returned
   - Callable: `V call()` - returns value

2. **Exception handling**
   - Runnable: Cannot throw checked exception
   - Callable: Can throw checked exception

3. **Usage**
   - Runnable: Can use with `Thread` or `ExecutorService`
   - Callable: Only with `ExecutorService` (returns `Future`)

**Example:**

```java
ExecutorService executor = Executors.newSingleThreadExecutor();

// Runnable
executor.submit(() -> System.out.println("Task done"));

// Callable - get result
Future<Integer> future = executor.submit(() -> {
    return 42;  // Return value
});
int result = future.get();  // 42
```

**Simple analogy:**
- Runnable = Worker who just does the job (no report)
- Callable = Worker who does the job and reports back with result

---

## Executor Framework

### 1. Executor and ExecutorService

**Answer:**

**Executor** = Simple interface to execute tasks
**ExecutorService** = Extended Executor with more features (lifecycle, Future, etc.)

**Executor (Simple):**

```java
interface Executor {
    void execute(Runnable command);  // Just execute, no return
}

Executor executor = Executors.newFixedThreadPool(5);
executor.execute(() -> System.out.println("Task"));
// No way to track or get result
```

**ExecutorService (Advanced):**

```java
interface ExecutorService extends Executor {
    Future<?> submit(Runnable task);           // Submit and get Future
    <T> Future<T> submit(Callable<T> task);   // Submit Callable
    void shutdown();                           // Graceful shutdown
    List<Runnable> shutdownNow();             // Force shutdown
    boolean isShutdown();                      // Check status
    // ... more methods
}

ExecutorService executor = Executors.newFixedThreadPool(5);

// Can submit and get Future
Future<String> future = executor.submit(() -> "Result");

// Can shutdown properly
executor.shutdown();
```

**Key Differences:**

| Feature | Executor | ExecutorService |
|---------|----------|-----------------|
| Submit tasks | ✅ execute() | ✅ execute(), submit() |
| Get result | ❌ No | ✅ Future |
| Callable support | ❌ No | ✅ Yes |
| Shutdown | ❌ No | ✅ Yes |
| Lifecycle management | ❌ No | ✅ Yes |

**When to use:**

**Use Executor when:**
- Simple task execution
- Don't need result tracking
- Don't need lifecycle management

**Use ExecutorService when:**
- Need Future to track tasks
- Need to submit Callable
- Need proper shutdown
- Need lifecycle management

**Example:**

```java
// ExecutorService (recommended)
ExecutorService executor = Executors.newFixedThreadPool(3);

// Submit Runnable
Future<?> f1 = executor.submit(() -> System.out.println("Task 1"));

// Submit Callable
Future<String> f2 = executor.submit(() -> "Result");

// Get result
String result = f2.get();

// Proper shutdown
executor.shutdown();
try {
    if(!executor.awaitTermination(60, TimeUnit.SECONDS)) {
        executor.shutdownNow();
    }
} catch(InterruptedException e) {
    executor.shutdownNow();
}
```

**Simple rule:** ExecutorService is like Executor with superpowers - use it for production code!

---

### 2. Explain Thread Pool and Executor Framework

**Answer:**

**Thread Pool** = Collection of reusable worker threads
**Executor Framework** = Framework to manage thread pools and execute tasks

**Think of it like:**
- **Thread Pool** = Team of workers ready to do jobs
- **Executor Framework** = Manager who assigns jobs to workers

**Why Thread Pool?**

**Without Thread Pool (Bad):**
```java
// Creates new thread for each task - EXPENSIVE!
for(int i = 0; i < 1000; i++) {
    Thread thread = new Thread(() -> doWork());
    thread.start();  // Creates 1000 threads! Wasteful!
}
```

**With Thread Pool (Good):**
```java
// Reuses threads - EFFICIENT!
ExecutorService pool = Executors.newFixedThreadPool(5);
for(int i = 0; i < 1000; i++) {
    pool.submit(() -> doWork());  // Reuses 5 threads!
}
```

**How Thread Pool works:**

```
Task Queue → Thread Pool (5 threads)
   ↓              ↓
Task 1    →    Thread 1 (working)
Task 2    →    Thread 2 (working)
Task 3    →    Thread 3 (working)
Task 4    →    Thread 4 (working)
Task 5    →    Thread 5 (working)
Task 6    →    Waiting in queue...
Task 7    →    Waiting in queue...
```

**Executor Framework Components:**

1. **Executor** - Execute tasks
2. **ExecutorService** - Extended executor with lifecycle
3. **ThreadPoolExecutor** - Actual thread pool implementation
4. **Executors** - Factory to create thread pools
5. **Future** - Represents async result

**Types of Thread Pools:**

```java
// 1. Fixed Thread Pool (fixed number of threads)
ExecutorService fixed = Executors.newFixedThreadPool(5);

// 2. Cached Thread Pool (creates as needed, reuses)
ExecutorService cached = Executors.newCachedThreadPool();

// 3. Single Thread Executor (one thread, sequential)
ExecutorService single = Executors.newSingleThreadExecutor();

// 4. Scheduled Thread Pool (for delayed/periodic tasks)
ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(3);
```

**Complete example:**

```java
import java.util.concurrent.*;

public class ThreadPoolExample {
    public static void main(String[] args) {
        // Create thread pool with 3 threads
        ExecutorService pool = Executors.newFixedThreadPool(3);
        
        // Submit 10 tasks
        for(int i = 1; i <= 10; i++) {
            final int taskId = i;
            pool.submit(() -> {
                System.out.println("Task " + taskId + 
                    " executed by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        
        // Shutdown
        pool.shutdown();
        
        try {
            // Wait for all tasks to complete
            if(!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch(InterruptedException e) {
            pool.shutdownNow();
        }
        
        System.out.println("All tasks completed");
    }
}
```

**Benefits:**
- ✅ Reuses threads (efficient)
- ✅ Controls resource usage
- ✅ Better performance
- ✅ Easy task management

**Key Points:**
- Thread Pool = Reusable worker threads
- Executor Framework = Management system
- Always use ExecutorService instead of creating threads directly!

---

### 3. ThreadPool

**Answer:**

**Thread Pool** = Collection of pre-created, reusable threads

**Why use Thread Pool?**

**Problem without Thread Pool:**
- Creating thread is expensive (time + memory)
- Too many threads = system overload
- No control over resource usage

**Solution: Thread Pool**
- Reuse threads (efficient!)
- Control number of threads
- Better resource management

**How it works:**

```
┌─────────────┐
│  Task Queue │
└──────┬──────┘
       │
       ↓
┌──────────────────┐
│  Thread Pool     │
│  ┌────────────┐  │
│  │ Thread 1  │  │ ← Takes task from queue
│  ├────────────┤  │
│  │ Thread 2  │  │ ← Takes task from queue
│  ├────────────┤  │
│  │ Thread 3  │  │ ← Takes task from queue
│  └────────────┘  │
└──────────────────┘
```

**Creating Thread Pool:**

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Fixed size pool (5 threads)
ExecutorService pool = Executors.newFixedThreadPool(5);

// Submit tasks
for(int i = 0; i < 100; i++) {
    final int taskId = i;
    pool.submit(() -> {
        System.out.println("Task " + taskId + " running");
        // Do work
    });
}

// Shutdown when done
pool.shutdown();
```

**Types:**

1. **Fixed Thread Pool** - Fixed number of threads
```java
ExecutorService pool = Executors.newFixedThreadPool(5);
```

2. **Cached Thread Pool** - Creates threads as needed
```java
ExecutorService pool = Executors.newCachedThreadPool();
```

3. **Single Thread Pool** - One thread (sequential)
```java
ExecutorService pool = Executors.newSingleThreadExecutor();
```

**Benefits:**
- ✅ Efficient (reuses threads)
- ✅ Controlled resource usage
- ✅ Better performance
- ✅ Easy to manage

**Simple analogy:** Thread Pool = Taxi company with fixed number of taxis. Tasks = Customers. Instead of buying new taxi for each customer, reuse existing taxis!

---

## Thread Methods

### 1. invoke and invokeAll

**Answer:**

Both are methods in `ExecutorService` to execute multiple tasks:

**invokeAll()** = Execute all tasks, wait for ALL to complete
**invokeAny()** = Execute all tasks, return FIRST completed result

**invokeAll() example:**

```java
import java.util.concurrent.*;
import java.util.*;

public class InvokeAllExample {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        List<Callable<String>> tasks = Arrays.asList(
            () -> {
                Thread.sleep(2000);
                return "Task 1 completed";
            },
            () -> {
                Thread.sleep(1000);
                return "Task 2 completed";
            },
            () -> {
                Thread.sleep(3000);
                return "Task 3 completed";
            }
        );
        
        // Execute all, wait for ALL to complete
        List<Future<String>> futures = executor.invokeAll(tasks);
        
        // Get results (all tasks are done by now)
        for(Future<String> future : futures) {
            System.out.println(future.get());
        }
        
        executor.shutdown();
    }
}
// Output (waits for all 3 tasks):
// Task 1 completed
// Task 2 completed
// Task 3 completed
```

**invokeAny() example:**

```java
import java.util.concurrent.*;
import java.util.*;

public class InvokeAnyExample {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        List<Callable<String>> tasks = Arrays.asList(
            () -> {
                Thread.sleep(2000);
                return "Task 1";
            },
            () -> {
                Thread.sleep(1000);
                return "Task 2";  // Fastest!
            },
            () -> {
                Thread.sleep(3000);
                return "Task 3";
            }
        );
        
        // Execute all, return FIRST completed result
        String result = executor.invokeAny(tasks);
        
        System.out.println("First completed: " + result);
        // Output: First completed: Task 2
        
        executor.shutdown();
    }
}
```

**Key Differences:**

| Method | Behavior | Returns | Use Case |
|--------|----------|---------|----------|
| invokeAll() | Waits for ALL tasks | List<Future> | Need all results |
| invokeAny() | Returns FIRST result | Single result | Need fastest result |

**When to use:**

**Use invokeAll() when:**
- Need results from all tasks
- Can wait for slowest task
- Processing batch of tasks

**Use invokeAny() when:**
- Need fastest result
- Multiple ways to get same result
- Race condition (first wins)

**Real-world example:**

```java
// invokeAny - Get data from fastest server
List<Callable<String>> servers = Arrays.asList(
    () -> fetchFromServer1(),  // Slow
    () -> fetchFromServer2(),  // Fast
    () -> fetchFromServer3()   // Medium
);
String data = executor.invokeAny(servers);  // Gets from fastest!

// invokeAll - Process all files
List<Callable<String>> fileProcessors = Arrays.asList(
    () -> processFile("file1.txt"),
    () -> processFile("file2.txt"),
    () -> processFile("file3.txt")
);
List<Future<String>> results = executor.invokeAll(fileProcessors);
// All files processed!
```

**Key Points:**
- invokeAll = "Wait for everyone"
- invokeAny = "First one wins"

---

### 2. One example of invoke

**Answer:**

Here's a practical example using `invokeAny()`:

**Scenario:** Fetch user data from multiple sources, return fastest result

```java
import java.util.concurrent.*;
import java.util.*;

public class InvokeExample {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // Simulate fetching from 3 different sources
        List<Callable<String>> dataSources = Arrays.asList(
            () -> {
                System.out.println("Fetching from Database...");
                Thread.sleep(3000);  // Slow database
                return "Data from Database";
            },
            () -> {
                System.out.println("Fetching from Cache...");
                Thread.sleep(500);  // Fast cache
                return "Data from Cache";
            },
            () -> {
                System.out.println("Fetching from API...");
                Thread.sleep(2000);  // Medium API
                return "Data from API";
            }
        );
        
        // Get fastest result
        String result = executor.invokeAny(dataSources);
        
        System.out.println("Result: " + result);
        // Output: Result: Data from Cache (fastest!)
        
        executor.shutdown();
    }
}
```

**Another example with invokeAll():**

```java
// Process multiple files in parallel
List<Callable<String>> fileTasks = Arrays.asList(
    () -> {
        System.out.println("Processing file1.txt");
        Thread.sleep(1000);
        return "file1.txt processed";
    },
    () -> {
        System.out.println("Processing file2.txt");
        Thread.sleep(1500);
        return "file2.txt processed";
    },
    () -> {
        System.out.println("Processing file3.txt");
        Thread.sleep(800);
        return "file3.txt processed";
    }
);

// Process all files, wait for all
List<Future<String>> results = executor.invokeAll(fileTasks);

// All files are processed now
for(Future<String> future : results) {
    System.out.println(future.get());
}
```

**Key Point:** `invokeAny()` is perfect when you have multiple ways to get the same data and want the fastest one!

---

### 3. Explain Notify

**Answer:**

**notify()** = Wakes up ONE waiting thread (part of wait/notify mechanism)

**Wait/Notify mechanism:**
- Used for thread communication
- Threads wait for condition, other threads notify when condition changes

**How it works:**

```java
class SharedResource {
    private boolean available = false;
    
    public synchronized void produce() {
        // Produce item
        available = true;
        System.out.println("Item produced");
        notify();  // Wake up ONE waiting thread
        // notifyAll() would wake up ALL waiting threads
    }
    
    public synchronized void consume() throws InterruptedException {
        while(!available) {
            System.out.println("Waiting for item...");
            wait();  // Release lock and wait
        }
        // Item is available now
        available = false;
        System.out.println("Item consumed");
    }
}
```

**Complete example:**

```java
public class WaitNotifyExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();
        
        // Consumer thread (waits)
        Thread consumer = new Thread(() -> {
            try {
                resource.consume();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        // Producer thread (notifies)
        Thread producer = new Thread(() -> {
            try {
                Thread.sleep(2000);  // Simulate work
                resource.produce();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        consumer.start();
        producer.start();
    }
}

class SharedResource {
    private boolean itemReady = false;
    
    public synchronized void produce() {
        itemReady = true;
        System.out.println("Producer: Item ready!");
        notify();  // Wake up waiting consumer
    }
    
    public synchronized void consume() throws InterruptedException {
        while(!itemReady) {
            System.out.println("Consumer: Waiting for item...");
            wait();  // Wait until notified
        }
        System.out.println("Consumer: Got the item!");
        itemReady = false;
    }
}
```

**notify() vs notifyAll():**

| Method | Behavior | Use When |
|--------|----------|----------|
| notify() | Wakes ONE thread | Only one thread can proceed |
| notifyAll() | Wakes ALL threads | Multiple threads can proceed |

**Important rules:**
1. Must be in `synchronized` block/method
2. Thread calling `wait()` releases the lock
3. `notify()` wakes ONE waiting thread
4. Always use `wait()` in a loop (spurious wakeups)

**Simple analogy:**
- `wait()` = "I'm waiting here, wake me up when ready"
- `notify()` = "Hey, wake up! Something changed"
- Like a doorbell - you wait, someone rings (notifies) when they arrive

**Key Point:** notify() is like a wake-up call - it tells ONE waiting thread "Hey, check your condition again!"
