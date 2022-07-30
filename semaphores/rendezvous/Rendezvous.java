import java.util.concurrent.Semaphore;

public class Rendezvous {

    public static void main(String[] args) {

        Semaphore aArrived = new Semaphore(0);
        Semaphore bArrived = new Semaphore(0);

        ThreadA taskA = new ThreadA(aArrived, bArrived);
        Thread threadA = new Thread(taskA);
        ThreadB taskB = new ThreadB(aArrived, bArrived);
        Thread threadB = new Thread(taskB);

        if (Math.random() > 0.5) {
            threadA.start();
            threadB.start();
        } else {
            threadB.start();
            threadA.start();
        }
    }
}
