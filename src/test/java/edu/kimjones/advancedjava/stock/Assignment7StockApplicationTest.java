package edu.kimjones.advancedjava.stock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertTrue;

/**
 * This class is for testing the {@code Assignment7StockApplication} class. (There is not much to test here as the
 * application takes no arguments or options.)
 *
 * @author Kim Jones
 */
public class Assignment7StockApplicationTest {

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
    public void testPositive() throws Exception {
        Assignment7StockApplication.main(null);
        assertTrue("application runs normally", bytes.toString().isEmpty());
    }
}