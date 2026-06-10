# Java Multithreading

## 83. What is process?

A **process** is a program in execution. It has its own memory space and resources.

```
Process = Program in Execution

Example:
- Opening MS Word = One process
- Opening Chrome = Another process
- Opening Calculator = Another process
```

**Characteristics:**
- Independent memory space
- Cannot share data directly with other processes
- Heavy weight
- Creating process is expensive

**Diagram:**
```
RAM Memory
├── Process 1 (Chrome)
│   ├── Code
│   ├── Data
│   └── Resources
│
├── Process 2 (Word)
│   ├── Code
│   ├── Data
│   └── Resources
```

---

## 84. What is thread in Java?

A **thread** is a lightweight subprocess, the smallest unit of execution within a process.

```java
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread is running");
    }
}

public class Test {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();  // Start the thread
    }
}
```

**Characteristics:**
- Shares memory with other threads in same process
- Lightweight
- Can communicate easily
- Multiple threads can run simultaneously

**Diagram:**
```
Process
    ├── Thread 1
    ├── Thread 2
    └── Thread 3
    (All share same memory)
```

---

## 85. Difference between process and thread

| Process | Thread |
|---------|--------|
| Program in execution | Lightweight subprocess |
| Independent memory | Shares memory with other threads |
| Heavy weight | Lightweight |
| Expensive to create | Cheap to create |
| Cannot share data easily | Can share data easily |
| Context switching is slow | Context switching is fast |
| Example: Chrome, Word | Example: Multiple tabs in Chrome |

**Visual:**
```
Process (Chrome)
    ├── Thread 1 (Tab 1 - Gmail)
    ├── Thread 2 (Tab 2 - YouTube)
    └── Thread 3 (Tab 3 - Google)
```

---

## 86. What is multitasking?

**Multitasking** is executing multiple tasks simultaneously.

**Example from daily life:**
- Listening to music while jogging
- Talking on phone while cooking
- Reading while having coffee

**In computers:**
```
Computer running:
- Chrome browser
- MS Word
- Music player
- Antivirus
All at the same time = Multitasking
```

**Benefits:**
- Better CPU utilization
- Saves time
- User can do multiple things

---

## 87. What are different types of multitasking?

**1. Process-based Multitasking**
- Running multiple processes simultaneously
- Each process has separate memory
- Example: Running Chrome, Word, Excel together

```
Process-based Multitasking
├── Process 1: Chrome
├── Process 2: Word
└── Process 3: Excel
```

**2. Thread-based Multitasking**
- Running multiple threads within a process
- Threads share same memory
- Example: Multiple tabs in browser

```
Thread-based Multitasking (Chrome)
├── Thread 1: Tab 1
├── Thread 2: Tab 2
└── Thread 3: Downloads
```

**Comparison:**

| Process-based | Thread-based |
|--------------|--------------|
| Heavy | Lightweight |
| Separate memory | Shared memory |
| Example: Multiple applications | Example: Multiple threads in one app |

---

## 88. What are the benefits of multithreaded programming?

**1. Better Performance**
```java
// Without threads - Sequential (slow)
downloadFile1();  // Takes 5 seconds
downloadFile2();  // Takes 5 seconds
// Total: 10 seconds

// With threads - Parallel (fast)
Thread t1 = new Thread(() -> downloadFile1());
Thread t2 = new Thread(() -> downloadFile2());
t1.start();
t2.start();
// Total: 5 seconds (both download simultaneously)
```

**2. Better CPU Utilization**
- While one thread waits for I/O, other threads can use CPU

**3. Responsive Applications**
- UI remains responsive while background tasks run
```java
// UI Thread - keeps interface responsive
// Background Thread - downloads file
```

**4. Resource Sharing**
- Threads share memory, easy to share data

**5. Cost Effective**
- Creating threads is cheaper than creating processes

---

## 89. Explain thread in Java

A **thread** in Java is created using:
1. Extending Thread class
2. Implementing Runnable interface

**Thread lifecycle:**
```
New → Runnable → Running → Dead
         ↕
      Waiting/Blocked
```

