import java.util.concurrent.atomic.AtomicBoolean;

public class BackoffLock implements LockInterface {

    private AtomicBoolean state = new AtomicBoolean(false);

    private static int MIN_DELAY = 1;

    private static int MAX_DELAY = 2;

    @Override
    public void lock() {
        Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);
        while (true) {
            while (this.state.get());
            if (!this.state.getAndSet(true)) {
                return;
            } else {
                try {
                    backoff.backoff();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void unlock() {
        this.state.set(false);
    }
}
