package edu.kimjones.advancedjava.stock.services;

import edu.kimjones.advancedjava.stock.model.Person;
import edu.kimjones.advancedjava.stock.model.PersonStock;
import edu.kimjones.advancedjava.stock.utilities.DatabaseUtility;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;


/**
 * This class models a person service that gets data from a database.
 *
 * @author Kim Jones
 */
public class DatabasePersonService implements PersonService {

    /**
     * This method gets a list of people managed by the service.
     *
     * @return                          a list of {@code Person} instances
     * @throws PersonServiceException   if a service can not read or write the requested data or otherwise perform the
     *                                  requested operation
     */
    @Override
    @SuppressWarnings("unchecked") // used to suppress warnings from criteria.list
    public List<Person> getPersonList() throws PersonServiceException {

        Session session = DatabaseUtility.getSessionFactory().openSession();
        List<Person> returnValue;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Person.class);
            returnValue = criteria.list();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
            throw new PersonServiceException("Could not get Person data: " + e.getMessage(), e);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }

        return returnValue;
    }

    /**
     * This method adds a new person to the list of people already managed by the service, or or updates an person
     * already in that list.
     *
     * @param person                    a {@code Person} instance
     * @throws PersonServiceException   if a service can not read or write the requested data or otherwise perform the
     *                                  requested operation
     */
    @Override
    public void addOrUpdatePerson(Person person) throws PersonServiceException {
        Session session = DatabaseUtility.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(person);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }

    /**
     * This method returns a list of all the stocks the given person is interested in.
     *
     * @return                          a list of stock symbols
     * @throws PersonServiceException   if a service can not read or write the requested data or otherwise perform the
     *                                  requested operation
     */
    @Override
    @SuppressWarnings("unchecked") // used to suppress warnings from criteria.list
    public List<String> getStocks(Person person) throws PersonServiceException {
        Session session =  DatabaseUtility.getSessionFactory().openSession();
        Transaction transaction = null;
        List<String> stocks = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(PersonStock.class);
            criteria.add(Restrictions.eq("person", person));

            List<PersonStock> list = criteria.list();
            for (PersonStock personStock : list) {
                stocks.add(personStock.getStockSymbol());
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
        return stocks;
    }

    /**
     * This method associates a stock with a person.
     *
     * @param stockSymbol               a stock symbol
     * @param person                    a {@code Person} instance
     * @throws PersonServiceException   if a service can not read or write the requested data or otherwise perform the
     *                                  requested operation
     */
    @Override
    public void addStockToPerson(String stockSymbol, Person person) throws PersonServiceException {
        Session session =  DatabaseUtility.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            PersonStock personStock = new PersonStock();
            personStock.setStockSymbol(stockSymbol);
            personStock.setPerson(person);
            session.saveOrUpdate(personStock);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
            System.out.println(e.getMessage());
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }
}
