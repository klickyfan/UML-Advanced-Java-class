package edu.kimjones.advancedjava.stock.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class models a database table that links a person with the stocks they are interested in.
 *
 * @author Kim Jones (using code obtained from Spencer Marks)
 */
@Entity
@Table(name = "person_stock")
final public class DAOPersonStock implements DatabaseAccessObject {
    private int id;
    private DAOPerson person;
    private String stockSymbol;

    /**
     * Creates a {@code DAOPersonStock} instance that needs to be initialized.
     */
    public DAOPersonStock() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Creates a {@code DAOPersonStock} instance.
     *
     * @param person a person
     * @param stockSymbol the symbol of the stock to associate the person with
     */
    public DAOPersonStock(DAOPerson person, String stockSymbol) {
        setPerson(person);
        setStockSymbol(stockSymbol);
    }

    /**
     * @return a unique id representing a particular row in the person_stock table
     */
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Sets the unique id for a particular row in the person_stock table. Should not be called by client code as the
     * value is managed internally.
     *
     * @param id a unique value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return a {@code DAOPerson} instance for the person associated with the {@code PersonStock}
     */
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    public DAOPerson getPerson() {
        return person;
    }

    /**
     * Sets the {@code PersonStock}'s person.
     *
     * @param person an {@code DAOPerson} instance
     */
    public void setPerson(DAOPerson person) {
        this.person = person;
    }

    /**
     * @return a stock symbol associated with the person referenced by a particular row of the person_stock table
     */
    @Basic
    @Column(name = "stock_symbol", nullable = false, length = 256)
    public String getStockSymbol() {
        return stockSymbol;
    }

    /**
     * Sets the {@code PersonStock}'s stock symbol.
     *
     * @param stockSymbol a stock symbol
     */
    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    /**
     * @param obj   a {@code DAOPersonStock} instance
     * @return      true if two @code DAOPersonStock} instances are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof DAOPersonStock)) return false;

        DAOPersonStock rhs = (DAOPersonStock) obj;

        if (id != rhs.id) return false;
        if (person != null ? !person.equals(rhs.person) : rhs.person != null) return false;
        return stockSymbol != null ? stockSymbol.equals(rhs.stockSymbol) : rhs.stockSymbol == null;
    }

    /**
     * @return an integer which uniquely identifies a {@code DAOPersonStock} instance
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (person != null ? person.hashCode() : 0);
        result = 31 * result + (stockSymbol != null ? stockSymbol.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "DAOPersonStock{" +
                "id=" + id +
                ", person=" + person +
                ", symbol='" + stockSymbol + '\'' +
                '}';
    }
}
