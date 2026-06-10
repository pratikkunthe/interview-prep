# Collections Framework Interview Questions

## 1. What is the Collections Framework in Java?

**Answer:**

Collections Framework is a unified architecture for storing and manipulating groups of objects.

**Hierarchy:**
```
Collection (interface)
├── List (interface) - ordered, allows duplicates
│   ├── ArrayList
│   ├── LinkedList
│   └── Vector
├── Set (interface) - no duplicates
│   ├── HashSet
│   ├── LinkedHashSet
│   └── TreeSet
└── Queue (interface) - FIFO order
    ├── PriorityQueue
    └── LinkedList

Map (interface) - key-value pairs
├── HashMap
├── LinkedHashMap
├── TreeMap
└── Hashtable
```

---

## 2. ArrayList vs LinkedList

**Answer:**

| ArrayList | LinkedList |
|-----------|------------|
| Array-based | Node-based (doubly linked) |
| Fast random access `get(index)` | Slow random access |
| Slow insert/delete in middle | Fast insert/delete anywhere |
| Less memory | More memory (stores pointers) |

**ArrayList:**
```java
List<String> list = new ArrayList<>();
list.add("Apple");      // Fast
list.get(0);           // Very fast O(1)
list.add(1, "Banana"); // Slow (shifts elements)
```

**LinkedList:**
```java
List<String> list = new LinkedList<>();
list.add("Apple");        // Fast
list.get(0);             // Slow O(n)
list.addFirst("Banana"); // Very fast O(1)
```

**When to use:**
- ArrayList: Frequent reading/accessing
- LinkedList: Frequent adding/removing

---

## 3. HashSet vs TreeSet vs LinkedHashSet

**Answer:**

| Feature | HashSet | LinkedHashSet | TreeSet |
|---------|---------|---------------|---------|
| Order | No order | Insertion order | Sorted order |
| Duplicates | No | No | No |
| Null | One null allowed | One null allowed | No null (Java 7+) |
| Performance | Fast O(1) | Fast O(1) | Slower O(log n) |
| Internal | HashMap | LinkedHashMap | TreeMap |

**HashSet:**
```java
Set<String> set = new HashSet<>();
set.add("Banana");
set.add("Apple");
set.add("Cherry");
// Order: random (maybe Banana, Cherry, Apple)
```

**LinkedHashSet:**
```java
Set<String> set = new LinkedHashSet<>();
set.add("Banana");
set.add("Apple");
set.add("Cherry");
// Order: Banana, Apple, Cherry (insertion order)
```

**TreeSet:**
```java
Set<String> set = new TreeSet<>();
set.add("Banana");
set.add("Apple");
set.add("Cherry");
// Order: Apple, Banana, Cherry (sorted)
```

---

## 4. HashMap vs Hashtable vs ConcurrentHashMap

**Answer:**

| Feature | HashMap | Hashtable | ConcurrentHashMap |
|---------|---------|-----------|-------------------|
| Thread-safe | No | Yes | Yes |
| Null key/value | Allowed | Not allowed | Not allowed |
| Performance | Fast | Slow (synchronized) | Fast |
| Legacy | No | Yes (legacy) | No |

**HashMap:**
```java
Map<String, Integer> map = new HashMap<>();
map.put("Apple", 100);
map.put(null, 50);  // null key allowed
// Not thread-safe
```

**Hashtable:**
```java
Map<String, Integer> map = new Hashtable<>();
map.put("Apple", 100);
// map.put(null, 50);  // throws NullPointerException
// Thread-safe but slow (locks entire table)
```

**ConcurrentHashMap:**
```java
Map<String, Integer> map = new ConcurrentHashMap<>();
map.put("Apple", 100);
// Thread-safe and fast (locks only segments)
```

**When to use:**
- HashMap: Single-threaded applications
- ConcurrentHashMap: Multi-threaded applications
- Hashtable: Legacy code (avoid in new code)

---

## 5. How does HashMap work internally?

**Answer:**

HashMap uses an array of buckets. Each bucket can hold multiple entries (using linked list or tree).

