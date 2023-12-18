package br.com.desktop.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotApplicableException extends RuntimeException {

    public NotApplicableException() {
    }

    public NotApplicableException(String message) {
        super(message);
    }

    public NotApplicableException(String message, Throwable cause) {
        super(message, cause);
    }
}
