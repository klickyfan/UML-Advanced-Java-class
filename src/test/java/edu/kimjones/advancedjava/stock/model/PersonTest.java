package edu.kimjones.advancedjava.stock.model;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * This class is for testing the class {@code Person}.
 *
 * @author Kim Jones
 */
public class PersonTest {

    private static final Calendar birthDayCalendar = Calendar.getInstance();

    static {
        birthDayCalendar.set(1990, Calendar.JANUARY, 12);
    }

    private static final String username = "smarks";
    private static final String firstName = "Spencer";
    private static final String lastName = "Marks";
    private static final Timestamp birthDate = Timestamp.valueOf("1990-01-12 00:00:00.00");

    private Person person;

    @Before
    public void setUp() {
        this.person = new Person(username, firstName, lastName, birthDate);
    }

    @Test
    public void testPersonGettersAndSettersPositive() {
        assertEquals("username matches", username, this.person.getUsername());
        assertEquals("first name matches", firstName, this.person.getFirstName());
        assertEquals("last name matches", lastName, this.person.getLastName());
        assertEquals("birthday matches", birthDate, this.person.getBirthDate());
    }

    @Test
    public void testPersonGettersAndSettersNegative() {
        assertNotSame("username does not match", "kjones", this.person.getUsername());
        assertNotSame("first name does not match", "Kim", this.person.getFirstName());
        assertNotSame("last name does not match", "Jones", this.person.getLastName());
        assertNotSame("birthday des not match", Timestamp.valueOf("2018-10-01 00:00:00.00"), this.person.getBirthDate());
    }

    @Test
    public void testEqualsContract() {
        EqualsVerifier.forClass(Person.class).verify();
    }
}
