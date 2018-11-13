package edu.kimjones.advancedjava.stock.model.database;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class is for testing the class {@code DAOPersonStock}.
 *
 * @author Kim Jones
 */
public class DAOPersonStockTest {

    private static final int ID = 10;
    private static final String STOCK_SYMBOL = "AAPL";

    private static DAOPerson person;
    private static DAOPersonStock personStock;

    @BeforeClass
    public static void setUp() {
        person = new DAOPerson();
        person.setId(DAOPersonTest.ID);
        person.setUsername(DAOPersonTest.USERNAME);
        person.setFirstName(DAOPersonTest.FIRST_NAME);
        person.setLastName(DAOPersonTest.LAST_NAME);
        person.setBirthDate(DAOPersonTest.BIRTH_DATE);

        personStock = new DAOPersonStock();
        personStock.setId(ID);
        personStock.setPerson(person);
        personStock.setStockSymbol(STOCK_SYMBOL);
    }

    @Test
    public void testPersonStocksGetterAndSettersPositive() {
        assertEquals("ID matches", ID, personStock.getId());
        assertEquals("person matches", person, personStock.getPerson());
        assertEquals("stock symbol matches", STOCK_SYMBOL, personStock.getStockSymbol());
    }

    @Test
    public void testPersonStocksGetterAndSettersNegative() {
        assertNotEquals("ID does not match", 20, personStock.getId());
        assertNotSame("person does not match", new DAOPerson(), personStock.getPerson());
        assertNotSame("stock symbol does not", "OOOO", personStock.getStockSymbol());
    }

    @Test
    public void testToString() {
        assertTrue("toString has LAST_NAME", personStock.toString().contains(DAOPersonTest.LAST_NAME));
        assertTrue("toString has FIRST_NAME", personStock.toString().contains(DAOPersonTest.FIRST_NAME));
        assertTrue("toString has STOCK_SYMBOL", personStock.toString().contains(STOCK_SYMBOL));
    }

    @Test
    public void testEqualsContract() {
        EqualsVerifier.forClass(DAOPersonStock.class).verify();
    }
}
