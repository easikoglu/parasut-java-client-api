package exception;

/**
 * @author erhanasikoglu
 */
public class ParasutHttpClientException extends RuntimeException {

    public ParasutHttpClientException(String message, Throwable cause) {
        super(message, cause);
    }
}