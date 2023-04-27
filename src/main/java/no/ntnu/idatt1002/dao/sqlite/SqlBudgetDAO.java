package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.dao.BudgetDAO;
import no.ntnu.idatt1002.dao.exception.DAOException;
import no.ntnu.idatt1002.model.economy.Budget;
import no.ntnu.idatt1002.model.economy.ExpenseType;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * This class is an implementation of the {@link BudgetDAO} interface, using
 * SQLite as the underlying data source.
 *
 * @see SqlDAO
 * @see BudgetDAO
 */
public final class SqlBudgetDAO extends SqlDAO implements BudgetDAO {

  private static final String INSERT_TYPE = """
                INSERT OR REPLACE INTO budgets (groupId, type, amount)
                VALUES (?, ?, ?);
            """;

  /**
   * {@inheritDoc}
   */
  @Override
  public void addType(long groupId, ExpenseType type, BigDecimal amount) {
    try(Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT_TYPE)) {
      statement.setLong(1, groupId);
      statement.setLong(2, type.getNumber());
      statement.setLong(3, amount.longValue());
      statement.execute();
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  private static final String FIND_ID = "SELECT * FROM budgets WHERE groupId = ?;";

  /**
   * {@inheritDoc}
   */
  @Override
  public Budget find(Long filter) {
    Objects.requireNonNull(filter);
    try(Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_ID)) {
      statement.setLong(1, filter);
      try(ResultSet resultSet = statement.executeQuery()) {
        Budget budget = new Budget();
        while(resultSet.next()) {
          ExpenseType type = ExpenseType.fromNumber(resultSet.getInt("type"));
          BigDecimal amount = BigDecimal.valueOf(resultSet.getLong("amount"));
          budget.add(type, amount);
        }
        return budget;
      }
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Budget> find(Collection<Long> filter) {
    throw new UnsupportedOperationException();
  }

  private static final String CREATE_BUDGET = """
                CREATE TABLE IF NOT EXISTS budgets (
            	groupId integer NOT NULL,
            	type integer NOT NULL,
            	amount integer NOT NULL,
            	FOREIGN KEY (groupId) REFERENCES groups(groupId),
            	PRIMARY KEY (groupId, type)
            );""";

  /**
   * {@inheritDoc}
   */
  @Override
  public void init() {
    try(Connection connection = getConnection();
        Statement statement = connection.createStatement()) {
      statement.addBatch(CREATE_BUDGET);
      statement.executeBatch();
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }
}
