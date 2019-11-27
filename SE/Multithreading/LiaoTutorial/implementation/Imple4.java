package implementation;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * ReadWrite Lock
 * 
 * Only allows one thread to write with pending the other threads from reading
 * or writing, when there is no writing, it allows all threads to read
 */

public class Imple4 {

}

class notebook {

    private final ReadWriteLock locke = new ReentrantReadWriteLock();
    private final Lock rlock = locke.readLock();
    private final Lock wlock = locke.writeLock();
    private int[] notes = new int[10];

    public void writeToNote(int index) {
        wlock.lock();
        try {
            notes[index] += 1;
        } finally {
            wlock.unlock();
        }
    }

    public int[] readFromNote() {
        rlock.lock();
        try {
        } finally {
            rlock.unlock();
        }
        return Arrays.copyOf(notes, notes.length);
    }
}