package implementation;

import java.util.LinkedList;
import java.util.Queue;

/** wait(), notify(), and notifyall()
 * 
 * A thread comes into WAITING state when calling the method wait() in a synchronized block of through its key and wait to be notified,
 * then another thread blocked in front of its own synchronized block with the same key will grab it and start executing.
 * After the other thread completes its task, it shoud notify the waiting thread to come into BLOCKED state and waiting for the reassigning
 * of the lock by the scheduler, however, to notify does not mean to release the lock obj immediately, because the lock will only be released 
 * after the synchronized block is fully executed. That's why there is a intermidian state BLOCKED between the WAITING and RUNNABLE
*/

public class Imple1 {
    public static void main(final String[] args) throws InterruptedException {
        // System.out.println();
        TaskQueue queue = new TaskQueue();

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

class TaskQueue {
    Queue<String> q = new LinkedList<>();

    public void add(final String new_member, Thread t2) {

        /** state: thread 2 state changes from WAITING to BLOCK after being notified, 
            * because it is still waiting its chance to acquire the lock which will be released
            * by some other thread(thread 1) after finished this synchronized block of code */

        synchronized (this) {
            q.add(new_member);
            System.out.println("Thread 2: " + t2.getState());
            // notify all waiting thread (if use notify, one thread will be chosen and come to acquire the lock.)
            // However, this can cause some pressure to the scheduler when some of threads which should not be notified 
            // get notified
            this.notifyAll();
        }
    }

    public String getMember() {

        synchronized (this) {
            while (q.isEmpty()) {
                try {
                    // the current lock obj, which is this instance, will be released and the other
                    // thread might get it after this line
                    this.wait();
                    // after being notified, threads are blocked and waiting for another thread to finish
                    // its synchronized block and release (this) lock, later the code starts after the wait()
                    // line, where it is still within the while loop
                } catch (InterruptedException e) {
                    System.out.println("Interrupted Exception!");
                }
            }
            // get (this) lock again
            System.out.println("Thread 2: " + Thread.currentThread().getState());
            return q.remove();
        }
    }
}