package no.ntnu.idatt1002.dao.exception;

/**
 * The DAOException is an exception that can be thrown during the
 * operation of Database Access Object classes.
 */
public class DAOException extends RuntimeException {

    /**
     * Instantiates a new DAOException.
     * @param   string the detail message
     */
    public DAOException(String string) {
        super(string);
    }
}