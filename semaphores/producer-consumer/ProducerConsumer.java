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

        Buffer buffer = new Buffer(size);
        Semaphore mutex = new Semaphore(1);
        Semaphore prodSem = new Semaphore(size);
        Semaphore consSem = new Semaphore(0);
        int v = 1;

        while(numberOfProducers != 0 || numberOfConsumers != 0) {
            if (Math.random() <= 0.5 && numberOfProducers > 0) {
                Producer prod = new Producer(buffer, v, mutex, prodSem, consSem, String.valueOf(i));
                prod.start();
                threads[i] = prod;
                v++;
                numberOfProducers--;
                i++;
            } else if (Math.random() > 0.5 && numberOfConsumers > 0) {
                Consumer cons = new Consumer(buffer, mutex, prodSem, consSem, String.valueOf(i));
                cons.start();
                threads[i] = cons;
                numberOfConsumers--;
                i++;
            }
        }
        for (Thread t : threads) {
            t.join();
        }
        String msg = String.format(
                "Final buffer: %s",
                Arrays.toString(buffer.getBuffer())
        );
        System.out.println(msg);
    }
}
