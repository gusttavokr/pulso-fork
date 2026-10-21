package web.pulso.exception.business;

import org.springframework.http.HttpStatus;

public class LegalRestrictionException extends BusinessException {

    public LegalRestrictionException(String message) {
        super(message, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
}