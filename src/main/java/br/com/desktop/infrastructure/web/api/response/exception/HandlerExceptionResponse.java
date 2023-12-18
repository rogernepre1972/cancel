package br.com.desktop.infrastructure.web.api.response.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Data
public class HandlerExceptionResponse {

    @JsonProperty("code")
    @Valid
    private String code;

    @JsonProperty("description")
    private String description;

    @JsonProperty("errors")
    private List<ValidationErrorResponse> errors;

    public HandlerExceptionResponse(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public HandlerExceptionResponse(String code, String description, List<ValidationErrorResponse> errors) {
        this.code = code;
        this.description = description;
        this.errors = errors;
    }
}
