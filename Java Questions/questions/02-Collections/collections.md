# Collections Framework

## List Interface

### 1. ArrayList vs LinkedList

**Answer:**

**ArrayList** - Uses array internally (like numbered parking slots)

```java
List<String> arrayList = new ArrayList<>();
arrayList.add("Apple");   // Fast add at end
arrayList.get(2);         // Fast access by index (like slot number)
arrayList.add(1, "Banana");  // Slow - needs to shift elements
```

**LinkedList** - Uses nodes with pointers (like a chain)

```java
List<String> linkedList = new LinkedList<>();
linkedList.add("Apple");     // Fast add anywhere
linkedList.get(2);           // Slow - must traverse from start
linkedList.addFirst("Banana");  // Very fast
```

**Key Differences:**

| ArrayList | LinkedList |
|-----------|------------|
| Array based | Node based (doubly linked) |
| Fast access by index `get(i)` | Slow access (must traverse) |
| Slow insert/delete in middle | Fast insert/delete anywhere |
| Less memory | More memory (stores pointers) |
| Good for reading | Good for adding/removing |

**When to use:**
- ArrayList: When you mostly read data (`get(index)`)
- LinkedList: When you mostly add/remove data

---

### 3. Write a code to create a traditional linkedList and define add method

**Answer:**

```java
// Node class - each element in linked list
class Node {
    int data;
    Node next;  // Pointer to next node
    
    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}

// LinkedList class
class MyLinkedList {
    Node head;  // First node
    
    // Add at the end
    public void add(int data) {
        Node newNode = new Node(data);
        
        // If list is empty
        if(head == null) {
            head = newNode;
            return;
        }
        
        // Traverse to the last node
        Node current = head;
        while(current.next != null) {
            current = current.next;
        }
        
        // Add new node at the end
        current.next = newNode;
    }
    
    // Add at the beginning
    public void addFirst(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }
    
    // Add at specific position
    public void addAt(int position, int data) {
        Node newNode = new Node(data);
        
        if(position == 0) {
            addFirst(data);
            return;
        }
        
        Node current = head;
        for(int i = 0; i < position - 1 && current != null; i++) {
            current = current.next;
        }
        
        if(current != null) {
            newNode.next = current.next;
            current.next = newNode;
        }
    }
    
    // Display the linked list
    public void display() {
        Node current = head;
        while(current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        MyLinkedList list = new MyLinkedList();
        
        list.add(10);
        list.add(20);
        list.add(30);
        list.display();  // 10 -> 20 -> 30 -> null
        
        list.addFirst(5);
        list.display();  // 5 -> 10 -> 20 -> 30 -> null
        
        list.addAt(2, 15);
        list.display();  // 5 -> 10 -> 15 -> 20 -> 30 -> null
    }
}
```

---

## Map Interface

### 1. How does HashMap work internally in Java?

**Answer:**

HashMap stores data in **key-value pairs** using **hashing**.

**How it works (simple explanation):**

1. **Hash Function:** Converts key into a number (hash code)
2. **Bucket:** Array of slots where values are stored
3. **Index:** Hash code determines which bucket to use

```java
HashMap<String, Integer> map = new HashMap<>();
map.put("Apple", 100);

// Internally:
// 1. "Apple".hashCode() = 63476538 (some number)
// 2. Index = hashCode % arraySize = 63476538 % 16 = 10
// 3. Store value 100 at bucket index 10
```

**Structure:**

```
Bucket Array (size 16 by default)
[0] -> null
[1] -> null
[2] -> Entry(key="Mango", value=50) -> Entry(key="Banana", value=25)
[3] -> null
[4] -> Entry(key="Apple", value=100)
...
[15] -> null
```

**Collision:** When two keys have same hash code/index

```java
// Before Java 8: Used LinkedList
[2] -> Entry1 -> Entry2 -> Entry3

// After Java 8: If more than 8 elements, converts to Tree
[2] -> TreeNode (faster search)
```

**Important points:**
- Initial capacity: 16 buckets
- Load factor: 0.75 (75% full, then resize to double)
- O(1) average time for get/put
- Uses hashCode() and equals() methods

---

### 3. Working of hashmap and enhancement after Java 8

**Answer:**

**Before Java 8:**

```
Bucket -> LinkedList (if collision)
[0] -> null
[1] -> Entry1 -> Entry2 -> Entry3 -> Entry4 (Slow search: O(n))
```

**After Java 8 Enhancement:**

```
Bucket -> Tree (if more than 8 elements)
[0] -> null
[1] -> TreeNode (Balanced BST - Fast search: O(log n))
```

**Key improvements:**

1. **Treeify:** When bucket has > 8 elements, LinkedList converts to Red-Black Tree
2. **Untreeify:** When elements reduce to < 6, Tree converts back to LinkedList

```java
HashMap<String, Integer> map = new HashMap<>();

// Add 9 elements with same hash code (same bucket)
// First 8 stored as LinkedList
// 9th element triggers conversion to Tree
```

