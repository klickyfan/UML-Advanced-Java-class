package edu.kimjones.advancedjava.stock.model.xml;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * This class is for testing the class {@code XMLDOStockQuote}.
 *
 * @author Kim Jones
 */
public class XMLDOStockQuoteTest {

    private final String symbol = "AAPL";
    private final String price = "100.00";
    private final String time = "2015-02-10 00:00:01";

    private XMLDOStockQuote stockQuote;

    @Before
    public void setUp() {
        this.stockQuote = new XMLDOStockQuote();
        this.stockQuote.setSymbol(this.symbol);
        this.stockQuote.setPrice(this.price);
        this.stockQuote.setTime(this.time);
    }

    @Test
    public void testSymbolPositive() {
        assertEquals("symbol matches", this.symbol, this.stockQuote.getSymbol());
    }

    @Test
    public void testSymbolNegative() {
        assertNotEquals("symbol does not match", "BMY", this.stockQuote.getSymbol());
    }

    @Test
    public void testPricePositive() {
        assertEquals("price matches", this.price, this.stockQuote.getPrice());
    }

    @Test
    public void testPriceNegative() {
        assertNotEquals("price does not match", "50.00", this.stockQuote.getPrice());
    }

    @Test
    public void testTimePositive() {
        assertEquals("time matches", this.time, this.stockQuote.getTime());
    }

    @Test
    public void testTimeNegative() {
        assertNotEquals("date does not match", "10/22/2018", this.stockQuote.getTime());
    }
}