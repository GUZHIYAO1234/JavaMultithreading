import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Capture the thread state during calling another thread's join method
 */
public class Demo7 {

    public static void main(String[] args) throws InterruptedException {
        final Object writeLock = new Object();
        JoinSignal joinSignal = new JoinSignal();
        Thread kk1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread kk11 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Grab the write lock and rewrite the signal status
                            synchronized (writeLock) {
                                System.out.println("\n" + KKThreadGroup.THREAD_KK11 + " => start");
                                joinSignal.setJoinSignal(true);
                                // waiting for the kk1's status to be written
                                writeLock.wait();
                                System.out.println(KKThreadGroup.THREAD_KK11 + " => state writing completed!");
                            }
                        } catch (InterruptedException e) {
                            System.out.println(Thread.currentThread().getId() + " is interrupted!");
                        }
                    }
                });

                kk11.start();
                try {
                    kk11.join();
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getId() + " is interrupted!");
                }
            }
        });

        System.out.println(KKThreadGroup.THREAD_MAIN + " => Start");
        kk1.start();
        System.out.print(KKThreadGroup.THREAD_MAIN + " => ");

        // Waiting for the status change
        while (!joinSignal.getJoinSignal()) {
            System.out.print("=");
        }

        // Join signal is received, waiting for the release of write lock
        synchronized (writeLock) {
            System.out.println("\n" + KKThreadGroup.THREAD_MAIN + " => kk1 is watting for kk11 to join");
            System.out.println(KKThreadGroup.THREAD_KK11 + " => " + kk1.getState());
            writeLock.notify();
        }

        kk1.join();
        System.out.println(KKThreadGroup.THREAD_MAIN + " => All tasks completed");
    }
}

class JoinSignal {
    private volatile boolean joinSignal = false;
    private final Lock lockkk = new ReentrantLock();

    public boolean getJoinSignal() {
        return joinSignal;
    }

    public void setJoinSignal(boolean signal) {
        lockkk.lock();
        try {
            joinSignal = signal;
        } finally {
            lockkk.unlock();
        }
    }
}

enum KKThreadGroup {
    THREAD_MAIN, THREAD_KK1, THREAD_KK11;
}
