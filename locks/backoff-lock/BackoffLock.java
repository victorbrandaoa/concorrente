import java.util.concurrent.atomic.AtomicBoolean;

public class BackoffLock {

    private AtomicBoolean state = new AtomicBoolean(false);

    private static int MIN_DELAY = 1;

    private static int MAX_DELAY = 2;

    public void lock() throws InterruptedException {
        Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);
        while (true) {
            while (this.state.get());
            if (!this.state.getAndSet(true)) {
                return;
            } else {
                backoff.backoff();
            }
        }
    }

    public void unlock() {
        this.state.set(false);
    }
}
