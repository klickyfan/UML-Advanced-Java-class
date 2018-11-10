package edu.kimjones.advancedjava.superheroes;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class is for testing the class Superhero
 *
 * @author Kim Jones
 */
public class SuperheroTest {

    private static final String NAME = "Java Man";
    private static final Gender GENDER = Gender.Male;
    private static final String SHAPE = "lion";
    private static final String WAR_CRY = "For UML!";
    
    private static ArrayList<Superpower> superpowers = new ArrayList<>();
    private static Weapon weapon = new Sword();

    private static Superhero superhero;

    @BeforeClass
    public static void setUp() {

        superpowers.add(new Invisibility());
        superpowers.add(new ShapeShifting(SHAPE));

        superhero = new Superhero(NAME, GENDER, superpowers, weapon, WAR_CRY);
    }

    @Test
    public void testNamePositive() {
        assertEquals("NAME matches", NAME, superhero.getName());
    }

    @Test
    public void testNameNegative() {
        assertNotSame("NAME does not match", "Git the Mighty", superhero.getName());
    }

    @Test
    public void testGenderPositive() {
        assertEquals("GENDER matches", GENDER, superhero.getGender());
    }

    @Test
    public void testGenderNegative() {
        assertNotSame("GENDER does not match", Gender.Female, superhero.getGender());
    }

    @Test
    public void testSuperpowersPositive() {

        List<Superpower> superpowers = superhero.getSuperpowers();
        assertEquals("there are two superpowers", superpowers.size(), 2);

        Superpower superpower0 = superpowers.get(0);
        assertEquals("superpower 0 is invisibility", superpower0.getClass(), Invisibility.class);

        Superpower superpower1 = superpowers.get(1);
        assertEquals("superpower 0 is SHAPE shifting", superpower1.getClass(), ShapeShifting.class);

        assertEquals("SHAPE matches", SHAPE, ((ShapeShifting) superpower1).getShape());
    }

    @Test
    public void testSuperpowersNegative() {

        List<Superpower> superpowers = superhero.getSuperpowers();
        assertNotEquals("there is one superpower", 1, superpowers.size());

        Superpower superpower0 = superpowers.get(0);
        assertNotSame("superpower 0 is SHAPE shifting", superpower0.getClass(), ShapeShifting.class);

        Superpower superpower1 = superpowers.get(1);
        assertNotSame("superpower 0 is invisibility", superpower1.getClass(), Invisibility.class);

        assertNotSame("SHAPE does not match", "bear", ((ShapeShifting) superpower1).getShape());
    }

    @Test
    public void testWeaponPositive() {
        assertEquals("weapon matches", weapon, superhero.getWeapon());
    }

    @Test
    public void testWeaponNegative() {
        assertNotSame("weapon does not match", new Fist(), superhero.getWeapon());
    }

    @Test
    public void testWarCryPositive() {
        assertEquals("war cry match", WAR_CRY, superhero.getWarCry());
    }

    @Test
    public void testWarCryNegative() {
        assertNotSame("war cry does not match", "Attack!", superhero.getWarCry());
    }

}