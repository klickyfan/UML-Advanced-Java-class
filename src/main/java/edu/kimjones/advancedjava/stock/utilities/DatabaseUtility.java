package edu.kimjones.advancedjava.stock.utilities;

import com.ibatis.common.jdbc.ScriptRunner;

import edu.kimjones.advancedjava.stock.services.DatabasePersonService;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class encapsulates database related utility methods. For it to function properly, the working directory of the
 * application using it it must have a properties file named database.properties that contains a value for
 * {@code driver} (a JDBC driver), {@code url}(the url at which the database may be reached, {@code username} (the
 * username to use when connecting to the database), and {@code password} (the password to use when connecting to the
 * database).
 */
public class DatabaseUtility {

    public static final String initializationFile = "./src/main/sql/create_stocks_database.sql";

    private static SessionFactory sessionFactory; // for use by Hibernate
    private static Configuration configuration; // for use by Hibernate

    private static final String HIBERNATE_CONFIGURATION_FILE = "hibernate.cfg.xml";
    private static final String DATABASE_DRIVER_CLASS = "hibernate.connection.driver_class";
    private static final String DATABASE_USERNAME = "hibernate.connection.username";
    private static final String DATABASE_PASSWORD = "hibernate.connection.password";
    private static final String DATABASE_URL = "hibernate.connection.url";

    /**
     * @return a Hibernate {@code SessionFactory} for use with database transactions
     */
    public static SessionFactory getSessionFactory() {

        // singleton pattern
        synchronized (DatabasePersonService.class) {
            if (sessionFactory == null) {

                Configuration configuration = getConfiguration();

                ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .buildServiceRegistry();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
        }
        return sessionFactory;
    }

    /**
     * @return a Hibernate {@code Configuration}
     */
    private static Configuration getConfiguration() {

        synchronized (DatabaseUtility.class) {
            if (configuration == null) {
                configuration = new Configuration();
                configuration.configure(HIBERNATE_CONFIGURATION_FILE);
            }
        }
        return configuration;
    }

    /**
     * @return                                  a database connection
     * @throws DatabaseConnectionException      if a connection to the database cannot be established
     */
    public static Connection getConnection() throws DatabaseConnectionException {

        Connection connection = null;
        Configuration configuration = getConfiguration();

        try {
            Class.forName(configuration.getProperty(DATABASE_DRIVER_CLASS));
            String databaseUrl = configuration.getProperty(DATABASE_URL);
            String username = configuration.getProperty(DATABASE_USERNAME);
            String password = configuration.getProperty(DATABASE_PASSWORD);

            connection = DriverManager.getConnection(databaseUrl, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DatabaseConnectionException("Could not connect to database. " + e.getMessage(), e);
        }
        return connection;
    }

    /**
     * Connects to a database and runs the given script to initialize its schema.
     *
     * @param initializationScript              full path to the script to run to create the schema
     * @throws DatabaseInitializationException  if the database cannot be initialized
     */
    public static void initializeDatabase(String initializationScript) throws DatabaseInitializationException {

        Connection connection = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            ScriptRunner runner = new ScriptRunner(connection, false, false);

            // stop the ScriptRunner from printing the sql in the initializationScript to the console
            runner.setErrorLogWriter(null);
            runner.setLogWriter(null);

            InputStream inputStream = new  FileInputStream(initializationScript);
            InputStreamReader reader = new InputStreamReader(inputStream);
            runner.runScript(reader);
            reader.close();

            connection.commit();
            connection.close();
        } catch (DatabaseConnectionException | SQLException | IOException e) {
            throw new DatabaseInitializationException("Could not initialize database. " + e.getMessage(), e);
        }
    }
}
