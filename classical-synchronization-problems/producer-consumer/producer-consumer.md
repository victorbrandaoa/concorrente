### Producer-Consumer Problem

A classical synchronization problem is the Producer-Consumer problem, some threads are producers and some threads are consumers. The producers create items of some kind and add them to a data structure and the consumers remove the items and process them.

In our example we have a buffer where the producers add integer values and the consumers get them. We have pointers to the indexes where the producer must add the produced value and where the consumer must get the value to process it.

There are some constraints that we have to enforce:

- While an item is being added to or removed from the buffer, the buffer is in an inconsistent state. Therefore, threads must have exclusive access to the buffer;
- If a consumer thread arrives while the buffer is empty, it blocks until a producer adds a new item;
- The buffer has a limited size, if a producer thread arrives while the buffer is full, it blocks until a consumer removes an item.

In the directory `classical-synchronization-problems/producer-consumer/` there is a code that create producers and consumers threads that work over a shared buffer. We have two solutions for this problem, one using semaphores and another using monitors.
