# Nested Classes and Inner Classes

## 139. What are nested classes in Java?

A **nested class** is a class defined inside another class.

```java
class Outer {
    // Nested class
    class Inner {
        void display() {
            System.out.println("Inner class");
        }
    }
}
```

**Types of nested classes:**
```
Nested Classes
    ├── Static Nested Class
    └── Inner Classes (Non-static)
            ├── Member Inner Class
            ├── Local Inner Class
            └── Anonymous Inner Class
```

---

## 140. What are inner classes or non-static nested classes?

**Inner classes** are non-static classes defined inside another class. They have access to all members of the outer class.

```java
class Outer {
    private String message = "Hello";
    
    // Inner class
    class Inner {
        void display() {
            System.out.println(message);  // Can access outer class private member
        }
    }
}

public class Test {
    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.display();  // Output: Hello
    }
}
```

**Key Points:**
- Cannot have static members
- Has access to all outer class members (even private)
- Needs outer class instance to create inner class instance

---

## 141. Why use nested classes in Java?

**Reasons to use nested classes:**

**1. Logical Grouping**
```java
class Car {
    class Engine {
        // Engine is part of Car
    }
}
```

**2. Encapsulation**
```java
class BankAccount {
    private double balance;
    
    // Only BankAccount can use Transaction
    private class Transaction {
        void withdraw(double amount) {
            balance -= amount;  // Access private member
        }
    }
}
```

**3. More Readable Code**
```java
// Instead of creating separate file
class CarEngine { }

// Keep related classes together
class Car {
    class Engine { }
}
```

**4. Closer to Outer Class**
- Can access all outer class members
- Tight coupling when needed

---

## 142. Explain about static nested classes in Java

**Static nested class** is a nested class with the `static` keyword. It doesn't need an outer class instance.

```java
class Outer {
    static String message = "Hello";
    
    // Static nested class
    static class Nested {
        void display() {
            System.out.println(message);  // Can access static members
        }
    }
}

public class Test {
    public static void main(String[] args) {
        // No need for Outer class instance
        Outer.Nested nested = new Outer.Nested();
        nested.display();
    }
}
```

**Key Points:**
- Can have static members
- Can only access static members of outer class
- Don't need outer class instance

---

## 143. How to instantiate static nested classes in Java?

Use `OuterClass.NestedClass` syntax.

```java
class Outer {
    static class Nested {
        void display() {
            System.out.println("Static nested class");
        }
    }
}

public class Test {
    public static void main(String[] args) {
        // Create instance directly
        Outer.Nested nested = new Outer.Nested();
        nested.display();
    }
}
```

**Comparison:**

```java
// Static nested class - No outer instance needed
Outer.StaticNested obj = new Outer.StaticNested();

// Inner class - Needs outer instance
Outer outer = new Outer();
Outer.Inner obj = outer.new Inner();
```

---

## 144. Explain about method local inner classes or local inner classes

**Local inner class** is defined inside a method.

```java
class Outer {
    void outerMethod() {
        // Local inner class
        class LocalInner {
            void display() {
                System.out.println("Local inner class");
            }
        }
        
        // Can only be used within this method
        LocalInner inner = new LocalInner();
        inner.display();
    }
}

public class Test {
    public static void main(String[] args) {
        Outer outer = new Outer();
        outer.outerMethod();
    }
}
```

**Key Points:**
- Scope limited to the method
- Can access method's final or effectively final variables
- Cannot have access modifiers (public, private, etc.)

---

## 145. Explain about features of local inner class

**Features:**

**1. Can access method local variables (if final or effectively final)**
```java
class Outer {
    void method() {
        final int num = 10;
        
        class LocalInner {
            void display() {
                System.out.println(num);  // OK - final variable
            }
        }
        
        LocalInner inner = new LocalInner();
        inner.display();
    }
}
```

**2. Can access outer class members**
```java
class Outer {
    private String message = "Hello";
    
    void method() {
        class LocalInner {
            void display() {
                System.out.println(message);  // Can access outer class member
            }
        }
    }
}
```

**3. Cannot have static members** (except static final constants)

**4. Cannot have access modifiers**

---

## 146. Explain about anonymous inner classes in Java

**Anonymous inner class** is a class without a name, defined and instantiated in a single expression.

```java
interface Greeting {
    void sayHello();
}

public class Test {
    public static void main(String[] args) {
        // Anonymous inner class
        Greeting greeting = new Greeting() {
            public void sayHello() {
                System.out.println("Hello from anonymous class");
            }
        };
        
        greeting.sayHello();
    }
}
```

