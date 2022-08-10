### TTAS Lock (Test And Test And Set Lock)

In the directory `locks/ttas-lock/` there is a lock algorithm that instead of performing the `testAndSet` directly, the thread repeatedly reads the lock until it appears to be free. Only after the lock appears to be free does the thread apply `testAndSet`. This technique is called `test-and-test-and-set-lock` and guarantees deadlock-free mutual exclusion.
