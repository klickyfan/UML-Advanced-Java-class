package edu.kimjones.advancedjava.stock;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

/**
 * This class is for testing the BasicStockService class.
 *
 * @author Kim Jones
 */
public class BasicStockServiceTest {

    private final int MINUTES_IN_DAY = 1440;
    private final int HOURS_IN_DAY = 24;

    private String stockSymbol = "AAPL";

    private Date stockDate;

    private BigDecimal stockPriceExpected = BigDecimal.valueOf(100.0);
    private BigDecimal stockPriceNotExpected = BigDecimal.valueOf(1.0);

    private BasicStockService basicStockService;

    private StockQuote basicQuoteNow;
    private StockQuote basicQuoteOnDate;

    private List<StockQuote> basicOnMinuteStockQuoteList;
    private StockQuote basicOnMinuteStockQuoteListFirstItemExpected;
    private StockQuote basicOnMinuteStockQuoteListTenthItemExpected;
    private StockQuote basicOnMinuteStockQuoteListLastItemExpected;

    private List<StockQuote> basicHourlyStockQuoteList;
    private StockQuote basicHourlyStockQuoteListFirstItemExpected;
    private StockQuote basicHourlyStockQuoteListLastItemExpected;
    private StockQuote basicHourlyStockQuoteListTenthItemExpected;

    private List<StockQuote> basicSixHourStockQuoteList;
    private List<StockQuote> basicSixHourStockQuoteListExpected = new ArrayList<StockQuote>();

    private List<StockQuote> basicTwelveHourStockQuoteList;
    private List<StockQuote> basicTwelveHourStockQuoteListExpected = new ArrayList<StockQuote>();

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
        this.basicQuoteNow = basicStockService.getStockQuote(this.stockSymbol);

        this.basicQuoteOnDate = basicStockService.getStockQuote(this.stockSymbol, this.stockDate);

        /**
         * prepare to test getStockQuote on minute interval
         */
        this.basicOnMinuteStockQuoteList =
                basicStockService.getStockQuote(this.stockSymbol, parseDateString("9/20/2018"), parseDateString("9/20/2018"), StockService.StockQuoteInterval.EVERY_MINUTE);

