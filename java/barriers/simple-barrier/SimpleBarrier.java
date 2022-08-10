import java.util.concurrent.Semaphore;

public class SimpleBarrier implements Barrier {

    private Semaphore mutex = new Semaphore(1);

    private Semaphore turnstile = new Semaphore(0);

    private int count = 0;

    private int numberOfThreads;

    public SimpleBarrier(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public void await() throws InterruptedException {
        this.mutex.acquire(); // mutex.down()
        this.count++; // update the count of threads waiting for the barrier to fall
        this.mutex.release(); // mutex.up()
        if (this.count == this.numberOfThreads) {
            // if all the threads are waiting it means that they rendezvous, then the barrier can fall
            this.turnstile.release(); // turnstile.up() - start the process of releasing the threads that are waiting
        }
        this.turnstile.acquire(); // turnstile.down() - block the threads until the barrier starts to fall
        this.turnstile.release(); // turnstile.up() - continue the process of releasing the threads that are waiting
    }
}
