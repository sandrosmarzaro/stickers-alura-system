package exceptions;

public class HttpClientRequestException extends RuntimeException {
    public HttpClientRequestException(String message) {
        super(message);
    }
}
