# Java Fundamentals - Interview Questions

## Core Java Basics

### 1. What are static blocks and static initializers in Java?
Static blocks are used to initialize static variables. They execute when the class is loaded into memory, before any object creation or main method execution.
```java
class Example {
    static int count;
    static {
        count = 10; // Static block
    }
}
```

### 2. How to call one constructor from the other constructor?
Use `this()` keyword to call another constructor in the same class.
```java
class Student {
    Student() { this(0); }
    Student(int id) { /* constructor logic */ }
}
```

### 3. What is method overriding in Java?
Method overriding is when a subclass provides a specific implementation of a method already defined in its parent class. Same method signature with different implementation.

### 4. What is super keyword in Java?
`super` keyword refers to the parent class object. It's used to:
- Access parent class variables
- Call parent class methods
- Call parent class constructor

### 5. Difference between method overloading and method overriding?
**Overloading:** Same method name, different parameters, same class. (Compile-time polymorphism)
**Overriding:** Same method signature, different classes (parent-child). (Runtime polymorphism)

### 6. Difference between abstract class and interface?
**Abstract Class:** Can have both abstract and concrete methods, constructors, instance variables.
**Interface:** Only abstract methods (before Java 8), no constructors, only constants.

### 7. Why is Java platform independent?
Java compiles to bytecode (.class files) which runs on JVM. The JVM is platform-specific, but bytecode is universal. "Write Once, Run Anywhere."

### 8. What is method overloading in Java?
Same method name with different parameters (number, type, or order) within the same class.
```java
void add(int a, int b) {}
void add(int a, int b, int c) {}
void add(double a, double b) {}
```

### 9. What is the difference between C++ and Java?
- Java has no pointers (direct memory access)
- Java has automatic garbage collection
- Java doesn't support multiple inheritance (classes)
- Java is platform-independent

### 10. What is JIT compiler?
Just-In-Time compiler converts bytecode to native machine code at runtime for faster execution. Part of JVM.

### 11. What is bytecode in Java?
Bytecode is intermediate platform-independent code generated after compilation. It's stored in .class files and executed by JVM.

### 12. Difference between this() and super()?
**this():** Calls constructor of the same class.
**super():** Calls constructor of parent class.
Both must be the first statement in constructor.

### 13. What is a class?
A class is a blueprint or template that defines the properties (variables) and behaviors (methods) of objects.

### 14. What is an object?
An object is an instance of a class. It has state (data) and behavior (methods).

### 15. What is a method in Java?
A method is a block of code that performs a specific task. It provides reusability and modularity.

### 16. What is encapsulation?
Encapsulation is wrapping data (variables) and code (methods) together as a single unit. Implemented using access modifiers and getter/setter methods.

### 17. Why is main() method public, static, and void?
**public:** Accessible from anywhere, JVM can call it.
**static:** Can be called without creating an object.
**void:** Doesn't return any value to JVM.

### 18. Explain about main() method in Java?
Entry point of Java application. Signature: `public static void main(String[] args)`. JVM calls this method to start execution.

### 19. What is a constructor in Java?
A special method with the same name as class, no return type. Used to initialize objects when they are created.

### 20. Difference between length and length() method?
**length:** Property of arrays. `array.length`
**length():** Method of String class. `string.length()`

### 21. What is ASCII Code?
American Standard Code for Information Interchange. 7-bit character encoding (0-127). Represents English characters.

### 22. What is Unicode?
Universal character encoding standard that represents characters from all languages. Supports 16-bit (0-65535).

### 23. Difference between Character Constant and String Constant?
**Character Constant:** Single character in single quotes. `'A'`
**String Constant:** Sequence of characters in double quotes. `"Hello"`

### 24. What are constants and how to create them?
Constants are values that don't change. Created using `final` keyword.
```java
final int MAX_VALUE = 100;
```

### 25. Difference between '>>' and '>>>' operators?
**>>:** Signed right shift (preserves sign bit)
**>>>:** Unsigned right shift (fills left with zeros)

## Java Coding Standards

### 26. Java Coding Standards for classes?
- Class names should be nouns
- First letter uppercase
- Use CamelCase: `StudentRecord`, `EmployeeData`

