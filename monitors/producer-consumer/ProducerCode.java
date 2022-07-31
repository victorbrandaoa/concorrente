public class ProducerCode implements Runnable {
    private BufferWithMonitors buffer;
    private int v;

    public ProducerCode(BufferWithMonitors buffer, int v) {
        this.buffer = buffer;
        this.v = v;

    }

    @Override
    public void run() {
        try {
            this.buffer.put(this.v); // add the produced value to the buffer
        }  catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
