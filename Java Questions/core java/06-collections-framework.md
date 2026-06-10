# Java Collection Framework

## 198. What is Collections Framework?

**Collections Framework** is a unified architecture for representing and manipulating collections of objects.

**Main components:**
```
Collection Framework
    ├── Interfaces (List, Set, Map, Queue)
    ├── Classes (ArrayList, HashSet, HashMap)
    └── Algorithms (sort, search)
```

**Benefits:**
- Ready-to-use data structures
- High performance
- Reduces programming effort
- Interoperability

---

## 199. What is collection?

**Collection** is a group of objects represented as a single unit.

```java
// Collection of strings
List<String> names = new ArrayList<>();
names.add("John");
names.add("Mary");
names.add("Bob");

// Collection of integers
Set<Integer> numbers = new HashSet<>();
numbers.add(1);
numbers.add(2);
numbers.add(3);
```

**Examples:**
- List of students
- Set of unique IDs
- Queue of tasks

---

## 200. Difference between collection, Collection and Collections

| Term | Description | Example |
|------|-------------|---------|
| collection (lowercase) | General concept of group of objects | "a collection of books" |
| Collection (interface) | Root interface of Collection Framework | `Collection<String> c = new ArrayList<>();` |
| Collections (class) | Utility class with static methods | `Collections.sort(list);` |

```java
// Collection interface
Collection<String> collection = new ArrayList<>();

// Collections class
Collections.sort(list);
Collections.reverse(list);
Collections.shuffle(list);
```

---

## 201. Explain about Collection interface in Java

**Collection** is the root interface. It represents a group of objects.

```java
Collection<String> collection = new ArrayList<>();

// Basic operations
collection.add("Item1");
collection.remove("Item1");
collection.size();
collection.isEmpty();
collection.contains("Item1");
collection.clear();

// Iterate
for (String item : collection) {
    System.out.println(item);
}
```

**Hierarchy:**
```
Collection
    ├── List
    ├── Set
    └── Queue
```

---

## 202. List the interfaces which extend Collection interface

**Three main interfaces:**

**1. List** - Ordered collection, allows duplicates
**2. Set** - Unordered collection, no duplicates
**3. Queue** - FIFO (First-In-First-Out) collection

```
Collection (interface)
    ├── List (interface)
    │       ├── ArrayList
    │       ├── LinkedList
    │       └── Vector
    │
    ├── Set (interface)
    │       ├── HashSet
    │       ├── LinkedHashSet
    │       └── TreeSet
    │
    └── Queue (interface)
            ├── PriorityQueue
            └── LinkedList
```

---

## 203. Explain List interface

**List** is an ordered collection that allows duplicates and maintains insertion order.

```java
List<String> list = new ArrayList<>();

// Add elements
list.add("Apple");      // Index 0
list.add("Banana");     // Index 1
list.add("Apple");      // Index 2 (duplicate allowed)

// Access by index
String fruit = list.get(0);  // Apple

// Modify by index
list.set(1, "Orange");  // Replace Banana with Orange

// Remove by index
list.remove(0);  // Remove Apple

// Size
int size = list.size();
```

**Features:**
- Maintains insertion order
- Allows duplicates
- Index-based access
- Allows null elements

---

## 204. Explain methods specific to List interface

**List-specific methods:**

```java
List<String> list = new ArrayList<>();

// 1. add(index, element) - Insert at position
list.add(0, "First");

// 2. get(index) - Retrieve element
String item = list.get(0);

// 3. set(index, element) - Replace element
list.set(0, "Updated");

// 4. remove(index) - Remove by index
list.remove(0);

// 5. indexOf(element) - Find first occurrence
int index = list.indexOf("Apple");

// 6. lastIndexOf(element) - Find last occurrence
int lastIndex = list.lastIndexOf("Apple");

// 7. subList(from, to) - Get sub-list
List<String> subList = list.subList(0, 2);

// 8. listIterator() - List iterator
ListIterator<String> iterator = list.listIterator();
```

---

## 205. List implementations of List Interface

**Three main implementations:**

**1. ArrayList**
```java
List<String> arrayList = new ArrayList<>();
```

**2. LinkedList**
```java
List<String> linkedList = new LinkedList<>();
```

**3. Vector**
```java
List<String> vector = new Vector<>();
```

**Comparison:**

