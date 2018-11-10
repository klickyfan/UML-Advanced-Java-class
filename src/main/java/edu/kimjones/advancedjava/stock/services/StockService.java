package edu.kimjones.advancedjava.stock.services;

import edu.kimjones.advancedjava.stock.model.StockQuote;

import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This interface specifies what a stock service must do.
 *
 * @author Kim Jones (using code obtained from Prof. Spencer Marks)
 *
 * Note to Prof. Marks:
 * In thinking about the comments you made on page 23 of this week's class notes, I decided to rename some of the
 * methods in this interface to make it more clear what they are expect to return. In particular, I renamed the
 * getStockQuote method that does not take a date argument to getLatestStockQuote, and the two getStockQuote methods
 * that return lists of stock quotes to getStockQuoteList. I recognize that this may make it more difficult for you to
 * review my work, as you will not find methods by names you are expecting them to have. Please let me know if you
 * wish me to back out these particular changes. - Kim
 */
public interface StockService {

    /**
     *  This enum represents an interval on which stock quotes should be obtained.
     */
    @Immutable
    enum StockQuoteInterval {

        HOURLY ("HOURLY", Calendar.HOUR, 1, "DATE(time), HOUR(time)"),
        DAILY ("DAILY", Calendar.DATE, 1, "DATE(time)"),
        WEEKLY ("WEEKLY", Calendar.DATE, 7, "WEEK(time)"),
        MONTHLY ("MONTHLY", Calendar.MONTH, 1, "MONTH(time)");

        private final String string;
        private final int unitsOfInterval; // hours or days
        private final int incrementOfInterval; // number of unitsOfInterval in the interval
        private final String sqlGroupBy;

        StockQuoteInterval(String string, int units, int increment, String sqlGroupBy) {
            this.string = string;
            this.unitsOfInterval = units;
            this.incrementOfInterval = increment;
            this.sqlGroupBy = sqlGroupBy;
        }

        public String string() { return string; }
        public int unitsOfInterval() { return unitsOfInterval; }
        public int incrementOfInterval() { return incrementOfInterval; }
        public String sqlGroupBy() { return sqlGroupBy; }

        public static StockQuoteInterval fromString(String intervalString) {

            for (StockQuoteInterval interval : StockQuoteInterval .values()) {
                if (interval.string.equalsIgnoreCase(intervalString)) {
                    return interval;
                }
            }

            throw new IllegalArgumentException("No interval matching " + intervalString + " was found.");
        }
    }

    /**
     * This method gets a stock quote (containing the current price) for the company indicated by the given symbol.
     *
     * @param symbol                    a stock symbol of a company, e.g. "APPL" for Apple
     * @return                          an instance of {@code StockQuote} (containing the current price) for the
     *                                  company with the given symbol
     * @throws StockServiceException    if an exception occurs when trying to get the quote
     */
    StockQuote getLatestStockQuote(@NotNull String symbol) throws StockServiceException;

    /**
     * This method gets a stock quote (containing the current price) for the company indicated by the given symbol on
     * the given date.
     *
     * @param symbol                    a stock symbol of a company, e.g. "APPL" for Apple
     * @param date                      a date
     * @return                          instance of {@code StockQuote} (containing the current price) for the
     *                                  company with the given symbol on the given date
     * @throws StockServiceException    if an exception occurs when trying to get the quote
     */
    StockQuote getStockQuote(@NotNull String symbol, @NotNull java.util.Date date) throws StockServiceException;

    /**
     * This function gets a list of stock quotes for the company indicated by the given symbol, one for each day in the
     * given date range.
     *
     * @param symbol                    a stock symbol of a company, e.g. "APPL" for Apple
     * @param from                      the date of the first stock quote
     * @param until                     the date of the last stock quote
     * @return                          a list of stock quotes for the company with the given symbol, one for each day
     *                                  in the date range given
     * @throws StockServiceException    if an exception occurs when trying to get the quote
     */
    List<StockQuote> getStockQuoteList(@NotNull String symbol, @NotNull Calendar from, @NotNull Calendar until) throws StockServiceException;

    /**
     * This function gets a list of stock quotes for the company indicated by the given symbol, one for each period
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
    List<StockQuote> getStockQuoteList(@NotNull String symbol, @NotNull Calendar from, @NotNull Calendar until, @NotNull StockQuoteInterval interval) throws StockServiceException;

    /**
     * This method adds a new stock quote to the list of stocks already managed by the service, or updates a stock
     * already in that list.

     * @param stockSymbol               the symbol of a company
     * @param stockPrice                the price of that company's stock
     * @param dateRecorded              the date of the price
     *
     * @throws StockServiceException   if a service can not read or write the requested data or otherwise perform the
     *                                  requested operation
     */
    void addOrUpdateStockQuote(@NotNull String stockSymbol, @NotNull BigDecimal stockPrice, @NotNull Date dateRecorded) throws StockServiceException;
}

