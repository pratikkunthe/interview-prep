# Java 8+ Features - Stream API

## Stream Operations

### 1. What are the intermediate and terminal operations in stream API?

**Answer:**

Think of Stream API like a factory assembly line:
- **Intermediate operations** = Steps in the middle (filtering, transforming) - they're lazy and don't execute until needed
- **Terminal operations** = Final step that produces result (collecting, counting) - triggers the actual execution

**Intermediate Operations** (return Stream, lazy):
- `filter()` - Remove unwanted items
- `map()` - Transform each item
- `sorted()` - Sort items
- `distinct()` - Remove duplicates
- `limit()` - Take first N items
- `skip()` - Skip first N items

**Terminal Operations** (return result, triggers execution):
- `collect()` - Collect into List/Set/Map
- `forEach()` - Do something with each item
- `count()` - Count items
- `reduce()` - Combine all items
- `findFirst()` - Get first item
- `anyMatch()` - Check if any matches

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

// Intermediate operations (lazy - nothing happens yet)
Stream<Integer> stream = numbers.stream()
    .filter(n -> n > 2)      // Intermediate
    .map(n -> n * 2)         // Intermediate
    .sorted();               // Intermediate

// Terminal operation (triggers execution)
List<Integer> result = stream.collect(Collectors.toList());  // [6, 8, 10, 12]
```

**Key Point:** Nothing happens until you call a terminal operation! It's like planning a trip vs actually going on the trip.

---

### 2. Use cases of Stream

**Answer:**

Stream API is perfect when you need to:
- Process collections of data
- Filter, transform, or aggregate data
- Write cleaner, more readable code
- Work with large datasets efficiently

**Common Use Cases:**

1. **Filtering data** - Find items matching criteria
```java
List<String> names = Arrays.asList("John", "Jane", "Bob", "Alice");
List<String> longNames = names.stream()
    .filter(name -> name.length() > 3)
    .collect(Collectors.toList());  // [John, Jane, Alice]
```

2. **Transforming data** - Convert one type to another
```java
List<String> words = Arrays.asList("hello", "world");
List<String> upperCase = words.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());  // [HELLO, WORLD]
```

3. **Finding elements** - Search in collections
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
Optional<Integer> firstEven = numbers.stream()
    .filter(n -> n % 2 == 0)
    .findFirst();  // Optional[2]
```

4. **Aggregating data** - Sum, average, count
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
int sum = numbers.stream()
    .mapToInt(Integer::intValue)
    .sum();  // 15
```

5. **Grouping data** - Group by criteria
```java
List<String> names = Arrays.asList("John", "Jane", "Bob", "Alice");
Map<Integer, List<String>> grouped = names.stream()
    .collect(Collectors.groupingBy(String::length));
// {3=[Bob], 4=[John, Jane], 5=[Alice]}
```

**Real-world analogy:** Like using a coffee machine - you put beans in (source), set filters (intermediate operations), and get coffee out (terminal operation).

---

### 3. Explain the Stream API and its benefits

**Answer:**

**Stream API** = A way to process collections of data in a functional, declarative style

Think of it like this:
- **Old way (imperative)** = "Do this, then that, then check this..." (like giving step-by-step directions)
- **Stream way (declarative)** = "I want filtered, transformed data" (like ordering food - you say what you want, not how to make it)

**Benefits:**

1. **Cleaner code** - Less boilerplate, more readable
```java
// Old way
List<String> result = new ArrayList<>();
for(String name : names) {
    if(name.length() > 3) {
        result.add(name.toUpperCase());
    }
}

// Stream way (much cleaner!)
List<String> result = names.stream()
    .filter(name -> name.length() > 3)
    .map(String::toUpperCase)
    .collect(Collectors.toList());
```

2. **Lazy evaluation** - Operations don't execute until needed (saves resources)
3. **Parallel processing** - Easy to make parallel with `.parallelStream()`
4. **Functional style** - No side effects, easier to test
5. **Chain operations** - Combine multiple operations easily

**Simple example:**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Find sum of squares of even numbers
int result = numbers.stream()
    .filter(n -> n % 2 == 0)    // Keep only even: [2, 4, 6, 8, 10]
    .map(n -> n * n)             // Square each: [4, 16, 36, 64, 100]
    .reduce(0, Integer::sum);    // Sum all: 220

System.out.println(result);  // 220
```

