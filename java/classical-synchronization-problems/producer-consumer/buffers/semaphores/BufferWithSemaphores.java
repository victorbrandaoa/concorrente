import java.util.concurrent.Semaphore;

public class BufferWithSemaphores implements Buffer {

    private int[] buffer;
    private int size;
    private Semaphore mutex;

    private Semaphore prodSem;

    private Semaphore consSem;
    private int cIn = 0;
    private int pIn = 0;

    public BufferWithSemaphores(int size) {
        this.buffer = new int[size];
        this.size = size;
        this.mutex = new Semaphore(1);
        this.prodSem = new Semaphore(size);
        this.consSem = new Semaphore(0);
    }

    @Override
    public void put(int v) throws InterruptedException {
        this.prodSem.acquire(); // prodSem.down() - if the buffer is full, wait until some consumer removes an item
        this.mutex.acquire(); // mutex.down()

        this.buffer[this.pIn] = v;
        this.pIn = (this.pIn + 1) % this.size; // update the index where the next producer must put its produced value

        String msg = String.format(
                "Producer %s has finished the value %d",
                Thread.currentThread().getName(),
                v
        );
        System.out.println(msg);

        this.mutex.release(); // mutex.up()
        this.consSem.release(); // consSem.up() - notify the consumers threads that there is a new item to process
    }

    @Override
    public int get() throws InterruptedException {
        this.consSem.acquire(); // consSem.down() - if the buffer is empty, wait until some producer adds an item
        this.mutex.acquire(); // mutex.down()

        int v = this.buffer[this.cIn];
        this.cIn = (this.cIn + 1) % this.size; // update the index where the next consumer must get the value to process

        String msg = String.format(
                "Consumer %s has finished the value %d",
                Thread.currentThread().getName(),
                v
        );
        System.out.println(msg);

        this.mutex.release(); // mutex.up()
        this.prodSem.release(); // prodSem.up() - notify the producers threads that there is an empty space in the buffer to add a new item

        return v;
    }

    @Override
    public int[] getBuffer() {
        return this.buffer;
    }
}
