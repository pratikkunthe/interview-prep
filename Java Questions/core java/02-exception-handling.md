# Java Exception Handling

## 52. What is an exception in Java?

An **exception** is an unexpected event that occurs during program execution and disrupts normal flow.

```java
public class Test {
    public static void main(String[] args) {
        int a = 10;
        int b = 0;
        int result = a / b;  // Exception! Cannot divide by zero
        System.out.println(result);
    }
}
```

**Output:** `ArithmeticException: / by zero`

**Think of it as:** An error that happens while the program is running.

---

## 53. State some situations where exceptions may arise in Java

**Common scenarios:**

1. **ArithmeticException** - Division by zero
```java
int result = 10 / 0;
```

2. **NullPointerException** - Using null reference
```java
String str = null;
System.out.println(str.length());
```

3. **ArrayIndexOutOfBoundsException** - Invalid array index
```java
int[] arr = {1, 2, 3};
System.out.println(arr[5]);
```

4. **FileNotFoundException** - File doesn't exist
```java
FileReader file = new FileReader("missing.txt");
```

5. **NumberFormatException** - Invalid string to number conversion
```java
int num = Integer.parseInt("abc");
```

---

## 54. What is Exception handling in Java?

**Exception handling** is the process of handling runtime errors to maintain normal program flow.

```java
public class Test {
    public static void main(String[] args) {
        try {
            int result = 10 / 0;  // Exception occurs here
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero");
        }
        
        System.out.println("Program continues...");
    }
}
```

**Output:**
```
Cannot divide by zero
Program continues...
```

**Without exception handling:** Program would crash
**With exception handling:** Program handles error and continues

---

## 55. What is an error in Java?

**Error** is a serious problem that cannot be handled by the program. Errors are caused by the environment.

**Examples:**
- **OutOfMemoryError** - JVM runs out of memory
- **StackOverflowError** - Too many method calls (infinite recursion)
- **VirtualMachineError** - JVM crashes

```java
public class Test {
    public static void main(String[] args) {
        main(args);  // Infinite recursion → StackOverflowError
    }
}
```

| Exception | Error |
|-----------|-------|
| Can be handled | Cannot be handled |
| Recoverable | Unrecoverable |
| Caused by program | Caused by environment |
| Example: NullPointerException | Example: OutOfMemoryError |

---

## 56. What are advantages of Exception handling in Java?

**1. Program doesn't crash**
```java
try {
    int result = 10 / 0;
} catch (Exception e) {
    System.out.println("Error occurred");
}
System.out.println("Program continues");  // Executes
```

**2. Separates error-handling code from normal code**
```java
// Clear and readable
try {
    // Normal code
} catch (Exception e) {
    // Error handling code
}
```

**3. Meaningful error messages**
```java
catch (ArithmeticException e) {
    System.out.println("Please enter non-zero denominator");
}
```

**4. Different errors handled differently**
```java
try {
    // code
} catch (ArithmeticException e) {
    // Handle math errors
} catch (NullPointerException e) {
    // Handle null errors
}
```

---

## 57. In how many ways we can do exception handling in Java?

**Two ways:**

**1. Using try-catch block**
```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Error: " + e.getMessage());
}
```

**2. Using throws keyword**
```java
public void divide() throws ArithmeticException {
    int result = 10 / 0;
}
```

**Note:** try-catch handles the exception, throws passes it to the caller.

---

## 58. List out five keywords related to Exception handling

**1. try** - Block where exception might occur
**2. catch** - Block to handle the exception
**3. finally** - Block that always executes
**4. throw** - Manually throw an exception
**5. throws** - Declare exceptions that method might throw

```java
public void readFile() throws IOException {  // throws
    try {                                      // try
        FileReader file = new FileReader("test.txt");
        if (file == null) {
            throw new IOException("File is null");  // throw
        }
    } catch (IOException e) {                // catch
        System.out.println("Error: " + e.getMessage());
    } finally {                              // finally
        System.out.println("Cleanup done");
    }
}
```

---

## 59. Explain try and catch keywords in Java

**try block** - Contains code that might throw an exception
**catch block** - Handles the exception if it occurs

```java
try {
    // Code that might throw exception
    int result = 10 / 0;
    System.out.println(result);
} catch (ArithmeticException e) {
    // Handle exception
    System.out.println("Cannot divide by zero");
}
```

**Flow:**
```
try block
    ↓
Exception occurs?
    ↓
   Yes → catch block → Rest of program
    ↓
   No → Skip catch block → Rest of program
```

**Example:**
```java
try {
    int[] arr = {1, 2, 3};
    System.out.println(arr[5]);  // Exception!
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Invalid index");
}
```

---

## 60. Can we have try block without catch block?

**Yes**, but must have **finally** block.

