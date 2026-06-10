# Java Basics Interview Questions

## 1. What is Java and why is it platform independent?

**Answer:**
Java is an object-oriented programming language. It's platform independent because Java code compiles into bytecode (`.class` files) that runs on JVM (Java Virtual Machine), not directly on the operating system.

```
Source Code (.java) → Compiler → Bytecode (.class) → JVM → Machine Code
```

**Key Point:** "Write Once, Run Anywhere" - Same bytecode runs on Windows, Mac, Linux.

---

## 2. What is JDK, JRE, and JVM?

**Answer:**

**JVM (Java Virtual Machine):**
- Executes Java bytecode
- Provides runtime environment

**JRE (Java Runtime Environment):**
- JVM + Libraries
- Needed to run Java programs

**JDK (Java Development Kit):**
- JRE + Development Tools (compiler, debugger)
- Needed to develop Java programs

```
JDK = JRE + Development Tools
JRE = JVM + Libraries
```

---

## 3. Difference between == and .equals()?

**Answer:**

**== (Comparison Operator):**
- Compares memory addresses (reference)
- For primitives, compares values

**equals() (Method):**
- Compares actual content/values
- Can be overridden

```java
String s1 = new String("Hello");
String s2 = new String("Hello");

System.out.println(s1 == s2);        // false (different objects)
System.out.println(s1.equals(s2));   // true (same content)

int a = 5, b = 5;
System.out.println(a == b);          // true (for primitives)
```

---

## 4. What are the main principles of OOP?

**Answer:**

**1. Encapsulation** - Hiding data using private fields and public methods
```java
public class Account {
    private double balance;  // hidden
    
    public double getBalance() { return balance; }  // controlled access
    public void deposit(double amount) { balance += amount; }
}
```

**2. Inheritance** - Child class inherits from parent class
```java
class Animal { void eat() {} }
class Dog extends Animal { void bark() {} }  // Dog inherits eat()
```

**3. Polymorphism** - Same method, different behavior
```java
class Animal { void sound() {} }
class Dog extends Animal { void sound() { System.out.println("Bark"); } }
class Cat extends Animal { void sound() { System.out.println("Meow"); } }
```

**4. Abstraction** - Hiding implementation details
```java
abstract class Vehicle {
    abstract void start();  // what to do, not how
}
```

---

## 5. What is the difference between abstract class and interface?

**Answer:**

| Abstract Class | Interface |
|---------------|-----------|
| Can have constructors | No constructors |
| Can have instance variables | Only constants (public static final) |
| Can have concrete methods | Only abstract methods (before Java 8) |
| Single inheritance | Multiple inheritance |
| Use `extends` | Use `implements` |

**Abstract Class:**
```java
abstract class Animal {
    String name;  // instance variable
    
    Animal(String name) { this.name = name; }  // constructor
    
    void eat() { System.out.println("Eating"); }  // concrete method
    abstract void sound();  // abstract method
}
```

**Interface:**
```java
interface Flyable {
    void fly();  // abstract method
}

class Bird implements Flyable {
    public void fly() { System.out.println("Flying"); }
}
```

**When to use:**
- Abstract class: When classes share common code
- Interface: When classes share common behavior but different implementations

---

## 6. What is method overloading and overriding?

**Answer:**

**Overloading** - Same method name, different parameters (Compile-time polymorphism)
```java
class Calculator {
    int add(int a, int b) { return a + b; }
    int add(int a, int b, int c) { return a + b + c; }
    double add(double a, double b) { return a + b; }
}
```

**Overriding** - Child class redefines parent method (Runtime polymorphism)
```java
class Animal {
    void sound() { System.out.println("Animal sound"); }
}

class Dog extends Animal {
    @Override
    void sound() { System.out.println("Bark"); }  // overrides parent
}
```

---

## 7. What is the difference between final, finally, and finalize?

**Answer:**

**final:**
- `final variable` - Cannot be changed
- `final method` - Cannot be overridden
- `final class` - Cannot be inherited

```java
final int MAX = 100;  // constant
final class Utility {}  // cannot extend
```

**finally:**
- Used with try-catch
- Always executes (even if exception occurs)

```java
try {
    // code
} catch (Exception e) {
    // handle
} finally {
    // always runs (cleanup code)
}
```

**finalize:**
- Method called by garbage collector before destroying object
- Deprecated in Java 9

```java
protected void finalize() {
    // cleanup before object is destroyed
}
```

---

## 8. What is exception handling in Java?

**Answer:**

Exception handling prevents program crashes when errors occur.

**Types:**
1. **Checked Exception** - Must handle (IOException, SQLException)
2. **Unchecked Exception** - Runtime exceptions (NullPointerException, ArrayIndexOutOfBoundsException)
3. **Error** - Serious issues (OutOfMemoryError)

