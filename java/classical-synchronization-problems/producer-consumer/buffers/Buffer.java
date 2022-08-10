public interface Buffer {

    public void put(int v) throws InterruptedException;

    public int get() throws InterruptedException;

    public int[] getBuffer();
}