```java
// Valid - try with finally
try {
    int result = 10 / 2;
    System.out.println(result);
} finally {
    System.out.println("Cleanup code");
}

// Invalid - try alone
try {
    int result = 10 / 2;
}  // Error! Need catch or finally

// Valid - try with catch
try {
    int result = 10 / 2;
} catch (Exception e) {
    e.printStackTrace();
}
```

**Rule:** try must be followed by either catch or finally (or both)

---

## 61. Can we have multiple catch block for a try block?

**Yes**, we can have multiple catch blocks to handle different exceptions.

```java
try {
    int[] arr = {1, 2, 3};
    System.out.println(arr[5]);  // ArrayIndexOutOfBoundsException
    int result = 10 / 0;         // ArithmeticException
    String str = null;
    str.length();                // NullPointerException
    
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Invalid array index");
    
} catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero");
    
} catch (NullPointerException e) {
    System.out.println("Null value found");
    
} catch (Exception e) {
    System.out.println("Some other error");
}
```

**Important:** Order matters - specific exceptions first, general exceptions last

```java
// Wrong order
catch (Exception e) { }              // Catches all
catch (ArithmeticException e) { }    // Never reached - Error!

// Correct order
catch (ArithmeticException e) { }    // Specific
catch (Exception e) { }              // General
```

---

## 62. Explain importance of finally block in Java

**finally block** always executes, whether exception occurs or not. Used for cleanup code.

```java
try {
    int result = 10 / 0;
    System.out.println(result);
} catch (ArithmeticException e) {
    System.out.println("Exception caught");
} finally {
    System.out.println("Finally block always runs");
}
```

**Output:**
```
Exception caught
Finally block always runs
```

**Use cases:**
- Closing files
- Releasing database connections
- Closing network connections

```java
FileReader file = null;
try {
    file = new FileReader("test.txt");
    // Read file
} catch (IOException e) {
    System.out.println("File error");
} finally {
    if (file != null) {
        file.close();  // Always close file
    }
}
```

---

## 63. Can we have any code between try and catch blocks?

**No**, you cannot have any code between try and catch blocks.

```java
// Invalid
try {
    int result = 10 / 0;
}
System.out.println("Hello");  // Error! Cannot have code here
catch (ArithmeticException e) {
    System.out.println("Error");
}

// Valid
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Error");
}
System.out.println("Hello");  // OK - after catch block
```

---

## 64. Can we have any code between try and finally blocks?

**No** (if no catch block), **Yes** (if catch block exists)

```java
// Invalid - No code between try and finally (without catch)
try {
    int result = 10 / 0;
}
System.out.println("Hello");  // Error!
finally {
    System.out.println("Finally");
}

// Valid - Code between try and finally (with catch)
try {
    int result = 10 / 0;
} catch (Exception e) {
    System.out.println("Error");
}
System.out.println("Hello");  // OK - between catch and finally
finally {
    System.out.println("Finally");
}
```

---

## 65. Can we catch more than one exception in single catch block?

**Yes** (Java 7+), using **multi-catch** feature.

```java
// Before Java 7 - Multiple catch blocks
try {
    // code
} catch (IOException e) {
    System.out.println("IO Error");
} catch (SQLException e) {
    System.out.println("DB Error");
}

// Java 7+ - Single catch for multiple exceptions
try {
    // code
} catch (IOException | SQLException e) {
    System.out.println("IO or DB Error");
}
```

**Example:**
```java
try {
    int[] arr = {1, 2, 3};
    System.out.println(arr[5]);
    int result = 10 / 0;
} catch (ArrayIndexOutOfBoundsException | ArithmeticException e) {
    System.out.println("Array or Math error occurred");
}
```

**Note:** Use `|` (pipe) symbol to separate exception types

---

## 66. What are checked Exceptions?

**Checked exceptions** are checked at **compile-time**. The compiler forces you to handle them.

```java
// This will give compile error if not handled
public void readFile() {
    FileReader file = new FileReader("test.txt");  // Error! Must handle
}

// Solution 1: Use try-catch
public void readFile() {
    try {
        FileReader file = new FileReader("test.txt");
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}

// Solution 2: Use throws
public void readFile() throws FileNotFoundException {
    FileReader file = new FileReader("test.txt");
}
```

**Common checked exceptions:**
- IOException
- FileNotFoundException
- SQLException
- ClassNotFoundException

---

## 67. What are unchecked exceptions in Java?

**Unchecked exceptions** are **not checked** at compile-time. They occur at runtime.

```java
// No compile error - but runtime exception
public void divide() {
    int result = 10 / 0;  // ArithmeticException at runtime
}

public void test() {
    String str = null;
    str.length();  // NullPointerException at runtime
}
```

**Common unchecked exceptions:**
- ArithmeticException
- NullPointerException
- ArrayIndexOutOfBoundsException
- NumberFormatException
- IllegalArgumentException

