import java.util.Scanner;

public class LockMain {

    public static void main(String[] args) throws InterruptedException {
        Scanner input = new Scanner(System.in);
        LockMain.showMenu();
        int pickedLock = input.nextInt();
        System.out.println("Enter the number of threads to create: ");
        int numberOfThreads = input.nextInt();

        Thread[] threads = new Thread[numberOfThreads];

        Counter c = new Counter(); // Create the counter that will be shared between the threads
        LockInterface lock = LockFactory.createLock(pickedLock); // Create the lock

        for (int i = 0; i < numberOfThreads; i++) {
            ConcurrentCode task = new ConcurrentCode(c, lock);
            Thread t = new Thread(task); // Create Thread
            threads[i] = t; // Add thread to array of threads
        }

        for (Thread t : threads) {
            t.start(); // Start a thread
        }

        for (Thread t : threads) {
            t.join(); // Wait a thread
        }
        String msg = String.format(
                "Final value of the counter: %d",
                c.getCount()
        );
        System.out.println(msg);
    }

    public static void showMenu() {
        System.out.println("Type the number of the lock that you want to test");
        System.out.println("[1] - Lock implementation with Mutex");
        System.out.println("[2] - Lock implementation with TAS");
        System.out.println("[3] - Lock implementation with TTAS");
        System.out.println("[4] - Lock implementation with TTAS and Backoff");
    }
}
