package br.com.desktop.infrastructure.web.api.exception;

import br.com.desktop.application.exception.BusinessException;
import br.com.desktop.application.exception.DataIntegrityException;
import br.com.desktop.application.exception.NotFoundException;
import br.com.desktop.application.exception.TechnicalException;
import br.com.desktop.infrastructure.web.api.response.exception.HandlerExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomersCancellationControllerException {

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<HandlerExceptionResponse> dataIntegrityException(DataIntegrityException exception) {
        var handlerExceptionResponse = new HandlerExceptionResponse(exception.getErrorMessageCode(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(handlerExceptionResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<HandlerExceptionResponse> businessException(BusinessException exception) {
        var handlerExceptionResponse = new HandlerExceptionResponse(exception.getErrorMessageCode(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerExceptionResponse);
    }

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<HandlerExceptionResponse> technicalException(TechnicalException exception) {
        var handlerExceptionResponse = new HandlerExceptionResponse(exception.getErrorMessageCode(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(handlerExceptionResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HandlerExceptionResponse> noContentException(TechnicalException exception) {
        var handlerExceptionResponse = new HandlerExceptionResponse(exception.getErrorMessageCode(),exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(handlerExceptionResponse);
    }



}
