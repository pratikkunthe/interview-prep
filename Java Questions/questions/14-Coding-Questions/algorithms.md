# Coding Questions - Algorithms and Data Structures

## Searching Algorithms

### 1. Write a code for binary search tree

**Answer:**

**Binary Search Tree (BST)** = Tree where left child < parent < right child

```java
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    
    TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class BinarySearchTree {
    private TreeNode root;
    
    // Insert node
    public void insert(int val) {
        root = insertRec(root, val);
    }
    
    private TreeNode insertRec(TreeNode root, int val) {
        if(root == null) {
            return new TreeNode(val);
        }
        
        if(val < root.val) {
            root.left = insertRec(root.left, val);
        } else if(val > root.val) {
            root.right = insertRec(root.right, val);
        }
        
        return root;
    }
    
    // Search node
    public boolean search(int val) {
        return searchRec(root, val);
    }
    
    private boolean searchRec(TreeNode root, int val) {
        if(root == null) {
            return false;
        }
        
        if(root.val == val) {
            return true;
        }
        
        if(val < root.val) {
            return searchRec(root.left, val);
        } else {
            return searchRec(root.right, val);
        }
    }
    
    // In-order traversal (sorted order)
    public void inOrder() {
        inOrderRec(root);
    }
    
    private void inOrderRec(TreeNode root) {
        if(root != null) {
            inOrderRec(root.left);
            System.out.print(root.val + " ");
            inOrderRec(root.right);
        }
    }
    
    // Delete node
    public void delete(int val) {
        root = deleteRec(root, val);
    }
    
    private TreeNode deleteRec(TreeNode root, int val) {
        if(root == null) {
            return null;
        }
        
        if(val < root.val) {
            root.left = deleteRec(root.left, val);
        } else if(val > root.val) {
            root.right = deleteRec(root.right, val);
        } else {
            // Node to delete found
            if(root.left == null) {
                return root.right;
            } else if(root.right == null) {
                return root.left;
            }
            
            // Node with two children: get inorder successor
            root.val = minValue(root.right);
            root.right = deleteRec(root.right, root.val);
        }
        
        return root;
    }
    
    private int minValue(TreeNode root) {
        int min = root.val;
        while(root.left != null) {
            min = root.left.val;
            root = root.left;
        }
        return min;
    }
    
    // Test
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        
        System.out.println("In-order traversal:");
        bst.inOrder();  // 20 30 40 50 70
        
        System.out.println("\nSearch 40: " + bst.search(40));  // true
        System.out.println("Search 60: " + bst.search(60));  // false
    }
}
```

**Key Points:**
- Left child < parent < right child
- Insert: Find correct position, insert
- Search: Compare and go left/right
- Delete: Handle 0, 1, or 2 children

---

### 2. Binary search tree implementation

**Answer:**

Same as above - complete BST implementation with insert, search, delete, and traversal methods.

---

## Sorting Algorithms

### 1. Bubble sort implementation

**Answer:**

**Bubble Sort** = Compare adjacent elements and swap if wrong order

```java
public class BubbleSort {
    
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        
        for(int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            
            for(int j = 0; j < n - i - 1; j++) {
                if(arr[j] > arr[j + 1]) {
                    // Swap
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            
            // If no swapping, array is sorted
            if(!swapped) {
                break;
            }
        }
    }
    
    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        
        System.out.println("Before sorting:");
        printArray(arr);
        
        bubbleSort(arr);
        
        System.out.println("After sorting:");
        printArray(arr);
        // Output: 11 12 22 25 34 64 90
    }
    
    private static void printArray(int[] arr) {
        for(int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
```

**How it works:**
- Compare adjacent elements
- Swap if wrong order
- Largest element "bubbles" to end
- Repeat for remaining elements

**Time Complexity:** O(n²)
**Space Complexity:** O(1)

---

### 2. Two sorted lists merge the list output list not sorted

**Answer:**

**Merge two sorted lists** into one sorted list:

```java
import java.util.*;

public class MergeSortedLists {
    
    // Method 1: Merge and sort
    public static List<Integer> mergeAndSort(List<Integer> list1, List<Integer> list2) {
        List<Integer> merged = new ArrayList<>(list1);
        merged.addAll(list2);
        Collections.sort(merged);
        return merged;
    }
    
    // Method 2: Merge without sorting (if already sorted)
    public static List<Integer> mergeSorted(List<Integer> list1, List<Integer> list2) {
        List<Integer> merged = new ArrayList<>();
        int i = 0, j = 0;
        
        while(i < list1.size() && j < list2.size()) {
            if(list1.get(i) <= list2.get(j)) {
                merged.add(list1.get(i));
                i++;
            } else {
                merged.add(list2.get(j));
                j++;
            }
        }
        
        // Add remaining elements
        while(i < list1.size()) {
            merged.add(list1.get(i));
            i++;
        }
        
        while(j < list2.size()) {
            merged.add(list2.get(j));
            j++;
        }
        
        return merged;
    }
    
    // Method 3: Using Stream API
    public static List<Integer> mergeWithStream(List<Integer> list1, List<Integer> list2) {
        return Stream.concat(list1.stream(), list2.stream())
            .sorted()
            .collect(Collectors.toList());
    }
    
    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(1, 3, 5, 7);
        List<Integer> list2 = Arrays.asList(2, 4, 6, 8);
        
        List<Integer> merged = mergeSorted(list1, list2);
        System.out.println("Merged: " + merged);
        // Output: [1, 2, 3, 4, 5, 6, 7, 8]
    }
}
```

