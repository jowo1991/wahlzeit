package org.wahlzeit.model;

/**
 * Represents a <u>immutable</u> coordinate with its {@link #latitude} and {@link #longitude}.<br>
 * For example the coordinate (49.575103, 11.030055) represents the
 * <a href="https://osm.rrze.fau.de/map-ll-osm?mlat=49.575103&mlon=11.030055&zoom=17">Mensa in Erlangen</a>.
 */
public class Coordinate {
    public static final double EARTH_RADIUS_IN_METERS = 6371000.00;

    private final double latitude;
    private final double longitude;

    /**
     * Instantiates an <u>immutable</u> coordinate instance.
     */
    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Calculate the distance between two coordinates using the <a href="https://en.wikipedia.org/wiki/Great-circle_distance">Great
     * Circle Distance</a>.
     *
     * @param a Coordinate A
     * @param b Coordinate B
     * @return Distance in meters
     */
    public static double getDistance(Coordinate a, Coordinate b) {
        double latARad = Math.toRadians(a.getLatitude());
        double latBRad = Math.toRadians(b.getLatitude());

        double deltaLambdaRad = Math.toRadians(a.getLongitude() - b.getLongitude());

        double productLatitudes = Math.sin(latARad) * Math.sin(latBRad);
        double product2 = Math.cos(latARad) * Math.cos(latBRad) * Math.cos(deltaLambdaRad);

        double deltaSigma = Math.acos(productLatitudes + product2);

        return EARTH_RADIUS_IN_METERS * deltaSigma;
    }

    /**
     * Calculates the distance between this {@link Coordinate} and the given {@link Coordinate}.
     * For details see: {@link #getDistance(Coordinate, Coordinate)}
     *
     * @param other the other Coordinate
     * @return Distance in meters
     */
    public double getDistance(Coordinate other) {
        return getDistance(this, other);
    }

    /**
     * Gets the Latitude.
     * (abbreviation: Lat., φ, or phi)
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets the Longitude.
     * (abbreviation: Long., λ, or lambda)
     */
    public double getLongitude() {
        return longitude;
    }
}