**Why this enhancement?**
- LinkedList search: O(n) - slow when many collisions
- Tree search: O(log n) - much faster

**Other Java 8 enhancements:**
```java
// compute if absent
map.computeIfAbsent("key", k -> expensiveOperation());

// merge
map.merge("key", 1, Integer::sum);  // Add or update

// forEach
map.forEach((k, v) -> System.out.println(k + ": " + v));
```

---

### 4. Difference between HashMap, HashTable, and ConcurrentHashMap?

**Answer:**

**HashMap** - Fast but not thread-safe

```java
HashMap<String, Integer> map = new HashMap<>();
map.put("key", 100);
map.put(null, 200);  // Allows one null key
map.put("key2", null);  // Allows null values

// Problem: Multiple threads can corrupt data
```

**HashTable** - Thread-safe but very slow

```java
Hashtable<String, Integer> table = new Hashtable<>();
table.put("key", 100);
// table.put(null, 200);  // NullPointerException! No null allowed

// Entire table is locked - only one thread can access
```

**ConcurrentHashMap** - Thread-safe and fast

```java
ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
concurrentMap.put("key", 100);
// concurrentMap.put(null, 200);  // NullPointerException! No null allowed

// Only locks the segment being modified - multiple threads can access different parts
```

**Comparison:**

| Feature | HashMap | Hashtable | ConcurrentHashMap |
|---------|---------|-----------|-------------------|
| Thread-safe | ❌ No | ✅ Yes | ✅ Yes |
| Speed | Very fast | Slow | Fast |
| Null key/value | ✅ Yes | ❌ No | ❌ No |
| Locking | No lock | Locks entire table | Locks only segment |
| When to use | Single thread | Legacy code (don't use) | Multiple threads |

**Simple analogy:**
- HashMap = Public park (no rules, fast but chaotic if crowded)
- Hashtable = One-person bathroom (safe but long queue)
- ConcurrentHashMap = Multiple bathroom stalls (safe and efficient)

---

### 5. Difference between hashmap and concurrent hashmap

**Answer:**

**HashMap** - Not thread-safe

```java
HashMap<String, Integer> map = new HashMap<>();

// If multiple threads modify simultaneously
Thread 1: map.put("A", 1);
Thread 2: map.put("B", 2);
// Can cause data corruption or infinite loop!
```

**ConcurrentHashMap** - Thread-safe with segment locking

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

// Multiple threads can safely modify
Thread 1: map.put("A", 1);  // Locks only segment with "A"
Thread 2: map.put("B", 2);  // Can work on different segment simultaneously
```

**Key Differences:**

| HashMap | ConcurrentHashMap |
|---------|-------------------|
| Single thread | Multiple threads |
| No synchronization | Segment-level locking |
| Faster | Slightly slower (due to locking) |
| Allows null key/value | No null key/value |
| Can fail in multithreading | Safe in multithreading |

**How ConcurrentHashMap achieves thread-safety:**

```
Divides map into 16 segments (by default)

Segment 0: [entries...]  → Thread 1 can modify here
Segment 1: [entries...]  → Thread 2 can modify here (simultaneously!)
Segment 2: [entries...]
...
Segment 15: [entries...]
```

**When to use:**
- HashMap: Single-threaded applications
- ConcurrentHashMap: Multi-threaded applications (like web servers)

---

### 6. Difference between hashmap and hashset

**Answer:**

**HashMap** - Stores key-value pairs

```java
HashMap<String, Integer> map = new HashMap<>();
map.put("Apple", 100);   // Key: "Apple", Value: 100
map.put("Banana", 200);  // Key: "Banana", Value: 200

System.out.println(map.get("Apple"));  // 100
```

**HashSet** - Stores only unique values (no key-value, just values)

```java
HashSet<String> set = new HashSet<>();
set.add("Apple");   // Just value
set.add("Banana");  // Just value
set.add("Apple");   // Duplicate ignored!

System.out.println(set.size());  // 2 (not 3)
```

**Key Differences:**

| HashMap | HashSet |
|---------|---------|
| Stores key-value pairs | Stores only values |
| `put(key, value)` | `add(value)` |
| Allows duplicate values (not keys) | No duplicates allowed |
| Fast access using key | Check if value exists |

**Secret:** HashSet internally uses HashMap!

```java
// HashSet internal implementation
public class HashSet<E> {
    private HashMap<E, Object> map;  // Uses HashMap
    private static final Object PRESENT = new Object();
    
    public boolean add(E e) {
        return map.put(e, PRESENT) == null;  // Value is always PRESENT object
    }
}
```

**When to use:**
- HashMap: When you need key-value mapping (Student ID → Student Name)
- HashSet: When you need unique collection (Unique email addresses)

---

### 7. Synchronized hashmap and concurrent hashmap

**Answer:**

**Synchronized HashMap** - Manually synchronized, locks entire map

```java
// Creating synchronized HashMap
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());

