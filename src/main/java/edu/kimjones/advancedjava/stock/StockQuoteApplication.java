package edu.kimjones.advancedjava.stock;

import edu.kimjones.advancedjava.stock.model.StockQuote;
import edu.kimjones.advancedjava.stock.services.ServiceFactory;
import edu.kimjones.advancedjava.stock.services.StockService;
import edu.kimjones.advancedjava.stock.services.StockServiceException;
import edu.kimjones.advancedjava.stock.utilities.DatabaseInitializationException;
import edu.kimjones.advancedjava.stock.utilities.DatabaseUtility;
import edu.kimjones.advancedjava.stock.utilities.DateOptionHandler;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Main entry point for the stock quote application which uses args4j to parse its command line arguments.
 *
 * @author Kim Jones
 */
public class StockQuoteApplication {

    private static final int MIN_ARGUMENTS = 6;

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

    @Option(name = "-interval",
            usage = "the interval on which you want prices (i.e. DAILY)")
    private StockService.StockQuoteInterval interval = StockService.StockQuoteInterval.DAILY;

    private Calendar calFromDate;
    private Calendar calUntilDate;

    /**
     * @param args 3 options are required: one containing a stock symbol, and two containing dates of the form
     *             "mm/dd/yyyy"
     */
    public static void main(String[] args) throws DatabaseInitializationException {

        new StockQuoteApplication().doMain(args);

        // clean up
        DatabaseUtility.initializeDatabase(DatabaseUtility.initializationFile);
    }

    private void doMain(String args[]) {

        CmdLineParser parser = new CmdLineParser(this);

        if (args == null || args.length < MIN_ARGUMENTS) {
            System.err.println("Insufficient options were supplied. %nUsage: ");
            parser.printUsage(System.err);
            return;
        }

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage() + "%nUsage: ");
            parser.printUsage(System.err);
            return;
        }

        calFromDate = Calendar.getInstance();
        calFromDate.setTime(fromDate);

        calUntilDate = Calendar.getInstance();
        calUntilDate.setTime(untilDate);

        System.out.printf("Symbol: %s %n", symbol);
        System.out.printf("From date: %tD %n", fromDate);
        System.out.printf("Until date: %tD %n", untilDate);
        System.out.printf("Interval: %s %n", interval.toString());

        System.out.printf("%n***** %n%n");

        try {

            initializeDatabase();

            StockService stockService = ServiceFactory.getStockService();

            /*
              get the latest price quote for a stock
             */
            getLatestStockQuote(stockService);

            System.out.printf("%n***** %n%n");

            /*
              get the a price quote for a stock on a particular date
             */
            getDatedStockQuote(stockService);

            System.out.printf("%n***** %n%n");

            /*
              get a list of price quotes for each day from calFromDate to calUntilDate
             */
            getStockQuoteList(stockService);

            System.out.printf("%n***** %n%n");

            /*
              get a list of price quotes for each day from calFromDate to calUntilDate on interval
             */
            getStockQuoteListOnInterval(stockService);

        } catch (DatabaseInitializationException | StockServiceException exception) {
            System.out.print(exception.getMessage());
        }
    }

    private void initializeDatabase() throws DatabaseInitializationException {
        DatabaseUtility.initializeDatabase(DatabaseUtility.initializationFile);
        DatabaseUtility.initializeDatabase("./src/main/sql/add_AAPL_interday_stock_data.sql");
        DatabaseUtility.initializeDatabase("./src/main/sql/add_AAPL_intraday_stock_data.sql");
    }

    private void getLatestStockQuote(StockService stockService) throws StockServiceException {
        StockQuote latestQuote = stockService.getLatestStockQuote(symbol);
        System.out.printf("%nThe latest price of stock %s is %.2f %n", symbol, latestQuote.getStockPrice());
    }

    private void getDatedStockQuote(StockService stockService) throws StockServiceException {
        StockQuote quoteOnDate = stockService.getStockQuote(symbol, fromDate);
        System.out.printf("%nThe price of stock %s on date %tD is %.2f %n", symbol, quoteOnDate.getDateRecorded(), quoteOnDate.getStockPrice());
    }

    private void getStockQuoteList(StockService stockService) throws StockServiceException {
        List<StockQuote> dailyQuoteList = stockService.getStockQuoteList(symbol, calFromDate, calUntilDate);
        System.out.printf("%n");
        for (StockQuote temp : dailyQuoteList) {
            System.out.printf("The price of stock %s on date %tD is %.2f %n", temp.getStockSymbol(), temp.getDateRecorded(), temp.getStockPrice());
        }
    }

    private void getStockQuoteListOnInterval(StockService stockService) throws StockServiceException {
        StockService.StockQuoteInterval interval = StockService.StockQuoteInterval.HOURLY;
        List<StockQuote> hourlyQuoteList = stockService.getStockQuoteList(symbol, calFromDate, calUntilDate, interval);
        System.out.printf("%n");
        for (StockQuote temp : hourlyQuoteList) {
            System.out.printf("The price of stock %s at time %tD %tR is %.2f %n", temp.getStockSymbol(), temp.getDateRecorded(), temp.getDateRecorded(), temp.getStockPrice());
        }
    }
}

