### Mutex

A common use for semaphores is to enforce mutual exclusion (only one thread at time can run some part of the code). We can use mutual exclusion to control the access to shared variables. We call mutex a semaphore used to guarantee mutual exclusion.

A mutex is a semaphore initialized with the value 1.

In the directory `semaphores/mutex/` there is a concurrent code that updates a counter, and it uses a mutex to control the access to the counter variable. You can comment the lines of the `acquire` and `release` to run the code and see the concurrency problem with the counter if you hadn't used the mutex.
