# Java Fundamentals

## 1. What are static blocks and static initializers in Java?

**Static blocks** are used to initialize static variables. They run only once when the class is loaded into memory.

```java
class Example {
    static int count;
    
    // Static block
    static {
        count = 10;
        System.out.println("Static block executed");
    }
}
```

**Key Points:**
- Executes before the main method
- Runs only once when class loads
- Used to initialize static variables

---

## 2. How to call one constructor from another constructor?

Use the `this()` keyword to call another constructor in the same class.

```java
class Student {
    String name;
    int age;
    
    // Constructor 1
    Student() {
        this("Unknown", 0);  // Calling constructor 2
    }
    
    // Constructor 2
    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

**Key Points:**
- Use `this()` to call another constructor
- Must be the first statement
- Called constructor chaining

---

## 3. What is method overriding in Java?

When a child class provides its own implementation of a method that is already defined in the parent class.

```java
class Animal {
    void sound() {
        System.out.println("Animal makes sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}
```

**Key Points:**
- Same method name, same parameters
- Happens in parent-child relationship
- Runtime polymorphism

---

## 4. What is super keyword in Java?

`super` is used to refer to the parent class. It can access parent class methods, variables, and constructors.

```java
class Parent {
    String name = "Parent";
    
    void display() {
        System.out.println("Parent method");
    }
}

class Child extends Parent {
    String name = "Child";
    
    void show() {
        System.out.println(super.name);  // Access parent variable
        super.display();                  // Call parent method
    }
}
```

**Key Points:**
- Refers to parent class
- Can call parent methods and variables
- `super()` calls parent constructor

---

## 5. Difference between method overloading and method overriding

| Method Overloading | Method Overriding |
|-------------------|-------------------|
| Same method name, different parameters | Same method name, same parameters |
| In the same class | In parent-child classes |
| Compile-time polymorphism | Runtime polymorphism |
| Can have different return types | Must have same return type |

**Example:**

```java
// Overloading
class Calculator {
    int add(int a, int b) { return a + b; }
    int add(int a, int b, int c) { return a + b + c; }
}

// Overriding
class Parent {
    void show() { System.out.println("Parent"); }
}
class Child extends Parent {
    void show() { System.out.println("Child"); }
}
```

---

## 6. Difference between abstract class and interface

| Abstract Class | Interface |
|---------------|-----------|
| Can have both abstract and concrete methods | All methods are abstract (before Java 8) |
| Can have constructors | Cannot have constructors |
| Can have instance variables | Only constants (public static final) |
| Use `extends` keyword | Use `implements` keyword |
| Single inheritance | Multiple inheritance |

```java
// Abstract class
abstract class Animal {
    String name;
    abstract void sound();
    void sleep() { System.out.println("Sleeping"); }
}

// Interface
interface Flyable {
    void fly();
}
```

---

## 7. Why Java is platform independent?

Java is platform independent because it uses **bytecode** and the **JVM** (Java Virtual Machine).

```
Java Source Code (.java)
         ↓
   Java Compiler
         ↓
   Bytecode (.class)
         ↓
   JVM (Windows/Mac/Linux)
         ↓
   Machine Code
```

**How it works:**
1. Java code is compiled to bytecode (not machine code)
2. Bytecode can run on any platform that has JVM
3. JVM converts bytecode to machine-specific code
4. "Write Once, Run Anywhere" (WORA)

---

## 8. What is method overloading in Java?

Having multiple methods with the same name but different parameters in the same class.

```java
class Printer {
    void print(int a) {
        System.out.println("Integer: " + a);
    }
    
    void print(String s) {
        System.out.println("String: " + s);
    }
    
    void print(int a, int b) {
        System.out.println("Two integers: " + a + ", " + b);
    }
}
```

**Rules:**
- Different number of parameters, OR
- Different types of parameters, OR
- Different order of parameters

---

## 9. What is difference between C++ and Java?

| Feature | C++ | Java |
|---------|-----|------|
| Platform | Platform dependent | Platform independent |
| Pointers | Supports pointers | No pointers |
| Memory Management | Manual (new/delete) | Automatic (Garbage Collection) |
| Multiple Inheritance | Supported | Not supported (uses interfaces) |
| Operator Overloading | Supported | Not supported |
| Goto | Supported | Not supported |
| Compiler & Interpreter | Compiler only | Both compiler and interpreter |

---

## 10. What is JIT compiler?

**JIT (Just-In-Time)** compiler converts bytecode into machine code at runtime for faster execution.

```
Bytecode → JIT Compiler → Native Machine Code
```

**How it works:**
1. JVM loads bytecode
2. JIT compiler identifies frequently used code (hot spots)
3. Converts hot spots to native machine code
4. Stores compiled code in cache
5. Next time, uses cached machine code (faster)

**Benefits:**
- Improves performance
- Faster than pure interpretation
- Optimizes code during runtime

---

## 11. What is bytecode in Java?

Bytecode is the intermediate, platform-independent code generated by the Java compiler.

```
Source Code (.java) → Compiler → Bytecode (.class) → JVM → Machine Code
```

**Characteristics:**
- Not human-readable
- Not machine-specific
- Can run on any JVM
- File extension: .class

**Example:**
```java
// HelloWorld.java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}

// After compilation → HelloWorld.class (bytecode)
```

---

## 12. Difference between this() and super() in Java

| this() | super() |
|--------|---------|
| Refers to current class | Refers to parent class |
| Calls current class constructor | Calls parent class constructor |
| Must be first statement | Must be first statement |
| Can't use both together | Can't use both together |

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
    
    Child(String name) {
        this();   // Calls Child() constructor
    }
}
```

---

## 13. What is a class?

A **class** is a blueprint or template for creating objects. It defines properties (variables) and behaviors (methods).

```java
class Car {
    // Properties (variables)
    String color;
    String model;
    
    // Behaviors (methods)
    void start() {
        System.out.println("Car started");
    }
    
    void stop() {
        System.out.println("Car stopped");
    }
}
```

**Think of it like:**
- Class = Blueprint of a house
- Object = Actual house built from blueprint

---

## 14. What is an object?

An **object** is an instance of a class. It has actual values for the properties defined in the class.

```java
class Dog {
    String name;
    int age;
}

public class Main {
    public static void main(String[] args) {
        // Creating objects
        Dog dog1 = new Dog();
        dog1.name = "Buddy";
        dog1.age = 3;
        
        Dog dog2 = new Dog();
        dog2.name = "Max";
        dog2.age = 5;
    }
}
```

**Key Points:**
- Created using `new` keyword
- Has state (variable values) and behavior (methods)
- Multiple objects can be created from one class

---

## 15. What is method in Java?

A **method** is a block of code that performs a specific task. It is defined inside a class.

```java
class Calculator {
    // Method with return type
    int add(int a, int b) {
        return a + b;
    }
    
    // Method without return type
    void display() {
        System.out.println("Hello");
    }
}
```

**Method Structure:**
```
returnType methodName(parameters) {
    // method body
    return value;
}
```

**Benefits:**
- Code reusability
- Makes code organized
- Easy to maintain

---

## 16. What is encapsulation?

**Encapsulation** means wrapping data (variables) and code (methods) together and hiding internal details.

```java
class BankAccount {
    // Private variables (hidden)
    private double balance;
    
    // Public methods (controlled access)
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
    
    public double getBalance() {
        return balance;
    }
}
```

**Key Points:**
- Use `private` for variables
- Use `public` getter/setter methods
- Provides data security and control

**Real-world example:** TV remote (you press buttons without knowing internal circuit)

---

## 17. Why main() method is public, static and void in Java?

```java
public static void main(String[] args) {
    // code
}
```

- **public**: JVM can access it from anywhere
- **static**: JVM can call it without creating an object
- **void**: Doesn't return anything to JVM
- **String[] args**: Command-line arguments

**Why static?**
If not static, JVM would need to create an object first, but which constructor to call? That's why it's static.

---

## 18. Explain about main() method in Java

The **main()** method is the entry point of any Java program. JVM starts execution from here.

```java
public class Test {
    public static void main(String[] args) {
        System.out.println("Program starts here");
    }
}
```

**Rules:**
- Must be `public static void`
- Must be named `main`
- Must accept `String[] args`
- Only one main method per class (but can be overloaded)

**Command-line arguments example:**
```bash
java Test arg1 arg2 arg3
```
```java
public static void main(String[] args) {
    System.out.println(args[0]); // prints: arg1
}
```

---

## 19. What is constructor in Java?

A **constructor** is a special method used to initialize objects. It has the same name as the class and no return type.

```java
class Student {
    String name;
    int age;
    
    // Default constructor
    Student() {
        name = "Unknown";
        age = 0;
    }
    
    // Parameterized constructor
    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

**Key Points:**
- Same name as class
- No return type (not even void)
- Called automatically when object is created
- Can be overloaded

---

## 20. What is difference between length and length() method in Java?

| length | length() |
|--------|----------|
| Used for arrays | Used for Strings |
| It's a variable | It's a method |
| No parentheses | Has parentheses |

```java
// length (for arrays)
int[] numbers = {1, 2, 3, 4, 5};
System.out.println(numbers.length);  // Output: 5

// length() (for Strings)
String name = "Java";
System.out.println(name.length());   // Output: 4
```

---

## 21. What is ASCII Code?

**ASCII (American Standard Code for Information Interchange)** is a character encoding standard that represents characters as numbers.

```
Character  →  ASCII Code
   'A'     →     65
   'B'     →     66
   'a'     →     97
   'b'     →     98
   '0'     →     48
   ' '     →     32
```

**Example:**
```java
char ch = 'A';
int ascii = ch;
System.out.println(ascii);  // Output: 65

int code = 66;
char character = (char) code;
System.out.println(character);  // Output: B
```

**Range:** 0 to 127 (128 characters)

---

## 22. What is Unicode?

**Unicode** is a universal character encoding standard that supports characters from all languages worldwide.

```
ASCII:   128 characters
Unicode: 143,859+ characters (including emojis, Chinese, Arabic, etc.)
```

**Example:**
```java
char ch1 = 'A';        // English
char ch2 = '中';       // Chinese
char ch3 = '😊';       // Emoji

System.out.println(ch1);  // A
System.out.println(ch2);  // 中
System.out.println(ch3);  // 😊
```

**Format:** `\uXXXX` where XXXX is hexadecimal
```java
char ch = '\u0041';  // Unicode for 'A'
System.out.println(ch);  // Output: A
```

---

## 23. Difference between Character Constant and String Constant in Java

| Character Constant | String Constant |
|-------------------|-----------------|
| Single character | Multiple characters |
| Uses single quotes ' ' | Uses double quotes " " |
| Type: char | Type: String |
| 2 bytes | Multiple bytes |

```java
// Character constant
char ch = 'A';        // Correct
char ch = "A";        // Error
char ch = 'AB';       // Error (only one character)

// String constant
String str = "Hello"; // Correct
String str = 'Hello'; // Error
String str = "A";     // Correct (String with one character)
```

---

## 24. What are constants and how to create constants in Java?

**Constants** are values that cannot be changed once assigned. Use the `final` keyword.

```java
class Circle {
    // Constant
    final double PI = 3.14159;
    
    // Static constant (shared by all objects)
    static final int MAX_SIZE = 100;
    
    void calculate() {
        // PI = 3.14;  // Error: cannot change constant
    }
}
```

**Naming Convention:** Use UPPERCASE with underscores
```java
final int MAX_VALUE = 100;
final String DATABASE_URL = "jdbc:mysql://localhost:3306";
```

---

## 25. Difference between '>>' and '>>>' operators in Java

Both are **right shift** operators but handle the sign bit differently.

| >> (Signed Right Shift) | >>> (Unsigned Right Shift) |
|------------------------|---------------------------|
| Preserves sign bit | Fills with 0 |
| For positive: fills with 0 | Always fills with 0 |
| For negative: fills with 1 | Always fills with 0 |

```java
int a = 16;   // Binary: 00010000
int b = a >> 2;  // Result: 4 (Binary: 00000100)

int c = -16;  // Binary: 11110000 (in 2's complement)
int d = c >> 2;   // Result: -4 (preserves sign)
int e = c >>> 2;  // Result: large positive number (fills with 0)

System.out.println(b);  // 4
System.out.println(d);  // -4
System.out.println(e);  // 1073741820
```

---

## Java Coding Standards

## 26. Java Coding Standards for Classes

**Rules:**
- **PascalCase**: First letter of each word capitalized
- Noun or noun phrase
- Should be meaningful

```java
// Good
class Employee { }
class StudentRecord { }
class BankAccount { }

// Bad
class employee { }      // Should be PascalCase
class Emp { }          // Not meaningful
class MyClass123 { }   // Poor naming
```

---

## 27. Java Coding Standards for Interfaces

**Rules:**
- **PascalCase**: Same as classes
- Usually adjective (ending with -able, -ible)
- Should describe capability

```java
// Good
interface Runnable { }
interface Serializable { }
interface Comparable { }
interface PaymentProcessor { }

// Bad
interface runnable { }     // Should be PascalCase
interface IMyInterface { } // Don't use 'I' prefix
```

---

## 28. Java Coding Standards for Methods

**Rules:**
- **camelCase**: First letter lowercase, then capital for each word
- Should be verb or verb phrase
- Meaningful names

```java
class Example {
    // Good
    void calculateSalary() { }
    int getAge() { }
    void setName(String name) { }
    boolean isValid() { }
    
    // Bad
    void CalculateSalary() { }  // Should start with lowercase
    void calc() { }             // Not meaningful
    void method1() { }          // Poor naming
}
```

---

## 29. Java Coding Standards for Variables

**Rules:**
- **camelCase**: First letter lowercase
- Should be noun
- Meaningful names
- No single-letter names (except in loops)

```java
class Person {
    // Good
    String firstName;
    int studentAge;
    boolean isActive;
    
    // Bad
    String FirstName;   // Should be camelCase
    int a;             // Not meaningful
    String s1, s2;     // Poor naming
    
    // Acceptable in loops
    for (int i = 0; i < 10; i++) { }
}
```

---

## 30. Java Coding Standards for Constants

**Rules:**
- **UPPER_CASE**: All letters uppercase
- Words separated by underscore
- Use `static final`

```java
class Configuration {
    // Good
    static final int MAX_SIZE = 100;
    static final String DATABASE_URL = "jdbc:mysql://localhost";
    static final double PI = 3.14159;
    
    // Bad
    static final int maxSize = 100;     // Should be UPPER_CASE
    static final String DatabaseUrl;    // Should use underscore
}
```

---

## More Core Concepts

## 31. Difference between overriding and overloading in Java

*(Same as question 5 - see above)*

---

## 32. What is 'IS-A' relationship in Java?

**IS-A** represents **inheritance**. It means one class is a type of another class.

```java
class Animal { }
class Dog extends Animal { }  // Dog IS-A Animal

class Vehicle { }
class Car extends Vehicle { }  // Car IS-A Vehicle
```

**Diagram:**
```
    Animal
      ↑
      |
     Dog  (Dog IS-A Animal)
```

**Use `extends` keyword**

---

## 33. What is 'HAS-A' relationship in Java?

**HAS-A** represents **composition**. It means one class contains another class as a member.

```java
class Engine {
    void start() { }
}

class Car {
    Engine engine = new Engine();  // Car HAS-A Engine
}

class Student {
    Address address;  // Student HAS-A Address
}
```

**Diagram:**
```
    Car
     |
   has-a
     |
   Engine
```

---

## 34. Difference between 'IS-A' and 'HAS-A' relationship in Java

| IS-A (Inheritance) | HAS-A (Composition) |
|-------------------|---------------------|
| Uses `extends` | Uses instance variable |
| "is a type of" | "has a" |
| Dog is an Animal | Car has an Engine |
| Tighter coupling | Loose coupling |

```java
// IS-A
class Animal { }
class Dog extends Animal { }  // Dog IS-A Animal

// HAS-A
class Engine { }
class Car {
    Engine engine;  // Car HAS-A Engine
}
```

---

## 35. Explain about instanceof operator in Java

**instanceof** checks if an object is an instance of a specific class or interface.

```java
class Animal { }
class Dog extends Animal { }

public class Test {
    public static void main(String[] args) {
        Dog dog = new Dog();
        
        System.out.println(dog instanceof Dog);      // true
        System.out.println(dog instanceof Animal);   // true
        System.out.println(dog instanceof Object);   // true
        
        Animal animal = new Animal();
        System.out.println(animal instanceof Dog);   // false
    }
}
```

**Syntax:** `object instanceof ClassName`

**Returns:** true or false

**Use case:** Before typecasting to avoid ClassCastException
```java
if (animal instanceof Dog) {
    Dog dog = (Dog) animal;  // Safe casting
}
```

---

## 36. What does null mean in Java?

**null** means the reference variable is not pointing to any object.

```java
String name = null;  // name is not pointing to any String object

Person person = null;  // person is not pointing to any Person object

// This will cause NullPointerException
System.out.println(name.length());  // Error!

// Correct way
if (name != null) {
    System.out.println(name.length());
}
```

**Key Points:**
- Default value for reference variables
- Not applicable to primitive types
- Calling methods on null causes NullPointerException

---

## 37. Can we have multiple classes in a single file?

**Yes**, but only **one public class** per file, and the file name must match the public class name.

```java
// File: Student.java

public class Student {  // Public class - must match filename
    // code
}

class Teacher {  // Non-public class - OK
    // code
}

class Subject {  // Non-public class - OK
    // code
}
```

**Rules:**
- Maximum one public class per file
- Filename = public class name
- Multiple non-public classes allowed

---

## 38. What all access modifiers are allowed for top-level class?

Only **two** access modifiers for top-level classes:

1. **public** - Accessible from anywhere
2. **default (no modifier)** - Accessible only within same package

```java
// Allowed
public class MyClass { }      // public
class MyClass { }            // default

// Not allowed
private class MyClass { }    // Error!
protected class MyClass { }  // Error!
```

---

## 39. What are packages in Java?

**Package** is a group of related classes and interfaces. It's like a folder for organizing code.

```java
package com.company.project;

public class Employee {
    // code
}
```

**Benefits:**
- Organizes classes
- Avoids name conflicts
- Provides access control
- Easy to maintain

**Example Structure:**
```
com
 └── company
      └── project
           ├── Employee.java
           ├── Manager.java
           └── Department.java
```

---

## 40. Can we have more than one package statement in source file?

**No**, only **one package statement** is allowed, and it must be the **first statement** in the file.

```java
// Correct
package com.example;

import java.util.*;

public class Test { }

// Wrong
package com.example;
package com.another;  // Error! Only one package statement allowed
```

---

## 41. Can we define package statement after import statement in Java?

**No**, package statement must be the **first statement** before any import statements.

```java
// Wrong
import java.util.*;
package com.example;  // Error!

// Correct
package com.example;
import java.util.*;

public class Test { }
```

**Order:**
1. Package statement (optional, but if present, must be first)
2. Import statements
3. Class definition

---

## 42. What are identifiers in Java?

**Identifiers** are names given to classes, variables, methods, packages, etc.

```java
class Student {           // Student is identifier
    String name;          // name is identifier
    int age;              // age is identifier
    
    void display() {      // display is identifier
        int marks = 90;   // marks is identifier
    }
}
```

**Rules:**
- Can contain letters, digits, underscore (_), dollar sign ($)
- Must start with letter, underscore, or dollar sign
- Cannot start with digit
- Cannot be a keyword
- Case-sensitive

```java
// Valid
int age;
int _age;
int $age;
int age123;

// Invalid
int 123age;   // Cannot start with digit
int int;      // Cannot be keyword
int my-age;   // Cannot have hyphen
```

---

## 43. What are access modifiers in Java?

**Access modifiers** control the visibility/accessibility of classes, methods, and variables.

**Four types:**

| Modifier | Class | Package | Subclass | World |
|----------|-------|---------|----------|-------|
| public | ✓ | ✓ | ✓ | ✓ |
| protected | ✓ | ✓ | ✓ | ✗ |
| default | ✓ | ✓ | ✗ | ✗ |
| private | ✓ | ✗ | ✗ | ✗ |

```java
public class Example {
    public int a = 10;        // Accessible everywhere
    protected int b = 20;     // Accessible in package and subclasses
    int c = 30;               // Default - accessible in package only
    private int d = 40;       // Accessible only in this class
}
```

---

## 44. What is the difference between access specifiers and access modifiers in Java?

In Java, **access specifiers** and **access modifiers** are the **same thing**. These terms are used interchangeably.

Both refer to: **public, private, protected, default**

Other modifiers (not access modifiers):
- static
- final
- abstract
- synchronized
- volatile

---

## 45. What access modifiers can be used for class?

**Top-level class:** Only 2 options
- **public** - accessible everywhere
- **default (no modifier)** - accessible only in same package

```java
public class MyClass { }   // OK
class MyClass { }          // OK (default)

private class MyClass { }    // Error!
protected class MyClass { }  // Error!
```

**Inner class:** All 4 access modifiers allowed
```java
class Outer {
    public class Inner1 { }     // OK
    private class Inner2 { }    // OK
    protected class Inner3 { }  // OK
    class Inner4 { }            // OK (default)
}
```

---

## 46. Explain what access modifiers can be used for methods

**All four** access modifiers can be used for methods:

```java
class Example {
    // Public - accessible everywhere
    public void method1() { }
    
    // Protected - accessible in package and subclasses
    protected void method2() { }
    
    // Default - accessible only in package
    void method3() { }
    
    // Private - accessible only in this class
    private void method4() { }
}
```

**Usage:**
- **public**: API methods, methods to be used by other classes
- **private**: Helper methods, internal implementation
- **protected**: Methods for subclasses to use/override
- **default**: Package-level utility methods

---

## 47. Explain what access modifiers can be used for variables

**All four** access modifiers can be used for variables:

```java
class Example {
    public int a = 10;        // Accessible everywhere
    protected int b = 20;     // Accessible in package and subclasses
    int c = 30;               // Default - accessible in package
    private int d = 40;       // Accessible only in this class
}
```

**Best Practice:**
- Make variables **private**
- Provide **public** getter/setter methods (encapsulation)

```java
class Person {
    private String name;  // Private variable
    
    // Public getter
    public String getName() {
        return name;
    }
    
    // Public setter
    public void setName(String name) {
        this.name = name;
    }
}
```

---

## 48. What is final access modifier in Java?

**final** means the value cannot be changed once assigned.

**1. Final Variable** - Cannot be changed
```java
final int MAX_VALUE = 100;
MAX_VALUE = 200;  // Error! Cannot change
```

**2. Final Method** - Cannot be overridden
```java
class Parent {
    final void display() {
        System.out.println("Parent");
    }
}

class Child extends Parent {
    void display() {  // Error! Cannot override final method
    }
}
```

**3. Final Class** - Cannot be inherited
```java
final class MyClass {
}

class SubClass extends MyClass {  // Error! Cannot extend final class
}
```

---

## 49. Explain about abstract classes in Java

**Abstract class** is a class that cannot be instantiated and may contain abstract methods.

```java
abstract class Animal {
    // Abstract method (no body)
    abstract void sound();
    
    // Concrete method (has body)
    void sleep() {
        System.out.println("Animal is sleeping");
    }
}

class Dog extends Animal {
    // Must implement abstract method
    void sound() {
        System.out.println("Dog barks");
    }
}

public class Test {
    public static void main(String[] args) {
        // Animal a = new Animal();  // Error! Cannot instantiate
        Animal a = new Dog();  // OK
        a.sound();
        a.sleep();
    }
}
```

**Key Points:**
- Use `abstract` keyword
- Cannot create object of abstract class
- Can have both abstract and concrete methods
- Subclass must implement all abstract methods

---

## 50. Can we create constructor in abstract class?

**Yes**, abstract classes can have constructors.

```java
abstract class Animal {
    String name;
    
    // Constructor in abstract class
    Animal(String name) {
        this.name = name;
        System.out.println("Animal constructor called");
    }
    
    abstract void sound();
}

class Dog extends Animal {
    Dog(String name) {
        super(name);  // Calling abstract class constructor
    }
    
    void sound() {
        System.out.println("Dog barks");
    }
}

public class Test {
    public static void main(String[] args) {
        Dog dog = new Dog("Buddy");
        // Output: Animal constructor called
    }
}
```

**Why?**
- To initialize common fields
- Called when subclass object is created

---

## 51. What are abstract methods in Java?

**Abstract methods** are methods without implementation (no body). They are declared with `abstract` keyword.

```java
abstract class Shape {
    // Abstract methods - no body
    abstract void draw();
    abstract double calculateArea();
    
    // Concrete method - has body
    void display() {
        System.out.println("This is a shape");
    }
}

class Circle extends Shape {
    // Must implement abstract methods
    void draw() {
        System.out.println("Drawing circle");
    }
    
    double calculateArea() {
        return 3.14 * 5 * 5;
    }
}
```

**Rules:**
- Declared using `abstract` keyword
- No method body (ends with semicolon)
- Must be in abstract class
- Subclass must implement or be abstract

