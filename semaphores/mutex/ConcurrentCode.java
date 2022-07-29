import java.util.concurrent.Semaphore;

public class ConcurrentCode implements Runnable {
    private Counter count;
    volatile private Semaphore mutex;

    public ConcurrentCode(Counter count, Semaphore mutex) {
        this.count = count;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        try {
            this.mutex.acquire(); // mutex.down()
            this.count.increment(); // Update the value of count
            String countValueMsg = String.format(
                    "The thread %s has updated the count value to %d",
                    Thread.currentThread().getName(),
                    this.count.getCount()
            );
            System.out.println(countValueMsg);
            this.mutex.release(); // mutex.up()
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
