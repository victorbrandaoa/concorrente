import java.util.concurrent.Semaphore;

public class ThreadA implements Runnable {

    private Semaphore aArrived;

    private Semaphore bArrived;

    public ThreadA(Semaphore aArrived, Semaphore bArrived) {
        this.aArrived = aArrived;
        this.bArrived = bArrived;
    }

    @Override
    public void run() {
        try {
            this.a1();
            this.aArrived.release(); // notify the ThreadB that the a1 function finished
            this.bArrived.acquire(); // wait ThreadB finishes the b1 function
            this.a2();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void a1() throws InterruptedException {
        long sleepTime = (long) (Math.random() * 1000);
        Thread.sleep(sleepTime);
        String msg = String.format(
                "The function a1 finished its execution in %d milliseconds",
                sleepTime
        );
        System.out.println(msg);
    }

    private void a2() throws InterruptedException {
        long sleepTime = (long) (Math.random() * 1000);
        Thread.sleep(sleepTime);
        String msg = String.format(
                "The function a2 finished its execution in %d milliseconds",
                sleepTime
        );
        System.out.println(msg);
    }
}
