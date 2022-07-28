import java.util.concurrent.Semaphore;

public class Consumer extends Thread {

    private static Buffer buffer;
    private static int cIn = 0;
    private static Semaphore mutex;
    private static Semaphore prodSem;
    private static Semaphore consSem;

    public Consumer(Buffer sharedBuffer, Semaphore sharedMutex, Semaphore sharedProdSem, Semaphore sharedConsSem, String threadName) {
        super(threadName);
        buffer = sharedBuffer;
        mutex = sharedMutex;
        prodSem = sharedProdSem;
        consSem = sharedConsSem;
    }

    @Override
    public void run() {
        try {
            consSem.acquire(); // consSem.down() - if the buffer is empty, wait until some producer adds an item
            mutex.acquire(); // mutex.down()
            int v = buffer.get(cIn); // get the value from the buffer
            cIn = (cIn + 1) % buffer.getSize(); // update the index where the next consumer must get the value to process
            String msg = String.format(
                    "Consumer %s has finished the value %d",
                    Thread.currentThread().getName(),
                    v
            );
            System.out.println(msg);
            mutex.release(); // mutex.up()
            prodSem.release(); // prodSem.up() - notify the producers threads that there is an empty space in the buffer to add a new item
        }  catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
