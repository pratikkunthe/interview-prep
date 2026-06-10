# Additional Core Java Topics

## 170. Explain the importance of import keyword in Java

**import** allows you to use classes from other packages without using fully qualified names.

```java
// Without import
java.util.ArrayList<String> list = new java.util.ArrayList<>();

// With import
import java.util.ArrayList;

ArrayList<String> list = new ArrayList<>();
```

**Types of imports:**

**1. Specific class import**
```java
import java.util.ArrayList;
import java.util.HashMap;
```

**2. Wildcard import (all classes from package)**
```java
import java.util.*;
```

**3. Static import (for static members)**
```java
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;

double area = PI * 5 * 5;  // No need for Math.PI
```

---

## 171. Explain naming conventions for packages

**Rules:**
- All lowercase letters
- Use domain name in reverse
- Separate words with dots

```java
// Good
package com.company.project;
package org.example.utilities;
package in.amazon.services;

// Bad
package Com.Company.Project;  // Should be lowercase
package myPackage;            // Should use domain
package my_package;           // Use dots, not underscores
```

**Examples:**
```java
package com.google.maps;
package org.apache.commons.lang;
package com.microsoft.azure.storage;
```

---

## 172. What is classpath?

**Classpath** is the path where JVM looks for classes and packages.

```bash
# Set classpath in Windows
set CLASSPATH=C:\myproject\classes;C:\libs\mylib.jar

# Set classpath in Linux/Mac
export CLASSPATH=/myproject/classes:/libs/mylib.jar

# Run with classpath
java -cp /path/to/classes MyClass
```

**Example:**
```
Project Structure:
/myproject
    /classes
        MyClass.class
    /lib
        library.jar
        
# Set classpath
java -cp classes:lib/library.jar MyClass
```

---

## 173. What is jar?

**JAR (Java Archive)** is a compressed file format to package multiple Java classes and resources.

```bash
# Create jar file
jar cf myapp.jar *.class

# Create jar with manifest
jar cfm myapp.jar manifest.txt *.class

# Extract jar
jar xf myapp.jar

# View jar contents
jar tf myapp.jar

# Run jar
java -jar myapp.jar
```

**Benefits:**
- Compress multiple files into one
- Easy distribution
- Faster loading
- Platform independent

---

## 174. What is the scope or lifetime of instance variables?

**Instance variables:**
- Created when object is created
- Destroyed when object is garbage collected
- Scope: Available to all methods in the class
- One copy per object

```java
class Student {
    String name;  // Instance variable
    
    void setName(String n) {
        name = n;  // Accessible
    }
    
    void displayName() {
        System.out.println(name);  // Accessible
    }
}

public class Test {
    public static void main(String[] args) {
        Student s1 = new Student();  // Instance variable created
        s1.name = "John";
        
        Student s2 = new Student();  // Different instance variable
        s2.name = "Mary";
        
        // s1 and s2 have separate copies of 'name'
    }
}
```

**Lifetime:** Object creation → Object destruction (GC)

---

## 175. Explain scope or lifetime of class variables or static variables

**Static variables:**
- Created when class is loaded
- Destroyed when program ends
- Scope: Entire class
- One copy shared by all objects

```java
class Counter {
    static int count = 0;  // Static variable
    
    Counter() {
        count++;
    }
}

public class Test {
    public static void main(String[] args) {
        Counter c1 = new Counter();
        Counter c2 = new Counter();
        Counter c3 = new Counter();
        
        System.out.println(Counter.count);  // 3 (shared by all)
    }
}
```

**Lifetime:** Class loading → Program termination

---

## 176. Explain scope or lifetime of local variables

**Local variables:**
- Created when method/block is entered
- Destroyed when method/block exits
- Scope: Only within the method/block
- Must be initialized before use

```java
class Test {
    void method() {
        int localVar = 10;  // Created when method is called
        System.out.println(localVar);
    }  // Destroyed when method ends
    
    void anotherMethod() {
        // System.out.println(localVar);  // Error! Not accessible
    }
}
```

**Example with blocks:**
```java
void method() {
    int x = 10;
    
    if (x > 5) {
        int y = 20;  // Local to if block
        System.out.println(x);  // OK
        System.out.println(y);  // OK
    }
    
    System.out.println(x);  // OK
    // System.out.println(y);  // Error! y not accessible
}
```

