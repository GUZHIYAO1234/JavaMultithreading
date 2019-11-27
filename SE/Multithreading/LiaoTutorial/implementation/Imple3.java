package implementation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * await(), signal(), and signalAll()
 * 
 * Similar to wait(), notify(), and notifyAll()
 */

public class Imple3 {

    public static void main(final String[] args) throws InterruptedException {
        // System.out.println();
        TakeQueue queue = new TakeQueue();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(queue.getMember());
            }
        });

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                queue.add("Noob", t2);
                queue.add("Lala", t2);
                queue.add("Lolo", t2);
            }
        });

        t2.start();
        t1.start();

        t2.join();
        t1.join();
        System.out.println("Task finishes.");
    }
}

class TakeQueue {
    Queue<String> qq = new LinkedList<>();
    Lock lockie = new ReentrantLock();
    Condition condition = lockie.newCondition();

    public void add(final String new_member, Thread t2) {

        /**
         * state: thread 2 state changes from WAITING to BLOCK after being notified,
         * because it is still waiting its chance to acquire the lock which will be
         * released by some other thread(thread 1) after finished this synchronized
         * block of code
         */
        lockie.lock();
        try {
            qq.add(new_member);
            System.out.println("Thread 2: " + t2.getState());
            condition.signalAll();
        } finally {
            lockie.unlock();
        }
    }

    public String getMember() {

        lockie.lock();
        try{
            while (qq.isEmpty()) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    System.out.println("Interrupted Exception!");
                }
            }
            // get (this) lock again
            return qq.remove();
        }finally{
            lockie.unlock();
        }
    }
}