package edu.wctc.distjava.veki.vv.bookwebapp.exceptions;

/**
 *
 * @author Veki Vulic 
 */
public class InvalidInputException extends IllegalArgumentException{
    
    private static final String MESSAGE = "Error: Values"
            + " passed in cannot be null/empty. Number values cannot be zero "
            + "or less";
    /**
     * 
     */
    public InvalidInputException() {
        super(MESSAGE);
    }
    /**
     * 
     * @param s 
     */
    public InvalidInputException(String s) {
        super(s);
    }
    /**
     * 
     * @param message
     * @param cause 
     */
    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * 
     * @param cause 
     */
    public InvalidInputException(Throwable cause) {
        super(MESSAGE, cause);
    }
    
}
