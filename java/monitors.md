### Java Monitors

Locks have been in existence to implement multithreading much before the monitors have come to usage. With further advancements, the use of monitors started as a mechanism to handle access and coordinate threads which proved to be more efficient, error-free, and compatible with the OOP (Object-Oriented Programming) paradigm.

Monitor in Java Concurrency is a synchronization mechanism that provides the fundamental requirements of multithreading: mutual exclusion between threads and cooperation among threads working at common tasks. Monitors basically 'monitor' the access control of shared resources and object among threads.

### synchronized

Java provides a way of creating threads and synchronizing their tasks using synchronized blocks. Synchronized blocks in Java are marked with the `synchronized` keyword. A synchronized block in Java is synchronized on some object. All synchronized blocks synchronize on the same object can only have one thread executing inside them at a time. All others threads attempting to enter the synchronized block are blocked until the thread inside the block exits.

Examples:

```java
public class XPTO {

    public synchronized void put(int i) {
        // put code
    }
    
    public synchronized int get() {
        // get code
    }
}
```

In the above code, all the methods of the class XPTO that have the `synchronized` keyword on their definition became part of the same critical section. In other words, if any thread calls the `put` method, no other thread can call neither the `put` nor the `get` until the first thread finishing the execution of the `put`. The same is valid if a thread calling the `get` method.

```java
public class XPTO {
    
    public void put(int i) {
        // put code
        synchronized (this) {
            // synchronized code
        }
        // put code
    }

    public int get() {
        // get code
        synchronized (this) {
            // synchronized code
        }
        // get code
    }
}
```

In the above code, we have another way to use the `synchronized` keyword. If the methods `put` and `get` have only a few line that need to be synchronized, there is no need to synchronize the entire methods body. Using the `synhcronized` keyword like above, only the lines inside the synchronized block will be synchronized.

As you can see, we need to pass an object to the synchronized block, in this example we passed `this`. You can pass any object you want, the object will work as a `guard`, but two distinct synchronized blocks only became part of the same critical section if the same object has been used on the both blocks.

### volatile

In Java, each thread has its own copy of the variables, so it's possible that the changes made by one thread do not reflect on the other threads. To solve this visibility problem we can use the `volatile` keyword, by using it, the variable can be used by multiple threads.

```java
public class XPTO {
    
    private volatile int count = 0;
    
}
```

The `synchronized` keyword guarantees visibility, so if you use it, you don't have to use the `volatile` keyword. But the `volatile` keyword does not guarantee mutual exclusion, so if you only need visibility you should use the `volatile`, because the `synchronized` can cause scalability problems in a case where you don't need mutual exclusion.

### Atomic Variables

In multithreading, the shared variables leads to concurrency problems, because the changes may result in the program's inconsistency. Use an `atomic variable` can be one of the alternatives in such a scenario.

Update a variable in a multithreading environment can lead to inconsistent results because updating a variable is done in three steps: reading, updating and writing. If two or more threads try to update the value at the same time, then it may not update properly.

The problem of updating a variable can be solved by using a lock or synchronization, but it compromises time efficiency or performance. First, it mandates resource and thread scheduler to control lock. Second, when multiple threads attempt to acquire a lock, only one of them wins, rest are suspended or blocked. Suspending or blocking of threads can have a huge impact on performance.

Java provides `atomic classes` such as `AtomicInteger`, `AtomicLong`, `AtomicBoolean` and `AtomicReference`. Objects of these classes represent the atomic variable of `int`, `long`, `boolean`, and `object reference` respectively. Any operation applied over these variables is atomic, in other words, it's executed in only one step.

```java
public class Main {
    public static void main(String[] args) {
        AtomicInteger count = new AtomicInteger(0);
        int v = count.incrementAndGet(); // executed in one step
    }
}
```

### Methods

The following methods must be used inside a synchronized block.

#### wait

When the `wait` method is called, the calling thread stops its execution until `notify` or `notifyAll` method is invoked by some other thread.

#### notify

When the `notify` method is called, one of the threads waiting for an object is waked up.

#### notifyAll

When the `notifyAll` method is called, all the threads waiting for an object are waked up.

### Summary
#### Monitors
In Java, every object inherits from the standard Object class a monitor lock. The simplest way to use this lock from the point of view that it was implemented by default is using the synchronized mark.

A monitor allows mutual exclusion and monitoring to take place without busy waiting. This improvement is quite significant, considering that busy waiting has a wide range of energy and processing costs (busy waiting burns CPU!).

In a monitor implementation, the thread that fails to reach the critical region may temporarily lose the CPU and “sleep”, so that it can be woken up when the current thread in the critical region exits. In Java, there are three notable implementation frameworks for performing synchronization using monitors:

→ wait() : starts waiting (lock!)

→notify() : notifies a thread that the critical region is free (wake up the thread!)

→notifyAll() : notifies all threads that the critical region has been freed.

It is important to remember that every instance has its lock, but also that the associated class has another one. Misuse and lack of knowledge of these two structures can cause contention.

Another notable property of the monitors is that their lock is reentrant, that is, the lock has an “owner” who can enter the critical region several times whenever he wants. This type of structure is very useful for locks that occur in recursive structures, considering that if there is no recess in these situations, a deadlock will occur.

#### LOCKS vs MONITORS

#### LOCKS

Locks allow the definition of smaller critical regions, and, given the lower abstraction of their implementation, they favor more advanced features, such as timeout, justice, interruptions, among other aspects

#### MONITORS

As monitors are implementations of programming languages, their level of abstraction is also higher. In view of this, they are less configurable and may favor misuse, but there is ease of use with the automatic unlocking of critical regions and implementation without the use of busy waiting.

### References

[Lock vs. Monitor](https://www.geeksforgeeks.org/difference-between-lock-and-monitor-in-java-concurrency/)

[Monitors](https://www.geeksforgeeks.org/monitors-in-process-synchronization/)

[Synchronization in Java](https://www.geeksforgeeks.org/synchronization-in-java/)

[Volatile](https://www.geeksforgeeks.org/volatile-keyword-in-java/?ref=gcse)

[Atomic Variables](https://www.geeksforgeeks.org/atomic-variables-in-java-with-examples/?ref=gcse)

[Atomic vs. Synchronized vs. Volatile](https://www.geeksforgeeks.org/difference-between-atomic-volatile-and-synchronized-in-java/)
