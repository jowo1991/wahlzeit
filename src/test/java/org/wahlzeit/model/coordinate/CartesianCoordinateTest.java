package org.wahlzeit.model.coordinate;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class CartesianCoordinateTest {
    final CartesianCoordinate coordA = new CartesianCoordinate(10, 20, 30);
    final CartesianCoordinate coordB = new CartesianCoordinate(20, 30, 110);

    @Test
    public void testConstructor() {
        Assert.assertEquals(10, coordA.getX(), 0);
        Assert.assertEquals(20, coordA.getY(), 0);
        Assert.assertEquals(30, coordA.getZ(), 0);
    }

    @Test
    public void testDistanceBetweenSameCoordinatesIsZero() {
        double distanceA = coordA.getDistance(coordA);
        double distanceB = coordB.getDistance(coordB);

        Assert.assertEquals(0, distanceA, 0);
        Assert.assertEquals(0, distanceB, 0);
    }

    @Test
    public void testDistanceCalculation() {
        double distance = coordA.getDistance(coordB);

        Assert.assertEquals(10, distance, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDistanceToOtherCoordinateThrowsException() {
        Coordinate other = Mockito.mock(Coordinate.class);
        coordA.getDistance(other);
    }
}
