package edu.kimjones.advancedjava.superheroes;

import java.util.Objects;

/**
 * This class models the shape shifting superpower.
 *
 * @author Kim Jones
 */
public final class ShapeShifting implements Superpower {

    private String shape;

    /**
     * This constructor creates a new ShapeShifting superpower.

     * @param shape     the shape to be shifted into
     */
    public ShapeShifting(String shape) {

        this.shape = Objects.requireNonNull(shape, "shape cannot be null");
    }

    /**
     * @return the shape that can be shifted into
     */
    public String getShape() {
        return this.shape;
    }

    /**
     * activate this superpower
     */
    public void activate() {
        System.out.printf("Becoming a %s...\n", shape);
    }
}