**Lifetime:** Method/block entry → Method/block exit

---

## 177. Explain about static imports in Java

**Static import** allows you to use static members without class name.

```java
// Without static import
import java.lang.Math;

double result = Math.sqrt(25);
double pi = Math.PI;

// With static import
import static java.lang.Math.*;

double result = sqrt(25);     // No need for Math.
double pi = PI;               // No need for Math.
```

**Examples:**

**1. Import specific static member**
```java
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;

double area = PI * 5 * 5;
double root = sqrt(16);
```

**2. Import all static members**
```java
import static java.lang.System.*;

out.println("Hello");  // No need for System.out
```

**Note:** Don't overuse - can make code less readable

---

## 178. Can we define static methods inside interface?

**Yes** (Java 8+), interfaces can have static methods.

```java
interface Calculator {
    // Static method in interface
    static int add(int a, int b) {
        return a + b;
    }
    
    // Abstract method
    int subtract(int a, int b);
}

public class Test {
    public static void main(String[] args) {
        // Call static method using interface name
        int result = Calculator.add(10, 20);
        System.out.println(result);  // 30
    }
}
```

**Key Points:**
- Static methods in interfaces (Java 8+)
- Cannot be overridden
- Called using interface name

---

## 179. Define interface in Java

**Interface** is a contract that defines what methods a class must implement, but not how.

```java
interface Animal {
    void sound();  // Abstract method (no body)
    void eat();
}

class Dog implements Animal {
    public void sound() {
        System.out.println("Bark");
    }
    
    public void eat() {
        System.out.println("Dog eats");
    }
}
```

**Key Points:**
- All methods are public and abstract (by default)
- Variables are public, static, final (constants)
- Cannot be instantiated
- Use `implements` keyword

---

## 180. What is the purpose of interface?

**Purposes:**

**1. Achieve abstraction**
```java
interface Payment {
    void processPayment(double amount);
}
```

**2. Multiple inheritance**
```java
interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

class Duck implements Flyable, Swimmable {
    public void fly() { }
    public void swim() { }
}
```

**3. Define contracts**
```java
interface DatabaseConnection {
    void connect();
    void disconnect();
}
```

**4. Loose coupling**
- Code depends on interface, not implementation

---

## 181. Explain features of interfaces in Java

**Features:**

**1. All methods are public and abstract** (before Java 8)
```java
interface MyInterface {
    void method1();  // public abstract by default
}
```

**2. Can have default methods** (Java 8+)
```java
interface MyInterface {
    default void defaultMethod() {
        System.out.println("Default implementation");
    }
}
```

**3. Can have static methods** (Java 8+)
```java
interface MyInterface {
    static void staticMethod() {
        System.out.println("Static method");
    }
}
```

**4. All variables are public, static, final**
```java
interface Constants {
    int MAX_VALUE = 100;  // public static final
}
```

**5. A class can implement multiple interfaces**
```java
class MyClass implements Interface1, Interface2, Interface3 {
}
```

---

## 182. Explain enumeration in Java

**Enum** is a special class that represents a group of constants.

```java
enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

public class Test {
    public static void main(String[] args) {
        Day today = Day.MONDAY;
        
        if (today == Day.MONDAY) {
            System.out.println("Start of week");
        }
        
        // Loop through all values
        for (Day day : Day.values()) {
            System.out.println(day);
        }
    }
}
```

**With constructor and methods:**
```java
enum Size {
    SMALL(10), MEDIUM(20), LARGE(30);
    
    private int value;
    
    Size(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}

public class Test {
    public static void main(String[] args) {
        Size size = Size.MEDIUM;
        System.out.println(size.getValue());  // 20
    }
}
```

---

## 183. Explain restrictions on using enum

**Restrictions:**

**1. Cannot extend other classes** (already extends Enum)
```java
// Error! Enum already extends java.lang.Enum
enum MyEnum extends SomeClass {
}
```

**2. Can implement interfaces**
```java
interface Printable {
    void print();
}

enum Color implements Printable {
    RED, GREEN, BLUE;
    
    public void print() {
        System.out.println(this);
    }
}
```

**3. Cannot be instantiated with new**
```java
// Error! Cannot instantiate enum
Day day = new Day();
```

**4. Cannot be abstract or final**

