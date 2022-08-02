import java.util.concurrent.atomic.AtomicBoolean;

public class TASLock implements LockInterface {
    AtomicBoolean state = new AtomicBoolean(false);

    @Override
    public void lock() {
        while (this.state.getAndSet(true));
    }

    @Override
    public void unlock() {
        this.state.set(false);
    }
}
