public class ThreadACode implements Runnable {
    private Barrier barrier;

    private int barrierRounds;

    public ThreadACode(Barrier barrier, int barrierRounds) {
        this.barrier = barrier;
        this.barrierRounds = barrierRounds;
    }

    @Override
    public void run() {
        try {
            String msg = String.format(
                    "The Barrier fell: AThread %s",
                    Thread.currentThread().getName()
            );
            for (int i=0; i < this.barrierRounds; i++) {
                this.a1();
                this.barrier.await(); // wait until all the threads that called the "await" finish their tasks
                System.out.println(msg);
                this.a2();
                Thread.sleep(2000L); // sleep before the next barrier round
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
