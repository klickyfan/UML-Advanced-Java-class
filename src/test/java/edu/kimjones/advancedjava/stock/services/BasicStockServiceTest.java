package edu.kimjones.advancedjava.stock.services;

import edu.kimjones.advancedjava.stock.model.DAOStockQuote;

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

    private final BigDecimal stockPriceExpected = BigDecimal.valueOf(100.0);
    private final BigDecimal stockPriceNotExpected = BigDecimal.valueOf(1.0);

    private DAOStockQuote basicQuoteNow;
    private DAOStockQuote basicQuoteOnDate;

    private List<DAOStockQuote> basicHourlyStockQuoteList;
    private DAOStockQuote basicHourlyStockQuoteListFirstItemExpected;
    private DAOStockQuote basicHourlyStockQuoteListLastItemExpected;
    private DAOStockQuote basicHourlyStockQuoteListTenthItemExpected;

    private List<DAOStockQuote> basicDailyStockQuoteList;
    private final List<DAOStockQuote> basicDailyStockQuoteListExpected = new ArrayList<DAOStockQuote>();

    @Before
    public void setUp() throws Exception {

        LocalDate localDate = LocalDate.of(2018, 9, 16);
        Date stockDate = java.sql.Date.valueOf(localDate);

        BasicStockService basicStockService = new BasicStockService();

        /*
          prepare to test versions of getStockQuote that return a single quote
         */
        String stockSymbol = "AAPL";
        this.basicQuoteNow = basicStockService.getLatestStockQuote(stockSymbol);

        this.basicQuoteOnDate = basicStockService.getStockQuote(stockSymbol, stockDate);

        /*
          prepare to test getStockQuoteList on hour interval
         */
        this.basicHourlyStockQuoteList =
                basicStockService.getStockQuoteList(stockSymbol, parseDateString("9/20/2018"), parseDateString("9/20/2018"), StockService.StockQuoteInterval.HOURLY);

        this.basicHourlyStockQuoteListFirstItemExpected =
                new DAOStockQuote(
                        stockSymbol,
                        new BigDecimal(100.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,20 )));
        this.basicHourlyStockQuoteListTenthItemExpected =
                new DAOStockQuote(
                        stockSymbol,
                        new BigDecimal(110.00),
                        addHoursToDate(10, java.sql.Date.valueOf(LocalDate.of(2018,9,20 ))));
        this.basicHourlyStockQuoteListLastItemExpected =
                new DAOStockQuote(
                        stockSymbol,
                        new BigDecimal(123.00),
                        addHoursToDate(HOURS_IN_DAY - 1, java.sql.Date.valueOf(LocalDate.of(2018,9,20 ))));

        /*
          prepare to test getStockQuoteList on daily interval
         */
        this.basicDailyStockQuoteList = basicStockService.getStockQuoteList(stockSymbol, parseDateString("9/20/2018"), parseDateString("9/22/2018"), StockService.StockQuoteInterval.DAILY);

        this.basicDailyStockQuoteListExpected.add(
                new DAOStockQuote(
                        stockSymbol,
                        new BigDecimal(100.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,20 ))));
        this.basicDailyStockQuoteListExpected.add(
                new DAOStockQuote(
                        stockSymbol,
                        new BigDecimal(101.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,21 ))));
        this.basicDailyStockQuoteListExpected.add(
                new DAOStockQuote(
                        stockSymbol,
                        new BigDecimal(102.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,22 ))));
    }

    @Test
    public void testGetStockQuotePositive() {
        assertEquals("price is 100.00", this.stockPriceExpected, this.basicQuoteNow.getStockPrice());
    }

    @Test
    public void testGetStockQuoteNegative() {
        assertNotSame("price is not 100.00", this.stockPriceNotExpected, this.basicQuoteNow.getStockPrice());
    }

    @Test
    public void testGetStockQuoteWithDatePositive() {
        assertEquals("price is 100.00", this.stockPriceExpected, this.basicQuoteOnDate.getStockPrice());
    }

    @Test
    public void testGetStockQuoteWithDateNegative() {
        assertNotSame("price is not 100.00", this.stockPriceNotExpected, this.basicQuoteOnDate.getStockPrice());
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
        assertThat("stock quote list obtained does not equal empty stock quote list ", this.basicHourlyStockQuoteList, is(not(new ArrayList<DAOStockQuote>())));
        assertThat("first stock quote obtained does not equal tenth stock quote expected", this.basicHourlyStockQuoteList.get(0), is(not(this.basicHourlyStockQuoteListTenthItemExpected)));
    }

    @Test
    public void testGetDailyStockQuotePositive() {
        assertThat("stock quote list obtained equals stock quote list expected", this.basicDailyStockQuoteList, is(this.basicDailyStockQuoteListExpected));
    }

    @Test
    public void testGetDailyStockQuotesNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", this.basicDailyStockQuoteList, is(not(new ArrayList<DAOStockQuote>())));
    }
}

