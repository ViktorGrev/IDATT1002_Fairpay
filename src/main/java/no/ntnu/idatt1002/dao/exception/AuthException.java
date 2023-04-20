package no.ntnu.idatt1002.dao.exception;

/**
 * The AuthException is thrown if a login attempt fails.
 */
public class AuthException extends RuntimeException {

    /**
     * Initializes a new auth exception.
     * @param   string the exception message
     */
    public AuthException(String string) {
        super(string);
    }
}