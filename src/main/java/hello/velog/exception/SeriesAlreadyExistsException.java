package hello.velog.exception;

public class SeriesAlreadyExistsException extends RuntimeException {
    public SeriesAlreadyExistsException(String message) {
        super(message);
    }
}