**Example:**
```java
class MyThread extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }
}

public class Test {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        t1.start();
        t2.start();
    }
}
```

---

## 90. List Java API that supports threads

**Key packages and classes:**

**1. java.lang.Thread** - Main thread class
```java
Thread t = new Thread();
t.start();
t.sleep(1000);
t.join();
```

**2. java.lang.Runnable** - Functional interface
```java
Runnable r = () -> System.out.println("Running");
```

**3. java.util.concurrent package**
- ExecutorService
- Future
- Callable
- ThreadPoolExecutor
- CountDownLatch
- Semaphore
- ConcurrentHashMap

**4. Synchronization**
- synchronized keyword
- Lock interface
- ReentrantLock

---

## 91. Explain about main thread in Java

**main thread** is created automatically by JVM when program starts. It's the first thread to execute.

```java
public class Test {
    public static void main(String[] args) {
        // This code runs in main thread
        Thread t = Thread.currentThread();
        System.out.println("Thread name: " + t.getName());      // main
        System.out.println("Thread priority: " + t.getPriority()); // 5
        System.out.println("Is alive: " + t.isAlive());        // true
    }
}
```

**Output:**
```
Thread name: main
Thread priority: 5
Is alive: true
```

**Key points:**
- Entry point of Java program
- Child threads are created from main thread
- Usually last to finish (can be changed)

---

## 92. In how many ways we can create threads in Java?

**Two ways:**

**1. Extending Thread class**
```java
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread running");
    }
}

public class Test {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();
    }
}
```

**2. Implementing Runnable interface**
```java
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Thread running");
    }
}

public class Test {
    public static void main(String[] args) {
        MyRunnable r = new MyRunnable();
        Thread t = new Thread(r);
        t.start();
    }
}
```

---

## 93. Explain creating threads by implementing Runnable class

**Runnable** is an interface with a single method `run()`.

```java
// Step 1: Implement Runnable interface
class MyTask implements Runnable {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }
}

// Step 2: Create Thread object and pass Runnable
public class Test {
    public static void main(String[] args) {
        MyTask task = new MyTask();
        
        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        
        t1.start();
        t2.start();
    }
}
```

**Lambda expression (Java 8+):**
```java
Thread t = new Thread(() -> {
    System.out.println("Thread running");
});
t.start();
```

---

## 94. Explain creating threads by extending Thread class

**Thread class** has a `run()` method that we override.

```java
// Step 1: Extend Thread class
class MyThread extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(getName() + ": " + i);
        }
    }
}

// Step 2: Create object and call start()
public class Test {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        
        t1.setName("Thread-1");
        t2.setName("Thread-2");
        
        t1.start();
        t2.start();
    }
}
```

**Output:**
```
Thread-1: 1
Thread-2: 1
Thread-1: 2
Thread-2: 2
...
```

---

## 95. Which is the best approach for creating thread?

**Implementing Runnable interface** is better.

**Reasons:**

**1. Java supports single inheritance**
```java
// Problem with extending Thread
class MyThread extends Thread {
    // Cannot extend another class
}

// Solution with Runnable
class MyTask extends SomeClass implements Runnable {
    // Can extend another class
}
```

**2. Separation of concerns**
```java
// Task and thread are separate
class Task implements Runnable {
    public void run() {
        // Task logic
    }
}

Thread t = new Thread(new Task());
```

**3. Object-oriented design**
- Thread represents a worker
- Runnable represents a task
- Better design: worker executes task

**Recommendation:** Use **Runnable** interface (or Callable for better features)

---

## 96. Explain the importance of thread scheduler in Java

**Thread scheduler** is part of JVM that decides which thread should run.

**How it works:**
```
Multiple Threads (Runnable state)
         ↓
   Thread Scheduler
         ↓
Selects one thread based on:
- Priority
- Time slicing
- Algorithm (varies by JVM)
```

**Example:**
```java
Thread t1 = new Thread(() -> {
    System.out.println("Thread 1");
});

Thread t2 = new Thread(() -> {
    System.out.println("Thread 2");
});

t1.start();
t2.start();

// Thread scheduler decides which runs first
// Output can be:
// Thread 1
// Thread 2
// OR
// Thread 2
// Thread 1
```

