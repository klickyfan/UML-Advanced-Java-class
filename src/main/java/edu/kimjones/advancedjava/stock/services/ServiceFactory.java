package edu.kimjones.advancedjava.stock.services;

/**
 * This class implements the factory pattern, and produces an instance of a class that implements the
 * StockService interface
 *
 * @author Kim Jones
 */
public class ServiceFactory {

    /**
     * I am hiding the constructor because this class contains only static methods.
     */
    private ServiceFactory() {
    }

    /**
     * @return an object implementing the {@code StockService} interface
     * @throws StockServiceException if unable to do this
     */
    public static final StockService getStockService() throws StockServiceException {

        try {
            return new DatabaseStockService(); // replace as needed with any stock service
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            throw new StockServiceException("Unable to produce an instance of a class that implements the StockService interface.");
        }
    }

    /**
     * @return an object implementing the {@code PersonService} interface
     * @throws PersonServiceException if unable to do this
     */
    public static final PersonService getPersonService() throws PersonServiceException {

        try {
            return new DatabasePersonService(); // replace as needed with any person service
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            throw new PersonServiceException("Unable to produce an instance of a class that implements the PersonService interface.");
        }
    }
}
