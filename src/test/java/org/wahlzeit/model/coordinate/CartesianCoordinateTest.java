package org.wahlzeit.model.coordinate;

import org.junit.Assert;
import org.junit.Test;

public class CartesianCoordinateTest {
    final CartesianCoordinate coordA = new CartesianCoordinate(10, 20, 30);
    final CartesianCoordinate coordB = new CartesianCoordinate(15, 80, 110);

    @Test
    public void testConstructor() {
        Assert.assertEquals(10, coordA.getX(), 0);
        Assert.assertEquals(20, coordA.getY(), 0);
        Assert.assertEquals(30, coordA.getZ(), 0);
    }

    @Test
    public void testDistanceBetweenSameCoordinatesIsZero() throws Exception {
        double distanceA = coordA.getDistance(coordA);
        double distanceB = coordB.getDistance(coordB);

        Assert.assertEquals(0, distanceA, 0);
        Assert.assertEquals(0, distanceB, 0);
    }

    @Test
    public void testDistanceCalculation() throws Exception {
        double distance = coordA.getDistance(coordB);

        Assert.assertEquals(100.12, distance, 0.1);
    }

    @Test
    public void testDistanceToSphericalCoordinate() throws Exception {
        CartesianCoordinate cartesian = new CartesianCoordinate(200, 300, 200);
        SphericalCoordinate other = new SphericalCoordinate(0, 90, 40);
        double distance = cartesian.getDistance(other);

        Assert.assertEquals(384.18, distance, 0.1);
    }

    @Test
    public void testTestCoordinatesAreNotEqual() {
        Assert.assertFalse(coordA.isEqual(coordB));
    }

    @Test
    public void testCoordinatesWithSameAttributesOrSameInstanceAreEqual() {
        CartesianCoordinate a = new CartesianCoordinate(10, 20, 30);
        CartesianCoordinate b = new CartesianCoordinate(10, 20, 30);

        Assert.assertTrue(a.isEqual(b));
        Assert.assertTrue(b.isEqual(a));

        Assert.assertTrue(a.isEqual(a));
        Assert.assertTrue(b.isEqual(b));
    }
}
