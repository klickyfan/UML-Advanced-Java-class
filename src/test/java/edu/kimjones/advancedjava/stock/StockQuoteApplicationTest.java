package edu.kimjones.advancedjava.stock;

import edu.kimjones.advancedjava.stock.utilities.DatabaseInitializationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertTrue;

/**
 * This class is for testing the {@code StockQuoteApplication} class (the main entry point for the stock quote
 * application).
 *
 * @author Kim Jones
 */
public class StockQuoteApplicationTest {

    private PrintStream console;
    private ByteArrayOutputStream bytes;

    @Before
    public void setUp() {
        bytes   = new ByteArrayOutputStream();
        console = System.err;
        System.setErr(new PrintStream(bytes));
    }

    @After
    public void tearDown() {
        System.setErr(console);
    }

    @Test
    public void testWhenNoOptionsSupplied() throws DatabaseInitializationException {
        StockQuoteApplication.main(null);
        assertTrue("application properly handles insufficient options", bytes.toString().contains("Insufficient options were supplied"));
    }

    @Test
    public void testWhenInsufficientOptionsSupplied() throws DatabaseInitializationException {
        StockQuoteApplication.main(new String[]{"-test"});
        assertTrue("application properly handles insufficient options", bytes.toString().contains("Insufficient options were supplied"));
    }

    @Test
    public void testWhenInvalidArgumentsSupplied() throws DatabaseInitializationException {
        StockQuoteApplication.main(new String[]{"a", "b", "c", "d", "e", "f"});
        assertTrue("application properly handles invalid argument", bytes.toString().contains("No argument is allowed: a"));
    }

    @Test
    public void testWhenIncorrectOptionSupplied() throws DatabaseInitializationException {
        StockQuoteApplication.main(new String[]{"-test", "a", "b", "c", "d", "e"});
        assertTrue("application properly handles incorrect option", bytes.toString().contains("is not a valid option"));
    }

    @Test
    public void testWhenCorrectArgumentSupplied() throws DatabaseInitializationException {
        java.lang.String[] args = new java.lang.String[8];

        args[0] = "-symbol";
        args[1] = "AAPL";
        args[2] = "-from";
        args[3] = "9/20/2018";
        args[4] = "-until";
        args[5] = "9/23/2018";
        args[6] = "-interval";
        args[7] = "DAILY";

        StockQuoteApplication.main(args);

        assertTrue("application runs normally when all options provided", bytes.toString().isEmpty());
    }
}
