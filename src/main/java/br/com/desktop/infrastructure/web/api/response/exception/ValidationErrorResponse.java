package br.com.desktop.infrastructure.web.api.response.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidationErrorResponse {

    @JsonProperty("field")
    @Valid
    private String field;

    @JsonProperty("errorMessage")
    private String errorMessage;
}
