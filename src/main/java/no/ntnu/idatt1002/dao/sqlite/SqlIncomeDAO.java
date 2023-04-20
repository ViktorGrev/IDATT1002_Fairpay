package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.dao.IncomeDAO;
import no.ntnu.idatt1002.dao.exception.DAOException;
import no.ntnu.idatt1002.data.economy.Income;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.*;

public final class SqlIncomeDAO extends SqlDAO implements IncomeDAO {

    private static final String INSERT_INCOME = """
                INSERT INTO income (userId, name, amount, createDate, shares)
                VALUES (?, ?, ?, ?, ?);
            """;

    @Override
    public Income create(long userId, String name, BigDecimal amount, Date date, int shares) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_INCOME)) {
            statement.setLong(1, userId);
            statement.setString(2, name);
            statement.setLong(3, amount.longValue());
            statement.setLong(4, date.getTime());
            statement.setInt(5, shares);
            statement.execute();
            try(ResultSet resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    long incomeId = resultSet.getInt(1);
                    return new Income(incomeId, amount, name, userId, date, shares);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return null;
    }

    @Override
    public Income find(Long filter) {
        return null;
    }

    @Override
    public List<Income> find(Collection<Long> filter) {
        Objects.requireNonNull(filter);
        if(filter.isEmpty()) throw new IllegalArgumentException("filter cannot be empty");
        String findStatement = buildFindStatement(filter.size());
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(findStatement)) {
            int i = 1;
            for(long f : filter) statement.setLong(i++, f);
            List<Income> list = new ArrayList<>();
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    list.add(buildIncome(resultSet));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private String buildFindStatement(int size) {
        return "SELECT * FROM income WHERE incomeId IN (?" + ", ?".repeat(Math.max(0, size)) + ");";
    }

    private static Income buildIncome(ResultSet resultSet) throws SQLException {
        long incomeId = resultSet.getLong("incomeId");
        long userId = resultSet.getLong("userId");
        String name = resultSet.getString("name");
        BigDecimal amount = new BigDecimal(resultSet.getLong("amount"));
        Date createDate = resultSet.getDate("createDate");
        int shares = resultSet.getInt("shares");
        return new Income(incomeId, amount, name, userId, createDate, shares);
    }

    private static final String CREATE_EXPENSES = """
                CREATE TABLE IF NOT EXISTS income (
            	incomeId integer PRIMARY KEY AUTOINCREMENT,
            	userId integer NOT NULL,
            	name text(32) NOT NULL,
            	amount integer NOT NULL,
            	createDate integer NOT NULL,
            	shares integer NOT NULL,
            	FOREIGN KEY (userId) REFERENCES income(userId)
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
