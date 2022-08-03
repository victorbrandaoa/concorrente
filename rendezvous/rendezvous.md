### Rendezvous

In the rendezvous problem we have two threads and the Thread A has to wait for Thread B and vice versa. The idea is that two threads rendezvous at a point of execution, and neither is allowed to proceed until both have arrived. In other words, given this code:

```
Thread A             Thread B

statement a1         statement b1
statement a2         statement b2
```

We want to guarantee that a1 happens before b2 and b1 happens before a2.

In the directory `semaphores/rendezvous` there is a code where the ThreadA executes the functions `a1` and `a2` and the ThreadB executes the functions `b1` and `b2`. In this code we used the rendezvous solution to guarantee the following order in the execution of the functions:

```
a1 -> b2
b1 -> a2

// notation
// x -> y means that x happens before y
```
