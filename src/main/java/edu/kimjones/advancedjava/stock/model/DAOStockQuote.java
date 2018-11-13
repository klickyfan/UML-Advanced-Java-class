package edu.kimjones.advancedjava.stock.model;

import javax.persistence.*;

import javax.validation.constraints.Digits;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Objects;

/**
 * This class models a stock price quote.
 *
 * @author Kim Jones (using code obtained from Spencer Marks)
 */
@Entity
@Table(name = "quotes")
final public class DAOStockQuote {

    private int id;
    private String stockSymbol;
    private BigDecimal stockPrice;
    private Date dateRecorded;

    /**
     * Creates a {@code DAOStockQuote} instance that needs to be initialized.
     */
    public DAOStockQuote() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Creates a {@code DAOStockQuote} instance.
     *
     * @param stockSymbol       the symbol of a company
     * @param stockPrice        the price of that company's stock
     * @param dateRecorded      the date (and time) of the price
     */
    public DAOStockQuote(String stockSymbol, BigDecimal stockPrice, Date dateRecorded) {
        this.stockSymbol = stockSymbol;
        this.stockPrice = stockPrice;
        this.dateRecorded = new Date(dateRecorded.getTime()); // make safe copy
    }

    /**
     * @return a unique id representing a particular row in the quote table
     */
    @Id
    @Column(name = "id",  nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Sets the unique id for a particular row in the quote table. It should not be called by client code as the value
     * is managed internally.
     *
     * @param id a unique value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the symbol of the company associated with the stock quote
     */
    @Basic
    @Column(name = "symbol", nullable = false)
    public String getStockSymbol() {
        return stockSymbol;
    }

    /**
     * Sets the stock quotes's symbol.
     *
     * @param stockSymbol a String value
     */
    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    /**
     * @return the price of this stock quote
     */
    @Basic
    @Digits(integer=5, fraction=2)
    @Column(name = "price", nullable = false)
    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    /**
     * Sets the stock quotes's price.
     *
     * @param stockPrice a BigDecimal value
     */
    public void setStockPrice(BigDecimal stockPrice) {
        this.stockPrice = stockPrice;
    }

    /**
     * @return the date of this stock quote
     */
    @Basic
    @Column(name = "time", nullable = false)
    public Date getDateRecorded() {
        return dateRecorded;
    }

    /**
     * Sets the stock quotes's date.
     *
     * @param dateRecorded a String value
     */
    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    /**
     * @param obj   a {@code DAOStockQuote} instance
     * @return      true if two {@code DAO StockQuotes} are equal
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == this) return true;

        if (!(obj instanceof DAOStockQuote)) return false;

        DAOStockQuote rhs = (DAOStockQuote) obj;

        if (stockPrice == null && rhs.stockPrice != null)
            return false;

        if (stockPrice != null && rhs.stockPrice == null)
            return false;

        // Note use of compareTo to compare BigDecimal stockPrices. We have to use this instead of equals because two
        // equals may be false for mathematically equivalent BigDecimals due to scaling.
        boolean test0 = (id == rhs.id);
        boolean test1 = Objects.equals(stockSymbol, rhs.stockSymbol);
        boolean test2 = (stockPrice == null && rhs.stockPrice == null) || (stockPrice.compareTo(rhs.stockPrice) == 0);
        boolean test4 = Objects.equals(dateRecorded, rhs.dateRecorded);

        return test0 && test1 && test2 && test4;
    }

    /**
      * @return an integer which uniquely identifies a {@code DAOStockQuote} instance
     */
    @Override
    public int hashCode() {

        // Note conversion of BigDecimal stockPrice to double. We have to do this because two mathematically equivalent
        // BigDecimals could product different hash codes due to scaling.
        if (stockPrice != null)
            return Objects.hash(id, stockSymbol, stockPrice.doubleValue(), dateRecorded);
        else
            return Objects.hash(id, stockSymbol, dateRecorded);
    }
}
