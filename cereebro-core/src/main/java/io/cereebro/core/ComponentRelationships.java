package io.cereebro.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

/**
 * Pictures a Component and how it relates to other. Within a System, there may
 * be different partial "views" of a given component depending on the
 * information available.
 * 
 * @author michaeltecourt
 */
@ToString
public class ComponentRelationships {

    private final Component component;

    private final Set<Dependency> dependencies;

    private final Set<Consumer> consumers;

    /**
     * Pictures a Component and how it relates to other
     * 
     * @param component
     * @param dependencies
     * @param consumers
     */
    @JsonCreator
    public ComponentRelationships(@JsonProperty("component") Component component,
            @JsonProperty("dependencies") Set<Dependency> dependencies,
            @JsonProperty("consumers") Set<Consumer> consumers) {
        this.component = Objects.requireNonNull(component, "Component required");
        this.dependencies = new HashSet<>(dependencies);
        this.consumers = new HashSet<>(consumers);
    }

    public static ComponentRelationships of(Component c, Set<Dependency> dependencies, Set<Consumer> consumers) {
        return new ComponentRelationships(c, dependencies, consumers);
    }

    /**
     * Pictures a Component and how it relates to other
     * 
     * @param component
     * @param relationships
     *            Relationship Collection that will be split between Dependency
     *            and Consumer.
     */
    public static ComponentRelationships of(Component c, Collection<Relationship> relationships) {
        return new ComponentRelationships(c, filterDependencies(relationships), filterConsumers(relationships));
    }

    /**
     * Extract {@link Consumer} objects out of a {@link Relationship}
     * collection.
     * 
     * @param relationships
     * @return Set of casted Consumer objects.
     */
    public static Set<Consumer> filterConsumers(Collection<Relationship> relationships) {
        Objects.requireNonNull(relationships, "relationship collection required");
        // @formatter:off
        return relationships.stream()
                .filter(rel -> rel instanceof Consumer)
                .map(rel -> (Consumer) rel)
                .collect(Collectors.toSet());
        // @formatter:on
    }

    /**
     * Extract {@link Dependency} objects out of a {@link Relationship}
     * collection.
     * 
     * @param relationships
     * @return Set of Dependency objects.
     */
    public static Set<Dependency> filterDependencies(Collection<Relationship> relationships) {
        Objects.requireNonNull(relationships, "relationship collection required");
        // @formatter:off
        return relationships.stream()
                .filter(rel -> rel instanceof Dependency)
                .map(rel -> (Dependency) rel)
                .collect(Collectors.toSet());
        // @formatter:on
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), component, dependencies, consumers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }
        ComponentRelationships that = (ComponentRelationships) o;
        return Objects.equals(this.component, that.component) && Objects.equals(this.dependencies, that.dependencies)
                && Objects.equals(this.consumers, that.consumers);
    }

    /**
     * @return Component
     */
    public Component getComponent() {
        return component;
    }

    /**
     * The components dependend upon by the component attribute.
     * 
     * @return Dependency Set
     */
    public Set<Dependency> getDependencies() {
        return new HashSet<>(dependencies);
    }

    /**
     * The component consuming the component.
     * 
     * @return Consumer Set
     */
    public Set<Consumer> getConsumers() {
        return new HashSet<>(consumers);
    }

    /**
     * All relationships at once.
     * 
     * @return Both {@link Consumer} and {@link Dependency} relationships.
     */
    public Set<Relationship> getRelationships() {
        return Stream.concat(dependencies.stream(), consumers.stream()).collect(Collectors.toSet());
    }

    /**
     * Create a {@link ComponentRelationships} builder.
     * 
     * @return new ComponentRelationshipsBuilder instance.
     */
    public static ComponentRelationshipsBuilder builder() {
        return new ComponentRelationshipsBuilder();
    }

    /**
     * {@link ComponentRelationships} Builder.
     * 
     * @author michaeltecourt
     */
    @ToString
    public static class ComponentRelationshipsBuilder {

        private Component component;
        private Set<Dependency> dependencies = new HashSet<>();
        private Set<Consumer> consumers = new HashSet<>();

        /**
         * Set the target component.
         * 
         * @param component
         * @return this ComponentRelationshipsBuilder instance.
         */
        public ComponentRelationshipsBuilder component(Component component) {
            this.component = Objects.requireNonNull(component, "component required");
            return this;
        }

        /**
         * Overwrite the Dependency Set.
         * 
         * @param dependencies
         * @return this ComponentRelationshipsBuilder instance.
         */
        public ComponentRelationshipsBuilder dependencies(Collection<Dependency> dependencies) {
            Objects.requireNonNull(dependencies, "dependencies required");
            this.dependencies = new HashSet<>(dependencies);
            return this;
        }

        /**
         * Overwrite the Consumer Set.
         * 
         * @param consumers
         * @return this ComponentRelationshipsBuilder instance.
         */
        public ComponentRelationshipsBuilder consumers(Collection<Consumer> consumers) {
            Objects.requireNonNull(consumers, "consumers required");
            this.consumers = new HashSet<>(consumers);
            return this;
        }

        /**
         * Add a Dependency to the existing set.
         * 
         * @param d
         * @return this ComponentRelationshipsBuilder instance.
         */
        public ComponentRelationshipsBuilder addDependency(Dependency d) {
            this.dependencies.add(d);
            return this;
        }

        /**
         * Add a Consumer to the existing Set.
         * 
         * @param c
         * @return this ComponentRelationshipsBuilder instance.
         */
        public ComponentRelationshipsBuilder addConsumer(Consumer c) {
            this.consumers.add(c);
            return this;
        }

        /**
         * Add all dependencies to the existing Set.
         * 
         * @param d
         * @return this ComponentRelationshipsBuilder instance.
         */
        public ComponentRelationshipsBuilder addDependencies(Collection<Dependency> d) {
            this.dependencies.addAll(d);
            return this;
        }

        /**
         * Add all consumers to the existing Set.
         * 
         * @param c
         * @return this ComponentRelationshipsBuilder instance.
         */
        public ComponentRelationshipsBuilder addConsumers(Collection<Consumer> c) {
            this.consumers.addAll(c);
            return this;
        }

        /**
         * Build an immutable {@link ComponentRelationships} object.
         * 
         * @return ComponentRelationships
         */
        public ComponentRelationships build() {
            return new ComponentRelationships(component, dependencies, consumers);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || !getClass().equals(o.getClass())) {
                return false;
            }
            ComponentRelationshipsBuilder that = (ComponentRelationshipsBuilder) o;
            return Objects.equals(this.component, that.component)
                    && Objects.equals(this.dependencies, that.dependencies)
                    && Objects.equals(this.consumers, that.consumers);
        }

        @Override
        public int hashCode() {
            return Objects.hash(getClass(), component, dependencies, consumers);
        }

    }

}
