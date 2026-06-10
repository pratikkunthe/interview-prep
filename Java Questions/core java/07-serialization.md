# Java Serialization

## 232. What is serialization in Java?

**Serialization** is the process of converting an object into a byte stream to save it or send it over a network.

```java
// Object → Byte Stream (Serialization)
// Byte Stream → Object (Deserialization)
```

**Example:**
```java
import java.io.*;

class Student implements Serializable {
    String name;
    int age;
    
    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

public class Test {
    public static void main(String[] args) throws Exception {
        Student student = new Student("John", 20);
        
        // Serialization - Object to File
        FileOutputStream fileOut = new FileOutputStream("student.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(student);
        out.close();
        System.out.println("Serialized");
        
        // Deserialization - File to Object
        FileInputStream fileIn = new FileInputStream("student.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Student s = (Student) in.readObject();
        in.close();
        System.out.println("Name: " + s.name + ", Age: " + s.age);
    }
}
```

**Diagram:**
```
Object (Memory)
      ↓ Serialization
Byte Stream
      ↓ Save to file/Send over network
Storage/Network
      ↓ Read from file/Receive
Byte Stream
      ↓ Deserialization
Object (Memory)
```

---

## 233. What is the main purpose of serialization in Java?

**Main purposes:**

**1. Save object state to file**
```java
// Save game progress
GameState state = new GameState();
// Serialize to file
```

**2. Send objects over network**
```java
// Send user object to remote server
User user = new User();
// Serialize and send
```

**3. Deep cloning objects**
```java
// Create exact copy of object
MyObject original = new MyObject();
// Serialize and deserialize to clone
```

**4. Caching**
```java
// Cache objects in Redis
Object obj = new Object();
// Serialize and store in cache
```

**Real-world examples:**
- Saving user session
- Remote Method Invocation (RMI)
- Storing objects in database
- Hibernate persistence

---

## 234. What are alternatives to Java serialization?

**Alternatives:**

**1. JSON (Jackson, Gson)**
```java
// Using Gson
Gson gson = new Gson();
String json = gson.toJson(student);  // Object to JSON
Student s = gson.fromJson(json, Student.class);  // JSON to Object
```

**2. XML**
```java
// Using JAXB
JAXBContext context = JAXBContext.newInstance(Student.class);
Marshaller marshaller = context.createMarshaller();
marshaller.marshal(student, new File("student.xml"));
```

**3. Protocol Buffers (Google)**
```protobuf
message Student {
  string name = 1;
  int32 age = 2;
}
```

**4. Apache Avro**
```java
// Binary serialization format
```

**Why use alternatives?**
- JSON/XML are human-readable
- Cross-language support
- Smaller size (Protocol Buffers)
- Faster (Protocol Buffers, Avro)

---

## 235. Explain about Serializable interface in Java

**Serializable** is a marker interface (no methods) that tells JVM the class can be serialized.

```java
import java.io.Serializable;

class Student implements Serializable {
    String name;
    int age;
}
```

**Key points:**
- Marker interface (no methods to implement)
- Mandatory for serialization
- If not implemented, throws `NotSerializableException`

**Example error without Serializable:**
```java
class Student {  // Not implementing Serializable
    String name;
}

// Try to serialize
ObjectOutputStream out = new ObjectOutputStream(fileOut);
out.writeObject(new Student());  // NotSerializableException!
```

---

## 236. How to make object serializable in Java?

**Steps:**

**1. Implement Serializable interface**
```java
import java.io.Serializable;

class Employee implements Serializable {
    String name;
    int id;
    double salary;
}
```

**2. That's it!** No methods to implement.

**Complete example:**
```java
import java.io.*;

class Employee implements Serializable {
    private static final long serialVersionUID = 1L;  // Recommended
    
    String name;
    int id;
    transient String password;  // Won't be serialized
    
    Employee(String name, int id, String password) {
        this.name = name;
        this.id = id;
        this.password = password;
    }
}

public class Test {
    public static void main(String[] args) throws Exception {
        // Serialize
        Employee emp = new Employee("John", 101, "secret123");
        ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream("employee.ser")
        );
        out.writeObject(emp);
        out.close();
        
        // Deserialize
        ObjectInputStream in = new ObjectInputStream(
            new FileInputStream("employee.ser")
        );
        Employee e = (Employee) in.readObject();
        in.close();
        
        System.out.println("Name: " + e.name);
        System.out.println("ID: " + e.id);
        System.out.println("Password: " + e.password);  // null
    }
}
```

---

## 237. What is serial version UID and its importance?

**serialVersionUID** is a unique identifier for each Serializable class.

```java
class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    String name;
    int age;
}
```

**Why important?**

**Version control:**
- Ensures compatibility between serialized and deserialized versions
- If class changes, serialVersionUID helps identify version mismatch

**Example:**
```java
// Version 1 - serialVersionUID = 1L
class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    int age;
}
// Serialize student object

// Version 2 - Add new field but keep same serialVersionUID
class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    int age;
    String address;  // New field added
}
// Can still deserialize old objects (backward compatible)
```

**Without serialVersionUID:**
```java
class Student implements Serializable {
    // No serialVersionUID
}
// If you modify the class and try to deserialize old objects
// → InvalidClassException (version mismatch)
```

---

## 238. What happens if we don't define serial version UID?

**If not defined:**
- JVM automatically generates one based on class details
- Any change to class generates different UID
- Old serialized objects can't be deserialized

