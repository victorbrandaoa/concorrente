import java.util.concurrent.Semaphore;

public class Consumer implements Runnable {

    private Buffer buffer;
    private Semaphore prodSem;
    private Semaphore consSem;

    public Consumer(Buffer buffer, Semaphore prodSem, Semaphore consSem) {
        this.buffer = buffer;
        this.prodSem = prodSem;
        this.consSem = consSem;
    }

    @Override
    public void run() {
        try {
            this.consSem.acquire(); // consSem.down() - if the buffer is empty, wait until some producer adds an item
            int v = this.buffer.get(); // get the value from the buffer
            String msg = String.format(
                    "Consumer %s has finished the value %d",
                    Thread.currentThread().getName(),
                    v
            );
            System.out.println(msg);
            this.prodSem.release(); // prodSem.up() - notify the producers threads that there is an empty space in the buffer to add a new item
        }  catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
