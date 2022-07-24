## Semaphores

### Definition

Semaphores are initialized with an integer value, but after the initialization, you are only allowed to perform two operations over the value: increment (by one) and decrement (by one). You can't read the current value of the semaphore.

### Operations

#### down

When you call the `down` method (also called `wait` or `acquire`), the thread that called the method decrements the semaphore, if the result is negative, the thread blocks itself until another thread increments the semaphore.

#### up

When you call the `up` method (also called `signal` or `release`), the thread that called the method increments the semaphore, if there are other threads waiting, one of the waiting thread gets unblocked.

### Consequences

* In general, there is no way to know whether a thread will block before it decrements the semaphore;

* After a thread increments the semaphore and another thread gets woken up, both threads continue running concurrently. There is no way to know which thread, if either, will continue immediately;

* When you signal a semaphore, you don't know necessarily wheter another thread is waiting, so the number of unblocked threads may be zero or one.

* If the value of the semaphore is positive, then it represents the number of threads that can decrement without blocking. If it is negative, then it represents the number of threads that have blocked and are waiting. If the value is zero, it means there are no threads waiting, but if a thread tries to decrement, it will block.