**Key Point:** Streams make your code read like English - "filter even numbers, square them, then sum them up"

---

### 4. How do you handle null values in the Stream API?

**Answer:**

Null values can cause `NullPointerException` in streams. Here are ways to handle them:

**Method 1: Filter out nulls**
```java
List<String> names = Arrays.asList("John", null, "Jane", null, "Bob");

List<String> nonNullNames = names.stream()
    .filter(Objects::nonNull)  // Remove nulls
    .collect(Collectors.toList());  // [John, Jane, Bob]
```

**Method 2: Use Optional**
```java
List<String> names = Arrays.asList("John", null, "Jane");

List<String> result = names.stream()
    .map(Optional::ofNullable)      // Wrap in Optional
    .filter(Optional::isPresent)    // Keep non-null
    .map(Optional::get)
    .collect(Collectors.toList());
```

**Method 3: Handle nulls in map operation**
```java
List<String> names = Arrays.asList("John", null, "Jane");

List<Integer> lengths = names.stream()
    .filter(Objects::nonNull)
    .map(String::length)
    .collect(Collectors.toList());  // [4, 4]
```

**Method 4: Use flatMap with Optional**
```java
List<String> names = Arrays.asList("John", null, "Jane");

List<String> upperCase = names.stream()
    .map(name -> Optional.ofNullable(name))
    .filter(Optional::isPresent)
    .map(opt -> opt.get().toUpperCase())
    .collect(Collectors.toList());  // [JOHN, JANE]
```

**Best Practice:** Always filter nulls first before doing operations, like checking if ingredients exist before cooking!

---

### 5. What is the difference between map() and flatMap()?

**Answer:**

**map()** = Transform one item to one item (1-to-1)
**flatMap()** = Transform one item to multiple items, then flatten (1-to-many, then flatten)

**Simple analogy:**
- `map()` = Each person gets one ID card
- `flatMap()` = Each person has multiple phone numbers, and you want all numbers in one list

**map() example:**
```java
List<String> words = Arrays.asList("hello", "world");

// Each string becomes one integer (length)
List<Integer> lengths = words.stream()
    .map(String::length)
    .collect(Collectors.toList());  // [5, 5]
```

**flatMap() example:**
```java
List<List<String>> nestedList = Arrays.asList(
    Arrays.asList("a", "b"),
    Arrays.asList("c", "d")
);

// Each inner list becomes multiple strings, then flattened
List<String> flatList = nestedList.stream()
    .flatMap(List::stream)  // Flatten nested lists
    .collect(Collectors.toList());  // [a, b, c, d]
```

**Real-world example:**
```java
// Students and their courses
class Student {
    String name;
    List<String> courses;
}

List<Student> students = Arrays.asList(
    new Student("John", Arrays.asList("Math", "Science")),
    new Student("Jane", Arrays.asList("English", "History"))
);

// Get all courses from all students (flatten)
List<String> allCourses = students.stream()
    .flatMap(student -> student.courses.stream())
    .collect(Collectors.toList());  
// [Math, Science, English, History]

// If we used map(), we'd get List<List<String>> - not what we want!
```

**Key Difference:**

| map() | flatMap() |
|-------|-----------|
| 1 input → 1 output | 1 input → many outputs, then flatten |
| Stream<T> → Stream<R> | Stream<T> → Stream<Stream<R>> → Stream<R> |
| Keeps structure | Flattens structure |

**Simple rule:** Use `flatMap()` when you have nested collections and want everything in one flat list!

---

### 6. How does the collect method in stream work

**Answer:**

**collect()** = Terminal operation that gathers all stream elements into a collection (List, Set, Map, etc.)

Think of it like collecting items in a basket - you process items through the stream pipeline, then collect them into your basket.

**Basic usage:**
```java
List<String> names = Arrays.asList("John", "Jane", "Bob");

// Collect into List
List<String> list = names.stream()
    .filter(name -> name.length() > 3)
    .collect(Collectors.toList());  // [John, Jane]

// Collect into Set (removes duplicates)
Set<String> set = names.stream()
    .collect(Collectors.toSet());  // [John, Jane, Bob]

// Collect into Map
Map<String, Integer> map = names.stream()
    .collect(Collectors.toMap(
        name -> name,           // Key
        name -> name.length()    // Value
    ));  // {John=4, Jane=4, Bob=3}
```

