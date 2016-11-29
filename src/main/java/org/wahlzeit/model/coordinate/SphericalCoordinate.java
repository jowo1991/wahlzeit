package org.wahlzeit.model.coordinate;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Represents an <u>immutable</u> coordinate with its {@link #latitude} and {@link #longitude}.<br>
 * For example the coordinate (49.575103, 11.030055) represents the
 * <a href="https://osm.rrze.fau.de/map-ll-osm?mlat=49.575103&mlon=11.030055&zoom=17">Mensa in Erlangen</a>.
 */
public class SphericalCoordinate extends AbstractCoordinate {
    public static final double EARTH_RADIUS_IN_METERS = 6371000.00;

    private final double latitude;
    private final double longitude;
    private final double radius;

    /**
     * Instantiates an <u>immutable</u> coordinate instance using the {@link #EARTH_RADIUS_IN_METERS}.
     *
     * @throws IllegalArgumentException thrown if the given parameters are not in the allowed range: <ul> <li>-90 <=
     *                                  latitude <= 90</li> <li>-180 <= longitude <= 180</li> </ul>
     */
    public SphericalCoordinate(double latitude, double longitude) {
        this(latitude, longitude, EARTH_RADIUS_IN_METERS);
    }

    /**
     * Instantiates an <u>immutable</u> coordinate instance.
     *
     * @throws IllegalArgumentException thrown if the given parameters are not in the allowed range: <ul> <li>-90 <=
     *                                  latitude <= 90</li> <li>-180 <= longitude <= 180</li> </ul>
     */
    public SphericalCoordinate(double latitude, double longitude, double radius) {
        Preconditions.checkArgument(latitude >= -90 && latitude <= 90);
        Preconditions.checkArgument(longitude >= -180 && longitude <= 180);

        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    /**
     * Parses the given "coordinate" which must be in the format "latitude,longitude".
     *
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

    /**
     * Converts the {@link SphericalCoordinate} to a {@link CartesianCoordinate}.
     *
     * @return Converted coordinate
     */
    @Override
    public CartesianCoordinate asCartesian() {
        double latRadian = Math.toRadians(latitude);
        double logRadian = Math.toRadians(longitude);

        double X = radius * Math.cos(latRadian) * Math.cos(logRadian);
        double Y = radius * Math.cos(latRadian) * Math.sin(logRadian);
        double Z = radius * Math.sin(latRadian);

        return new CartesianCoordinate(X, Y, Z);
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
