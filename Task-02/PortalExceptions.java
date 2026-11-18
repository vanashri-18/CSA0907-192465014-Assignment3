class ExamWindowClosedException extends ExamException {
    ExamWindowClosedException(String message) {
        super(message);
    }
}

class CourseNotRegisteredException extends ExamException {
    CourseNotRegisteredException(String message) {
        super(message);
    }
}

public class PortalExceptions {
    public static void main(String[] args) {
        try {
            throw new ExamWindowClosedException(
                "Exam window is closed"
            );
        } catch (ExamException e) {
            System.out.println(e.getMessage());
        }

        try {
            throw new CourseNotRegisteredException(
                "Student is not registered"
            );
        } catch (ExamException e) {
            System.out.println(e.getMessage());
        }
    }
}