### 27. Java Coding standards for interfaces?
- Should be adjectives (describing capability)
- First letter uppercase
- Use CamelCase: `Runnable`, `Serializable`

### 28. Java Coding standards for Methods?
- Should be verbs
- First letter lowercase
- Use camelCase: `getValue()`, `calculateTotal()`

### 29. Java Coding Standards for variables?
- First letter lowercase
- Use camelCase: `firstName`, `studentAge`
- Meaningful names

### 30. Java Coding Standards for Constants?
- All uppercase letters
- Words separated by underscore: `MAX_VALUE`, `DEFAULT_SIZE`

### 31. Difference between overriding and overloading?
Same as question 5.

### 32. What is 'IS-A' relationship?
Inheritance relationship. Child class IS-A type of parent class.
Example: Dog IS-A Animal.

### 33. What is 'HAS-A' relationship?
Composition/Aggregation. One class contains reference to another class.
Example: Car HAS-A Engine.

### 34. Difference between 'IS-A' and 'HAS-A' relationship?
**IS-A:** Inheritance (extends/implements)
**HAS-A:** Composition (class contains another class as member)

### 35. Explain instanceof operator?
Tests whether an object is an instance of a specific class or interface.
```java
if (obj instanceof String) { }
```

### 36. What does null mean in Java?
`null` is a literal representing no value or no object. Default value for object references.

### 37. Can we have multiple classes in single file?
Yes, but only one public class per file. File name must match public class name.

### 38. What access modifiers are allowed for top-level class?
- public
- default (no modifier)

### 39. What are packages in Java?
Packages are containers/folders that group related classes and interfaces. Prevents naming conflicts.

### 40. Can we have more than one package statement in source file?
No. Only one package statement allowed, and it must be the first statement.

### 41. Can we define package statement after import statement?
No. Package statement must be first, before imports.

### 42. What are identifiers in Java?
Names given to variables, methods, classes, packages. Must start with letter, $, or _.

### 43. What are access modifiers in Java?
Keywords that set access level: `public`, `private`, `protected`, `default`.

### 44. Difference between access specifiers and access modifiers?
They are the same thing in Java. Both terms are used interchangeably.

### 45. What access modifiers can be used for class?
- public
- default (no modifier)
- abstract
- final

### 46. What access modifiers can be used for methods?
- public
- private
- protected
- default

### 47. What access modifiers can be used for variables?
- public
- private
- protected
- default

### 48. What is final access modifier?
**final class:** Cannot be inherited
**final method:** Cannot be overridden
**final variable:** Cannot be changed (constant)

### 49. Explain about abstract classes?
- Cannot be instantiated
- May contain abstract methods (no body)
- Can have concrete methods
- Used for partial implementation

### 50. Can we create constructor in abstract class?
Yes. Constructors in abstract classes are called when subclass objects are created.

### 51. What are abstract methods?
Methods declared without implementation (no body). Subclasses must provide implementation.
```java
abstract void display();
```

## Exception Handling

### 52. What is an exception?
An event that disrupts normal program flow. An object representing an error condition.

### 53. Situations where exceptions may arise?
- Dividing by zero
- Accessing null object
- Array index out of bounds
- File not found
- Network connection failure

### 54. What is Exception handling?
Mechanism to handle runtime errors and maintain normal program flow using try-catch blocks.

### 55. What is an error in Java?
Serious problems that applications shouldn't try to catch. Examples: OutOfMemoryError, StackOverflowError.

### 56. Advantages of Exception handling?
- Separates error handling from regular code
- Groups error types
- Propagates errors up the call stack
- Maintains program flow

### 57. In how many ways can we do exception handling?
Two ways:
1. try-catch-finally blocks
2. throws keyword (declare exception)

### 58. Five keywords related to Exception handling?
1. try
2. catch
3. finally
4. throw
5. throws

### 59. Explain try and catch keywords?
**try:** Contains code that might throw exception
**catch:** Handles the exception if it occurs

### 60. Can we have try block without catch block?
Yes, if we have finally block. `try-finally` is valid.

### 61. Can we have multiple catch blocks for a try block?
Yes. Multiple catch blocks for different exception types.

### 62. Explain importance of finally block?
Always executes whether exception occurs or not. Used for cleanup (closing files, connections).

### 63. Can we have code between try and catch blocks?
No. catch must immediately follow try.

