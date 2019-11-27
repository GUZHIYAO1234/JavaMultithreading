/**
 * Dead Lock
 * The sequence of getting the lock during multithreading should be same, or it 
 * leads to dead lock
 */
public class Demo6 {
    public static void main(String[] args) throws InterruptedException{
        Beeper beeper = new Beeper();
        Beeper beeper2 = new Beeper();

        Thread tt1 = new Thread(new Runnable(){
            @Override
            public void run() {
                beeper.add(Diamonded.lockA,Diamonded.lockB);
            }
        });

        Thread tt2 = new Thread(new Runnable(){
        
            @Override
            public void run() {
                beeper2.sub(Diamonded.lockA, Diamonded.lockB);
            }
        });

        tt1.start();
        tt2.start();

        tt1.join();
        tt2.join();

        System.out.println(beeper.getCount());
        System.out.println(beeper2.getCount());
        System.out.println("End");
    }
}

class Diamonded {
    public static final Object lockA = new Object();
    public static final Object lockB = new Object();
}

class Beeper {
    private int beep = 0;

    public int getCount() {
        return beep;
    }

    public void add(Object lockA, Object lockB) {
        synchronized (lockA) {
            beep++;
            System.out.println("Thread " + Thread.currentThread().getId() + " : " + Thread.currentThread().getState());
            synchronized (lockB) {
                beep++;
            }
        }
    }

    public void sub(Object lockA, Object lockB) {
        synchronized (lockB) {
            beep--;
            System.out.println("Thread " + Thread.currentThread().getId() + " : " + Thread.currentThread().getState());
            synchronized (lockA) {
                beep--;
            }
        }
    }
}