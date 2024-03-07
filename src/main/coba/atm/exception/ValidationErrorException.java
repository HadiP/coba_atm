package coba.atm.exception;

public class ValidationErrorException extends Exception {

    public ValidationErrorException() {
    }

    public ValidationErrorException(String message){
        super(message);
    }
}
