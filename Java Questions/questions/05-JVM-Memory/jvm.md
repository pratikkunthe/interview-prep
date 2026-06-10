# JVM and Memory Management

## JVM Architecture

### 1. Explain the JVM architecture

**Answer:**

**JVM (Java Virtual Machine)** = Runtime environment that executes Java bytecode

Think of JVM like a translator:
- You write Java code (human language)
- Compiler converts to bytecode (intermediate language)
- JVM converts bytecode to machine code (computer language)

**JVM Architecture Components:**

```
┌─────────────────────────────────────┐
│         Class Loader Subsystem      │ ← Loads classes
├─────────────────────────────────────┤
│                                     │
│  ┌──────────────┐  ┌─────────────┐│
│  │ Runtime Data │  │  Execution  ││
│  │    Area      │  │   Engine    ││
│  └──────────────┘  └─────────────┘│
│                                     │
│  ┌───────────────────────────────┐ │
│  │    Memory Areas:              │ │
│  │  • Method Area                │ │
│  │  • Heap                       │ │
│  │  • Stack                      │ │
│  │  • PC Registers               │ │
│  │  • Native Method Stack        │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌──────────────┐  ┌─────────────┐│
│  │   Garbage    │  │   JNI       ││
│  │  Collector   │  │  (Native)  ││
│  └──────────────┘  └─────────────┘│
└─────────────────────────────────────┘
```

**Main Components:**

**1. Class Loader Subsystem**
- Loads `.class` files
- Links classes
- Initializes classes

**2. Runtime Data Area (Memory)**
- **Method Area** - Stores class info, static variables
- **Heap** - Stores objects (shared by all threads)
- **Stack** - Stores method calls, local variables (per thread)
- **PC Registers** - Tracks current instruction (per thread)
- **Native Method Stack** - For native methods (per thread)

**3. Execution Engine**
- Interprets bytecode
- Uses JIT (Just-In-Time) compiler
- Executes native code

**4. Garbage Collector**
- Removes unused objects from heap
- Frees memory automatically

**5. JNI (Java Native Interface)**
- Interface to native libraries

**Simple analogy:**
- **Class Loader** = Librarian (loads books/classes)
- **Runtime Data Area** = Library shelves (stores data)
- **Execution Engine** = Reader (executes instructions)
- **Garbage Collector** = Cleaner (removes unused items)

**How JVM works:**

```java
// 1. You write Java code
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}

// 2. Compiler converts to bytecode (Hello.class)
// 3. Class Loader loads Hello.class
// 4. JVM executes bytecode
// 5. Output: Hello
```

**Key Points:**
- JVM is platform-dependent (different JVM for Windows, Linux, Mac)
- Java is platform-independent (same code runs everywhere)
- JVM provides memory management, garbage collection, security

**Simple explanation:** JVM = Virtual computer that runs Java programs. It manages memory, executes code, and cleans up automatically!

---

### 2. What are the different memory areas in the JVM?

**Answer:**

JVM divides memory into different areas, each with specific purpose:

**Memory Areas:**

```
┌─────────────────────────────────────┐
│      Runtime Data Area              │
├─────────────────────────────────────┤
│                                     │
│  ┌───────────────────────────────┐ │
│  │   Method Area (Shared)        │ │ ← Class info, static vars
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │   Heap (Shared)               │ │ ← Objects (Young + Old)
│  │   ┌─────────┐  ┌───────────┐ │ │
│  │   │  Young  │  │   Old     │ │ │
│  │   │  Gen    │  │   Gen     │ │ │
│  │   └─────────┘  └───────────┘ │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │   Stack (Per Thread)          │ │ ← Method calls, locals
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │   PC Register (Per Thread)    │ │ ← Current instruction
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │   Native Method Stack         │ │ ← Native methods
│  │   (Per Thread)                │ │
│  └───────────────────────────────┘ │
└─────────────────────────────────────┘
```

**1. Method Area (Shared)**
- Stores class metadata (class name, methods, fields)
- Stores static variables
- One per JVM (shared by all threads)

```java
class MyClass {
    static int count = 0;  // Stored in Method Area
    void method() { }      // Method info stored here
}
```

**2. Heap (Shared)**
- Stores all objects
- Divided into:
  - **Young Generation** - New objects
    - Eden Space - New objects created here
    - Survivor Space (S0, S1) - Survived objects
  - **Old Generation** - Long-lived objects

```java
String str = new String("Hello");  // Object stored in Heap
MyClass obj = new MyClass();       // Object stored in Heap
```

**3. Stack (Per Thread)**
- Each thread has its own stack
- Stores method calls (stack frames)
- Stores local variables, parameters

```java
void method1() {
    int x = 10;        // Stored in stack
    method2();         // New frame pushed
}

void method2() {
    int y = 20;        // Stored in stack
}                      // Frame popped when method ends
```

**4. PC Register (Per Thread)**
- Program Counter
- Stores address of current instruction
- One per thread

**5. Native Method Stack (Per Thread)**
- For native methods (C/C++ code)
- Similar to stack but for native code

**Memory Comparison:**

| Area | Shared/Per Thread | Stores | Lifecycle |
|------|-------------------|--------|-----------|
| Method Area | Shared | Class info, static vars | Until class unloaded |
| Heap | Shared | Objects | Until GC collects |
| Stack | Per Thread | Method calls, locals | Until method ends |
| PC Register | Per Thread | Instruction address | Per thread |
| Native Stack | Per Thread | Native method calls | Per thread |

**Simple analogy:**
- **Method Area** = Library catalog (class information)
- **Heap** = Warehouse (all objects stored here)
- **Stack** = Work desk (current work, local items)
- **PC Register** = Bookmark (where you are reading)
- **Native Stack** = Special desk for foreign language books

