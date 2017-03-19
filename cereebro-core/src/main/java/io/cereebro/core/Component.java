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

import java.util.Objects;
import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

/**
 * Cereebro base unit of work.
 * 
 * @author michaeltecourt
 */
@ToString
public class Component {

    private final String name;

    private final String type;

    /**
     * Cereebro base unit of work.
     * 
     * @param name
     *            Name of the component
     * @param type
     *            Type of the component
     */
    @JsonCreator
    public Component(@JsonProperty("name") String name, @JsonProperty("type") String type) {
        this.name = Objects.requireNonNull(name, "Component name required");
        this.type = Objects.requireNonNull(type, "Component type required");
    }

    /**
     * Cereebro base unit of work.
     * 
     * @param name
     *            Name of the component
     * @param type
     *            Type of the component
     * @return Component
     */
    public static Component of(String name, String type) {
        return new Component(name, type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), name, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }
        Component that = (Component) o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.type, that.type);
    }

    public String asString() {
        return new StringJoiner(":").add(type).add(name).toString();
    }

    /**
     * Component name.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Component type.
     * 
     * @return type
     */
    public String getType() {
        return type;
    }

}