| Feature | ArrayList | LinkedList | Vector |
|---------|-----------|------------|--------|
| Data Structure | Dynamic array | Doubly linked list | Dynamic array |
| Access Speed | Fast (O(1)) | Slow (O(n)) | Fast (O(1)) |
| Insert/Delete | Slow | Fast | Slow |
| Thread-safe | No | No | Yes |
| Performance | Best for retrieval | Best for insertion | Synchronized (slower) |

---

## 206. Explain about ArrayList

**ArrayList** is a resizable array implementation.

```java
// Create ArrayList
ArrayList<String> fruits = new ArrayList<>();

// Add elements
fruits.add("Apple");
fruits.add("Banana");
fruits.add("Orange");

// Access element
String first = fruits.get(0);  // Apple

// Update element
fruits.set(1, "Mango");

// Remove element
fruits.remove("Apple");
fruits.remove(0);

// Size
int size = fruits.size();

// Check if contains
boolean hasApple = fruits.contains("Apple");

// Iterate
for (String fruit : fruits) {
    System.out.println(fruit);
}
```

**Features:**
- Fast random access
- Dynamic size
- Allows duplicates and null
- Not thread-safe
- Good for read operations

---

## 207. Difference between Array and ArrayList

| Array | ArrayList |
|-------|-----------|
| Fixed size | Dynamic size |
| Can hold primitives | Only objects (uses wrappers) |
| Length property | size() method |
| Cannot use generics | Uses generics |
| Direct assignment | add() method |

```java
// Array
int[] arr = new int[5];
arr[0] = 10;
int length = arr.length;

// ArrayList
ArrayList<Integer> list = new ArrayList<>();
list.add(10);
int size = list.size();
```

**Example:**
```java
// Array - Fixed size
String[] array = new String[3];
array[0] = "A";
array[1] = "B";
array[2] = "C";
// array[3] = "D";  // Error! Fixed size

// ArrayList - Dynamic size
ArrayList<String> list = new ArrayList<>();
list.add("A");
list.add("B");
list.add("C");
list.add("D");  // OK! Dynamic size
```

---

## 208. What is Vector?

**Vector** is a synchronized (thread-safe) dynamic array.

```java
Vector<String> vector = new Vector<>();

// Add elements
vector.add("Item1");
vector.add("Item2");

// Access
String item = vector.get(0);

// Vector-specific methods
vector.addElement("Item3");      // Same as add()
vector.elementAt(0);             // Same as get()
vector.removeElement("Item1");   // Same as remove()
vector.capacity();               // Current capacity
```

**Features:**
- Thread-safe (synchronized)
- Dynamic array
- Legacy class (from Java 1.0)
- Slower than ArrayList
- Capacity doubles when full

---

## 209. Difference between ArrayList and Vector

| ArrayList | Vector |
|-----------|--------|
| Not synchronized | Synchronized |
| Fast | Slower |
| Capacity increases by 50% | Capacity doubles |
| Introduced in Java 1.2 | Legacy class (Java 1.0) |
| Preferred for single-threaded | Used for multi-threaded |

```java
// ArrayList - Not thread-safe
ArrayList<String> arrayList = new ArrayList<>();

// Vector - Thread-safe
Vector<String> vector = new Vector<>();
```

**When to use:**
- **ArrayList:** Single-threaded applications (faster)
- **Vector:** Multi-threaded applications (or use Collections.synchronizedList())

---

## 210. Define LinkedList and its features

**LinkedList** is a doubly-linked list implementation.

```java
LinkedList<String> linkedList = new LinkedList<>();

// Add elements
linkedList.add("A");
linkedList.add("B");
linkedList.addFirst("First");
linkedList.addLast("Last");

// Remove elements
linkedList.removeFirst();
linkedList.removeLast();

// Access
String first = linkedList.getFirst();
String last = linkedList.getLast();
```

**Signature:**
```java
public class LinkedList<E> 
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E> { }
```

**Features:**
- Doubly-linked list
- Fast insertion/deletion
- Slow random access
- Can be used as List, Queue, or Deque
- Not thread-safe

**Internal structure:**
```
Node ↔ Node ↔ Node ↔ Node
[A]    [B]    [C]    [D]
```

---

## 211. Define Iterator and methods in Iterator

**Iterator** is used to traverse collections.

```java
List<String> list = new ArrayList<>();
list.add("A");
list.add("B");
list.add("C");

// Get iterator
Iterator<String> iterator = list.iterator();

// Iterate
while (iterator.hasNext()) {
    String item = iterator.next();
    System.out.println(item);
}
```