**Common Collectors:**

1. **toList()** - Collect to List
```java
List<String> result = stream.collect(Collectors.toList());
```

2. **toSet()** - Collect to Set (no duplicates)
```java
Set<String> result = stream.collect(Collectors.toSet());
```

3. **joining()** - Join strings
```java
String result = stream.collect(Collectors.joining(", "));  
// "John, Jane, Bob"
```

4. **groupingBy()** - Group by criteria
```java
Map<Integer, List<String>> grouped = names.stream()
    .collect(Collectors.groupingBy(String::length));
// {3=[Bob], 4=[John, Jane]}
```

5. **counting()** - Count elements
```java
long count = stream.collect(Collectors.counting());
```

6. **summingInt()** - Sum integers
```java
int sum = stream.collect(Collectors.summingInt(Integer::intValue));
```

**Custom collector example:**
```java
// Collect to ArrayList with custom logic
List<String> result = names.stream()
    .filter(name -> name.startsWith("J"))
    .collect(Collectors.toCollection(ArrayList::new));
```

**Key Point:** `collect()` is the "gathering" step - it takes all processed items and puts them into your chosen container!

---

## Stream API Coding Questions

### 1. Write a code using stream api to find the occurrences of a first name in a given string arraylist

**Answer:**

```java
import java.util.*;
import java.util.stream.Collectors;

public class FindFirstNameOccurrences {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("John Doe", "Jane Smith", "John Brown", 
                                          "Alice Johnson", "John Wilson");
        
        String firstNameToFind = "John";
        
        // Count occurrences
        long count = names.stream()
            .filter(name -> name.startsWith(firstNameToFind + " "))
            .count();
        
        System.out.println("Occurrences of '" + firstNameToFind + "': " + count);
        // Output: Occurrences of 'John': 3
        
        // Get all names with that first name
        List<String> matchingNames = names.stream()
            .filter(name -> name.startsWith(firstNameToFind + " "))
            .collect(Collectors.toList());
        
        System.out.println("Matching names: " + matchingNames);
        // Output: Matching names: [John Doe, John Brown, John Wilson]
        
        // More flexible: Extract first name and compare
        long count2 = names.stream()
            .map(name -> name.split(" ")[0])  // Extract first name
            .filter(firstName -> firstName.equals(firstNameToFind))
            .count();
        
        System.out.println("Count using split: " + count2);  // 3
    }
}
```

**Key Points:**
- Use `filter()` to keep only matching names
- Use `startsWith()` or `split()` to extract first name
- Use `count()` to count occurrences
- Use `collect()` to get list of matching names

---

### 2. Make a list and find the greatest element in the list, make the cube of that element, filter that element if the cube is greater than 50 and print that element

**Answer:**

```java
import java.util.*;
import java.util.stream.Collectors;

public class GreatestElementCube {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(2, 5, 3, 8, 1, 4);
        
        // Step 1: Find greatest element
        Optional<Integer> greatest = numbers.stream()
            .max(Integer::compareTo);
        
        if(greatest.isPresent()) {
            int max = greatest.get();
            System.out.println("Greatest element: " + max);  // 8
            
            // Step 2: Calculate cube
            int cube = max * max * max;
            System.out.println("Cube of " + max + ": " + cube);  // 512
            
            // Step 3: Filter if cube > 50 and print
            if(cube > 50) {
                System.out.println("Element with cube > 50: " + max);  // 8
            }
        }
        
        // One-liner approach
        numbers.stream()
            .max(Integer::compareTo)
            .map(max -> max * max * max)
            .filter(cube -> cube > 50)
            .ifPresent(cube -> {
                int original = (int) Math.cbrt(cube);
                System.out.println("Element: " + original + ", Cube: " + cube);
            });
    }
}
```

**Step-by-step breakdown:**
1. `max()` finds greatest element
2. `map()` calculates cube
3. `filter()` checks if cube > 50
4. `ifPresent()` prints if condition met

---

### 3. Print List of numbers where 2 comes

**Answer:**

