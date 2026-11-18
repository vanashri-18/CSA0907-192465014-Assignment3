public class RunVsStart {
    public static void main(String[] args) {

        Thread t = new Thread(() -> {
            System.out.println("Executing in: "
                    + Thread.currentThread().getName());
        });

        System.out.println("Calling run():");
        t.run();

        System.out.println("Calling start():");
        Thread t2 = new Thread(() -> {
            System.out.println("Executing in: "
                    + Thread.currentThread().getName());
        });

        t2.start();
    }
}