package frandz.api_test.exception;

public class ExpiredTokenException extends Exception{

    public ExpiredTokenException(String message) {
        super(message);
    }

}
