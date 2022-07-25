import java.util.concurrent.Semaphore;

public class Consumer extends Thread {

    volatile private static Buffer buffer;
    private static int cIn = 0;
    volatile private static Semaphore mutex;
    volatile private static Semaphore prodSem;
    volatile private static Semaphore consSem;

    public Consumer(Buffer sharedBuffer, Semaphore sharedMutex, Semaphore sharedProdSem, Semaphore sharedConsSem) {
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
            System.out.println("Consumer has finished");
            mutex.release();
            prodSem.release();
        }  catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
