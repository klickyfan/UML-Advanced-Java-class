package edu.kimjones.advancedjava.stock.services;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the ServiceFactory class.
 *
 * @author Kim Jones
 */
public class ServiceFactoryTest {

    private StockService stockService;
    private PersonService personService;

    @Before
    public void setUp() throws Exception {
        stockService = ServiceFactory.getStockService();
        personService = ServiceFactory.getPersonService();
    }

    @Test
    public void testGetStockServicePositive() {
        assertTrue("stock service is a stock service", stockService instanceof StockService);
    }

    @Test
    public void testGetStockServiceNegative() {
        assertFalse("stock service is null", stockService == null);
    }

    @Test
    public void testGetPersonServicePositive() {
        assertTrue("person service is a person service", personService instanceof PersonService);
    }

    @Test
    public void testGetPersonServiceNegative() {
        assertFalse("person service is null", personService == null);
    }

}
