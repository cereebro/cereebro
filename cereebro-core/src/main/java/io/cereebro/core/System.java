/*
 * Copyright © 2017 the original authors (http://cereebro.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    private final Set<ResolutionError> errors;

    /**
     * System big picture, with all the resolved components and their
     * relationships.
     * 
     * @param name
     *            System name.
     * @param rels
     *            Components and their relationships.
     * @param errors
     *            Errors caught while resolving the system graph.
     */
    public System(String name, Collection<ComponentRelationships> rels, Collection<ResolutionError> errors) {
        this.name = Objects.requireNonNull(name, "System name required");
        this.componentRelationships = new HashSet<>(rels);
        this.errors = new HashSet<>(errors);
    }

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
        this(name, rels, new HashSet<ResolutionError>());
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
     * @param errors
     *            Errors caught while resolving the System.
     * @return System
     */
    public static System of(String name, Collection<ComponentRelationships> rels, Collection<ResolutionError> errors) {
        return new System(name, rels, errors);
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

    /**
     * Tells whether the System resolution encountered errors.
     * 
     * @return {@code true} if the System contains resolution errors,
     *         {@code false} otherwise.
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
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
                && Objects.equals(this.componentRelationships, that.componentRelationships)
                && Objects.equals(this.errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), name, componentRelationships, errors);
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

    /**
     * Errors caught while resolving the system graph.
     * 
     * @return a copy of the resolution errors.
     */
    public Set<ResolutionError> getErrors() {
        return new HashSet<>(errors);
    }

    /**
     * New {@link SystemBuilder} instance.
     * 
     * @return SystemBuilder instance.
     */
    public static SystemBuilder builder() {
        return new SystemBuilder();
    }

    /**
     * {@link System} Builder.
     * 
     * @author michaeltecourt
     */
    public static class SystemBuilder {

        private String name;
        private Set<ComponentRelationships> componentRelationships;
        private Set<ResolutionError> errors;

        /**
         * {@link System} Builder.
         */
        public SystemBuilder() {
            componentRelationships = new HashSet<>();
            errors = new HashSet<>();
        }

        /**
         * System name.
         * 
         * @param name
         *            System name.
         * @return this SystemBuilder instance.
         */
        public SystemBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * All resolved components within this system, and their relationships
         * with one another.
         * 
         * @param componentRelationships
         *            Replace existing relationships.
         * @return this SystemBuilder instance.
         */
        public SystemBuilder componentRelationships(Set<ComponentRelationships> componentRelationships) {
            this.componentRelationships = componentRelationships;
            return this;
        }

        /**
         * System resolution errors.
         * 
         * @param errors
         *            System resolution errors.
         * @return this SystemBuilder instance.
         */
        public SystemBuilder errors(Set<ResolutionError> errors) {
            this.errors = errors;
            return this;
        }

        /**
         * Build a {@link System} object.
         * 
         * @return System.
         */
        public System build() {
            return System.of(name, componentRelationships, errors);
        }

    }

}
