class Cinema {
    boolean[] seats = new boolean[10];
    int sold = 0;

    // Without synchronization
    void bookWithoutSync(int seat) {
        if (!seats[seat]) {
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            seats[seat] = true;
            sold++;
        }
    }

    // Synchronized method
    synchronized void bookWithMethod(int seat) {
        if (!seats[seat]) {
            seats[seat] = true;
            sold++;
        }
    }

    // Synchronized block
    void bookWithBlock(int seat) {
        synchronized (this) {
            if (!seats[seat]) {
                seats[seat] = true;
                sold++;
            }
        }
    }
}

public class SeatBookingDemo {

    static long runTest(int mode) throws InterruptedException {
        Cinema cinema = new Cinema();
        Thread[] users = new Thread[100];

        long start = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            final int seat = i % 10;

            users[i] = new Thread(() -> {
                if (mode == 1)
                    cinema.bookWithoutSync(seat);
                else if (mode == 2)
                    cinema.bookWithMethod(seat);
                else
                    cinema.bookWithBlock(seat);
            });

            users[i].start();
        }

        for (Thread t : users) {
            t.join();
        }

        long end = System.nanoTime();

        System.out.println("Seats sold: " + cinema.sold);

        return (end - start) / 1_000_000;
    }

    public static void main(String[] args)
            throws InterruptedException {

        System.out.println("WITHOUT SYNCHRONIZATION");
        System.out.println("Time: " + runTest(1) + " ms");

        System.out.println("\nSYNCHRONIZED METHOD");
        System.out.println("Time: " + runTest(2) + " ms");

        System.out.println("\nSYNCHRONIZED BLOCK");
        System.out.println("Time: " + runTest(3) + " ms");
    }
}