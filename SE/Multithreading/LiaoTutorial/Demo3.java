import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 * Daemon Thread The JVM shut-down does not care about the Daemon Thread, thus,
 * I/O operation within this thread is considered to be unsafe but the thread is
 * running until finishes its task it could be used to do monitoring
 */
public class Demo3 extends Thread {
    public static void main(String args[]) throws InterruptedException {
        Thread myProgram = new MyProgram();
        Thread mainProgram = currentThread();
        System.out.println(mainProgram.getId() + " Main: Program Start");
        myProgram.start();
        myProgram.join();
        Thread.sleep(60000);
        System.out.println(mainProgram.getId() + " Main: The computer is shut down");
    }
}

class MyProgram extends Thread {

    @Override
    public void run() {
        Thread monitor = new Monitor();
        System.out.println(currentThread().getId() + " MyProgram: Start");
        monitor.setDaemon(true);
        monitor.start();
        System.out.println(currentThread().getId() + " MyProgram: end");
    }
}

class Monitor extends Thread {
    @Override
    public void run() {
        System.out.println(currentThread().getId() + " Monitor: Start");
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        while (!interrupted()) {
            String systemLoadInfo = "system load: " + Math.round(osBean.getSystemLoadAverage() * 100) / 100.0+" ";
            String processorsInfo = "Processors:  " + osBean.getAvailableProcessors();
            System.out.print(systemLoadInfo);
            System.out.print(processorsInfo);
            System.out.print("\b".repeat((systemLoadInfo + processorsInfo).length()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("hit");
            }
        }
    }
}
