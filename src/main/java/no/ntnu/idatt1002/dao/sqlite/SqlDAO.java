package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.FairPay;
import no.ntnu.idatt1002.dao.DAO;
import no.ntnu.idatt1002.dao.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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

  /**
   * Executes a query.
   *
   * @param   sql the query
   */
  protected void execute(String sql) {
    try (Connection connection = getConnection();
         Statement statement = connection.createStatement()) {
      statement.execute(sql);
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  /**
   * Executes an array of queries in a batch.
   *
   * @param   sql the queries
   */
  protected void executeBatch(String... sql) {
    try (Connection connection = getConnection();
         Statement statement = connection.createStatement()) {
      for (String s : sql)
        statement.addBatch(s);
      statement.executeBatch();
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  /**
   * Creates a prepared statement and sets the params with
   * indexes matching the input order.
   *
   * @param   sql the query
   * @param   params the params
   * @throws  DAOException if an error occurs
   */
  protected void executePreparedStatement(String sql, Object... params) {
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      int i = 1;
      for (Object param : params)
        statement.setObject(i++, param);
      statement.execute();
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  /**
   * Executes a query where the result set is transformed by the
   * specified function.
   *
   * @param   sql the query
   * @param   function the mapping function
   * @param   param the statement parameters
   * @return  the object
   * @param   <T> the row type
   */
  protected static <T> T executePreparedStatement(String sql, Function<ResultSet, T> function,
                                                  Object param) {
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setObject(1, param);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return function.apply(resultSet);
        }
      }
      return null;
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  /**
   * Executes a query where each list entry is a result set
   * transformed by the specified function.
   *
   * @param   sql the query
   * @param   function the mapping function
   * @param   params the statement parameters
   * @return  a list of objects
   * @param   <T> the row type
   */
  protected static <T> List<T> executePreparedStatementList(String sql, Function<ResultSet, T> function,
                                                            Object... params) {
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      int i = 1;
      for (Object param : params)
        statement.setObject(i++, param);
      List<T> list = new ArrayList<>();
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          T value = function.apply(resultSet);
          list.add(value);
        }
      }
      return list;
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }
}