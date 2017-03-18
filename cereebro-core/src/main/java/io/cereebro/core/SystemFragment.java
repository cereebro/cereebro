package io.cereebro.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

/**
 * Subset of a given System.
 * 
 * @author michaeltecourt
 */
@ToString
public class SystemFragment {

    private final Set<ComponentRelationships> componentRelationships;

    /**
     * Subset of a given System.
     * 
     * @param rels
     *            known relationships.
     */
    @JsonCreator
    public SystemFragment(@JsonProperty("componentRelationships") Set<ComponentRelationships> rels) {
        this.componentRelationships = new HashSet<>(rels);
    }

    /**
     * Subset of a given System.
     * 
     * @param rels
     *            known relationships.
     * @return SystemFragment
     */
    public static SystemFragment of(Set<ComponentRelationships> rels) {
        return new SystemFragment(rels);
    }

    /**
     * Subset of a given System.
     * 
     * @param rels
     *            known relationships.
     * @return SystemFragment
     */
    public static SystemFragment of(ComponentRelationships... rels) {
        return SystemFragment.of(new HashSet<>(Arrays.asList(rels)));
    }

    /**
     * Convenience factory method to create a SystemFragment without any
     * component.
     * 
     * @return empty SystemFragment
     */
    public static SystemFragment empty() {
        return new SystemFragment(new HashSet<>());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }
        SystemFragment that = (SystemFragment) o;
        return Objects.equals(this.componentRelationships, that.componentRelationships);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), componentRelationships);
    }

    /**
     * All the components detected, and their relationships.
     * 
     * @return a copy of the ComponentRelationships held by this object.
     */
    public Set<ComponentRelationships> getComponentRelationships() {
        return new HashSet<>(componentRelationships);
    }

}
