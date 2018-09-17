package edu.kimjones.advancedjava.stock;

import java.util.Date;

/**
 * This interface specifies what a stock service must do.
 *
 * @author Kim Jones (using code obtained from Spencer Marks)
 */
public interface StockService {

    /**
     * @param symbol        a symbol of a company
     * @param date          a date
     * @return              a stock quote for the company with the given symbol on the given date
     */
    StockQuote getStockQuote(String symbol, Date date);
}
