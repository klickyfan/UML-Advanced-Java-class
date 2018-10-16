package edu.kimjones.advancedjava.stock.utilities;

/**
 * This class is used to signal a problem connecting to a database.
 */
public class DatabaseConnectionException extends Exception {

    /**
     * This method constructs a new exception with the specified detail message and cause. Note that the detail
     * message associated with {@code cause} is <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param message   a detailed message (which may be retrieved by the {@link #getMessage()} method)
     * @param cause     a cause (which may be retrieved by the {@link #getCause()} method) (A <tt>null</tt> value is
     *                  permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
