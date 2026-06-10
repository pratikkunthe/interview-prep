# Java 8 Features Interview Questions

## 1. What are the main features introduced in Java 8?

**Answer:**

1. **Lambda Expressions** - Functional programming
2. **Stream API** - Process collections functionally
3. **Functional Interfaces** - Interface with single abstract method
4. **Default Methods** - Methods in interfaces with implementation
5. **Optional Class** - Handle null values
6. **Method References** - Shorthand for lambda
7. **Date/Time API** - New java.time package
8. **forEach() method** - Iterate collections easily

---

## 2. What are Lambda Expressions?

**Answer:**

Lambda expressions enable functional programming - treating functions as method arguments.

**Syntax:** `(parameters) -> expression` or `(parameters) -> { statements }`

**Before Java 8:**
```java
// Anonymous inner class
Runnable r = new Runnable() {
    @Override
    public void run() {
        System.out.println("Running");
    }
};
```

**Java 8 Lambda:**
```java
Runnable r = () -> System.out.println("Running");
```

**More Examples:**
```java
// No parameter
() -> System.out.println("Hello")

// One parameter (parentheses optional)
x -> x * x

// Multiple parameters
(a, b) -> a + b

// Multiple statements
(a, b) -> {
    int sum = a + b;
    return sum;
}
```

---

## 3. What is a Functional Interface?

**Answer:**

An interface with exactly one abstract method. Used as lambda expression target.

**Built-in Functional Interfaces:**

**1. Predicate<T>** - Takes input, returns boolean
```java
Predicate<Integer> isEven = n -> n % 2 == 0;
System.out.println(isEven.test(4));  // true
```

**2. Function<T, R>** - Takes input T, returns R
```java
Function<String, Integer> length = s -> s.length();
System.out.println(length.apply("Hello"));  // 5
```

**3. Consumer<T>** - Takes input, returns nothing
```java
Consumer<String> print = s -> System.out.println(s);
print.accept("Hello");  // Hello
```

**4. Supplier<T>** - Takes nothing, returns T
```java
Supplier<Double> random = () -> Math.random();
System.out.println(random.get());  // random number
```

**Custom Functional Interface:**
```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}

Calculator add = (a, b) -> a + b;
Calculator multiply = (a, b) -> a * b;

System.out.println(add.calculate(5, 3));      // 8
System.out.println(multiply.calculate(5, 3)); // 15
```

---

## 4. What is Stream API?

**Answer:**

Stream API processes collections in a functional way (filter, map, reduce operations).

**Stream is NOT a data structure** - it's a pipeline of operations on data.

**Creating Streams:**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

Stream<Integer> stream1 = numbers.stream();
Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5);
```

**Common Operations:**

**1. filter() - Filter elements**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
List<Integer> evens = numbers.stream()
                            .filter(n -> n % 2 == 0)
                            .collect(Collectors.toList());
// [2, 4, 6]
```

**2. map() - Transform elements**
```java
List<String> names = Arrays.asList("alice", "bob", "charlie");
List<String> upper = names.stream()
                          .map(String::toUpperCase)
                          .collect(Collectors.toList());
// [ALICE, BOB, CHARLIE]
```

**3. sorted() - Sort elements**
```java
List<Integer> numbers = Arrays.asList(5, 2, 8, 1);
List<Integer> sorted = numbers.stream()
                              .sorted()
                              .collect(Collectors.toList());
// [1, 2, 5, 8]
```

**4. reduce() - Combine elements**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
int sum = numbers.stream()
                 .reduce(0, (a, b) -> a + b);
// 15
```

**5. collect() - Convert to collection**
```java
Set<Integer> set = numbers.stream()
                          .collect(Collectors.toSet());

String joined = names.stream()
                     .collect(Collectors.joining(", "));
// "alice, bob, charlie"
```

---

## 5. What is the difference between Intermediate and Terminal operations?

**Answer:**

**Intermediate Operations:**
- Returns another stream
- Lazy (not executed until terminal operation)
- Examples: `filter()`, `map()`, `sorted()`, `distinct()`

**Terminal Operations:**
- Returns result or produces side effect
- Triggers stream processing
- Examples: `collect()`, `forEach()`, `reduce()`, `count()`

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

numbers.stream()
    .filter(n -> {
        System.out.println("Filter: " + n);
        return n % 2 == 0;
    })  // Intermediate - not executed yet
    .map(n -> {
        System.out.println("Map: " + n);
        return n * 10;
    })  // Intermediate - not executed yet
    .collect(Collectors.toList());  // Terminal - triggers all operations

// Output:
// Filter: 1
// Filter: 2
// Map: 2
// Filter: 3
// Filter: 4
// Map: 4
// Filter: 5
```

