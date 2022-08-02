import java.util.Random;

public class Backoff {
    private int minDelay;

    private int maxDelay;

    private int limit;

    private Random random;

    public Backoff(int minDelay, int maxDelay) {
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.limit = minDelay;
        this.random = new Random();
    }

    public void backoff() throws InterruptedException {
        int delay = this.random.nextInt(this.limit);
        this.limit = Math.min(this.maxDelay, 2 * this.limit);
        Thread.sleep(delay);
    }
}
