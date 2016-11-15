package org.wahlzeit.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import org.wahlzeit.model.coordinate.Coordinate;
import org.wahlzeit.model.coordinate.SphericalCoordinate;

/**
 * Represents an <u>immutable</u> location with a {@link #coordinate}.
 */
public class Location {
    public final Coordinate coordinate;

    /**
     * Instantiates an <u>immutable</u> location instance.
     */
    public Location(Coordinate coordinate) {
        Preconditions.checkNotNull(coordinate);
        this.coordinate = coordinate;
    }

    /**
     * Required by GAE
     */
    private Location() {
        coordinate = null;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("coordinate", coordinate)
                .toString();
    }
}
