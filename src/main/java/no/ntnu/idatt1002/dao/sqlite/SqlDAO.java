package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.FairPay;
import no.ntnu.idatt1002.dao.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class defines the set of common methods for DAOs interacting
 * with SQLite.
 *
 * @see DAO
 */
public abstract class SqlDAO {

  /**
   * Returns the URL for this SQL instance.
   * @return  the URL for this SQL instance
   */
  private static String getURL() {
    return "jdbc:sqlite:" + (FairPay.test ? "test" : "main") + ".db";
  }

  /**
   * Attempts to establish a connection to the database located
   * at the specified URL, with an appropriate driver registered by the JDBC.
   *
   * @return  a connection to the URL
   * @throws  SQLException if the access is denied or the URL is invalid
   * @see     DriverManager
   */
  protected static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(getURL());
  }
}