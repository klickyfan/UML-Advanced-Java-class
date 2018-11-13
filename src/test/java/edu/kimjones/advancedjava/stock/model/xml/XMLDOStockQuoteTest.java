package edu.kimjones.advancedjava.stock.model.xml;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * This class is for testing the class {@code XMLDOStockQuote}
 *
 * @author Kim Jones
 */
public class XMLDOStockQuoteTest {

    private static final String SYMBOL = "AAPL";
    private static final String PRICE = "100.00";
    private static final String TIME = "2015-02-10 00:00:01";

    private static XMLDOStockQuote stockQuote;

    @BeforeClass
    public static void setUp() {
        stockQuote = new XMLDOStockQuote();
        stockQuote.setSymbol(SYMBOL);
        stockQuote.setPrice(PRICE);
        stockQuote.setTime(TIME);
    }

    @Test
    public void testSymbolPositive() {
        assertEquals("SYMBOL matches", SYMBOL, stockQuote.getSymbol());
    }

    @Test
    public void testSymbolNegative() {
        assertNotEquals("SYMBOL does not match", "BMY", stockQuote.getSymbol());
    }

    @Test
    public void testPricePositive() {
        assertEquals("PRICE matches", PRICE, stockQuote.getPrice());
    }

    @Test
    public void testPriceNegative() {
        assertNotEquals("PRICE does not match", "50.00", stockQuote.getPrice());
    }

    @Test
    public void testTimePositive() {
        assertEquals("TIME matches", TIME, stockQuote.getTime());
    }

    @Test
    public void testTimeNegative() {
        assertNotEquals("date does not match", "10/22/2018", stockQuote.getTime());
    }
}