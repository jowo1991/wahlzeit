package org.wahlzeit.model.coordinate;

public abstract class AbstractCoordinate implements Coordinate {
    /**
     * Calculates the distance between the given coordinates in meters.
     * For details see <a href="https://en.wikipedia.org/wiki/Cartesian_coordinate_system#Distance_between_two_points">wikipedia</a>.
     * @param a Coordinate A
     * @param b Coordinate B
     * @return Distance in meters
     */
    private double getCartesianDistance(CartesianCoordinate a, CartesianCoordinate b) {
        return Math.sqrt( Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2) + Math.pow(b.getZ() - a.getZ(), 2));
    }

    @Override
    public double getDistance(Coordinate other) throws IllegalArgumentException {
        CartesianCoordinate otherAsCartesian = other.asCartesian();
        CartesianCoordinate thisAsCartesian = this.asCartesian();

        if(thisAsCartesian != null && otherAsCartesian != null) {
            return getCartesianDistance(thisAsCartesian, otherAsCartesian);
        }
        else {
            throw new IllegalArgumentException("Given coordinate of type " + other.getClass().getCanonicalName() + " not supported");
        }
    }

    @Override
    public boolean isEqual(Coordinate other) {
        CartesianCoordinate otherAsCartesian = other.asCartesian();
        CartesianCoordinate thisAsCartesian = this.asCartesian();

        if(thisAsCartesian != null && otherAsCartesian != null) {
            return thisAsCartesian.equals(otherAsCartesian);
        }
        else {
            return false;
        }
    }
}
