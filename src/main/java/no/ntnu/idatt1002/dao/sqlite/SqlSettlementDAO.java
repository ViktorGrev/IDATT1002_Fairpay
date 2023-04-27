package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.dao.SettlementDAO;
import no.ntnu.idatt1002.dao.exception.DAOException;
import no.ntnu.idatt1002.model.economy.Settlement;

import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * This class is an implementation of the {@link SettlementDAO} interface, using
 * SQLite as the underlying data source.
 *
 * @see SqlDAO
 * @see SettlementDAO
 */
public final class SqlSettlementDAO extends SqlDAO implements SettlementDAO {

  private static final String INSERT_SETTLEMENT = "INSERT INTO settlements (userId, name, createDate, ended) VALUES (?, ?, ?, ?);";

  /**
   * {@inheritDoc}
   */
  @Override
  public Settlement create(String name, long userId) {
    try(Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT_SETTLEMENT)) {
      statement.setLong(1, userId);
      statement.setString(2, name);
      statement.setLong(3, System.currentTimeMillis());
      statement.setBoolean(4, false);
      statement.execute();
      try(ResultSet resultSet = statement.getGeneratedKeys()) {
        if(resultSet.next()) {
          long settlementId = resultSet.getInt(1);
          return new Settlement(settlementId, userId, name, new Date(), false);
        }
      }
    } catch (SQLException e) {
      throw new DAOException(e);
    }
    return null;
  }

  private static final String DELETE_SETTLEMENT = "DELETE FROM settlements WHERE settlementId = ?;";
  private static final String DELETE_SETTLEMENT_USERS = "DELETE FROM settlementUsers WHERE settlementId = ?;";
  private static final String DELETE_SETTLEMENT_EXPENSES = "DELETE FROM settlementExpenses WHERE settlementId = ?;";

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(long settlementId) {
    executePreparedStatement(DELETE_SETTLEMENT, settlementId);
    executePreparedStatement(DELETE_SETTLEMENT_USERS, settlementId);
    executePreparedStatement(DELETE_SETTLEMENT_EXPENSES, settlementId);
  }

  private static final String FIND_BY_USER = """
                SELECT * FROM settlementUsers
                JOIN settlements s on settlementUsers.settlementId = s.settlementId
                WHERE settlementUsers.userId = ?;
                """;

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Settlement> findByUser(long userId) {
    return executePreparedStatementList(FIND_BY_USER, SqlSettlementDAO::buildSettlement, userId);
  }

  private static final String INSERT_SETTLEMENT_USER = """
                INSERT INTO settlementUsers (settlementId, userId)
                VALUES (?, ?);
            """;

  /**
   * {@inheritDoc}
   */
  @Override
  public void addMember(long settlementId, long userId) {
    executePreparedStatement(INSERT_SETTLEMENT_USER, settlementId, userId);
  }

  private static final String DELETE_SETTLEMENT_USER = """
                DELETE FROM settlementUsers WHERE settlementId = ? AND userId = ?;
            """;

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeMember(long settlementId, long userId) {
    executePreparedStatement(DELETE_SETTLEMENT_USER, settlementId, userId);
  }

  private static final String INSERT_SETTLEMENT_EXPENSE = """
                INSERT INTO settlementExpenses (settlementId, expenseId)
                VALUES (?, ?);
            """;

  /**
   * {@inheritDoc}
   */
  @Override
  public void addExpense(long settlementId, long expenseId) {
    executePreparedStatement(INSERT_SETTLEMENT_EXPENSE, settlementId, expenseId);
  }

  private static final String UPDATE_ENDED = "UPDATE settlements SET ended = ? WHERE settlementId = ?;";

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnded(long settlementId, boolean ended) {
    executePreparedStatement(UPDATE_ENDED, ended ? 1 : 0, settlementId);
  }

  private static final String FIND_MEMBERS = "SELECT * FROM settlementUsers WHERE settlementId = ?;";

  /**
   * Returns a list of members within a settlement specified by its ID.
   *
   * @param   settlementId the settlement ID
   * @return  a list of member IDs within a settlement
   */
  private static List<Long> getMembers(long settlementId) {
    List<Long> users = new ArrayList<>();
    try(Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_MEMBERS)) {
      statement.setLong(1, settlementId);
      try(ResultSet resultSet = statement.executeQuery()) {
        while(resultSet.next()) {
          users.add(resultSet.getLong("userId"));
        }
      }
    } catch (SQLException e) {
      throw new DAOException(e);
    }
    return users;
  }

  private static final String FIND_EXPENSES = "SELECT * FROM settlementExpenses WHERE settlementId = ?;";

  /**
   * Returns a list of expenses within a settlement specified by its ID.
   *
   * @param   settlementId the settlement ID
   * @return  a list of expenses within a settlement
   */
  private static List<Long> getExpenses(long settlementId) {
    List<Long> expenses = new ArrayList<>();
    try(Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_EXPENSES)) {
      statement.setLong(1, settlementId);
      try(ResultSet resultSet = statement.executeQuery()) {
        while(resultSet.next()) {
          expenses.add(resultSet.getLong("expenseId"));
        }
      }
    } catch (SQLException e) {
      throw new DAOException(e);
    }
    return expenses;
  }

  private static final String FIND_ONE_ID = "SELECT * FROM settlements WHERE settlementId = ?;";

  /**
   * {@inheritDoc}
   */
  @Override
  public Settlement find(Long filter) {
    Objects.requireNonNull(filter);
    return executePreparedStatement(FIND_ONE_ID, SqlSettlementDAO::buildSettlement, filter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Settlement> find(Collection<Long> filter) {
    throw new UnsupportedOperationException();
  }

  /**
   * Creates a settlement object from a ResultSet.
   *
   * @param   resultSet the ResultSet to retrieve data from
   * @return  a new Settlement object
   * @throws  SQLException if a database access error occurs
   */
  private static Settlement buildSettlement(ResultSet resultSet) {
    try {
      long settlementId = resultSet.getLong("settlementId");
      String name = resultSet.getString("name");
      long userId = resultSet.getLong("userId");
      Date date = resultSet.getDate("createDate");
      boolean ended = resultSet.getBoolean("ended");
      Settlement settlement = new Settlement(settlementId, userId, name, date, ended);
      getMembers(settlementId).forEach(settlement::addMember);
      getExpenses(settlementId).forEach(settlement::addExpense);
      return settlement;
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  private static final String CREATE_SETTLEMENTS = """
                CREATE TABLE IF NOT EXISTS settlements (
            	settlementId integer PRIMARY KEY AUTOINCREMENT,
            	name text(32) NOT NULL,
            	userId integer NOT NULL,
            	createDate integer NOT NULL,
            	ended integer NOT NULL,
            	FOREIGN KEY (userId) REFERENCES users(userId)
            );""";

  private static final String CREATE_SETTLEMENT_USER_LINK = """
                CREATE TABLE IF NOT EXISTS settlementUsers (
                settlementId integer NOT NULL,
            	userId integer NOT NULL,
            	FOREIGN KEY (settlementId) REFERENCES expenses(expenseId),
            	FOREIGN KEY (userId) REFERENCES users(userId),
            	PRIMARY KEY (settlementId, userId)
            );""";

  private static final String CREATE_SETTLEMENT_EXPENSE_LINK = """
                CREATE TABLE IF NOT EXISTS settlementExpenses (
                settlementId integer NOT NULL,
            	expenseId integer NOT NULL,
            	FOREIGN KEY (settlementId) REFERENCES expenses(expenseId),
            	FOREIGN KEY (expenseId) REFERENCES expenses(expenseId),
            	PRIMARY KEY (settlementId, expenseId)
            );""";

  /**
   * {@inheritDoc}
   */
  @Override
  public void init() {
    executeBatch(CREATE_SETTLEMENTS, CREATE_SETTLEMENT_USER_LINK, CREATE_SETTLEMENT_EXPENSE_LINK);
  }
}
