package edu.kimjones.advancedjava.stock.services;

import edu.kimjones.advancedjava.stock.model.StockQuote;
import edu.kimjones.advancedjava.stock.model.database.DAOStockQuote;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static edu.kimjones.advancedjava.stock.utilities.TestUtility.parseDateString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

/**
 * This class is for testing the YahooStockServiceAdapter class.
 *
 * @author Kim Jones
 */
public class YahooStockServiceAdapterTest {

    private static YahooStockServiceAdapter stockService;

    private static final BigDecimal STOCK_PRICE_EXPECTED = BigDecimal.valueOf(227.26);
    private static final BigDecimal STOCK_PRICE_NOT_EXPECTED = BigDecimal.valueOf(-1.0);

    private static StockQuote quoteNow0;
    private static StockQuote quoteNow1;

    private static StockQuote quoteOnDate;

    private static List<StockQuote> dailyStockQuoteList;
    private static List<StockQuote> dailyStockQuoteListExpected = new ArrayList<>();

    @BeforeClass
    public static void setUp() throws Exception {

        LocalDate localDate = LocalDate.of(2018, 10, 1);
        Date stockDate = java.sql.Date.valueOf(localDate);

        stockService = new YahooStockServiceAdapter();

        /*
          prepare to test versions of getStockQuote that return a single quote
         */
        String stockSymbol = "AAPL";
        quoteNow0 = stockService.getLatestStockQuote(stockSymbol);
        quoteNow1 = stockService.getLatestStockQuote(stockSymbol);
        quoteOnDate = stockService.getStockQuote(stockSymbol, stockDate);

        /*
          prepare to test getStockQuoteList on daily interval
         */
        dailyStockQuoteList = stockService.getStockQuoteList(stockSymbol, parseDateString("10/1/2018"), parseDateString("10/3/2018"), StockService.StockQuoteInterval.DAILY);

        dailyStockQuoteListExpected.add(
                new StockQuote(
                        stockSymbol,
                        new BigDecimal(227.26).setScale(2, RoundingMode.HALF_UP),
                        java.sql.Date.valueOf(LocalDate.of(2018,10,1 ))));
        dailyStockQuoteListExpected.add(
                new StockQuote(
                        stockSymbol,
                        new BigDecimal(229.28).setScale(2, RoundingMode.HALF_UP),
                        java.sql.Date.valueOf(LocalDate.of(2018,10,2 ))));
        dailyStockQuoteListExpected.add(
                new StockQuote(
                        stockSymbol,
                        new BigDecimal(232.07).setScale(2, RoundingMode.HALF_UP),
                        java.sql.Date.valueOf(LocalDate.of(2018,10,3 ))));
    }

    @Test
    public void testGetStockQuotePositive() {
        assertEquals("prices returned by two sequential calls match", quoteNow0.getStockPrice(), quoteNow1.getStockPrice());
    }

    @Test
    public void testGetStockQuoteNegative() {
        assertNotSame("price is not 100.00", STOCK_PRICE_NOT_EXPECTED, quoteNow0.getStockPrice());
    }

    @Test
    public void testGetStockQuoteWithDatePositive() {
        assertEquals("price is 100.00", STOCK_PRICE_EXPECTED, quoteOnDate.getStockPrice());
    }

    @Test
    public void testGetStockQuoteWithDateNegative() {
        assertNotSame("price is not 100.00", STOCK_PRICE_NOT_EXPECTED, quoteOnDate.getStockPrice());
    }

    @Test
    public void testGetDailyStockQuotePositive() {
        assertThat("stock quote list obtained equals stock quote list expected", dailyStockQuoteList, is(dailyStockQuoteListExpected));
    }

    @Test
    public void testGetDailyStockQuotesNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", dailyStockQuoteList, is(not(new ArrayList<DAOStockQuote>())));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnsupportedOperationException() throws StockServiceException {
        stockService.addOrUpdateStockQuote("AAPL",new BigDecimal(100.00), java.sql.Date.valueOf(LocalDate.of(2018,10,1 )));

    }

    @Test(expected = StockServiceException.class)
    public void testStockServiceExceptionDueToUnsupportedInterval() throws ParseException,StockServiceException {
             stockService.getStockQuoteList(
                "AAPL",
                parseDateString("10/1/2018"),
                parseDateString("10/3/2018"),
                StockService.StockQuoteInterval.HOURLY);

    }
}

