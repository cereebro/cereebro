package io.cereebro.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.ToString;

/**
 * System big picture, with all the resolved components and their relationships.
 * 
 * @author michaeltecourt
 */
@ToString
public class System {

    private final String name;

    private final Set<ComponentRelationships> componentRelationships;

    /**
     * System big picture, with all the resolved components and their
     * relationships.
     * 
     * @param name
     *            System name.
     * @param rels
     *            Components and their relationships.
     */
    public System(String name, Collection<ComponentRelationships> rels) {
        this.name = Objects.requireNonNull(name, "System name required");
        this.componentRelationships = new HashSet<>(rels);
    }

    /**
     * System big picture, with all the resolved components and their
     * relationships.
     * 
     * @param name
     *            System name.
     * @param rels
     *            Components and their relationships.
     * @return System
     */
    public static System of(String name, Collection<ComponentRelationships> rels) {
        return new System(name, rels);
    }

    /**
     * System big picture, with all the resolved components and their
     * relationships.
     * 
     * @param name
     *            System name.
     * @param rels
     *            Components and their relationships.
     * @return System
     */
    public static System of(String name, ComponentRelationships... rels) {
        return System.of(name, Arrays.asList(rels));
    }

    /**
     * An empty system, without any component.
     * 
     * @param name
     *            System name.
     * @return System
     */
    public static System empty(String name) {
        return System.of(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }
        System that = (System) o;
        return Objects.equals(this.name, that.name)
                && Objects.equals(this.componentRelationships, that.componentRelationships);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), name, componentRelationships);
    }

    /**
     * System name
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * All resolved components within this system, and their relationships with
     * one another.
     * 
     * @return a copy of the system's relationships.
     */
    public Set<ComponentRelationships> getComponentRelationships() {
        return new HashSet<>(componentRelationships);
    }

}
