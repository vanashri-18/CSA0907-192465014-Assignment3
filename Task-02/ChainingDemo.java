import java.io.IOException;

class ChainingDemo {
    public static void main(String[] args) {
        try {
            try {
                throw new IOException("Disk quota exceeded");
            } catch (IOException e) {
                throw new RuntimeException(
                    "Could not store answer02.pdf", e
                );
            }
        } catch (RuntimeException e) {
            System.out.println("Original cause: "
                    + e.getCause().getMessage());

            System.out.println("\nFull chained stack trace:");
            e.printStackTrace();
        }
    }
}