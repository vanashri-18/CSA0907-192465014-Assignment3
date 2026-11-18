

import java.io.*;

public class ManualReport {
    static int openHandles = 0;

    public static void main(String[] args) {
        String[] files = {"store1.txt", "store2.txt", "store3.txt"};
        String currentFile = "";

        BufferedReader reader = null;
        BufferedWriter writer = null;
        int total = 0;

        try {
            writer = new BufferedWriter(new FileWriter("manual_report.txt"));
            openHandles++;

            for (String file : files) {
                currentFile = file;
                System.out.println("Reading: " + file);

                reader = new BufferedReader(new FileReader(file));
                openHandles++;

                String line;
                while ((line = reader.readLine()) != null) {
                    total += Integer.parseInt(line);
                }

                reader.close();
                openHandles--;
                reader = null;
            }

            writer.write("Total Sales: " + total);
            System.out.println("Report generated successfully.");

        } catch (Exception e) {
            System.out.println("LOG: Failed file: " + currentFile);
            System.out.println("ERROR: " + e.getMessage());

        } finally {
            try {
                if (writer != null) {
                    writer.close();
                    openHandles--;
                }
            } catch (IOException e) {
                System.out.println("Writer closing error: " + e.getMessage());
            }

            System.out.println("Open handles remaining: " + openHandles);
        }
    }
}
