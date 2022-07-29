import java.util.concurrent.Semaphore;

public class Buffer {

    private int[] buffer;
    private final int size;
    private Semaphore mutex;
    private int cIn = 0;
    private int pIn = 0;

    public Buffer(int size, Semaphore mutex) {
        this.buffer = new int[size];
        this.size = size;
        this.mutex = mutex;
    }

    public void put(int v) throws InterruptedException {
        this.mutex.acquire(); // mutex.down()
        this.buffer[this.pIn] = v;
        this.pIn = (this.pIn + 1) % this.size;
        this.mutex.release(); // mutex.up()
    }

    public int get() throws InterruptedException {
        this.mutex.acquire(); // mutex.down()
        int v = this.buffer[this.cIn];
        this.cIn = (this.cIn + 1) % this.size; // update the index where the next consumer must get the value to process
        this.mutex.release(); // mutex.up()
        return v;
    }

    public int[] getBuffer() {
        return buffer;
    }

    public int getSize() {
        return this.size;
    }
}
