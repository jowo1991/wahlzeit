package org.wahlzeit.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.model.LandscapeType;

import java.util.List;


public class LandscapeTypeManagerTest {
    LandscapeTypeManager manager = LandscapeTypeManager.getInstance();

    @Before
    public void setup() {
        manager.clear();
    }

    @Test
    public void getType_for_same_type_twice_returns_same_object() {
        LandscapeType typeA = manager.getType("EmpireStateBuilding");
        LandscapeType typeB = manager.getType("EmpireStateBuilding");

        Assert.assertTrue(typeA == typeB);
    }
}
