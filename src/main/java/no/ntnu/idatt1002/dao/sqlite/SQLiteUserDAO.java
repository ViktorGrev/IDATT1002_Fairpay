package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.dao.UserDAO;
import no.ntnu.idatt1002.dao.exception.AuthException;
import no.ntnu.idatt1002.data.economy.Budget;
import no.ntnu.idatt1002.data.User;

import java.sql.*;
import java.util.Collection;
import java.util.List;

public final class SQLiteUserDAO extends SQLiteDAO implements UserDAO {

    // Implementation test

    private static final String CREATE_USERS = """
                CREATE TABLE IF NOT EXISTS users (
            	userId integer PRIMARY KEY AUTOINCREMENT,
            	username text(16) UNIQUE NOT NULL,
            	password text(60) NOT NULL,
            	registerDate integer NOT NULL,
            	phoneNumber integer NOT NULL,
            	budgetId integer NOT NULL
            );""";

    @Override
    public void setup() {
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            statement.execute(CREATE_USERS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String INSERT_PERSON = """
                INSERT INTO users (username, password, registerDate, phoneNumber, budgetId)
                VALUES (?, ?, ?, ?, ?);
            """;

    @Override
    public boolean insert(User person) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_PERSON)) {
            statement.setString(1, person.getUsername());
            statement.setString(2, person.getPassword());
            statement.setLong(3, person.getRegisterDate().getTime());
            statement.setLong(4, person.getPhoneNumber());
            statement.setInt(5, 0);
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String FIND_ONE = """
                SELECT * FROM users WHERE userId = ?;
            """;

    @Override
    public User find(long id) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ONE)) {
            statement.setLong(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    return new User(
                            resultSet.getLong("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getDate("date"),
                            resultSet.getInt("phoneNumber")
                            );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<User> find(Collection<Long> ids) {
        return null;
    }

    private static final String USER_AUTH = """
                SELECT * FROM users WHERE username = ? AND password = ?;
            """;

    @Override
    public long authenticate(String username, String password) throws AuthException {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(USER_AUTH)) {
            String encrypted = "";
            statement.setString(1, username);
            statement.setString(2, encrypted);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    return resultSet.getLong("userId");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new AuthException("No user found");
    }
}