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
package io.cereebro.server.graph.d3.wheel;

import java.util.ArrayList;
import java.util.List;

import io.cereebro.core.ComponentRelationships;
import io.cereebro.core.Dependency;
import io.cereebro.core.System;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

/**
 * d3.js Dependency Wheel model.
 * <p>
 * This object is meant to be serialized in JSON.
 * </p>
 * 
 * @author michaeltecourt
 */
@Data
@Builder
@AllArgsConstructor
public final class DependencyWheel {

    /** Ordered list of component names */
    @Singular
    @NonNull
    private final List<String> names;

    /**
     * Each line contains whether (0 or 1) the component with a given index has
     * a dependency toward a component with the column index, including itself.
     * The ordering is key here, as the matrix should be ordered the same was as
     * the {@link #names}.
     */
    @Singular("matrixLine")
    @NonNull
    private final List<List<Integer>> dependencyMatrix;

    /**
     * Create the model for a d3.js dependency wheel.
     * 
     * @param system
     *            The system to draw.
     * @return DependencyWheel ready to be serialized into JSON.
     */
    public static DependencyWheel of(System system) {
        final DependencyWheelBuilder builder = DependencyWheel.builder();

        List<ComponentRelationships> rels = new ArrayList<>(system.getComponentRelationships());
        rels.sort((c1, c2) -> c1.getComponent().asString().compareToIgnoreCase(c2.getComponent().asString()));
        for (ComponentRelationships rel : rels) {
            List<Integer> line = new ArrayList<>();
            for (ComponentRelationships potentialDependency : rels) {
                line.add(rel.getDependencies().contains(Dependency.on(potentialDependency.getComponent())) ? 1 : 0);
            }
            builder.name(rel.getComponent().getName().toLowerCase()).matrixLine(line);
        }

        return builder.build();
    }

}
