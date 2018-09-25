package edu.kimjones.advancedjava.stock;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * This class is for testing the StupidStockService class
 *
 * @author Kim Jones
 */
public class StupidStockServiceTest {

    private String stockSymbol = "AAPL";
    private Date stockDate;

    private BigDecimal stockPriceExpected = BigDecimal.valueOf(100.0);
    private BigDecimal stockPriceNotExpected = BigDecimal.valueOf(1.0);;

    private StupidStockService stupidStockService;
    private StockQuote stupidQuote;

    @org.junit.Before
    public void setUp() throws Exception {

        LocalDate localDate = LocalDate.of(2018, 9, 16);
        this.stockDate = java.sql.Date.valueOf(localDate);

        this.stupidStockService = new StupidStockService();
        this.stupidQuote = stupidStockService.getStockQuote(this.stockSymbol, this.stockDate);
    }

    @Test
    public void testGetStockQuotePositive() {
        // StupidStockService..getStockQuote claims to always return a price of $100.00
        assertEquals("price is 100.00", this.stockPriceExpected, this.stupidQuote.getStockPrice());
    }

    @Test
    public void testGetStockQuoteNegative() {
        // StupidStockService..getStockQuote claims to always return a price of $100.00
        assertFalse("price is not 100.00", this.stockPriceNotExpected == this.stupidQuote.getStockPrice());
    }
}
