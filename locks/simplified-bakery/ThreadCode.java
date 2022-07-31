public class ThreadCode implements Runnable {

    private int id;

    private Counter count;

    private SimplifiedBakeryLock bakery;

    public ThreadCode(Counter count, SimplifiedBakeryLock bakery, int id) {
        this.id = id;
        this.count = count;
        this.bakery = bakery;
    }

    @Override
    public void run() {
        try {
            this.bakery.lock(this.id); // mutex.down()
            this.count.increment(); // Update the value of count
            String countValueMsg = String.format(
                    "The thread %s has updated the count value to %d",
                    Thread.currentThread().getName(),
                    this.count.getCount()
            );
            System.out.println(countValueMsg);
            this.bakery.unlock(this.id); // mutex.up()
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
