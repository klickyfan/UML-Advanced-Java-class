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
import static org.junit.Assert.*;

/**
 * This class is for testing the BasicStockService class.
 *
 * @author Kim Jones
 */
public class BasicStockServiceTest {

    private String stockSymbol = "AAPL";
    private Date stockDate;

    private BigDecimal stockPriceExpected = BigDecimal.valueOf(100.0);
    private BigDecimal stockPriceNotExpected = BigDecimal.valueOf(1.0);

    private BasicStockService basicStockService;

    private StockQuote basicQuoteNow;
    private StockQuote basicQuoteOnDate;

    private Calendar from;
    private Calendar until;

    private List<StockQuote> basicStockQuoteList;
    private List<StockQuote> basicStockQuoteListExpected = new ArrayList<StockQuote>();

    @Before
    public void setUp() throws Exception {

        LocalDate localDate = LocalDate.of(2018, 9, 16);
        this.stockDate = java.sql.Date.valueOf(localDate);

        this.basicStockService = new BasicStockService();

        this.basicQuoteNow = basicStockService.getStockQuote(this.stockSymbol);
        this.basicQuoteOnDate = basicStockService.getStockQuote(this.stockSymbol, this.stockDate);

        this.from = StockQuoteApplication.parseDateString("9/20/2018");
        this.until = StockQuoteApplication.parseDateString("9/22/2018");

        this.basicStockQuoteList = basicStockService.getStockQuoteList(this.stockSymbol, this.from, this.until);

        this.basicStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(100.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,20 ))));
        this.basicStockQuoteListExpected.add(
                new StockQuote(
                        this.stockSymbol,
                        new BigDecimal(101.00),
                        java.sql.Date.valueOf(LocalDate.of(2018,9,21 ))));
        this.basicStockQuoteListExpected.add(
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
    public void testGetStockQuotesPositive() {
        assertThat("stock quote list obtained equals stock quote list expected", this.basicStockQuoteList, is(this.basicStockQuoteListExpected));
    }

    @Test
    public void testGetStockQuotesNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", this.basicStockQuoteList, is(not(new ArrayList<StockQuote>())));
    }
}

