package no.ntnu.idatt1002.demo.dao.sqlite;

import no.ntnu.idatt1002.demo.dao.PersonDAO;
import no.ntnu.idatt1002.demo.data.Budget;
import no.ntnu.idatt1002.demo.data.person.Person;

import java.sql.*;
import java.util.Collection;
import java.util.List;

public final class SQLitePersonDAO extends SQLiteDAO implements PersonDAO {

    // Implementation test

    private static final String CREATE_PERSONS = """
                CREATE TABLE IF NOT EXISTS persons (
            	personId integer PRIMARY KEY AUTOINCREMENT,
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
            statement.execute(CREATE_PERSONS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String INSERT_PERSON = """
                INSERT INTO persons (username, password, registerDate, phoneNumber, budgetId)
                VALUES (?, ?, ?, ?, ?);
            """;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(Person person) {
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

    private static final String FIND_PERSON = """
                SELECT * FROM persons WHERE personId = ?;
            """;

    /**
     * {@inheritDoc}
     */
    @Override
    public Person find(int id) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_PERSON)) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    return new Person(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getDate("date"),
                            resultSet.getInt("phoneNumber"),
                            new Budget());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Person> find(Collection<Integer> ids) {
        return null;
    }
}