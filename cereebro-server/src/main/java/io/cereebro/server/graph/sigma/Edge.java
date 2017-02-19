package io.cereebro.server.graph.sigma;

import javax.validation.constraints.NotNull;

import io.cereebro.core.Component;
import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "create")
public class Edge {

    @NotNull
    private final String id;
    @NotNull
    private final String source;
    @NotNull
    private final String target;

    public static Edge to(Component component, Dependency dependency) {
        String from = component.getName();
        String to = dependency.getComponent().getName();
        return Edge.create(from + "-to-" + to, from, to);
    }

    public static Edge from(Component component, Consumer consumer) {
        String to = component.getName();
        String from = consumer.getComponent().getName();
        return Edge.create(from + "-to-" + to, from, to);
    }

}
