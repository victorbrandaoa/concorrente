### Reusable Barrier

Often a set of cooperating threads will perform a series of steps in a loop and synchronize ata a barrier after each step. For this application we need a reusable barrier that locks itself after all the threads have passed through.

Our implementation of barrier can't do that, to do so all the variables in our barrier should have the same values that we used to initialize the barrier after the barrier falls. When the barrier falls we have the following values:

```
Barrier:
    count = n // we initialized with 0
    mutex = 1
    barrier = 1 // we initialized with 0
```

In the directory `semaphores/reusable-barrier` there is a code where every instance of ThreadALoopCode execute the functions `a1` and `a2` inside a loop and every instance of ThreadBLoopCode execute the functions `b1` and `b2` inside a loop. We want to guarantee that functions execute in the following order just like we wanted with the barrier solution, but now we want to guarantee that our barrier be able to be used in every iteration of the loop.

```
for every thread:
    for 0..n:
        a1 -> b2
        b1 -> a2

// notation
// x -> y means that x happens before y
```
