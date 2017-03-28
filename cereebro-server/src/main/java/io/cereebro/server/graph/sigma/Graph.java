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
package io.cereebro.server.graph.sigma;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import io.cereebro.core.ComponentRelationships;
import io.cereebro.core.System;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * Sigma.js graph object.
 * 
 * @author michaeltecourt
 */
@Data
@AllArgsConstructor(staticName = "create")
public final class Graph {

    @NonNull
    private final Set<Node> nodes;
    @NonNull
    private final Set<Edge> edges;

    /**
     * Create the Graph of a System.
     * 
     * @param system
     *            System to picture.
     * @return Graph
     */
    public static Graph of(System system) {
        Set<Node> nodes = new HashSet<>();
        Set<Edge> edges = new HashSet<>();
        for (ComponentRelationships rel : system.getComponentRelationships()) {
            nodes.add(Node.of(rel.getComponent(), rel.getRelationships().size() + 3));
            // @formatter:off
	    // Transform dependency relationships to graph edges
	    Set<Edge> dependencies = rel.getDependencies().stream().map(d -> Edge.to(rel.getComponent(), d))
		    .collect(Collectors.toSet());
	    edges.addAll(dependencies);
	    // Transform consumer relationships to graph edges
	    Set<Edge> consumers = rel.getConsumers().stream().map(c -> Edge.from(rel.getComponent(), c))
		    .collect(Collectors.toSet());
	    edges.addAll(consumers);
	    // @formatter:on
        }
        return Graph.create(nodes, edges);
    }

}
