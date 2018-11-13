package edu.kimjones.advancedjava.superheroes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class models a superhero.
 *
 * @author Kim Jones
 */
final public class Superhero {

    private final String name;
    private final Gender gender;
    private final List<Superpower> superpowers;
    private final Weapon weapon;
    private final String warCry;

    /**
     * This constructor creates a new superhero.
     *
     * @param name          the name of the superhero
     * @param gender        the gender of the superhero
     * @param superpowers   the superhero's superpowers
     * @param weapon        the superhero's weapon
     * @param warCry        what the superhero yells when attacking
     */
    public Superhero(String name, Gender gender, List<Superpower> superpowers, Weapon weapon, String warCry) {

        this.name = Objects.requireNonNull(name, "name cannot be null");

        this.gender = Objects.requireNonNull(gender, "gender cannot be null");

        // make copy of superpowers list
        this.superpowers = new ArrayList<>(superpowers);

        if (weapon == null) {
            this.weapon = new Fist();
        } else {
            this.weapon = weapon;
        }

        this.warCry = warCry;
    }

    /**
     * @return the name of this superhero
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the gender of this superhero
     */
    public Gender getGender()
    {
        return gender;
    }

    /**
     * @return a copy of the superpowers of this superhero
     */
    public List<Superpower> getSuperpowers()
    {
        return new ArrayList<>(this.superpowers);
    }

    /**
     * @return this superhero's weapon
     */
    public Weapon getWeapon()
    {
        return weapon;
    }

    /**
     * @return this superhero's war cry
     */
    public String getWarCry()
    {
        return warCry;
    }

    /**
     * activate this superhero's superpowers
     */
    public void activateSuperpowers(){

        for (Superpower superpower: superpowers) {
            System.out.printf("%s: ", this.name);
            superpower.activate();
        }
    }

    /**
     * cause this superhero to attack
     *
     * @param enemyName     the name of the enemy the superhero should attack
     */
    public void attackEnemy(String enemyName) {

        System.out.printf("%s: ", this.name);
        this.weapon.attackEnemy(enemyName);

        System.out.printf("%s: ", this.name);
        if (this.warCry != null) {
            System.out.println(this.warCry);
        }
    }
}
