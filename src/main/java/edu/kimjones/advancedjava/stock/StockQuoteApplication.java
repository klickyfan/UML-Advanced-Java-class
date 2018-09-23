package edu.kimjones.advancedjava.stock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Main entry point for the stock quote application.
 *
 * @author Kim Jones
 */
public class StockQuoteApplication {

    /**
     * @param args      3 string args are required: one containing a stock symbol followed by two containing dates
     *                  of the form "mm/dd/yyyy"
     */
    public static void main(String[] args) {

        if (args.length != 3) {
            throw new IllegalArgumentException("Exactly 3 arguments are required!");
        }

        System.out.printf("Parameter 1: %s %n", args[0]);
        System.out.printf("Parameter 2: %s %n", args[1]);
        System.out.printf("Parameter 3: %s %n", args[2]);

        String symbol = args[0];

        Calendar fromDate;
        Calendar untilDate;

        try {
            fromDate = parseDateString(args[1]);
        } catch(ParseException exception) {
            throw new IllegalArgumentException("Parameter 2 must be a string of the form mm/dd/yyyy");
        }

        try {
            untilDate = parseDateString(args[2]);
        } catch(ParseException exception) {
            throw new IllegalArgumentException("Parameter 2 must be a string of the form mm/dd/yyyy");
        }

        StockService stockService = StockServiceFactory.createStockService();

        StockQuote quote = stockService.getStockQuote(symbol);

        System.out.printf("The current price of stock %s is %.2f %n", symbol, quote.getStockPrice());

        List<StockQuote> quoteList = stockService.getStockQuoteList(symbol, fromDate, untilDate);

        for (StockQuote temp : quoteList) {
            System.out.printf("The price of stock %s on date %tD is %.2f %n", temp.getStockSymbol(), temp.getDateRecorded(), temp.getStockPrice());
        }
    }

    /**
     * @param dateString        a string containing a date of the form "mm/dd/yyyy"
     * @return                  an instance of the Calendar class for that date
     * @throws ParseException   if the string given is not of the form "mm/dd/yyyy"
     */
    public static Calendar parseDateString(String dateString) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date = formatter.parse(dateString);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal;
    }
}