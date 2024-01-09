package coba.atm.exception;

public class NoDefaultConstructorException extends Exception {

    private static final String ERR_DEFAULT = "Please provide no arg constructor entity class when using CsvReaderUtil";

    public NoDefaultConstructorException(){
        super(ERR_DEFAULT);
    }
}
