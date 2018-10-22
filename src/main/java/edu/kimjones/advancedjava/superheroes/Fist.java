package edu.kimjones.advancedjava.superheroes;

/**
 * This class models a fist.
 *
 * @author Kim Jones
 */
public final class Fist implements Weapon {

    /**
     * causes the fist to strike an enemy
     *
     * @param enemyName  the name of the enemy the fist should strike
     */
    public void attackEnemy(String enemyName) {
        if (enemyName == null) {
            System.out.println("Swung and missed!");
        } else {
            System.out.printf("Hit %s right in the kisser\n", enemyName);
        }
    }
}

