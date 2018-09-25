package edu.kimjones.advancedjava.stock;

/**
 * This class implements the factory pattern, and produces an instance of a class that implements the
 * StockService interface
 *
 * @author Kim Jones
 */
public class StockServiceFactory {

    /**
     * I am hiding the constructor because this class contains only static methods.
     */
    private StockServiceFactory() {
    }

    /**
     * @return an object implementing the {@code StockService} interface
     */
    public static final StockService createStockService() throws StockServiceException {

        return new BasicStockService(); // replace as needed with any stock service
    }
}
