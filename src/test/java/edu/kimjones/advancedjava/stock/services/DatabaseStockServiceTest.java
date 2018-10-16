package edu.kimjones.advancedjava.stock.services;

import edu.kimjones.advancedjava.stock.model.StockQuote;
import edu.kimjones.advancedjava.stock.utilities.DatabaseUtility;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static edu.kimjones.advancedjava.stock.utilities.TestUtility.parseDateString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

/**
 * This class is for testing the DatabaseStockService class.
 *
 * @author Kim Jones
 */
public class DatabaseStockServiceTest {

    private String stockSymbol = "OOOO"; // this is an untaken stock symbol

    private Date stockDate;

    private BigDecimal latestStockPriceExpected = BigDecimal.valueOf(84.61).setScale(2, RoundingMode.HALF_UP);
    private BigDecimal stockPriceExpected = BigDecimal.valueOf(118.55).setScale(2, RoundingMode.HALF_UP);
    private BigDecimal stockPriceNotExpected = BigDecimal.valueOf(1.0).setScale(2, RoundingMode.HALF_UP);

    private DatabaseStockService databaseStockService;

    private StockQuote databaseQuoteNow;
    private StockQuote databaseQuoteOnDate;

    private List<StockQuote> databaseHourlyStockQuoteList;
    private StockQuote databaseHourlyStockQuoteListFirstItemExpected;
    private StockQuote databaseHourlyStockQuoteListSecondItemExpected;
    private StockQuote databaseHourlyStockQuoteListLastItemExpected;

    private List<StockQuote> databaseDailyStockQuoteList;
    private List<StockQuote> databaseDailyStockQuoteListExpected = new ArrayList<StockQuote>();

    @Before
    public void setUp() throws Exception {

        DatabaseUtility.initializeDatabase(DatabaseUtility.initializationFile);
        DatabaseUtility.initializeDatabase("./src/main/sql/add_stock_service_test_data.sql");

        this.databaseStockService = new DatabaseStockService();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.stockDate = dateFormat.parse("2018-09-21 00:00");

        /**
         * prepare to test versions of getStockQuote that return a single quote
         */
        this.databaseQuoteNow = databaseStockService.getLatestStockQuote(this.stockSymbol);

        this.databaseQuoteOnDate = databaseStockService.getStockQuote(this.stockSymbol, this.stockDate);

        /**
         * prepare to test getStockQuoteList on hour interval
         */
        this.databaseHourlyStockQuoteList =
                databaseStockService.getStockQuoteList(this.stockSymbol, parseDateString("9/20/2018"), parseDateString("9/21/2018"), StockService.StockQuoteInterval.HOURLY);

        this.databaseHourlyStockQuoteListFirstItemExpected =
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(118.88).setScale(2, RoundingMode.HALF_UP), // see add_stock_service_test_data.sql
                        dateFormat.parse("2018-09-20 10:00"));
        this.databaseHourlyStockQuoteListSecondItemExpected =
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(117.58).setScale(2, RoundingMode.HALF_UP),
                        dateFormat.parse("2018-09-20 11:00"));
        this.databaseHourlyStockQuoteListLastItemExpected =
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(118.55).setScale(2, RoundingMode.HALF_UP),
                        dateFormat.parse("2018-09-20 16:00"));

        /**
         * prepare to test getStockQuoteList on daily interval
         */
        this.databaseDailyStockQuoteList = databaseStockService.getStockQuoteList(this.stockSymbol, parseDateString("10/1/2018"), parseDateString("10/3/2018"), StockService.StockQuoteInterval.DAILY);

        this.databaseDailyStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(84.46).setScale(2, RoundingMode.HALF_UP),
                        dateFormat.parse("2018-10-01 00:00")));
        this.databaseDailyStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(82.77).setScale(2, RoundingMode.HALF_UP),
                        dateFormat.parse("2018-10-02 00:00")));
        this.databaseDailyStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(83.91).setScale(2, RoundingMode.HALF_UP),
                        dateFormat.parse("2018-10-03 00:00")));

    }

    @After
    public void tearDown() throws Exception {
        DatabaseUtility.initializeDatabase(DatabaseUtility.initializationFile);
    }

    @Test
    public void testGetLatestStockQuotePositive() {
        assertEquals("price is " + this.latestStockPriceExpected, this.latestStockPriceExpected, this.databaseQuoteNow.getStockPrice());
    }

    @Test
    public void testGetLatestStockQuoteNegative() {
        assertFalse("price is not " + this.latestStockPriceExpected, this.stockPriceNotExpected == this.databaseQuoteNow.getStockPrice());
    }

    @Test
    public void testGetStockQuoteWithDatePositive() {
        assertEquals("price is " + this.stockPriceExpected, this.stockPriceExpected, this.databaseQuoteOnDate.getStockPrice());
    }

    @Test
    public void testGetStockQuoteWithDateNegative() {
        assertFalse("price is not " + this.stockPriceExpected, this.stockPriceNotExpected == this.databaseQuoteOnDate.getStockPrice());
    }

    @Test
    public void testGetHourlyStockQuotesPositive() {
        assertEquals("stock quote obtained equals stock quote expected", this.databaseHourlyStockQuoteList.get(0), this.databaseHourlyStockQuoteListFirstItemExpected);
        assertEquals("second stock quote obtained equals stock quote expected", this.databaseHourlyStockQuoteList.get(1), this.databaseHourlyStockQuoteListSecondItemExpected);
        assertEquals("last stock quote obtained equals stock quote expected", this.databaseHourlyStockQuoteList.get(6), this.databaseHourlyStockQuoteListLastItemExpected);

        assertEquals("quote list has 7 items", this.databaseHourlyStockQuoteList.size(), 7);
    }

    @Test
    public void testGetHourlyStockQuotesNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", this.databaseHourlyStockQuoteList, is(not(new ArrayList<StockQuote>())));
        assertThat("first stock quote obtained does not equal tenth stock quote expected", this.databaseHourlyStockQuoteList.get(0), is(not(this.databaseHourlyStockQuoteListSecondItemExpected)));
    }

    @Test
    public void testGetDailyStockQuotePositive() {
        assertThat("stock quote list obtained equals stock quote list expected", this.databaseDailyStockQuoteList, is(this.databaseDailyStockQuoteListExpected));
    }

    @Test
    public void testGetDailyStockQuotesNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", this.databaseDailyStockQuoteList, is(not(new ArrayList<StockQuote>())));
    }
}

