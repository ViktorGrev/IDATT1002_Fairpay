package no.ntnu.idatt1002.dao.sqlite;

import at.favre.lib.crypto.bcrypt.BCrypt;
import no.ntnu.idatt1002.dao.UserDAO;
import no.ntnu.idatt1002.dao.exception.AuthException;
import no.ntnu.idatt1002.dao.exception.DAOException;
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
            	phoneNumber integer NOT NULL
            );""";

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup() {
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            statement.execute(CREATE_USERS);
        } catch (SQLException e) {
            throw new DAOException(ERROR_MSG);
        }
    }

    private static final String INSERT_PERSON = """
                INSERT INTO users (username, password, registerDate, phoneNumber)
                VALUES (?, ?, ?, ?);
            """;

    /**
     * {@inheritDoc}
     */
    @Override
    public User create(String username, String password, long phoneNumber) {
        if(username.isBlank()) throw new IllegalArgumentException("Username cannot be empty");
        if(password.isBlank()) throw new IllegalArgumentException("Password cannot be empty");
        Date date = new Date(System.currentTimeMillis());
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_PERSON)) {
            String encryptedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            statement.setString(1, username);
            statement.setString(2, encryptedPassword);
            statement.setDate(3, date);
            statement.setLong(4, phoneNumber);
            statement.execute();
            try(ResultSet resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    long userId = resultSet.getInt(1);
                    return new User(userId, username, encryptedPassword, date, phoneNumber);
                }
            }
        } catch (SQLException e) {
            if(e.getErrorCode() == 19) {
                throw new DAOException("Username is taken");
            }
            e.printStackTrace();
            throw new DAOException(ERROR_MSG);
        }
        return null;
    }

    private static final String FIND_ONE_ID = "SELECT * FROM users WHERE userId = ?;";

    /**
     * {@inheritDoc}
     */
    @Override
    public User find(long userId) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ONE_ID)) {
            statement.setLong(1, userId);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    return build(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ERROR_MSG);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> find(Collection<Long> ids) {
        return null;
    }

    private static final String FIND_ONE_BY_NAME = "SELECT * FROM users WHERE username = ?;";

    /**
     * {@inheritDoc}
     */
    @Override
    public User authenticate(String username, String password) throws AuthException {
        if(username.isBlank()) throw new IllegalArgumentException("Username cannot be empty");
        if(password.isBlank()) throw new IllegalArgumentException("Password cannot be empty");
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ONE_BY_NAME)) {
            statement.setString(1, username);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    User user = build(resultSet);
                    verifyPassword(user, password.toCharArray());
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ERROR_MSG);
        }
        throw new AuthException("User does not exist");
    }

    /**
     * Verifies the password of a user.
     * @param   user the user to verify the password for
     * @param   charArray the password as an array of characters
     * @throws  AuthException if the password is invalid
     */
    private void verifyPassword(User user, char[] charArray) throws AuthException {
        BCrypt.Result result = BCrypt.verifyer().verify(charArray, user.getPassword());
        if(!result.verified)
            throw new AuthException("Invalid password");
    }

    /**
     * Creates a new User object from a ResultSet.
     * @param   resultSet the ResultSet to retrieve data from
     * @return  a new User object
     * @throws  SQLException if a database access error occurs
     */
    /*@Override
    protected User build(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getLong("userId"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getDate("registerDate"),
                resultSet.getInt("phoneNumber"));
    }*/

    static User build(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getLong("userId"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getDate("registerDate"),
                resultSet.getInt("phoneNumber"));
    }
}