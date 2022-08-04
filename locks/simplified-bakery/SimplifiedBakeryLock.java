import java.util.concurrent.atomic.AtomicInteger;

public class SimplifiedBakeryLock implements LockInterface {

    private AtomicInteger ticket = new AtomicInteger(0);

    private int numberOfThreads;

    volatile private int[] threadTickets;

    public SimplifiedBakeryLock(int numberOfThreads) {
        this.threadTickets = new int[numberOfThreads];
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public void lock() {
        int id = Integer.parseInt(Thread.currentThread().getName());
        this.threadTickets[id] = this.ticket.incrementAndGet();
        for (int i=0; i < this.numberOfThreads; i++) {
            while ((this.threadTickets[i] != 0) && (this.threadTickets[i] < this.threadTickets[id]));
        }
    }

    @Override
    public void unlock() {
        int id = Integer.parseInt(Thread.currentThread().getName());
        this.threadTickets[id] = 0;
    }
}
