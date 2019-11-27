/**
 * interrupt()
 */
public class Demo2 {
    public static void main(String args[]) throws InterruptedException {
        Thread t = new MyThread();
        System.out.println("Main Starts.");
        t.start();
        Thread.sleep(100);
        t.interrupt();
        t.join();
        System.out.println("Main Ends.");
    }

}

class MyThread extends Thread {
    @Override
    public void run() {
        int a = 0;
        while (!isInterrupted()) {
            a++;
            System.out.println(a + "-This is my thread.");
        }
        System.out.println("Current thread is Interruped");
    }
}