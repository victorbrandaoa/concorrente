import java.util.concurrent.Semaphore;

public class Lightswitch {

    private int count = 0;

    private Semaphore mutex = new Semaphore(1);

    public void lock(Semaphore s) throws InterruptedException {
        this.mutex.acquire();
        this.count++;

        if (this.count == 1) {
            s.acquire();
        }
        this.mutex.release();
    }

    public void unlock(Semaphore s) throws InterruptedException {
        this.mutex.acquire();
        this.count--;

        if (this.count == 0) {
            s.release();
        }
        this.mutex.release();
    }
}
