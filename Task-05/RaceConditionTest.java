public class RaceConditionTest {
    public static void main(String[] args)
            throws InterruptedException {

        for (int run = 1; run <= 10; run++) {
            Cinema cinema = new Cinema();
            Thread[] users = new Thread[100];

            for (int i = 0; i < 100; i++) {
                final int seat = i % 10;

                users[i] = new Thread(() ->
                    cinema.bookWithoutSync(seat)
                );

                users[i].start();
            }

            for (Thread t : users) {
                t.join();
            }

            System.out.println("Run " + run
                    + " - Seats sold: " + cinema.sold);
        }
    }
}