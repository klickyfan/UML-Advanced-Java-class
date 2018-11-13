package edu.kimjones.advancedjava.stock.services;

import edu.kimjones.advancedjava.stock.model.database.DAOPersonTest;
import edu.kimjones.advancedjava.stock.model.Person;
import edu.kimjones.advancedjava.stock.utilities.DatabaseUtility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * This class is for testing the DatabasePersonService class.
 *
 * @author Kim Jones
 */
public class DatabasePersonServiceTest {

    private PersonService personService;
    private Person testPerson;

    @Before
    public void setUp() throws Exception {
        DatabaseUtility.initializeDatabase(DatabaseUtility.initializationFile);
        DatabaseUtility.initializeDatabase("./src/main/sql/add_person_service_test_data.sql");

        this.personService = ServiceFactory.getPersonService();

        this.testPerson = new Person(DAOPersonTest.username, DAOPersonTest.firstName, DAOPersonTest.lastName, DAOPersonTest.birthDate);
    }

    @After
    public void tearDown() throws Exception {
        DatabaseUtility.initializeDatabase(DatabaseUtility.initializationFile);
    }

    @Test
    public void getPersonListPositive() throws PersonServiceException {
        List<Person> personList = personService.getPersonList();
        assertTrue("person list a list", personList instanceof List);
        personService.addOrUpdatePerson(testPerson);
        personList = personService.getPersonList();
        assertTrue("person list a list of person instances", personList.size()>0 && (personList.get(0) instanceof Person));
    }

    @Test
    public void getPersonListNegative() throws PersonServiceException {
        List<Person> personList = personService.getPersonList();
        assertNotNull("person list is null", personList);
    }

    @Test
    public void testAddOrUpdatePerson() throws PersonServiceException {
        personService.addOrUpdatePerson(testPerson);
        List<Person> personList = personService.getPersonList();
        boolean found = false;
        for (Person person : personList) {
            if (person.equals(testPerson)) {
                found = true;
                break;
            }
        }
        assertTrue("found the testPerson we added", found);
    }

    @Test
    public void testGetStocksPositive() throws PersonServiceException {
        List<Person> personList = personService.getPersonList();
        List<String> stocks = personService.getStockList(personList.get(0));

        assertEquals("stock list has 3 stocks", 3, stocks.size());
        assertTrue("stock list contains GOOG", stocks.contains("GOOG"));
        assertTrue("stock list contains GOOG", stocks.contains("AAPL"));
        assertTrue("stock list contains GOOG", stocks.contains("NFLX"));
    }

    @Test
    public void testGetStocksNegative() throws PersonServiceException {
        List<Person> personList = personService.getPersonList();
        List<String> stocks = personService.getStockList(personList.get(0));

        assertFalse("stock list is empty", stocks.isEmpty());
        assertNotEquals("stock list has 1 stock", 1, stocks.size());
        assertFalse("stock list contains ABC", stocks.contains("ABC"));
    }

    @Test
    public void testAddStockToPersonPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(testPerson);

        List<String> stocks = personService.getStockList(testPerson);

        assertTrue("initial stock list is empty", stocks.isEmpty());

        personService.addStockToPerson("ABC",testPerson);
        stocks = personService.getStockList(testPerson);
        assertEquals("stock list now has 1 stock", 1, stocks.size());
        assertEquals("stock list contains ABC", "ABC", stocks.get(0));

        personService.addStockToPerson("123",testPerson);
        stocks = personService.getStockList(testPerson);
        assertEquals("stock list now has 2 stocks", 2, stocks.size());
        assertEquals("stock list contains 123", "123", stocks.get(1));
    }

    @Test
    public void testAddStockToPersonNegative() throws PersonServiceException {
        personService.addOrUpdatePerson(testPerson);

        List<String> stocks = personService.getStockList(testPerson);
        assertNotEquals("initial stock list has 1 item", 1, stocks.size());

        personService.addStockToPerson("ABC", testPerson);
        stocks = personService.getStockList(testPerson);
        assertFalse("stock list now is empty", stocks.isEmpty());
        assertNotEquals("stock list contains 123", "123", stocks.get(0));
    }
}

