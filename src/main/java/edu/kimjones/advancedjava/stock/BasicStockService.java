package edu.kimjones.advancedjava.stock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class models a basic stock service that always returns a stock quote with price 100.00.
 *
 * @author Kim Jones
 */
public class BasicStockService implements StockService {

    /**
     * This method gets a stock quote (containing the current price) for the company indicated by the given symbol.
     *
     * @param symbol    a stock symbol of a company, e.g. "APPL" for Apple
     * @return          a stock quote (containing the current price) for the company with the given symbol
     */
    public StockQuote getStockQuote(String symbol) {

        BigDecimal price = BigDecimal.valueOf(100.0);

        Calendar cal = Calendar.getInstance();

        StockQuote quote = new StockQuote(symbol, price, cal.getTime());

        return quote;
    }

    /**
     * This method gets a stock quote (containing the current price) for the company indicated by the given symbol on
     * the given date.
     *
     * @param symbol    a stock symbol of a company, e.g. "APPL" for Apple
     * @param date      a date
     * @return          a stock quote (containing the current price) for the company with the given symbol on the given
     *                  date
     */
    public StockQuote getStockQuote(String symbol, Date date) {

        BigDecimal price = BigDecimal.valueOf(100.0);

        StockQuote quote = new StockQuote(symbol, price, date);

        return quote;
    }

    /**
     * This function gets a list of stock quotes for the company indicated by the given symbol.
     *
     * @param symbol    a stock symbol of a company, e.g. "APPL" for Apple
     * @param from      the date of the first stock quote
     * @param until     the date of the last stock quote
     * @return          a list of stock quotes or the company with the given symbol, one for each day in the date range
     *                  given
     */
    public List<StockQuote> getStockQuoteList(String symbol, Calendar from, Calendar until) {

        BigDecimal startingPrice = BigDecimal.valueOf(100.0);

        // System.out.println(from);
        // System.out.println(until);

        List<StockQuote> stockQuoteList = new ArrayList<StockQuote>();

        if (!from.after(until)) { // stop if from is after than until

            until.add(Calendar.DATE, 1); // be sure to include until date (don't stop iteration before)

            int i = 0;

            for (Date date = from.getTime(); from.before(until); from.add(Calendar.DATE, 1), date = from.getTime()) {

                BigDecimal price = startingPrice.add(new BigDecimal(i));

                StockQuote quote = new StockQuote(symbol, price, date);
                stockQuoteList.add(quote);

                i++;
            }
        }

        return stockQuoteList;
    }
}

