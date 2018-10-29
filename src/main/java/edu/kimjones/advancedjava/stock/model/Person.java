package edu.kimjones.advancedjava.stock.model;

import java.sql.Timestamp;

/**
 * This class models a person.
 *
 * @author Kim Jones (using code obtained from Spencer Marks)
 */
final public class Person {

    private final String username;
    private final String firstName;
    private final String lastName;
    private final Timestamp birthDate;

    public Person(String username, String firstName, String lastName, Timestamp birthDate) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Timestamp getBirthDate() {
        return birthDate;
    }

    /**
     * @param obj   a {@code Person} instance
     * @return      true if two @code Person} instances are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Person)) {
            return false;
        }

        Person rhs = (Person) obj;

        if (username != null ? !username.equals(rhs.username) : rhs.username != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(rhs.firstName) : rhs.firstName != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(rhs.lastName) : rhs.lastName != null) {
            return false;
        }

        return birthDate != null ? birthDate.equals(rhs.birthDate) : rhs.birthDate == null;
    }

    /**
     * @return an integer which uniquely identifies a {@code Person} instance
     */
    @Override
    public int hashCode() {
        int result = (username != null ? username.hashCode() : 31);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        return result;
    }
}
