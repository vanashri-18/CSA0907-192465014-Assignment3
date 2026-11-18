class ScannerStation extends Thread {
    private int scripts;

    ScannerStation(String name, int scripts) {
        super(name);
        this.scripts = scripts;
    }

    public void run() {
        try {
            System.out.println(getName() + " started");

            Thread.sleep(1000);

            System.out.println(getName() + " scanned "
                    + scripts + " scripts");
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrupted");
            Thread.currentThread().interrupt();
        }
    }
}

class RunnableStation implements Runnable {
    private String name;
    private int scripts;

    RunnableStation(String name, int scripts) {
        this.name = name;
        this.scripts = scripts;
    }

    public void run() {
        try {
            System.out.println(name + " started");
            Thread.sleep(1000);
            System.out.println(name + " scanned "
                    + scripts + " scripts");
        } catch (InterruptedException e) {
            System.out.println(name + " interrupted");
            Thread.currentThread().interrupt();
        }
    }
}

public class ThreadStations {
    public static void main(String[] args)
            throws InterruptedException {

        Thread[] stations = new Thread[4];

        stations[0] = new ScannerStation("Station 1", 10);
        stations[1] = new ScannerStation("Station 2", 20);

        stations[2] = new Thread(
                new RunnableStation("Station 3", 30));

        stations[3] = new Thread(
                new RunnableStation("Station 4", 40));

        for (Thread t : stations) {
            t.start();
        }

        for (Thread t : stations) {
            System.out.println(t.getName()
                    + " alive: " + t.isAlive());
        }

        for (Thread t : stations) {
            t.join();
        }

        System.out.println("All stations completed.");
        System.out.println("Total scripts scanned: 100");
    }
}