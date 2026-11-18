public class DaemonDemo {
    public static void main(String[] args)
            throws InterruptedException {

        Thread worker = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("Daemon working: " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("Daemon interrupted");
            }
        });

        worker.setDaemon(true);
        worker.start();

        System.out.println("Main thread finished.");
    }
}