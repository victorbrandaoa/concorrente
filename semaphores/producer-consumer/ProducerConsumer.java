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

        Buffer buffer = new Buffer(size);
        Semaphore mutex = new Semaphore(1);
        Semaphore prodSem = new Semaphore(size);
        Semaphore consSem = new Semaphore(0);
        int v = 1;

        while(numberOfProducers != 0 || numberOfConsumers != 0) {
            if (Math.random() <= 0.5 && numberOfProducers > 0) {
                Producer prod = new Producer(buffer, v, mutex, prodSem, consSem);
                prod.start();
                v++;
                numberOfProducers--;
            } else if (Math.random() > 0.5 && numberOfConsumers > 0) {
                Consumer cons = new Consumer(buffer, mutex, prodSem, consSem);
                cons.start();
                numberOfConsumers--;
            }
        }
        System.out.println(Arrays.toString(buffer.getBuffer()));
    }
}
