package org.wahlzeit.model.coordinate;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Represents an <u>immutable</u> Coordinate (X, Y, Z).<br> For details see: <a href="https://en.wikipedia.org/wiki/Cartesian_coordinate_system">Cartesian
 * Coordinate at wikipedia</a>
 */
public class CartesianCoordinate extends AbstractCoordinate {
    private final double x;
    private final double y;
    private final double z;

    /**
     * Creates a new coordinate.
     * @param x
     * @param y
     * @param z
     * @throws IllegalArgumentException If any of the given values is {@link Double#isNaN(double)} or {@link Double#isInfinite(double)}.
     */
    public CartesianCoordinate(double x, double y, double z) throws IllegalArgumentException {
        isValidDouble(x);
        isValidDouble(y);
        isValidDouble(z);

        this.x = x;
        this.y = y;
        this.z = z;
    }

    private void isValidDouble(double value) {
        if(Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid value '" + value + "'");
        }
    }

    @Override
    public CartesianCoordinate asCartesian() {
        return this;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartesianCoordinate that = (CartesianCoordinate) o;

        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y, z);
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
