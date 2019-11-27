public class Demo2_2 extends Thread
{
    public static void main(String args[]) throws InterruptedException
    {
        Thread mainThread = Thread.currentThread();
        System.out.println("Main:" + mainThread.getState());

        Thread expert = new Expert();
        System.out.println("Main: Expert please start working");
        expert.start();

        System.out.println("Main:" + mainThread.getState());

        Thread.sleep(1000);
        System.out.println("Expert:" + expert.getState());
        System.out.println("Main: Mining accident happens, all experts move");
        System.out.println("Main:" + mainThread.getState());

        expert.interrupt();
        System.out.println("Main:" + mainThread.getState());
        System.out.println("Expert:" + expert.getState());

        expert.join();
        System.out.println("Expert:" + expert.getState());
        System.out.println("Main:" + mainThread.getState());
        System.out.println("Main: Situation is under control now. Safe.");
    }
}

class Expert extends Thread {
    @Override
    public void run() 
    {
        Thread miner = new Miner();
        System.out.println("Expert: all miners go to work!");
        miner.start();
        try
        {
            miner.join();
        }catch(InterruptedException e)
        {
            System.out.println("Expert: Alert Received! I'm going to inform the miner on this!");
            System.out.println(super.getState());
            System.out.println("Miner: " + miner.getState());
            miner.interrupt();
        }
    }
}

class Miner extends Thread 
{
    @Override
    public void run() 
    {
        int a = 0;
        while(!interrupted())
        {
            a++;
            System.out.println("Miner: Mining diamonds: "+a);
            try
            {
                Thread.sleep(100);
            }catch(InterruptedException e)
            {
                System.out.println("Miner: Alert, all miners stop working...");
                break;
            }
        }
    }
}