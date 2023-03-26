package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.dao.GroupDAO;
import no.ntnu.idatt1002.dao.exception.DAOException;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class SQLiteGroupDAO extends SQLiteDAO implements GroupDAO {

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

    private static final String CREATE_GROUP_INVITES = """
                CREATE TABLE IF NOT EXISTS groupInvites (
                groupId integer NOT NULL,
            	userId integer NOT NULL,
            	FOREIGN KEY (groupId) REFERENCES groups(groupId),
            	FOREIGN KEY (userId) REFERENCES users(userId),
            	PRIMARY KEY (groupId, userId)
            );""";

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup() {
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            statement.addBatch(CREATE_GROUPS);
            statement.addBatch(CREATE_GROUP_LINK);
            statement.addBatch(CREATE_GROUP_INVITES);
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String INSERT_GROUP = """
                INSERT INTO groups (groupName)
                VALUES (?);
            """;

    /**
     * {@inheritDoc}
     */
    @Override
    public Group create(String name) {
        if(name == null || name.isBlank()) throw new IllegalArgumentException("Group name cannot be blank");
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_GROUP, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.execute();
            try(ResultSet resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    long groupId = resultSet.getInt(1);
                    return new Group(groupId, name);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ERROR_MSG);
        }
        return null;
    }

    private static final String ADD_MEMBER = """
                INSERT INTO groupUsers (groupId, userId)
                VALUES (?, ?);
            """;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMember(long groupId, long userId) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_MEMBER)) {
            statement.setLong(1, groupId);
            statement.setLong(2, userId);
            statement.execute();
        } catch (SQLException e) {
            if(e.getErrorCode() == 19) throw new DAOException("User is already a member");
            e.printStackTrace();
            throw new DAOException(ERROR_MSG);
        }
    }

    private static final String ADD_INVITE = """
                INSERT INTO groupInvites (groupId, userId)
                VALUES (?, ?);
            """;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addInvite(long groupId, long userId) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_INVITE)) {
            statement.setLong(1, groupId);
            statement.setLong(2, userId);
            statement.execute();
        } catch (SQLException e) {
            if(e.getErrorCode() == 19) {
                throw new DAOException("Invite already sent");
            }
            throw new DAOException(ERROR_MSG);
        }
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
        List<User> users = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_MEMBERS)) {
            statement.setLong(1, groupId);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    User user = SQLiteUserDAO.build(resultSet);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ERROR_MSG);
        }
        return users;
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
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ONE_BY_USER)) {
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

    static Group build(ResultSet resultSet) throws SQLException {
        long groupId = resultSet.getLong("groupId");
        Group group = new Group(groupId,
                resultSet.getString("groupName"));
        getMembers(groupId).forEach(group::addMember);
        return group;
    }
}