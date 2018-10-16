package edu.kimjones.advancedjava.stock.utilities;

import com.google.common.util.concurrent.UncheckedExecutionException;
import com.ibatis.common.jdbc.ScriptRunner;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class encapsulates database related utility methods. For it to function properly, the working directory of the
 * application using it it must have a properties file named database.properties that contains a value for
 * {@code driver} (a JDBC driver), {@code url}(the url at which the database may be reached, {@code username} (the
 * username to use when connecting to the database), and {@code password} (the password to use when connecting to the
 * database).
 *
 * This class uses Jasypt (http://www.jasypt.org/encrypting-configuration.html) to decrypt encrypted values in
 * database.properties.
 */
public class DatabaseUtility {

    private static final String PROPERTY_FILE = "database.properties";

    // password needed by Jasypt to decrypt the password property in database.properties
    private static final String JASYPT_PASSWORD = "wordsarewind";

    private static Properties properties;

    /**
     * This static initializer gets the properties that will be needed by the other methods of this class.
     */
    static {

        try {

            File f = new File(PROPERTY_FILE);

            // create an encryptor for decrypting the values in our database.properties
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setPassword(JASYPT_PASSWORD);

            // create an EncryptableProperties object and load it the way one would an ordinary Properties object
            properties = new EncryptableProperties(encryptor);
            properties.load(new FileInputStream(f));

            // properties.list(System.out);

        } catch (FileNotFoundException ex) {

            throw new UncheckedExecutionException("Unable to find " + PROPERTY_FILE + ".", ex);

        } catch (IOException ex) {

            throw new UncheckedIOException("Unable to load " + PROPERTY_FILE + ".", ex);
        }
    }

    /**
     * This method returns a handle to a new database connection.
     *
     * @return                                  a database connection
     * @throws DatabaseConnectionException
     */
    public static Connection getConnection() throws DatabaseConnectionException {

        Connection connection = null;

        try {
            Class.forName(properties.getProperty("driver"));
            connection = DriverManager.getConnection(
                    properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
        } catch (ClassNotFoundException  | SQLException e)  {
            throw new DatabaseConnectionException("Could not connection to database." + e.getMessage(), e);
        }

        return connection;
    }

    /**
     * This method runs connects to a database and runs a script to initialize its schema.
     *
     * @param initializationScript                  full path to the script to run to create the schema
     * @throws DatabaseInitializationException
     */
    public static void initializeDatabase(String initializationScript) throws DatabaseInitializationException {

        Connection connection = null;

        try {

            connection = getConnection();
            ScriptRunner runner = new ScriptRunner(connection, false, false);
            InputStream inputStream = new  FileInputStream(initializationScript);
            InputStreamReader reader = new InputStreamReader(inputStream);

            runner.runScript(reader);

            reader.close();
            //connection.commit();
            connection.close();

        } catch (DatabaseConnectionException | SQLException |IOException e) {

            throw new DatabaseInitializationException("Could not initialize database :" + e.getMessage(), e);

        }
    }
}
