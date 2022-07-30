import java.util.Scanner;

public class MultiThreadRendezvousLoop {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of threads to create: ");
        int numberOfThreads = input.nextInt();

        Thread[] threads = new Thread[numberOfThreads];
        ReusableBarrier barrier = new ReusableBarrier(numberOfThreads);

        int i = 0;
        while (i < numberOfThreads) {
            if (Math.random() > 0.5) {
                ThreadALoopCode task = new ThreadALoopCode(barrier);
                Thread t = new Thread(task);
                threads[i] = t;
            } else {
                ThreadBLoopCode task = new ThreadBLoopCode(barrier);
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