**Key Points:**
- We cannot control scheduler directly
- Uses priority and time slicing
- Platform dependent

---

## 97. Explain the life cycle of thread

**Thread States:**

```
1. New
   ↓ (start())
2. Runnable
   ↓ (scheduler)
3. Running
   ↓ (sleep/wait/block)
4. Waiting/Blocked
   ↓ (notify/time up)
   Back to Runnable
   ↓ (run() completes)
5. Dead
```

**Detailed Example:**
```java
Thread t = new Thread(() -> {
    System.out.println("Running");
});

// 1. New - Thread created but not started
System.out.println(t.getState());  // NEW

// 2. Runnable - Thread started, ready to run
t.start();
System.out.println(t.getState());  // RUNNABLE

// 3. Running - Executing run() method
// (No separate state in Java, part of RUNNABLE)

// 4. Waiting - Thread waiting for something
Thread.sleep(1000);  // TIMED_WAITING

// 5. Dead - Thread finished execution
// (After run() completes)
```

---

## 98. Can we restart a dead thread in Java?

**No**, once a thread is dead, it cannot be restarted.

```java
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread running");
    }
}

public class Test {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        
        t.start();  // First time - OK
        
        // Wait for thread to complete
        try {
            t.join();
        } catch (InterruptedException e) { }
        
        t.start();  // Error! IllegalThreadStateException
    }
}
```

**Solution:** Create a new thread
```java
MyThread t1 = new MyThread();
t1.start();

MyThread t2 = new MyThread();  // New thread
t2.start();
```

---

## 99. Can one thread block the other thread?

**Yes**, using synchronization or locks.

```java
class Counter {
    private int count = 0;
    
    // Synchronized method - only one thread at a time
    public synchronized void increment() {
        count++;
    }
}

public class Test {
    public static void main(String[] args) {
        Counter counter = new Counter();
        
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();  // Thread 1 enters
            }
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();  // Thread 2 waits (blocked)
            }
        });
        
        t1.start();
        t2.start();
    }
}
```

**When t1 is executing `increment()`, t2 is blocked until t1 finishes.**

---

## 100. Can we restart a thread already started in Java?

**No**, calling `start()` again throws `IllegalThreadStateException`.

```java
Thread t = new Thread(() -> System.out.println("Running"));

t.start();  // OK
t.start();  // Exception! IllegalThreadStateException
```

**Solution:** Create new thread instance
```java
Thread t1 = new Thread(() -> System.out.println("Running"));
t1.start();

Thread t2 = new Thread(() -> System.out.println("Running"));
t2.start();
```

---

## 101. What happens if we don't override run method?

**Nothing will happen.** The thread will start and finish immediately without doing any work.

```java
class MyThread extends Thread {
    // No run() method override
}

public class Test {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();  // Thread starts and immediately finishes
        System.out.println("Main thread");
    }
}
```

**Output:** `Main thread` (MyThread does nothing)

**With run() override:**
```java
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread working");
    }
}
```

**Output:**
```
Thread working
Main thread
```

---

## 102. Can we overload run() method in Java?

**Yes**, but only the no-argument `run()` will be called by the thread.

```java
class MyThread extends Thread {
    // This is called by thread
    public void run() {
        System.out.println("run() with no arguments");
    }
    
    // Overloaded - but not called by thread automatically
    public void run(String message) {
        System.out.println("run(String): " + message);
    }
}

public class Test {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();  // Calls run() with no arguments
        
        t.run("Hello");  // Can call manually, but runs in main thread
    }
}
```

**Output:**
```
run() with no arguments
run(String): Hello
```

---

## 105. What is a lock or purpose of locks in Java?

**Lock** is a mechanism to control access to shared resources by multiple threads.

**Purpose:** Prevent multiple threads from accessing the same resource simultaneously (thread safety).

```java
class BankAccount {
    private int balance = 1000;
    
    // synchronized = lock
    public synchronized void withdraw(int amount) {
        if (balance >= amount) {
            System.out.println(Thread.currentThread().getName() + " withdrawing " + amount);
            balance -= amount;
            System.out.println("Balance: " + balance);
        }
    }
}
```

