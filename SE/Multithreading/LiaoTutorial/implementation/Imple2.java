package implementation;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Reentrant Lock
 * Similar to synchronized block, which utilizes an object to lock the block of code
 * however, you could choose to do sth else if the lock could not be acquired 
 * temporarily
 */

public class Imple2 {

}

class bee {
    private final Lock lock = new ReentrantLock();
    private int bee = 0;

    public void add(int n) throws InterruptedException {

        // Try to acquire lock, if not possible, 
        if (lock.tryLock(1, TimeUnit.SECONDS)) {
            lock.lock();
            try {
                bee++;
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("The lock is still in using. let's go to swim");
        }
    }
}