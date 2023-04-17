package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.dao.ExpenseDAO;
import no.ntnu.idatt1002.dao.exception.DAOException;
import no.ntnu.idatt1002.data.economy.Expense;
import no.ntnu.idatt1002.data.economy.ExpenseType;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.*;

public final class SqlExpenseDAO extends SqlDAO implements ExpenseDAO {

    private static final String INSERT_EXPENSE = """
                INSERT INTO expenses (userId, name, type, amount, createDate)
                VALUES (?, ?, ?, ?, ?);
            """;

    @Override
    public Expense create(long userId, ExpenseType type, String name, BigDecimal amount, Date date) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_EXPENSE)) {
            statement.setLong(1, userId);
            statement.setString(2, name);
            statement.setInt(3, type.getCategoryNumber());
            statement.setLong(4, amount.longValue());
            statement.setLong(5, date.getTime());
            statement.execute();
            try(ResultSet resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    long expenseId = resultSet.getInt(1);
                    return new Expense(expenseId, userId, type, name, amount, date);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return null;
    }

    @Override
    public Expense find(Long filter) {
        return null;
    }

    @Override
    public List<Expense> find(Collection<Long> filter) {
        Objects.requireNonNull(filter);
        if(filter.isEmpty()) throw new IllegalArgumentException("filter cannot be empty");
        String findStatement = buildFindStatement(filter.size());
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(findStatement)) {
            int i = 1;
            for(long f : filter) statement.setLong(i++, f);
            List<Expense> list = new ArrayList<>();
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
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

    private static Expense buildExpense(ResultSet resultSet) throws SQLException {
        long expenseId = resultSet.getLong("expenseId");
        long userId = resultSet.getLong("userId");
        ExpenseType type = ExpenseType.getCategoryByCategoryNumber(resultSet.getInt("type"));
        String name = resultSet.getString("name");
        BigDecimal amount = new BigDecimal(resultSet.getLong("amount"));
        Date createDate = resultSet.getDate("createDate");
        return new Expense(expenseId, userId, type, name, amount, createDate);
    }

    private static final String CREATE_EXPENSES = """
                CREATE TABLE IF NOT EXISTS expenses (
            	expenseId integer PRIMARY KEY AUTOINCREMENT,
            	userId integer NOT NULL,
            	name text(32),
            	type text(32) NOT NULL,
            	amount integer NOT NULL,
            	createDate integer NOT NULL,
            	FOREIGN KEY (userId) REFERENCES users(userId)
            );""";

    @Override
    public void init() {
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            statement.addBatch(CREATE_EXPENSES);
            statement.executeBatch();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
