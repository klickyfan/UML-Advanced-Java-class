package edu.kimjones.advancedjava.stock.services;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the ServiceFactory class.
 *
 * @author Kim Jones
 */
public class ServiceFactoryTest {

    private static StockService stockService;
    private static PersonService personService;

    @BeforeClass
    public static void setUp() throws Exception {
        stockService = ServiceFactory.getStockService();
        personService = ServiceFactory.getPersonService();
    }

    @Test
    public void testGetStockServicePositive() {
        assertTrue("stock service is a stock service", stockService instanceof StockService);
    }

    @Test
    public void testGetStockServiceNegative() {
        assertNotNull("stock service is null", stockService);
    }

    @Test
    public void testGetPersonServicePositive() {
        assertTrue("person service is a person service", personService instanceof PersonService);
    }

    @Test
    public void testGetPersonServiceNegative() {
        assertNotNull("person service is null", personService);
    }

}
