package edu.kimjones.advancedjava.stock.services;

import edu.kimjones.advancedjava.stock.model.Person;
import edu.kimjones.advancedjava.stock.model.PersonTest;
import edu.kimjones.advancedjava.stock.utilities.DatabaseUtility;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

        this.testPerson = new Person();
        this.testPerson.setId(PersonTest.id);
        this.testPerson.setFirstName(PersonTest.firstName);
        this.testPerson.setLastName(PersonTest.lastName);
        this.testPerson.setBirthDate(PersonTest.birthDate);
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
        assertFalse("person list is null", personList == null);
    }

    @Test
    public void testAddOrUpdatePersonPositive() throws PersonServiceException {

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

        List<String> stocks = personService.getStocks(personList.get(0));
        assertTrue("stock list has 3 stocks", stocks.size() == 3);
        assertTrue("stock list contains GOOG", stocks.contains("GOOG"));
        assertTrue("stock list contains GOOG", stocks.contains("AAPL"));
        assertTrue("stock list contains GOOG", stocks.contains("NFLX"));
    }

    @Test
    public void testGetStocksNegative() throws PersonServiceException {

        List<Person> personList = personService.getPersonList();

        List<String> stocks = personService.getStocks(personList.get(0));
        assertFalse("stock list is empty", stocks.isEmpty());
        assertFalse("stock list has 1 stock", stocks.size() == 1);
        assertFalse("stock list contains ABC", stocks.contains("ABC"));
    }

    @Test
    public void testAddStockToPersonPositive() throws PersonServiceException {

        personService.addOrUpdatePerson(testPerson);

        List<String> stocks = personService.getStocks(testPerson);
        assertTrue("initial stock list is empty", stocks.isEmpty());

        personService.addStockToPerson("ABC",testPerson);
        stocks = personService.getStocks(testPerson);
        assertTrue("stock list now has 1 stock", stocks.size() == 1);
        assertTrue("stock list contains ABC", stocks.get(0).equals("ABC"));

        personService.addStockToPerson("123",testPerson);
        stocks = personService.getStocks(testPerson);
        assertTrue("stock list now has 2 stocks", stocks.size() == 2);
        assertTrue("stock list contains 123", stocks.get(1).equals("123"));
    }

    @Test
    public void testAddStockToPersonNegative() throws PersonServiceException {

        personService.addOrUpdatePerson(testPerson);

        List<String> stocks = personService.getStocks(testPerson);
        assertFalse("initial stock list has 1 item", stocks.size() == 1);

        personService.addStockToPerson("ABC",testPerson);
        stocks = personService.getStocks(testPerson);
        assertFalse("stock list now is empty", stocks.isEmpty());
        assertFalse("stock list contains 123", stocks.get(0).equals("123"));
    }
}

