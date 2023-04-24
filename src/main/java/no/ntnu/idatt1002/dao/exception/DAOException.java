package no.ntnu.idatt1002.dao.exception;

/**
 * The DAOException is an exception that can be thrown during the
 * operation of Database Access Object classes.
 */
public class DAOException extends RuntimeException {

  /**
   * Constructs a new DAOException.
   *
   * @param   string the detail message
   */
  public DAOException(String string) {
    super(string);
  }

  /**
   * Constructs a new DAOException.
   *
   * @param   throwable the cause
   */
  public DAOException(Throwable throwable) {
    super(throwable);
  }
}