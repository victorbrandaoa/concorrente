public class ConcurrentCode implements Runnable {

    private Counter count;
    private LockInterface lock;

    public ConcurrentCode(Counter count, LockInterface lock) {
        this.count = count;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            this.lock.lock(); // mutex.down()
            this.count.increment(); // Update the value of count
            String countValueMsg = String.format(
                    "The thread %s has updated the count value to %d",
                    Thread.currentThread().getName(),
                    this.count.getCount()
            );
            System.out.println(countValueMsg);
            this.lock.unlock(); // mutex.up()
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
