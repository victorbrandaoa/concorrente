### locks

In mutual exclusion problems, when you can't acquire the lock there are two alternatives.

- If you keep trying, the lock is called a `spin lock`, and repeatedly testing the lock is called `spinning`, or `busy-waiting`. Spinning is sensible when you expect the lock delay to be short.
- The alternative is to suspend yourself and ask the operating system's scheduler to schedule another thread on your processor, which is sometimes called `blocking`. Because switching from one thread to another is expensive, blocking makes sense only if you expect the lock delay to be long.

### Welcome to real world

When programming our multiprocessor, we naturally assumed that read-write operations are atomic, that it, they are linearizable to some sequential execution, or at very least, that they are sequentially consistent.

Sequential consistency implies that there is some global order on all operations in which each thread's operations take effect as ordered by its program.  We relied on the assumption that memory is sequentially consistent when proving the Peterson lock correct.
