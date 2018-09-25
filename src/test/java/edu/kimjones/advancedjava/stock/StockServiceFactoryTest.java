package edu.kimjones.advancedjava.stock;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the StockServiceFactory class.
 *
 * @author Kim Jones
 */
public class StockServiceFactoryTest {

    private StockService stockService;

    @Before
    public void setUp() throws Exception {
        stockService = StockServiceFactory.createStockService();
    }

    @Test
    public void testGetStockServicePositive() {
        assertTrue("stock service is a stock service", stockService instanceof StockService);
    }

    @Test
    public void testGetStockServiceNegative() {
        assertFalse("stock service is null", stockService == null);
    }
}