```java
try {
    int result = 10 / 0;  // ArithmeticException
    File file = new File("test.txt");  // IOException
} catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero");
} catch (IOException e) {
    System.out.println("File error");
} finally {
    System.out.println("Cleanup");
}
```

**throw vs throws:**
```java
// throws - declares exception
void readFile() throws IOException {
    // throw - actually throws exception
    throw new IOException("File not found");
}
```

---

## 9. What is the difference between String, StringBuilder, and StringBuffer?

**Answer:**

| Feature | String | StringBuilder | StringBuffer |
|---------|--------|---------------|--------------|
| Mutability | Immutable | Mutable | Mutable |
| Thread-safe | Yes | No | Yes (synchronized) |
| Performance | Slow for concatenation | Fast | Medium |

**String** - Immutable (creates new object on modification)
```java
String s = "Hello";
s = s + " World";  // creates new String object
```

**StringBuilder** - Mutable, not thread-safe, fast
```java
StringBuilder sb = new StringBuilder("Hello");
sb.append(" World");  // modifies same object
```

**StringBuffer** - Mutable, thread-safe, slower than StringBuilder
```java
StringBuffer sb = new StringBuffer("Hello");
sb.append(" World");  // synchronized methods
```

**When to use:**
- String: When value doesn't change
- StringBuilder: Single-threaded string manipulation
- StringBuffer: Multi-threaded string manipulation

---

## 10. What is garbage collection in Java?

**Answer:**

Garbage collection automatically removes unused objects from memory to prevent memory leaks.

**How it works:**
1. JVM identifies objects with no references
2. Marks them for deletion
3. Removes them from memory

```java
public class Demo {
    public static void main(String[] args) {
        String s1 = new String("Hello");
        s1 = null;  // now eligible for garbage collection
        
        System.gc();  // suggests JVM to run GC (not guaranteed)
    }
}
```

**Key Points:**
- Automatic memory management
- Developer cannot force garbage collection
- Objects without references are eligible for GC

---

## 11. What are access modifiers in Java?

**Answer:**

| Modifier | Class | Package | Subclass | World |
|----------|-------|---------|----------|-------|
| public | ✓ | ✓ | ✓ | ✓ |
| protected | ✓ | ✓ | ✓ | ✗ |
| default (no modifier) | ✓ | ✓ | ✗ | ✗ |
| private | ✓ | ✗ | ✗ | ✗ |

```java
public class Example {
    public int a;       // accessible everywhere
    protected int b;    // accessible in same package + subclasses
    int c;             // accessible in same package only
    private int d;     // accessible only in this class
}
```

---

## 12. What is static keyword in Java?

**Answer:**

`static` belongs to the class, not instances (objects).

**Static Variable:**
```java
class Counter {
    static int count = 0;  // shared by all objects
    
    Counter() {
        count++;
    }
}

Counter c1 = new Counter();  // count = 1
Counter c2 = new Counter();  // count = 2
```

**Static Method:**
```java
class MathUtil {
    static int add(int a, int b) {
        return a + b;
    }
}

MathUtil.add(5, 3);  // call without creating object
```

**Static Block:**
```java
class Database {
    static {
        // runs once when class is loaded
        System.out.println("Database initialized");
    }
}
```

---

## 13. What is the difference between Heap and Stack memory?

**Answer:**

**Stack Memory:**
- Stores method calls and local variables
- Fast access
- Limited size
- Automatically managed (LIFO)

**Heap Memory:**
- Stores objects and instance variables
- Slower access
- Larger size
- Managed by garbage collector

```java
public void method() {
    int x = 10;              // stored in Stack
    String s = new String("Hello");  // reference in Stack, object in Heap
}
```

---

## 14. What is constructor in Java?

**Answer:**

Constructor is a special method that initializes objects. It has the same name as the class and no return type.

**Default Constructor:**
```java
class Student {
    Student() {
        System.out.println("Object created");
    }
}
```

**Parameterized Constructor:**
```java
class Student {
    String name;
    int age;
    
    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

Student s = new Student("John", 20);
```

**Constructor Overloading:**
```java
class Student {
    String name;
    
    Student() {
        this.name = "Unknown";
    }
    
    Student(String name) {
        this.name = name;
    }
}
```

---

## 15. What is the difference between this and super keyword?

**Answer:**

**this:**
- Refers to current class instance
- Used to access current class members

```java
class Student {
    String name;
    
    Student(String name) {
        this.name = name;  // this.name refers to instance variable
    }
    
    void display() {
        System.out.println(this.name);
    }
}
```

**super:**
- Refers to parent class
- Used to access parent class members

```java
class Animal {
    void eat() { System.out.println("Eating"); }
}

class Dog extends Animal {
    void eat() { System.out.println("Dog eating"); }
    
    void display() {
        super.eat();  // calls parent method
        this.eat();   // calls current method
    }
}
```
