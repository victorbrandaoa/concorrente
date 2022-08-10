import java.util.concurrent.Semaphore;

public class ReusableBarrier implements Barrier {

    private Semaphore mutex = new Semaphore(1);

    private Semaphore turnstile = new Semaphore(0);

    private Semaphore turnstile2 = new Semaphore(1);

    private int count = 0;

    private int numberOfThreads;

    public ReusableBarrier(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public void await() throws InterruptedException {
        this.mutex.acquire(); // mutex.down()
        this.count++; // increment the count of threads waiting for the barrier to fall
        if (this.count == this.numberOfThreads) {
            // if all the threads are waiting it means that they rendezvous, then the barrier can fall
            this.turnstile2.acquire(); // turnstile2.down() - start the process of blocking the threads until the count get back to 0
            this.turnstile.release(); // turnstile.up() - start the process of releasing the threads that are waiting
        }
        this.mutex.release(); // mutex.up()

        this.turnstile.acquire(); // turnstile.down() - block the threads until the barrier starts to fall
        this.turnstile.release(); // turnstile.up() - continue the process of releasing the threads that are waiting

        this.mutex.acquire(); // mutex.down()
        this.count--; // decrement the count of threads waiting for the barrier to fall
        if (this.count == 0) {
            // if the count is 0 it means that the count reset
            this.turnstile.acquire(); // turnstile.down() - turns the final value of the turnstile back to 0 after the barrier falls
            this.turnstile2.release(); // turnstile2.up() - start the process of releasing the threads that are waiting the count get back to 0
        }
        this.mutex.release(); // mutex.up()

        this.turnstile2.acquire(); // turnstile2.down() - block the threads until the count get back to 0
        this.turnstile2.release(); // turnstile2.up() - continue the process of releasing the threads that are waiting the count get back to 0
    }
}
