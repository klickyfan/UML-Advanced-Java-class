package edu.kimjones.advancedjava.stock;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This interface specifies what a stock service must do.
 *
 * @author Kim Jones (using code obtained from Spencer Marks)
 */
public interface StockService {

    /**
     * This method gets a stock quote (containing the current price) for the company indicated by the given symbol.
     *
     * @param symbol    a stock symbol of a company, e.g. "APPL" for Apple
     * @return          a stock quote (containing the current price) for the company with the given symbol
     */
    StockQuote getStockQuote(String symbol);

    /**
     * This method gets a stock quote (containing the current price) for the company indicated by the given symbol on
     * the given date.
     *
     * @param symbol    a stock symbol of a company, e.g. "APPL" for Apple
     * @param date      a date
     * @return          a stock quote (containing the current price) for the company with the given symbol on the given
     *                  date
     */
    StockQuote getStockQuote(String symbol, Date date);

    /**
     * This function gets a list of stock quotes for the company indicated by the given symbol.
     *
     * @param symbol    a stock symbol of a company, e.g. "APPL" for Apple
     * @param from      the date of the first stock quote
     * @param until     the date of the last stock quote
     * @return          a list of stock quotes or the company with the given symbol, one for each day in the date range
     *                  given
     */
    List<StockQuote> getStockQuoteList(String symbol, Calendar from, Calendar until);

}
