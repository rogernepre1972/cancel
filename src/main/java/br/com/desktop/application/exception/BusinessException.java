package br.com.desktop.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BusinessException extends RuntimeException {

    private String errorMessageCode;
    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(String message, Throwable cause){
        super(message, cause);
    }

    public BusinessException(String message, String errorMessageCode) {
        super(message);
        this.errorMessageCode = errorMessageCode;
    }

    public BusinessException(String message, Throwable cause, String errorMessageCode) {
        super(message, cause);
        this.errorMessageCode = errorMessageCode;
    }

    public String getErrorMessageCode() {
        return errorMessageCode;
    }
}
