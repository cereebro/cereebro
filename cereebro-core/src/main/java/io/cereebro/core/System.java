/*
 * Copyright © 2009 the original authors (http://cereebro.io)
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