**How lock works:**
```
Thread 1 enters synchronized method
    ↓
Acquires lock on object
    ↓
Other threads wait (blocked)
    ↓
Thread 1 finishes and releases lock
    ↓
Next thread acquires lock
```

**Real-world example:** ATM machine - only one person can use at a time

---

## 106. In how many ways we can do synchronization in Java?

**Three ways:**

**1. Synchronized Method**
```java
class Counter {
    private int count = 0;
    
    public synchronized void increment() {
        count++;
    }
}
```

**2. Synchronized Block**
```java
class Counter {
    private int count = 0;
    
    public void increment() {
        synchronized(this) {
            count++;
        }
    }
}
```

**3. Static Synchronization (Class level)**
```java
class Counter {
    private static int count = 0;
    
    public static synchronized void increment() {
        count++;
    }
}
```

---

## 107. What are synchronized methods?

**Synchronized method** allows only one thread to execute it at a time.

```java
class Counter {
    private int count = 0;
    
    // Synchronized method
    public synchronized void increment() {
        count++;
    }
    
    public synchronized void decrement() {
        count--;
    }
    
    public int getCount() {
        return count;
    }
}

public class Test {
    public static void main(String[] args) throws Exception {
        Counter counter = new Counter();
        
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        System.out.println("Count: " + counter.getCount());  // 2000
    }
}
```

---

## 108. When do we use synchronized methods in Java?

Use synchronized methods when **multiple threads access shared data** and at least one modifies it.

**Scenarios:**

**1. Banking transactions**
```java
synchronized void withdraw(int amount) {
    balance -= amount;
}
```

**2. Counter increments**
```java
synchronized void increment() {
    count++;
}
```

**3. Shared collections**
```java
synchronized void add(Object item) {
    list.add(item);
}
```

**Don't use when:**
- Thread doesn't modify data (read-only)
- Data is local to thread
- No shared data

---

## 109. When a thread is executing synchronized method, can other threads execute other synchronized methods simultaneously?

**No**, when one thread executes a synchronized method, other threads cannot execute ANY synchronized method on that object.

```java
class Test {
    public synchronized void method1() {
        System.out.println(Thread.currentThread().getName() + " in method1");
        try { Thread.sleep(2000); } catch (Exception e) { }
    }
    
    public synchronized void method2() {
        System.out.println(Thread.currentThread().getName() + " in method2");
    }
}

public class Main {
    public static void main(String[] args) {
        Test obj = new Test();
        
        Thread t1 = new Thread(() -> obj.method1());
        Thread t2 = new Thread(() -> obj.method2());
        
        t1.start();
        t2.start();
        
        // t1 executes method1
        // t2 WAITS (cannot execute method2 until t1 releases lock)
    }
}
```

**Output:**
```
Thread-0 in method1
(2 seconds delay)
Thread-1 in method2
```

---

## 110. When a thread is executing a synchronized method, can the same thread access other synchronized methods of the same object?

**Yes**, the same thread can access other synchronized methods. This is called **reentrant synchronization**.

```java
class Test {
    public synchronized void method1() {
        System.out.println("Method1");
        method2();  // Same thread can call method2
    }
    
    public synchronized void method2() {
        System.out.println("Method2");
    }
}

public class Main {
    public static void main(String[] args) {
        Test obj = new Test();
        Thread t = new Thread(() -> obj.method1());
        t.start();
    }
}
```

**Output:**
```
Method1
Method2
```

**Why?** The thread already holds the lock, so it can enter other synchronized methods.

---

## 111. What are synchronized blocks in Java?

**Synchronized block** synchronizes only a specific section of code instead of entire method.

```java
class Counter {
    private int count = 0;
    
    public void increment() {
        // Some code (not synchronized)
        System.out.println("Before sync block");
        
        // Only this part is synchronized
        synchronized(this) {
            count++;
        }
        
        // Some code (not synchronized)
        System.out.println("After sync block");
    }
}
```

**Syntax:**
```java
synchronized(object) {
    // Code to be synchronized
}
```

