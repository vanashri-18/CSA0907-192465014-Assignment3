class Service {
    private Storage storage = new Storage();

    void submitAnswer(String fileName, long sizeKb)
            throws SubmissionStorageException {
        storage.save(fileName, sizeKb);
    }
}

class Controller {
    private Service service = new Service();

    void handleSubmission(String fileName, long sizeKb) {
        try {
            service.submitAnswer(fileName, sizeKb);
        } catch (ExamException e) {
            System.out.println("Controller handled the error.");
            System.out.println("Student message: " + e.getMessage());
            System.out.println("Original cause: "
                    + e.getCause().getMessage());
        }
    }
}

public class PortalLayersDemo {
    public static void main(String[] args) {
        Controller controller = new Controller();

        controller.handleSubmission("answer01.pdf", 120);
        controller.handleSubmission("answer02.pdf", 800);
    }
}