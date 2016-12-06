package org.wahlzeit.model.coordinate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SphericalCoordinateTest {
    final double TOLERANCE = 0.1;
    final double invalidLatitude = 94;
    final double invalidLongitude = 200;
    final double invalidRadius = -10;
    /**
     * 0.031-113 - Seminarraum
     */
    SphericalCoordinate room;
    /**
     * Mensa-Süd
     */
    SphericalCoordinate mensa;

    @Before
    public void setup() {
        room = new SphericalCoordinate(49.573817, 11.027639);
        mensa = new SphericalCoordinate(49.575103, 11.030055);
    }

    @Test
    public void testDistanceBetweenSameCoordinatesIsZero() throws Exception {
        Assert.assertEquals(0, room.getDistance(room), TOLERANCE);
    }

    @Test
    public void testDistanceBetweenRoomAndMensa() throws Exception {
        Assert.assertEquals(225.3, room.getDistance(mensa), TOLERANCE);
    }

    @Test
    public void testDistanceBetweenMensaAndRoom() throws Exception {
        Assert.assertEquals(225.3, mensa.getDistance(room), TOLERANCE);
    }

    @Test(expected = AssertionError.class)
    public void testConstructorInvalidLatitudeThrowsException() {
        new SphericalCoordinate(invalidLatitude, 20);
    }

    @Test(expected = AssertionError.class)
    public void testConstructorInvalidLongitudeThrowsException() {
        new SphericalCoordinate(45, invalidLongitude);
    }

    @Test(expected = AssertionError.class)
    public void testConstructorInvalidRadiusThrowsException() {
        new SphericalCoordinate(44, 43, invalidRadius);
    }

    @Test(expected = AssertionError.class)
    public void testSetLatitudeWithInvalidArgumentThrowsException() {
        room.setLatitude(invalidLatitude);
    }

    @Test(expected = AssertionError.class)
    public void testSetLongitudeWithInvalidArgumentThrowsException() {
        room.setLongitude(invalidLongitude);
    }

    @Test(expected = AssertionError.class)
    public void testSetRadiusWithInvalidArgumentThrowsException() {
        room.setRadius(invalidRadius);
    }

    @Test
    public void testSettersWithValidInputChangesCoordinate() {
        room.setLatitude(20);
        room.setLongitude(30);
        room.setRadius(300000);

        Assert.assertEquals(20, room.getLatitude(), TOLERANCE);
        Assert.assertEquals(30, room.getLongitude(), TOLERANCE);
        Assert.assertEquals(300000, room.getRadius(), TOLERANCE);
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
    public void testDistanceToCartesianCoordinate() throws Exception {
        CartesianCoordinate coordinate = new CartesianCoordinate(500, 200, 678);
        double distance = room.getDistance(coordinate);

        Assert.assertEquals(6370140.8, distance, TOLERANCE);
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
