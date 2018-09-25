package edu.kimjones.advancedjava.stock;

import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * This class is for testing the StockTicker class
 *
 * @author Kim Jones and Spencer Marks
 */
public class StockTickerTest {

    /**
     * This method verifies (using mocking) that the correct number of days is returned
     * by the StockTicker getStockHistory method
     */
    @Test
    public void testGetStockHistoryNumberOfDays() {

        /*
         * we want to 'mock' the external dependency which is the StockService so that we can test just
         * the StockTicker class
         */

        // set up the mock
        StockService stockServiceMock = Mockito.mock(StockService.class);

        // create the data we expect the service to return
        Date endDate = Calendar.getInstance().getTime();
        Calendar startCalendar = Calendar.getInstance();
        int NumberOfDayOfData = 15;
        startCalendar.add(Calendar.DAY_OF_YEAR,-NumberOfDayOfData);
        Date startDate = startCalendar.getTime();

        String stockSymbol = "APPL";
        BigDecimal expectedPrice = new BigDecimal(100);

        // tell the mock service to return the data the getQuote() method is called with a specific symbol
        when(stockServiceMock.getStockQuote(any(String.class),any(Date.class))).thenReturn(new StockQuote(stockSymbol, expectedPrice, Calendar.getInstance().getTime()));

        // create the StickTicker instance to test
        StockTicker stockTicker = new StockTicker(stockServiceMock);

        // execute the method we want to test
        List<StockQuote> stockHistory = stockTicker.getStockHistory(stockSymbol, startDate,endDate);

        // verify that it returned the expected results

        // there should 15 days of data
        assertTrue("There should be 15 days of data", stockHistory.size() == NumberOfDayOfData);
    }
}


