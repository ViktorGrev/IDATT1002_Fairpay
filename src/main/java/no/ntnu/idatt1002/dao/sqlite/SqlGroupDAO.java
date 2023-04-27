package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.dao.GroupDAO;
import no.ntnu.idatt1002.dao.exception.DAOException;
import no.ntnu.idatt1002.model.Group;
import no.ntnu.idatt1002.model.Invite;
import no.ntnu.idatt1002.model.User;

import java.sql.Date;
import java.sql.*;
import java.util.*;

/**
 * This class is an implementation of the {@link GroupDAO} interface, using
 * SQLite as the underlying data source.
 *
 * @see SqlDAO
 * @see GroupDAO
 */
public final class SqlGroupDAO extends SqlDAO implements GroupDAO {

  private static final String FIND_ONE_ID = "SELECT * FROM groups WHERE groupId = ?;";

  /**
   * {@inheritDoc}
   */
  @Override
  public Group find(Long filter) {
    Objects.requireNonNull(filter);
    return executePreparedStatement(FIND_ONE_ID, SqlGroupDAO::buildGroup, filter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Group> find(Collection<Long> filter) {
    Objects.requireNonNull(filter);
    if (filter.isEmpty()) throw new IllegalArgumentException("filter cannot be empty");
    String findStatement = buildFindStatement(filter.size());
    return executePreparedStatementList(findStatement, SqlGroupDAO::buildGroup, filter.toArray());
  }

  private String buildFindStatement(int size) {
    return "SELECT * FROM groups WHERE groupId IN (?" + ", ?".repeat(Math.max(0, size)) + ");";
  }

  private static final String INSERT_GROUP = "INSERT INTO groups (groupName) VALUES (?);";

  /**
   * {@inheritDoc}
   */
  @Override
  public Group create(String name) {
    Group.validateName(name);
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(INSERT_GROUP, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, name);
      statement.execute();
      try (ResultSet resultSet = statement.getGeneratedKeys()) {
        if (resultSet.next()) {
          long groupId = resultSet.getInt(1);
          return new Group(groupId, name);
        }
      }
    } catch (SQLException e) {
      throw new DAOException(e);
    }
    return null;
  }

  private static final String SET_NAME = "UPDATE groups SET groupName = ? WHERE groupId = ?;";

  /**
   * {@inheritDoc}
   */
  @Override
  public void setName(long groupId, String name) {
    Group.validateName(name);
    executePreparedStatement(SET_NAME, name, groupId);
  }

  private static final String ADD_MEMBER = "INSERT INTO groupUsers (groupId, userId) VALUES (?, ?);";

  /**
   * {@inheritDoc}
   */
  @Override
  public void addMember(long groupId, long userId) {
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(ADD_MEMBER)) {
      statement.setLong(1, groupId);
      statement.setLong(2, userId);
      statement.execute();
    } catch (SQLException e) {
      if(e.getErrorCode() == 19) throw new DAOException("User is already a member");
      e.printStackTrace();
      throw new DAOException(e);
    }
  }

  private static final String REMOVE_MEMBER = "DELETE FROM groupUsers WHERE groupId = ? AND userId = ?;";

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeMember(long groupId, long userId) {
    executePreparedStatement(REMOVE_MEMBER, groupId, userId);
  }

  private static final String INSERT_GROUP_EXPENSE = """
                INSERT INTO groupExpenses (groupId, expenseId)
                VALUES (?, ?);
            """;

  /**
   * {@inheritDoc}
   */
  @Override
  public void addExpense(long groupId, long expenseId) {
    executePreparedStatement(INSERT_GROUP_EXPENSE, groupId, expenseId);
  }

  private static final String REMOVE_GROUP_EXPENSE = "DELETE FROM groupExpenses WHERE groupId = ? AND expenseId = ?;";

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeExpense(long groupId, long expenseId) {
    executePreparedStatement(REMOVE_GROUP_EXPENSE, groupId, expenseId);
  }

  private static final String INSERT_GROUP_INCOME = """
                INSERT INTO groupIncome (groupId, incomeId)
                VALUES (?, ?);
            """;

