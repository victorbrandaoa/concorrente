### locks

In mutual exclusion problems, when you can't acquire the lock there are two alternatives.

- If you keep trying, the lock is called a `spin lock`, and repeatedly testing the lock is called `spinning`, or `busy-waiting`. Spinning is sensible when you expect the lock delay to be short.
- The alternative is to suspend yourself and ask the operating system's scheduler to schedule another thread on your processor, which is sometimes called `blocking`. Because switching from one thread to another is expensive, blocking makes sense only if you expect the lock delay to be long.

### Welcome to real world

When programming our multiprocessor, we naturally assumed that read-write operations are atomic, that is, they are linearizable to some sequential execution, or at very least, that they are sequentially consistent.

Sequential consistency implies that there is some global order on all operations in which each thread's operations take effect as ordered by its program.  We relied on the assumption that memory is sequentially consistent when proving the Peterson lock correct.

Our proof that the Peterson lock provided mutual exclusion implicitly relied on the assumption that any two memory accesses by the same thread, even to separate variables, take effect in program order. Unfortunately, modern multiprocessors typically do not provide sequentially consistent memory, nor do they necessarily guarantee program order among read-writes by a given thread.

### Why not?

- There are compilers that reorder instructions to enhance performance. Most programming languages preserve program order for each individual variable, but not across multiple variables;
- Writes to multiprocessor memory do not necessarily take effect when they are issued, because in most programs the vast majority of writes do not need to take effect in shared memory right away. Thus, on many multiprocessor architectures, writes to shared memory are buffered in a special `write buffer`, to be written to memory only when needed.

To prevent the reordering of operations resulting from write buffering, modern architectures provide a special `memory barrier` instruction that forces outstanding operations to take effect. It is programmer's responsibility to know where to insert a memory barrier, because they are expensive, and we want to minimize their use. In Java one way to use a memory barrier is by using the keyword `volatile`.