**Methods:**

**1. hasNext()** - Returns true if more elements exist
**2. next()** - Returns next element
**3. remove()** - Removes current element

```java
Iterator<String> it = list.iterator();

// Check if has next
if (it.hasNext()) {
    // Get next element
    String item = it.next();
    
    // Remove element
    it.remove();
}
```

---

## 212. In which order does Iterator iterate?

**Iterator traverses in the collection's natural order:**

**ArrayList/LinkedList:** Insertion order
```java
List<String> list = new ArrayList<>();
list.add("A");
list.add("B");
list.add("C");

Iterator<String> it = list.iterator();
while (it.hasNext()) {
    System.out.println(it.next());
}
// Output: A, B, C
```

**HashSet:** No specific order (unpredictable)
```java
Set<String> set = new HashSet<>();
set.add("C");
set.add("A");
set.add("B");

// Output: Could be any order (C, B, A or A, C, B, etc.)
```

**TreeSet:** Sorted order
```java
Set<String> set = new TreeSet<>();
set.add("C");
set.add("A");
set.add("B");

// Output: A, B, C (sorted)
```

---

## 213. Explain ListIterator and methods

**ListIterator** is a bidirectional iterator for Lists.

```java
List<String> list = new ArrayList<>();
list.add("A");
list.add("B");
list.add("C");

ListIterator<String> iterator = list.listIterator();

// Forward iteration
while (iterator.hasNext()) {
    String item = iterator.next();
    System.out.println(item);
}

// Backward iteration
while (iterator.hasPrevious()) {
    String item = iterator.previous();
    System.out.println(item);
}
```

**Methods:**

```java
ListIterator<String> it = list.listIterator();

// 1. hasNext() - Check if next exists
boolean hasNext = it.hasNext();

// 2. next() - Get next element
String next = it.next();

// 3. hasPrevious() - Check if previous exists
boolean hasPrevious = it.hasPrevious();

// 4. previous() - Get previous element
String previous = it.previous();

// 5. nextIndex() - Get next index
int nextIndex = it.nextIndex();

// 6. previousIndex() - Get previous index
int prevIndex = it.previousIndex();

// 7. remove() - Remove current element
it.remove();

// 8. set() - Replace current element
it.set("New Value");

// 9. add() - Add element
it.add("New Item");
```

**Difference from Iterator:**

| Iterator | ListIterator |
|----------|--------------|
| Forward only | Bidirectional |
| No index methods | Has index methods |
| Only remove() | remove(), set(), add() |
| For all collections | Only for List |

---

## 214. Explain about Sets

**Set** is a collection that doesn't allow duplicates.

```java
Set<String> set = new HashSet<>();

// Add elements
set.add("Apple");
set.add("Banana");
set.add("Apple");  // Ignored (duplicate)

System.out.println(set.size());  // 2 (not 3)

// Check if contains
boolean hasApple = set.contains("Apple");

// Remove
set.remove("Apple");

// Iterate
for (String item : set) {
    System.out.println(item);
}
```

**Features:**
- No duplicates
- No specific order (depends on implementation)
- Allows one null element (except TreeSet)

---

## 215. Implementations of Set interface

**Three main implementations:**

**1. HashSet** - Fastest, no order
```java
Set<String> hashSet = new HashSet<>();
```

**2. LinkedHashSet** - Maintains insertion order
```java
Set<String> linkedHashSet = new LinkedHashSet<>();
```

**3. TreeSet** - Sorted order
```java
Set<String> treeSet = new TreeSet<>();
```

**Comparison:**

| HashSet | LinkedHashSet | TreeSet |
|---------|---------------|---------|
| No order | Insertion order | Sorted order |
| Fastest | Medium | Slowest |
| Allows null | Allows null | No null |
| Uses HashMap | Uses LinkedHashMap | Uses TreeMap |

---

## 216. Explain HashSet and its features

**HashSet** stores elements in a hash table.

```java
HashSet<String> set = new HashSet<>();

// Add elements
set.add("Apple");
set.add("Banana");
set.add("Orange");
set.add("Apple");  // Duplicate ignored

// Output: Could be any order
// [Banana, Orange, Apple] or [Apple, Orange, Banana], etc.
```

**Features:**
- No duplicates
- No order guarantee
- Allows one null
- Fast operations (O(1) for add, remove, contains)
- Uses hashCode() and equals()
- Not thread-safe

