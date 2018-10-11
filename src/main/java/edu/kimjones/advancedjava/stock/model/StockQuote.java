package edu.kimjones.advancedjava.stock.model;

import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * This class models a stock price quote.
 *
 * @author Kim Jones (using code obtained from Spencer Marks)
 */
@Immutable
final public class StockQuote {

    final private String stockSymbol;
    final private BigDecimal stockPrice;
    final private Date dateRecorded;

    /**
     * This constructor creates a new stock quote.
     *
     * @param stockSymbol       the symbol of a company
     * @param stockPrice        the price of that company's stock
     * @param dateRecorded      the date of the price
     */
    public StockQuote(@NotNull String stockSymbol, @NotNull BigDecimal stockPrice, @NotNull Date dateRecorded) {
        this.stockSymbol = stockSymbol;
        this.stockPrice = stockPrice;
        this.dateRecorded = new Date(dateRecorded.getTime()); // make safe copy
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

    /**
     * This method and the next are needed to enable testing for StockQuote (or array of StockQuote) equality.
     *
     * @param obj            a StockQuote instance
     * @return               true if two StockQuotes are equal
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;

        if (obj == this)
            return true;

        if (!(obj instanceof StockQuote))
            return false;

        StockQuote rhs = (StockQuote) obj;

        if (stockPrice == null && rhs.stockPrice != null)
            return false;

        if (stockPrice != null && rhs.stockPrice == null)
            return false;

        // Note use of compareTo to compare BigDecimal stockPrices. We have to use this instead of equals because two
        // equals may be false for mathematically equivalent BigDecimals due to scaling.
        boolean test0  = Objects.equals(stockSymbol, rhs.stockSymbol);
        boolean test1 = (stockPrice == null && rhs.stockPrice == null) || (stockPrice.compareTo(rhs.stockPrice) == 0);
        boolean test2 = Objects.equals(dateRecorded, rhs.dateRecorded);

        return test0 && test1 && test2;
    }

    /**
     * This method and the former are needed to enable testing for StockQuote (or array of StockQuote) equality.
     *
     * @return               an integer which uniquely identifies a StockQuote instance
     */
    @Override
    public int hashCode() {

        // Note conversion of BigDecimal stockPrice to double. We have to do this because two mathematically equivalent
        // BigDecimals could product different hash codes due to scaling.
        if (stockPrice != null)
            return Objects.hash(stockSymbol, stockPrice.doubleValue(), dateRecorded);
        else
            return Objects.hash(stockSymbol, dateRecorded);
    }
}
