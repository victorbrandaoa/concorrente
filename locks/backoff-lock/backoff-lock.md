### TTAS with backoff

In the directory `locks/backoff-lock/` there is the TTAS lock algorithm, but now it uses an exponential backoff to minimize the high contention problem when the thread that holds the lock release it.
