package edu.kimjones.advancedjava.stock.services;

import edu.kimjones.advancedjava.stock.model.Person;

import java.util.List;

/**
 * This interface specifies what a person service must do.
 *
 * @author Kim Jones (using code obtained from Prof. Spencer Marks)
 **/
public interface PersonService {

    /**
     * This method gets a list of people managed by the service.
     *
     * @return                          a list of {@code Person} instances
     * @throws PersonServiceException   if a service can not read or write the requested data or otherwise perform the
     *                                  requested operation
     */
    List<Person> getPersonList() throws PersonServiceException;

    /**
     * This method adds a new person to the list of people already managed by the service, or or updates an person
     * already in that list.
     *
     * @param person                    a {@code Person} instance
     * @throws PersonServiceException   if a service can not read or write the requested data or otherwise perform the
     *                                  requested operation
     */
    void addOrUpdatePerson(Person person) throws PersonServiceException;

    /**
     * This method returns a list of all the stocks the given person is interested in.
     *
     * @return                          a list of stock symbols
     * @throws PersonServiceException   if a service can not read or write the requested data or otherwise perform the
     *                                  requested operation
     */
    List<String> getStocks(Person person) throws PersonServiceException;

    /**
     * This method associates a stock with a person.
     *
     * @param stockSymbol               a stock symbol
     * @param person                    a {@code Person} instance
     * @throws PersonServiceException   if a service can not read or write the requested data or otherwise perform the
     *                                  requested operation
     */
    public void addStockToPerson(String stockSymbol, Person person) throws PersonServiceException;

}
