package web.pulso.exception.business;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import web.pulso.exception.ApplicationException;


@Getter
public abstract class BusinessException extends ApplicationException {

    private final HttpStatus status;

    protected BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}