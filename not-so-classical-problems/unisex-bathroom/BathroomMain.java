import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class BathroomMain {
    public static void main(String[] args) throws InterruptedException {
        Scanner input = new Scanner(System.in);
        System.out.println("Número de threads: ");
        int numberOfThreads = input.nextInt();

        Thread[] threads = new Thread[numberOfThreads];
        Semaphore empty = new Semaphore(1);
        Semaphore turnstile = new Semaphore(3);

        int i = 0;
        while (i < numberOfThreads) {
            if (Math.random() > 0.5) {
                Male male = new Male(empty, turnstile);
                Thread t = new Thread(male);
                threads[i] = t;
            } else {
                Female female = new Female(empty, turnstile);
                Thread t = new Thread(female);
                threads[i] = t;
            }
            i++;
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }
}
