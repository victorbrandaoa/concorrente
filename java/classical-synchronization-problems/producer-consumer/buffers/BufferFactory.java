public class BufferFactory {

    public static Buffer createBuffer(int pickedBuffer, int size) {
        Buffer buffer;

        switch (pickedBuffer) {
            case 1:
                buffer = new BufferWithSemaphores(size);
                break;
            case 2:
                buffer = new BufferWithMonitors(size);
                break;
            default:
                String msg = String.format(
                        "There is no buffer implementation for id %d",
                        pickedBuffer
                );
                throw new IllegalArgumentException(msg);
        }

        return buffer;
    }
}
