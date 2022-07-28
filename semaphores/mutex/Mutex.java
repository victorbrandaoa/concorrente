import java.util.Scanner;

public class Mutex {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of threads to create: ");
        int numberOfThreads = input.nextInt();

        for (int i = 0; i < numberOfThreads; i++) {
            ConcurrentCode thread = new ConcurrentCode(String.valueOf(i));
            thread.start();
        }
    }
}
