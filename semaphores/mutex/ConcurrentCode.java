import java.util.concurrent.Semaphore;

public class ConcurrentCode extends Thread {
    public static int count = 0;
    private static Semaphore mutex = new Semaphore(1);

    public ConcurrentCode(String threadName) {
        super(threadName);
    }

    @Override
    public void run() {
        try {
            long sleepTime = (long) (Math.random() * 1000);
            String msg = String.format(
                    "Thread %s will sleep %d milliseconds",
                    Thread.currentThread().getName(),
                    sleepTime
            );
            System.out.println(msg);
            // This sleep is simulating the time of some operations before read the value of count
            Thread.sleep(sleepTime);
            mutex.acquire(); // mutex.down()
            int temp = count; // Read the value of count
            // This sleep is simulating the time of some operations after read the value of count and before update it
            Thread.sleep(sleepTime);
            count = temp + 1; // Update the value of count
            String countValueMsg = String.format(
                    "The thread %s has updated the count value to %d",
                    Thread.currentThread().getName(),
                    count
            );
            System.out.println(countValueMsg);
            mutex.release(); // mutex.up()
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
