package org.wahlzeit.model;

public class Location {
    public static final int EARTH_RADIUS = 6371;

    private Coordinate coordinate;

    public Location(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Location() {
    }

    public static double getDistance(Coordinate a, Coordinate b) {
        double productLatitudes = Math.acos(Math.sin(a.getLatitude()) * Math.sin(b.getLatitude()));
        double deltaLambda = a.getLongitude() - b.getLongitude();
        double product2 = Math.cos(a.getLatitude()) * Math.cos(b.getLatitude()) * deltaLambda;

        double deltaSigma = Math.acos(productLatitudes + product2);

        return EARTH_RADIUS * deltaSigma;
    }

    public double getDistance(Coordinate other) {
        return getDistance(this.getCoordinate(), other);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
