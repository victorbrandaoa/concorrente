import java.util.concurrent.Semaphore;

public class Producer implements Runnable {

    private Buffer buffer;
    private int v;

    public Producer(Buffer buffer, int v) {
        this.buffer = buffer;
        this.v = v;

    }

    @Override
    public void run() {
        try {
            this.buffer.put(this.v); // add the produced value to the buffer
        }  catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
