public class ThreadBLoopCode implements Runnable {
    private ReusableBarrier barrier;

    public ThreadBLoopCode(ReusableBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            for (int i=0; i < 2; i++) {
                this.b1();
                this.barrier.await(); // wait until all the threads that called the "await" finish their tasks
                this.b2();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void b1() throws InterruptedException {
        long sleepTime = (long) (Math.random() * 1000);
        Thread.sleep(sleepTime);
        String msg = String.format(
                "Thread %s finish the execution of the function b1 in %d milliseconds",
                Thread.currentThread().getName(),
                sleepTime
        );
        System.out.println(msg);
    }

    private void b2() throws InterruptedException {
        long sleepTime = (long) (Math.random() * 1000);
        Thread.sleep(sleepTime);
        String msg = String.format(
                "Thread %s finish the execution of the function b2 in %d milliseconds",
                Thread.currentThread().getName(),
                sleepTime
        );
        System.out.println(msg);
    }
}
