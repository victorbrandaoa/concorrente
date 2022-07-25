public class Buffer {

    private static int[] buffer;
    private final int size;

    public Buffer(int size) {
        buffer = new int[size];
        this.size = size;
    }

    public void put(int pIn, int v) {
        buffer[pIn] = v;
    }

    public int get(int cIn) {
        return buffer[cIn];
    }

    public int[] getBuffer() {
        return buffer;
    }

    public int getSize() {
        return this.size;
    }
}
