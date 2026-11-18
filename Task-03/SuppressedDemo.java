
import java.io.*;

public class SuppressedDemo {
    static class MyResource implements AutoCloseable {
        public void close() throws IOException {
            throw new IOException("Resource closing failed");
        }
    }

    public static void main(String[] args) {
        try (MyResource r = new MyResource()) {
            throw new NumberFormatException("Invalid sales value");
        } catch (Exception e) {
            System.out.println("Main exception: " + e.getMessage());

            for (Throwable s : e.getSuppressed()) {
                System.out.println("Suppressed exception: " + s.getMessage());
            }
        }
    }
}
