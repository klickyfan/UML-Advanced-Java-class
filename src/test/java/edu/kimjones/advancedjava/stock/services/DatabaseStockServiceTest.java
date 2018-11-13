package edu.kimjones.advancedjava.stock.services;

import edu.kimjones.advancedjava.stock.model.StockQuote;
import edu.kimjones.advancedjava.stock.model.database.DAOStockQuote;
import edu.kimjones.advancedjava.stock.utilities.DatabaseUtility;

import org.junit.AfterClass;
import org.junit.BeforeClass;
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

    private static final BigDecimal LATEST_STOCK_PRICE_EXPECTED = BigDecimal.valueOf(84.61).setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal STOCK_PRICE_EXPECTED = BigDecimal.valueOf(82.35).setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal STOCK_PRICE_NOT_EXPECTED = BigDecimal.valueOf(1.0).setScale(2, RoundingMode.HALF_UP);

    private static StockService stockService;

    private static StockQuote databaseQuoteNow;
    private static StockQuote databaseQuoteOnDate;
    
    private static List<StockQuote> databaseHourlyStockQuoteList;
    private static StockQuote databaseHourlyStockQuoteListFirstItemExpected;
    private static StockQuote databaseHourlyStockQuoteListSecondItemExpected;
    private static StockQuote databaseHourlyStockQuoteListLastItemExpected;

    private static List<StockQuote> databaseDailyStockQuoteList;
    private static ArrayList<StockQuote> databaseDailyStockQuoteListExpected = new ArrayList<>();

    @BeforeClass
    public static void setUp() throws Exception {

        DatabaseUtility.initializeDatabase(DatabaseUtility.INITIALIZATION_FILE);
        DatabaseUtility.initializeDatabase("./src/main/sql/add_stock_service_test_data.sql");

        stockService = new DatabaseStockService();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date stockDate = dateFormat.parse("2018-10-09 00:00");

        /*
          prepare to test versions of getStockQuote that return a single quote
         */

        // this is an untaken stock symbol
        String stockSymbol = "OOOO";
        databaseQuoteNow = stockService.getLatestStockQuote(stockSymbol);
        databaseQuoteOnDate = stockService.getStockQuote(stockSymbol, stockDate);

        /*
          prepare to test getStockQuoteList on hour interval
         */

        databaseHourlyStockQuoteList =
                stockService.getStockQuoteList(stockSymbol, parseDateString("9/20/2018"), parseDateString("9/21/2018"), StockService.StockQuoteInterval.HOURLY);

        databaseHourlyStockQuoteListFirstItemExpected =
                new StockQuote(
                        stockSymbol,
                        new BigDecimal(118.88).setScale(2, RoundingMode.HALF_UP), // see add_stock_service_test_data.sql
                        dateFormat.parse("2018-09-20 10:00"));
        databaseHourlyStockQuoteListSecondItemExpected =
                new StockQuote(
                        stockSymbol,
                        new BigDecimal(117.58).setScale(2, RoundingMode.HALF_UP),
                        dateFormat.parse("2018-09-20 11:00"));
        databaseHourlyStockQuoteListLastItemExpected =
                new StockQuote(
                        stockSymbol,
                        new BigDecimal(118.55).setScale(2, RoundingMode.HALF_UP),
                        dateFormat.parse("2018-09-20 16:00"));

        /*
          prepare to test getStockQuoteList on daily interval
         */

        databaseDailyStockQuoteList = stockService.getStockQuoteList(stockSymbol, parseDateString("10/1/2018"), parseDateString("10/3/2018"), StockService.StockQuoteInterval.DAILY);

        databaseDailyStockQuoteListExpected.add(
                new StockQuote(
                        stockSymbol,
                        new BigDecimal(84.46).setScale(2, RoundingMode.HALF_UP),
                        dateFormat.parse("2018-10-01 00:00")));
        databaseDailyStockQuoteListExpected.add(
                new StockQuote(
                        stockSymbol,
                        new BigDecimal(82.77).setScale(2, RoundingMode.HALF_UP),
                        dateFormat.parse("2018-10-02 00:00")));
        databaseDailyStockQuoteListExpected.add(
                new StockQuote(
                        stockSymbol,
                        new BigDecimal(83.91).setScale(2, RoundingMode.HALF_UP),
                        dateFormat.parse("2018-10-03 00:00")));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        DatabaseUtility.initializeDatabase(DatabaseUtility.INITIALIZATION_FILE);
    }

    @Test
    public void testGetLatestStockQuotePositive() {
        assertEquals("price is " + LATEST_STOCK_PRICE_EXPECTED, LATEST_STOCK_PRICE_EXPECTED, databaseQuoteNow.getStockPrice());
    }

    @Test
    public void testGetLatestStockQuoteNegative() {
        assertNotSame("price is not " + LATEST_STOCK_PRICE_EXPECTED, STOCK_PRICE_NOT_EXPECTED, databaseQuoteNow.getStockPrice());
    }

    @Test
    public void testGetStockQuoteWithDatePositive() {
       assertEquals("price is " + STOCK_PRICE_EXPECTED, STOCK_PRICE_EXPECTED, databaseQuoteOnDate.getStockPrice());
    }

    @Test
    public void testGetStockQuoteWithDateNegative() {
        assertNotSame("price is not " + STOCK_PRICE_EXPECTED, STOCK_PRICE_NOT_EXPECTED, databaseQuoteOnDate.getStockPrice());
    }

    @Test
    public void testGetHourlyStockQuotesPositive() {
        assertEquals("stock quote obtained equals stock quote expected", databaseHourlyStockQuoteList.get(0), databaseHourlyStockQuoteListFirstItemExpected);
        assertEquals("second stock quote obtained equals stock quote expected", databaseHourlyStockQuoteList.get(1), databaseHourlyStockQuoteListSecondItemExpected);
        assertEquals("last stock quote obtained equals stock quote expected", databaseHourlyStockQuoteList.get(6), databaseHourlyStockQuoteListLastItemExpected);

        assertEquals("quote list has 7 items", databaseHourlyStockQuoteList.size(), 7);
    }

    @Test
    public void testGetHourlyStockQuotesNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", databaseHourlyStockQuoteList, is(not(new ArrayList<DAOStockQuote>())));
        assertThat("first stock quote obtained does not equal tenth stock quote expected", databaseHourlyStockQuoteList.get(0), is(not(databaseHourlyStockQuoteListSecondItemExpected)));
    }

    @Test
    public void testGetDailyStockQuotePositive() {
        assertThat("stock quote list obtained equals stock quote list expected", databaseDailyStockQuoteList, is(databaseDailyStockQuoteListExpected));
    }

    @Test
    public void testGetDailyStockQuotesNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", databaseDailyStockQuoteList, is(not(new ArrayList<DAOStockQuote>())));
    }

    @Test
    public void testAddOrUpdateStockQuote() throws Exception {
        String symbol = "DIS";
        BigDecimal price = new BigDecimal(100.00).setScale(2, RoundingMode.HALF_UP);

        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = dateFormat.parse("2018-10-22 12:00:00");

        StockQuote testStockQuote = new StockQuote(symbol, price, date);

        stockService.addOrUpdateStockQuote(symbol, price, date);

        List<StockQuote> stockQuoteList =
                stockService.getStockQuoteList(symbol, parseDateString("10/22/2018"), parseDateString("10/23/2018"), StockService.StockQuoteInterval.DAILY);

        boolean found = false;
        for (StockQuote stockQuote : stockQuoteList) {
            if (stockQuote.equals(testStockQuote)) {
                found = true;
                break;
            }
        }

        assertTrue("found the testStockQuote we added", found);
    }
}

