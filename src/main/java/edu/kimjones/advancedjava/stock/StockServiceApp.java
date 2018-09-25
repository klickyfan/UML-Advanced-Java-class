package edu.kimjones.advancedjava.stock;

import java.time.LocalDate;
import java.util.Date;

/**
 * Main entry point for the stock service application.
 *
 * @author Kim Jones
 */
public class StockServiceApp {

    /**
     * @param args      no args are required
     */
    public static void main(String[] args) {

        String symbol = "AAPL";

        /*
         https://finance.yahoo.com/quote/AAPL/history/ I can see that the closing price of Apple stock
         on this day was 182.72
        */
        LocalDate localDate = LocalDate.of(2018, 9, 16);
        Date date = java.sql.Date.valueOf(localDate);

        StockService stupidStockService = StockServiceFactory.createStockService(StockServiceType.STUPID);
        StockQuote stupidQuote = stupidStockService.getStockQuote(symbol, date);

        System.out.printf("Stupid quote for stock %s on date %tD: %.2f %n", symbol, date, stupidQuote.getStockPrice());

        StockService randomStockService = StockServiceFactory.createStockService(StockServiceType.RANDOM);
        StockQuote randomQuote = randomStockService.getStockQuote(symbol, date);

        System.out.printf("Radom quote for stock %s on date %tD: %.2f %n", symbol, date,randomQuote.getStockPrice());

        StockService iexTradingStockService = StockServiceFactory.createStockService(StockServiceType.IEX_TRADING);
        StockQuote iexTradingQuote = iexTradingStockService.getStockQuote(symbol, date);

        if (iexTradingQuote == null) {
            System.out.printf("An IEX quote for stock %s on date %tD is not available.%n", symbol, date);
        }
        else {
            System.out.printf("IEX quote for stock %s on date %tD: %.2f %n", symbol, date, iexTradingQuote.getStockPrice());
        }
    }
}