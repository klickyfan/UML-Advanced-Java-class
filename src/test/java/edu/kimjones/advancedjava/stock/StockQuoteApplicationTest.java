package edu.kimjones.advancedjava.stock;

import org.junit.Test;

import java.io.IOException;

/**
 * This class is for testing the StockQuoteApplication class (the main entry point for the stock quote application).
 *
 * @author Kim Jones
 */
public class StockQuoteApplicationTest {

    @Test
    public void testMainPositive() throws IOException {

        java.lang.String[] args = new java.lang.String[3];

        args[0] = "AAPL";
        args[1] = "9/20/2018";
        args[2] = "9/23/2018";

        StockQuoteApplication.main(args);
    }

    @Test(expected = NullPointerException.class)
    public void testMainNegative() throws IOException {

        StockQuoteApplication.main(null);
    }
}
