import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable {

    volatile private Semaphore[] forks;

    private Semaphore footman;

    private int philosopherId;

    public Philosopher(int philosopherId, Semaphore[] forks, Semaphore footman) {
        this.philosopherId = philosopherId;
        this.forks = forks;
        this.footman = footman;
    }

    @Override
    public void run() {
        try {
            while (true) {
                this.think();
                this.getForks();
                this.eat();
                this.putForks();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void think() {
        long sleepTime = (long) (Math.random() * 1000);
        String msg = String.format(
                "The philosopher %d will think for %d milliseconds",
                this.philosopherId,
                sleepTime
        );
        System.out.println(msg);
    }

    private void eat() {
        long sleepTime = (long) (Math.random() * 1000);
        String msg = String.format(
                "The philosopher %d will eat for %d milliseconds",
                this.philosopherId,
                sleepTime
        );
        System.out.println(msg);
    }

    private int left() {
        return this.philosopherId;
    }

    private int right() {
        return (this.philosopherId + 1) % 5;
    }

    private void getForks() throws InterruptedException {
        this.forks[this.right()].acquire();
        this.forks[this.left()].acquire();
    }

    private void putForks() {
        this.forks[this.right()].release();
        this.forks[this.left()].release();
    }
}