**Example with different locks:**
```java
class Test {
    Object lock1 = new Object();
    Object lock2 = new Object();
    
    public void method1() {
        synchronized(lock1) {
            // Code using resource 1
        }
    }
    
    public void method2() {
        synchronized(lock2) {
            // Code using resource 2
        }
    }
}
```

---

## 112. When do we use synchronized blocks and advantages

**Use synchronized blocks when:**
1. Only part of method needs synchronization
2. Need different locks for different resources
3. Want better performance

**Advantages:**

**1. Better Performance**
```java
// Synchronized method - entire method locked
public synchronized void process() {
    // 10 lines of code
}

// Synchronized block - only critical section locked
public void process() {
    // 5 lines (not locked)
    synchronized(this) {
        // 2 lines (locked)
    }
    // 3 lines (not locked)
}
```

**2. Fine-grained control**
```java
class Test {
    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    
    public void method() {
        synchronized(list1) {
            list1.add("Item");
        }
        
        synchronized(list2) {
            list2.add("Item");
        }
    }
}
```

**3. Reduced lock contention**
- Less waiting time for threads

---

## 113. What is class level lock?

**Class level lock** is applied on the class itself, not on object. Used for static synchronized methods.

```java
class Counter {
    private static int count = 0;
    
    // Class level lock
    public static synchronized void increment() {
        count++;
    }
}
```

**Difference:**

| Object Level Lock | Class Level Lock |
|------------------|------------------|
| On instance (object) | On class |
| One lock per object | One lock for entire class |
| Used for instance methods | Used for static methods |
| Different objects have different locks | All objects share same lock |

**Example:**
```java
class Test {
    // Object level lock
    public synchronized void instanceMethod() {
        // Each object has its own lock
    }
    
    // Class level lock
    public static synchronized void staticMethod() {
        // Lock on Test.class
    }
}
```

---

## 114. Can we synchronize static methods in Java?

**Yes**, static methods can be synchronized using class level lock.

```java
class Counter {
    private static int count = 0;
    
    // Synchronized static method
    public static synchronized void increment() {
        count++;
    }
    
    public static int getCount() {
        return count;
    }
}

public class Test {
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                Counter.increment();
            }
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                Counter.increment();
            }
        });
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        System.out.println("Count: " + Counter.getCount());  // 2000
    }
}
```

---

## 115. Can we use synchronized block for primitives?

**No**, synchronized block requires an object. Primitives are not objects.

```java
int count = 0;

// Error! Cannot synchronize on primitive
synchronized(count) {
    count++;
}

// Solution: Use wrapper object
Integer countObj = 0;
synchronized(countObj) {
    count++;
}

// Better solution: Use a dedicated lock object
Object lock = new Object();
synchronized(lock) {
    count++;
}
```

---

## 116. What are thread priorities and importance?

**Thread priority** is a number from 1 to 10 that helps thread scheduler decide which thread to execute.

```java
Thread t1 = new Thread();
t1.setPriority(Thread.MAX_PRIORITY);  // 10

Thread t2 = new Thread();
t2.setPriority(Thread.MIN_PRIORITY);  // 1

Thread t3 = new Thread();
t3.setPriority(Thread.NORM_PRIORITY); // 5
```

**Importance:**
- Higher priority thread gets more CPU time
- Helps in scheduling decisions
- Not guaranteed (depends on JVM and OS)

---

## 117. Explain different types of thread priorities

**Three constants:**

```java
Thread.MIN_PRIORITY  = 1   (Lowest)
Thread.NORM_PRIORITY = 5   (Default)
Thread.MAX_PRIORITY  = 10  (Highest)
```

**Example:**
```java
Thread t1 = new Thread(() -> {
    System.out.println("Low priority thread");
});
t1.setPriority(Thread.MIN_PRIORITY);

Thread t2 = new Thread(() -> {
    System.out.println("High priority thread");
});
t2.setPriority(Thread.MAX_PRIORITY);

t1.start();
t2.start();

// t2 is more likely to execute first (but not guaranteed)
```

**Default priority:** Every thread inherits parent's priority (usually 5)

---

## 118. How to change the priority of thread or set priority?

Use `setPriority()` method before starting the thread.

