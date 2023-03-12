package no.ntnu.idatt1002.demo.dao.sqlite;

import no.ntnu.idatt1002.demo.dao.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class defines the set of common methods for DAOs interacting
 * with SQLite.
 * @see DAO
 */
public abstract class SQLiteDAO extends DAO {

    private static final String URL = "jdbc:sqlite:test.db";

    /**
     * Attempts to establish a connection to the database located
     * at the specified URL, with an appropriate driver registered by the JDBC.
     * @return  a connection
     * @throws  SQLException if the access is denied or the URL is invalid
     * @see DriverManager
     */
    protected final Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}