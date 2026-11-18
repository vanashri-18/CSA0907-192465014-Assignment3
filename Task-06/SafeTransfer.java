public class SafeTransfer {
    public static void main(String[] args) {

        final Object walletA = new Object();
        final Object walletB = new Object();

        Runnable transfer = () -> {
            synchronized (walletA) {
                System.out.println(
                    Thread.currentThread().getName()
                    + " locked Wallet A");

                synchronized (walletB) {
                    System.out.println(
                        Thread.currentThread().getName()
                        + " locked Wallet B");

                    System.out.println(
                        Thread.currentThread().getName()
                        + " transferred money");
                }
            }
        };

        Thread user1 = new Thread(transfer, "User 1");
        Thread user2 = new Thread(transfer, "User 2");

        user1.start();
        user2.start();
    }
}