```java
class MyThread extends Thread {
    public void run() {
        System.out.println(getName() + " - Priority: " + getPriority());
    }
}

public class Test {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        MyThread t3 = new MyThread();
        
        // Set priorities before starting
        t1.setPriority(Thread.MIN_PRIORITY);  // 1
        t2.setPriority(Thread.NORM_PRIORITY); // 5
        t3.setPriority(Thread.MAX_PRIORITY);  // 10
        
        t1.start();
        t2.start();
        t3.start();
    }
}
```

**Note:** Must be between 1 and 10, otherwise `IllegalArgumentException`

---

## 119. If two threads have same priority which thread will be executed first?

**Cannot predict.** It depends on the thread scheduler algorithm.

```java
Thread t1 = new Thread(() -> System.out.println("Thread 1"));
Thread t2 = new Thread(() -> System.out.println("Thread 2"));

t1.setPriority(5);
t2.setPriority(5);

t1.start();
t2.start();

// Output can be:
// Thread 1
// Thread 2
// OR
// Thread 2
// Thread 1
```

**Thread scheduler decides based on:**
- Platform-dependent algorithm
- Time slicing
- Current system state

**Not deterministic!**

---

## 120. What all methods are used to prevent thread execution?

**Three methods:**

**1. yield()** - Pauses current thread, gives chance to other threads
**2. join()** - Waits for a thread to complete
**3. sleep()** - Pauses thread for specified time

```java
// 1. yield()
Thread.yield();

// 2. join()
Thread t = new Thread();
t.start();
t.join();  // Wait for t to complete

// 3. sleep()
Thread.sleep(1000);  // Sleep for 1 second
```

---

## 121. Explain yield() method in thread class

**yield()** temporarily pauses current thread and gives chance to other threads of same priority.

```java
class MyThread extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            Thread.yield();  // Give chance to other threads
        }
    }
}

public class Test {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        
        t1.start();
        t2.start();
    }
}
```

**Output:** (interleaved)
```
Thread-0: 1
Thread-1: 1
Thread-0: 2
Thread-1: 2
...
```

**Key Points:**
- Static method
- Hint to scheduler (not guaranteed)
- Gives chance to same or higher priority threads

---

## 122. Is it possible for yielded thread to get chance for execution again?

**Yes**, yielded thread can be selected again by the scheduler.

```java
class MyThread extends Thread {
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(getName() + ": " + i);
            Thread.yield();
        }
    }
}

public class Test {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();
    }
}
```

**Possible output:**
```
Thread-0: 1
Thread-0: 2  (Same thread selected again after yield)
Thread-0: 3
```

**yield() is just a hint**, scheduler can choose to run the same thread again.

---

## 123. Explain the importance of join() method in thread class

**join()** makes one thread wait until another thread completes.

```java
class Task extends Thread {
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println("Task: " + i);
            try { Thread.sleep(500); } catch (Exception e) { }
        }
    }
}

public class Test {
    public static void main(String[] args) throws Exception {
        Task task = new Task();
        task.start();
        
        task.join();  // Main thread waits for task to complete
        
        System.out.println("Task completed, continuing main");
    }
}
```

**Output:**
```
Task: 1
Task: 2
Task: 3
Task completed, continuing main
```

**Without join():**
```
Task: 1
Task completed, continuing main  (main doesn't wait)
Task: 2
Task: 3
```

**Use case:** When you need results from another thread before proceeding

---

## 124. Explain purpose of sleep() method in Java

**sleep()** pauses the current thread for specified milliseconds.

```java
class MyThread extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(i);
            try {
                Thread.sleep(1000);  // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Test {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();
    }
}
```

**Output:** (with 1 second gap)
```
1
(1 sec pause)
2
(1 sec pause)
3
...
```

**Key Points:**
- Static method
- Throws InterruptedException (checked exception)
- Thread goes to TIMED_WAITING state

---

## 125. When a thread has lock and calls sleep(), will it release the lock?

**No**, `sleep()` does NOT release the lock. Thread keeps the lock while sleeping.

