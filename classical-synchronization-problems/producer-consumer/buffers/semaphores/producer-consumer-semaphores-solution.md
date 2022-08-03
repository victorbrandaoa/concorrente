### Producer-Consumer Problem With Semaphores

In our solution, we have only one mutex to protect the buffer's update, but the variables of the indexes where the producers must add and where the consumers must get are different. By using only one mutex we block consumers while producers are working, but since the indexes variables are different, there is no need to do so. We also block the producers while the consumers are working, but if the buffer is not full, we shouldn't do so.

You can optimize the code by creating different mutexes for the producers and consumers.
