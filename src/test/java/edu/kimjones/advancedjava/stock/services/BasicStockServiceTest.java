package edu.kimjones.advancedjava.stock.services;

import edu.kimjones.advancedjava.stock.model.StockQuote;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static edu.kimjones.advancedjava.stock.utilities.TestUtility.addHoursToDate;
import static edu.kimjones.advancedjava.stock.utilities.TestUtility.parseDateString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

/**
 * This class is for testing the BasicStockService class.
 *
 * @author Kim Jones
 */
public class BasicStockServiceTest {

    private final int HOURS_IN_DAY = 24;

    private String stockSymbol = "AAPL";

    private Date stockDate;

    private BigDecimal stockPriceExpected = BigDecimal.valueOf(100.0);
    private BigDecimal stockPriceNotExpected = BigDecimal.valueOf(1.0);

    private BasicStockService basicStockService;

    private StockQuote basicQuoteNow;
    private StockQuote basicQuoteOnDate;

    private List<StockQuote> basicHourlyStockQuoteList;
    private StockQuote basicHourlyStockQuoteListFirstItemExpected;
    private StockQuote basicHourlyStockQuoteListLastItemExpected;
    private StockQuote basicHourlyStockQuoteListTenthItemExpected;

    private List<StockQuote> basicDailyStockQuoteList;
    private List<StockQuote> basicDailyStockQuoteListExpected = new ArrayList<StockQuote>();

    @Before
    public void setUp() throws Exception {

        LocalDate localDate = LocalDate.of(2018, 9, 16);
        this.stockDate = java.sql.Date.valueOf(localDate);

        this.basicStockService = new BasicStockService();

        /**
         * prepare to test versions of getStockQuote that return a single quote
         */
        this.basicQuoteNow = basicStockService.getLatestStockQuote(this.stockSymbol);

        this.basicQuoteOnDate = basicStockService.getStockQuote(this.stockSymbol, this.stockDate);

        /**
         * prepare to test getStockQuoteList on hour interval
         */
        this.basicHourlyStockQuoteList =
                basicStockService.getStockQuoteList(this.stockSymbol, parseDateString("9/20/2018"), parseDateString("9/20/2018"), StockService.StockQuoteInterval.HOURLY);

        this.basicHourlyStockQuoteListFirstItemExpected =
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(100.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,20 )));
        this.basicHourlyStockQuoteListTenthItemExpected =
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(110.00),
                        addHoursToDate(10, java.sql.Date.valueOf(LocalDate.of(2018,9,20 ))));
        this.basicHourlyStockQuoteListLastItemExpected =
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(123.00),
                        addHoursToDate(HOURS_IN_DAY - 1, java.sql.Date.valueOf(LocalDate.of(2018,9,20 ))));

        /**
         * prepare to test getStockQuoteList on daily interval
         */
        this.basicDailyStockQuoteList = basicStockService.getStockQuoteList(this.stockSymbol, parseDateString("9/20/2018"), parseDateString("9/22/2018"), StockService.StockQuoteInterval.DAILY);

        this.basicDailyStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(100.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,20 ))));
        this.basicDailyStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(101.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,21 ))));
        this.basicDailyStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(102.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,22 ))));
    }

    @Test
    public void testGetStockQuotePositive() {
        assertEquals("price is 100.00", this.stockPriceExpected, this.basicQuoteNow.getStockPrice());
    }

    @Test
    public void testGetStockQuoteNegative() {
        assertFalse("price is not 100.00", this.stockPriceNotExpected == this.basicQuoteNow.getStockPrice());
    }

    @Test
    public void testGetStockQuoteWithDatePositive() {
        assertEquals("price is 100.00", this.stockPriceExpected, this.basicQuoteOnDate.getStockPrice());
    }

    @Test
    public void testGetStockQuoteWithDateNegative() {
        assertFalse("price is not 100.00", this.stockPriceNotExpected == this.basicQuoteOnDate.getStockPrice());
    }

    @Test
    public void testGetHourlyStockQuotePositive() {
        assertThat("stock quote obtained equals stock quote expected", this.basicHourlyStockQuoteList.get(0), is(this.basicHourlyStockQuoteListFirstItemExpected));
        assertThat("tenth stock quote obtained equals stock quote expected", this.basicHourlyStockQuoteList.get(10), is(this.basicHourlyStockQuoteListTenthItemExpected));
        assertThat("last stock quote obtained equals stock quote expected", this.basicHourlyStockQuoteList.get(HOURS_IN_DAY - 1), is(this.basicHourlyStockQuoteListLastItemExpected));

        assertEquals("quote list has 24 items", this.basicHourlyStockQuoteList.size(), HOURS_IN_DAY);
    }

    @Test
    public void testGetHourlyStockQuotesNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", this.basicHourlyStockQuoteList, is(not(new ArrayList<StockQuote>())));
        assertThat("first stock quote obtained does not equal tenth stock quote expected", this.basicHourlyStockQuoteList.get(0), is(not(this.basicHourlyStockQuoteListTenthItemExpected)));
    }

    @Test
    public void testGetDailyStockQuotePositive() {
        assertThat("stock quote list obtained equals stock quote list expected", this.basicDailyStockQuoteList, is(this.basicDailyStockQuoteListExpected));
    }

    @Test
    public void testGetDailyStockQuotesNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", this.basicDailyStockQuoteList, is(not(new ArrayList<StockQuote>())));
    }
}

