package org.wahlzeit.services;

import org.wahlzeit.model.LandscapeType;
import org.wahlzeit.utils.PatternInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manager class to ensure each {@link LandscapeType} is created only once.
 */
@PatternInstance(name = "Singleton")
public class LandscapeTypeManager {
    private static LandscapeTypeManager instance;
    private final Map<String, LandscapeType> typeMap = new HashMap<>();

    public synchronized static LandscapeTypeManager getInstance() {
        if (instance == null) {
            instance = new LandscapeTypeManager();
        }
        return instance;
    }

    public synchronized LandscapeType getType(String canonicalId) {
        return getType(canonicalId, canonicalId, "");
    }

    public synchronized LandscapeType getType(String canonicalId, String name, String description) {
        if (canonicalId == null) {
            canonicalId = "";
        }

        if (name == null) {
            name = "";
        }

        if (!typeMap.containsKey(canonicalId)) {
            typeMap.put(canonicalId, new LandscapeType(canonicalId, name, description));
        }

        return typeMap.get(canonicalId);
    }

    public List<LandscapeType> getRootTypes() {
        List<LandscapeType> rootTypes = new ArrayList<>();
        for (LandscapeType type : typeMap.values()) {
            if (type.getSuperType() == null) {
                rootTypes.add(type);
            }
        }

        return rootTypes;
    }

    public void loadDefaults() {
        typeMap.clear();

        getType("", "None", "Fallback category");
        getType("Family", "Family", "Family related photos");
        LandscapeType vacationType = getType("Vacation", "Vacations", "Parent category for all vacations");

        LandscapeType mallorca2016 = getType("Mallorca2016", "Mallorca 2016", "Vacation in Mallorca 2016. Good times!").
                setSuperType(vacationType);
        LandscapeType italy2010 = getType("Italy2010", "Italy 2010", "Vacation in Italy.").
                setSuperType(vacationType);

        getType("SunsetsMallorca2016", "Sunset", "Sunsets in the Mallorca vacation.").
                setSuperType(mallorca2016);
    }

    /**
     * Only relevant for jUnit tests.
     */
    void clear() {
        typeMap.clear();
    }
}