```java
class Counter {
    public synchronized void display() {
        System.out.println(Thread.currentThread().getName() + " entered");
        try {
            Thread.sleep(3000);  // Sleeps but keeps lock
        } catch (InterruptedException e) { }
        System.out.println(Thread.currentThread().getName() + " exiting");
    }
}

public class Test {
    public static void main(String[] args) {
        Counter counter = new Counter();
        
        Thread t1 = new Thread(() -> counter.display(), "Thread-1");
        Thread t2 = new Thread(() -> counter.display(), "Thread-2");
        
        t1.start();
        t2.start();
    }
}
```

**Output:**
```
Thread-1 entered
(3 second pause - t2 waits)
Thread-1 exiting
Thread-2 entered
(3 second pause)
Thread-2 exiting
```

**Comparison:**
- `sleep()` - Does NOT release lock
- `wait()` - Releases lock

---

## 126. Can sleep() method cause another thread to sleep?

**No**, `sleep()` only affects the current thread.

```java
Thread t1 = new Thread(() -> {
    System.out.println("T1 running");
});

Thread t2 = new Thread(() -> {
    System.out.println("T2 running");
    try {
        Thread.sleep(2000);  // Only t2 sleeps
    } catch (InterruptedException e) { }
    System.out.println("T2 after sleep");
});

t1.start();
t2.start();
```

**Output:**
```
T1 running
T2 running
(t1 finishes)
(2 seconds later)
T2 after sleep
```

**t1 is NOT affected by t2's sleep()**

---

## 127. Explain about interrupt() method of thread class

**interrupt()** interrupts a thread that is sleeping or waiting.

```java
class MyThread extends Thread {
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println(i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted!");
        }
    }
}

public class Test {
    public static void main(String[] args) throws Exception {
        MyThread t = new MyThread();
        t.start();
        
        Thread.sleep(2500);  // Wait 2.5 seconds
        t.interrupt();       // Interrupt the thread
    }
}
```

**Output:**
```
1
2
Thread interrupted!
```

**When to use:** To stop a sleeping/waiting thread gracefully

---

## 128. Explain about interthread communication and how it takes place in Java

**Interthread communication** is the process where threads communicate with each other.

**Methods used:**
- `wait()` - Thread waits until notified
- `notify()` - Wakes up one waiting thread
- `notifyAll()` - Wakes up all waiting threads

**Example: Producer-Consumer**
```java
class SharedResource {
    private int data;
    private boolean hasData = false;
    
    public synchronized void produce(int value) throws InterruptedException {
        while (hasData) {
            wait();  // Wait if data already exists
        }
        data = value;
        hasData = true;
        System.out.println("Produced: " + value);
        notify();  // Notify consumer
    }
    
    public synchronized int consume() throws InterruptedException {
        while (!hasData) {
            wait();  // Wait if no data
        }
        hasData = false;
        System.out.println("Consumed: " + data);
        notify();  // Notify producer
        return data;
    }
}
```

---

## 129. Explain wait(), notify() and notifyAll() methods

**wait()** - Releases lock and waits
**notify()** - Wakes up one waiting thread
**notifyAll()** - Wakes up all waiting threads

```java
class Resource {
    public synchronized void method1() throws InterruptedException {
        System.out.println("In method1, waiting...");
        wait();  // Releases lock and waits
        System.out.println("Resumed method1");
    }
    
    public synchronized void method2() {
        System.out.println("In method2");
        notify();  // Wakes up waiting thread
        System.out.println("Notified");
    }
}
```

**Key Points:**
- Must be called from synchronized context
- Part of Object class (not Thread class)
- wait() releases lock, sleep() doesn't

---

## 130. Why are wait(), notify(), notifyAll() in Object class and not Thread class?

**Because locks are associated with objects, not threads.**

```java
// Any object can be used as a lock
Object lock = new Object();

synchronized(lock) {
    lock.wait();    // Wait on this object
    lock.notify();  // Notify threads waiting on this object
}
```

**Reasoning:**
- Multiple threads can wait on same object
- Lock is on object, not thread
- Any object can be a lock

**If they were in Thread class:**
- Only threads could be locks
- Less flexible
- Against Java's design

---

## 131. Explain IllegalMonitorStateException and when it's thrown