### 64. Can we have code between try and finally blocks?
Yes, if there are catch blocks between them. But not directly between try and finally.

### 65. Can we catch more than one exception in single catch block?
Yes (Java 7+). Using pipe symbol:
```java
catch(IOException | SQLException e) {}
```

### 66. What are checked exceptions?
Exceptions checked at compile-time. Must be handled or declared. Example: IOException, SQLException.

### 67. What are unchecked exceptions?
Exceptions not checked at compile-time. Runtime exceptions. Example: NullPointerException, ArrayIndexOutOfBoundsException.

### 68. Differences between checked and unchecked exceptions?
**Checked:** Compile-time, must handle, extend Exception
**Unchecked:** Runtime, optional handling, extend RuntimeException

### 69. What is default exception handling?
If exception is not handled, JVM terminates the program and prints exception details (stack trace).

### 70. Explain throw keyword?
Used to explicitly throw an exception.
```java
throw new IllegalArgumentException("Invalid value");
```

### 71. Can we write code after throw statement?
No. It results in unreachable code compilation error.

### 72. Explain importance of throws keyword?
Declares that a method may throw exceptions. Caller must handle them.
```java
void readFile() throws IOException {}
```

### 73. Explain importance of finally over return statement?
Finally block executes even if there's a return statement in try or catch.

### 74. When will finally block not execute?
- If `System.exit()` is called
- If JVM crashes
- If thread is killed

### 75. Can we use catch statement for checked exceptions?
Yes. Catch can handle both checked and unchecked exceptions.

### 76. What are user-defined exceptions?
Custom exception classes created by extending Exception or RuntimeException class.

### 77. Can we rethrow the same exception from catch handler?
Yes.
```java
catch(Exception e) {
    // Handle
    throw e; // Rethrow
}
```

### 78. Can we have nested try statements?
Yes. Try block can contain another try block.

### 79. Explain Throwable class and its methods?
Root class for all exceptions and errors. Key methods:
- `getMessage()`: Returns error message
- `printStackTrace()`: Prints stack trace
- `toString()`: String representation

### 80. When is ClassNotFoundException raised?
When trying to load a class using `Class.forName()` or `ClassLoader.loadClass()` but class is not found.

### 81. When is NoClassDefFoundError raised?
When class was present at compile-time but not found at runtime. Usually after successful compilation.

## Threads

### 83. What is a process?
An executing instance of an application. Has its own memory space.

### 84. What is a thread?
Lightweight subprocess. Smallest unit of execution within a process. Shares process memory.

### 85. Difference between process and thread?
**Process:** Independent memory, heavyweight, separate execution
**Thread:** Shared memory, lightweight, concurrent execution within process

### 86. What is multitasking?
Executing multiple tasks simultaneously. Improves CPU utilization.

### 87. Different types of multitasking?
1. **Process-based:** Multiple processes running concurrently
2. **Thread-based:** Multiple threads within a process

### 88. Benefits of multithreaded programming?
- Better CPU utilization
- Improved performance
- Simplified program structure
- Responsive applications
- Resource sharing

### 89. Explain thread in Java?
A thread is a path of execution. Java supports multithreading through Thread class and Runnable interface.

### 90. Java API that supports threads?
- `java.lang.Thread` class
- `java.lang.Runnable` interface
- `java.util.concurrent` package

### 91. Explain main thread?
The thread that starts when program begins. It's the thread from which child threads are spawned.

### 92. In how many ways can we create threads?
Two ways:
1. Extending Thread class
2. Implementing Runnable interface

### 93. Creating threads by implementing Runnable?
```java
class MyRunnable implements Runnable {
    public void run() { /* thread logic */ }
}
Thread t = new Thread(new MyRunnable());
t.start();
```

### 94. Creating threads by extending Thread class?
```java
class MyThread extends Thread {
    public void run() { /* thread logic */ }
}
MyThread t = new MyThread();
t.start();
```

### 95. Best approach for creating thread?
Implementing Runnable is better because:
- Java doesn't support multiple inheritance
- Better object-oriented design
- Separates task from thread mechanics

### 96. Importance of thread scheduler?
Part of JVM that decides which thread should run. Based on priority and time-slicing.

