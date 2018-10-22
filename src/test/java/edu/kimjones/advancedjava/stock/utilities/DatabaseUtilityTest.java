package edu.kimjones.advancedjava.stock.utilities;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

/**
* This class is for testing the DatabaseUtility class.
*/
public class DatabaseUtilityTest {

    private final String initializationFile = "./src/main/sql/initialization_test.sql";

    @Test
    public void testGetConnectionPositive() throws DatabaseConnectionException, SQLException {

        Connection connection = DatabaseUtility.getConnection();
        assertTrue("connection is a java.sql.Connection", connection instanceof Connection);
        connection.close();
    }

    @Test
    public void testGetConnectionNegative() throws DatabaseConnectionException, SQLException {

        Connection connection = DatabaseUtility.getConnection();
        assertNotNull("connection is null", connection);
        connection.close();
    }

    @Test
    public final void testInitializeDatabasePositive() throws DatabaseConnectionException, DatabaseInitializationException, SQLException {

        DatabaseUtility.initializeDatabase(initializationFile);
        Connection connection = DatabaseUtility.getConnection();
        assertTrue("table test exists", connection.createStatement().execute("select * from test"));
        connection.createStatement().executeUpdate("DROP TABLE test;");
        connection.close();
    }

    @Test
    public final void testInitializeDatabaseNegative() throws DatabaseConnectionException, DatabaseInitializationException, SQLException {

        DatabaseUtility.initializeDatabase(initializationFile);
        Connection connection = DatabaseUtility.getConnection();

        try {
            connection.createStatement().execute("SELECT * from nonexistent_table");
        } catch (MySQLSyntaxErrorException e) {
            assertThat("exception", e.getMessage(), is("Table 'stocks.nonexistent_table' doesn't exist"));
        }

        DatabaseUtility.getConnection().createStatement().executeUpdate("DROP TABLE test;");
        connection.close();
    }
}
