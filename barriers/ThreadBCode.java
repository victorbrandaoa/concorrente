public class ThreadBCode implements Runnable {
    private Barrier barrier;

    private int barrierRounds;

    public ThreadBCode(Barrier barrier, int barrierRounds) {
        this.barrier = barrier;
        this.barrierRounds = barrierRounds;
    }

    @Override
    public void run() {
        try {
            String msg = String.format(
                    "The Barrier fell: BThread %s",
                    Thread.currentThread().getName()
            );
            for (int i=0; i < this.barrierRounds; i++) {
                this.b1();
                this.barrier.await(); // wait until all the threads that called the "await" finish their tasks
                System.out.println(msg);
                this.b2();
                Thread.sleep(2000L); // sleep before the next barrier round
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
