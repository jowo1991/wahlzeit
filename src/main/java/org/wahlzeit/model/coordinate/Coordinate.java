package org.wahlzeit.model.coordinate;

/**
 * Represents a coordinate that supports distance calculation.
 */
public interface Coordinate {
    /**
     * Calculates the distance between this {@link Coordinate} and the given {@link Coordinate} in meters.<br>
     *
     * @param other the other Coordinate
     * @return Distance in meters
     *
     * @throws IllegalArgumentException thrown if the given coordinate is not supported
     */
    double getDistance(Coordinate other) throws IllegalArgumentException;

    /**
     * Converts the coordinate to its {@link CartesianCoordinate} representation if one exists.
     * @return {@link CartesianCoordinate} representation or {@code null} if the conversion is not possible
     */
    CartesianCoordinate asCartesian();

    /**
     * Determines if the given {@link Coordinate} is equal to the coordinate.
     * @param other coordinate
     * @return true/false
     */
    boolean isEqual(Coordinate other);
}
