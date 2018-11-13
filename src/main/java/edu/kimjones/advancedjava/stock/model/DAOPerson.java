package edu.kimjones.advancedjava.stock.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.sql.Timestamp;

/**
 * This class models a person.
 *
 * @author Kim Jones (using code obtained from Spencer Marks)
 */
@Entity
@Table(name="person")
final public class DAOPerson implements DatabaseAccessObject {

    private int id;
    private String firstName;
    private String lastName;
    private Timestamp birthDate;

    /**
     * Creates a {@code DAOPerson} instance that needs to be initialized.
     */
    public DAOPerson() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * @return a unique id representing a particular row in the person table
     */
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Sets the unique id for a particular row in the person table. Should not be called by client code as the value is
     * managed internally.
     *
     * @param id a unique int value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the person's first name
     */
    @Basic
    @Column(name = "first_name", nullable = false, length = 256)
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the person's first name.
     *
     * @param firstName a String value
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the person's last name
     */
    @Basic
    @Column(name = "last_name", nullable = false, length = 256)
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the person's last name.
     *
     * @param lastName a String value
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the person's date of birth
     */
    @Basic
    @Column(name = "birth_date", nullable = false)
    public Timestamp getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the person's date of birth.
     *
     * @param birthDate a Timestamp value
     */
    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @param obj   a {@code DAOPerson} instance
     * @return      true if two @code DAOPerson} instances are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof DAOPerson)) return false;

        DAOPerson rhs = (DAOPerson) obj;

        if (id != rhs.id) return false;

        if (birthDate != null ? !birthDate.equals(rhs.birthDate) : rhs.birthDate != null)
            return false;
        if (firstName != null ? !firstName.equals(rhs.firstName) : rhs.firstName != null)
            return false;
        return lastName != null ? lastName.equals(rhs.lastName) : rhs.lastName == null;
    }

    /**
     * @return an integer which uniquely identifies a {@code DAOPerson} instance
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DAOPerson{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
