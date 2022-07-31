public class BufferWithMonitors {
    private int[] buffer;

    private int size;

    private int cIn = 0;

    private int pIn = 0;

    private int length = 0;

    public BufferWithMonitors(int size) {
        this.buffer = new int[size];
        this.size = size;
    }

    public synchronized void put(int v) throws InterruptedException { // synchronized to guarantee mutual exclusion
        while (this.length == this.size) {
            // while the buffer is full call the wait method
            // inside a while loop because the method notifyAll can wake up this thread while the buffer is still full
            wait();
        }

        this.buffer[this.pIn] = v;
        this.pIn = (this.pIn + 1) % this.size; // update the index where the next producer must put its produced value
        this.length++; // increment the length of produced values on the buffer
        String msg = String.format(
                "Producer %s has finished the value %d",
                Thread.currentThread().getName(),
                v
        );
        System.out.println(msg);

        notifyAll(); // notify all the threads (producers and consumers) after a producer finish its task
    }

    public synchronized int get() throws InterruptedException { // synchronized to guarantee mutual exclusion
        while (this.length == 0) {
            // while the buffer is empty call the wait method
            // inside a while loop because the method notifyAll can wake up this thread while the buffer is still empty
            wait();
        }

        int v = this.buffer[this.cIn];
        this.cIn = (this.cIn + 1) % this.size; // update the index where the next consumer must get the value to process
        this.length--; // decrement the length of produced values on the buffer
        String msg = String.format(
                "Consumer %s has finished the value %d",
                Thread.currentThread().getName(),
                v
        );
        System.out.println(msg);
        notifyAll(); // notify all the threads (producers and consumers) after a consumer finish its task

        return v;
    }

    public int[] getBuffer() {
        return buffer;
    }
}