```java
// Version 1
class Student implements Serializable {
    String name;
}
// JVM generates serialVersionUID = 12345 (example)
// Serialize student object

// Version 2 - Add a method
class Student implements Serializable {
    String name;
    void display() { }  // Just added a method
}
// JVM generates serialVersionUID = 67890 (different!)
// Try to deserialize old object → InvalidClassException!
```

**Solution:** Always explicitly define serialVersionUID
```java
private static final long serialVersionUID = 1L;
```

---

## 239. Can we serialize static variables in Java?

**No**, static variables are NOT serialized because they belong to the class, not the object.

```java
class Student implements Serializable {
    String name;           // Instance variable - Serialized
    static String school;  // Static variable - NOT serialized
    
    Student(String name) {
        this.name = name;
    }
}

public class Test {
    public static void main(String[] args) throws Exception {
        Student.school = "ABC School";
        Student student = new Student("John");
        
        // Serialize
        ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream("student.ser")
        );
        out.writeObject(student);
        out.close();
        
        // Change static variable
        Student.school = "XYZ School";
        
        // Deserialize
        ObjectInputStream in = new ObjectInputStream(
            new FileInputStream("student.ser")
        );
        Student s = (Student) in.readObject();
        in.close();
        
        System.out.println("Name: " + s.name);      // John
        System.out.println("School: " + Student.school);  // XYZ School (NOT ABC School)
    }
}
```

**Why?** Static variables are shared by class, not tied to any specific object.

---

## 240. When we serialize an object, does serialization save its references too?

**Yes**, serialization automatically handles object references (object graph).

```java
class Address implements Serializable {
    String city;
    
    Address(String city) {
        this.city = city;
    }
}

class Student implements Serializable {
    String name;
    Address address;  // Reference to another object
    
    Student(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}

public class Test {
    public static void main(String[] args) throws Exception {
        Address address = new Address("New York");
        Student student = new Student("John", address);
        
        // Serialize student
        ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream("student.ser")
        );
        out.writeObject(student);  // Automatically serializes address too!
        out.close();
        
        // Deserialize student
        ObjectInputStream in = new ObjectInputStream(
            new FileInputStream("student.ser")
        );
        Student s = (Student) in.readObject();
        in.close();
        
        System.out.println("Name: " + s.name);           // John
        System.out.println("City: " + s.address.city);   // New York
    }
}
```

**Important:**
- All referenced objects must be Serializable
- If any reference is not Serializable → NotSerializableException

```java
class Address {  // NOT implementing Serializable
    String city;
}

class Student implements Serializable {
    Address address;  // Error! Address must be Serializable too
}
```

---

## 241. If we don't want some fields to be serialized, how to do that?

**Use the `transient` keyword** to exclude fields from serialization.

```java
class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    String username;
    transient String password;  // Won't be serialized
    int age;
}

public class Test {
    public static void main(String[] args) throws Exception {
        User user = new User();
        user.username = "john";
        user.password = "secret123";
        user.age = 25;
        
        // Serialize
        ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream("user.ser")
        );
        out.writeObject(user);
        out.close();
        
        // Deserialize
        ObjectInputStream in = new ObjectInputStream(
            new FileInputStream("user.ser")
        );
        User u = (User) in.readObject();
        in.close();
        
        System.out.println("Username: " + u.username);  // john
        System.out.println("Password: " + u.password);  // null (not serialized)
        System.out.println("Age: " + u.age);            // 25
    }
}
```

**Use cases for transient:**
- Sensitive data (passwords, credit card numbers)
- Derived/calculated values
- Temporary cache data
- File handles, database connections

**Example:**
```java
class BankAccount implements Serializable {
    String accountNumber;
    transient String pin;           // Sensitive
    double balance;
    transient double interestRate;  // Can be recalculated
}
```

---

## Summary

### Key Concepts Covered:

**1. Java Fundamentals**
- Static blocks, constructors, method overloading/overriding
- Access modifiers, keywords (this, super, final)
- Classes, objects, methods, encapsulation

**2. Exception Handling**
- try-catch-finally blocks
- throw vs throws
- Checked vs unchecked exceptions
- Custom exceptions

**3. Multithreading**
- Thread creation (Thread class vs Runnable)
- Thread lifecycle and synchronization
- wait(), notify(), sleep(), join()
- Thread priorities and daemon threads

**4. Nested Classes**
- Static nested classes
- Inner classes (member, local, anonymous)
- When to use each type

**5. OOP Concepts**
- Inheritance (IS-A relationship)
- Composition (HAS-A relationship)
- Polymorphism
- Abstraction vs Interface

**6. Collections Framework**
- List (ArrayList, LinkedList, Vector)
- Set (HashSet, LinkedHashSet, TreeSet)
- Map (HashMap, TreeMap, LinkedHashMap)
- Iterator vs ListIterator
- Fail-fast vs fail-safe

**7. Serialization**
- Converting objects to byte streams
- Serializable interface
- transient keyword
- serialVersionUID importance

---

## Tips for Learning:

1. **Practice coding examples** - Don't just read, type and run code
2. **Draw diagrams** - Visualize class hierarchies and object relationships
3. **Compare concepts** - Understand differences (ArrayList vs LinkedList)
4. **Real-world examples** - Connect concepts to practical use cases
5. **Build projects** - Apply multiple concepts together

---

## Common Interview Tips:

- **Be concise** - Give short, clear answers then elaborate if asked
- **Use examples** - Code examples make concepts clearer
- **Explain trade-offs** - Show you understand when to use what
- **Mention best practices** - Shows experience and knowledge
- **Ask clarifying questions** - Shows thoughtfulness

Good luck with your interviews! 🎯
