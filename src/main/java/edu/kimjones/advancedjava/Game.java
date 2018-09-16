package edu.kimjones.advancedjava;

import java.util.List;
import java.util.ArrayList;

/**
 * Main entry point for the superhero game.
 */
public class Game {

    /**
     * @param args      no args are required
     */
    public static void main(String[] args) {

        ArrayList<Superpower> javaManSuperpowers = new ArrayList<Superpower>();
        javaManSuperpowers.add(new Invisibility());
        javaManSuperpowers.add(new GreatStrength());

        ArrayList<Superpower> gitTheMightySuperpowers = new ArrayList<Superpower>();
        gitTheMightySuperpowers.add(new ShapeShifting("lion"));

        List<Superhero> heroes = new ArrayList<Superhero>();
        heroes.add(new Superhero("Java Man", Gender.Male, javaManSuperpowers, new Sword(),"For UML!"));
        heroes.add(new Superhero("Git the Mighty", Gender.Female, gitTheMightySuperpowers, null,"I've got this!"));

        for (Superhero hero: heroes) {
            hero.activateSuperpowers();
            hero.attackEnemy("C#");
        }
    }
}
