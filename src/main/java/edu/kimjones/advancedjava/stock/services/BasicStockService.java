package edu.kimjones.advancedjava.stock.services;

import edu.kimjones.advancedjava.stock.model.StockQuote;

import javax.validation.constraints.NotNull;
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
     * @param symbol                    a stock symbol of a company, e.g. "APPL" for Apple
     * @return                          an instance of {@code StockQuote} (containing the current price) for the
     *                                  company with the given symbol
     * @throws StockServiceException    if an exception occurs when trying to get the quote
     */
    @Override
    public StockQuote getLatestStockQuote(@NotNull String symbol) throws StockServiceException {

        BigDecimal price = BigDecimal.valueOf(100.0); // fake price (for now)

        Calendar cal = Calendar.getInstance();

        StockQuote quote = new StockQuote(symbol, price, cal.getTime());

        return quote;
    }

    /**
     * This method gets a stock quote (containing the current price) for the company indicated by the given symbol on
     * the given date.
     *
     * @param symbol                    a stock symbol of a company, e.g. "APPL" for Apple
     * @param date                      a date
     * @return                          instance of <CODE>StockQuote</CODE>(containing the current price) for the
     *                                  company with the given symbol on the given date
     * @throws StockServiceException    if an exception occurs when trying to get the quote
     */
    @Override
    public StockQuote getStockQuote(@NotNull String symbol, @NotNull Date date) throws StockServiceException {

        BigDecimal price = BigDecimal.valueOf(100.0); // fake price (for now)

        StockQuote quote = new StockQuote(symbol, price, date);

        return quote;
    }

    /**
     * This method gets a list of stock quotes for the company indicated by the given symbol, one for each day in the
     * given date range.
     *
     * @param symbol                    a stock symbol of a company, e.g. "APPL" for Apple
     * @param from                      the date of the first stock quote
     * @param until                     the date of the last stock quote
     * @return                          a list of stock quotes for the company with the given symbol, one for each day
     *                                  in the date range given
     * @throws StockServiceException    if an exception occurs when trying to get the quote
     */
    @Override
    public List<StockQuote> getStockQuoteList(@NotNull String symbol, @NotNull Calendar from, @NotNull Calendar until) throws StockServiceException {

        List<StockQuote> stockQuoteList = new ArrayList<StockQuote>();

        if (!from.after(until)) { // stop if from is after than until

            constructStockQuoteList(stockQuoteList, symbol, from, until, StockQuoteInterval.DAILY);
        }

        return stockQuoteList;
    }

    /**
     * This method gets a list of stock quotes for the company indicated by the given symbol, one for each period
     * in the given interval in the given date range.
     *
     * @param symbol                    a stock symbol of a company, e.g. "APPL" for Apple
     * @param from                      the date of the first stock quote
     * @param until                     the date of the last stock quote
     * @param interval                  the interval between which stock quotes should be obtained, i.e. if DAILY, then
     *                                  one per day
     * @return                          a list of stock quotes for the company with the given symbol, one for each
     *                                  period in the given interval in the given date range
     * @throws StockServiceException    if an exception occurs when trying to get the quote
     */
    @Override
    public List<StockQuote> getStockQuoteList(@NotNull String symbol, @NotNull Calendar from, @NotNull Calendar until, @NotNull StockQuoteInterval interval) throws StockServiceException {

        List<StockQuote> stockQuoteList = new ArrayList<StockQuote>();

        if (!from.after(until)) { // stop if from is after than until

            constructStockQuoteList(stockQuoteList, symbol, from, until, interval);
        }

        return stockQuoteList;
    }

    /**
     * This method does the work needed by {@code}List<StockQuote>getStockQuote(...} to construct a list of
     * {@code StockQuote} instances for a company with the given symbol, in the given date range and on the given
     * interval.
     *
     * @param stockQuoteList    the list we are building out
     * @param symbol            a stock symbol of a company, e.g. "APPL" for Apple
     * @param from              the date of the first stock quote
     * @param until             the date of the last stock quote
     * @param interval          the interval between which stock quotes should be obtained, i.e. if DAILY, then one per day
     */
    private void constructStockQuoteList(
            @NotNull List<StockQuote> stockQuoteList,
            @NotNull String symbol,
            @NotNull Calendar from,
            @NotNull Calendar until,
            @NotNull StockQuoteInterval interval) {

        Calendar safeUntil = (Calendar) until.clone();

        // be sure to include quotes on until date
        safeUntil.add(Calendar.DATE, 1);

        BigDecimal startingPrice = BigDecimal.valueOf(100.0); // the fake price to assign to the first stock quote

        /**
         * This BasicStockService is a work-in-progress, and does not yet have the ability to return correct quote
         * pricing. Instead it returns a value equal to the <CODE>startingPrice</CODE> initialized above, plus a
         * <CODE>valueToAddToStartingPrice</CODE>, an int that is incremented each time another quote is created in the
         * loop below. This variable will go away in future versions of this method.
         */
        int valueToAddToStartingPrice = 0;

        for (Date date = from.getTime(); from.before(safeUntil); from.add(interval.unitsOfInterval(), interval.incrementOfInterval()), date = from.getTime()) {

            // prepare fake price
            BigDecimal price = startingPrice.add(new BigDecimal(valueToAddToStartingPrice));

            // create quote with symbol, fake price, and date
            StockQuote quote = new StockQuote(symbol, price, date);

            // add quote to list
            stockQuoteList.add(quote);

            // prepare increment that will be added to startingPrice on next iteration to create new fake price
            valueToAddToStartingPrice++;
        }
    }
}
