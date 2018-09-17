package edu.kimjones.advancedjava.stock;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class IEXTradingServiceTest {

    private String stockSymbol = "AAPL";

    private Date stockDatePriceAvailable;
    private Date stockDatePriceUnavailable;

    // the closing price on the date we will test was 223.90
    private BigDecimal stockPriceExpected = BigDecimal.valueOf(223.90);
    private BigDecimal stockPriceNotExpected = BigDecimal.valueOf(1.0);

    private IEXTradingStockService iexTradingStockService;

    private StockQuote quotePriceAvailable;
    private StockQuote quotePriceUnavailable;

    @org.junit.Before
    public void setUp() throws Exception {

        LocalDate localDate0 = LocalDate.of(2018, 9, 12);
        this.stockDatePriceAvailable = java.sql.Date.valueOf(localDate0);

        LocalDate localDate1 = LocalDate.of(2018, 1, 1);
        this.stockDatePriceUnavailable = java.sql.Date.valueOf(localDate1);

        this.iexTradingStockService = new IEXTradingStockService();

        this.quotePriceAvailable = iexTradingStockService.getStockQuote(this.stockSymbol, this.stockDatePriceAvailable);
        this.quotePriceUnavailable = iexTradingStockService.getStockQuote(this.stockSymbol, this.stockDatePriceUnavailable);
    }

    @Test
    public void testGetStockQuotePriceAvailablePositive() {
        // StupidStockService..getStockQuote claims to always return a price of $100.00
        assertEquals("price is as expected", this.stockPriceExpected, this.quotePriceAvailable.getStockPrice());
    }

    @Test
    public void testGetStockQuotePriceAvailableNegative() {
        // StupidStockService..getStockQuote claims to always return a price of $100.00
        assertFalse("price is not as expected", this.stockPriceNotExpected == this.quotePriceAvailable.getStockPrice());
    }

    @Test
    public void testGetStockQuotePriceUnavailableNull() {
        // StupidStockService..getStockQuote claims to always return a price of $100.00
        assertNull("quote is null", this.quotePriceUnavailable);
    }
}
