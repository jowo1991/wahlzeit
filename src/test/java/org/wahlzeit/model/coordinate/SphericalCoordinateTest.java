package org.wahlzeit.model.coordinate;

import org.junit.Assert;
import org.junit.Test;

public class SphericalCoordinateTest {
    /**
     * 0.031-113 - Seminarraum
     */
    final SphericalCoordinate room = new SphericalCoordinate(49.573817, 11.027639);
    /**
     * Mensa-Süd
     */
    final SphericalCoordinate mensa = new SphericalCoordinate(49.575103, 11.030055);

    @Test
    public void testDistanceBetweenSameCoordinatesIsZero() {
        Assert.assertEquals(0, room.getDistance(room), 0.1);
    }

    @Test
    public void testDistanceBetweenRoomAndMensa() {
        Assert.assertEquals(225.3, room.getDistance(mensa), 0.1);
    }

    @Test
    public void testDistanceBetweenMensaAndRoom() {
        Assert.assertEquals(225.3, mensa.getDistance(room), 0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidLatitudeThrowsException() {
        new SphericalCoordinate(94, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidLongitudeThrowsException() {
        new SphericalCoordinate(45, 190);
    }

    @Test
    public void testConstructorValidArgumentsNoException() {
        new SphericalCoordinate(45, 45);
    }

    @Test
    public void testTryParseValidArgumentReturnsCoordinate() {
        SphericalCoordinate coordinate = SphericalCoordinate.tryParse("44, 55");

        Assert.assertEquals(44, coordinate.getLatitude(), 0);
        Assert.assertEquals(55, coordinate.getLongitude(), 0);
    }

    @Test
    public void testTryParseInvalidArgumentReturnsNull() {
        SphericalCoordinate coordinate = SphericalCoordinate.tryParse("44");
        Assert.assertNull(coordinate);
    }

    @Test
    public void testTryParseNullArgumentReturnsNull() {
        SphericalCoordinate coordinate = SphericalCoordinate.tryParse(null);
        Assert.assertNull(coordinate);
    }

    @Test
    public void testDistanceToCartesianCoordinate() {
        CartesianCoordinate coordinate = new CartesianCoordinate(500, 200, 678);
        double distance = room.getDistance(coordinate);

        Assert.assertEquals(6370140.8, distance, 0.1);
    }

    @Test
    public void testRoomAndMensaCoordinatesAreNotEqual() {
        Assert.assertFalse(room.isEqual(mensa));
        Assert.assertFalse(mensa.isEqual(room));
    }

    @Test
    public void testCoordinatesWithSameAttributesOrSameInstanceAreEqual() {
        SphericalCoordinate a = new SphericalCoordinate(33, 44);
        SphericalCoordinate b = new SphericalCoordinate(33, 44);

        Assert.assertTrue(a.isEqual(b));
        Assert.assertTrue(b.isEqual(a));

        Assert.assertTrue(a.isEqual(a));
        Assert.assertTrue(b.isEqual(b));
    }

    @Test
    public void testSphericalAsCartesianEqualsCartesianCoordinate() {
        CartesianCoordinate cartesian = mensa.asCartesian();

        Assert.assertTrue(cartesian.isEqual(mensa));
    }
}