**With abstract class:**
```java
abstract class Animal {
    abstract void sound();
}

public class Test {
    public static void main(String[] args) {
        Animal dog = new Animal() {
            void sound() {
                System.out.println("Bark");
            }
        };
        
        dog.sound();
    }
}
```

**Use case:** One-time use implementations

---

## 147. Explain restrictions for using anonymous inner classes

**Restrictions:**

**1. Cannot have constructor**
```java
// Cannot do this
new Runnable() {
    Runnable() { }  // Error! No constructor
    public void run() { }
};
```

**2. Cannot implement multiple interfaces**
```java
// Error! Can only extend one class OR implement one interface
new Runnable, Comparable() { };  // Not allowed
```

**3. Cannot have static members** (except static final constants)

**4. Cannot extend a class and implement interface simultaneously**

**5. Can only access final or effectively final local variables**
```java
void method() {
    int num = 10;
    // num = 20;  // Would make it non-effectively-final
    
    Runnable r = new Runnable() {
        public void run() {
            System.out.println(num);  // OK - effectively final
        }
    };
}
```

---

## 148. Is this valid? Can we instantiate interface in Java?

**We cannot directly instantiate an interface, but we can create an anonymous implementation.**

```java
interface MyInterface {
    void display();
}

public class Test {
    public static void main(String[] args) {
        // This is NOT instantiating interface
        // It's creating anonymous class that implements interface
        MyInterface obj = new MyInterface() {
            public void display() {
                System.out.println("Implementation");
            }
        };
        
        obj.display();
    }
}
```

**What's happening:**
1. Creating an anonymous class that implements `MyInterface`
2. Creating an instance of that anonymous class
3. NOT creating an instance of the interface itself

**Invalid:**
```java
MyInterface obj = new MyInterface();  // Error! Cannot instantiate interface
```

---

## 149. Explain about member inner classes

**Member inner class** is a non-static class defined as a member of the outer class.

```java
class Outer {
    private String name = "Outer";
    
    // Member inner class
    class Inner {
        void display() {
            System.out.println("Outer name: " + name);
            System.out.println("Inner class method");
        }
    }
    
    void createInner() {
        Inner inner = new Inner();
        inner.display();
    }
}

public class Test {
    public static void main(String[] args) {
        Outer outer = new Outer();
        outer.createInner();
        
        // Or create directly
        Outer.Inner inner = outer.new Inner();
        inner.display();
    }
}
```

**Features:**
- Can access all outer class members (including private)
- Cannot have static members (except static final)
- Needs outer class instance

---

## 150. How to instantiate member inner class?

**Two ways:**

**1. From within outer class**
```java
class Outer {
    class Inner {
        void display() {
            System.out.println("Inner class");
        }
    }
    
    void createInner() {
        Inner inner = new Inner();  // Simple
        inner.display();
    }
}
```

**2. From outside outer class**
```java
public class Test {
    public static void main(String[] args) {
        // Step 1: Create outer class instance
        Outer outer = new Outer();
        
        // Step 2: Create inner class instance using outer instance
        Outer.Inner inner = outer.new Inner();
        inner.display();
    }
}
```

**Syntax:** `outerInstance.new InnerClass()`

---

## 151. How to do encapsulation in Java?

**Encapsulation** = Data hiding + Controlled access

**Steps:**

**1. Make variables private**
**2. Provide public getter/setter methods**

```java
class Student {
    // Private variables
    private String name;
    private int age;
    
    // Public getter
    public String getName() {
        return name;
    }
    
    // Public setter with validation
    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        if (age > 0 && age < 100) {
            this.age = age;
        }
    }
}

public class Test {
    public static void main(String[] args) {
        Student student = new Student();
        student.setName("John");
        student.setAge(20);
        
        System.out.println(student.getName());
        System.out.println(student.getAge());
    }
}
```

**Benefits:**
- Data security
- Validation control
- Flexibility to change implementation

---

## 152. What are reference variables in Java?

**Reference variable** is a variable that holds the reference (memory address) of an object.

```java
class Dog {
    String name;
}

public class Test {
    public static void main(String[] args) {
        Dog dog1 = new Dog();  // dog1 is reference variable
        dog1.name = "Buddy";
        
        Dog dog2 = dog1;  // dog2 points to same object as dog1
        dog2.name = "Max";
        
        System.out.println(dog1.name);  // Max (both point to same object)
        System.out.println(dog2.name);  // Max
    }
}
```

