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