syncMap.put("A", 1);  // Entire map is locked
syncMap.get("B");     // Entire map is locked

// Problem: Only ONE thread can access at a time
```

**ConcurrentHashMap** - Built-in thread-safety, locks only segments

```java
ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();

concurrentMap.put("A", 1);  // Locks only segment containing "A"
concurrentMap.get("B");     // Can access different segment simultaneously
```

**Key Differences:**

| Synchronized HashMap | ConcurrentHashMap |
|---------------------|-------------------|
| Locks entire map | Locks only affected segment |
| One thread at a time | Multiple threads can work on different segments |
| Iterator needs external synchronization | Iterator is fail-safe |
| Slower | Faster |
| Allows null | No null allowed |

**Performance example:**

```java
// Synchronized HashMap
Thread 1: map.put("A", 1);  → Locks entire map
Thread 2: map.get("Z");     → Must wait (even though different key!)

// ConcurrentHashMap
Thread 1: map.put("A", 1);  → Locks segment 1
Thread 2: map.get("Z");     → Works on segment 10 (no waiting!)
```

**When to use:**
- Synchronized HashMap: When you need null keys/values with thread-safety
- ConcurrentHashMap: High performance multi-threaded applications (recommended)

---

### 8. We have 2 maps, return the output to map key of first map with value of second map

**Answer:**

**Problem:** Combine two maps - take keys from Map1 and values from Map2

```java
public class MergeMaps {
    public static void main(String[] args) {
        // Map 1
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("A", 1);
        map1.put("B", 2);
        map1.put("C", 3);
        
        // Map 2
        Map<String, String> map2 = new HashMap<>();
        map2.put("A", "Apple");
        map2.put("B", "Banana");
        map2.put("C", "Cherry");
        
        // Result map: Key from map1, Value from map2
        Map<String, String> result = new HashMap<>();
        
        for(String key : map1.keySet()) {
            if(map2.containsKey(key)) {
                result.put(key, map2.get(key));
            }
        }
        
        System.out.println(result);
        // Output: {A=Apple, B=Banana, C=Cherry}
    }
}
```

**Using Java 8 Streams:**

```java
Map<String, String> result = map1.keySet().stream()
    .filter(map2::containsKey)  // Only keys present in both maps
    .collect(Collectors.toMap(
        key -> key,           // Key from map1
        key -> map2.get(key)  // Value from map2
    ));
```

**Another scenario - merge based on different keys:**

```java
// Map 1: ID -> Name
Map<Integer, String> map1 = Map.of(1, "John", 2, "Jane");

// Map 2: Name -> Salary
Map<String, Double> map2 = Map.of("John", 50000.0, "Jane", 60000.0);

// Result: ID -> Salary
Map<Integer, Double> result = new HashMap<>();
for(Map.Entry<Integer, String> entry : map1.entrySet()) {
    String name = entry.getValue();
    if(map2.containsKey(name)) {
        result.put(entry.getKey(), map2.get(name));
    }
}

System.out.println(result);  // {1=50000.0, 2=60000.0}
```

---

### 9. Sort the map using values

**Answer:**

**Problem:** HashMap is unordered, need to sort by values

```java
import java.util.*;
import java.util.stream.*;

public class SortMapByValue {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("Apple", 40);
        map.put("Banana", 10);
        map.put("Cherry", 30);
        map.put("Date", 20);
        
        System.out.println("Original: " + map);
        
        // Method 1: Using Java 8 Streams (most popular)
        Map<String, Integer> sortedMap = map.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())  // Sort by value
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new  // Maintains insertion order
            ));
        
        System.out.println("Sorted: " + sortedMap);
        // Output: {Banana=10, Date=20, Cherry=30, Apple=40}
        
        // Method 2: Reverse order (descending)
        Map<String, Integer> reverseSorted = map.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
        
        System.out.println("Reverse: " + reverseSorted);
        // Output: {Apple=40, Cherry=30, Date=20, Banana=10}
    }
}
```

**Method 3: Traditional way using List**

```java
// Convert map to list of entries
List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());

// Sort the list by value
list.sort(Map.Entry.comparingByValue());

// Put back into LinkedHashMap to maintain order
Map<String, Integer> sortedMap = new LinkedHashMap<>();
for(Map.Entry<String, Integer> entry : list) {
    sortedMap.put(entry.getKey(), entry.getValue());
}

System.out.println(sortedMap);
```

**Method 4: Using TreeMap with custom comparator**

```java
// Create TreeMap with value comparator
Map<String, Integer> sortedMap = new TreeMap<>((k1, k2) -> {
    int valueCompare = map.get(k1).compareTo(map.get(k2));
    if(valueCompare == 0) {
        return k1.compareTo(k2);  // If values same, sort by key
    }
    return valueCompare;
});

sortedMap.putAll(map);
System.out.println(sortedMap);
```

**Simple explanation:**
1. Convert map to list of entries
2. Sort the list by values
3. Put sorted entries into LinkedHashMap (maintains order)
