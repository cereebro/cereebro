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

import lombok.ToString;

/**
 * Base relationship class.
 * 
 * <p>
 * Only a {@link Component} holder at this time, but leaves room to add details
 * about the relationship itself : qualifier, cardinality...
 * </p>
 * 
 * @author michaeltecourt
 */
@ToString
public abstract class Relationship {

    private final Component component;

    protected Relationship(Component component) {
        this.component = Objects.requireNonNull(component, "Component required");
    }

    /**
     * Related component.
     * 
     * @return Component
     */
    public Component getComponent() {
        return component;
    }

}
