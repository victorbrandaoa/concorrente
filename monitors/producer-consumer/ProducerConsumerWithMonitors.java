import java.util.Arrays;
import java.util.Scanner;

public class ProducerConsumerWithMonitors {
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

        BufferWithMonitors buffer = new BufferWithMonitors(size);
        int v = 1;

        while(numberOfProducers != 0 || numberOfConsumers != 0) {

            if (numberOfProducers > 0) {
                ProducerCode task = new ProducerCode(buffer, v);
                Thread t = new Thread(task); // Create a thread
                threads[i] = t; // Add thread to array of threads
                v++;
                numberOfProducers--;
                i++;
            }

            if (numberOfConsumers > 0) {
                ConsumerCode task = new ConsumerCode(buffer);
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
