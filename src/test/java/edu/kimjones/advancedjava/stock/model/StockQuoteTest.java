package edu.kimjones.advancedjava.stock.model;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * This class is for testing the class {@code StockQuote}.
 *
 * @author Kim Jones
 */
public class StockQuoteTest {

    private final String stockSymbol = "AAPL";
    private final BigDecimal stockPrice = BigDecimal.valueOf(100.0);
    private Date dateRecorded;
    private Date dateNotRecorded; // for negative test

    private StockQuote stockQuote;

    @Before
    public void setUp() {
        LocalDate localDate0 = LocalDate.of(2018, 9, 16);
        this.dateRecorded = java.sql.Date.valueOf(localDate0);

        LocalDate localDate1 = LocalDate.of(2017, 1, 1);
        this.dateNotRecorded = java.sql.Date.valueOf(localDate1);

        this.stockQuote = new StockQuote(this.stockSymbol, this.stockPrice, this.dateRecorded);
    }

    @Test
    public void testSymbolPositive() {
        assertEquals("symbol matches", this.stockSymbol, this.stockQuote.getStockSymbol());
    }

    @Test
    public void testSymbolNegative() {
        assertNotSame("symbol does not match", "BMY", this.stockQuote.getStockSymbol());
    }

    @Test
    public void testPricePositive() {
        assertEquals("price matches", this.stockPrice, this.stockQuote.getStockPrice());
    }

    @Test
    public void testPriceNegative() {
        assertNotSame("price does not match", BigDecimal.valueOf(1.0), this.stockQuote.getStockPrice());
    }

    @Test
    public void testDatePositive() {
        assertEquals("date matches", this.dateRecorded, this.stockQuote.getDateRecorded());
    }

    @Test
    public void testDateNegative() {
        assertNotSame("date does not match", this.dateNotRecorded, this.stockQuote.getDateRecorded());
    }

    @Test
    public void testEqualsContract() {
        EqualsVerifier.forClass(StockQuote.class).verify();
    }
}
