package org.wahlzeit.model.coordinate;

import com.google.common.base.MoreObjects;

/**
 * Represents an <u>immutable</u> Coordinate (X, Y, Z).<br>
 * For details see: <a href="https://en.wikipedia.org/wiki/Cartesian_coordinate_system">Cartesian Coordinate at wikipedia</a>
 */
public class CartesianCoordinate implements Coordinate {
    private final double x;
    private final double y;
    private final double z;

    public CartesianCoordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Calculates the distance between the given coordinates in meters.
     * For details see <a href="https://en.wikipedia.org/wiki/Cartesian_coordinate_system#Distance_between_two_points">wikipedia</a>.
     * @param a Coordinate A
     * @param b Coordinate B
     * @return Distance in meters
     */
    public static double getDistance(CartesianCoordinate a, CartesianCoordinate b) {
        return Math.sqrt( (b.x - a.x) + (b.y - a.y) + (b.z - a.z));
    }

    @Override
    public double getDistance(Coordinate other) throws IllegalArgumentException {
        if(other instanceof CartesianCoordinate) {
            return getDistance(this, (CartesianCoordinate) other);
        }
        else {
            throw new IllegalArgumentException("Given coordinate of type " + other.getClass().getCanonicalName() + " not supported");
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("x", x)
                .add("y", y)
                .add("z", z)
                .toString();
    }
}
