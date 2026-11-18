import java.util.LinkedList;
import java.util.LinkedList;
import java.util.Queue;

class TokenBuffer {
    private Queue<Integer> buffer = new LinkedList<>();
    private final int capacity = 5;
    private int maximumSize = 0;
    private int consumedSum = 0;

    public synchronized void put(int token)
            throws InterruptedException {

        while (buffer.size() == capacity) {
            wait();
        }

        buffer.add(token);
        maximumSize = Math.max(maximumSize, buffer.size());

        System.out.println("Produced: " + token
                + " Buffer size: " + buffer.size());

        notifyAll();
    }

    public synchronized int take()
            throws InterruptedException {

        while (buffer.isEmpty()) {
            wait();
        }

        int token = buffer.remove();
        consumedSum += token;

        System.out.println("Consumed: " + token
                + " Buffer size: " + buffer.size());

        notifyAll();
        return token;
    }

    public synchronized int getMaximumSize() {
        return maximumSize;
    }

    public synchronized int getConsumedSum() {
        return consumedSum;
    }
}

public class TokenCounter {
    public static void main(String[] args)
            throws InterruptedException {

        TokenBuffer buffer = new TokenBuffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 20; i++) {
                    buffer.put(i);
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Runnable consumerTask = () -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    buffer.take();
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread consumer1 = new Thread(consumerTask);
        Thread consumer2 = new Thread(consumerTask);

        producer.start();
        consumer1.start();
        consumer2.start();

        producer.join();
        consumer1.join();
        consumer2.join();

        System.out.println("Total consumed sum: "
                + buffer.getConsumedSum());

        System.out.println("Maximum buffer size: "
                + buffer.getMaximumSize());

        System.out.println("All 20 tokens processed.");
    }
}