**Process:**
1. Calculate hash code of the key
2. Find bucket index using `hashCode() % capacity`
3. Store key-value pair in that bucket
4. If collision occurs, use linked list (or tree if > 8 entries)

```java
Map<String, Integer> map = new HashMap<>();
map.put("Apple", 100);

// Internal steps:
// 1. "Apple".hashCode() = 63476538
// 2. index = 63476538 % 16 = 10
// 3. Store at bucket[10]
```

**Key Concepts:**
- Default capacity: 16
- Load factor: 0.75 (resizes when 75% full)
- Collision handling: Linked list → Tree (if > 8 entries)

```java
// Custom object as key - must override hashCode() and equals()
class Employee {
    int id;
    String name;
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee emp = (Employee) obj;
        return id == emp.id && Objects.equals(name, emp.name);
    }
}
```

---

## 6. What is the difference between List and Set?

**Answer:**

| List | Set |
|------|-----|
| Ordered collection | Unordered (except LinkedHashSet, TreeSet) |
| Allows duplicates | No duplicates |
| Access by index | No index-based access |
| Multiple null values | One null value (except TreeSet) |

**List:**
```java
List<String> list = new ArrayList<>();
list.add("Apple");
list.add("Apple");  // duplicate allowed
list.add(null);
list.add(null);     // multiple nulls allowed
System.out.println(list);  // [Apple, Apple, null, null]
```

**Set:**
```java
Set<String> set = new HashSet<>();
set.add("Apple");
set.add("Apple");  // ignored (duplicate)
set.add(null);
set.add(null);     // ignored (duplicate null)
System.out.println(set);  // [Apple, null]
```

---

## 7. What is Iterator and how is it used?

**Answer:**

Iterator is used to traverse collections one by one.

```java
List<String> list = Arrays.asList("Apple", "Banana", "Cherry");

// Using Iterator
Iterator<String> iterator = list.iterator();
while (iterator.hasNext()) {
    String item = iterator.next();
    System.out.println(item);
}
```

**Iterator vs for-each:**
```java
// for-each loop (cannot remove elements)
for (String item : list) {
    System.out.println(item);
    // list.remove(item);  // ConcurrentModificationException
}

// Iterator (can remove elements safely)
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String item = it.next();
    if (item.equals("Banana")) {
        it.remove();  // Safe removal
    }
}
```

---

## 8. What is the difference between Comparable and Comparator?

**Answer:**

**Comparable:**
- Natural ordering (single sorting logic)
- Implemented in the class itself
- Uses `compareTo()` method

```java
class Student implements Comparable<Student> {
    String name;
    int age;
    
    @Override
    public int compareTo(Student other) {
        return this.age - other.age;  // sort by age
    }
}

List<Student> students = new ArrayList<>();
Collections.sort(students);  // uses compareTo()
```

**Comparator:**
- Custom ordering (multiple sorting logic)
- Separate class or lambda
- Uses `compare()` method

```java
class Student {
    String name;
    int age;
}

// Sort by age
Comparator<Student> ageComparator = (s1, s2) -> s1.age - s2.age;

// Sort by name
Comparator<Student> nameComparator = (s1, s2) -> s1.name.compareTo(s2.name);

Collections.sort(students, ageComparator);  // custom sorting
```

**When to use:**
- Comparable: Default sorting logic
- Comparator: Multiple sorting options

---

## 9. What is fail-fast and fail-safe iterator?

**Answer:**

**Fail-Fast:**
- Throws `ConcurrentModificationException` if collection is modified during iteration
- Used by: ArrayList, HashMap, HashSet

```java
List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));
Iterator<String> it = list.iterator();

while (it.hasNext()) {
    String item = it.next();
    list.remove(item);  // ConcurrentModificationException
}
```

**Fail-Safe:**
- Works on clone/copy of collection
- No exception if modified
- Used by: CopyOnWriteArrayList, ConcurrentHashMap

