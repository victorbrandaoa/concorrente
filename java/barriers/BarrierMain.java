import java.util.Scanner;

public class BarrierMain {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BarrierMain.showMenu();
        int pickedBarrier = input.nextInt();
        System.out.println("Enter the number of threads to create: ");
        int numberOfThreads = input.nextInt();

        Thread[] threads = new Thread[numberOfThreads];
        Barrier barrier = BarrierFactory.createBarrier(pickedBarrier, numberOfThreads);

        int barrierRounds = 1;
        if (pickedBarrier == 2) {
            System.out.println("Enter the number of times that the barrier has to be reused: ");
            barrierRounds = input.nextInt();
        }

        int i = 0;
        while (i < numberOfThreads) {
            if (Math.random() > 0.5) {
                ThreadACode task = new ThreadACode(barrier, barrierRounds);
                Thread t = new Thread(task);
                threads[i] = t;
            } else {
                ThreadBCode task = new ThreadBCode(barrier, barrierRounds);
                Thread t = new Thread(task);
                threads[i] = t;
            }
            i++;
        }

        for (Thread t : threads) {
            t.start();
        }
    }

    public static void showMenu() {
        System.out.println("Type the number of the barrier that you want to test");
        System.out.println("[1] - Simple barrier implementation");
        System.out.println("[2] - Reusable barrier implementation");
    }
}