### 97. Life cycle of thread?
1. New (created)
2. Runnable (ready to run)
3. Running (executing)
4. Blocked/Waiting (waiting for resource)
5. Dead (terminated)

### 98. Can we restart a dead thread?
No. Once thread completes execution, it cannot be restarted.

### 99. Can one thread block another thread?
Yes, through synchronization, wait/notify, or by acquiring locks.

### 100. Can we restart a thread already started?
No. Calling start() twice throws IllegalThreadStateException.

### 101. What happens if we don't override run method?
Thread will execute empty run() method from Thread class. No useful work is done.

### 102. Can we overload run() method?
Yes, but JVM only calls `run()` with no parameters. Overloaded versions won't be called automatically.

### 105. What is a lock or purpose of locks?
Mechanism to control access to shared resources. Ensures only one thread accesses the resource at a time.

### 106. In how many ways can we do synchronization?
Two ways:
1. Synchronized methods
2. Synchronized blocks

### 107. What are synchronized methods?
Methods declared with `synchronized` keyword. Only one thread can execute at a time.

### 108. When do we use synchronized methods?
When multiple threads access shared data and we need to prevent data inconsistency.

### 109. Can other threads execute synchronized methods simultaneously?
No. Only one thread can execute any synchronized method of an object at a time.

### 110. Can same thread access other synchronized methods?
Yes. The same thread can call other synchronized methods of the same object.

### 111. What are synchronized blocks?
Block of code marked as synchronized. More flexible than synchronized methods.
```java
synchronized(obj) {
    // critical section
}
```

### 112. When to use synchronized blocks?
When only part of method needs synchronization. Better performance than synchronizing entire method.

### 113. What is class-level lock?
Lock on class object (not instance). Used for static synchronized methods. Only one thread per class.

### 114. Can we synchronize static methods?
Yes. They acquire class-level lock.

### 115. Can we use synchronized block for primitives?
No. Primitives cannot be used as lock objects. Only objects can be used.

### 116. Thread priorities and importance?
Priorities suggest to thread scheduler which thread should execute first. Range: 1 (MIN) to 10 (MAX).

### 117. Different types of thread priorities?
- MIN_PRIORITY = 1
- NORM_PRIORITY = 5 (default)
- MAX_PRIORITY = 10

### 118. How to set priority of thread?
```java
thread.setPriority(Thread.MAX_PRIORITY);
```

### 119. If two threads have same priority?
Scheduler decides based on time-slicing algorithm. Behavior is platform-dependent.

### 120. Methods to prevent thread execution?
- `sleep()`
- `wait()`
- `join()`
- `yield()`

### 121. Explain yield() method?
Suggests current thread to pause and allow other threads of same priority to execute. Thread moves to runnable state.

### 122. Can yielded thread get chance again?
Yes. It goes back to runnable state and can be selected by scheduler.

### 123. Explain join() method?
Makes one thread wait for another thread to complete.
```java
thread.join(); // Wait for thread to die
```

### 124. Explain sleep() method?
Pauses current thread for specified milliseconds.
```java
Thread.sleep(1000); // Sleep for 1 second
```

### 125. Does sleep() release the lock?
No. Thread keeps the lock while sleeping.

### 126. Can sleep() cause another thread to sleep?
No. Sleep() only affects the current thread.

### 127. Explain interrupt() method?
Interrupts a sleeping or waiting thread. Throws InterruptedException.

### 128. Explain interthread communication?
Threads communicate using wait(), notify(), and notifyAll() methods. Used for thread coordination.

### 129. Explain wait(), notify(), notifyAll() methods?
**wait():** Releases lock and waits
**notify():** Wakes up one waiting thread
**notifyAll():** Wakes up all waiting threads

### 130. Why are wait/notify/notifyAll in Object class?
Because locks are associated with objects, not threads. Every object can be a lock.

### 131. Explain IllegalMonitorStateException?
Thrown when calling wait/notify/notifyAll without owning the object's lock (outside synchronized context).

### 132. Does wait/notify release or hold lock?
**wait():** Releases lock
**notify()/notifyAll():** Doesn't release immediately, releases when synchronized block ends

### 133. Which methods release the lock?
Only `wait()` releases the lock. yield(), join(), sleep(), notify(), notifyAll() do not release lock.

### 134. What are thread groups?
A way to group multiple threads. Useful for managing threads as a group.

