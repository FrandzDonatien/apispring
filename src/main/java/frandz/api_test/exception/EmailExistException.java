package frandz.api_test.exception;

public class EmailExistException extends Exception{

    public EmailExistException(String message){
        super(message);
    }
}
