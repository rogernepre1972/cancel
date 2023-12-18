package br.com.desktop.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The class {@code BusinessException} is used to indicates generic business exceptions.
 * @author vinicius.juliani@desktop.com.br
 * @since 17/09/2020
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ExecutionLimitException extends RuntimeException {

    private String errorMessageCode;

    public ExecutionLimitException(String message) {
        super(message);
    }

    public ExecutionLimitException(String message, Throwable cause){
        super(message, cause);
    }

    public ExecutionLimitException(String message, String errorMessageCode) {
        super(message);
        this.errorMessageCode = errorMessageCode;
    }

    public ExecutionLimitException(String message, Throwable cause, String errorMessageCode) {
        super(message, cause);
        this.errorMessageCode = errorMessageCode;
    }

    public String getErrorMessageCode() {
        return errorMessageCode;
    }
}
