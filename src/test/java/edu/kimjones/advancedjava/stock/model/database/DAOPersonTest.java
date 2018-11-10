package edu.kimjones.advancedjava.stock.model.database;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * This class is for testing the class {@code DAOPerson}.
 *
 * @author Kim Jones
 */
public class DAOPersonTest {

    private static final Calendar BIRTH_DAY_CALENDAR = Calendar.getInstance();

    static {
        BIRTH_DAY_CALENDAR.set(1990, Calendar.JANUARY, 12);
    }

    // these are public to allow them to be used by DAOPersonStockTest
    public static final int ID = 10;
    public static final String USERNAME = "smarks";
    public static final String FIRST_NAME = "Spencer";
    public static final String LAST_NAME = "Marks";
    public static final Timestamp BIRTH_DATE = Timestamp.valueOf("1990-01-12 00:00:00.00");

    private static DAOPerson person;

    @BeforeClass
    public static void setUp() {
        person = new DAOPerson();
        person.setId(ID);
        person.setUsername(USERNAME);
        person.setFirstName(FIRST_NAME);
        person.setLastName(LAST_NAME);
        person.setBirthDate(BIRTH_DATE);
    }

    @Test
    public void testPersonGettersAndSettersPositive() {
        assertEquals("ID matches", ID, person.getId());
        assertEquals("USERNAME matches", USERNAME, person.getUsername());
        assertEquals("first name matches", FIRST_NAME, person.getFirstName());
        assertEquals("last name matches", LAST_NAME, person.getLastName());
        assertEquals("birthday matches", BIRTH_DATE, person.getBirthDate());
    }

    @Test
    public void testPersonGettersAndSettersNegative() {
        assertNotEquals("ID does not match", 20, person.getId());
        assertNotSame("USERNAME does not match", "kjones", person.getUsername());
        assertNotSame("first name does not match", "Kim", person.getFirstName());
        assertNotSame("last name does not match", "Jones", person.getLastName());
        assertNotSame("birthday des not match", Timestamp.valueOf("2018-10-01 00:00:00.00"), person.getBirthDate());
    }

    @Test
    public void testEqualsContract() {
        EqualsVerifier.forClass(DAOPerson.class).verify();
    }
}
