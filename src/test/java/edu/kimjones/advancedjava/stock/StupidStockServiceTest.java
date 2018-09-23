package edu.kimjones.advancedjava.stock;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

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

    private StockQuote stupidQuoteNow;
    private StockQuote stupidQuoteOnDate;

    private Calendar from;
    private Calendar until;

    private List<StockQuote> stupidStockQuoteList;
    private List<StockQuote> stupidStockQuoteListExpected = new ArrayList<StockQuote>();

    @Before
    public void setUp() throws Exception {

        LocalDate localDate = LocalDate.of(2018, 9, 16);
        this.stockDate = java.sql.Date.valueOf(localDate);

        this.stupidStockService = new StupidStockService();

        this.stupidQuoteNow = stupidStockService.getStockQuote(this.stockSymbol);
        this.stupidQuoteOnDate = stupidStockService.getStockQuote(this.stockSymbol, this.stockDate);

        this.from = StockQuoteApplication.parseDateString("9/20/2018");
        this.until = StockQuoteApplication.parseDateString("9/22/2018");

        this.stupidStockQuoteList = stupidStockService.getStockQuoteList(this.stockSymbol, this.from, this.until);

        this.stupidStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(100.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,20 ))));
        this.stupidStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(100.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,21 ))));
        this.stupidStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(100.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,22 ))));
    }

    @Test
    public void testGetStockQuotePositive() {
        // StupidStockService..getStockQuote claims to always return a price of $100.00
        assertEquals("price is 100.00", this.stockPriceExpected, this.stupidQuoteNow.getStockPrice());
    }

    @Test
    public void testGetStockQuoteNegative() {
        // StupidStockService..getStockQuote claims to always return a price of $100.00
        assertFalse("price is not 100.00", this.stockPriceNotExpected == this.stupidQuoteNow.getStockPrice());
    }

    @Test
    public void testGetStockQuoteWithDatePositive() {
        // StupidStockService..getStockQuote claims to always return a price of $100.00
        assertEquals("price is 100.00", this.stockPriceExpected, this.stupidQuoteOnDate.getStockPrice());
    }

    @Test
    public void testGetStockQuoteWithDateNegative() {
        // StupidStockService..getStockQuote claims to always return a price of $100.00
        assertFalse("price is not 100.00", this.stockPriceNotExpected == this.stupidQuoteOnDate.getStockPrice());
    }

    @Test
    public void testGetStockQuotesPositive() {
        assertThat("stock quote list obtained equals stock quote list expected", this.stupidStockQuoteList, is(this.stupidStockQuoteListExpected));
    }

    @Test
    public void testGetStockQuotesNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", this.stupidStockQuoteList, is(not(new ArrayList<StockQuote>())));
    }
}
