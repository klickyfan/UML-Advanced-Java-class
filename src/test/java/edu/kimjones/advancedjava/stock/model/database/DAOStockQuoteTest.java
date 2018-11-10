package edu.kimjones.advancedjava.stock.model.database;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * This class is for testing the class {@code DAOStockQuote}.
 *
 * @author Kim Jones
 */
public class DAOStockQuoteTest {

    private static final String STOCK_SYMBOL = "AAPL";
    private static final BigDecimal STOCK_PRICE = BigDecimal.valueOf(100.0);

    private static Date dateRecorded;
    private static Date dateNotRecorded; // for negative test

    private static DAOStockQuote stockQuote;

    @BeforeClass
    public static void setUp() {
        LocalDate localDate0 = LocalDate.of(2018, 9, 16);
        dateRecorded = java.sql.Date.valueOf(localDate0);

        LocalDate localDate1 = LocalDate.of(2017, 1, 1);
        dateNotRecorded = java.sql.Date.valueOf(localDate1);

        stockQuote = new DAOStockQuote(STOCK_SYMBOL, STOCK_PRICE, dateRecorded);
    }

    @Test
    public void testSymbolPositive() {
        assertEquals("symbol matches", STOCK_SYMBOL, stockQuote.getStockSymbol());
    }

    @Test
    public void testSymbolNegative() {
        assertNotSame("symbol does not match", "BMY", stockQuote.getStockSymbol());
    }

    @Test
    public void testPricePositive() {
        assertEquals("price matches", STOCK_PRICE, stockQuote.getStockPrice());
    }

    @Test
    public void testPriceNegative() {
        assertNotSame("price does not match", BigDecimal.valueOf(1.0), stockQuote.getStockPrice());
    }

    @Test
    public void testDatePositive() {
        assertEquals("date matches", dateRecorded, stockQuote.getDateRecorded());
    }

    @Test
    public void testDateNegative() {
        assertNotSame("date does not match", dateNotRecorded, stockQuote.getDateRecorded());
    }

    @Test
    public void testEqualsContract() {
        EqualsVerifier.forClass(DAOStockQuote.class).verify();
    }
}
