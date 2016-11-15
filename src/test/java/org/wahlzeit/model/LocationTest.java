package org.wahlzeit.model;

import org.junit.Test;
import org.wahlzeit.model.coordinate.Coordinate;
import org.wahlzeit.model.coordinate.SphericalCoordinate;

public class LocationTest {
    @Test(expected = NullPointerException.class)
    public void testNullThrowsException() {
        new Location(null);
    }


    @Test
    public void testValidCreationWorks() {
        Location location = new Location(new SphericalCoordinate(10, 20));
    }
}
