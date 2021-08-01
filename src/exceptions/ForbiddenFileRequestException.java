package exceptions;

public class ForbiddenFileRequestException extends Exception {

    public ForbiddenFileRequestException(String message) {
        super(message);
    }
}
