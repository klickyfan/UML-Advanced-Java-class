package edu.kimjones.advancedjava.stock;

import java.math.BigDecimal;
import java.util.Date;

/**
 * This class models a stpid stock service that always returns a stock quote with price 100.00.
 *
 * @author Kim Jones
 */
public class StupidStockService implements StockService {

    /**
     * @param symbol    a symbol of a company
     * @param date      a date
     * @return          a stock quote for the company with the given symbol on the given date
     */
    public StockQuote getStockQuote(String symbol, Date date) {

        BigDecimal price = BigDecimal.valueOf(100.0);

        StockQuote quote = new StockQuote(symbol, price, date);

        return quote;
    }
}
