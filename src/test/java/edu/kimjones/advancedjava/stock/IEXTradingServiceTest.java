package edu.kimjones.advancedjava.stock;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * This class is for testing the IEXTradingService class.
 *
 * @author Kim Jones
 */
public class IEXTradingServiceTest {

    private String stockSymbol = "AAPL";

    private Date stockDatePriceAvailable;
    private Date stockDatePriceUnavailable;

    private IEXTradingStockService iexTradingStockService;

    private StockQuote quoteNow;

    private StockQuote quoteOnDatePriceAvailable;
    private StockQuote quoteOnDatePriceUnavailable;

    @Before
    public void setUp() throws Exception {

        LocalDate localDate0 = LocalDate.of(2018, 9, 12);
        this.stockDatePriceAvailable = java.sql.Date.valueOf(localDate0);

        LocalDate localDate1 = LocalDate.of(2018, 1, 1);
        this.stockDatePriceUnavailable = java.sql.Date.valueOf(localDate1);

        this.iexTradingStockService = new IEXTradingStockService();

        this.quoteNow = iexTradingStockService.getStockQuote(this.stockSymbol);

        this.quoteOnDatePriceAvailable = iexTradingStockService.getStockQuote(this.stockSymbol, this.stockDatePriceAvailable);
        this.quoteOnDatePriceUnavailable = iexTradingStockService.getStockQuote(this.stockSymbol, this.stockDatePriceUnavailable);
    }

    @Test
    public void testGetStockQuoteNowPositive() {
        assertEquals("price is as expected", this.quoteNow.getStockPrice(), this.quoteNow.getStockPrice());
    }

    @Test
    public void testGetStockQuoteNowNegative() {
        assertFalse("price is not as expected", BigDecimal.valueOf(0.0) == this.quoteNow.getStockPrice());
    }

    @Test
    public void testGetStockQuotePriceAvailablePositive() {
        assertEquals("price is as expected", BigDecimal.valueOf(223.9), this.quoteOnDatePriceAvailable.getStockPrice());
    }

    @Test
    public void testGetStockQuotePriceAvailableNegative() {
        assertFalse("price is not as expected", BigDecimal.valueOf(1.0) == this.quoteOnDatePriceAvailable.getStockPrice());
    }

    @Test
    public void testGetStockQuotePriceUnavailableNull() {
        assertNull("quote is null", this.quoteOnDatePriceUnavailable);
    }

    /** To do: tests for getStockQuoteList methods (see those in BasicStockServiceTest) **/
}
