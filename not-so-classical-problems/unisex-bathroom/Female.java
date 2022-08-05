import java.util.concurrent.Semaphore;

public class Female implements Runnable {

    private Lightswitch femaleSwitch = new Lightswitch();

    private Semaphore femaleMultiplex = new Semaphore(3);

    private Semaphore empty;

    private Semaphore turnstile;

    public Female(Semaphore empty, Semaphore turnstile) {
        this.empty = empty;
        this.turnstile = turnstile;
    }

    @Override
    public void run() {
        try {
            this.turnstile.acquire();
            this.femaleSwitch.lock(this.empty);
            this.turnstile.release();
            String entrou = String.format(
                    "%s é mulher e entrou no banheiro",
                    Thread.currentThread().getName()
            );
            System.out.println(entrou);

            this.femaleMultiplex.acquire();
            long sleepTime = (long) (Math.random() * 1000);
            String msg = String.format(
                    "%s é mulher e está usando o banheiro por %d millisegundos",
                    Thread.currentThread().getName(),
                    sleepTime
            );
            System.out.println(msg);
            Thread.sleep(sleepTime);
            this.femaleMultiplex.release();
            String saiu = String.format(
                    "%s é mulher e saiu do banheiro",
                    Thread.currentThread().getName()
            );
            System.out.println(saiu);

            this.femaleSwitch.unlock(this.empty);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