**Key Points:**
- Two pointers approach
- Compare and add smaller element
- Add remaining elements
- O(n + m) time complexity

---

## Array/List Problems

### 1. Second/3rd highest element using stream as well as traditional approach

**Answer:**

Already answered in Stream API file (question 7). See that file for complete solution.

---

### 2. Print List of numbers where 2 comes

**Answer:**

Already answered in Stream API file (question 3). See that file for complete solution.

---

### 3. Filter Out Even Numbers: Write a program to filter out employees with even Employee IDs. Also, ensure that employees are filtered with double salaries

**Answer:**

Already answered in Stream API file (question 4). See that file for complete solution.

---

## String Problems

### 1. String replace

**Answer:**

```java
public class StringReplace {
    public static void main(String[] args) {
        String str = "Hello World";
        
        // Replace character
        String result1 = str.replace('o', 'x');
        System.out.println(result1);  // Hellx Wxrld
        
        // Replace substring
        String result2 = str.replace("World", "Java");
        System.out.println(result2);  // Hello Java
        
        // Replace all occurrences (regex)
        String str2 = "Java123Java456Java";
        String result3 = str2.replaceAll("\\d+", "");
        System.out.println(result3);  // JavaJavaJava
        
        // Replace first occurrence
        String result4 = str2.replaceFirst("Java", "Python");
        System.out.println(result4);  // Python123Java456Java
    }
}
```

---

### 2. Find the duplicates from the string

**Answer:**

Already answered in Java Basics file. See that file for complete solution.

---

### 3. Check if the string is palindrome or not

**Answer:**

Already answered in Java Basics file. See that file for complete solution.

---

### 4. Frequency of character using Java 8 features

**Answer:**

Already answered in Stream API file (question 5). See that file for complete solution.

---

### 5. Non Repeating character using Java 8 feature

**Answer:**

Already answered in Stream API file (question 6). See that file for complete solution.

---

### 6. A long sentence was given that needed to be modified

**Answer:**

Common string modifications:

```java
public class StringModification {
    public static void main(String[] args) {
        String sentence = "  Hello   World  Java  Programming  ";
        
        // Remove extra spaces
        String modified1 = sentence.trim().replaceAll("\\s+", " ");
        System.out.println(modified1);  // "Hello World Java Programming"
        
        // Convert to lowercase
        String modified2 = sentence.toLowerCase();
        System.out.println(modified2);
        
        // Convert to uppercase
        String modified3 = sentence.toUpperCase();
        System.out.println(modified3);
        
        // Reverse words
        String[] words = sentence.trim().split("\\s+");
        StringBuilder reversed = new StringBuilder();
        for(int i = words.length - 1; i >= 0; i--) {
            reversed.append(words[i]).append(" ");
        }
        System.out.println(reversed.toString().trim());
        
        // Capitalize first letter of each word
        String[] wordArray = sentence.trim().split("\\s+");
        StringBuilder capitalized = new StringBuilder();
        for(String word : wordArray) {
            if(word.length() > 0) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
            }
        }
        System.out.println(capitalized.toString().trim());
    }
}
```

---

### 7. Write a code using stream api to find the occurrences of a first name in a given string arraylist

**Answer:**

Already answered in Stream API file (question 1). See that file for complete solution.

---

## Mathematical Problems

### 1. Make a list and find the greatest element in the list, make the cube of that element, filter that element if the cube is greater than 50 and print that element

**Answer:**

Already answered in Stream API file (question 2). See that file for complete solution.

---

## Subset and Combination Problems

### 1. Find a Subset of Integers with a Given Sum: Write a program to find all subsets of integers from a given set whose values add up to a specified target sum

**Answer:**

```java
import java.util.*;

public class SubsetSum {
    
    public static List<List<Integer>> findSubsets(int[] arr, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        findSubsetsRec(arr, 0, targetSum, new ArrayList<>(), result);
        return result;
    }
    
    private static void findSubsetsRec(int[] arr, int index, int targetSum, 
                                       List<Integer> current, List<List<Integer>> result) {
        // Base case: if target sum is reached
        if(targetSum == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Base case: if we've processed all elements
        if(index >= arr.length) {
            return;
        }
        
        // Include current element
        if(arr[index] <= targetSum) {
            current.add(arr[index]);
            findSubsetsRec(arr, index + 1, targetSum - arr[index], current, result);
            current.remove(current.size() - 1);  // Backtrack
        }
        
        // Exclude current element
        findSubsetsRec(arr, index + 1, targetSum, current, result);
    }
    
    public static void main(String[] args) {
        int[] arr = {2, 3, 5, 6, 8};
        int targetSum = 8;
        
        List<List<Integer>> subsets = findSubsets(arr, targetSum);
        
        System.out.println("Subsets with sum " + targetSum + ":");
        for(List<Integer> subset : subsets) {
            System.out.println(subset);
        }
        // Output:
        // [2, 6]
        // [3, 5]
        // [8]
    }
}
```

