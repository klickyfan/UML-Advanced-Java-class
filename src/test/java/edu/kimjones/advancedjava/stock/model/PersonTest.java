package edu.kimjones.advancedjava.stock.model;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


/**
 * This class is for testing the class Person.
 *
 * @author Kim Jones
 */
public class PersonTest {

    public  static final Calendar birthDayCalendar = Calendar.getInstance();

    static {
        birthDayCalendar.set(1990, Calendar.JANUARY, 12);
    }

    // these are public to allow them to be used by PersonTest
    public static final int id = 10;
    public static final String firstName = "Spencer";
    public static final String lastName = "Marks";
    public static final Timestamp birthDate = Timestamp.valueOf("1990-01-12 00:00:00.00");

    private Person person;

    @Before
    public void setUp() throws Exception {
        this.person = new Person();
        this.person.setId(this.id);
        this.person.setFirstName(this.firstName);
        this.person.setLastName(this.lastName);
        this.person.setBirthDate(this.birthDate);
    }

    @Test
    public void testPersonGettersAndSettersPositive() {
        assertEquals("id matches", id, this.person.getId());
        assertEquals("first name matches", firstName, this.person.getFirstName());
        assertEquals("last name matches", lastName, this.person.getLastName());
        assertEquals("birthday matches", birthDate, this.person.getBirthDate());
    }

    @Test
    public void testPersonGettersAndSettersNegative() {
        assertFalse("id does not match", 20 == this.person.getId());
        assertFalse("first name does not match", "Kim" == this.person.getFirstName());
        assertFalse("last name does not match", "Jones" == this.person.getLastName());
        assertFalse("birthday des not match", Timestamp.valueOf("2018-10-01 00:00:00.00") == this.person.getBirthDate());
    }

    @Test
    public void testEqualsContract() {
        EqualsVerifier.forClass(Person.class).verify();
    }
}
