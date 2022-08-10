import java.util.concurrent.Semaphore;

public class ThreadB implements Runnable {
    private Semaphore aArrived;

    private Semaphore bArrived;

    public ThreadB(Semaphore aArrived, Semaphore bArrived) {
        this.aArrived = aArrived;
        this.bArrived = bArrived;
    }

    @Override
    public void run() {
        try {
            this.b1();
            this.bArrived.release(); // notify the ThreadA that the b1 function finished
            this.aArrived.acquire(); // wait ThreadA finishes the a1 function
            this.b2();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void b1() throws InterruptedException {
        long sleepTime = (long) (Math.random() * 1000);
        Thread.sleep(sleepTime);
        String msg = String.format(
                "The function b1 finished its execution in %d milliseconds",
                sleepTime
        );
        System.out.println(msg);
    }

    private void b2() throws InterruptedException {
        long sleepTime = (long) (Math.random() * 1000);
        Thread.sleep(sleepTime);
        String msg = String.format(
                "The function b2 finished its execution in %d milliseconds",
                sleepTime
        );
        System.out.println(msg);
    }
}