---

## 6. What is Optional class?

**Answer:**

Optional is a container that may or may not contain a value. Used to avoid `NullPointerException`.

**Creating Optional:**
```java
Optional<String> optional1 = Optional.of("Hello");           // value present
Optional<String> optional2 = Optional.empty();               // empty
Optional<String> optional3 = Optional.ofNullable(null);      // may be null
```

**Using Optional:**
```java
Optional<String> optional = Optional.of("Hello");

// Check if value present
if (optional.isPresent()) {
    System.out.println(optional.get());
}

// Better way - ifPresent()
optional.ifPresent(value -> System.out.println(value));

// Get value with default
String result = optional.orElse("Default");

// Get value or throw exception
String result = optional.orElseThrow(() -> new Exception("Not found"));
```

**Real-world example:**
```java
// Before Optional
public String findUserName(int id) {
    User user = findUser(id);
    if (user != null) {
        String name = user.getName();
        if (name != null) {
            return name.toUpperCase();
        }
    }
    return "UNKNOWN";
}

// With Optional
public String findUserName(int id) {
    return findUser(id)
            .map(User::getName)
            .map(String::toUpperCase)
            .orElse("UNKNOWN");
}
```

---

## 7. What are Method References?

**Answer:**

Method references are shorthand for lambda expressions that only call a method.

**Syntax:** `ClassName::methodName`

**Types:**

**1. Static method reference**
```java
// Lambda
Function<String, Integer> parser1 = s -> Integer.parseInt(s);

// Method reference
Function<String, Integer> parser2 = Integer::parseInt;
```

**2. Instance method reference**
```java
String str = "Hello";

// Lambda
Supplier<String> upper1 = () -> str.toUpperCase();

// Method reference
Supplier<String> upper2 = str::toUpperCase;
```

**3. Constructor reference**
```java
// Lambda
Supplier<List<String>> list1 = () -> new ArrayList<>();

// Constructor reference
Supplier<List<String>> list2 = ArrayList::new;
```

**4. Instance method of arbitrary object**
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

// Lambda
names.stream().map(s -> s.toUpperCase());

// Method reference
names.stream().map(String::toUpperCase);
```

---

## 8. What are Default Methods in interfaces?

**Answer:**

Default methods allow adding new methods to interfaces without breaking existing implementations.

```java
interface Vehicle {
    void start();  // abstract method
    
    // Default method with implementation
    default void honk() {
        System.out.println("Beep beep!");
    }
}

class Car implements Vehicle {
    @Override
    public void start() {
        System.out.println("Car started");
    }
    // honk() inherited from interface
}

Car car = new Car();
car.start();  // Car started
car.honk();   // Beep beep!
```

**Why Default Methods?**
- Add new features to interfaces without breaking old code
- Enable multiple inheritance of behavior

**Diamond Problem Solution:**
```java
interface A {
    default void show() { System.out.println("A"); }
}

interface B {
    default void show() { System.out.println("B"); }
}

class C implements A, B {
    @Override
    public void show() {
        A.super.show();  // Choose which default to use
    }
}
```

---

## 9. What is forEach() method?

**Answer:**

`forEach()` iterates over collections using lambda expressions or method references.

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

// Traditional for loop
for (String name : names) {
    System.out.println(name);
}

// forEach with lambda
names.forEach(name -> System.out.println(name));

// forEach with method reference (cleaner)
names.forEach(System.out::println);
```

**With Map:**
```java
Map<String, Integer> map = new HashMap<>();
map.put("Alice", 25);
map.put("Bob", 30);

map.forEach((key, value) -> {
    System.out.println(key + " -> " + value);
});
```

---

## 10. What is the new Date/Time API in Java 8?

**Answer:**

Java 8 introduced `java.time` package to fix issues with old `java.util.Date`.

**Main Classes:**

**1. LocalDate** - Date without time
```java
LocalDate today = LocalDate.now();
LocalDate date = LocalDate.of(2024, 1, 15);

System.out.println(today);  // 2024-01-15
System.out.println(date.plusDays(10));  // 2024-01-25
```

**2. LocalTime** - Time without date
```java
LocalTime now = LocalTime.now();
LocalTime time = LocalTime.of(14, 30, 45);

System.out.println(now);  // 14:30:45.123
```

**3. LocalDateTime** - Date and Time
```java
LocalDateTime now = LocalDateTime.now();
LocalDateTime dt = LocalDateTime.of(2024, 1, 15, 14, 30);

System.out.println(now);  // 2024-01-15T14:30:45.123
```

