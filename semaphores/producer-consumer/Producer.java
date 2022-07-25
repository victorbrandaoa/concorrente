import java.util.concurrent.Semaphore;

public class Producer extends Thread {

    volatile private static Buffer buffer;
    private int v;
    volatile private static int pIn = 0;
    volatile private static Semaphore mutex;
    volatile private static Semaphore prodSem;
    volatile private static Semaphore consSem;

    public Producer(Buffer sharedBuffer, int v, Semaphore sharedMutex, Semaphore sharedProdSem, Semaphore sharedConsSem) {
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
            System.out.println("Producer has finished");
            mutex.release();
            consSem.release();
        }  catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
