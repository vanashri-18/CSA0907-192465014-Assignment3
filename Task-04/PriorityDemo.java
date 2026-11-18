public class PriorityDemo {
    public static void main(String[] args)
            throws InterruptedException {

        Thread t1 = new Thread(() ->
            System.out.println("Low priority thread completed"));

        Thread t2 = new Thread(() ->
            System.out.println("Normal priority thread completed"));

        Thread t3 = new Thread(() ->
            System.out.println("High priority thread completed"));

        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.NORM_PRIORITY);
        t3.setPriority(Thread.MAX_PRIORITY);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("All priority threads completed.");
    }
}