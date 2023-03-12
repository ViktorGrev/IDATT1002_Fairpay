package no.ntnu.idatt1002.demo.dao.sqlite;

import no.ntnu.idatt1002.demo.dao.PersonDAO;
import no.ntnu.idatt1002.demo.data.person.Person;

import java.sql.*;
import java.util.Collection;
import java.util.List;

public final class SQLitePersonDAO extends SQLiteDAO implements PersonDAO {

    // Implementation test

    private static final String CREATE_PERSONS = """
                CREATE TABLE IF NOT EXISTS persons (
            	personId integer PRIMARY KEY,
            	username varchar(16),
            	password char(60)
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
                INSERT INTO persons (username, password) VALUES (?, ?);
            """;

    @Override
    public boolean add(Person person) {
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            statement.execute(CREATE_PERSONS);

            PreparedStatement preparedStatement2 = connection.prepareStatement(INSERT_PERSON);
            preparedStatement2.setString(1, "username");
            preparedStatement2.setString(2, "password");
            System.out.println(preparedStatement2.execute());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private static final String FIND_PERSON = """
                SELECT * FROM persons WHERE personId=?;
            """;

    @Override
    public Person find(int personId) {
        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_PERSON);
            preparedStatement.setInt(1, 0);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                System.out.println("hit!");
                break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Person> find(Collection<Integer> personIds) {
        return null;
    }
}
