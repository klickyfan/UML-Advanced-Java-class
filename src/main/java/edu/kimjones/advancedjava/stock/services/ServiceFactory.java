package edu.kimjones.advancedjava.stock.services;

import javax.annotation.concurrent.Immutable;

/**
 * This class implements the factory pattern, and produces an instance of a class that implements the
 * StockService interface
 *
 * @author Kim Jones
 */
public class ServiceFactory {

    @Immutable
    public enum ServiceType {
        DATABASE("DATABASE") {
            @Override
            public StockService createInstance() {
                return new DatabaseStockService();
            }
        },
        YAHOO("YAHOO") {
            @Override
            public StockService createInstance() {
                return new YahooStockServiceAdapter();
            }
        };

        private final String string;

        ServiceType(String string) {
            this.string = string;
        }

        public abstract StockService createInstance();
        public String string() { return string; }

        public static ServiceType fromString(String serviceTypeString) {

            for (ServiceType serviceType : ServiceType.values()) {
                if (serviceType.string.equalsIgnoreCase(serviceTypeString)) {
                    return serviceType;
                }
            }

            throw new IllegalArgumentException("No service type matching " + serviceTypeString + " was found.");
        }
    }

    /**
     * I am hiding the constructor because this class contains only static methods.
     */
    private ServiceFactory() {
    }

    /**
     * @return                          an object implementing the {@code StockService} interface (one of the author's
     *                                  choosing)
     * @throws StockServiceException    if unable to do this
     */
    public static StockService getStockService() throws StockServiceException {
        try {
            return new YahooStockServiceAdapter(); // replace as needed with any stock service
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            throw new StockServiceException("Unable to produce an instance of a class that implements the StockService interface.");
        }
    }

    /**
     * @param type                      the type of {@code StockService} desired
     * @return                          an object of type {@code type} implementing the {@code StockService} interface
     * @throws StockServiceException    if unable to do this
     */
    public static StockService getStockService(ServiceType type) throws StockServiceException {
        try {
            return type.createInstance();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            throw new StockServiceException(
                    String.format("Unable to produce an instance of StockService of type %s that implements the StockService interface.",
                                   type.string()));
        }
    }

    /**
     * @return                          an object implementing the {@code PersonService} interface
     * @throws PersonServiceException   if unable to do this
     */
    public static PersonService getPersonService() throws PersonServiceException {
        try {
            return new DatabasePersonService(); // replace as needed with any person service
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            throw new PersonServiceException("Unable to produce an instance of a class that implements the PersonService interface.");
        }
    }
}