### 135. What are thread-local variables?
Variables that are local to each thread. Each thread has its own copy.
```java
ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
```

### 136. What are daemon threads?
Background threads that provide services to user threads. JVM exits when only daemon threads remain.

### 137. How to make thread daemon?
```java
thread.setDaemon(true);
```
Must be called before start().

### 138. Can we make main() thread daemon?
No. Main thread cannot be daemon. IllegalThreadStateException is thrown.

## Nested and Inner Classes

### 139. What are nested classes?
Classes defined within another class. Types: static nested, inner, local, anonymous.

### 140. What are inner classes or non-static nested classes?
Non-static classes defined inside another class. Has access to outer class members.

### 141. Why use nested classes?
- Logical grouping
- Encapsulation
- More readable and maintainable code
- Access to outer class members

### 142. Explain static nested classes?
Nested class declared as static. Cannot access non-static members of outer class.

### 143. How to instantiate static nested classes?
```java
Outer.StaticNested obj = new Outer.StaticNested();
```

### 144. Explain method local inner classes?
Classes defined inside a method. Scope is limited to that method.

### 145. Features of local inner class?
- Cannot have access modifiers
- Cannot be static
- Can access final or effectively final local variables

### 146. Explain anonymous inner classes?
Classes without a name. Used for one-time use. Often used with interfaces or abstract classes.
```java
Runnable r = new Runnable() {
    public void run() { }
};
```

### 147. Restrictions for anonymous inner classes?
- Cannot have constructors
- Cannot define static members
- Can implement one interface or extend one class

### 148. Can we instantiate interface?
Not directly. But we can create anonymous inner class implementing the interface.

### 149. Explain member inner classes?
Non-static nested classes. Associated with instance of outer class.

### 150. How to instantiate member inner class?
```java
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
```

## Additional Core Concepts

### 151. How to do encapsulation in Java?
- Declare variables as private
- Provide public getter/setter methods

### 152. What are reference variables?
Variables that store memory address of objects, not the actual object.

### 153. Will compiler create default constructor if we have parameterized constructor?
No. Default constructor is created only if no constructors are defined.

### 154. Can method name be same as class name?
Yes, but it's not a constructor if it has a return type.

### 155. Can we override constructors?
No. Constructors are not inherited, so cannot be overridden.

### 156. Can static methods access instance variables?
No. Static methods can only access static members directly.

### 157. How do we access static members?
```java
ClassName.staticMember;
// or
object.staticMember; // Not recommended
```

### 158. Can we override static methods?
No. Static methods are bound at compile-time (method hiding, not overriding).

### 159. Difference between object and reference?
**Object:** Actual instance in memory
**Reference:** Variable that points to object

### 160. Objects or references - which gets garbage collected?
Objects get garbage collected when no references point to them.

### 161. How many times is finalize() invoked?
Only once per object, before garbage collection. Called by garbage collector.

### 162. Can we pass objects as arguments?
Yes. Objects are passed by reference value.

### 163. Explain wrapper classes?
Classes that wrap primitive types into objects. Enable primitives to be used where objects are required.

### 164. Different types of wrapper classes?
- Byte, Short, Integer, Long
- Float, Double
- Character
- Boolean

### 165. Explain transient variables?
Variables marked with `transient` keyword are not serialized.
```java
transient int password;
```

### 166. Can we serialize static variables?
No. Static variables belong to class, not object. They are not serialized.

### 167. What is type conversion?
Converting one data type to another.

### 168. Explain automatic type conversion?
Widening conversion happens automatically. Smaller type to larger type.
```java
int i = 10;
long l = i; // Automatic
```

### 169. Explain narrowing conversion?
Converting larger type to smaller type. Requires explicit casting.
```java
long l = 100;
int i = (int) l; // Explicit cast
```

### 170. Importance of import keyword?
Makes classes from other packages available. Avoids writing fully qualified names.

### 171. Naming conventions for packages?
- All lowercase
- Reverse domain name: `com.company.project`

### 172. What is classpath?
Path where JVM searches for class files. Set using -classpath or CLASSPATH environment variable.

### 173. What is JAR?
Java Archive. Compressed file containing multiple .class files and resources.