**How it works:**
```
hashCode("Apple") → Hash → Bucket → Store
hashCode("Banana") → Hash → Bucket → Store
```

---

## 217. Explain TreeSet and its features

**TreeSet** stores elements in sorted order.

```java
TreeSet<Integer> set = new TreeSet<>();

// Add elements in random order
set.add(50);
set.add(20);
set.add(80);
set.add(10);

// Output: Always sorted
System.out.println(set);  // [10, 20, 50, 80]

// Navigation methods
Integer first = set.first();      // 10
Integer last = set.last();        // 80
Integer lower = set.lower(50);    // 20
Integer higher = set.higher(50);  // 80
```

**Features:**
- Sorted order (natural or custom)
- No duplicates
- No null elements
- Slower than HashSet (O(log n))
- Uses Red-Black tree
- Not thread-safe

**Custom sorting:**
```java
TreeSet<String> set = new TreeSet<>((a, b) -> b.compareTo(a));
set.add("C");
set.add("A");
set.add("B");

System.out.println(set);  // [C, B, A] (reverse order)
```

---

## 218. When do we use HashSet over TreeSet?

**Use HashSet when:**
- Don't need sorted order
- Need fast operations
- Order doesn't matter

```java
// Fast lookup of unique values
HashSet<String> uniqueEmails = new HashSet<>();
```

**Use TreeSet when:**
- Need sorted order
- Need navigation methods (first, last, lower, higher)
- Willing to sacrifice speed for sorting

```java
// Sorted set of scores
TreeSet<Integer> scores = new TreeSet<>();
scores.add(85);
scores.add(92);
scores.add(78);
System.out.println(scores);  // [78, 85, 92]
```

**Performance:**

| Operation | HashSet | TreeSet |
|-----------|---------|---------|
| add() | O(1) | O(log n) |
| remove() | O(1) | O(log n) |
| contains() | O(1) | O(log n) |

---

## 219. What is LinkedHashSet and its features?

**LinkedHashSet** maintains insertion order.

```java
LinkedHashSet<String> set = new LinkedHashSet<>();

set.add("C");
set.add("A");
set.add("B");

System.out.println(set);  // [C, A, B] (insertion order)
```

**Features:**
- Maintains insertion order
- No duplicates
- Allows one null
- Slightly slower than HashSet
- Uses doubly-linked list + hash table

**Comparison:**
```java
// HashSet - No order
Set<String> hashSet = new HashSet<>();
hashSet.add("C");
hashSet.add("A");
hashSet.add("B");
// Output: [A, B, C] or [B, C, A] or any order

// LinkedHashSet - Insertion order
Set<String> linkedHashSet = new LinkedHashSet<>();
linkedHashSet.add("C");
linkedHashSet.add("A");
linkedHashSet.add("B");
// Output: [C, A, B] (always)
```

---

## 220. Explain about Map interface in Java

**Map** stores key-value pairs. Keys are unique.

```java
Map<String, Integer> map = new HashMap<>();

// Put key-value pairs
map.put("John", 25);
map.put("Mary", 30);
map.put("Bob", 28);

// Get value by key
Integer age = map.get("John");  // 25

// Check if key exists
boolean hasKey = map.containsKey("John");

// Check if value exists
boolean hasValue = map.containsValue(25);

// Remove
map.remove("John");

// Size
int size = map.size();

// Iterate
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}
```

**Note:** Map doesn't extend Collection interface

---

## 221. What is LinkedHashMap and its features?

**LinkedHashMap** maintains insertion order of keys.

```java
LinkedHashMap<String, Integer> map = new LinkedHashMap<>();

map.put("C", 3);
map.put("A", 1);
map.put("B", 2);

// Maintains insertion order
for (String key : map.keySet()) {
    System.out.println(key);  // C, A, B
}
```

**Features:**
- Insertion order maintained
- Allows one null key
- Slightly slower than HashMap

---

## 222. What is SortedMap interface?

**SortedMap** maintains keys in sorted order.

```java
SortedMap<String, Integer> map = new TreeMap<>();

map.put("C", 3);
map.put("A", 1);
map.put("B", 2);

// Keys are sorted
for (String key : map.keySet()) {
    System.out.println(key);  // A, B, C
}

// Additional methods
String firstKey = map.firstKey();  // A
String lastKey = map.lastKey();    // C
```

---

## 223. What is Hashtable and its features?

**Hashtable** is a synchronized (thread-safe) Map.