```java
import java.util.*;
import java.util.stream.Collectors;

public class NumbersContainingTwo {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(12, 23, 34, 25, 56, 2, 102, 7);
        
        // Method 1: Convert to string and check if contains "2"
        List<Integer> result = numbers.stream()
            .filter(n -> String.valueOf(n).contains("2"))
            .collect(Collectors.toList());
        
        System.out.println("Numbers containing 2: " + result);
        // Output: [12, 23, 25, 2, 102]
        
        // Method 2: Check each digit
        List<Integer> result2 = numbers.stream()
            .filter(n -> {
                int num = Math.abs(n);
                while(num > 0) {
                    if(num % 10 == 2) return true;
                    num /= 10;
                }
                return false;
            })
            .collect(Collectors.toList());
        
        System.out.println("Method 2: " + result2);  // [12, 23, 25, 2, 102]
        
        // Print directly
        numbers.stream()
            .filter(n -> String.valueOf(n).contains("2"))
            .forEach(System.out::println);
        // Output:
        // 12
        // 23
        // 25
        // 2
        // 102
    }
}
```

**Simple approach:** Convert number to string and check if it contains "2"!

---

### 4. Filter Out Even Numbers: Write a program to filter out employees with even Employee IDs. Also, ensure that employees are filtered with double salaries

**Answer:**

```java
import java.util.*;
import java.util.stream.Collectors;

class Employee {
    int id;
    String name;
    double salary;
    
    Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
    
    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', salary=" + salary + "}";
    }
}

public class FilterEmployees {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee(1, "John", 50000),
            new Employee(2, "Jane", 60000),
            new Employee(3, "Bob", 55000),
            new Employee(4, "Alice", 70000),
            new Employee(5, "Charlie", 65000)
        );
        
        // Filter employees with even IDs and double their salaries
        List<Employee> result = employees.stream()
            .filter(emp -> emp.id % 2 == 0)  // Even ID
            .map(emp -> {
                emp.salary = emp.salary * 2;  // Double salary
                return emp;
            })
            .collect(Collectors.toList());
        
        System.out.println("Employees with even IDs and doubled salaries:");
        result.forEach(System.out::println);
        // Output:
        // Employee{id=2, name='Jane', salary=120000.0}
        // Employee{id=4, name='Alice', salary=140000.0}
        
        // Alternative: Create new Employee objects (immutable approach)
        List<Employee> result2 = employees.stream()
            .filter(emp -> emp.id % 2 == 0)
            .map(emp -> new Employee(emp.id, emp.name, emp.salary * 2))
            .collect(Collectors.toList());
    }
}
```

**Key Points:**
- `filter()` keeps only even IDs
- `map()` doubles the salary
- `collect()` gathers results

---

### 5. Frequency of character using Java 8 features

**Answer:**

```java
import java.util.*;
import java.util.stream.Collectors;

public class CharacterFrequency {
    public static void main(String[] args) {
        String str = "programming";
        
        // Method 1: Using groupingBy
        Map<Character, Long> frequency = str.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(
                c -> c,
                Collectors.counting()
            ));
        
        System.out.println("Character frequencies:");
        frequency.forEach((char, count) -> 
            System.out.println(char + ": " + count)
        );
        // Output:
        // p: 1
        // r: 2
        // o: 1
        // g: 2
        // a: 1
        // m: 2
        // i: 1
        // n: 1
        
        // Method 2: Using toMap
        Map<Character, Integer> freq2 = str.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.toMap(
                c -> c,
                c -> 1,
                Integer::sum  // Merge function for duplicates
            ));
        
        System.out.println("\nMethod 2:");
        freq2.forEach((char, count) -> 
            System.out.println(char + ": " + count)
        );
        
        // Method 3: For specific character
        long countOfR = str.chars()
            .filter(c -> c == 'r')
            .count();
        
        System.out.println("\nCount of 'r': " + countOfR);  // 2
    }
}
```

**How it works:**
- `chars()` converts string to IntStream
- `mapToObj()` converts to Character stream
- `groupingBy()` groups by character and counts
- `counting()` counts occurrences in each group

---

### 6. Non Repeating character using Java 8 feature

**Answer:**

