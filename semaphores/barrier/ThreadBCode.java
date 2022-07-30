public class ThreadBCode implements Runnable {
    private Barrier barrier;

    public ThreadBCode(Barrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            this.b1();
            this.barrier.await(); // wait until all the threads that called the "await" finish their tasks
            this.b2();
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
