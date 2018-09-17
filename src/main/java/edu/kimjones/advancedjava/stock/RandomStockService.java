package edu.kimjones.advancedjava.stock;

import java.math.BigDecimal;
import java.util.Date;

/**
 * This class models a stock service that returns a stock quote with a random price.
 *
 * @author Kim Jones
 */
public class RandomStockService implements StockService {

    /**
     * @param symbol        a symbol of a company
     * @param date          a date
     * @return              a stock quote for the company with the given symbol on the given date
     */
    public StockQuote getStockQuote(String symbol, Date date) {

        BigDecimal price = new BigDecimal(Math.random());

        StockQuote quote = new StockQuote(symbol, price, date);

        return quote;
    }
}
