package edu.kimjones.advancedjava.stock.model;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class is for testing the class PersonStock.
 *
 * @author Kim Jones
 */
public class PersonStockTest {

    private int id = 10;
    private String stockSymbol = "AAPL";

    private Person person;
    private PersonStock personStock;

    @Before
    public void setUp() throws Exception {
        this.person = new Person();
        this.person.setId(PersonTest.id);
        this.person.setFirstName(PersonTest.firstName);
        this.person.setLastName(PersonTest.lastName);
        this.person.setBirthDate(PersonTest.birthDate);

        this.personStock = new PersonStock();
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
        assertFalse("id does not match", 20 == personStock.getId());
        assertFalse("person does not match", new Person() == personStock.getPerson());
        assertFalse("stock symbol does not", "OOOO" == personStock.getStockSymbol());
    }

    @Test
    public void testToString() {
        assertTrue("toString has lastName", personStock.toString().contains(PersonTest.lastName));
        assertTrue("toString has firstName", personStock.toString().contains(PersonTest.firstName));
        assertTrue("toString has stockSymbol", personStock.toString().contains(stockSymbol));
    }

    @Test
    public void testEqualsContract() {
        EqualsVerifier.forClass(PersonStock.class).verify();
    }
}
