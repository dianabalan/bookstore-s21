package exceptions;

public class InexistentItemException extends Exception{

    public InexistentItemException(String message) {
        super(message);
    }
}
