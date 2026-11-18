import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class FaultTolerantProcessor {

    static final int TOTAL_FILES = 10000;
    static final Path DIRECTORY = Paths.get("scores");

    // Generate valid, corrupt and missing files
    static void prepareFiles() throws IOException {

        Files.createDirectories(DIRECTORY);

        int valid = 0;
        int corrupt = 0;
        int missing = 0;

        for (int i = 1; i <= TOTAL_FILES; i++) {

            Path file = DIRECTORY.resolve(
                String.format("script_%05d.txt", i));

            Files.deleteIfExists(file);

            if (i % 100 == 0) {
                missing++;
                continue;
            }

            if (i % 97 == 0) {
                Files.writeString(file, "INVALID_SCORE");
                corrupt++;
            } else {
                int score = 50 + (i % 51);
                Files.writeString(file, String.valueOf(score));
                valid++;
            }
        }

        System.out.println("Input files prepared:");
        System.out.println("Valid files: " + valid);
        System.out.println("Corrupt files: " + corrupt);
        System.out.println("Missing files: " + missing);
    }

    // Process files using a fixed thread pool
    static void processFiles(int poolSize)
            throws InterruptedException {

        ExecutorService executor =
            Executors.newFixedThreadPool(poolSize);

        ConcurrentLinkedQueue<String> failures =
            new ConcurrentLinkedQueue<>();

        List<Future<Boolean>> results = new ArrayList<>();

        long start = System.nanoTime();

        for (int i = 1; i <= TOTAL_FILES; i++) {

            final Path file = DIRECTORY.resolve(
                String.format("script_%05d.txt", i));

            Callable<Boolean> task = () -> {
                try {
                    String data = Files.readString(file).trim();

                    int score = Integer.parseInt(data);

                    if (score < 0 || score > 100) {
                        throw new IllegalArgumentException(
                            "Invalid score");
                    }

                    return true;

                } catch (Exception e) {
                    failures.add(file.getFileName()
                        + " : " + e.getClass().getSimpleName());

                    return false;
                }
            };

            results.add(executor.submit(task));
        }

        executor.shutdown();

        int success = 0;
        int failed = 0;
        int unhandled = 0;

        for (Future<Boolean> result : results) {
            try {
                if (result.get()) {
                    success++;
                } else {
                    failed++;
                }
            } catch (ExecutionException e) {
                unhandled++;
            }
        }

        long end = System.nanoTime();
        double time = (end - start) / 1_000_000.0;

        System.out.println("\nTHREAD POOL SIZE: " + poolSize);
        System.out.println("Successful files: " + success);
        System.out.println("Failed files: " + failed);
        System.out.println("Unhandled failures: " + unhandled);
        System.out.printf("Processing time: %.2f ms%n", time);

        System.out.println("Sample failure details:");

        int count = 0;
        for (String failure : failures) {
            if (count == 3) {
                break;
            }

            System.out.println(failure);
            count++;
        }

        System.out.println("--------------------------------");
    }

    public static void main(String[] args)
            throws IOException, InterruptedException {

        System.out.println("FAULT-TOLERANT SCORE PROCESSOR");
        prepareFiles();

        processFiles(1);
        processFiles(2);
        processFiles(4);
        processFiles(8);

        System.out.println("\nPROCESSING COMPLETED.");
    }
}