**4. ZonedDateTime** - Date, Time, and Timezone
```java
ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("America/New_York"));
System.out.println(zdt);
```

**5. Period and Duration**
```java
// Period - Date-based (days, months, years)
LocalDate start = LocalDate.of(2024, 1, 1);
LocalDate end = LocalDate.of(2024, 12, 31);
Period period = Period.between(start, end);
System.out.println(period.getMonths());  // 11

// Duration - Time-based (hours, minutes, seconds)
LocalTime time1 = LocalTime.of(10, 0);
LocalTime time2 = LocalTime.of(14, 30);
Duration duration = Duration.between(time1, time2);
System.out.println(duration.toHours());  // 4
```

---

## 11. What is the difference between map() and flatMap()?

**Answer:**

**map():**
- Transforms each element (1-to-1 mapping)
- Returns Stream<T>

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
List<Integer> lengths = names.stream()
                             .map(String::length)
                             .collect(Collectors.toList());
// [5, 3, 7]
```

**flatMap():**
- Flattens nested structures (1-to-many mapping)
- Returns Stream<T> from Stream<Stream<T>>

```java
List<List<Integer>> nested = Arrays.asList(
    Arrays.asList(1, 2),
    Arrays.asList(3, 4),
    Arrays.asList(5, 6)
);

// map returns Stream<Stream<Integer>>
List<Stream<Integer>> withMap = nested.stream()
                                      .map(list -> list.stream())
                                      .collect(Collectors.toList());

// flatMap returns Stream<Integer> (flattened)
List<Integer> withFlatMap = nested.stream()
                                  .flatMap(list -> list.stream())
                                  .collect(Collectors.toList());
// [1, 2, 3, 4, 5, 6]
```

**Practical Example:**
```java
List<String> sentences = Arrays.asList("Hello World", "Java Stream");

// Get all words from all sentences
List<String> words = sentences.stream()
                              .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
                              .collect(Collectors.toList());
// [Hello, World, Java, Stream]
```

---

## 12. How to find duplicate elements in a list using Stream API?

**Answer:**

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 2, 4, 3, 5);

// Method 1: Using Collections.frequency()
Set<Integer> duplicates = numbers.stream()
    .filter(n -> Collections.frequency(numbers, n) > 1)
    .collect(Collectors.toSet());

// Method 2: Using Set
Set<Integer> seen = new HashSet<>();
Set<Integer> duplicates = numbers.stream()
    .filter(n -> !seen.add(n))  // add() returns false if already present
    .collect(Collectors.toSet());

System.out.println(duplicates);  // [2, 3]
```

---

## 13. How to convert a List to Map using Stream API?

**Answer:**

```java
class Employee {
    int id;
    String name;
    
    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

List<Employee> employees = Arrays.asList(
    new Employee(1, "Alice"),
    new Employee(2, "Bob"),
    new Employee(3, "Charlie")
);

// Convert to Map<Integer, String> (id -> name)
Map<Integer, String> map = employees.stream()
    .collect(Collectors.toMap(
        emp -> emp.id,      // key
        emp -> emp.name     // value
    ));

// Or using method references
Map<Integer, Employee> empMap = employees.stream()
    .collect(Collectors.toMap(
        Employee::getId,    // key
        Function.identity() // value (entire object)
    ));
```

---

## 14. What is parallel stream?

**Answer:**

Parallel streams split data into multiple chunks and process them in parallel using multiple threads.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

// Sequential stream
numbers.stream()
       .forEach(System.out::println);  // 1, 2, 3, 4, 5, 6, 7, 8 (in order)

// Parallel stream
numbers.parallelStream()
       .forEach(System.out::println);  // random order (processed in parallel)
```

**When to use Parallel Streams:**
- Large datasets
- CPU-intensive operations
- Independent operations (no shared state)

**When NOT to use:**
- Small datasets (overhead > benefit)
- I/O operations (disk, network)
- Operations need specific order

**Example:**
```java
long count = IntStream.rangeClosed(1, 1000000)
                     .parallel()
                     .filter(n -> n % 2 == 0)
                     .count();
// Faster than sequential for large data
```

---

## 15. What are Static methods in interfaces (Java 8)?

**Answer:**

Java 8 allows static methods in interfaces for utility/helper methods.

```java
interface MathUtils {
    // Static method
    static int add(int a, int b) {
        return a + b;
    }
    
    static int multiply(int a, int b) {
        return a * b;
    }
}

// Call without implementing interface
int result = MathUtils.add(5, 3);  // 8
```

**Difference from Default Methods:**
- Static methods cannot be overridden
- Called using interface name, not object
- Default methods can be overridden
