package edu.kimjones.advancedjava.superheroes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class is for testing the class Superhero
 *
 * @author Kim Jones
 */
public class SuperheroTest {

    private final String name = "Java Man";
    private final Gender gender = Gender.Male;
    private final String shape = "lion";
    private final ArrayList<Superpower> superpowers = new ArrayList<>();
    private final Weapon weapon = new Sword();
    private final String warCry = "For UML!";

    private Superhero superhero;

    @org.junit.Before
    public void setUp() {

        this.superpowers.add(new Invisibility());
        this.superpowers.add(new ShapeShifting(this.shape));

        this.superhero = new Superhero(this.name, this.gender, this.superpowers, this.weapon, this.warCry);
    }

    @org.junit.Test
    public void testNamePositive() {
        assertEquals("name matches", this.name, this.superhero.getName());
    }

    @org.junit.Test
    public void testNameNegative() {
        assertNotSame("name does not match", "Git the Mighty", superhero.getName());
    }

    @org.junit.Test
    public void testGenderPositive() {
        assertEquals("gender matches", this.gender, this.superhero.getGender());
    }

    @org.junit.Test
    public void testGenderNegative() {
        assertNotSame("gender does not match", Gender.Female, superhero.getGender());
    }

    @org.junit.Test
    public void testSuperpowersPositive() {

        List<Superpower> superpowers = this.superhero.getSuperpowers();
        assertEquals("there are two superpowers", superpowers.size(), 2);

        Superpower superpower0 = superpowers.get(0);
        assertEquals("superpower 0 is invisibility", superpower0.getClass(), Invisibility.class);

        Superpower superpower1 = superpowers.get(1);
        assertEquals("superpower 0 is shape shifting", superpower1.getClass(), ShapeShifting.class);

        assertEquals("shape matches", this.shape, ((ShapeShifting) superpower1).getShape());
    }

    @org.junit.Test
    public void testSuperpowersNegative() {

        List<Superpower> superpowers = this.superhero.getSuperpowers();
        assertNotEquals("there is one superpower", 1, superpowers.size());

        Superpower superpower0 = superpowers.get(0);
        assertNotSame("superpower 0 is shape shifting", superpower0.getClass(), ShapeShifting.class);

        Superpower superpower1 = superpowers.get(1);
        assertNotSame("superpower 0 is invisibility", superpower1.getClass(), Invisibility.class);

        assertNotSame("shape does not match", "bear", ((ShapeShifting) superpower1).getShape());
    }

    @org.junit.Test
    public void testWeaponPositive() {
        assertEquals("weapon matches", this.weapon, superhero.getWeapon());
    }

    @org.junit.Test
    public void testWeaponNegative() {
        assertNotSame("weapon does not match", new Fist(), superhero.getWeapon());
    }

    @org.junit.Test
    public void testWarCryPositive() {
        assertEquals("war cry match", this.warCry, superhero.getWarCry());
    }

    @org.junit.Test
    public void testWarCryNegative() {
        assertNotSame("war cry does not match", "Attack!", superhero.getWarCry());
    }

}