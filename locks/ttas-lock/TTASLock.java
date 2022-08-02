import java.util.concurrent.atomic.AtomicBoolean;

public class TTASLock implements LockInterface {
    AtomicBoolean state = new AtomicBoolean(false);

    @Override
    public void lock() {
        while (true) {
            while (this.state.get());
            if (!this.state.getAndSet(true)) {
                return;
            }
        }
    }

    @Override
    public void unlock() {
        this.state.set(false);
    }
}
