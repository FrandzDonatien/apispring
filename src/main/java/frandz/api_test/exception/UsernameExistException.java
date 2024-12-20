package frandz.api_test.exception;

public class UsernameExistException extends Exception{

    public UsernameExistException(String message){
        super(message);
    }
}
