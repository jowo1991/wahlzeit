package org.wahlzeit.model.coordinate;

/**
 * Represents a coordinate that supports distance calculation.
 */
public interface Coordinate {
    /**
     * Calculates the distance between this {@link Coordinate} and the given {@link Coordinate} in meters.<br>
     * Typically only calculations between {@link Coordinate}s of the same type (e.g. two {@link SphericalCoordinate}s) are supported.
     *
     * @param other the other Coordinate
     * @return Distance in meters
     *
     * @throws IllegalArgumentException thrown if the given coordinate is not supported
     */
    double getDistance(Coordinate other) throws IllegalArgumentException;

}
