package org.wahlzeit.model.coordinate;

/**
 * Represents a coordinate that supports distance calculation.
 */
public interface Coordinate {
    /**
     * Calculates the distance between this {@link Coordinate} and the given {@link Coordinate} based on their {@link
     * Coordinate#asCartesian()} representation.<br>
     *
     * @param other the other Coordinate
     * @return Distance in meters
     * @throws CoordinateConversionNotSupportedException thrown if the given coordinate is not supported
     */
    double getDistance(Coordinate other) throws CoordinateConversionNotSupportedException;

    /**
     * Converts the coordinate to its {@link CartesianCoordinate} representation.
     *
     * @throws CoordinateConversionNotSupportedException thrown if the conversion is not supported
     */
    CartesianCoordinate asCartesian() throws CoordinateConversionNotSupportedException;

    /**
     * Determines if the given {@link Coordinate} is equal to the given coordinate.
     *
     * @param other coordinate
     * @return true/false
     */
    boolean isEqual(Coordinate other);
}