```java
import java.util.*;
import java.util.stream.Collectors;

public class NonRepeatingCharacter {
    public static void main(String[] args) {
        String str = "programming";
        
        // Find first non-repeating character
        Optional<Character> firstNonRepeating = str.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(
                c -> c,
                LinkedHashMap::new,  // Preserve insertion order
                Collectors.counting()
            ))
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue() == 1)
            .map(Map.Entry::getKey)
            .findFirst();
        
        if(firstNonRepeating.isPresent()) {
            System.out.println("First non-repeating: " + firstNonRepeating.get());
            // Output: First non-repeating: p
        }
        
        // Find all non-repeating characters
        List<Character> allNonRepeating = str.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(
                c -> c,
                Collectors.counting()
            ))
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue() == 1)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
        
        System.out.println("All non-repeating: " + allNonRepeating);
        // Output: All non-repeating: [p, o, a, i, n]
        
        // More efficient: Using frequency map
        Map<Character, Long> frequency = str.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        
        Character firstNonRepeat = str.chars()
            .mapToObj(c -> (char) c)
            .filter(c -> frequency.get(c) == 1)
            .findFirst()
            .orElse(null);
        
        System.out.println("First non-repeating (efficient): " + firstNonRepeat);
    }
}
```

**Key Points:**
- Count frequency of each character
- Filter characters with count == 1
- Use `LinkedHashMap` to preserve order for first non-repeating

---

### 7. Second/3rd highest element using stream as well as traditional approach

**Answer:**

```java
import java.util.*;
import java.util.stream.Collectors;

public class NthHighestElement {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 5, 20, 15, 30, 25);
        
        // ========== STREAM APPROACH ==========
        
        // Second highest
        Optional<Integer> secondHighest = numbers.stream()
            .distinct()  // Remove duplicates
            .sorted(Collections.reverseOrder())  // Sort descending
            .skip(1)  // Skip first (highest)
            .findFirst();
        
        System.out.println("Second highest (stream): " + 
            secondHighest.orElse(null));  // 25
        
        // Third highest
        Optional<Integer> thirdHighest = numbers.stream()
            .distinct()
            .sorted(Collections.reverseOrder())
            .skip(2)  // Skip first two
            .findFirst();
        
        System.out.println("Third highest (stream): " + 
            thirdHighest.orElse(null));  // 20
        
        // Generic method for Nth highest
        int n = 2;
        Optional<Integer> nthHighest = numbers.stream()
            .distinct()
            .sorted(Collections.reverseOrder())
            .skip(n - 1)
            .findFirst();
        
        System.out.println(n + "nd highest: " + nthHighest.orElse(null));
        
        // ========== TRADITIONAL APPROACH ==========
        
        // Second highest (traditional)
        List<Integer> sorted = new ArrayList<>(numbers);
        Collections.sort(sorted, Collections.reverseOrder());
        Set<Integer> unique = new LinkedHashSet<>(sorted);  // Remove duplicates, keep order
        List<Integer> uniqueList = new ArrayList<>(unique);
        
        if(uniqueList.size() >= 2) {
            System.out.println("Second highest (traditional): " + uniqueList.get(1));  // 25
        }
        
        // Third highest (traditional)
        if(uniqueList.size() >= 3) {
            System.out.println("Third highest (traditional): " + uniqueList.get(2));  // 20
        }
        
        // Alternative traditional: Two passes
        int max = Collections.max(numbers);
        int secondMax = numbers.stream()
            .filter(n -> n != max)
            .mapToInt(Integer::intValue)
            .max()
            .orElse(-1);
        
        System.out.println("Second max (two passes): " + secondMax);
    }
}
```

**Comparison:**

| Stream Approach | Traditional Approach |
|----------------|---------------------|
| More readable | More verbose |
| Functional style | Imperative style |
| Chain operations | Multiple steps |
| Less code | More code |

**Key Points:**
- Stream: `distinct()` → `sorted()` → `skip(n-1)` → `findFirst()`
- Traditional: Sort → Remove duplicates → Get index

---

## Functional Programming

### 1. What is the functional interface and write a code to implement it

**Answer:**

**Functional Interface** = Interface with exactly ONE abstract method (can have multiple default/static methods)

Think of it like a contract: "You must implement this ONE method"

```java
// Functional Interface (has exactly one abstract method)
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);  // Single abstract method
    
    // Can have default methods
    default void printResult(int result) {
        System.out.println("Result: " + result);
    }
    
    // Can have static methods
    static void info() {
        System.out.println("This is a calculator");
    }
}

// Implementation using anonymous class
Calculator add = new Calculator() {
    @Override
    public int calculate(int a, int b) {
        return a + b;
    }
};

// Implementation using Lambda (much simpler!)
Calculator multiply = (a, b) -> a * b;
Calculator subtract = (a, b) -> a - b;

// Usage
System.out.println(add.calculate(5, 3));        // 8
System.out.println(multiply.calculate(5, 3));  // 15
System.out.println(subtract.calculate(5, 3));   // 2
```