**5. Enum constructors are always private**

---

## 184. Explain about field hiding in Java

**Field hiding** occurs when a child class has a field with the same name as the parent class.

```java
class Parent {
    String name = "Parent";
}

class Child extends Parent {
    String name = "Child";  // Hides parent's name
    
    void display() {
        System.out.println(name);         // Child
        System.out.println(super.name);   // Parent
    }
}

public class Test {
    public static void main(String[] args) {
        Child child = new Child();
        child.display();
        
        Parent parent = new Child();
        System.out.println(parent.name);  // Parent (field binding is static)
    }
}
```

**Output:**
```
Child
Parent
Parent
```

**Key difference:** Variables are not overridden, they are hidden

---

## 185. Explain about Varargs in Java

**Varargs (Variable Arguments)** allows a method to accept zero or more arguments.

```java
class Test {
    // Varargs method
    static void display(String... names) {
        for (String name : names) {
            System.out.println(name);
        }
    }
    
    public static void main(String[] args) {
        display();                    // 0 arguments
        display("John");              // 1 argument
        display("John", "Mary");      // 2 arguments
        display("A", "B", "C", "D");  // 4 arguments
    }
}
```

**Rules:**
- Use three dots `...`
- Must be last parameter
- Only one varargs per method

```java
// Valid
void method(int a, String... names) { }

// Invalid
void method(String... names, int a) { }  // Varargs must be last
void method(String... names, int... numbers) { }  // Only one varargs
```

---

## 186. Explain where variables are created in memory

**Memory areas:**

**1. Stack** - Local variables and method calls
**2. Heap** - Objects and instance variables
**3. Method Area** - Static variables and class information

```java
class Student {
    static int count = 0;        // Method Area
    String name;                 // Heap (with object)
    
    void display() {
        int age = 20;           // Stack
        System.out.println(age);
    }
}
```

**Diagram:**
```
Stack               Heap                Method Area
┌──────────┐       ┌──────────┐        ┌──────────┐
│ age=20   │       │ name="John"│      │ count=1  │
│ (local)  │       │ (instance) │      │ (static) │
└──────────┘       └──────────┘        └──────────┘
```

---

## 187. Can we use Switch statement with Strings?

**Yes** (Java 7+), switch works with Strings.

```java
String day = "Monday";

switch (day) {
    case "Monday":
        System.out.println("Start of week");
        break;
    case "Friday":
        System.out.println("End of week");
        break;
    case "Saturday":
    case "Sunday":
        System.out.println("Weekend");
        break;
    default:
        System.out.println("Midweek");
}
```

**Switch supports:**
- byte, short, int, char (all versions)
- Enum (Java 5+)
- String (Java 7+)
- Wrapper classes: Byte, Short, Integer, Character

**Not supported:**
- long, float, double
- Boolean

---

## 188. In Java how do we copy objects?

**Two ways:**

**1. Shallow Copy** - Copies object reference
```java
class Student {
    String name;
    
    Student(String name) {
        this.name = name;
    }
}

public class Test {
    public static void main(String[] args) {
        Student s1 = new Student("John");
        Student s2 = s1;  // Shallow copy (both point to same object)
        
        s2.name = "Mary";
        System.out.println(s1.name);  // Mary (both affected)
    }
}
```

**2. Deep Copy** - Creates new object with same values
```java
class Student implements Cloneable {
    String name;
    
    Student(String name) {
        this.name = name;
    }
    
    // Override clone method
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

public class Test {
    public static void main(String[] args) throws Exception {
        Student s1 = new Student("John");
        Student s2 = (Student) s1.clone();  // Deep copy
        
        s2.name = "Mary";
        System.out.println(s1.name);  // John (separate objects)
        System.out.println(s2.name);  // Mary
    }
}
```

**Manual deep copy:**
```java
class Student {
    String name;
    
    Student(String name) {
        this.name = name;
    }
    
    Student copy() {
        return new Student(this.name);
    }
}

Student s1 = new Student("John");
Student s2 = s1.copy();  // Deep copy
```

---

# Object-Oriented Programming Concepts

## 189. Explain about procedural programming language

**Procedural programming** focuses on functions/procedures that operate on data.

**Characteristics:**
- Programs divided into functions
- Data and functions are separate
- Top-down approach
- Examples: C, Pascal, FORTRAN

