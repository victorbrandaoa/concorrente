import java.util.Arrays;
import java.util.Scanner;

public class ProducerConsumerMain {
    public static void main(String args[]) throws InterruptedException {
        Scanner input = new Scanner(System.in);
        ProducerConsumerMain.showMenu();
        int pickedBuffer = input.nextInt();
        System.out.println("Enter the size of the buffer: ");
        int size = input.nextInt();
        System.out.println("Enter the number of producers: ");
        int numberOfProducers = input.nextInt();
        System.out.println("Enter the number of consumers: ");
        int numberOfConsumers = input.nextInt();

        Thread[] threads = new Thread[numberOfConsumers + numberOfProducers];
        int i = 0;

        Buffer buffer = BufferFactory.createBuffer(pickedBuffer, size);
        int v = 1;

        while(numberOfProducers != 0 || numberOfConsumers != 0) {

            if (numberOfProducers > 0) {
                Producer task = new Producer(buffer, v);
                Thread t = new Thread(task); // Create a thread
                threads[i] = t; // Add thread to array of threads
                v++;
                numberOfProducers--;
                i++;
            }

            if (numberOfConsumers > 0) {
                Consumer task = new Consumer(buffer);
                Thread t = new Thread(task); // Create a thread
                threads[i] = t; // Add thread to array of threads
                numberOfConsumers--;
                i++;
            }

        }

        for (Thread t : threads) {
            t.start(); // Start a thread
        }

        for (Thread t : threads) {
            t.join(); // Wait a thread
        }

        String msg = String.format(
                "Final buffer: %s",
                Arrays.toString(buffer.getBuffer())
        );
        System.out.println(msg);
    }

    public static void showMenu() {
        System.out.println("Type the number of the buffer that you want to test");
        System.out.println("[1] - Buffer implementation with semaphores");
        System.out.println("[1] - Buffer implementation with monitors");
    }
}
