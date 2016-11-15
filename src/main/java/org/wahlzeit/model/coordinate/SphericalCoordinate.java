package org.wahlzeit.model.coordinate;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * Represents an <u>immutable</u> coordinate with its {@link #latitude} and {@link #longitude}.<br>
 * For example the coordinate (49.575103, 11.030055) represents the
 * <a href="https://osm.rrze.fau.de/map-ll-osm?mlat=49.575103&mlon=11.030055&zoom=17">Mensa in Erlangen</a>.
 */
public class SphericalCoordinate implements Coordinate {
    public static final double EARTH_RADIUS_IN_METERS = 6371000.00;

    private final double latitude;
    private final double longitude;

    /**
     * Instantiates an <u>immutable</u> coordinate instance.
     *
     * @throws IllegalArgumentException thrown if the given parameters are not in the allowed range:
     * <ul>
     *     <li>-90 <= latitude <= 90</li>
     *     <li>-180 <= longitude <= 180</li>
     * </ul>
     */
    public SphericalCoordinate(double latitude, double longitude) {
        Preconditions.checkArgument(latitude >= -90 && latitude <= 90);
        Preconditions.checkArgument(longitude >= -180 && longitude <= 180);

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
    public static double getDistance(SphericalCoordinate a, SphericalCoordinate b) {
        double latARad = Math.toRadians(a.getLatitude());
        double latBRad = Math.toRadians(b.getLatitude());

        double deltaLambdaRad = Math.toRadians(a.getLongitude() - b.getLongitude());

        double productLatitudes = Math.sin(latARad) * Math.sin(latBRad);
        double product2 = Math.cos(latARad) * Math.cos(latBRad) * Math.cos(deltaLambdaRad);

        double deltaSigma = Math.acos(productLatitudes + product2);

        return EARTH_RADIUS_IN_METERS * deltaSigma;
    }

    /**
     * Parses the given "coordinate" which must be in the format "latitude,longitude".
     * @param coordinate String to parse, e.g. "49.575103, 11.030055".
     * @return a {@link SphericalCoordinate} instance or "null" if the given String was not a valid coordinate.
     */
    public static SphericalCoordinate tryParse(String coordinate) {
        if (Strings.isNullOrEmpty(coordinate))
            return null;

        String[] split = coordinate.split(",");
        if (split.length == 2) {
            try {
                double lat = Double.parseDouble(split[0]);
                double log = Double.parseDouble(split[1]);

                return new SphericalCoordinate(lat, log);
            } catch (Exception ex) {
            }
        }

        return null;
    }

    @Override
    public double getDistance(Coordinate other) throws IllegalArgumentException {
        if(other instanceof SphericalCoordinate) {
            return getDistance(this, (SphericalCoordinate) other);
        }
        else {
            throw new IllegalArgumentException("Given coordinate of type " + other.getClass().getCanonicalName() + " not supported");
        }
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("latitude", latitude)
                .add("longitude", longitude)
                .toString();
    }
}
