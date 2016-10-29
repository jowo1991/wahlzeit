package org.wahlzeit.model;

import com.google.common.base.Preconditions;

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
}
