/**
 * Concurrency describes the ability to handle multiple tasks at the same time while
 * Parallelism describes one possible method to achieve that through multi-processing
 * An improvisation from the original @author Liao Xuefeng
 * Thread start(), run(), sleep() and join()
 */
class Demo1 {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("\nA new thread is noobing...");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("lololol");
            }
        }
    }

    /**
     * @param t a new thread to be created
     */

    public static void main(String args[]) throws InterruptedException {
        Thread t = new Demo1.MyThread();
        System.out.println("\nMain Thread: start");
        t.start(); // it creates thread, which starts together with the main thread
        // t.run(); this does not create any new thread.
        t.join(); // current thread will wait for t to join
        System.out.println("\nMain Thread: end");
    }
}
