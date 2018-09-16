package edu.kimjones.advancedjava;

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
    public void attackEnemy(String enemyName);

}
