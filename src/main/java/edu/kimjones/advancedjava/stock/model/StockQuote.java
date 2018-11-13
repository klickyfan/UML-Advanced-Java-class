package edu.kimjones.advancedjava.stock.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * This class models a stock price quote.
 *
 * @author Kim Jones (using code obtained from Spencer Marks)
 */
final public class StockQuote  {

    private final String stockSymbol;
    private final BigDecimal stockPrice;
    private final Date dateRecorded;

    /**
     * Creates a {@code StockQuote} instance.
     *
     * @param stockSymbol       the symbol of a company
     * @param stockPrice        the price of that company's stock
     * @param dateRecorded      the date (and time) of the price
     */
    public StockQuote(String stockSymbol, BigDecimal stockPrice, Date dateRecorded) {
        this.stockSymbol = stockSymbol;
        this.stockPrice = stockPrice;
        this.dateRecorded = new Date(dateRecorded.getTime()); // make safe copy
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    /**
     * @param obj   a {@code StockQuote} instance
     * @return      true if two {@code StockQuotes} are equal
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof StockQuote)) {
            return false;
        }

        StockQuote rhs = (StockQuote) obj;

        if (stockPrice == null && rhs.stockPrice != null) {
            return false;
        }

        if (stockPrice != null && rhs.stockPrice == null) {
            return false;
        }

        // Note use of compareTo to compare BigDecimal stockPrices. We have to use this instead of equals because two
        // equals may be false for mathematically equivalent BigDecimals due to scaling.
        boolean test0 = Objects.equals(stockSymbol, rhs.stockSymbol);
        boolean test1 = (stockPrice == null && rhs.stockPrice == null) || (stockPrice.compareTo(rhs.stockPrice) == 0);
        boolean test2 = Objects.equals(dateRecorded, rhs.dateRecorded);

        return test0 && test1 && test2;
    }

    /**
     * @return an integer which uniquely identifies a {@code StockQuote} instance
     */
    @Override
    public int hashCode() {

        // Note conversion of BigDecimal stockPrice to double. We have to do this because two mathematically equivalent
        // BigDecimals could product different hash codes due to scaling.
        if (stockPrice != null) {
            return Objects.hash(stockSymbol, stockPrice.doubleValue(), dateRecorded);
        } else {
            return Objects.hash(stockSymbol, dateRecorded);
        }
    }
}
