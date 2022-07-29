import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class ProducerConsumer {
    public static void main(String args[]) throws InterruptedException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the size of the buffer: ");
        int size = input.nextInt();
        System.out.println("Enter the number of producers: ");
        int numberOfProducers = input.nextInt();
        System.out.println("Enter the number of consumers: ");
        int numberOfConsumers = input.nextInt();

        Thread[] threads = new Thread[numberOfConsumers + numberOfProducers];
        int i = 0;

        Semaphore mutex = new Semaphore(1);
        Buffer buffer = new Buffer(size, mutex);
        Semaphore prodSem = new Semaphore(size);
        Semaphore consSem = new Semaphore(0);
        int v = 1;

        while(numberOfProducers != 0 || numberOfConsumers != 0) {

            if (numberOfProducers > 0) {
                Producer task = new Producer(buffer, v, prodSem, consSem);
                Thread t = new Thread(task); // Create a thread
                threads[i] = t; // Add thread to array of threads
                v++;
                numberOfProducers--;
                i++;
            }

            if (numberOfConsumers > 0) {
                Consumer task = new Consumer(buffer, prodSem, consSem);
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
}
