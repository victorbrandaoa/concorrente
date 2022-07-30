public class ThreadALoopCode implements Runnable {
    private ReusableBarrier barrier;

    public ThreadALoopCode(ReusableBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            for (int i=0; i < 2; i++) {
                this.a1();
                this.barrier.await(); // wait until all the threads that called the "await" finish their tasks
                this.a2();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void a1() throws InterruptedException {
        long sleepTime = (long) (Math.random() * 1000);
        Thread.sleep(sleepTime);
        String msg = String.format(
                "Thread %s finish the execution of the function a1 in %d milliseconds",
                Thread.currentThread().getName(),
                sleepTime
        );
        System.out.println(msg);
    }

    private void a2() throws InterruptedException {
        long sleepTime = (long) (Math.random() * 1000);
        Thread.sleep(sleepTime);
        String msg = String.format(
                "Thread %s finish the execution of the function a2 in %d milliseconds",
                Thread.currentThread().getName(),
                sleepTime
        );
        System.out.println(msg);
    }
}
