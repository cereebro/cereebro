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
public class Graph {

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
            nodes.add(Node.of(rel.getComponent(), rel.getRelationships().size() + 30));
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
