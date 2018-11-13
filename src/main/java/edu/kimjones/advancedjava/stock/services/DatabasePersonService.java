package edu.kimjones.advancedjava.stock.services;

import edu.kimjones.advancedjava.stock.model.DAOPerson;
import edu.kimjones.advancedjava.stock.model.DAOPersonStock;
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
     * Gets a list of people managed by the service.
     *
     * @return                          a list of {@code DAOPerson} instances
     * @throws PersonServiceException   if a service can not read or write the requested data or otherwise perform the
     *                                  requested operation
     */
    @Override
    @SuppressWarnings("unchecked") // used to suppress warnings from criteria.list
    public List<DAOPerson> getPersonList() throws PersonServiceException {

        Session session = DatabaseUtility.getSessionFactory().openSession();
        List<DAOPerson> returnValue;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(DAOPerson.class);
            returnValue = criteria.list();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
            throw new PersonServiceException("Could not get DAOPerson data. " + e.getMessage(), e);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }

        return returnValue;
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
    public void addOrUpdatePerson(DAOPerson person) throws PersonServiceException {

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
            throw new PersonServiceException("Could not add or update DAOPerson. " + e.getMessage(), e);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
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
    public List<String> getStocks(DAOPerson person) throws PersonServiceException {

        Session session =  DatabaseUtility.getSessionFactory().openSession();
        Transaction transaction = null;
        List<String> stocks = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(DAOPersonStock.class);
            criteria.add(Restrictions.eq("person", person));

            List<DAOPersonStock> list = criteria.list();
            for (DAOPersonStock personStock : list) {
                stocks.add(personStock.getStockSymbol());
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
            throw new PersonServiceException("Could not get stocks for DAOPPerson. " + e.getMessage(), e);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
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
    public void addStockToPerson(String stockSymbol, DAOPerson person) throws PersonServiceException {

        Session session =  DatabaseUtility.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            DAOPersonStock personStock = new DAOPersonStock();
            personStock.setStockSymbol(stockSymbol);
            personStock.setPerson(person);
            session.saveOrUpdate(personStock);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
            throw new PersonServiceException("Could not link stock to DAOPerson. " + e.getMessage(), e);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }
}
