package edu.kimjones.advancedjava.stock;

import java.math.BigDecimal;
import java.util.Date;

/**
 * This class models a stock price quote.
 *
 * @author Kim Jones (using code obtained from Spencer Marks)
 */
public class StockQuote {

    private String stockSymbol;
    private BigDecimal stockPrice;
    private Date dateRecorded;

    /**
     * This constructor creates a new stock quote.
     *
     * @param stockSymbol           the symbol of a company
     * @param stockPrice            the price of that company's stock
     * @param dateRecorded          the date of the price
     */
    public StockQuote(String stockSymbol,BigDecimal stockPrice, Date dateRecorded) {
        this.stockSymbol = stockSymbol;
        this.stockPrice = stockPrice;
        this.dateRecorded = dateRecorded;
    }

    /**
     * @return the symbol of the company associated with this stock quote
     */
    public String getStockSymbol() {
        return stockSymbol;
    }

    /**
     * @return the price of this stock quote
     */
    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    /**
     * @return the date of this stock quote
     */
    public Date getDateRecorded() {
        return dateRecorded;
    }
}
