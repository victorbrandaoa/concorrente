import java.util.concurrent.atomic.AtomicBoolean;

public class TASLock {
    AtomicBoolean state = new AtomicBoolean(false);

    public void lock() {
        while (this.state.getAndSet(true));
    }

    public void unlock() {
        this.state.set(false);
    }
}