**Built-in Functional Interfaces:**
- `Function<T, R>` - Takes T, returns R
- `Predicate<T>` - Takes T, returns boolean
- `Consumer<T>` - Takes T, returns void
- `Supplier<T>` - Takes nothing, returns T

```java
// Using built-in Function
Function<Integer, Integer> square = x -> x * x;
System.out.println(square.apply(5));  // 25

// Using Predicate
Predicate<Integer> isEven = x -> x % 2 == 0;
System.out.println(isEven.test(4));  // true

// Using Consumer
Consumer<String> printer = s -> System.out.println(s);
printer.accept("Hello");  // Hello

// Using Supplier
Supplier<String> supplier = () -> "Hello World";
System.out.println(supplier.get());  // Hello World
```

---

### 2. Functional Interface - Explain

**Answer:**

**Functional Interface** = Interface with exactly ONE abstract method

**Why it exists:**
- Enables Lambda expressions
- Makes code more functional/declarative
- Used extensively in Stream API

**Rules:**
1. Must have exactly ONE abstract method
2. Can have multiple default methods
3. Can have multiple static methods
4. `@FunctionalInterface` annotation is optional but recommended

```java
// Valid Functional Interface
@FunctionalInterface
interface Greeter {
    void greet(String name);  // One abstract method
    
    default void greetDefault() {  // Default method OK
        System.out.println("Hello Default");
    }
    
    static void greetStatic() {  // Static method OK
        System.out.println("Hello Static");
    }
}

// Usage with Lambda
Greeter greeter = name -> System.out.println("Hello " + name);
greeter.greet("John");  // Hello John
```

**Common Examples:**
- `Runnable` - `run()` method
- `Comparator` - `compare()` method
- `Callable` - `call()` method

**Key Point:** Functional Interface = One job, one method. Like a single-purpose tool!

---

### 3. What is Functional Interface

**Answer:**

**Functional Interface** = Interface with exactly ONE abstract method

**Simple explanation:**
- It's a contract that says "implement this one method"
- Used with Lambda expressions
- Makes code cleaner and more functional

**Example:**
```java
// Define functional interface
@FunctionalInterface
interface MathOperation {
    int operate(int a, int b);
}

// Use with Lambda
MathOperation add = (a, b) -> a + b;
MathOperation multiply = (a, b) -> a * b;

System.out.println(add.operate(5, 3));        // 8
System.out.println(multiply.operate(5, 3));   // 15
```

**Why use it?**
- Enables Lambda expressions
- Less boilerplate code
- More readable
- Functional programming style

**Think of it like:** A job description with one main task - "You must do this one thing"

---

### 4. Can we have multiple methods in functional interface

**Answer:**

**Yes, BUT** only ONE abstract method!

**Rules:**
- ✅ One abstract method (required)
- ✅ Multiple default methods (allowed)
- ✅ Multiple static methods (allowed)
- ❌ Multiple abstract methods (NOT allowed)

```java
@FunctionalInterface
interface MyInterface {
    // ONE abstract method (required)
    void doSomething();
    
    // Multiple default methods (OK)
    default void method1() {
        System.out.println("Default 1");
    }
    
    default void method2() {
        System.out.println("Default 2");
    }
    
    // Multiple static methods (OK)
    static void staticMethod1() {
        System.out.println("Static 1");
    }
    
    static void staticMethod2() {
        System.out.println("Static 2");
    }
    
    // ❌ This would break it - can't have second abstract method
    // void doSomethingElse();  // ERROR!
}

// Usage
MyInterface obj = () -> System.out.println("Doing something");
obj.doSomething();      // Doing something
obj.method1();          // Default 1
obj.method2();          // Default 2
MyInterface.staticMethod1();  // Static 1
```

**Key Point:** One abstract method = Functional Interface. Multiple default/static methods = Still functional interface!

---

### 5. What are Lambda expressions?

**Answer:**

**Lambda Expression** = Short way to write anonymous functions (like a mini-function)

Think of it like this:
- **Old way** = Write full method with name, parameters, body
- **Lambda way** = Just write the essential part: parameters and what to do

**Syntax:**
```java
(parameters) -> expression
// OR
(parameters) -> { statements }
```

**Examples:**

