package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.dao.SettlementDAO;
import no.ntnu.idatt1002.dao.exception.DAOException;
import no.ntnu.idatt1002.data.economy.Settlement;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public final class SqlSettlementDAO extends SqlDAO implements SettlementDAO {

    private static final String INSERT_SETTLEMENT = "INSERT INTO settlements (userId, name, createDate, ended, deleted) VALUES (?, ?, ?, ?, ?);";

    @Override
    public Settlement create(String name, long userId) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_SETTLEMENT)) {
            statement.setLong(1, userId);
            statement.setString(2, name);
            statement.setLong(3, System.currentTimeMillis());
            statement.setBoolean(4, false);
            statement.setBoolean(5, false);
            statement.execute();
            try(ResultSet resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    long settlementId = resultSet.getInt(1);
                    return new Settlement(settlementId, userId, name, new Date(), false, false);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return null;
    }

    private static final String DELETE_SETTLEMENT = """
                UPDATE settlements SET deleted = 1 WHERE settlementId = ?;
            """;

    @Override
    public void delete(long settlementId) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_SETTLEMENT)) {
            statement.setLong(1, settlementId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static final String FIND_BY_USER = """
                SELECT * FROM settlementUsers
                JOIN settlements s on settlementUsers.settlementId = s.settlementId
                WHERE settlementUsers.userId = ?;
                """;

    @Override
    public List<Settlement> findByUser(long userId) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USER)) {
            statement.setLong(1, userId);
            try(ResultSet resultSet = statement.executeQuery()) {
                List<Settlement> list = new ArrayList<>();
                while(resultSet.next()) {
                    list.add(buildSettlement(resultSet));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static final String INSERT_SETTLEMENT_USER = """
                INSERT INTO settlementUsers (settlementId, userId)
                VALUES (?, ?);
            """;

    @Override
    public void addMember(long settlementId, long userId) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_SETTLEMENT_USER)) {
            statement.setLong(1, settlementId);
            statement.setLong(2, userId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static final String DELETE_SETTLEMENT_USER = """
                DELETE FROM settlementUsers WHERE settlementId = ? AND userId = ?;
            """;

    @Override
    public void removeMember(long settlementId, long userId) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_SETTLEMENT_USER)) {
            statement.setLong(1, settlementId);
            statement.setLong(2, userId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static final String INSERT_SETTLEMENT_EXPENSE = """
                INSERT INTO settlementExpenses (settlementId, expenseId)
                VALUES (?, ?);
            """;

    @Override
    public void addExpense(long settlementId, long expenseId) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_SETTLEMENT_EXPENSE)) {
            statement.setLong(1, settlementId);
            statement.setLong(2, expenseId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static final String FIND_MEMBERS = "SELECT * FROM settlementUsers WHERE settlementId = ?;";

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

    @Override
    public Settlement find(Long filter) {
        Objects.requireNonNull(filter);
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ONE_ID)) {
            statement.setLong(1, filter);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    Settlement settlement = buildSettlement(resultSet);
                    getExpenses(settlement.getId()).forEach(settlement::addExpense);
                    return settlement;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Settlement> find(Collection<Long> filter) {
        throw new UnsupportedOperationException();
    }

    private static Settlement buildSettlement(ResultSet resultSet) throws SQLException {
        long settlementId = resultSet.getLong("settlementId");
        String name = resultSet.getString("name");
        long userId = resultSet.getLong("userId");
        Date date = resultSet.getDate("createDate");
        boolean ended = resultSet.getBoolean("ended");
        boolean deleted = resultSet.getBoolean("deleted");
        Settlement settlement = new Settlement(settlementId, userId, name, date, ended, deleted);
        getMembers(settlementId).forEach(settlement::addMember);
        getExpenses(settlement.getId()).forEach(settlement::addExpense);
        return settlement;
    }

    private static final String CREATE_SETTLEMENTS = """
                CREATE TABLE IF NOT EXISTS settlements (
            	settlementId integer PRIMARY KEY AUTOINCREMENT,
            	name text(32) NOT NULL,
            	userId integer NOT NULL,
            	createDate integer NOT NULL,
            	ended integer NOT NULL,
            	deleted integer NOT NULL,
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

    @Override
    public void init() {
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            statement.addBatch(CREATE_SETTLEMENTS);
            statement.addBatch(CREATE_SETTLEMENT_USER_LINK);
            statement.addBatch(CREATE_SETTLEMENT_EXPENSE_LINK);
            statement.executeBatch();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
