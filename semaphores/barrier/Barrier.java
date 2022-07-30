import java.util.concurrent.Semaphore;

public class Barrier {

    private Semaphore mutex = new Semaphore(1);

    private Semaphore barrier = new Semaphore(0);

    private int count = 0;

    private int numberOfThreads;

    public Barrier(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public void await() throws InterruptedException {
        this.mutex.acquire();
        this.count++;
        this.mutex.release();
        if (this.count == this.numberOfThreads) {
            this.barrier.release();
        }
        this.barrier.acquire();
        this.barrier.release();
    }
}