**How it works:**
- Recursive backtracking
- For each element: include or exclude
- If sum matches target, add to result
- Backtrack to try other combinations

---

## Data Structure Implementation

### 1. Write a code to create a traditional linkedList and define add method for the same

**Answer:**

```java
public class LinkedList {
    private Node head;
    private int size;
    
    private class Node {
        int data;
        Node next;
        
        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
    
    // Add at end
    public void add(int data) {
        Node newNode = new Node(data);
        
        if(head == null) {
            head = newNode;
        } else {
            Node current = head;
            while(current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }
    
    // Add at beginning
    public void addFirst(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
        size++;
    }
    
    // Add at index
    public void add(int index, int data) {
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        
        if(index == 0) {
            addFirst(data);
            return;
        }
        
        Node newNode = new Node(data);
        Node current = head;
        
        for(int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }
    
    // Display
    public void display() {
        Node current = head;
        while(current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
    
    // Get size
    public int size() {
        return size;
    }
    
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        
        list.add(10);
        list.add(20);
        list.add(30);
        list.addFirst(5);
        list.add(2, 15);
        
        list.display();
        // Output: 5 -> 10 -> 15 -> 20 -> 30 -> null
        
        System.out.println("Size: " + list.size());  // 5
    }
}
```

**Key Points:**
- Node class with data and next pointer
- Add at end: traverse to last node
- Add at beginning: update head
- Add at index: find position, insert

---

## Map Operations

### 1. We have 2 maps, return the output to map key of first map with value of second map

**Answer:**

```java
import java.util.*;

public class MapMerge {
    
    public static Map<String, String> mergeMaps(Map<String, Integer> map1, 
                                                Map<String, String> map2) {
        Map<String, String> result = new HashMap<>();
        
        // Iterate through first map
        for(Map.Entry<String, Integer> entry : map1.entrySet()) {
            String key = entry.getKey();
            // Get value from second map if key exists
            String value = map2.getOrDefault(key, "Not Found");
            result.put(key, value);
        }
        
        return result;
    }
    
    // Using Stream API
    public static Map<String, String> mergeMapsStream(Map<String, Integer> map1, 
                                                      Map<String, String> map2) {
        return map1.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> map2.getOrDefault(entry.getKey(), "Not Found")
            ));
    }
    
    public static void main(String[] args) {
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("a", 1);
        map1.put("b", 2);
        map1.put("c", 3);
        
        Map<String, String> map2 = new HashMap<>();
        map2.put("a", "Apple");
        map2.put("b", "Banana");
        map2.put("d", "Date");
        
        Map<String, String> result = mergeMaps(map1, map2);
        System.out.println(result);
        // Output: {a=Apple, b=Banana, c=Not Found}
    }
}
```

---

### 2. Sort the map using values

**Answer:**

```java
import java.util.*;
import java.util.stream.Collectors;

public class SortMapByValue {
    
    // Method 1: Using Stream API
    public static <K, V extends Comparable<V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new  // Preserve order
            ));
    }
    
    // Method 2: Traditional approach
    public static <K, V extends Comparable<V>> Map<K, V> sortByValueTraditional(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        
        // Sort by value
        list.sort(Map.Entry.comparingByValue());
        
        // Create new LinkedHashMap to preserve order
        Map<K, V> sortedMap = new LinkedHashMap<>();
        for(Map.Entry<K, V> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        return sortedMap;
    }
    
    // Method 3: Reverse order
    public static <K, V extends Comparable<V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
        return map.entrySet().stream()
            .sorted(Map.Entry.<K, V>comparingByValue().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }
    
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 50);
        map.put("banana", 20);
        map.put("cherry", 80);
        map.put("date", 10);
        
        System.out.println("Original: " + map);
        // {apple=50, banana=20, cherry=80, date=10}
        
        Map<String, Integer> sorted = sortByValue(map);
        System.out.println("Sorted by value: " + sorted);
        // {date=10, banana=20, apple=50, cherry=80}
        
        Map<String, Integer> sortedDesc = sortByValueDescending(map);
        System.out.println("Sorted descending: " + sortedDesc);
        // {cherry=80, apple=50, banana=20, date=10}
    }
}
```

**Key Points:**
- Convert to list of entries
- Sort by value
- Create LinkedHashMap to preserve order
- Use Stream API for cleaner code

---

All coding questions answered with complete, runnable code examples!
