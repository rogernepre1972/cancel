package br.com.desktop.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class GenericResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private GenericResponse(Builder<? extends T> builder) {
        this.message = builder.message;
        this.data = builder.data;
    }

    public static class Builder<T> {


        private String message;
        private T data;

        public Builder() {
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public GenericResponse build() {
            return new GenericResponse<T>(this);
        }
    }
}
