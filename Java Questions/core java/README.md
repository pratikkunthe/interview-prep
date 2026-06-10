# Core Java Interview Questions & Answers

This folder contains comprehensive answers to 240+ core Java interview questions, organized by topic.

## 📚 Table of Contents

### [1. Java Fundamentals](./01-java-fundamentals.md)
**Questions 1-51**
- Static blocks and initializers
- Constructors and method overloading/overriding
- super vs this keywords
- Abstract classes vs interfaces
- Platform independence and JVM
- Access modifiers and coding standards
- IS-A and HAS-A relationships
- Final keyword and packages

### [2. Exception Handling](./02-exception-handling.md)
**Questions 52-81**
- What are exceptions and errors?
- try-catch-finally blocks
- Checked vs unchecked exceptions
- throw vs throws keywords
- Custom exceptions
- Exception handling best practices
- ClassNotFoundException vs NoClassDefFoundError

### [3. Multithreading](./03-multithreading.md)
**Questions 83-138**
- Process vs Thread
- Creating threads (Thread class vs Runnable)
- Thread lifecycle and states
- Synchronization methods and blocks
- wait(), notify(), sleep(), join(), yield()
- Thread priorities and scheduling
- Daemon threads
- Thread groups and ThreadLocal

### [4. Nested Classes & Inner Classes](./04-nested-inner-classes.md)
**Questions 139-169**
- Static nested classes
- Member inner classes
- Local inner classes
- Anonymous inner classes
- When to use each type
- Encapsulation techniques
- Reference variables
- Wrapper classes and type conversion

### [5. Additional Core Concepts](./05-additional-concepts.md)
**Questions 170-197**
- Import statements and packages
- Classpath and JAR files
- Variable scopes and lifetimes
- Static imports
- Interfaces and enums
- Field hiding and varargs
- Memory management
- OOP concepts (encapsulation, inheritance, polymorphism)

### [6. Collections Framework](./06-collections-framework.md)
**Questions 198-231**
- Collection vs Collections
- List interface (ArrayList, LinkedList, Vector)
- Set interface (HashSet, TreeSet, LinkedHashSet)
- Map interface (HashMap, TreeMap, LinkedHashMap)
- Iterator vs ListIterator
- Comparator vs Comparable
- Thread-safe collections (ConcurrentHashMap, CopyOnWriteArrayList)
- Fail-fast vs fail-safe iterators

### [7. Serialization](./07-serialization.md)
**Questions 232-241**
- What is serialization?
- Serializable interface
- serialVersionUID importance
- transient keyword
- Static variable serialization
- Object graph serialization
- Alternatives to Java serialization

---

## 🎯 How to Use This Guide

### For Interview Preparation:
1. **Start with fundamentals** - Make sure you understand questions 1-51
2. **Practice coding** - Type out the examples, don't just read
3. **Focus on differences** - Many questions ask about comparisons
4. **Draw diagrams** - Visualize concepts like thread lifecycle, collection hierarchy

### For Quick Review:
- Each file is standalone - jump to the topic you need
- Questions are numbered for easy reference
- Code examples are simple and runnable
- Diagrams included where helpful

### Study Tips:
- ✅ Read the question first, try to answer, then check
- ✅ Run the code examples to see output
- ✅ Modify examples to test your understanding
- ✅ Create your own examples for practice

---

## 📖 Answer Format

Each answer includes:
- **Simple explanation** - No jargon, easy to understand
- **Code examples** - Working Java code you can run
- **Diagrams** - Visual representations where helpful
- **Key points** - Bullet points for quick review
- **Comparisons** - Tables comparing related concepts

---

## 🔑 Key Concepts Summary

### Must-Know Topics:
1. **OOP Principles** - Encapsulation, Inheritance, Polymorphism, Abstraction
2. **Exception Handling** - try-catch-finally, checked vs unchecked
3. **Collections** - When to use ArrayList vs LinkedList vs HashSet
4. **Multithreading** - Thread creation, synchronization, thread safety
5. **Interfaces** - Abstract classes vs interfaces
6. **Memory** - Stack vs Heap, garbage collection

### Common Patterns:
- **Difference questions** - Compare two similar concepts
- **When to use** - Explain use cases for different approaches
- **Code examples** - Be ready to write code on whiteboard/computer

---

## 💡 Interview Tips

### Answer Structure:
1. **Start simple** - Give a one-line definition
2. **Provide example** - Show with code
3. **Elaborate** - Explain details if interviewer wants more
4. **Real-world usage** - Mention practical applications

### Example:
**Q: What is an ArrayList?**

**Answer:**
"ArrayList is a resizable array implementation of the List interface. 

*(Show code)*
```java
ArrayList<String> list = new ArrayList<>();
list.add("Apple");
String fruit = list.get(0);
```

It provides fast random access (O(1)) but slower insertion/deletion in the middle (O(n)). It's best used when you need frequent access by index and don't know the size in advance.

In real-world applications, I've used ArrayList for maintaining lists of items where we primarily need to iterate or access by index, like displaying products in an e-commerce application."

---

## 🎓 Difficulty Levels

- **Beginner** (★☆☆) - Questions 1-50 (Fundamentals)
- **Intermediate** (★★☆) - Questions 51-150 (Exceptions, Basic Threading, Collections)
- **Advanced** (★★★) - Questions 151-241 (Advanced Threading, Inner Classes, Deep Concepts)

---

## 📝 Practice Exercises

After studying each section:
1. Try to explain concepts without looking at answers
2. Write code from memory
3. Solve LeetCode problems related to the topic
4. Create your own examples

---

## 🔗 Related Topics

Want to learn more? Check these related topics:
- **Java 8+ Features** - Streams, Lambdas, Optional
- **Spring Framework** - Dependency Injection, Spring Boot
- **Design Patterns** - Singleton, Factory, Builder
- **JVM Internals** - Memory management, Garbage collection
- **Concurrency** - ExecutorService, CompletableFuture

---

## 📊 Quick Reference Tables

### Access Modifiers
| Modifier | Class | Package | Subclass | World |
|----------|-------|---------|----------|-------|
| public | ✓ | ✓ | ✓ | ✓ |
| protected | ✓ | ✓ | ✓ | ✗ |
| default | ✓ | ✓ | ✗ | ✗ |
| private | ✓ | ✗ | ✗ | ✗ |

### Collection Comparison
| Collection | Ordered | Duplicates | Thread-Safe | Null |
|-----------|---------|------------|-------------|------|
| ArrayList | Yes | Yes | No | Yes |
| LinkedList | Yes | Yes | No | Yes |
| HashSet | No | No | No | One |
| TreeSet | Sorted | No | No | No |
| HashMap | No | Values only | No | One key |

### Thread Methods
| Method | Releases Lock? | Use Case |
|--------|---------------|----------|
| wait() | ✓ Yes | Wait for condition |
| sleep() | ✗ No | Pause execution |
| join() | ✗ No | Wait for thread to complete |
| yield() | ✗ No | Give chance to other threads |

---

## 🎯 Final Tips

1. **Understand, don't memorize** - Focus on concepts, not exact wording
2. **Practice regularly** - Code every day, even if just 30 minutes
3. **Build projects** - Apply multiple concepts together
4. **Review mistakes** - Learn from what you get wrong
5. **Stay calm** - In interviews, it's okay to think before answering

---

## 📞 Need Help?

- Stuck on a concept? Re-read with the code examples
- Want more practice? Try coding the examples from scratch
- Need clarification? Most questions build on previous ones

---

**Good luck with your Java interviews! Remember: confidence comes from preparation.** 💪

---

*Last updated: January 2026*
