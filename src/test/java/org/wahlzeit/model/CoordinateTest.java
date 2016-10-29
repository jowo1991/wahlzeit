package org.wahlzeit.model;

import org.junit.Assert;
import org.junit.Test;

public class CoordinateTest {
    /**
     * 0.031-113 - Seminarraum
     */
    final Coordinate room = new Coordinate(49.573817, 11.027639);
    /**
     * Mensa-Süd
     */
    final Coordinate mensa = new Coordinate(49.575103, 11.030055);

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
}