**Diagram:**
```
dog1 ──┐
       ├──> [Object in Heap: name="Max"]
dog2 ──┘
```

**Key Points:**
- Stores memory address, not actual object
- Multiple references can point to same object
- Default value is `null`

---

## 153. Will compiler create default constructor if there's a parameterized constructor?

**No**, if you define ANY constructor, compiler will NOT create a default constructor.

```java
class Student {
    String name;
    
    // Parameterized constructor
    Student(String name) {
        this.name = name;
    }
}

public class Test {
    public static void main(String[] args) {
        // Student s = new Student();  // Error! No default constructor
        Student s = new Student("John");  // Must use parameterized
    }
}
```

**Solution:** Explicitly define default constructor if needed
```java
class Student {
    String name;
    
    // Default constructor
    Student() {
    }
    
    // Parameterized constructor
    Student(String name) {
        this.name = name;
    }
}
```

---

## 154. Can we have a method name same as class name?

**Yes**, but it's a regular method, not a constructor.

```java
class Test {
    // Constructor (no return type)
    Test() {
        System.out.println("Constructor");
    }
    
    // Regular method (has return type)
    void Test() {
        System.out.println("Method");
    }
    
    public static void main(String[] args) {
        Test t = new Test();  // Calls constructor
        t.Test();             // Calls method
    }
}
```

**Output:**
```
Constructor
Method
```

**Difference:**
- Constructor: No return type
- Method: Has return type (even void)

---

## 155. Can we override constructors in Java?

**No**, constructors cannot be overridden.

**Why?**
- Constructors are not inherited
- Overriding requires inheritance
- Each class has its own constructors

**But you can overload constructors:**
```java
class Student {
    // Constructor overloading (not overriding)
    Student() {
        System.out.println("Default");
    }
    
    Student(String name) {
        System.out.println("Name: " + name);
    }
}
```

**And call parent constructor:**
```java
class Parent {
    Parent() {
        System.out.println("Parent constructor");
    }
}

class Child extends Parent {
    Child() {
        super();  // Calls parent constructor
        System.out.println("Child constructor");
    }
}
```

---

## 156. Can static methods access instance variables?

**No**, static methods cannot access instance variables directly.

```java
class Test {
    int instanceVar = 10;
    static int staticVar = 20;
    
    static void staticMethod() {
        // System.out.println(instanceVar);  // Error! Cannot access
        System.out.println(staticVar);       // OK
        
        // Solution: Create object
        Test obj = new Test();
        System.out.println(obj.instanceVar);  // OK
    }
}
```

**Why?**
- Static methods belong to class, not object
- Instance variables belong to object
- When static method runs, objects might not exist

---

## 157. How do we access static members in Java?

**Two ways:**

**1. Using class name (recommended)**
```java
class Calculator {
    static int add(int a, int b) {
        return a + b;
    }
}

public class Test {
    public static void main(String[] args) {
        int result = Calculator.add(10, 20);  // Using class name
        System.out.println(result);
    }
}
```

**2. Using object reference (not recommended)**
```java
Calculator calc = new Calculator();
int result = calc.add(10, 20);  // Works but not recommended
```

**Best practice:** Always use class name for static members

---

## 158. Can we override static methods in Java?

**No**, static methods cannot be overridden. They are **hidden**, not overridden.

```java
class Parent {
    static void display() {
        System.out.println("Parent static method");
    }
}

class Child extends Parent {
    // This is method hiding, not overriding
    static void display() {
        System.out.println("Child static method");
    }
}

public class Test {
    public static void main(String[] args) {
        Parent p1 = new Parent();
        p1.display();  // Parent static method
        
        Child c1 = new Child();
        c1.display();  // Child static method
        
        Parent p2 = new Child();
        p2.display();  // Parent static method (NOT Child!)
    }
}
```

**Key difference:**
- **Overriding:** Runtime polymorphism (dynamic binding)
- **Hiding:** Compile-time resolution (static binding)

---

## 159. Difference between object and reference?

| Object | Reference |
|--------|-----------|
| Actual entity in heap memory | Variable that points to object |
| Created using `new` keyword | Declared with class type |
| Has actual data | Has memory address |
| Can exist without reference | Cannot access object without reference |

