### Dining Philosophers

In the dining philosophers problem we have a round table with 5 plates, 5 forks and a big bowl of spaghetti. 5 philosophers, who represent interacting threads, come to the table and execute the following loop:

```python
while True:
    think();
    getForks();
    eat();
    putForks();
```

The forks represent resources that the threads have to hold exclusively in order to make progress. The thing that makes the problem interesting, is that the philosophers need two forks to eat, so a hungry philosopher might have to wait for a neighbor to put down a fork. Assuming that the philosophers know how to `think` and `eat`, our job is to write a version of `getForks` and `putForks` that satisfies the following constraints:

- Only one philosopher can hold a fork at a time;
- It must be impossible for a deadlock to occur;
- It must be impossible for a philosopher to starve waiting for a fork;
- It must be possible for more than one philosopher to eat at the same time;

We make no assumption about how long `eat` and `think` take, except that `eat` has to terminate eventually.

#### Solution

In the directory `semaphores/dining-philosophers/` there is a code that solves the dining philosophers problem. We represent the forks as a list of mutexes and each philosopher is represented with an index (0 to 4). For each philosopher, its left fork's index is its own index and the right fork's index is ((its own index + 1) % n) where n is the number of philosophers. 

#### Possible solution

```python
def getForks:
    this.forks[this.right].down()
    this.forks[this.left].down()
    
def putForks:
    this.forks[this.right].up()
    this.forks[this.left].up()
```

The above solution has a deadlock, because each philosopher can pick up a fork and then wait forever for the other fork. We can solve that by controlling the number of philosophers on the table and guarantee that we have only 4 philosophers on the table.

#### Solution

Since we guarantee that we have 4 philosophers on the table, there is a fork left on the table, and that fork has two neighbors, each of which is holding another fork. Therefore, either of these neighbors can pick up the remaining fork and eat. We control that using a semaphore initialized with 4.
