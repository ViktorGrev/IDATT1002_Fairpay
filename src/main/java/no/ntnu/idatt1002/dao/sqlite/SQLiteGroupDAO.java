package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.dao.GroupDAO;
import no.ntnu.idatt1002.data.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public final class SQLiteGroupDAO extends SQLiteDAO implements GroupDAO {

    private static final String CREATE_GROUPS = """
                CREATE TABLE IF NOT EXISTS groups (
            	groupId integer PRIMARY KEY AUTOINCREMENT,
            	name text(16) UNIQUE NOT NULL,
            	createdDate integer NOT NULL
            );""";

    @Override
    public void setup() {
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            statement.execute(CREATE_GROUPS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String INSERT_GROUP = """
                INSERT INTO groups (username, password, registerDate, phoneNumber)
                VALUES (?, ?, ?, ?);
            """;

    @Override
    public boolean insert(Group group) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_GROUP)) {
            /*statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setLong(3, user.getRegisterDate().getTime());
            statement.setLong(4, user.getPhoneNumber());*/
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Group find(long groupId) {
        return null;
    }

    @Override
    public Group findByUser(long userId) {

        return null;
    }
}