  /**
   * {@inheritDoc}
   */
  @Override
  public void addIncome(long groupId, long incomeId) {
    executePreparedStatement(INSERT_GROUP_INCOME, groupId, incomeId);
  }

  private static final String REMOVE_GROUP_INCOME = "DELETE FROM groupIncome WHERE groupId = ? AND incomeId = ?;";

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeIncome(long groupId, long incomeId) {
    executePreparedStatement(REMOVE_GROUP_INCOME, groupId, incomeId);
  }

  private static final String INSERT_RECEIVED_INCOME = """
                INSERT INTO receivedIncome (incomeId, userId)
                VALUES (?, ?);
            """;

  /**
   * {@inheritDoc}
   */
  @Override
  public void setReceivedIncome(long incomeId, long userId) {
    executePreparedStatement(INSERT_RECEIVED_INCOME, incomeId, userId);
  }

  private static final String REMOVE_RECEIVED_INCOME = "DELETE FROM receivedIncome WHERE userId = ? AND incomeId = ?;";

  /**
   * {@inheritDoc}
   */
  @Override
  public void unsetReceivedIncome(long incomeId, long userId) {
    executePreparedStatement(REMOVE_RECEIVED_INCOME, incomeId, userId);
  }

  private static final String INSERT_PAID_EXPENSE = """
                INSERT INTO paidExpenses (expenseId, userId)
                VALUES (?, ?);
            """;

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPaidExpense(long expenseId, long userId) {
    executePreparedStatement(INSERT_PAID_EXPENSE, expenseId, userId);
  }

  private static final String REMOVE_PAID_EXPENSE = "DELETE FROM paidExpenses WHERE userId = ? AND expenseId = ?;";

  /**
   * {@inheritDoc}
   */
  @Override
  public void unsetPaidExpense(long expenseId, long userId) {
    executePreparedStatement(REMOVE_PAID_EXPENSE, userId, expenseId);
  }

  private static final String ADD_INVITE = """
                INSERT INTO groupInvites (groupId, senderId, targetId, sendDate)
                VALUES (?, ?, ?, ?);
            """;

  /**
   * {@inheritDoc}
   */
  @Override
  public Invite addInvite(long groupId, long senderId, long targetId) {
    try(Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(ADD_INVITE)) {
      statement.setLong(1, groupId);
      statement.setLong(2, senderId);
      statement.setLong(3, targetId);
      statement.setLong(4, System.currentTimeMillis());
      statement.execute();
      return new Invite(groupId, senderId, targetId, new Date(System.currentTimeMillis()));
    } catch (SQLException e) {
      if(e.getErrorCode() == 19) {
        throw new DAOException("Invite has already been sent");
      }
      throw new DAOException(e);
    }
  }

  private static final String REMOVE_INVITE = "DELETE FROM groupInvites WHERE groupId = ? AND targetId = ?;";

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeInvite(long groupId, long userId) {
    executePreparedStatement(REMOVE_INVITE, groupId, userId);
  }

  private static final String FIND_INVITES = "SELECT * FROM groupInvites WHERE groupId = ?;";

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Invite> getInvites(long groupId) {
    return Collections.unmodifiableList(executePreparedStatementList(FIND_INVITES,
            SqlGroupDAO::buildInvite, groupId));
  }

  private static final String FIND_INVITES_BY_USER = "SELECT * FROM groupInvites WHERE targetId = ?;";

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Invite> getInvitesByUser(long userId) {
    return Collections.unmodifiableList(executePreparedStatementList(FIND_INVITES_BY_USER,
            SqlGroupDAO::buildInvite, userId));
  }

  private static final String FIND_MEMBERS = """
                    SELECT * FROM groupUsers
                    JOIN users u on groupUsers.userId = u.userId
                    WHERE groupId = ?;
                    """;

  /**
   * Returns a list of users in the group specified by the group id.
   * @param   groupId the group id
   * @return  a list of users in the group specified by the group id
   */
  private static List<User> getMembers(long groupId) {
    return Collections.unmodifiableList(executePreparedStatementList(FIND_MEMBERS,
            SqlUserDAO::build, groupId));
  }

