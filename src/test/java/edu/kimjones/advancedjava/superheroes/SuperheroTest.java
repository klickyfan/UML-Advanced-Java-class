package edu.kimjones.advancedjava;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class is for testing the class Superhero
 *
 * @author Kim Jones
 */
public class SuperheroTest {

    private String name = "Java Man";
    private Gender gender = Gender.Male;
    private String shape = "lion";
    private ArrayList<Superpower> superpowers = new ArrayList<Superpower>();
    private Weapon weapon = new Sword();
    private String warCry = "For UML!";

    private Superhero superhero;

    @org.junit.Before
    public void setUp() throws Exception {

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
        assertFalse("name does not match", "Git the Mighty" == superhero.getName());
    }

    @org.junit.Test
    public void testGenderPositive() {
        assertEquals("gender matches", this.gender, this.superhero.getGender());
    }

    @org.junit.Test
    public void testGenderNegative() {
        assertFalse("gender does not match", Gender.Female == superhero.getGender());
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
        assertFalse("there is one superpower", superpowers.size() == 1);

        Superpower superpower0 = superpowers.get(0);
        assertFalse("superpower 0 is shape shifting", superpower0.getClass() == ShapeShifting.class);

        Superpower superpower1 = superpowers.get(1);
        assertFalse("superpower 0 is invisibility", superpower1.getClass() == Invisibility.class);

        assertFalse("shape does not matche", "bear" == ((ShapeShifting) superpower1).getShape());
    }

    @org.junit.Test
    public void testWeaponPositive() {
        assertEquals("weapon matches", this.weapon, superhero.getWeapon());
    }

    @org.junit.Test
    public void testWeaponNegative() {
        assertFalse("weapon does not match", new Fist() == superhero.getWeapon());
    }

    @org.junit.Test
    public void testWarCryPositive() {
        assertEquals("war cry match", this.warCry, superhero.getWarCry());
    }

    @org.junit.Test
    public void testWarCryNegative() {
        assertFalse("war cry does not match", "Attack!" == superhero.getWarCry());
    }

}