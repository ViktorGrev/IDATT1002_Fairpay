package no.ntnu.idatt1002.dao.sqlite;

import at.favre.lib.crypto.bcrypt.BCrypt;
import no.ntnu.idatt1002.dao.UserDAO;
import no.ntnu.idatt1002.dao.exception.AuthException;
import no.ntnu.idatt1002.data.User;

import java.sql.*;
import java.util.Collection;
import java.util.List;

public final class SQLiteUserDAO extends SQLiteDAO implements UserDAO {

    private static final String CREATE_USERS = """
                CREATE TABLE IF NOT EXISTS users (
            	userId integer PRIMARY KEY AUTOINCREMENT,
            	username text(16) UNIQUE NOT NULL,
            	password text(60) NOT NULL,
            	registerDate integer NOT NULL,
            	phoneNumber integer NOT NULL,
            	groupId integer NOT NULL
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
                INSERT INTO users (username, password, registerDate, phoneNumber)
                VALUES (?, ?, ?, ?);
            """;

    @Override
    public boolean insert(User user) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_PERSON)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setLong(3, user.getRegisterDate().getTime());
            statement.setLong(4, user.getPhoneNumber());
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String FIND_ONE_ID = """
                SELECT * FROM users WHERE userId = ?;
            """;

    @Override
    public User find(long userId) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ONE_ID)) {
            statement.setLong(1, userId);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    return new User(
                            resultSet.getLong("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getDate("registerDate"),
                            resultSet.getInt("phoneNumber"));
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

    private static final String FIND_ONE_NAME = """
                SELECT * FROM users WHERE username = ?;
            """;

    @Override
    public long authenticate(String username, String password) throws AuthException {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ONE_NAME)) {
            statement.setString(1, username);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), resultSet.getString("password"));
                    if(result.verified) {
                        return resultSet.getLong("userId");
                    } else {
                        throw new AuthException("Invalid password");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new AuthException("User does not exist");
    }
}