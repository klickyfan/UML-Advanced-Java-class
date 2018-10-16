package edu.kimjones.advancedjava.stock.model;

import javax.persistence.*;

/**
 * This class models a database table that links a person with the stocks they are interested in.
 *
 * @author Kim Jones (using code obtained from Spencer Marks)
 */
@Entity
@Table(name = "person_stock", catalog = "")
final public class PersonStock {
    private int id;
    private Person person;
    private String stockSymbol;

    /**
     * This constructor creates a {@code PersonStock} instance that needs to be initialized.
     */
    public PersonStock() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * This constructor creates a {@code PersonStock} instance.
     *
     * @param person a person
     * @param stockSymbol the symbol of the stock to associate the person with
     */
    public PersonStock(Person person, String stockSymbol) {
        setPerson(person);
        setStockSymbol(stockSymbol);
    }

    /**
     * @return a unique id representing a particular row in the person_stock table
     */
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    /**
     * This method sets the unique id for a particular row in the person_stock table. It should not be called by client
     * code as the value is managed internally.
     *
     * @param id a unique value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return a {@code Person} instance for the person referenced by a particular row of the person_stock table
     */
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    public Person getPerson() {
        return person;
    }

    /**
     * This method sets the Person for a particular row in the person_stock table.
     *
     * @param person an {@code Person} instance
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     *
     * @return a stock symbol associated with the person referenced by a particular row of the person_stock table
     */
    @Basic
    @Column(name = "stock_symbol", nullable = false, insertable = true, updatable = true, length = 256)
    public String getStockSymbol() { return stockSymbol; }

    /**
     * This method sets the stock symbol for a particular row in the person_stock table.
     *
     * @param stockSymbol a stock symbol
     */
    public void setStockSymbol(String stockSymbol) { this.stockSymbol = stockSymbol; }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj == null || !(obj instanceof PersonStock)) return false;

        PersonStock rhs = (PersonStock) obj;

        if (id != rhs.id) return false;
        if (person != null ? !person.equals(rhs.person) : rhs.person != null) return false;
        if (stockSymbol != null ? !stockSymbol.equals(rhs.stockSymbol) : rhs.stockSymbol != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (person != null ? person.hashCode() : 0);
        result = 31 * result + (stockSymbol != null ? stockSymbol.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "PersonStock{" +
                "id=" + id +
                ", person=" + person +
                ", symbol='" + stockSymbol + '\'' +
                '}';
    }
}
