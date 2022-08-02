import java.util.concurrent.Semaphore;

public class MutexLock implements LockInterface {

    private Semaphore mutex = new Semaphore(1);

    @Override
    public void lock() {
        try {
            this.mutex.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unlock() {
        this.mutex.release();
    }
}
