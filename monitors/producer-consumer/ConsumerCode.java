public class ConsumerCode implements Runnable {
    private BufferWithMonitors buffer;

    public ConsumerCode(BufferWithMonitors buffer) {
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