### 174. Scope/lifetime of instance variables?
Created when object is created. Destroyed when object is garbage collected.

### 175. Scope/lifetime of static variables?
Created when class is loaded. Destroyed when program ends or class is unloaded.

### 176. Scope/lifetime of local variables?
Created when method is called. Destroyed when method completes.

### 177. Explain static imports?
Import static members of a class directly.
```java
import static java.lang.Math.PI;
// Use PI directly without Math.PI
```

### 178. Can we define static methods inside interface?
Yes (Java 8+). Static methods in interfaces cannot be overridden.

### 179. Define interface?
Blueprint of a class. Contains abstract methods (contract) that implementing classes must fulfill.

### 180. Purpose of interface?
- Achieve abstraction
- Support multiple inheritance
- Define contracts
- Achieve loose coupling

### 181. Features of interfaces?
- All methods are public and abstract (before Java 8)
- Variables are public, static, final
- Cannot be instantiated
- Can be implemented by classes

### 182. Explain enumeration?
Special data type for defining collections of constants.
```java
enum Day { MONDAY, TUESDAY, WEDNESDAY }
```

### 183. Restrictions on enum?
- Cannot extend other classes (already extends Enum)
- Can implement interfaces
- Can have constructors, methods, fields

### 184. Explain field hiding?
When subclass declares a variable with same name as parent class variable. Parent's variable is hidden.

### 185. Explain Varargs?
Variable number of arguments. Allows method to accept zero or more arguments.
```java
void method(String... args) { }
```

### 186. Where are variables created in memory?
- Instance variables: Heap
- Static variables: Method area (Metaspace)
- Local variables: Stack

### 187. Can we use Switch with Strings?
Yes (Java 7+). Switch supports String, int, char, enum.

### 188. How do we copy objects in Java?
- Using clone() method
- Copy constructor
- Serialization
- Manual copying fields

## OOP Concepts

### 189. Procedural programming and features?
Programming using procedures/functions. Features:
- Top-down approach
- Functions operate on data
- Less secure (data is global)

### 190. Object-oriented programming and features?
Programming using objects. Features:
- Bottom-up approach
- Data and functions bundled
- Encapsulation, inheritance, polymorphism
- More secure

### 191. Benefits of OOP?
- Modularity
- Reusability
- Extensibility
- Maintainability
- Data security

### 192. Differences between traditional and OOP?
**Traditional:** Function-centric, data can be accessed anywhere
**OOP:** Object-centric, data is encapsulated

### 193. Explain OOP concepts?
Four main pillars:
1. **Encapsulation:** Data hiding
2. **Inheritance:** Code reuse
3. **Polymorphism:** One interface, multiple forms
4. **Abstraction:** Hiding complexity

### 194. What is encapsulation?
Wrapping data and methods together. Hiding internal details using access modifiers.

### 195. What is inheritance?
Mechanism where one class acquires properties of another class. Promotes code reuse.

### 196. Importance of inheritance?
- Code reusability
- Method overriding
- Establishes IS-A relationship
- Supports polymorphism

### 197. What is polymorphism?
"Many forms." One interface, multiple implementations. Types:
- Compile-time: Method overloading
- Runtime: Method overriding

## Collections Framework

### 198. What is collections framework?
Unified architecture for representing and manipulating collections. Provides interfaces and classes.

### 199. What is a collection?
Object that groups multiple elements into a single unit.

### 200. Difference between collection, Collection, and Collections?
**collection:** General term
**Collection:** Root interface in framework
**Collections:** Utility class with static methods

### 201. Explain Collection interface?
Root interface. Defines basic operations: add, remove, size, clear, iterator.

### 202. Interfaces that extend Collection?
- List
- Set
- Queue

### 203. Explain List interface?
Ordered collection (sequence). Allows duplicates. Elements accessible by index.

### 204. Methods specific to List?
- `get(index)`
- `set(index, element)`
- `add(index, element)`
- `remove(index)`
- `indexOf()`

### 205. Implementations of List?
- ArrayList
- LinkedList
- Vector
- Stack

### 206. Explain ArrayList?
Resizable array implementation. Fast random access. Slow insertion/deletion in middle.

### 207. Difference between Array and ArrayList?
**Array:** Fixed size, can hold primitives
**ArrayList:** Dynamic size, holds objects only

