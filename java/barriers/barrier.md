### Barrier

A limitation of the rendezvous solution is that it does not work with more than two threads. We can generalize the rendezvous solution for N threads, this generalized solution is called Barrier. Given this code:

```
rendezvous
critical point
```

The idea is that no thread executes the `critical point` until after all threads have executed `rendezvous`.

In the directory `barriers` there is a code where every instance of ThreadACode execute the functions `a1` and `a2` and every instance of ThreadBCode execute the functions `b1` and `b2`. We want to guarantee that functions execute in the following order just like we wanted with the rendezvous solution, but now we want to guarantee that for N threads.

```
for every thread:
    a1 -> b2
    b1 -> a2

// notation
// x -> y means that x happens before y
```

In order to do that we have two barrier implementations, the simple barrier in the directory `barriers/simple-barrier` and the reusable barrier in the directory `barriers/reusable-barrier`.