**Hierarchy:**
```
RuntimeException (unchecked)
    ↓
ArithmeticException
NullPointerException
ArrayIndexOutOfBoundsException
```

---

## 68. Explain differences between checked and Unchecked exceptions in Java

| Checked Exception | Unchecked Exception |
|-------------------|---------------------|
| Checked at compile-time | Checked at runtime |
| Must handle (try-catch or throws) | Optional to handle |
| Extends Exception | Extends RuntimeException |
| Example: IOException | Example: NullPointerException |
| Compiler forces handling | Compiler doesn't check |

```java
// Checked - Must handle
public void method1() throws IOException {
    throw new IOException();  // Must declare throws
}

// Unchecked - Optional to handle
public void method2() {
    throw new ArithmeticException();  // No need to declare
}
```

**Diagram:**
```
Throwable
    ↓
    ├── Exception (Checked)
    │       ├── IOException
    │       └── SQLException
    │
    └── RuntimeException (Unchecked)
            ├── NullPointerException
            └── ArithmeticException
```

---

## 69. What is default Exception handling in Java?

**Default exception handling** is done by JVM when we don't handle the exception.

**Steps JVM follows:**
1. Creates exception object
2. Checks if exception is handled
3. If not handled, passes to default exception handler
4. Default handler prints exception info and terminates program

```java
public class Test {
    public static void main(String[] args) {
        int result = 10 / 0;  // No try-catch
        System.out.println("After exception");  // Never executes
    }
}
```

**Output:**
```
Exception in thread "main" java.lang.ArithmeticException: / by zero
    at Test.main(Test.java:3)
```

**Flow:**
```
Exception occurs
    ↓
Handled in method? → No
    ↓
Handled in caller? → No
    ↓
JVM default handler
    ↓
Print exception & Terminate program
```

---

## 70. Explain throw keyword in Java

**throw** is used to explicitly throw an exception.

```java
public class Test {
    static void checkAge(int age) {
        if (age < 18) {
            throw new ArithmeticException("Age must be 18+");
        } else {
            System.out.println("Valid age");
        }
    }
    
    public static void main(String[] args) {
        checkAge(15);  // Throws exception
    }
}
```

**Output:** `ArithmeticException: Age must be 18+`

**Custom exception:**
```java
throw new Exception("Custom error message");
throw new NullPointerException("Object is null");
throw new IllegalArgumentException("Invalid input");
```

**Syntax:** `throw new ExceptionType("message");`

---

## 71. Can we write any code after throw statement?

**No**, code after throw is unreachable and will give compile error.

```java
public void test() {
    throw new Exception("Error");
    System.out.println("Hello");  // Error! Unreachable code
}

// Correct
public void test() {
    throw new Exception("Error");
    // No code after throw
}

// Exception: Code after throw in if block is OK
public void test(int age) {
    if (age < 18) {
        throw new Exception("Too young");
    }
    System.out.println("Valid age");  // OK - reachable if age >= 18
}
```

---

## 72. Explain importance of throws keyword in Java

**throws** is used in method signature to declare that method might throw exceptions.

```java
// Method declares it might throw IOException
public void readFile() throws IOException {
    FileReader file = new FileReader("test.txt");
    // No try-catch needed here
}

// Caller must handle it
public void caller() {
    try {
        readFile();
    } catch (IOException e) {
        System.out.println("File error");
    }
}
```

**Multiple exceptions:**
```java
public void method() throws IOException, SQLException {
    // code that might throw IOException or SQLException
}
```

**Key Points:**
- Used for checked exceptions
- Passes responsibility to caller
- Multiple exceptions separated by comma

---

## 73. Explain the importance of finally over return statement

**finally block executes even if there's a return statement** in try or catch.

```java
public class Test {
    static int method() {
        try {
            return 10;  // Returns 10
        } finally {
            System.out.println("Finally executes");
        }
    }
    
    public static void main(String[] args) {
        int result = method();
        System.out.println("Result: " + result);
    }
}
```

**Output:**
```
Finally executes
Result: 10
```

**Finally can even override return value:**
```java
static int method() {
    try {
        return 10;
    } finally {
        return 20;  // This value is returned
    }
}
// Result: 20 (finally's return overrides try's return)
```

---

## 74. Explain a situation where finally block will not be executed

**Only 2 situations:**

**1. System.exit()**
```java
try {
    System.out.println("Try block");
    System.exit(0);  // Terminates JVM
} finally {
    System.out.println("Finally");  // Never executes
}
```

**2. Fatal JVM crash or power failure**
```java
try {
    System.out.println("Try block");
    // Sudden system crash
} finally {
    System.out.println("Finally");  // Won't execute if JVM crashes
}
```

**In all other cases, finally always executes!**

---

## 75. Can we use catch statement for checked exceptions?

