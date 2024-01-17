package br.com.desktop.domain.model.oss.availability;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private boolean success;
    private List<String> errors;

    public ErrorResponse(boolean success, List<String> errors) {
        this.success = success;
        this.errors = errors;
    }

    public ErrorResponse() {

    }
}
