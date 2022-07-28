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
            consSem.acquire();
            mutex.acquire();
            int v = buffer.get(cIn);
            cIn = (cIn + 1) % buffer.getSize();
            String msg = String.format(
                    "Consumer %s has finished the value %d",
                    Thread.currentThread().getName(),
                    v
            );
            System.out.println(msg);
            mutex.release();
            prodSem.release();
        }  catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
