package edu.kimjones.advancedjava.stock;

/**
 * This class implements the factory pattern, and produces an instance of a class that implements the
 * StockSerivce interface
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
    public static final StockService createStockService(StockServiceType type) throws StockServiceException {
        if (type.equals(StockServiceType.STUPID)) {
            return new StupidStockService();
        } else if (type.equals(StockServiceType.RANDOM)) {
            return new RandomStockService();
        } else if (type.equals(StockServiceType.IEX_TRADING)) {
            return new IEXTradingStockService();
        } else {
            throw new StockServiceException();
        }
    }
}
