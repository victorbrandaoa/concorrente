import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
    public static void main(String[] args) {
        Semaphore[] forks = new Semaphore[5];
        Semaphore footman = new Semaphore(4);

        Thread[] threads = new Thread[5];

        for (int i=0; i < 5; i++) {
            forks[i] = new Semaphore(1);
        }

        for (int i=0; i < 5; i++) {
            Philosopher task = new Philosopher(i, forks, footman);
            Thread t = new Thread(task);
            threads[i] = t;
        }

        for (Thread t : threads) {
            t.start();
        }
    }
}
