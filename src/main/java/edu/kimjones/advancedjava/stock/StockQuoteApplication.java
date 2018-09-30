package edu.kimjones.advancedjava.stock;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Main entry point for the stock quote application which uses args4j to parse its command line arguments.
 *
 * @author Kim Jones
 */
public class StockQuoteApplication {

    @Option(name = "-symbol",
            required = true,
            handler = StringArrayOptionHandler.class,
            usage = "the symbol of the stock whose price you want")
    private String symbol;

    @Option(name = "-from",
            required = true,
            handler = DateOptionHandler.class,
            usage = "the first date for which you want a price")
    private Date fromDate;

    @Option(name = "-until",
            required = true,
            handler = DateOptionHandler.class,
            usage = "the last date for which you want a price")
    private Date untilDate;

    @Option(name="-interval",
            required = false,
            usage = "the interval on which you want prices (i.e. DAILY)")
    public StockService.StockQuoteInterval interval = StockService.StockQuoteInterval.DAILY;

    /**
     * @param args      3 options are required: one containing a stock symbol, and two containing dates of the form
     *                  "mm/dd/yyyy"
     */
    public static void main(String[] args) throws IOException {
        new StockQuoteApplication().doMain(args);
    }

    private void doMain(String args[]) throws IOException {

        //registerHandler(Date.class, DateOptionHandler.class);

        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        }
        catch (CmdLineException e) {

            System.err.println(e.getMessage());
            parser.printUsage(System.err);

            return;
        }

        Calendar calFromDate = Calendar.getInstance();
        calFromDate.setTime(fromDate);

        Calendar calUntilDate = Calendar.getInstance();
        calUntilDate.setTime(untilDate);

        System.out.printf("Symbol: %s %n", symbol);
        System.out.printf("From date: %tD %n", fromDate);
        System.out.printf("Until date: %tD %n", untilDate);
        System.out.printf("Interval: %s %n", interval.toString());

        System.out.printf("%n***** %n");

        StockService stockService = StockServiceFactory.createStockService();

        /**
         * get a single price quote
         */
        StockQuote quote = stockService.getStockQuote(symbol);

        System.out.printf("%nThe current price of stock %s is %.2f %n", symbol, quote.getStockPrice());

        System.out.printf("%n***** %n%n");

        /**
         * get a list of price quotes for each day from calFromDate to calUntilDate
         */
        List<StockQuote> dailyQuoteList = stockService.getStockQuote(symbol, calFromDate, calUntilDate);

        for (StockQuote temp : dailyQuoteList) {
            System.out.printf("The price of stock %s on date %tD is %.2f %n", temp.getStockSymbol(), temp.getDateRecorded(), temp.getStockPrice());
        }

        System.out.printf("%n***** %n%n");

        StockService.StockQuoteInterval interval = StockService.StockQuoteInterval.EVERY_SIX_HOURS;
        /**
         * get a list of price quotes for each day from calFromDate to calUntilDate on interval
         */
        List<StockQuote> hourlyQuoteList = stockService.getStockQuote(symbol, calFromDate, calUntilDate, interval);

        for (StockQuote temp : hourlyQuoteList) {
            System.out.printf("The price of stock %s at time %tD %tR is %.2f %n", temp.getStockSymbol(), temp.getDateRecorded(), temp.getDateRecorded(), temp.getStockPrice());
        }
    }
}
