public class Counter {
    private int count = 0;

    public void increment() throws InterruptedException {
        long sleepTime = (long) (Math.random() * 1000);
        String msg = String.format(
                "Thread %s will sleep %d milliseconds",
                Thread.currentThread().getName(),
                sleepTime
        );
        System.out.println(msg);
        int tmp = this.count; // Read the value of count
        // This sleep is simulating the time of some operations after read the value of count and before update it
        Thread.sleep(sleepTime);
        this.count = tmp + 1; // Update the value of count
    }

    public int getCount() {
        return this.count;
    }
}
