package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.dao.ExpenseDAO;
import no.ntnu.idatt1002.dao.exception.DAOException;
import no.ntnu.idatt1002.data.economy.Expense;
import no.ntnu.idatt1002.data.economy.ExpenseType;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * This class is an implementation of the {@link ExpenseDAO} interface, using
 * SQLite as the underlying data source.
 *
 * @see SqlDAO
 * @see ExpenseDAO
 */
public final class SqlExpenseDAO extends SqlDAO implements ExpenseDAO {

  private static final String INSERT_EXPENSE = """
                INSERT INTO expenses (userId, name, addDate, type, amount, createDate, shares)
                VALUES (?, ?, ?, ?, ?, ?, ?);
            """;

  /**
   * {@inheritDoc}
   */
  @Override
  public Expense create(long userId, ExpenseType type, String name,
                        BigDecimal amount, Date date, int shares) {
    Date now = new Date();
    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT_EXPENSE)) {
      statement.setLong(1, userId);
      statement.setString(2, name);
      statement.setLong(3, now.getTime());
      statement.setInt(4, type.getNumber());
      statement.setLong(5, amount.longValue());
      statement.setLong(6, date.getTime());
      statement.setLong(7, shares);
      statement.execute();
      try (ResultSet resultSet = statement.getGeneratedKeys()) {
        if (resultSet.next()) {
          long expenseId = resultSet.getInt(1);
          return new Expense(expenseId, userId, now, type, name, amount, date, shares);
        }
      }
    } catch (SQLException e) {
      throw new DAOException(e);
    }
    return null;
  }

  private static final String REMOVE_EXPENSE = "DELETE FROM expenses WHERE expenseId = ?;";

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(long expenseId) {
    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(REMOVE_EXPENSE)) {
      statement.setLong(1, expenseId);
      statement.execute();
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Expense find(Long filter) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Expense> find(Collection<Long> filter) {
    Objects.requireNonNull(filter);
    if (filter.isEmpty()) throw new IllegalArgumentException("filter cannot be empty");
    String findStatement = buildFindStatement(filter.size());
    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(findStatement)) {
      int i = 1;
      for (long f : filter) statement.setLong(i++, f);
      List<Expense> list = new ArrayList<>();
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          list.add(buildExpense(resultSet));
        }
      }
      return list;
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  private String buildFindStatement(int size) {
    return "SELECT * FROM expenses WHERE expenseId IN (?" + ", ?".repeat(Math.max(0, size)) + ");";
  }

  /**
   * Creates an expense object from a ResultSet.
   *
   * @param   resultSet the ResultSet to retrieve data from
   * @return  a new Expense object
   * @throws  SQLException if a database access error occurs
   */
  private static Expense buildExpense(ResultSet resultSet) throws SQLException {
    long expenseId = resultSet.getLong("expenseId");
    long userId = resultSet.getLong("userId");
    Date addDate = resultSet.getDate("addDate");
    ExpenseType type = ExpenseType.fromNumber(resultSet.getInt("type"));
    String name = resultSet.getString("name");
    BigDecimal amount = new BigDecimal(resultSet.getLong("amount"));
    Date createDate = resultSet.getDate("createDate");
    int shares = resultSet.getInt("shares");
    return new Expense(expenseId, userId, addDate, type, name, amount, createDate, shares);
  }

  private static final String CREATE_EXPENSES = """
            CREATE TABLE IF NOT EXISTS expenses (
            expenseId integer PRIMARY KEY AUTOINCREMENT,
            userId integer NOT NULL,
            addDate integer NOT NULL,
            name text(32),
            type text(32) NOT NULL,
            amount integer NOT NULL,
            createDate integer NOT NULL,
            shares integer NOT NUll,
            FOREIGN KEY (userId) REFERENCES users(userId)
            );""";

  /**
   * {@inheritDoc}
   */
  @Override
  public void init() {
    try (Connection connection = getConnection();
        Statement statement = connection.createStatement()) {
      statement.addBatch(CREATE_EXPENSES);
      statement.executeBatch();
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }
}