```java
Hashtable<String, Integer> table = new Hashtable<>();

table.put("John", 25);
table.put("Mary", 30);
// table.put(null, 35);  // Error! No null keys
// table.put("Bob", null);  // Error! No null values
```

**Features:**
- Thread-safe (synchronized)
- No null keys or values
- Legacy class
- Slower than HashMap

---

## 224. Difference between HashMap and Hashtable

| HashMap | Hashtable |
|---------|-----------|
| Not synchronized | Synchronized |
| Fast | Slower |
| Allows one null key | No null keys |
| Allows null values | No null values |
| Java 1.2 | Java 1.0 (legacy) |
| Preferred | Use ConcurrentHashMap instead |

```java
// HashMap - Fast, not thread-safe
Map<String, Integer> hashMap = new HashMap<>();
hashMap.put(null, 1);  // OK

// Hashtable - Slow, thread-safe
Hashtable<String, Integer> hashtable = new Hashtable<>();
// hashtable.put(null, 1);  // Error!
```

---

## 225. Difference between ArrayList and LinkedList

| ArrayList | LinkedList |
|-----------|------------|
| Array-based | Linked nodes |
| Fast access (O(1)) | Slow access (O(n)) |
| Slow insertion/deletion | Fast insertion/deletion |
| Less memory | More memory (stores pointers) |
| Best for retrieval | Best for manipulation |

```java
// ArrayList - Best for frequent access
ArrayList<String> arrayList = new ArrayList<>();
String item = arrayList.get(1000);  // Fast

// LinkedList - Best for frequent insertion/deletion
LinkedList<String> linkedList = new LinkedList<>();
linkedList.addFirst("Item");  // Fast
linkedList.removeLast();      // Fast
```

---

## 226. Difference between Comparator and Comparable

| Comparable | Comparator |
|-----------|------------|
| Single sorting sequence | Multiple sorting sequences |
| compareTo() method | compare() method |
| In same class | Separate class |
| Natural ordering | Custom ordering |

```java
// Comparable - Natural ordering
class Student implements Comparable<Student> {
    String name;
    int age;
    
    public int compareTo(Student other) {
        return this.age - other.age;  // Sort by age
    }
}

// Comparator - Custom ordering
class NameComparator implements Comparator<Student> {
    public int compare(Student s1, Student s2) {
        return s1.name.compareTo(s2.name);  // Sort by name
    }
}

// Usage
Collections.sort(students);  // Uses Comparable
Collections.sort(students, new NameComparator());  // Uses Comparator
```

---

## 227. What is ConcurrentHashMap and its features?

**ConcurrentHashMap** is a thread-safe Map without locking the entire map.

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

map.put("Key1", 1);
map.put("Key2", 2);
```

**Features:**
- Thread-safe
- Better performance than Hashtable
- No null keys or values
- Segments for concurrent access

---

## 228. Difference between ConcurrentHashMap, Hashtable, and synchronizedHashMap

| ConcurrentHashMap | Hashtable | synchronizedHashMap |
|-------------------|-----------|---------------------|
| Segment locking | Full map locking | Full map locking |
| Best performance | Slowest | Slow |
| Java 1.5+ | Legacy | Wrapper |

```java
// ConcurrentHashMap - Best for multi-threading
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

// Hashtable - Legacy
Hashtable<String, Integer> table = new Hashtable<>();

// synchronizedHashMap - Wrapper
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
```

---

## 229. Explain copyOnWriteArrayList

**CopyOnWriteArrayList** creates a new copy of array for every modification.

```java
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

list.add("A");
list.add("B");

// Iterate (doesn't throw ConcurrentModificationException)
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    list.add("C");  // OK during iteration
    System.out.println(it.next());
}
```

**Use when:**
- Read operations >> Write operations
- Thread-safe iteration needed

---

## 230. Explain fail-fast iterators

**Fail-fast iterator** throws `ConcurrentModificationException` if collection is modified during iteration.

```java
List<String> list = new ArrayList<>();
list.add("A");
list.add("B");

Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String item = it.next();
    list.add("C");  // ConcurrentModificationException!
}
```

**Examples:** ArrayList, HashMap, HashSet

---

## 231. Explain fail-safe iterators

**Fail-safe iterator** works on a copy of collection, doesn't throw exception.

```java
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
list.add("A");
list.add("B");

Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String item = it.next();
    list.add("C");  // OK! No exception
}
```

**Examples:** CopyOnWriteArrayList, ConcurrentHashMap

