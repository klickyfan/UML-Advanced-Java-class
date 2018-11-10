package edu.kimjones.advancedjava.stock.services;

import edu.kimjones.advancedjava.stock.model.StockQuote;
import edu.kimjones.advancedjava.stock.model.database.DAOStockQuote;
import edu.kimjones.advancedjava.stock.utilities.DatabaseAddOrUpdateException;
import edu.kimjones.advancedjava.stock.utilities.DatabaseConnectionException;
import edu.kimjones.advancedjava.stock.utilities.DatabaseUtility;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class models a stock service that gets stock data from a database.
 *
 * @author Kim Jones (starting with code obtained from Spencer Marks)
 */
public class DatabaseStockService implements StockService {

    private static final Logger LOGGER = LogManager.getLogger(DatabaseStockService.class.getName());
    private static final String TIME_ZONE = "America/New_York";

    /**
     * Gets a stock quote (containing the current price) for the company indicated by the given symbol.
     *
     * @param symbol                    a stock symbol of a company, e.g. "APPL" for Apple
     * @return                          an instance of {@code StockQuote} (containing the current price) for the
     *                                  company with the given symbol
     * @throws StockServiceException    if an exception occurs when trying to get the quote
     */
    @Override
    public StockQuote getLatestStockQuote(@NotNull String symbol) throws StockServiceException {

        return constructStockQuote(symbol, new java.util.Date());
    }

    /**
     * Gets a stock quote (containing the current price) for the company indicated by the given symbol on the given
     * date.
     *
     * @param symbol                    a stock symbol of a company, e.g. "APPL" for Apple
     * @param date                      a date
     * @return                          instance of {@code StockQuote} containing the current price) for the
     *                                  company with the given symbol on the given date
     * @throws StockServiceException    if an exception occurs when trying to get the quote
     */
    @Override
    public StockQuote getStockQuote(@NotNull String symbol, @NotNull java.util.Date date) throws StockServiceException {

        return constructStockQuote(symbol, date);
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
            constructStockQuoteList(stockQuoteList, symbol, from, until, StockQuoteInterval.DAILY);
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

        if (!from.after(until)) { // stop if from is after than until
            constructStockQuoteList(stockQuoteList, symbol, from, until, interval);
        }

        return stockQuoteList;
    }

    /**
     * Does the work needed by {@code}getLatestStockQuote and getStockQuote} to construct a {@code StockQuote}
     * instance for a company with the given symbol as recent as the given date.
     *
     * @param symbol                    a stock symbol of a company, e.g. "APPL" for Apple
     * @param date                      the date
     * @return                          instance of {@code StockQuote} containing the current price) for the
     *                                  company with the given symbol on the given date
     * @throws StockServiceException    if an exception occurs when trying to get the quote
     */
    private StockQuote constructStockQuote(@NotNull String symbol, @NotNull java.util.Date date) throws StockServiceException {

        StockQuote stockQuote = null;

        try {
            // establish database connection
            Connection connection = DatabaseUtility.getConnection();

            // select the latest stock quote for the given symbol
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM quotes WHERE symbol = ? AND time <= ?  ORDER BY time DESC LIMIT 1;");

            statement.setString(1, symbol);
            statement.setObject(2,
                    date.toInstant()  // convert from legacy class `java.util.Date` (a moment in UTC) to a modern `java.time.Instant` (a moment in UTC).
                            .atZone(ZoneId.of(TIME_ZONE))  // adjust from UTC to EST
                            .toLocalDate());

            LOGGER.debug(statement.toString());

            ResultSet resultSet = statement.executeQuery();

            LOGGER.debug(statement.toString());

            if (resultSet.next()) { // if we found a quote...

                String symbolValue = resultSet.getString("symbol");
                BigDecimal price = resultSet.getBigDecimal("price").setScale(2, RoundingMode.HALF_UP);
                Timestamp timestamp = resultSet.getTimestamp("time");

                stockQuote = new StockQuote(symbolValue, price, DateUtils.round(new java.util.Date(timestamp.getTime()), Calendar.HOUR));
            }

        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }

        if (stockQuote == null) {
            String message = String.format("The database has no stock data for symbol %s on date %tD.", symbol, date);
            throw new StockServiceException(message);
        }

        return stockQuote;
    }

    /**
     * Does the work needed by {@code List<StockQuote>getStockQuoteList(...} to construct a list of
     * {@code StockQuote} instances for a company with the given symbol, in the given date range and on the given
     * interval.
     *
     * @param stockQuoteList            the list we are building out
     * @param symbol                    a stock symbol of a company, e.g. "APPL" for Apple
     * @param from                      the date of the first stock quote
     * @param until                     the date of the last stock quote
     * @param interval                  the interval between which stock quotes should be obtained, i.e. if DAILY, then
     *                                  one per day
     * @throws StockServiceException    if an exception occurs when trying to get the quote ist
     */
    private void constructStockQuoteList(
            @NotNull List<StockQuote> stockQuoteList,
            @NotNull String symbol,
            @NotNull Calendar from,
            @NotNull Calendar until,
            @NotNull StockQuoteInterval interval) throws StockServiceException {

        try {
            // establish database connection
            Connection connection = DatabaseUtility.getConnection();

            // select the desired stock quotes for the given symbol
            PreparedStatement statement =
                    connection.prepareStatement(
                            "SELECT * FROM quotes WHERE time IN (SELECT max(time) FROM quotes GROUP BY " + interval.sqlGroupBy() + ") AND symbol = ? AND time >= ? AND time <= ? ORDER BY time");
            statement.setString(1, symbol);
            statement.setTimestamp(2, new Timestamp(from.getTimeInMillis()));
            statement.setTimestamp(3, new Timestamp(until.getTimeInMillis()));

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) { // process the query results

                String symbolValue = resultSet.getString("symbol");
                BigDecimal price = resultSet.getBigDecimal("price").setScale(2, RoundingMode.HALF_UP);
                Timestamp timestamp = resultSet.getTimestamp("time");

                stockQuoteList.add(new StockQuote(symbolValue, price, DateUtils.round(new java.util.Date(timestamp.getTime()), Calendar.HOUR)));
            }

        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
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
    public void addOrUpdateStockQuote(@NotNull String stockSymbol, @NotNull BigDecimal stockPrice, @NotNull Date dateRecorded) throws StockServiceException {
        try {
            DAOStockQuote stockQuote = new DAOStockQuote(stockSymbol, stockPrice, dateRecorded);
            DatabaseUtility.addOrUpdate(stockQuote);
        } catch (DatabaseAddOrUpdateException exception) {
            String message = String.format("Could not add or update stock with symbol %s.", stockSymbol);
            throw new StockServiceException(message, exception);
        }
    }
}
