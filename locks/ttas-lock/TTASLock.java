import java.util.concurrent.atomic.AtomicBoolean;

public class TTASLock {
    AtomicBoolean state = new AtomicBoolean(false);

    public void lock() {
        while (true) {
            while (this.state.get());
            if (!this.state.getAndSet(true)) {
                return;
            }
        }
    }

    public void unlock() {
        this.state.set(false);
    }
}
