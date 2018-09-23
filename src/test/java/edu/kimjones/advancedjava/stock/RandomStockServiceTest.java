package edu.kimjones.advancedjava.stock;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * This class is for testing the RandomStockService class.
 *
 * @author Kim Jones
 */
public class RandomStockServiceTest {

    private String stockSymbol = "AAPL";
    private Date stockDate;

    private BigDecimal stockPriceNotExpected = BigDecimal.valueOf(1.0);;

    private RandomStockService randomStockService;

    private StockQuote randomQuoteNow0;
    private StockQuote randomQuoteNow1;

    private StockQuote randomQuoteOnDate0;
    private StockQuote randomQuoteOnDate1;

    @Before
    public void setUp() throws Exception {

        LocalDate localDate = LocalDate.of(2018, 9, 16);
        this.stockDate = java.sql.Date.valueOf(localDate);

        this.randomStockService = new RandomStockService();

        this.randomQuoteNow0 = randomStockService.getStockQuote(this.stockSymbol);
        this.randomQuoteNow1 = randomStockService.getStockQuote(this.stockSymbol);

        this.randomQuoteOnDate0 = randomStockService.getStockQuote(this.stockSymbol, this.stockDate);
        this.randomQuoteOnDate1 = randomStockService.getStockQuote(this.stockSymbol, this.stockDate);
    }

    @Test
    public void testGetStockQuotePositive() {
        // RandomStockService.getStockQuote claims to return a random price, so there is no way to test that it is a
        // particular value. I will therefore simply test that it returns a number.
        assertEquals("price is a number", this.randomQuoteNow0.getStockPrice().getClass(), BigDecimal.class);
    }

    @Test
    public void testGetStockQuoteNegative() {
        // RandomStockService..getStockQuote claims to return a random price each time it is executed
        assertFalse("prices on subsequent invocations match", this.randomQuoteNow0.getStockPrice() == this.randomQuoteNow1.getStockPrice());
    }

    @Test
    public void testGetStockQuoteWithDatePositive() {
        // RandomStockService.getStockQuote claims to return a random price, so there is no way to test that it is a
        // particular value. I will therefore simply test that it returns a number.
        assertEquals("price is a number", this.randomQuoteOnDate0.getStockPrice().getClass(), BigDecimal.class);
    }

    @Test
    public void testGetStockQuoteWithDateNegative() {
        // RandomStockService..getStockQuote claims to return a random price each time it is executed
        assertFalse("prices on subsequent invocations match", this.randomQuoteOnDate0.getStockPrice() == this.randomQuoteOnDate1.getStockPrice());
    }

    /** To do: tests for getStockQuoteList methods (see those in BasicStockServiceTest) **/
}
