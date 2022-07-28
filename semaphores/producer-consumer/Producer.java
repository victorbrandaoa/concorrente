import java.util.concurrent.Semaphore;

public class Producer extends Thread {

    private static Buffer buffer;
    private int v;
    private static int pIn = 0;
    private static Semaphore mutex;
    private static Semaphore prodSem;
    private static Semaphore consSem;

    public Producer(Buffer sharedBuffer, int v, Semaphore sharedMutex, Semaphore sharedProdSem, Semaphore sharedConsSem, String threadName) {
        super(threadName);
        buffer = sharedBuffer;
        this.v = v;
        mutex = sharedMutex;
        prodSem = sharedProdSem;
        consSem = sharedConsSem;
    }

    @Override
    public void run() {
        try {
            prodSem.acquire(); // prodSem.down() - if the buffer is full, wait until some consumer removes an item
            mutex.acquire(); // mutex.down()
            buffer.put(pIn, this.v); // add the produced value to the buffer
            pIn = (pIn + 1) % buffer.getSize(); // update the index where the next producer must add its produced value
            String msg = String.format(
                    "Producer %s has finished the value %d",
                    Thread.currentThread().getName(),
                    this.v
            );
            System.out.println(msg);
            mutex.release(); // mutex.up()
            consSem.release(); // consSem.up() - notify the consumers threads that there is a new item to process
        }  catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
