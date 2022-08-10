### Reusable Barrier

Often a set of cooperating threads will perform a series of steps in a loop and synchronize at a barrier after each step. For this application we need a reusable barrier that locks itself after all the threads have passed through.

A simple implementation of barrier can't do that, to do so all the variables in our barrier should have the same values that we used to initialize the barrier after the barrier falls. When the barrier falls we have the following values:

```
Barrier:
    count = n // we initialized with 0
    mutex = 1
    barrier = 1 // we initialized with 0
```

We want to guarantee that functions execute in the following order just like we wanted with the barrier solution, but now we want to guarantee that our barrier be able to be used in every iteration of the loop.

```
for every thread:
    for 0..n:
        a1 -> b2
        b1 -> a2

// notation
// x -> y means that x happens before y
```

This solution is sometimes called a `two-phase barrier` because it forces all the threads to wait twice: one for all the threads to rendezvous and again for all the threads to reset the values of the barrier's variables back to their original values.
