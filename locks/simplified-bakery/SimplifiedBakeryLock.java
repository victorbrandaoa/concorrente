import java.util.concurrent.atomic.AtomicInteger;

public class SimplifiedBakeryLock {

    private AtomicInteger ticket = new AtomicInteger(0);

    private int numberOfThreads;

    volatile private int[] threadTickets;

    public SimplifiedBakeryLock(int numberOfThreads) {
        this.threadTickets = new int[numberOfThreads];
        this.numberOfThreads = numberOfThreads;
    }

    public void lock(int id) {
        this.threadTickets[id] = this.ticket.incrementAndGet();
        for (int i=0; i < this.numberOfThreads; i++) {
            while (
                    (this.threadTickets[i] != 0) && (this.threadTickets[i] < this.threadTickets[id]));
        }
    }

    public void unlock(int id) {
        this.threadTickets[id] = 0;
    }
}
