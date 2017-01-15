package org.wahlzeit.model;

import org.wahlzeit.utils.PatternInstance;

import java.util.HashSet;
import java.util.Set;

/**
 * A type object that can be used to structure the {@link LandscapePhoto}s hierarchically.
 * <pre>
 *              Undefined              Landmark
 *                                         |
 *                                         |
 *                                        / \
 *                                      /    \
 *                                    /       \
 *                           EmpireState      StatueOfLiberty(name="Statue of Liberty", description="A well known landmark that[...]")
 * </pre>
 */
@PatternInstance(name = "Type", participants = {"LandscapeType", "LandscapePhoto"})
public class LandscapeType {
    private LandscapeType superType;
    private final Set<LandscapeType> subTypes = new HashSet<>();

    private final String canonicalId;
    private final String description;
    private final String name;

    public LandscapeType(String canonicalId, String name, String description) {
        this.canonicalId = canonicalId;
        this.name = name;
        this.description = description;
    }

    /**
     * The canonicalId that uniquely identifies each instance, e.g. "StatueOfLiberty"
     *
     * @return canonicalId
     */
    public String getCanonicalId() {
        return canonicalId;
    }

    /**
     * A short description of the LandscapeType.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * The display name of Type, e.g. "Statue of Liberty" that should be displayed in the UI.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    public Iterable<LandscapeType> getSubTypes() {
        return subTypes;
    }

    public LandscapeType getSuperType() {
        return superType;
    }

    public LandscapeType addSubType(LandscapeType type) {
        subTypes.add(type);
        return this;
    }

    public LandscapeType setSuperType(LandscapeType superType) {
        this.superType = superType;
        this.superType.addSubType(this);
        return this;
    }

    /**
     * States whether {@code this} instance is part of the subType hierarchy of the given {@code type}.
     * @param type parent type
     * @return true/false
     */
    public boolean isSubtypeOf(LandscapeType type) {
        for(LandscapeType subType : type.subTypes) {
            if(subType == this || this.isSubtypeOf(subType)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", canonicalId, name, description);
    }
}
