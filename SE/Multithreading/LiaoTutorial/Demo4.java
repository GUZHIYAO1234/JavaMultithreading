/**
 * Sychronization and lock During concurrent programming, to seize the resource
 * that multiple threads want to access to a single thread each time is
 * essential While, in parellel programming, the common value could be access by
 * either thread at any time. When one thread has read the value, make sure the
 * most recent thread that changes the value flush the result from its cache to
 * the shared memory.
 * 
 * Class level lock: A lock on a static obj or sth.class will only allow one thread 
 * to access protected obj in the class or any instance of the class each time.
 * 
 * Objct level lock: A lock on an obj or this will only allow one thread to execute 
 * access protected obj in the instance.
 */
public class Demo4 {

    public static void main(String args[]) throws InterruptedException {
        Thread[] wokers = new Thread[] { new Thread(new Digger()), new Thread(new Stealer()) };
        for (Thread worker : wokers) {
            worker.start();
        }

        for (Thread worker : wokers) {
            worker.join();
        }

        System.out.println("Diamonds: " + Diamond.diamonds_count);
    }

}

class Diamond {
    public static final Object mineLock = new Object();
    public static int diamonds_count = 0;
}

class Digger implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            synchronized (Diamond.mineLock) {
                Diamond.diamonds_count++;
                System.out.println("digger");
            }
        }
    }

}

class Stealer implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            synchronized (Diamond.mineLock) {
                Diamond.diamonds_count--;
                System.out.println("stealer");
            }
        }
    }
}
