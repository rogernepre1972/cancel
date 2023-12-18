package br.com.desktop.infrastructure.web.api.exception;

import br.com.desktop.infrastructure.web.api.response.exception.HandlerExceptionResponse;
import br.com.desktop.infrastructure.web.api.response.exception.ValidationErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomerCancellationValidationHandler {

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<HandlerExceptionResponse>> handleValidationException(WebExchangeBindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ValidationErrorResponse> validationErrors = new ArrayList<>();


        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            ValidationErrorResponse errorResponse = new ValidationErrorResponse(fieldError.getField(), fieldError.getDefaultMessage());
            validationErrors.add(errorResponse);
        }


        HandlerExceptionResponse exceptionResponse = new HandlerExceptionResponse("400", "Invalid Request Body", validationErrors);
        return Mono.just(ResponseEntity.badRequest().body(exceptionResponse));
    }

}
