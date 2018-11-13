package edu.kimjones.advancedjava.superheroes;

/**
 * This class models a sword.
 *
 * @author Kim Jones
 */
public final class Sword implements Weapon {

    /**
     * causes the sword to strike an enemy
     *
     * @param enemyName  hte name of the enemy the sword should strike
     */
    public void attackEnemy(String enemyName) {
        if (enemyName == null) {
            System.out.println("Swung and missed!");
        } else {
            System.out.printf("Chopped %s clean in two!\n", enemyName);
        }
    }
}