        this.basicOnMinuteStockQuoteListFirstItemExpected =
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(100.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,20 )));
        this.basicOnMinuteStockQuoteListTenthItemExpected =
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(110.00),
                        addMinutesToDate(10, java.sql.Date.valueOf(LocalDate.of(2018,9,20 ))));
        this.basicOnMinuteStockQuoteListLastItemExpected =
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(1539.00),
                        addMinutesToDate(MINUTES_IN_DAY - 1, java.sql.Date.valueOf(LocalDate.of(2018,9,20 ))));

        /**
         * prepare to test getStockQuote on hour interval
         */
        this.basicHourlyStockQuoteList =
                basicStockService.getStockQuote(this.stockSymbol, parseDateString("9/20/2018"), parseDateString("9/20/2018"), StockService.StockQuoteInterval.HOURLY);

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
         * prepare to test getStockQuote on 6 hour interval
         */
        this.basicSixHourStockQuoteList =
                basicStockService.getStockQuote(this.stockSymbol, parseDateString("9/20/2018"), parseDateString("9/20/2018"), StockService.StockQuoteInterval.EVERY_SIX_HOURS);

        this.basicSixHourStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(100.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,20 ))));
        this.basicSixHourStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(101.00),
                        addHoursToDate(6, java.sql.Date.valueOf(LocalDate.of(2018,9,20 )))));
        this.basicSixHourStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(102.00),
                        addHoursToDate(12, java.sql.Date.valueOf(LocalDate.of(2018,9,20)))));
        this.basicSixHourStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(103.00),
                        addHoursToDate(18, java.sql.Date.valueOf(LocalDate.of(2018,9,20 )))));

        /**
         * prepare to test getStockQuote on 12 hour interval
         */
        this.basicTwelveHourStockQuoteList =
                basicStockService.getStockQuote(this.stockSymbol, parseDateString("9/20/2018"), parseDateString("9/20/2018"), StockService.StockQuoteInterval.EVERY_TWELVE_HOURS);

        this.basicTwelveHourStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(100.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,20 ))));
        this.basicTwelveHourStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(101.00),
                        addHoursToDate(12, java.sql.Date.valueOf(LocalDate.of(2018,9,20 )))));

        /**
         * prepare to test getStockQuote on daily interval
         */
        this.basicDailyStockQuoteList = basicStockService.getStockQuote(this.stockSymbol, parseDateString("9/20/2018"), parseDateString("9/22/2018"), StockService.StockQuoteInterval.DAILY);

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
    public void testGetOnMinuteStockQuotePositive() {

        assertThat("stock quote obtained equals stock quote expected", this.basicOnMinuteStockQuoteList.get(0), is(this.basicOnMinuteStockQuoteListFirstItemExpected));
        assertThat("tenth stock quote obtained equals stock quote expected", this.basicOnMinuteStockQuoteList.get(10), is(this.basicOnMinuteStockQuoteListTenthItemExpected));
        assertThat("last stock quote obtained equals stock quote expected", this.basicOnMinuteStockQuoteList.get(MINUTES_IN_DAY - 1), is(this.basicOnMinuteStockQuoteListLastItemExpected));

        assertEquals("quote list has 1440 items", this.basicOnMinuteStockQuoteList.size(), MINUTES_IN_DAY);
    }

    @Test
    public void testGetOnMinuteStockQuotesNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", this.basicOnMinuteStockQuoteList, is(not(new ArrayList<StockQuote>())));
        assertThat("first stock quote obtained does not equal tenth stock quote expected", this.basicOnMinuteStockQuoteList.get(0), is(not(this.basicOnMinuteStockQuoteListTenthItemExpected)));
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
    public void testSixHourGetStockQuotePositive() {
        assertThat("stock quote list obtained equals stock quote list expected", this.basicSixHourStockQuoteList, is(this.basicSixHourStockQuoteListExpected));
    }

    @Test
    public void testSixHourGetStockQuotesNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", this.basicSixHourStockQuoteList, is(not(new ArrayList<StockQuote>())));
    }

    @Test
    public void testTwelveHourGetStockQuotePositive() {
        assertThat("stock quote list obtained equals stock quote list expected", this.basicTwelveHourStockQuoteList, is(this.basicTwelveHourStockQuoteListExpected));
    }

    @Test
    public void testTwelveHourGetStockQuotesNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", this.basicTwelveHourStockQuoteList, is(not(new ArrayList<StockQuote>())));
    }

    @Test
    public void testDailyGetDailyStockQuotePositive() {
        assertThat("stock quote list obtained equals stock quote list expected", this.basicDailyStockQuoteList, is(this.basicDailyStockQuoteListExpected));
    }

    @Test
    public void testGetDailyStockQuotesNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", this.basicDailyStockQuoteList, is(not(new ArrayList<StockQuote>())));
    }

    /**
     * @param dateString        a string containing a date of the form "mm/dd/yyyy"
     * @return                  an instance of the Calendar class for that date
     * @throws ParseException   if the string given is not of the form "mm/dd/yyyy"
     */
    public static Calendar parseDateString(String dateString) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date = formatter.parse(dateString);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal;
    }

    /**
     *  @param  minutes         minutes to add
     *  @param  beforeDate      date that will have minutes added to it
     *  @return                 date object with the specified number of minutes added to it
     */
    private static Date addMinutesToDate(int minutes, Date beforeDate){

        final long ONE_MINUTE_IN_MILLISECONDS = 60000; // millisecs

        long currentTimeInMs = beforeDate.getTime();

        Date dateAfterAddingMinutes = new Date(currentTimeInMs + (minutes * ONE_MINUTE_IN_MILLISECONDS));

        return dateAfterAddingMinutes;
    }

    /**
     *  @param  hours           hours to add
     *  @param  beforeDate      date that will have hours added to it
     *  @return                 date object with the specified number of hours added to it
     */
    private static Date addHoursToDate(int hours, Date beforeDate){

        final long ONE_MINUTE_IN_MILLISECONDS = 60000; // millisecs

        long currentTimeInMs = beforeDate.getTime();
        long timeAfterAddingHours = currentTimeInMs + (hours * 60 * ONE_MINUTE_IN_MILLISECONDS);

        Date dateAfterAddingHours = new Date(timeAfterAddingHours);

        return dateAfterAddingHours;
    }
}

