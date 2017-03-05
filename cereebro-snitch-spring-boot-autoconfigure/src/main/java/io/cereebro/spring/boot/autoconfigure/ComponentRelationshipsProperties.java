package io.cereebro.spring.boot.autoconfigure;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import lombok.Data;

@Data
public final class ComponentRelationshipsProperties {

    private ComponentProperties component = new ComponentProperties();
    private List<DependencyProperties> dependencies = new ArrayList<>();
    private List<ConsumerProperties> consumers = new ArrayList<>();

    public Set<Dependency> dependencies() {
        return dependencies.stream().map(DependencyProperties::toDependency).collect(Collectors.toSet());
    }

    public Set<Consumer> consumers() {
        return consumers.stream().map(ConsumerProperties::toConsumer).collect(Collectors.toSet());
    }

    public Set<Relationship> getRelationships() {
        return Stream.concat(dependencies().stream(), consumers().stream()).collect(Collectors.toSet());
    }

}
