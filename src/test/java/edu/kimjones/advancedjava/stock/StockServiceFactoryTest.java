package edu.kimjones.advancedjava.stock;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * This class tests the StockServiceFactory class.
 *
 * Note to Prof. Marks: according to the second response here
 *
 *      https://stackoverflow.com/questions/37310/checking-the-results-of-a-factory-in-a-unit-test
 *
 * what I am doing in this test class in >integration< testing, not unit testing, but I wasn't sure
 * what else to turn in!
 *
 * @author Kim Jones
 */
public class StockServiceFactoryTest {

    private StockService stupidStockService;
    private StockService randomStockService;
    private StockService iexTradingStockService;

    @Before
    public void setUp() throws Exception {
        stupidStockService = StockServiceFactory.createStockService(StockServiceType.STUPID);
        randomStockService = StockServiceFactory.createStockService(StockServiceType.RANDOM);
        iexTradingStockService = StockServiceFactory.createStockService(StockServiceType.IEX_TRADING);
    }

    @Test
    public void testGetStockServicePositive() {
        assertEquals("stock service is stupid", stupidStockService.getClass(), StupidStockService.class);
        assertEquals("stock service is random", randomStockService.getClass(), RandomStockService.class);
        assertEquals("stock service is IEX Trading", iexTradingStockService.getClass(), IEXTradingStockService.class);
    }

    @Test
    public void testGetStockServiceNegative() {

        assertFalse("stupid stock service is random", stupidStockService.getClass() == RandomStockService.class);
        assertFalse("stupid stock service is IEX Trading", stupidStockService.getClass() == IEXTradingStockService.class);
        assertFalse("stupid stock service is null", stupidStockService == null);

        assertFalse("random stock service is IEX Trading", randomStockService.getClass() ==IEXTradingStockService.class);
        assertFalse("random stock service is stupid", randomStockService.getClass() == StupidStockService.class);
        assertFalse("random stock service is null", randomStockService == null);

        assertFalse("IEX Trading stock service is stupid", iexTradingStockService.getClass() == StupidStockService.class);
        assertFalse("IEX Trading stock service is random", iexTradingStockService.getClass() == RandomStockService.class);
        assertFalse("IEX Trading stock service is null", iexTradingStockService == null);

    }
}
