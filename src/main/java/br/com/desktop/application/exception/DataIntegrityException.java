package br.com.desktop.application.exception;



public class DataIntegrityException extends RuntimeException {

    private String errorMessageCode;
    public DataIntegrityException(String message) {
        super(message);
    }
    public DataIntegrityException(String message, Throwable cause){
        super(message, cause);
    }

    public DataIntegrityException(String message, String errorMessageCode) {
        super(message);
        this.errorMessageCode = errorMessageCode;
    }

    public DataIntegrityException(String message, Throwable cause, String errorMessageCode) {
        super(message, cause);
        this.errorMessageCode = errorMessageCode;
    }

    public String getErrorMessageCode() {
        return errorMessageCode;
    }


}
