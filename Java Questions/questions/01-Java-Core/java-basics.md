# Java Core - Basics

## Object-Oriented Programming

### 1. What are OOPS concepts and their disadvantages?

**Answer:**

**Main OOPs Concepts:**
- **Encapsulation** - Hiding data inside a class (like putting money in a locker)
- **Inheritance** - Child class gets properties from parent (like you inherit features from your parents)
- **Polymorphism** - Same thing behaving differently (like a person being a father, son, employee at the same time)
- **Abstraction** - Showing only what's needed, hiding details (like you use a phone without knowing how circuits work)

**Disadvantages:**
- Can make code complex for simple problems
- Uses more memory
- Slower than procedural programming sometimes
- Steep learning curve for beginners

---

### 2. Explain method overloading vs. method overriding

**Answer:**

**Method Overloading** - Same method name, different parameters (in same class)

```java
// Same class, same method name, different parameters
class Calculator {
    int add(int a, int b) {
        return a + b;
    }
    
    int add(int a, int b, int c) {  // Different number of parameters
        return a + b + c;
    }
    
    double add(double a, double b) {  // Different type of parameters
        return a + b;
    }
}
```

**Method Overriding** - Child class changes parent's method behavior

```java
// Parent class
class Animal {
    void sound() {
        System.out.println("Animal makes sound");
    }
}

// Child class overrides parent method
class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}
```

**Simple difference:**
- Overloading = Same class, same name, different parameters
- Overriding = Parent-child relationship, same method signature, different implementation

---

### 3. What is the difference between abstraction and encapsulation?

**Answer:**

**Abstraction** - Hiding complex details, showing only what's needed

Think of a car: You just turn the key and drive. You don't need to know how the engine works internally.

```java
abstract class Vehicle {
    abstract void start();  // How it starts? Don't care, just know it starts
}

class Car extends Vehicle {
    void start() {
        // Complex engine starting logic hidden here
        System.out.println("Car started");
    }
}
```

**Encapsulation** - Wrapping data and methods together, controlling access

Think of a capsule medicine: Everything is wrapped inside, you can't access individual ingredients.

```java
class BankAccount {
    private double balance;  // Hidden from outside
    
    // Controlled access through methods
    public void deposit(double amount) {
        if(amount > 0) {
            balance += amount;
        }
    }
    
    public double getBalance() {
        return balance;
    }
}
```

**Simple difference:**
- Abstraction = What to show (hiding complexity)
- Encapsulation = How to hide (data protection using private/public)

---

### 4. Difference between abstract class and interface

**Answer:**

```java
// Abstract Class - Can have both complete and incomplete methods
abstract class Animal {
    String name;  // Can have variables
    
    void eat() {  // Can have complete method
        System.out.println("Eating...");
    }
    
    abstract void sound();  // Incomplete method (no body)
}

// Interface - Only incomplete methods (before Java 8)
interface Flyable {
    void fly();  // No body, just declaration
}
```

**Key Differences:**

| Abstract Class | Interface |
|---------------|-----------|
| Can have variables | Cannot have variables (only constants) |
| Can have constructors | No constructors |
| Can have complete methods | Only method signatures (before Java 8) |
| Use `extends` keyword | Use `implements` keyword |
| One class can extend only ONE abstract class | One class can implement MULTIPLE interfaces |

