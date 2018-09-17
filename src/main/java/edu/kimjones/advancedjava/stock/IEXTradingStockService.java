package edu.kimjones.advancedjava.stock;

import pl.zankowski.iextrading4j.api.stocks.Chart;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.ChartRequestBuilder;

import java.time.LocalDate;
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
     * @param symbol        a symbol of a company
     * @param date          a date
     * @return              a stock quote for the company with the given symbol on the given date or null if no quote
     *                      is available
     */
    public StockQuote getStockQuote(String symbol, Date date) {

        final IEXTradingClient tradingClient = IEXTradingClient.create();

        /*

        PLEASE IGNORE THESE COMMENTS: just playing around with this API!

        // get historical data

        final List<HistoricalStats> historicalStatsList = tradingClient.executeRequest(new HistoricalStatsRequestBuilder()
                .withDate(YearMonth.of(2017, 5))
                .build());
        System.out.println(historicalStatsList);

        // get current stock price

        final Quote quoteFromIEXTrading = tradingClient.executeRequest(new QuoteRequestBuilder()
                .withSymbol(symbol)
                .build());
        BigDecimal price = quoteFromIEXTrading.getLatestPrice();

        StockQuote quote = new StockQuote(symbol, price, date);

        */

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
}
