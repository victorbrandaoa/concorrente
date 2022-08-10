import java.util.concurrent.Semaphore;

public class Male implements Runnable {
    private Lightswitch maleSwitch = new Lightswitch();

    private Semaphore maleMultiplex = new Semaphore(3);

    private Semaphore empty;


    private Semaphore turnstile;

    public Male(Semaphore empty, Semaphore turnstile) {
        this.empty = empty;
        this.turnstile = turnstile;
    }

    @Override
    public void run() {
        try {
            this.turnstile.acquire();
            this.maleSwitch.lock(this.empty);
            this.turnstile.release();
            String entrou = String.format(
                    "%s é homem e entrou no banheiro",
                    Thread.currentThread().getName()
            );
            System.out.println(entrou);

            this.maleMultiplex.acquire();
            long sleepTime = (long) (Math.random() * 1000);
            String msg = String.format(
                    "%s é homem e está usando o banheiro por %d millisegundos",
                    Thread.currentThread().getName(),
                    sleepTime
            );
            System.out.println(msg);
            Thread.sleep(sleepTime);
            this.maleMultiplex.release();
            String saiu = String.format(
                    "%s é homem e saiu do banheiro",
                    Thread.currentThread().getName()
            );
            System.out.println(saiu);

            this.maleSwitch.unlock(this.empty);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
