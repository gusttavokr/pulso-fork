package web.pulso.exception.business;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends BusinessException {

    public UnprocessableEntityException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}