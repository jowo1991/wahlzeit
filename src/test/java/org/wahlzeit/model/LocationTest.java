package org.wahlzeit.model;

import org.junit.Test;

public class LocationTest {
    @Test(expected = NullPointerException.class)
    public void testNullThrowsException() {
        new Location(null);
    }


    @Test
    public void testValidCreationWorks() {
        Location location = new Location(new Coordinate(10, 20));
    }
}
