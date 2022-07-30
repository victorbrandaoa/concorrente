import java.util.Scanner;

public class MultiThreadRendezvous {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of threads to create: ");
        int numberOfThreads = input.nextInt();

        Thread[] threads = new Thread[numberOfThreads];
        Barrier barrier = new Barrier(numberOfThreads);

        int i = 0;
        while (i < numberOfThreads) {
            if (Math.random() > 0.5) {
                ThreadACode task = new ThreadACode(barrier);
                Thread t = new Thread(task);
                threads[i] = t;
            } else {
                ThreadBCode task = new ThreadBCode(barrier);
                Thread t = new Thread(task);
                threads[i] = t;
            }
            i++;
        }

        for (Thread t : threads) {
            t.start();
        }
    }
}
