package edu.kimjones.advancedjava.stock;

import pl.zankowski.iextrading4j.api.stocks.Chart;
import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.ChartRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class models a stock service that uses the stock API IEKTrading4j which is described
 * here: https://github.com/WojciechZankowski/iextrading4j
 *
 * @author Kim Jones
 */
public class IEXTradingStockService implements StockService {

    private static final int MAX_ITERATIONS = 10;

    /**
     * This method gets a stock quote (containing the current price) for the company indicated by the given symbol.
     *
     * @param symbol    a stock symbol of a company, e.g. "APPL" for Apple
     * @return          a stock quote (containing the current price) for the company with the given symbol
     */
    public StockQuote getStockQuote(String symbol) {

        final IEXTradingClient tradingClient = IEXTradingClient.create();

        final Quote quoteFromIEXTrading = tradingClient.executeRequest(new QuoteRequestBuilder()
                .withSymbol(symbol)
                .build());
        BigDecimal price = quoteFromIEXTrading.getLatestPrice();

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        StockQuote quote = new StockQuote(symbol, price, cal.getTime());

        return quote;
    }

    /**
     * This method gets a stock quote (containing the current price) for the company indicated by the given symbol on
     * the given date (or null if it can't obtain a quote for that date).
     *
     * @param symbol    a stock symbol of a company, e.g. "APPL" for Apple
     * @param date      a date
     * @return          a stock quote (containing the current price) for the company with the given symbol or null if
     *                  no quote is available
     */
    public StockQuote getStockQuote(String symbol, Date date) {

        final IEXTradingClient tradingClient = IEXTradingClient.create();

        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        LocalDate localDate = sqlDate.toLocalDate();

        // System.out.println(localDate);
        // System.out.println(symbol);

        int iterations = 0;

        while (iterations < MAX_ITERATIONS) {

            final List<Chart> chartList = tradingClient.executeRequest(new ChartRequestBuilder()
                    .withSymbol("AAPL")
                    //.withDate(LocalDate.of(2018, 8, 13))
                    .withDate(localDate)
                    .build());

            // System.out.println(chartList);

            if (chartList.size() > 0) {

                Chart chart = chartList.get(0);

                StockQuote quote = new StockQuote(symbol, chart.getMarketClose(), date);

                return quote;
            }

            // if here, there is no data is available for localDate, so prepare to look one day earlier in case
            // the current localDate is simply a stock market holiday
            localDate = localDate.minusDays(1);
            iterations++;
        }

        // if here, there is no stock price available for date or the MAX_ITERATIONS days before
        return null;
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

        List<StockQuote> stockQuoteList = new ArrayList<StockQuote>();

        /** TO DO! **/

        return stockQuoteList;
    }
}
