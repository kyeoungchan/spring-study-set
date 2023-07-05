package hello.jdbc.repository.ex;

public class MyDuplicatedKeyException extends MyDbException {

    public MyDuplicatedKeyException() {
    }

    public MyDuplicatedKeyException(String message) {
        super(message);
    }

    public MyDuplicatedKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDuplicatedKeyException(Throwable cause) {
        super(cause);
    }
}
