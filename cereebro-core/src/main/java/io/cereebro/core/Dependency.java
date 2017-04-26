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
 * Dependency to another Component.
 * <p>
 * Nothing more than a typed Relationship at this point, but leaves room to add
 * specific attributes.
 * </p>
 * 
 * @author michaeltecourt
 */
@ToString(callSuper = true)
public class Dependency extends Relationship {

    @JsonCreator
    public Dependency(@JsonProperty("component") Component component) {
        super(component);
    }

    public static Dependency on(Component component) {
        return new Dependency(component);
    }

    /**
     * Relationship set containing only this dependency.
     * 
     * @return Set containing this dependency.
     */
    public Set<Relationship> asRelationshipSet() {
        return new HashSet<>(Arrays.asList(this));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), getComponent());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }
        Dependency that = (Dependency) o;
        return Objects.equals(this.getComponent(), that.getComponent());
    }
}
