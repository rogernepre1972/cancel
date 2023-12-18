package br.com.desktop.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
public class TechnicalException extends RuntimeException {

    private String errorMessageCode;

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(String message, Throwable cause){
        super(message, cause);
    }

    public TechnicalException(String message, String errorMessageCode) {
        super(message);
        this.errorMessageCode = errorMessageCode;
    }

    public TechnicalException(String message, Throwable cause, String errorMessageCode) {
        super(message, cause);
        this.errorMessageCode = errorMessageCode;
    }

    public String getErrorMessageCode() {
        return errorMessageCode;
    }
}
