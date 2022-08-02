public class LockFactory {

    public static LockInterface createLock(int pickedLock) {
        LockInterface lock;

        switch (pickedLock) {
            case 1:
                lock = new MutexLock();
                break;
            case 2:
                lock = new TASLock();
                break;
            case 3:
                lock = new TTASLock();
                break;
            case 4:
                lock = new BackoffLock();
                break;
            default:
                String msg = String.format(
                        "There is no lock implementation for id %d",
                        pickedLock
                );
                throw new IllegalArgumentException(msg);
        }

        return lock;
    }
}