**IllegalMonitorStateException** is thrown when you call `wait()`, `notify()`, or `notifyAll()` without owning the lock.

```java
// Wrong - Not synchronized
public void method() {
    wait();  // IllegalMonitorStateException!
}

// Correct - Synchronized
public synchronized void method() {
    wait();  // OK
}

// Or using synchronized block
public void method() {
    synchronized(this) {
        wait();  // OK
    }
}
```

**Example:**
```java
class Test {
    public static void main(String[] args) throws Exception {
        Test obj = new Test();
        obj.wait();  // IllegalMonitorStateException - no lock on obj
    }
}
```

---

## 132. When wait(), notify(), notifyAll() are called, do they release the lock?

| Method | Releases Lock? |
|--------|---------------|
| wait() | **Yes** - Releases lock immediately |
| notify() | **No** - Releases after synchronized block ends |
| notifyAll() | **No** - Releases after synchronized block ends |

```java
synchronized(obj) {
    obj.wait();     // Releases lock here
}

synchronized(obj) {
    obj.notify();   // Lock released when block ends
}
```

---

## 133. Which methods release lock: yield(), join(), sleep(), wait(), notify(), notifyAll()?

| Method | Releases Lock? |
|--------|---------------|
| wait() | ✓ Yes |
| notify() | ✗ No |
| notifyAll() | ✗ No |
| sleep() | ✗ No |
| join() | ✗ No |
| yield() | ✗ No |

**Only wait() releases the lock!**

---

## 134. What are thread groups?

**Thread groups** are a way to organize threads into groups.

```java
ThreadGroup group = new ThreadGroup("MyGroup");

Thread t1 = new Thread(group, () -> {
    System.out.println("Thread 1");
});

Thread t2 = new Thread(group, () -> {
    System.out.println("Thread 2");
});

t1.start();
t2.start();

System.out.println("Active threads: " + group.activeCount());
```

**Benefits:**
- Organize threads
- Perform operations on all threads in group
- Set common properties

---

## 135. What are thread local variables?

**ThreadLocal** provides thread-local variables. Each thread has its own copy.

```java
class UserContext {
    private static ThreadLocal<String> userId = new ThreadLocal<>();
    
    public static void setUserId(String id) {
        userId.set(id);
    }
    
    public static String getUserId() {
        return userId.get();
    }
}

public class Test {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            UserContext.setUserId("User1");
            System.out.println(Thread.currentThread().getName() + ": " + UserContext.getUserId());
        });
        
        Thread t2 = new Thread(() -> {
            UserContext.setUserId("User2");
            System.out.println(Thread.currentThread().getName() + ": " + UserContext.getUserId());
        });
        
        t1.start();
        t2.start();
    }
}
```

**Output:**
```
Thread-0: User1
Thread-1: User2
```

**Each thread has its own value!**

---

## 136. What are daemon threads in Java?

**Daemon threads** are low-priority background threads that provide services to user threads.

```java
Thread t = new Thread(() -> {
    while (true) {
        System.out.println("Daemon running");
        try { Thread.sleep(1000); } catch (Exception e) { }
    }
});

t.setDaemon(true);  // Make it daemon
t.start();

Thread.sleep(3000);
System.out.println("Main ending");
// Daemon thread also stops when main ends
```

**Characteristics:**
- Dies when all user threads die
- JVM doesn't wait for daemon threads
- Example: Garbage Collector

---

## 137. How to make a non-daemon thread as daemon?

Use `setDaemon(true)` **before** starting the thread.

```java
Thread t = new Thread(() -> {
    System.out.println("Thread running");
});

t.setDaemon(true);  // Must be before start()
t.start();

// Wrong
Thread t2 = new Thread(() -> {});
t2.start();
t2.setDaemon(true);  // IllegalThreadStateException!
```

---

## 138. Can we make main() thread as daemon?

**No**, main thread cannot be made daemon because it's created by JVM and already started.

```java
public static void main(String[] args) {
    Thread.currentThread().setDaemon(true);  // IllegalThreadStateException!
}
```

**Reason:** `setDaemon()` must be called before thread starts. Main thread is already running.

