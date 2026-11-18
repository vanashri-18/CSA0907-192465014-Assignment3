public class DeadlockDemo {
    public static void main(String[] args) {

        final Object walletA = new Object();
        final Object walletB = new Object();

        Thread user1 = new Thread(() -> {
            synchronized (walletA) {
                System.out.println("User 1 locked Wallet A");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                synchronized (walletB) {
                    System.out.println("User 1 transferred money");
                }
            }
        });

        Thread user2 = new Thread(() -> {
            synchronized (walletB) {
                System.out.println("User 2 locked Wallet B");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                synchronized (walletA) {
                    System.out.println("User 2 transferred money");
                }
            }
        });

        user1.start();
        user2.start();
    }
}