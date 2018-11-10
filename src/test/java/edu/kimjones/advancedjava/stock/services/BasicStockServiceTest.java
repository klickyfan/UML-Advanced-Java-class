package edu.kimjones.advancedjava.stock.services;

import edu.kimjones.advancedjava.stock.model.StockQuote;
import edu.kimjones.advancedjava.stock.model.database.DAOStockQuote;

import org.junit.BeforeClass;
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

    private static final int HOURS_IN_DAY = 24;

    private static final BigDecimal STOCK_PRICE_EXPECTED = BigDecimal.valueOf(100.0);
    private static final BigDecimal STOCK_PRICE_NOT_EXPECTED = BigDecimal.valueOf(1.0);

    private static StockQuote basicQuoteNow;
    private static StockQuote basicQuoteOnDate;

    private static List<StockQuote> basicHourlyStockQuoteList;
    private static StockQuote basicHourlyStockQuoteListFirstItemExpected;
    private static StockQuote basicHourlyStockQuoteListLastItemExpected;
    private static StockQuote basicHourlyStockQuoteListTenthItemExpected;

    private static List<StockQuote> basicDailyStockQuoteList;
    private static List<StockQuote> basicDailyStockQuoteListExpected = new ArrayList<>();

    @BeforeClass
    public static void setUp() throws Exception {
        LocalDate localDate = LocalDate.of(2018, 9, 16);
        Date stockDate = java.sql.Date.valueOf(localDate);

        BasicStockService basicStockService = new BasicStockService();

        /*
          prepare to test versions of getStockQuote that return a single quote
         */
        String stockSymbol = "AAPL";
        basicQuoteNow = basicStockService.getLatestStockQuote(stockSymbol);

        basicQuoteOnDate = basicStockService.getStockQuote(stockSymbol, stockDate);

        /*
          prepare to test getStockQuoteList on hour interval
         */
        basicHourlyStockQuoteList =
                basicStockService.getStockQuoteList(stockSymbol, parseDateString("9/20/2018"), parseDateString("9/20/2018"), StockService.StockQuoteInterval.HOURLY);

        basicHourlyStockQuoteListFirstItemExpected =
                new StockQuote(
                        stockSymbol,
                        new BigDecimal(100.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,20 )));
        basicHourlyStockQuoteListTenthItemExpected =
                new StockQuote(
                        stockSymbol,
                        new BigDecimal(110.00),
                        addHoursToDate(10, java.sql.Date.valueOf(LocalDate.of(2018,9,20 ))));
        basicHourlyStockQuoteListLastItemExpected =
                new StockQuote(
                        stockSymbol,
                        new BigDecimal(123.00),
                        addHoursToDate(HOURS_IN_DAY - 1, java.sql.Date.valueOf(LocalDate.of(2018,9,20 ))));

        /*
          prepare to test getStockQuoteList on daily interval
         */
        basicDailyStockQuoteList = basicStockService.getStockQuoteList(stockSymbol, parseDateString("9/20/2018"), parseDateString("9/22/2018"), StockService.StockQuoteInterval.DAILY);

        basicDailyStockQuoteListExpected.add(
                new StockQuote(
                        stockSymbol,
                        new BigDecimal(100.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,20 ))));
        basicDailyStockQuoteListExpected.add(
                new StockQuote(
                        stockSymbol,
                        new BigDecimal(101.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,21 ))));
        basicDailyStockQuoteListExpected.add(
                new StockQuote(
                        stockSymbol,
                        new BigDecimal(102.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,22 ))));
    }

    @Test
    public void testGetStockQuotePositive() {
        assertEquals("price is 100.00", STOCK_PRICE_EXPECTED, basicQuoteNow.getStockPrice());
    }

    @Test
    public void testGetStockQuoteNegative() {
        assertNotSame("price is not 100.00", STOCK_PRICE_NOT_EXPECTED, basicQuoteNow.getStockPrice());
    }

    @Test
    public void testGetStockQuoteWithDatePositive() {
        assertEquals("price is 100.00", STOCK_PRICE_EXPECTED, basicQuoteOnDate.getStockPrice());
    }

    @Test
    public void testGetStockQuoteWithDateNegative() {
        assertNotSame("price is not 100.00", STOCK_PRICE_NOT_EXPECTED, basicQuoteOnDate.getStockPrice());
    }

    @Test
    public void testGetHourlyStockQuotePositive() {
        assertThat("stock quote obtained equals stock quote expected", basicHourlyStockQuoteList.get(0), is(basicHourlyStockQuoteListFirstItemExpected));
        assertThat("tenth stock quote obtained equals stock quote expected", basicHourlyStockQuoteList.get(10), is(basicHourlyStockQuoteListTenthItemExpected));
        assertThat("last stock quote obtained equals stock quote expected", basicHourlyStockQuoteList.get(HOURS_IN_DAY - 1), is(basicHourlyStockQuoteListLastItemExpected));

        assertEquals("quote list has 24 items", basicHourlyStockQuoteList.size(), HOURS_IN_DAY);
    }

    @Test
    public void testGetHourlyStockQuotesNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", basicHourlyStockQuoteList, is(not(new ArrayList<DAOStockQuote>())));
        assertThat("first stock quote obtained does not equal tenth stock quote expected", basicHourlyStockQuoteList.get(0), is(not(basicHourlyStockQuoteListTenthItemExpected)));
    }

    @Test
    public void testGetDailyStockQuotePositive() {
        assertThat("stock quote list obtained equals stock quote list expected", basicDailyStockQuoteList, is(basicDailyStockQuoteListExpected));
    }

    @Test
    public void testGetDailyStockQuotesNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", basicDailyStockQuoteList, is(not(new ArrayList<DAOStockQuote>())));
    }
}