**Example:**

```java
public class MemoryExample {
    static int count = 0;  // Method Area
    
    public static void main(String[] args) {
        // Stack: main() frame created
        int x = 10;                    // Stack: local variable
        String str = new String("Hi"); // Heap: object created
                                       // Stack: reference stored
        
        method1();                     // Stack: new frame pushed
    }
    
    static void method1() {
        // Stack: method1() frame
        int y = 20;                    // Stack: local variable
        MyClass obj = new MyClass();   // Heap: object created
    }
}
```

**Key Points:**
- Heap = Where objects live (shared)
- Stack = Where method calls happen (per thread)
- Method Area = Where class info lives (shared)
- Each thread has its own stack and PC register

**Simple rule:** Objects go to Heap, method calls go to Stack, class info goes to Method Area!

---

## Garbage Collection

### 1. How does garbage collection work in Java?

**Answer:**

**Garbage Collection (GC)** = Automatic memory management - removes unused objects from heap

Think of it like a cleaning service:
- You create objects (leave things around)
- GC automatically finds and removes unused objects (cleans up)
- You don't need to manually free memory!

**How GC works:**

**Step 1: Mark**
- GC identifies which objects are still in use
- Objects referenced by active variables = "alive"
- Objects not referenced = "garbage" (can be deleted)

**Step 2: Sweep**
- GC removes garbage objects
- Frees memory

**Step 3: Compact (optional)**
- Rearranges remaining objects
- Reduces fragmentation

**Simple example:**

```java
public class GCExample {
    public static void main(String[] args) {
        // Object created in heap
        String str1 = new String("Hello");
        
        // Another object created
        String str2 = new String("World");
        
        // str1 now points to new object
        str1 = new String("New");
        // Old "Hello" object is now garbage (no reference)
        
        // str2 = null;  // Explicitly remove reference
        str2 = null;
        // "World" object is now garbage
        
        // GC will eventually remove "Hello" and "World" objects
    }
}
```

**When GC runs:**
- Automatically (you don't control it directly)
- When heap is getting full
- Can be triggered manually (not recommended): `System.gc()`

**GC Algorithms:**

**1. Mark and Sweep**
- Marks live objects
- Sweeps dead objects
- Can cause fragmentation

**2. Generational GC (Most common)**
- Divides heap into generations:
  - **Young Generation** - New objects (collected frequently)
  - **Old Generation** - Long-lived objects (collected less frequently)

```
Heap:
┌─────────────────────────────────┐
│  Young Generation                │ ← Frequent GC (Minor GC)
│  ┌──────┐  ┌──────┐  ┌──────┐  │
│  │Eden  │  │Surv0 │  │Surv1 │  │
│  └──────┘  └──────┘  └──────┘  │
├─────────────────────────────────┤
│  Old Generation                  │ ← Less frequent GC (Major GC)
│  ┌───────────────────────────┐  │
│  │        Tenured           │  │
│  └───────────────────────────┘  │
└─────────────────────────────────┘
```

**How Generational GC works:**

1. **New objects** created in **Eden Space**
2. When Eden is full → **Minor GC**
   - Live objects moved to **Survivor Space**
   - Dead objects removed
3. Objects surviving multiple Minor GCs → moved to **Old Generation**
4. When Old Generation is full → **Major GC** (Full GC)
   - More expensive, pauses application longer

**GC Types:**

| Type | When | Speed | Impact |
|------|------|-------|--------|
| Minor GC | Young gen full | Fast | Low pause |
| Major GC | Old gen full | Slow | High pause |
| Full GC | Entire heap | Very slow | High pause |

**Example:**

```java
public class GCExample {
    public static void main(String[] args) {
        // Creates objects in Young Generation (Eden)
        for(int i = 0; i < 1000; i++) {
            String str = new String("Object " + i);
            // After loop, these become garbage
        }
        // Minor GC will clean these up
        
        // Long-lived object
        List<String> longList = new ArrayList<>();
        for(int i = 0; i < 10000; i++) {
            longList.add("Item " + i);
        }
        // This might survive to Old Generation
    }
}
```

**What makes object eligible for GC:**

1. **No references** - Object not referenced by any variable
```java
String str = new String("Hello");
str = null;  // Object eligible for GC
```

2. **Isolated references** - Objects referencing each other but no external reference
```java
class Node {
    Node next;
}

Node node1 = new Node();
Node node2 = new Node();
node1.next = node2;
node2.next = node1;
node1 = null;  // Both eligible for GC (circular reference)
node2 = null;
```

**GC Benefits:**
- ✅ No manual memory management
- ✅ Prevents memory leaks (mostly)
- ✅ No dangling pointers
- ✅ Automatic cleanup

**GC Drawbacks:**
- ❌ Unpredictable timing
- ❌ Can cause pauses (stop-the-world)
- ❌ Uses CPU resources

**How to help GC:**

```java
// 1. Set references to null when done
String largeString = new String("...");
// Use it
largeString = null;  // Help GC

// 2. Use try-with-resources for resources
try(FileReader reader = new FileReader("file.txt")) {
    // Auto-closes, helps GC
}

// 3. Avoid creating unnecessary objects
// Bad
String result = "";
for(int i = 0; i < 1000; i++) {
    result += i;  // Creates many String objects!
}

// Good
StringBuilder result = new StringBuilder();
for(int i = 0; i < 1000; i++) {
    result.append(i);  // Reuses same object
}
```

**Key Points:**
- GC runs automatically
- Removes objects with no references
- Generational GC is most common
- Young gen collected frequently, Old gen less frequently
- You can't force GC, but can help it by removing references

**Simple analogy:** GC = Automatic garbage truck that comes periodically to collect trash (unused objects) from your neighborhood (heap). You don't need to call it - it comes automatically!
