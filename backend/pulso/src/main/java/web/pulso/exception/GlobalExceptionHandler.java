package web.pulso.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import web.pulso.exception.business.BusinessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // -------- BUSINESS --------
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(
            BusinessException ex,
            HttpServletRequest request) {

        HttpStatus status = ex.getStatus();

        ErrorResponse response = new ErrorResponse(
                status.value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(response);
    }

    // -------- INFRASTRUCTURE --------
    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<ErrorResponse> handleInfrastructure(
            InfrastructureException ex,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse response = new ErrorResponse(
                status.value(),
                "Internal server error",
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(response);
    }

    // -------- GENERIC --------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse response = new ErrorResponse(
                status.value(),
                "Unexpected error occurred",
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(response);
    }

}