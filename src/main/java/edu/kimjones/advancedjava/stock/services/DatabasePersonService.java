package edu.kimjones.advancedjava.stock.services;

import edu.kimjones.advancedjava.stock.model.database.DAOPerson;
import edu.kimjones.advancedjava.stock.model.Person;
import edu.kimjones.advancedjava.stock.model.database.DAOPersonStock;
import edu.kimjones.advancedjava.stock.utilities.DatabaseAddOrUpdateException;
import edu.kimjones.advancedjava.stock.utilities.DatabaseGetListException;
import edu.kimjones.advancedjava.stock.utilities.DatabaseUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * This class models a person service that gets data from a database.
 *
 * @author Kim Jones
 */
public class DatabasePersonService implements PersonService {

    /**
     * Gets a list of people managed by the service.
     *
     * @return                          a list of {@code DAOPerson} instances
     * @throws PersonServiceException   if a service can not read or write the requested data or otherwise perform the
     *                                  requested operation
     */
    @Override
    public List<Person> getPersonList() throws PersonServiceException {
         List<Person> personList = new ArrayList<>();
        try {
            List<DAOPerson> daoPersonList = DatabaseUtility.getListBy("", null, DAOPerson.class);
            for (DAOPerson daoPerson: daoPersonList) {
                personList.add(new Person(daoPerson.getUsername(), daoPerson.getFirstName(), daoPerson.getLastName(), daoPerson.getBirthDate()));
            }
        } catch (DatabaseGetListException exception) {
            throw new PersonServiceException("Could not get a list of persons managed by the service.", exception);
        }
        return personList;
    }

    /**
     * Adds a new person to the list of people already managed by the service, or or updates an person already in that
     * list.
     *
     * @param person                    a {@code DAOPerson} instance
     * @throws PersonServiceException   if a service can not read or write the requested data or otherwise perform the
     *                                  requested operation
     */
    @Override
    public void addOrUpdatePerson(Person person) throws PersonServiceException {
        try {
            DAOPerson daoPerson = new DAOPerson();
            daoPerson.setUsername(person.getUsername());
            daoPerson.setFirstName(person.getFirstName());
            daoPerson.setLastName(person.getLastName());
            daoPerson.setBirthDate(person.getBirthDate());
            DatabaseUtility.addOrUpdate(daoPerson);
        } catch (DatabaseAddOrUpdateException exception) {
            String message = String.format("Could not add or update %s %s.", person.getFirstName(), person.getLastName());
            throw new PersonServiceException(message, exception);
        }
    }

    /**
     * Returns a list of all the stocks the given person is interested in.
     *
     * @param person                    a {@code DAOPerson}
     * @return                          a list of stock symbols
     * @throws PersonServiceException   if a service can not read or write the requested data or otherwise perform the
     *                                  requested operation
     */
    @Override
    @SuppressWarnings("unchecked") // used to suppress warnings from criteria.list
    public List<String> getStockList(Person person) throws PersonServiceException {

        List<String> stocks = new ArrayList<>();

        try {
            DAOPerson daoPerson = getDAOPerson(person);

            List<DAOPersonStock> personStockList = DatabaseUtility.getListBy("person", daoPerson, DAOPersonStock.class);
            for (DAOPersonStock personStock : personStockList) {
                stocks.add(personStock.getStockSymbol());
            }
        } catch (DatabaseGetListException exception){
            String message = String.format("Could not get stocks for %s %s.", person.getFirstName(), person.getLastName());
            throw new PersonServiceException(message, exception);
        }

        return stocks;
    }

    /**
     * Associates a stock with a person.
     *
     * @param stockSymbol               a stock symbol
     * @param person                    a {@code DAOPerson} instance
     * @throws PersonServiceException   if a service can not read or write the requested data or otherwise perform the
     *                                  requested operation
     */
    @Override
    public void addStockToPerson(String stockSymbol, Person person) throws PersonServiceException {

        DAOPerson daoPerson = getDAOPerson(person);

        DAOPersonStock personStock = new DAOPersonStock();
        personStock.setStockSymbol(stockSymbol);
        personStock.setPerson(daoPerson);

        try {
            DatabaseUtility.addOrUpdate(personStock);
        } catch (DatabaseAddOrUpdateException exception) {
            String message = String.format("Could not add stock with symbol %s to %s %s.", stockSymbol, person.getFirstName(), person.getLastName());
            throw new PersonServiceException(message, exception);
        }
    }

    /**
     * Returns the {@code DAOPerson} that is assoiated with the give {@code Person}, that is, the one with the same
     * username.
     *
     * @param person                    a {@code Person}
     * @return                          a {@code DAOPerson}
     * @throws PersonServiceException   if a service can not read or write the requested data or otherwise perform the
     *                                  requested operation
     */
    private DAOPerson getDAOPerson(Person person) throws PersonServiceException {

        List<DAOPerson> daoPersonList = null;

        try {
            daoPersonList = DatabaseUtility.getListBy("username", person.getUsername(), DAOPerson.class);
        } catch (DatabaseGetListException exception) {
            String message = String.format("Could not find person with username %s.", person.getUsername());
            throw new PersonServiceException(message, exception);
        }

        if (daoPersonList == null || daoPersonList.isEmpty()) {
            String message = String.format("Could not find person with username %s.", person.getUsername());
            throw new PersonServiceException(message);
        }

        /// there should only be 1 in the list
        return daoPersonList.get(0);
    }
}