```java
List<String> list = new CopyOnWriteArrayList<>(Arrays.asList("A", "B", "C"));
Iterator<String> it = list.iterator();

while (it.hasNext()) {
    String item = it.next();
    list.remove(item);  // No exception
}
```

---

## 10. What is PriorityQueue?

**Answer:**

PriorityQueue orders elements based on priority (natural order or custom comparator).

```java
// Min heap (default - smallest element at front)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
minHeap.add(5);
minHeap.add(2);
minHeap.add(8);
System.out.println(minHeap.poll());  // 2 (smallest)

// Max heap (custom comparator - largest element at front)
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
maxHeap.add(5);
maxHeap.add(2);
maxHeap.add(8);
System.out.println(maxHeap.poll());  // 8 (largest)
```

**Use cases:**
- Task scheduling (priority-based)
- Dijkstra's algorithm
- Huffman coding

---

## 11. What is the difference between Arrays.asList() and new ArrayList()?

**Answer:**

**Arrays.asList():**
- Returns fixed-size list
- Backed by original array (modifications reflect in array)
- Cannot add/remove elements

```java
String[] arr = {"A", "B", "C"};
List<String> list = Arrays.asList(arr);
list.set(0, "Z");  // OK - modifies array too
// list.add("D");  // UnsupportedOperationException
```

**new ArrayList():**
- Returns resizable list
- Independent copy
- Can add/remove elements

```java
String[] arr = {"A", "B", "C"};
List<String> list = new ArrayList<>(Arrays.asList(arr));
list.set(0, "Z");  // OK - doesn't modify array
list.add("D");     // OK
```

---

## 12. How to synchronize a collection?

**Answer:**

**Method 1: Collections.synchronizedXXX()**
```java
List<String> list = new ArrayList<>();
List<String> syncList = Collections.synchronizedList(list);

// Manual synchronization needed for iteration
synchronized (syncList) {
    Iterator<String> it = syncList.iterator();
    while (it.hasNext()) {
        System.out.println(it.next());
    }
}
```

**Method 2: Concurrent Collections (Better)**
```java
// Thread-safe and better performance
List<String> list = new CopyOnWriteArrayList<>();
Map<String, Integer> map = new ConcurrentHashMap<>();
Set<String> set = ConcurrentHashMap.newKeySet();
```

---

## 13. What is WeakHashMap?

**Answer:**

WeakHashMap uses weak references for keys. If a key is no longer referenced elsewhere, it can be garbage collected.

```java
Map<String, String> map = new WeakHashMap<>();
String key = new String("key1");
map.put(key, "value1");

System.out.println(map.size());  // 1

key = null;  // No strong reference to key
System.gc();  // Suggest garbage collection

Thread.sleep(1000);
System.out.println(map.size());  // 0 (key was garbage collected)
```

**Use case:** Cache implementations where entries can be removed automatically when memory is low.

---

## 14. What is the difference between poll() and remove() in Queue?

**Answer:**

| Method | Empty Queue Behavior |
|--------|---------------------|
| poll() | Returns null |
| remove() | Throws NoSuchElementException |

```java
Queue<String> queue = new LinkedList<>();

System.out.println(queue.poll());    // null
System.out.println(queue.remove());  // NoSuchElementException

queue.add("A");
System.out.println(queue.poll());    // "A"
System.out.println(queue.remove());  // NoSuchElementException
```

**Similar methods:**
- `peek()` vs `element()` - Same difference for viewing front element

---

## 15. How to create an immutable collection?

**Answer:**

**Java 9+ (Best way):**
```java
List<String> list = List.of("A", "B", "C");
Set<String> set = Set.of("A", "B", "C");
Map<String, Integer> map = Map.of("A", 1, "B", 2);

// list.add("D");  // UnsupportedOperationException
```

**Before Java 9:**
```java
List<String> mutableList = new ArrayList<>();
mutableList.add("A");
mutableList.add("B");

List<String> immutableList = Collections.unmodifiableList(mutableList);
// immutableList.add("C");  // UnsupportedOperationException
```

**Note:** Original list can still be modified in the second approach.
