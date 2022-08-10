public class BarrierFactory {

    public static Barrier createBarrier(int pickedBarrier, int numberOfThreads) {
        Barrier barrier;

        switch (pickedBarrier) {
            case 1:
                barrier = new SimpleBarrier(numberOfThreads);
                break;
            case 2:
                barrier = new ReusableBarrier(numberOfThreads);
                break;
            default:
                String msg = String.format(
                        "There is no barrier implementation for id %d",
                        pickedBarrier
                );
                throw new IllegalArgumentException(msg);
        }

        return barrier;
    }
}
