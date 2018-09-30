package edu.kimjones.advancedjava.stock;

import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotNull;
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
     *  This enum represents an interval on which stock quotes should be obtained.
     */
    @Immutable
    public enum StockQuoteInterval {

        EVERY_MINUTE (Calendar.MINUTE, 1),
        HOURLY (Calendar.HOUR, 1),
        EVERY_SIX_HOURS (Calendar.HOUR, 6),
        EVERY_TWELVE_HOURS (Calendar.HOUR, 12),
        DAILY (Calendar.DATE, 1);

        private final int unitsOfInterval;
        private final int incrementOfInterval;

        StockQuoteInterval(int units, int increment) {
            this.unitsOfInterval = units;
            this.incrementOfInterval = increment;
        }

        public int unitsOfInterval() { return unitsOfInterval; }
        public int incrementOfInterval() { return incrementOfInterval; }
    }

    /**
     * This method gets a stock quote (containing the current price) for the company indicated by the given symbol.
     *
     * @param symbol    a stock symbol of a company, e.g. "APPL" for Apple
     * @return          an instance of <CODE>StockQuote</CODE>(containing the current price) for the company with the
     *                  given symbol
     */
    StockQuote getStockQuote(@NotNull String symbol);

    /**
     * This method gets a stock quote (containing the current price) for the company indicated by the given symbol on
     * the given date.
     *
     * @param symbol    a stock symbol of a company, e.g. "APPL" for Apple
     * @param date      a date
     * @return          instance of <CODE>StockQuote</CODE>(containing the current price) for the company with the given
     *                  symbol on the given date
     */
    StockQuote getStockQuote(@NotNull String symbol, @NotNull Date date);

    /**
     * This function gets a list of stock quotes for the company indicated by the given symbol, one for each day in the
     * given date range.
     *
     * @param symbol    a stock symbol of a company, e.g. "APPL" for Apple
     * @param from      the date of the first stock quote
     * @param until     the date of the last stock quote
     * @return          a list of stock quotes for the company with the given symbol, one for each day in the date range
     *                  given
     */
    List<StockQuote> getStockQuote(@NotNull String symbol, @NotNull Calendar from, @NotNull Calendar until);

    /**
     * This function gets a list of stock quotes for the company indicated by the given symbol, one for each period
     * in the given interval in the given date range.
     *
     * @param symbol    a stock symbol of a company, e.g. "APPL" for Apple
     * @param from      the date of the first stock quote
     * @param until     the date of the last stock quote
     * @param interval  the interval between which stock quotes should be obtained, i.e. if DAILY, then one per day
     * @return          a list of stock quotes for the company with the given symbol, one for each period in the
     *                  given interval in the given date range
     */
    List<StockQuote> getStockQuote(@NotNull String symbol, @NotNull Calendar from, @NotNull Calendar until, @NotNull StockQuoteInterval interval);
}
