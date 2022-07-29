import java.util.concurrent.Semaphore;

public class Producer implements Runnable {

    private Buffer buffer;
    private int v;
    private Semaphore prodSem;
    private Semaphore consSem;

    public Producer(Buffer buffer, int v, Semaphore prodSem, Semaphore consSem) {
        this.buffer = buffer;
        this.v = v;
        this.prodSem = prodSem;
        this.consSem = consSem;
    }

    @Override
    public void run() {
        try {
            this.prodSem.acquire(); // prodSem.down() - if the buffer is full, wait until some consumer removes an item
            this.buffer.put(this.v); // add the produced value to the buffer
            String msg = String.format(
                    "Producer %s has finished the value %d",
                    Thread.currentThread().getName(),
                    this.v
            );
            System.out.println(msg);
            this.consSem.release(); // consSem.up() - notify the consumers threads that there is a new item to process
        }  catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