### 208. What is Vector?
Synchronized version of ArrayList. Thread-safe but slower. Legacy class.

### 209. Difference between ArrayList and Vector?
**ArrayList:** Not synchronized, fast
**Vector:** Synchronized, thread-safe, slower

### 210. Define LinkedList and features?
Doubly-linked list implementation. Fast insertion/deletion. Slow random access.
```java
public class LinkedList<E> extends AbstractSequentialList<E>
    implements List<E>, Deque<E>
```

### 211. Define Iterator and methods?
Interface for iterating over collections. Methods:
- `hasNext()`
- `next()`
- `remove()`

### 212. Iterator iteration order?
Depends on collection. List: insertion order. TreeSet: sorted order.

### 213. Explain ListIterator and methods?
Bi-directional iterator for lists. Methods:
- `hasNext()`, `next()`
- `hasPrevious()`, `previous()`
- `add()`, `set()`, `remove()`

### 214. Explain Sets?
Collection that doesn't allow duplicates. Models mathematical set.

### 215. Implementations of Set?
- HashSet
- LinkedHashSet
- TreeSet

### 216. Explain HashSet and features?
- Uses HashMap internally
- No duplicates
- No ordering
- Allows one null
- Fast operations

### 217. Explain TreeSet and features?
- Sorted set (natural order or comparator)
- No duplicates
- No null elements
- Slower than HashSet

### 218. When to use HashSet over TreeSet?
When you don't need sorting. HashSet is faster for add/remove/contains.

### 219. What is LinkedHashSet?
HashSet with predictable iteration order (insertion order).

### 220. Explain Map interface?
Key-value pair mapping. Keys are unique. Not part of Collection hierarchy.

### 221. What is LinkedHashMap?
HashMap with predictable iteration order (insertion order).

### 222. What is SortedMap?
Map with keys in sorted order. TreeMap implements it.

### 223. What is Hashtable?
Synchronized HashMap. Thread-safe. No null keys/values. Legacy class.

### 224. Difference between HashMap and Hashtable?
**HashMap:** Not synchronized, allows one null key
**Hashtable:** Synchronized, no null keys/values

### 225. Difference between ArrayList and LinkedList?
**ArrayList:** Random access fast, insertion/deletion slow
**LinkedList:** Insertion/deletion fast, random access slow

### 226. Difference between Comparator and Comparable?
**Comparable:** Natural ordering, `compareTo()`, in same class
**Comparator:** Custom ordering, `compare()`, separate class

### 227. What is ConcurrentHashMap?
Thread-safe HashMap alternative. Better concurrency than Hashtable. No null keys/values.

### 228. Difference between ConcurrentHashMap, Hashtable, and synchronizedMap?
**ConcurrentHashMap:** Segment-level locking, best concurrency
**Hashtable:** Object-level locking
**synchronizedMap:** Object-level locking, wrapper

### 229. Explain CopyOnWriteArrayList?
Thread-safe variant of ArrayList. Creates new copy on modification. Good for read-heavy scenarios.

### 230. Explain fail-fast iterators?
Immediately throw ConcurrentModificationException if collection is modified during iteration.

### 231. Explain fail-safe iterators?
Work on clone of collection. Don't throw exception if collection is modified.

## Serialization

### 232. What is serialization?
Converting object to byte stream for storage or transmission.

### 233. Main purpose of serialization?
- Persistence (save object state)
- Network transmission
- Deep copying

### 234. Alternatives to Java serialization?
- JSON (Jackson, Gson)
- XML
- Protocol Buffers
- Apache Avro

### 235. Explain Serializable interface?
Marker interface (no methods). Indicates class can be serialized.

### 236. How to make object serializable?
Implement Serializable interface.
```java
class Student implements Serializable { }
```

### 237. What is serialVersionUID?
Unique identifier for serializable class. Ensures compatibility during deserialization.

### 238. What happens if we don't define serialVersionUID?
JVM generates it automatically based on class details. Can cause InvalidClassException during deserialization.

### 239. Can we serialize static variables?
No. Static variables belong to class, not object.

### 240. Does serialization save references too?
Yes. If object has references to other serializable objects, they are serialized too (object graph).

### 241. How to prevent fields from serialization?
Mark them as `transient`.
```java
transient String password;
```
