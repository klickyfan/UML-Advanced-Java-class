package edu.kimjones.advancedjava.stock.model;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class is for testing the class {@code DAOPersonStock}.
 *
 * @author Kim Jones
 */
public class DAOPersonStockTest {

    private final int id = 10;
    private final String stockSymbol = "AAPL";

    private DAOPerson person;
    private DAOPersonStock personStock;

    @Before
    public void setUp() {
        this.person = new DAOPerson();
        this.person.setId(DAOPersonTest.id);
        this.person.setFirstName(DAOPersonTest.firstName);
        this.person.setLastName(DAOPersonTest.lastName);
        this.person.setBirthDate(DAOPersonTest.birthDate);

        this.personStock = new DAOPersonStock();
        this.personStock.setId(id);
        this.personStock.setPerson(person);
        this.personStock.setStockSymbol(stockSymbol);
    }

    @Test
    public void testPersonStocksGetterAndSettersPositive() {
        assertEquals("id matches", id, personStock.getId());
        assertEquals("person matches", person, personStock.getPerson());
        assertEquals("stock symbol matches", stockSymbol, personStock.getStockSymbol());
    }

    @Test
    public void testPersonStocksGetterAndSettersNegative() {
        assertNotEquals("id does not match", 20, personStock.getId());
        assertNotSame("person does not match", new DAOPerson(), personStock.getPerson());
        assertNotSame("stock symbol does not", "OOOO", personStock.getStockSymbol());
    }

    @Test
    public void testToString() {
        assertTrue("toString has lastName", personStock.toString().contains(DAOPersonTest.lastName));
        assertTrue("toString has firstName", personStock.toString().contains(DAOPersonTest.firstName));
        assertTrue("toString has stockSymbol", personStock.toString().contains(stockSymbol));
    }

    @Test
    public void testEqualsContract() {
        EqualsVerifier.forClass(DAOPersonStock.class).verify();
    }
}
