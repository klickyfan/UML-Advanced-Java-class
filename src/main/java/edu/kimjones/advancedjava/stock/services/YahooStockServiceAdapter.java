package edu.kimjones.advancedjava.stock.services;

import edu.kimjones.advancedjava.stock.model.StockQuote;

import yahoofinance.Stock;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import javax.validation.constraints.NotNull;

import java.io.IOException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class models a stock service that gets stock data from the Yahoo Finance web service
 * (https://financequotes-api.com/). It uses the adapter pattern to enable {@code YahooStockService} to be used as a
 * stock service, though it does not implement the {@code StockService} interface.
 *
 * @author Kim Jones
 */
public class YahooStockServiceAdapter implements StockService {

    private YahooStockService stockService = new YahooStockService();

    /**
     * Gets a stock quote (containing the current price) for the company indicated by the given symbol.
     *
     * @param symbol                    a stock symbol of a company, e.g. "APPL" for Apple
     * @return                          an instance of {@code DAOStockQuote} (containing the current price) for the
     *                                  company with the given symbol
     * @throws StockServiceException    if an exception occurs when trying to get the quote
     */
    @Override
    public StockQuote getLatestStockQuote(@NotNull String symbol) throws StockServiceException {

        StockQuote stockQuote = null;

        try {
            Stock stock = stockService.getLatestStockQuote(symbol);
            BigDecimal price = stock.getQuote(true).getPrice();
            Calendar cal = Calendar.getInstance();
            stockQuote = new StockQuote(symbol, price, cal.getTime());
        } catch (IOException e) {
            throw new StockServiceException("Could not get latest stock quote for symbol " + symbol + ".");
        }

        return stockQuote;
    }

    /**
     * Gets a stock quote (containing the current price) for the company indicated by the given symbol on the given
     * date.
     *
     * @param symbol                    a stock symbol of a company, e.g. "APPL" for Apple
     * @param date                      a date
     * @return                          instance of {@code DAOStockQuote} containing the current price) for the
     *                                  company with the given symbol on the given date
     * @throws StockServiceException    if an exception occurs when trying to get the quote
     */
    @Override
    public StockQuote getStockQuote(@NotNull String symbol, @NotNull Date date) throws StockServiceException {

        StockQuote stockQuote = null;

        try {
            Calendar from = Calendar.getInstance();
            from.setTime(date);
            from.add(Calendar.DATE, -1);
            Calendar until = Calendar.getInstance();
            until.setTime(date);
            List<HistoricalQuote> historicalQuotes = stockService.getStockQuoteList(symbol, from, until, Interval.DAILY);

            BigDecimal price = historicalQuotes.get(0).getClose().setScale(2, RoundingMode.HALF_UP);;

            stockQuote = new StockQuote(symbol, price, date);
        } catch (IOException e) {
            throw new StockServiceException("Could not get stock quote for symbol " + symbol + ".");
        }

        return stockQuote;
    }

    /**
     * Gets a list of stock quotes for the company indicated by the given symbol, one for each day in the given date
     * range.
     *
     * @param symbol                    a stock symbol of a company, e.g. "APPL" for Apple
     * @param from                      the date of the first stock quote
     * @param until                     the date of the last stock quote
     * @return                          a list of stock quotes for the company with the given symbol, one for each day
     *                                  in the date range given
     * @throws StockServiceException    if an exception occurs when trying to get the quote list
     */
    @Override
    public List<StockQuote> getStockQuoteList(@NotNull String symbol, @NotNull Calendar from, @NotNull Calendar until) throws StockServiceException {

        List<StockQuote> stockQuoteList = new ArrayList<>();

        if (!from.after(until)) { // stop if from is after than until
            try {
                List<HistoricalQuote> historicalQuotes = stockService.getStockQuoteList(symbol, from, until, Interval.DAILY);
                for (HistoricalQuote historicalQuote : historicalQuotes) {
                    stockQuoteList.add(
                            new StockQuote(
                                    symbol,
                                    historicalQuote.getClose().setScale(2, RoundingMode.HALF_UP),
                                    historicalQuote.getDate().getTime()));
                }
            }  catch (IOException e) {
                throw new StockServiceException("Could not get stock quotes for symbol " + symbol + ".");
            }
        }

        return stockQuoteList;
    }

    /**
     * Gets a list of stock quotes for the company indicated by the given symbol, one for each period in the given
     * interval in the given date range.
     *
     * @param symbol                    a stock symbol of a company, e.g. "APPL" for Apple
     * @param from                      the date of the first stock quote
     * @param until                     the date of the last stock quote
     * @param interval                  the interval between which stock quotes should be obtained, i.e. if DAILY, then
     *                                  one per day
     * @return                          a list of stock quotes for the company with the given symbol, one for each
     *                                  period in the given interval in the given date range
     * @throws StockServiceException    if an exception occurs when trying to get the quote list
     */
    @Override
    public List<StockQuote> getStockQuoteList(@NotNull String symbol, @NotNull Calendar from, @NotNull Calendar until, @NotNull StockQuoteInterval interval) throws StockServiceException {

        List<StockQuote> stockQuoteList = new ArrayList<>();

        Interval yahooInterval = getYahooInterval(interval);

        /* when monthly stock quotes are requested, Yahoo Finance bombs unless the from date is the first day of the month */
        if (yahooInterval == Interval.MONTHLY) {
            from.add(Calendar.MONTH, 1);
            from.set(Calendar.DAY_OF_MONTH, 1);
        }

        if (!from.after(until)) { // stop if from is after than until
            try {

                List<HistoricalQuote> historicalQuotes = stockService.getStockQuoteList(symbol, from, until, yahooInterval);
                for (HistoricalQuote historicalQuote : historicalQuotes) {
                    stockQuoteList.add(new
                            StockQuote(
                                    symbol,
                                    historicalQuote.getClose().setScale(2, RoundingMode.HALF_UP),
                                    historicalQuote.getDate().getTime()));
                }
            }  catch (IOException e) {
                throw new StockServiceException("Could not get stock quotes for symbol " + symbol + ".");
            }
        }

        return stockQuoteList;
    }

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
    public void addOrUpdateStockQuote(@NotNull String stockSymbol,  @NotNull BigDecimal stockPrice, @NotNull Date dateRecorded) throws StockServiceException {

        throw new UnsupportedOperationException();
    }

    /**
     * Gets the Yahoo Finance interval that corresponds to the {@code StockQuoteInterval} interval.
     *
     * @param interval                  a {@code StockQuoteInterval} interval
     * @return                          a Yahoo Finance interval
     * @throws StockServiceException
     */
    private Interval getYahooInterval(StockQuoteInterval interval) throws StockServiceException {
        Interval yahooInterval;

        if (interval == StockQuoteInterval.DAILY) {
            yahooInterval = Interval.DAILY;
        } else if (interval == StockQuoteInterval.WEEKLY) {
            yahooInterval = Interval.WEEKLY;
        } else if (interval == StockQuoteInterval.MONTHLY) {
            yahooInterval = Interval.MONTHLY;
        } else {
            throw new StockServiceException("Yahoo stock service does not support the given interval.");
        }

        return yahooInterval;
    }
}