**Yes**, catch can handle both checked and unchecked exceptions.

```java
// Checked exception
try {
    FileReader file = new FileReader("test.txt");  // IOException
} catch (IOException e) {  // Catching checked exception
    System.out.println("File not found");
}

// Unchecked exception
try {
    int result = 10 / 0;  // ArithmeticException
} catch (ArithmeticException e) {  // Catching unchecked exception
    System.out.println("Cannot divide by zero");
}

// Both
try {
    FileReader file = new FileReader("test.txt");
    int result = 10 / 0;
} catch (IOException e) {
    System.out.println("IO Error");
} catch (ArithmeticException e) {
    System.out.println("Math Error");
}
```

---

## 76. What are user defined exceptions?

**User defined exceptions** are custom exceptions created by extending Exception class.

```java
// Custom exception
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

// Using custom exception
class Voting {
    static void checkAge(int age) throws InvalidAgeException {
        if (age < 18) {
            throw new InvalidAgeException("Age must be 18 or above");
        } else {
            System.out.println("Valid age for voting");
        }
    }
    
    public static void main(String[] args) {
        try {
            checkAge(15);
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
        }
    }
}
```

**Output:** `Age must be 18 or above`

**Why create custom exceptions?**
- More meaningful error messages
- Better organization
- Specific to your application

---

## 77. Can we rethrow the same exception from catch handler?

**Yes**, we can rethrow the exception using `throw`.

```java
public class Test {
    static void method1() throws Exception {
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("Exception in method1");
            throw e;  // Rethrowing same exception
        }
    }
    
    static void method2() {
        try {
            method1();
        } catch (Exception e) {
            System.out.println("Exception in method2");
        }
    }
    
    public static void main(String[] args) {
        method2();
    }
}
```

**Output:**
```
Exception in method1
Exception in method2
```

**Use case:** Log the exception and pass it to caller

---

## 78. Can we have nested try statements in Java?

**Yes**, we can have try-catch blocks inside another try-catch block.

```java
public class Test {
    public static void main(String[] args) {
        try {
            System.out.println("Outer try");
            
            try {
                System.out.println("Inner try");
                int result = 10 / 0;  // Exception in inner try
            } catch (ArithmeticException e) {
                System.out.println("Inner catch");
            }
            
            String str = null;
            str.length();  // Exception in outer try
            
        } catch (NullPointerException e) {
            System.out.println("Outer catch");
        }
    }
}
```

**Output:**
```
Outer try
Inner try
Inner catch
Outer catch
```

---

## 79. Explain the importance of Throwable class and its methods

**Throwable** is the parent class of all exceptions and errors.

**Hierarchy:**
```
Throwable
    ↓
    ├── Exception
    │       ├── IOException
    │       └── RuntimeException
    │
    └── Error
            ├── OutOfMemoryError
            └── StackOverflowError
```

**Important methods:**

```java
try {
    int result = 10 / 0;
} catch (Exception e) {
    // 1. getMessage() - Returns error message
    System.out.println(e.getMessage());  // / by zero
    
    // 2. toString() - Returns exception class + message
    System.out.println(e.toString());  // java.lang.ArithmeticException: / by zero
    
    // 3. printStackTrace() - Prints stack trace
    e.printStackTrace();
    
    // 4. getCause() - Returns cause of exception
    Throwable cause = e.getCause();
}
```

---

## 80. Explain when ClassNotFoundException will be raised

**ClassNotFoundException** occurs when JVM tries to load a class at runtime but cannot find it.

```java
try {
    // Trying to load a class that doesn't exist
    Class.forName("com.example.NonExistentClass");
} catch (ClassNotFoundException e) {
    System.out.println("Class not found: " + e.getMessage());
}
```

**Common scenarios:**
1. Loading JDBC driver
```java
Class.forName("com.mysql.jdbc.Driver");  // If MySQL driver not in classpath
```

2. Dynamic class loading
```java
Class.forName("MyClass");  // If MyClass.class not found
```

3. Reflection
```java
Class<?> clazz = Class.forName("java.util.InvalidClass");
```

**It's a checked exception** - must handle with try-catch or throws

---

## 81. Explain when NoClassDefFoundError will be raised

**NoClassDefFoundError** occurs when a class was present during compile time but not available at runtime.

```java
// ClassA.java
public class ClassA {
    public void display() {
        System.out.println("Hello");
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        ClassA obj = new ClassA();  // NoClassDefFoundError if ClassA.class deleted
        obj.display();
    }
}
```

**Difference:**

| ClassNotFoundException | NoClassDefFoundError |
|----------------------|----------------------|
| Exception (checked) | Error (unchecked) |
| Class never existed | Class existed but now missing |
| During explicit loading | During implicit loading |
| Example: Class.forName() | Example: new ClassName() |
| Can be caught and handled | Serious error, shouldn't catch |