```java
// Dog is class
Dog myDog = new Dog();

// myDog = reference variable (points to object)
// new Dog() = object in heap memory
```

**Diagram:**
```
Stack                    Heap
┌──────────┐            ┌──────────┐
│ myDog    │───────────>│ Dog      │
│ (reference)│          │ (object) │
└──────────┘            └──────────┘
```

**Example:**
```java
Dog d1 = new Dog();  // 1 reference, 1 object
Dog d2 = d1;         // 2 references, 1 object
new Dog();           // 0 references, 1 object (eligible for GC)
```

---

## 160. Objects or references - which gets garbage collected?

**Objects get garbage collected, not references.**

```java
Dog dog1 = new Dog();  // Object created
dog1 = null;           // Reference removed, object eligible for GC

Dog dog2 = new Dog();  // Object created
dog2 = new Dog();      // First object eligible for GC (no reference)
```

**Garbage Collection triggers when:**
- No more references pointing to object
- Object is unreachable

**Example:**
```java
Dog d1 = new Dog();  // Object A
Dog d2 = new Dog();  // Object B

d1 = d2;  // Object A eligible for GC
d2 = null;
d1 = null;  // Object B eligible for GC
```

---

## 161. How many times is finalize() invoked? Who invokes it?

**finalize()** is invoked **only once** by the **Garbage Collector** before destroying the object.

```java
class MyClass {
    protected void finalize() throws Throwable {
        System.out.println("finalize() called");
        // Cleanup code
    }
}

public class Test {
    public static void main(String[] args) {
        MyClass obj = new MyClass();
        obj = null;  // Make object eligible for GC
        
        System.gc();  // Request garbage collection
        
        // Output: finalize() called (maybe - not guaranteed)
    }
}
```

**Key Points:**
- Called by Garbage Collector
- Called only once per object
- Not guaranteed to be called
- Deprecated in Java 9+

---

## 162. Can we pass objects as arguments in Java?

**Yes**, objects can be passed as arguments (pass by reference value).

```java
class Student {
    String name;
    
    Student(String name) {
        this.name = name;
    }
}

class Test {
    // Method accepting object as parameter
    static void changeName(Student student) {
        student.name = "Changed";
    }
    
    public static void main(String[] args) {
        Student s = new Student("John");
        System.out.println("Before: " + s.name);  // John
        
        changeName(s);
        System.out.println("After: " + s.name);   // Changed
    }
}
```

**Note:** Java passes a copy of the reference, so changes to object are reflected.

---

## 163. Explain wrapper classes in Java

**Wrapper classes** convert primitive types to objects.

```java
// Primitive to Object (Boxing)
int num = 10;
Integer obj = Integer.valueOf(num);

// Object to Primitive (Unboxing)
int num2 = obj.intValue();

// Autoboxing (Java 5+)
Integer obj2 = 20;  // Automatic

// Auto-unboxing
int num3 = obj2;  // Automatic
```

---

## 164. Different types of wrapper classes

| Primitive | Wrapper Class |
|-----------|---------------|
| byte | Byte |
| short | Short |
| int | Integer |
| long | Long |
| float | Float |
| double | Double |
| char | Character |
| boolean | Boolean |

```java
Integer i = 10;
Double d = 10.5;
Character c = 'A';
Boolean b = true;
```

---

## 165. Explain about transient variables

**transient** keyword prevents variables from being serialized.

```java
class Student implements Serializable {
    String name;
    transient String password;  // Won't be serialized
}
```

---

## 166. Can we serialize static variables?

**No**, static variables are not serialized because they belong to the class, not the object.

---

## 167. What is type conversion?

**Type conversion** is converting one data type to another.

```java
int num = 10;
double d = num;  // Implicit conversion (widening)

double d2 = 10.5;
int num2 = (int) d2;  // Explicit conversion (narrowing)
```

---

## 168. Automatic type conversion

**Automatic (implicit) type conversion** happens when converting smaller type to larger type.

```java
byte b = 10;
int i = b;      // byte → int (automatic)
long l = i;     // int → long (automatic)
float f = l;    // long → float (automatic)
double d = f;   // float → double (automatic)
```

---

## 169. Narrowing conversion

**Narrowing conversion** requires explicit casting (larger to smaller type).

```java
double d = 10.99;
int i = (int) d;  // 10 (decimal part lost)

long l = 1000L;
int num = (int) l;
```

---

## 170-178. Various Java Concepts

(Continued in the file...)
