package edu.kimjones.advancedjava.superheroes;

/**
 * This interface specifies what a weapon must do.
 *
 *  @author Kim Jones
 */
public interface Weapon {

    /**
     * causes the weapon to attack an enemy
     *
     * @param enemyName     the name of the enemy to attack
     */
    void attackEnemy(String enemyName);

}