**When to use what:**
- Abstract class = When classes have common behavior (Car, Bike both have engine)
- Interface = When classes need to implement common behavior but are unrelated (Bird flies, Airplane flies - but they're not related)

---

## Keywords and Modifiers

### 1. Final vs Static keyword

**Answer:**

**Final** - Cannot be changed

```java
class Demo {
    final int MAX_SPEED = 100;  // Cannot change value
    
    final void display() {  // Cannot be overridden
        System.out.println("Final method");
    }
}

final class FinalClass {  // Cannot be inherited
    // code
}
```

**Static** - Belongs to class, not object

```java
class Student {
    static String schoolName = "ABC School";  // Shared by all students
    String name;  // Each student has their own name
    
    static void showSchool() {  // Can call without creating object
        System.out.println(schoolName);
    }
}

// Usage
Student.showSchool();  // No object needed
```

**Simple difference:**
- Final = Once set, cannot change
- Static = Shared among all objects, belongs to class

---

### 2. What are volatile variables? When to use them?

**Answer:**

**Volatile** = Always read from main memory, not from CPU cache

Think of it like this: 
- Normal variable = Each person keeps their own photocopy of a document
- Volatile variable = Everyone reads from the same original document

```java
class SharedData {
    volatile boolean flag = false;  // Will be visible to all threads immediately
    
    // Thread 1
    void method1() {
        flag = true;  // Other threads see this change immediately
    }
    
    // Thread 2
    void method2() {
        while(!flag) {  // Will see the change from Thread 1
            // waiting
        }
    }
}
```

**When to use:**
- When multiple threads read/write the same variable
- When you want changes to be visible immediately across all threads
- For flags or status variables in multithreading

**Simple explanation:** Use volatile when multiple threads are playing with the same variable and you want everyone to see the latest value immediately.

---

### 3. Difference between Volatile and synchronized

**Answer:**

**Volatile** - Ensures visibility (everyone sees the latest value)

```java
volatile int counter = 0;
// Only good for reading/writing
// NOT safe for operations like counter++
```

**Synchronized** - Ensures both visibility AND atomicity (only one thread can access at a time)

```java
synchronized void increment() {
    counter++;  // Safe, only one thread can execute this at a time
}
```

**Simple difference:**

| Volatile | Synchronized |
|----------|--------------|
| Just makes sure everyone sees the latest value | Makes sure only one person can use it at a time |
| Fast | Slower (because threads have to wait) |
| Good for simple read/write | Good for complex operations |
| Like a public notice board | Like a single-person toilet |

---

## Exceptions

### 1. Checked exceptions vs Unchecked exceptions

**Answer:**

**Checked Exceptions** - Compiler forces you to handle them (like a strict teacher)

```java
// Compiler will give error if you don't handle
void readFile() throws IOException {  // Must declare
    FileReader file = new FileReader("test.txt");  // Checked exception
}

// OR handle with try-catch
void readFile() {
    try {
        FileReader file = new FileReader("test.txt");
    } catch(IOException e) {
        System.out.println("File not found");
    }
}
```

**Unchecked Exceptions** - Compiler doesn't force you (your choice to handle or not)

```java
// No need to declare or handle, but can crash at runtime
void divide(int a, int b) {
    int result = a / b;  // Can throw ArithmeticException, but compiler doesn't complain
}
```

**Examples:**
- Checked: IOException, SQLException, ClassNotFoundException
- Unchecked: NullPointerException, ArrayIndexOutOfBoundsException, ArithmeticException

**Simple rule:** 
- Checked = Compiler checks = Must handle
- Unchecked = Compiler doesn't check = Your wish

---

### 3. What is the difference between throw and throws?

**Answer:**

**throw** - Actually throwing an exception (doing the action)

```java
void checkAge(int age) {
    if(age < 18) {
        throw new IllegalArgumentException("Too young!");  // Actually throwing
    }
}
```

**throws** - Just declaring that this method might throw an exception (warning label)

```java
void readFile() throws IOException {  // Just declaring, not throwing yet
    // Inside this method, IOException might happen
    FileReader file = new FileReader("test.txt");
}
```

**Real-life example:**
- throw = Actually throwing a ball
- throws = Warning sign "Ball might be thrown from here"

---

### 4. Custom exception - Write implementation

**Answer:**

```java
// Create your own exception class
class AgeNotValidException extends Exception {
    public AgeNotValidException(String message) {
        super(message);  // Pass message to parent Exception class
    }
}

// Using custom exception
class Validator {
    void checkAge(int age) throws AgeNotValidException {
        if(age < 18) {
            throw new AgeNotValidException("Age must be 18 or above!");
        }
        System.out.println("Age is valid");
    }
}

// Main method
public class Main {
    public static void main(String[] args) {
        Validator validator = new Validator();
        try {
            validator.checkAge(15);
        } catch(AgeNotValidException e) {
            System.out.println(e.getMessage());  // Age must be 18 or above!
        }
    }
}
```

---

### 5. Try catch block working

**Answer:**

```java
try {
    // Risky code that might fail
    int result = 10 / 0;  // This will cause error
    System.out.println("This line won't execute");
}
catch(ArithmeticException e) {
    // Handle the error
    System.out.println("Cannot divide by zero!");
}
finally {
    // This ALWAYS runs, error or no error
    System.out.println("Cleanup work done");
}
```

**How it works:**
1. Try block runs first
2. If error happens, jump to catch block
3. Finally block always runs (even if error occurs)

**Multiple catch blocks:**

```java
try {
    String str = null;
    System.out.println(str.length());  // Will throw NullPointerException
}
catch(NullPointerException e) {
    System.out.println("String is null");
}
catch(Exception e) {  // Generic catch for any other exception
    System.out.println("Some other error");
}
finally {
    System.out.println("Done");
}
```

---

### 6. Finally block use

**Answer:**

**Finally** = This code will run NO MATTER WHAT (error or no error, return or no return)

**Use cases:**
- Closing database connections
- Closing files
- Releasing resources
- Cleanup operations

```java
Connection conn = null;
try {
    conn = getConnection();  // Open database
    // Do some work
    return "Success";  // Even with return, finally will execute
}
catch(Exception e) {
    System.out.println("Error occurred");
    return "Failure";  // Even with return, finally will execute
}
finally {
    if(conn != null) {
        conn.close();  // This WILL run, guaranteed!
    }
}
```

**Important:** Finally runs even if:
- No error occurs
- Error occurs
- Return statement is there
- Continue/break statement is there

Only case finally doesn't run: System.exit() or JVM crash

---

### 7. What is Finally and Finalize

**Answer:**

**Finally** - Block that always executes after try-catch

```java
try {
    // some code
} finally {
    System.out.println("I will always run!");
}
```

**Finalize** - Method called by Garbage Collector before destroying object

```java
class Demo {
    @Override
    protected void finalize() {
        System.out.println("Object is being destroyed");
    }
}
```

**Simple difference:**
- Finally = Block, used with try-catch, runs immediately
- Finalize = Method, called by Garbage Collector, runs when object is destroyed (not reliable, don't use it)

**Note:** finalize() is deprecated in Java 9+ because it's unreliable. Use try-with-resources instead.

---

## String

### 1. String immutable with example

**Answer:**

**Immutable** = Once created, cannot be changed

```java
String str = "Hello";
str.concat(" World");  // Creates NEW string, doesn't change original
System.out.println(str);  // Still prints "Hello"

// To change, you must reassign
str = str.concat(" World");
System.out.println(str);  // Now prints "Hello World"
```

**Why String is immutable:**

```java
String s1 = "Java";
String s2 = "Java";  // Points to same object in memory (String Pool)

// If String was mutable and we change s1
s1.change("Python");  // This doesn't exist, just example
// Then s2 would also become "Python" - This is dangerous!
```

**Benefits:**
- Thread-safe (multiple threads can use same string safely)
- String Pool saves memory
- Secure (can't be modified after creation)
- Hashcode can be cached

---

### 2. StringBuffer and StringBuilder diff

**Answer:**

Both are mutable (can be changed), but:

**StringBuffer** - Thread-safe but slower

```java
StringBuffer sb = new StringBuffer("Hello");
sb.append(" World");  // Changes the original
System.out.println(sb);  // Hello World

// Multiple threads can use it safely (synchronized)
```

**StringBuilder** - NOT thread-safe but faster

```java
StringBuilder sb = new StringBuilder("Hello");
sb.append(" World");  // Changes the original
System.out.println(sb);  // Hello World

// Faster because no synchronization overhead
```

**When to use:**
- String = When not changing value (immutable)
- StringBuilder = Single thread, need to modify (fast)
- StringBuffer = Multiple threads, need to modify (safe but slow)

```java
// Example: Why StringBuilder is better for concatenation
// Bad (creates new String object each time)
String result = "";
for(int i = 0; i < 1000; i++) {
    result += i;  // Creates 1000 new String objects!
}

// Good (modifies same object)
StringBuilder result = new StringBuilder();
for(int i = 0; i < 1000; i++) {
    result.append(i);  // Fast!
}
```

---

### 3. String replace - Write code

**Answer:**

```java
public class StringReplace {
    public static void main(String[] args) {
        String str = "Hello World";
        
        // Replace single character
        String result1 = str.replace('o', 'x');
        System.out.println(result1);  // Hellx Wxrld
        
        // Replace substring
        String result2 = str.replace("World", "Java");
        System.out.println(result2);  // Hello Java
        
        // Replace all occurrences using regex
        String str2 = "Java123Java456Java";
        String result3 = str2.replaceAll("\\d+", "");  // Remove all digits
        System.out.println(result3);  // JavaJavaJava
        
        // Replace first occurrence only
        String result4 = str2.replaceFirst("Java", "Python");
        System.out.println(result4);  // Python123Java456Java
    }
}
```

---

### 4. Find the duplicates from the string

**Answer:**

```java
public class FindDuplicates {
    public static void main(String[] args) {
        String str = "programming";
        
        // Method 1: Using HashMap
        Map<Character, Integer> map = new HashMap<>();
        for(char c : str.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        
        System.out.println("Duplicate characters:");
        for(Map.Entry<Character, Integer> entry : map.entrySet()) {
            if(entry.getValue() > 1) {
                System.out.println(entry.getKey() + " appears " + entry.getValue() + " times");
            }
        }
        
        // Method 2: Simple way using nested loops
        System.out.println("\nMethod 2:");
        for(int i = 0; i < str.length(); i++) {
            int count = 1;
            if(str.charAt(i) != ' ') {  // Skip if already counted
                for(int j = i + 1; j < str.length(); j++) {
                    if(str.charAt(i) == str.charAt(j)) {
                        count++;
                        str = str.substring(0, j) + " " + str.substring(j + 1);  // Mark as counted
                    }
                }
                if(count > 1) {
                    System.out.println(str.charAt(i) + " - " + count + " times");
                }
            }
        }
    }
}

// Output:
// g appears 2 times
// r appears 2 times
// m appears 2 times
```

---

### 6. Check if the string is palindrome or not

**Answer:**

**Palindrome** = Same when read forward or backward (like "madam", "racecar")

```java
public class PalindromeCheck {
    
    // Method 1: Using two pointers (most efficient)
    public static boolean isPalindrome1(String str) {
        str = str.toLowerCase();  // Convert to lowercase
        int left = 0;
        int right = str.length() - 1;
        
        while(left < right) {
            if(str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    // Method 2: Using reverse string
    public static boolean isPalindrome2(String str) {
        str = str.toLowerCase();
        String reversed = new StringBuilder(str).reverse().toString();
        return str.equals(reversed);
    }
    
    public static void main(String[] args) {
        String str1 = "madam";
        String str2 = "hello";
        
        System.out.println(str1 + " is palindrome: " + isPalindrome1(str1));  // true
        System.out.println(str2 + " is palindrome: " + isPalindrome1(str2));  // false
    }
}
```

---

## Data Types and Arrays

### 1. Array vs List

**Answer:**

**Array** - Fixed size, can store primitives

```java
int[] arr = new int[5];  // Size fixed to 5
arr[0] = 10;
arr[1] = 20;
// arr[5] = 30;  // Error! Cannot add more

// Cannot change size once created
```

**List** - Dynamic size, stores objects only

```java
List<Integer> list = new ArrayList<>();  // Size is flexible
list.add(10);
list.add(20);
list.add(30);  // Can keep adding
list.remove(1);  // Can remove

// Size changes automatically
```

**Key Differences:**

| Array | List |
|-------|------|
| Fixed size | Dynamic size |
| Can store primitives (int, char) | Only stores objects (Integer, String) |
| Fast access | Slightly slower |
| arr[0] syntax | list.get(0) syntax |
| No built-in methods | Many useful methods (add, remove, contains) |

---

## Immutability

### 1. What is immutable class

**Answer:**

**Immutable** = Once object is created, its values cannot be changed

**How to create immutable class:**

```java
// 1. Make class final (cannot be inherited)
// 2. Make all fields private and final
// 3. No setter methods
// 4. Initialize all fields in constructor

public final class Employee {
    private final String name;
    private final int age;
    
    // Constructor
    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Only getters, no setters
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
}

// Usage
Employee emp = new Employee("John", 25);
System.out.println(emp.getName());  // John
// emp.setName("Mike");  // This method doesn't exist!
// Cannot change name after creation
```

**Real examples of immutable classes:**
- String
- Integer, Long, Double (wrapper classes)
- LocalDate, LocalTime

**Benefits:**
- Thread-safe
- Can be cached and reused
- Safe to use as HashMap keys
