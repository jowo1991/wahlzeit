package org.wahlzeit.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.services.LandscapeTypeManager;


public class LandscapeTypeTest {
    private LandscapeType vacationMallorcaType;
    private LandscapeType vacationType;
    private LandscapeType sunsetType;

    @Before
    public void setup() {
        vacationType = new LandscapeType("Vacation", "Vacations", "Parent category for all vacations");

        vacationMallorcaType = new LandscapeType("Mallorca2016", "Mallorca 2016", "Vacation in Mallorca 2016. Good times!").
                setSuperType(vacationType);

        sunsetType = new LandscapeType("SunsetsMallorca2016", "Sunset", "Sunsets in the Mallorca vacation.").
                setSuperType(vacationMallorcaType);
    }

    @Test
    public void isSubtypeOf_on_parentType_returns_true() {
        Assert.assertTrue(vacationMallorcaType.isSubtypeOf(vacationType));
    }

    @Test
    public void isSubtypeOf_on_parentTypes_parent_returns_true() {
        Assert.assertTrue(sunsetType.isSubtypeOf(vacationType));
        Assert.assertTrue(sunsetType.isSubtypeOf(vacationMallorcaType));
    }

    @Test
    public void isSubtypeOf_for_unrelated_type_returns_false() {
        LandscapeType otherType = new LandscapeType("Other", "Other", "");

        Assert.assertFalse(otherType.isSubtypeOf(vacationType));
        Assert.assertFalse(otherType.isSubtypeOf(vacationMallorcaType));
    }

    @Test
    public void isSubtypeOf_for_superType_returns_false() {
        Assert.assertFalse(vacationMallorcaType.isSubtypeOf(sunsetType));
    }
}
