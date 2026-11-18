
import java.io.*;

public class TryWithResourcesReport {

    static int openHandles = 0;

    static class TrackedReader extends BufferedReader {
        TrackedReader(String file) throws IOException {
            super(new FileReader(file));
            openHandles++;
        }

        public void close() throws IOException {
            try {
                super.close();
            } finally {
                openHandles--;
            }
        }
    }

    static class TrackedWriter extends BufferedWriter {
        TrackedWriter(String file) throws IOException {
            super(new FileWriter(file));
            openHandles++;
        }

        public void close() throws IOException {
            try {
                super.close();
            } finally {
                openHandles--;
            }
        }
    }

    public static void main(String[] args) {
        String[] files = {"store1.txt", "store2.txt", "store3.txt"};
        int total = 0;

        try (TrackedWriter writer =
                 new TrackedWriter("safe_report.txt")) {

            for (String file : files) {
                System.out.println("Reading: " + file);

                try (TrackedReader reader = new TrackedReader(file)) {
                    String line;

                    while ((line = reader.readLine()) != null) {
                        total += Integer.parseInt(line);
                    }
                } catch (Exception e) {
                    System.out.println("Failed file: " + file);
                    throw e;
                }
            }

            writer.write("Total Sales: " + total);
            System.out.println("Report generated successfully.");
            System.out.println("Total Sales: " + total);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Open handles remaining: " + openHandles);
    }
}