```java
// Old way: Anonymous class
Runnable r1 = new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello");
    }
};

// Lambda way: Much shorter!
Runnable r2 = () -> System.out.println("Hello");

// With parameters
Function<Integer, Integer> square = x -> x * x;
System.out.println(square.apply(5));  // 25

// Multiple parameters
BinaryOperator<Integer> add = (a, b) -> a + b;
System.out.println(add.apply(3, 4));  // 7

// With body (multiple statements)
Runnable complex = () -> {
    System.out.println("Step 1");
    System.out.println("Step 2");
};
```

**Common use cases:**

1. **With Stream API**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
numbers.stream()
    .filter(n -> n % 2 == 0)  // Lambda
    .map(n -> n * 2)           // Lambda
    .forEach(n -> System.out.println(n));  // Lambda
```

2. **With Collections**
```java
List<String> names = Arrays.asList("John", "Jane");
names.forEach(name -> System.out.println(name));
```

3. **With Threads**
```java
Thread t = new Thread(() -> System.out.println("Running"));
t.start();
```

**Benefits:**
- Less code
- More readable
- Functional style
- Easy to pass around

**Simple analogy:** Lambda = Recipe card (just ingredients and steps), Regular method = Full cookbook page!

---

### 6. Explain lambda expression

**Answer:**

**Lambda Expression** = Anonymous function (function without a name)

**Why use it?**
- Shorter code
- More readable
- Functional programming style
- Easy to pass functions around

**Syntax breakdown:**
```java
(parameter) -> { body }
```

**Examples:**

**1. No parameters**
```java
Runnable task = () -> System.out.println("Hello");
task.run();  // Hello
```

**2. One parameter**
```java
Consumer<String> printer = name -> System.out.println(name);
printer.accept("John");  // John

// With type (optional)
Consumer<String> printer2 = (String name) -> System.out.println(name);
```

**3. Multiple parameters**
```java
BinaryOperator<Integer> add = (a, b) -> a + b;
System.out.println(add.apply(5, 3));  // 8
```

**4. With return statement**
```java
Function<Integer, Integer> square = x -> {
    int result = x * x;
    return result;
};
// OR simpler
Function<Integer, Integer> square2 = x -> x * x;
```

**5. Real-world example**
```java
List<Person> people = Arrays.asList(
    new Person("John", 25),
    new Person("Jane", 30)
);

// Sort by age using Lambda
people.sort((p1, p2) -> p1.age - p2.age);

// Filter using Lambda
List<Person> young = people.stream()
    .filter(p -> p.age < 30)
    .collect(Collectors.toList());
```

**Key Points:**
- `->` is the Lambda operator (arrow)
- Left side = parameters
- Right side = what to do
- Can use method references: `String::toUpperCase` instead of `s -> s.toUpperCase()`

**Think of it like:** Instead of writing a full recipe, just write "add salt" - Lambda is the short version!

---

## Optional Class

### 1. What is Optional Class

**Answer:**

**Optional** = Container that may or may not contain a value (like a box that might be empty)

**Why use it?**
- Avoids `NullPointerException`
- Makes "no value" explicit
- Forces you to handle null cases

**Think of it like:**
- `null` = "I don't know if there's a value" (dangerous!)
- `Optional.empty()` = "I checked, there's no value" (safe!)

**Basic usage:**

```java
import java.util.Optional;

// Create Optional with value
Optional<String> name = Optional.of("John");
System.out.println(name.get());  // John

// Create empty Optional
Optional<String> empty = Optional.empty();
System.out.println(empty.isPresent());  // false

// Create from nullable (might be null)
String str = null;
Optional<String> opt = Optional.ofNullable(str);
System.out.println(opt.isPresent());  // false

// Safe way to get value
Optional<String> name2 = Optional.of("Jane");
if(name2.isPresent()) {
    System.out.println(name2.get());  // Jane
}

// Better way: Use ifPresent
name2.ifPresent(n -> System.out.println(n));  // Jane

// Default value if empty
Optional<String> empty2 = Optional.empty();
String result = empty2.orElse("Default");  // "Default"

// Throw exception if empty
String value = empty2.orElseThrow(() -> new RuntimeException("No value"));
```

**Common methods:**

```java
Optional<String> opt = Optional.ofNullable(getName());

// Check if value exists
if(opt.isPresent()) { ... }

