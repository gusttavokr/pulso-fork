package web.pulso.exception.business;
import org.springframework.http.HttpStatus;

public class TooManyRequestsException extends BusinessException {

    public TooManyRequestsException(String message) {
        super(message, HttpStatus.TOO_MANY_REQUESTS);
    }
}