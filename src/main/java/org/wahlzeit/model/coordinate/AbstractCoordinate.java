package org.wahlzeit.model.coordinate;

import com.google.common.base.Preconditions;

public abstract class AbstractCoordinate implements Coordinate {
    /**
     * Calculates the distance between the given coordinates in meters. For details see <a
     * href="https://en.wikipedia.org/wiki/Cartesian_coordinate_system#Distance_between_two_points">wikipedia</a>.
     *
     * @param a Coordinate A
     * @param b Coordinate B
     * @return Distance in meters
     */
    private double getCartesianDistance(CartesianCoordinate a, CartesianCoordinate b) {
        double deltaX = b.getX() - a.getX();
        double deltaY = b.getY() - a.getY();
        double deltaZ = b.getZ() - a.getZ();
        
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }

    @Override
    public double getDistance(Coordinate other) throws CoordinateConversionNotSupportedException {
        Preconditions.checkNotNull(other, "other Coordinate may not be null");

        CartesianCoordinate otherAsCartesian = other.asCartesian();
        CartesianCoordinate thisAsCartesian = this.asCartesian();

        return getCartesianDistance(thisAsCartesian, otherAsCartesian);
    }

    @Override
    public boolean isEqual(Coordinate other) {
        try {
            CartesianCoordinate otherAsCartesian = other.asCartesian();
            CartesianCoordinate thisAsCartesian = this.asCartesian();

            return thisAsCartesian.equals(otherAsCartesian);
        } catch (CoordinateConversionNotSupportedException e) {
            return false;
        }
    }
}
