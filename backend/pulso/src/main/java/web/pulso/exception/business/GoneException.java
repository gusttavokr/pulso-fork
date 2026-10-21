package web.pulso.exception.business;

import org.springframework.http.HttpStatus;

public class GoneException extends BusinessException {

    public GoneException(String message) {
        super(message, HttpStatus.GONE);
    }
}