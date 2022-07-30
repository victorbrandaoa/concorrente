public class Consumer implements Runnable {

    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            int v = this.buffer.get(); // get the value from the buffer
        }  catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