  private static final String FIND_PAID_EXPENSES = """
                    SELECT * FROM groupExpenses
                    JOIN paidExpenses ON paidExpenses.expenseId = groupExpenses.expenseId
                    WHERE groupId = ?;
                    """;

  /**
   * Returns a map of expenses and the users who have paid them
   * @param   groupId the group ID
   * @return  a map of expenses and the users who have paid them
   */
  private static Map<Long, List<Long>> getPaidExpenses(long groupId) {
    Map<Long, List<Long>> expenses = new HashMap<>();
    try(Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_PAID_EXPENSES)) {
      statement.setLong(1, groupId);
      try(ResultSet resultSet = statement.executeQuery()) {
        while(resultSet.next()) {
          long userId = resultSet.getLong("userId");
          long expenseId = resultSet.getLong("expenseId");
          if(!expenses.containsKey(expenseId)) {
            expenses.put(expenseId, new ArrayList<>());
          }
          expenses.get(expenseId).add(userId);
        }
      }
    } catch (SQLException e) {
      throw new DAOException(e);
    }
    return expenses;
  }

  private static final String FIND_RECEIVED_INCOME = """
                    SELECT * FROM groupIncome
                    JOIN receivedIncome ON receivedIncome.incomeId = groupIncome.incomeId
                    WHERE groupId = ?;
                    """;

  /**
   * Returns a map of incomes and the users who have received them
   * @param   groupId the group ID
   * @return  a map of incomes and the users who have received them
   */
  private static Map<Long, List<Long>> getReceivedIncome(long groupId) {
    Map<Long, List<Long>> received = new HashMap<>();
    try(Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_RECEIVED_INCOME)) {
      statement.setLong(1, groupId);
      try(ResultSet resultSet = statement.executeQuery()) {
        while(resultSet.next()) {
          long userId = resultSet.getLong("userId");
          long incomeId = resultSet.getLong("incomeId");
          if(!received.containsKey(incomeId)) {
            received.put(incomeId, new ArrayList<>());
          }
          received.get(incomeId).add(userId);
        }
      }
    } catch (SQLException e) {
      throw new DAOException(e);
    }
    return received;
  }

  private static final String FIND_EXPENSES = "SELECT * FROM groupExpenses WHERE groupId = ?;";

  /**
   * Returns a list of expenses within a group.
   * @param   groupId the group ID
   * @return  a list of expenses within a group
   */
  private static List<Long> getExpenses(long groupId) {
    List<Long> expenses = new ArrayList<>();
    try(Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_EXPENSES)) {
      statement.setLong(1, groupId);
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

  private static final String FIND_INCOME = "SELECT * FROM groupIncome WHERE groupId = ?;";

  /**
   * Returns a list of incomes within a group.
   * @param   groupId the group ID
   * @return  a list of incomes within a group
   */
  private static List<Long> getIncome(long groupId) {
    List<Long> incomeIds = new ArrayList<>();
    try(Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_INCOME)) {
      statement.setLong(1, groupId);
      try(ResultSet resultSet = statement.executeQuery()) {
        while(resultSet.next()) {
          incomeIds.add(resultSet.getLong("incomeId"));
        }
      }
    } catch (SQLException e) {
      throw new DAOException(e);
    }
    return incomeIds;
  }

  private static final String FIND_ONE_BY_USER = """
                    SELECT * FROM groupUsers
                    JOIN groups ON groupUsers.groupId = groups.groupId
                    WHERE userId = ?
                    LIMIT 1;
                    """;

  /**
   * {@inheritDoc}
   */
  @Override
  public Group findByUser(long userId) {
    return executePreparedStatement(FIND_ONE_BY_USER, SqlGroupDAO::buildGroup, userId);
  }

  /**
   * Creates a group object based on data provided by the database.
   * @param   resultSet the ResultSet providing the data
   * @return  a group object
   * @throws  SQLException if a database error occurs
   */
  private static Group buildGroup(ResultSet resultSet) {
    try {
      long groupId = resultSet.getLong("groupId");
      String groupName = resultSet.getString("groupName");
      Group group = new Group(groupId, groupName);
      getMembers(groupId).forEach(group::addMember);
      getExpenses(groupId).forEach(group::addExpense);
      getIncome(groupId).forEach(group::addIncome);
      getPaidExpenses(groupId).forEach((aLong, longs) -> longs.forEach(l -> group.addPaidExpense(aLong, l)));
      getReceivedIncome(groupId).forEach((aLong, longs) -> longs.forEach(l -> group.addReceivedIncome(aLong, l)));
      return group;
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  /**
   * Creates an invitation object based on data provided by the database.
   * @param   resultSet the ResultSet providing the data
   * @return  an invitation object
   * @throws  SQLException if a database error occurs
   */
  private static Invite buildInvite(ResultSet resultSet) {
    try {
      long groupId = resultSet.getLong("groupId");
      long senderId = resultSet.getLong("senderId");
      long targetId = resultSet.getLong("targetId");
      Date sendDate = resultSet.getDate("sendDate");
      return new Invite(groupId, senderId, targetId, sendDate);
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  private static final String CREATE_GROUPS = """
                CREATE TABLE IF NOT EXISTS groups (
            	groupId integer PRIMARY KEY AUTOINCREMENT,
            	groupName text(32) NOT NULL
            );""";

  private static final String CREATE_GROUP_LINK = """
                CREATE TABLE IF NOT EXISTS groupUsers (
                groupId integer NOT NULL,
            	userId integer NOT NULL,
            	FOREIGN KEY (groupId) REFERENCES groups(groupId),
            	FOREIGN KEY (userId) REFERENCES users(userId),
            	PRIMARY KEY (groupId, userId)
            );""";

  private static final String CREATE_GROUP_EXPENSE_LINK = """
                CREATE TABLE IF NOT EXISTS groupExpenses (
                groupId integer NOT NULL,
            	expenseId integer NOT NULL,
            	FOREIGN KEY (groupId) REFERENCES groups(groupId),
            	FOREIGN KEY (expenseId) REFERENCES expenses(expenseId),
            	PRIMARY KEY (groupId, expenseId)
            );""";

  private static final String CREATE_GROUP_INCOME_LINK = """
                CREATE TABLE IF NOT EXISTS groupIncome (
                groupId integer NOT NULL,
            	incomeId integer NOT NULL,
            	FOREIGN KEY (groupId) REFERENCES groups(groupId),
            	FOREIGN KEY (incomeId) REFERENCES income(incomeId),
            	PRIMARY KEY (groupId, incomeId)
            );""";

  private static final String CREATE_RECEIVED_INCOME_LINK = """
                CREATE TABLE IF NOT EXISTS receivedIncome (
                userId integer NOT NULL,
            	incomeId integer NOT NULL,
            	FOREIGN KEY (userId) REFERENCES users(userId),
            	FOREIGN KEY (incomeId) REFERENCES income(incomeId),
            	PRIMARY KEY (userId, incomeId)
            );""";

  private static final String CREATE_PAID_EXPENSE_LINK = """
                CREATE TABLE IF NOT EXISTS paidExpenses (
                userId integer NOT NULL,
            	expenseId integer NOT NULL,
            	FOREIGN KEY (userId) REFERENCES users(userId),
            	FOREIGN KEY (expenseId) REFERENCES expenses(expenseId),
            	PRIMARY KEY (userId, expenseId)
            );""";

  private static final String CREATE_GROUP_INVITES = """
                CREATE TABLE IF NOT EXISTS groupInvites (
                groupId integer NOT NULL,
                senderId integer NOT NULL,
            	targetId integer NOT NULL,
            	sendDate integer NOT NULL,
            	FOREIGN KEY (groupId) REFERENCES groups(groupId),
            	FOREIGN KEY (senderId) REFERENCES users(userId),
            	FOREIGN KEY (targetId) REFERENCES users(userId),
            	PRIMARY KEY (groupId, targetId)
            );""";

  /**
   * {@inheritDoc}
   */
  @Override
  public void init() {
    executeBatch(CREATE_GROUPS,
            CREATE_GROUP_LINK,
            CREATE_PAID_EXPENSE_LINK,
            CREATE_GROUP_EXPENSE_LINK,
            CREATE_GROUP_INCOME_LINK,
            CREATE_RECEIVED_INCOME_LINK,
            CREATE_GROUP_INVITES);
  }
}