// Get value (throws exception if empty)
String value = opt.get();

// Get value or default
String value = opt.orElse("Default");

// Get value or throw exception
String value = opt.orElseThrow(() -> new RuntimeException());

// Do something if present
opt.ifPresent(v -> System.out.println(v));

// Transform value
Optional<Integer> length = opt.map(String::length);

// Filter
Optional<String> filtered = opt.filter(s -> s.length() > 3);
```

**Real-world example:**

```java
// Old way (dangerous)
public String getName() {
    return name;  // Might return null!
}

String n = getName();
int len = n.length();  // NullPointerException if n is null!

// New way (safe)
public Optional<String> getName() {
    return Optional.ofNullable(name);
}

Optional<String> n = getName();
int len = n.map(String::length).orElse(0);  // Safe!
```

**Key Benefits:**
- ✅ No more NullPointerException
- ✅ Forces null handling
- ✅ More readable code
- ✅ Functional style

**Simple rule:** Use Optional when a value might not exist. It's like asking "Is there something in the box?" before opening it!

---

## Java 8 Features Overview

### 1. What are main JAVA 8 Features

**Answer:**

Java 8 introduced major features that changed how we write Java code:

**1. Lambda Expressions**
- Anonymous functions
- Makes code shorter and more readable
```java
List<Integer> numbers = Arrays.asList(1, 2, 3);
numbers.forEach(n -> System.out.println(n));
```

**2. Stream API**
- Process collections functionally
- Lazy evaluation, parallel processing
```java
numbers.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * 2)
    .collect(Collectors.toList());
```

**3. Functional Interfaces**
- Interfaces with one abstract method
- Enables Lambda expressions
- `@FunctionalInterface` annotation

**4. Optional Class**
- Avoid NullPointerException
- Explicit null handling
```java
Optional<String> name = Optional.ofNullable(getName());
name.ifPresent(n -> System.out.println(n));
```

**5. Default Methods in Interfaces**
- Interfaces can have method implementations
- Backward compatibility
```java
interface MyInterface {
    default void method() {
        System.out.println("Default implementation");
    }
}
```

**6. Method References**
- Shorthand for Lambdas
```java
list.forEach(System.out::println);  // Instead of s -> System.out.println(s)
```

**7. New Date/Time API**
- `LocalDate`, `LocalTime`, `LocalDateTime`
- Better than old `Date` class
```java
LocalDate today = LocalDate.now();
LocalDate tomorrow = today.plusDays(1);
```

**8. Nashorn JavaScript Engine**
- Run JavaScript in JVM

**Summary Table:**

| Feature | Purpose | Example |
|---------|---------|---------|
| Lambda | Anonymous functions | `x -> x * 2` |
| Stream API | Process collections | `list.stream().filter(...)` |
| Optional | Handle null safely | `Optional.ofNullable(value)` |
| Default Methods | Interface implementations | `default void method() {}` |
| Method References | Shorthand Lambdas | `String::toUpperCase` |
| New Date API | Better date handling | `LocalDate.now()` |

**Impact:** Java 8 made Java more modern, functional, and easier to write!

---

### 2. Which last Java version you worked on?

**Answer:**

This is a personal/project-specific question. Here's how to answer:

**Good answer structure:**
- Mention the version you've used
- Mention features you've used from that version
- Show you're aware of newer versions

**Example answers:**

**If you worked with Java 8:**
"I've primarily worked with Java 8, using features like Stream API, Lambda expressions, and Optional class. I'm familiar with newer versions like Java 11 (LTS) and Java 17 (LTS), and I'm interested in learning about newer features."

**If you worked with Java 11:**
"I've worked extensively with Java 11, which is an LTS (Long Term Support) version. I've used features like:
- Stream API enhancements
- `var` keyword for local variables
- New String methods
- HTTP Client API

I'm also aware of Java 17 (latest LTS) features like sealed classes and pattern matching."

**If you worked with Java 17:**
"I've worked with Java 17, the latest LTS version. I've used:
- Sealed classes for better inheritance control
- Pattern matching for instanceof
- Records for immutable data classes
- Text blocks for multi-line strings

I stay updated with newer versions and their features."

**Key Points:**
- Be honest about what you've used
- Mention specific features you know
- Show awareness of newer versions
- Express willingness to learn

**Tip:** Even if you've only used Java 8, mention that you're aware of newer versions and their benefits!
