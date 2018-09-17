package edu.kimjones.advancedjava.stock;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RandomStockServiceTest {

    private String stockSymbol = "AAPL";
    private Date stockDate;

    private BigDecimal stockPriceNotExpected = BigDecimal.valueOf(1.0);;

    private RandomStockService randomStockService;

    private StockQuote randomQuote0;
    private StockQuote randomQuote1;

    @org.junit.Before
    public void setUp() throws Exception {

        LocalDate localDate = LocalDate.of(2018, 9, 16);
        this.stockDate = java.sql.Date.valueOf(localDate);

        this.randomStockService = new RandomStockService();

        this.randomQuote0 = randomStockService.getStockQuote(this.stockSymbol, this.stockDate);
        this.randomQuote1 = randomStockService.getStockQuote(this.stockSymbol, this.stockDate);
    }

    @Test
    public void testGetStockQuotePositive() {

        // StupidStockService.getStockQuote claims to return a random price, so there is no way to test that it is a
        // particular value. I will therefore simply test that it returns a number.
        assertEquals("price is a number", this.randomQuote0.getStockPrice().getClass(), BigDecimal.class);
    }

    @Test
    public void testGetStockQuoteNegative() {

        // StupidStockService..getStockQuote claims to return a random price each time it is executed
        assertFalse("prices on subsequent invocations match", this.randomQuote0.getStockPrice() == this.randomQuote1.getStockPrice());
    }
}
