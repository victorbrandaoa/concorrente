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
            prodSem.acquire();
            mutex.acquire();
            buffer.put(pIn, this.v);
            pIn = (pIn + 1) % buffer.getSize();
            String msg = String.format(
                    "Producer %s has finished the value %d",
                    Thread.currentThread().getName(),
                    this.v
            );
            System.out.println(msg);
            mutex.release();
            consSem.release();
        }  catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
