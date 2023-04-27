package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.dao.IncomeDAO;
import no.ntnu.idatt1002.dao.exception.DAOException;
import no.ntnu.idatt1002.model.economy.Income;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * This class is an implementation of the {@link IncomeDAO} interface, using
 * SQLite as the underlying data source.
 *
 * @see SqlDAO
 * @see IncomeDAO
 */
public final class SqlIncomeDAO extends SqlDAO implements IncomeDAO {

  private static final String INSERT_INCOME = """
                INSERT INTO income (userId, name, addDate, amount, createDate, shares)
                VALUES (?, ?, ?, ?, ?, ?);
            """;

  /**
   * {@inheritDoc}
   */
  @Override
  public Income create(long userId, String name, BigDecimal amount, Date date, int shares) {
    Date now = new Date();
    try(Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT_INCOME)) {
      statement.setLong(1, userId);
      statement.setString(2, name);
      statement.setLong(3, now.getTime());
      statement.setLong(4, amount.longValue());
      statement.setLong(5, date.getTime());
      statement.setInt(6, shares);
      statement.execute();
      try(ResultSet resultSet = statement.getGeneratedKeys()) {
        if(resultSet.next()) {
          long incomeId = resultSet.getInt(1);
          return new Income(incomeId, userId, amount, name, now, date, shares);
        }
      }
    } catch (SQLException e) {
      throw new DAOException(e);
    }
    return null;
  }

  private static final String REMOVE_INCOME = "DELETE FROM income WHERE incomeId = ?;";

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(long incomeId) {
    executePreparedStatement(REMOVE_INCOME, incomeId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Income find(Long filter) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Income> find(Collection<Long> filter) {
    Objects.requireNonNull(filter);
    if(filter.isEmpty()) throw new IllegalArgumentException("filter cannot be empty");
    String findStatement = buildFindStatement(filter.size());
    return executePreparedStatementList(findStatement, SqlIncomeDAO::buildIncome, filter.toArray());
  }

  private String buildFindStatement(int size) {
    return "SELECT * FROM income WHERE incomeId IN (?" + ", ?".repeat(Math.max(0, size)) + ");";
  }

  /**
   * Creates an income object from a ResultSet.
   *
   * @param   resultSet the ResultSet to retrieve data from
   * @return  a new Income object
   * @throws  SQLException if a database access error occurs
   */
  private static Income buildIncome(ResultSet resultSet) {
    try {
      long incomeId = resultSet.getLong("incomeId");
      long userId = resultSet.getLong("userId");
      Date addDate = resultSet.getDate("addDate");
      String name = resultSet.getString("name");
      BigDecimal amount = new BigDecimal(resultSet.getLong("amount"));
      Date createDate = resultSet.getDate("createDate");
      int shares = resultSet.getInt("shares");
      return new Income(incomeId, userId, amount, name, addDate, createDate, shares);
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  private static final String CREATE_EXPENSES = """
                CREATE TABLE IF NOT EXISTS income (
            	incomeId integer PRIMARY KEY AUTOINCREMENT,
            	userId integer NOT NULL,
            	addDate integer NOT NULL,
            	name text(32) NOT NULL,
            	amount integer NOT NULL,
            	createDate integer NOT NULL,
            	shares integer NOT NULL,
            	FOREIGN KEY (userId) REFERENCES income(userId)
            );""";

  /**
   * {@inheritDoc}
   */
  @Override
  public void init() {
    execute(CREATE_EXPENSES);
  }
}
