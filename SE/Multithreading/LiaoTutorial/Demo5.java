/**Thread safety
 * For immutable class, the read-only characteristic makes it thread safe. While using Arraylist class, to achieve 
 * thread safe means to only read without changing the value
 */

public class Demo5{
    public static void main(String[] args) throws InterruptedException{
        Counter c1 = new Counter();
        Counter c2 = new Counter();

        Thread t1 = new Thread(new Runnable(){
        
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    c1.add(i*Integer.MAX_VALUE);
                }
            }
        });
        
        Thread t2 = new Thread(new Runnable(){
        
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    c1.sub(i);
                }
            }
        });

        Thread t3 = new Thread(new Runnable(){
        
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    c2.add(i);
                }
            }
        });
        
        Thread t4 = new Thread(new Runnable(){
        
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    c2.sub(i);
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("c1: "+c1.get());
        System.out.println("c2: "+c2.get());
    }
}

/** Thread-safe class with object lock (Non class lock, which means only one thread could access the instance each time )*/
class Counter{
    private int count = 0;

    public void add(int n){
        synchronized(this){
            try{
                count += n;
            }catch(StackOverflowError e){
                System.out.println("Stack Overflow");
            }
        }
    }

    public void sub(int n){
        synchronized(this){
            count -= n;
        }
    }

    public int get(){
        return count;
    }
}