package edu.kimjones.advancedjava.stock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class models a stock ticker.
 *
 * @author Kim Jones (using code obtained from Spencer Marks)
 */
public class StockTicker {

    private StockService stockService;

    /**
     * This constructor creates a new <CODE>StockTicker</CODE> instance
     *
     * @param stockService      class from which to get actual stock data (external dependency)
     */
    public StockTicker(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * get a list containing stock quotes for each of the days in the date range specified
     *
     * @param symbol        the symbol of the company whose stock quote you want
     * @param startDate     the first day on which to get a quote
     * @param endDate       the last day on which to get a quote
     * @return              a list of <CODE>StockData</CODE> instances, one for each day in the given date range
     */
    public List<StockQuote> getStockHistory(String symbol, Date startDate, Date endDate) {

        Date date = startDate;
        List<StockQuote> returnValue = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        while (date.before(endDate)) {
            returnValue.add(stockService.getStockQuote(symbol, date));
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            date = calendar.getTime();
        }
        return returnValue;
    }
}
