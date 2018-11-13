package edu.kimjones.advancedjava.stock.model.database;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
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

    private static final Calendar birthDayCalendar = Calendar.getInstance();

    static {
        birthDayCalendar.set(1990, Calendar.JANUARY, 12);
    }

    // these are public to allow them to be used by DAOPersonStockTest
    public static final int id = 10;
    public static final String username = "smarks";
    public static final String firstName = "Spencer";
    public static final String lastName = "Marks";
    public static final Timestamp birthDate = Timestamp.valueOf("1990-01-12 00:00:00.00");

    private DAOPerson person;

    @Before
    public void setUp() {
        this.person = new DAOPerson();
        this.person.setId(id);
        this.person.setUsername(username);
        this.person.setFirstName(firstName);
        this.person.setLastName(lastName);
        this.person.setBirthDate(birthDate);
    }

    @Test
    public void testPersonGettersAndSettersPositive() {
        assertEquals("id matches", id, this.person.getId());
        assertEquals("username matches", username, this.person.getUsername());
        assertEquals("first name matches", firstName, this.person.getFirstName());
        assertEquals("last name matches", lastName, this.person.getLastName());
        assertEquals("birthday matches", birthDate, this.person.getBirthDate());
    }

    @Test
    public void testPersonGettersAndSettersNegative() {
        assertNotEquals("id does not match", 20, this.person.getId());
        assertNotSame("username does not match", "kjones", this.person.getUsername());
        assertNotSame("first name does not match", "Kim", this.person.getFirstName());
        assertNotSame("last name does not match", "Jones", this.person.getLastName());
        assertNotSame("birthday des not match", Timestamp.valueOf("2018-10-01 00:00:00.00"), this.person.getBirthDate());
    }

    @Test
    public void testEqualsContract() {
        EqualsVerifier.forClass(DAOPerson.class).verify();
    }
}