```c
// C language example
int balance = 1000;

void withdraw(int amount) {
    balance -= amount;
}

void deposit(int amount) {
    balance += amount;
}
```

**Issues:**
- Data is global and can be modified by any function
- Difficult to maintain large programs
- No data hiding

---

## 190. Explain about object-oriented programming and its features

**OOP** organizes programs around objects that contain both data and methods.

**Core concepts:**
1. **Encapsulation** - Bundling data and methods
2. **Inheritance** - Reusing code from parent class
3. **Polymorphism** - Same method, different behavior
4. **Abstraction** - Hiding complex details

```java
class BankAccount {
    // Encapsulation
    private double balance;
    
    public void deposit(double amount) {
        balance += amount;
    }
    
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        }
    }
}
```

---

## 191. List benefits of object-oriented programming

**Benefits:**

**1. Reusability** - Use existing code
```java
class Animal { }
class Dog extends Animal { }  // Reuses Animal code
```

**2. Data Security** - Encapsulation protects data
```java
private int balance;  // Protected
```

**3. Easy Maintenance** - Changes in one place
```java
// Change method implementation without affecting callers
```

**4. Modularity** - Code organized in classes
```java
class User { }
class Product { }
class Order { }
```

**5. Flexibility** - Polymorphism allows flexibility
```java
Animal animal = new Dog();
animal.sound();  // Can be changed to Cat easily
```

---

## 192. Differences between traditional and object-oriented programming

| Procedural | Object-Oriented |
|-----------|-----------------|
| Function-based | Object-based |
| Top-down approach | Bottom-up approach |
| Data and functions separate | Data and functions together |
| No data hiding | Encapsulation provides data hiding |
| Difficult to modify | Easy to modify |
| Example: C | Example: Java |

---

## 193. Explain OOP concepts in detail

**1. Encapsulation**
```java
class Employee {
    private double salary;
    
    public void setSalary(double salary) {
        if (salary > 0) {
            this.salary = salary;
        }
    }
}
```

**2. Inheritance**
```java
class Vehicle {
    void start() { }
}

class Car extends Vehicle {
    // Inherits start() method
}
```

**3. Polymorphism**
```java
class Animal {
    void sound() { }
}

class Dog extends Animal {
    void sound() { System.out.println("Bark"); }
}

class Cat extends Animal {
    void sound() { System.out.println("Meow"); }
}
```

**4. Abstraction**
```java
abstract class Shape {
    abstract void draw();  // Hide implementation
}
```

---

## 194. What is encapsulation?

*(See question 16 for detailed answer)*

---

## 195. What is inheritance?

**Inheritance** allows a class to acquire properties and methods of another class.

```java
// Parent class
class Animal {
    void eat() {
        System.out.println("Animal eats");
    }
}

// Child class
class Dog extends Animal {
    void bark() {
        System.out.println("Dog barks");
    }
}

public class Test {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.eat();   // Inherited from Animal
        dog.bark();  // Own method
    }
}
```

**Types:**
- Single inheritance
- Multilevel inheritance
- Hierarchical inheritance
- Multiple inheritance (through interfaces)

---

## 196. Explain importance of inheritance in Java

**Benefits:**

**1. Code Reusability**
```java
class Employee {
    String name;
    void work() { }
}

class Manager extends Employee {
    // Reuses name and work()
}
```

**2. Method Overriding**
```java
class Parent {
    void display() { }
}

class Child extends Parent {
    void display() {  // Override
        // Custom implementation
    }
}
```

**3. Polymorphism**
```java
Animal animal = new Dog();
animal.sound();  // Dog's sound
```

**4. Organization**
- Represents real-world relationships
- IS-A relationship

---

## 197. What is polymorphism in Java?

**Polymorphism** means "many forms" - same method name, different implementations.

**Types:**

**1. Compile-time (Method Overloading)**
```java
class Calculator {
    int add(int a, int b) {
        return a + b;
    }
    
    int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

**2. Runtime (Method Overriding)**
```java
class Animal {
    void sound() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {
    void sound() {
        System.out.println("Bark");
    }
}

public class Test {
    public static void main(String[] args) {
        Animal animal = new Dog();
        animal.sound();  // Bark (runtime polymorphism)
    }
}
```

**Real-world example:** TV remote - same buttons, different channels

