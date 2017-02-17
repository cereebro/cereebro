package io.cereebro.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.cereebro.core.ComponentRelationships.ComponentRelationshipsBuilder;

/**
 * Simple system resolver, adding up all the relationships discovered while
 * browsing a SnitchRegistry.
 * 
 * @author michaeltecourt
 */
public class SimpleSystemResolver implements SystemResolver {

    @Override
    public System resolve(String systemName, SnitchRegistry snitchRegistry) {
        Set<SystemFragment> frags = snitchRegistry.getAll().stream().map(Snitch::snitch).collect(Collectors.toSet());
        return resolve(systemName, frags);
    }

    @Override
    public System resolve(String systemName, Collection<SystemFragment> fragments) {
        // Flatten all fragments into a single relationship set
        Set<ComponentRelationships> relationships = fragments.stream().map(SystemFragment::getComponentRelationships)
                .flatMap(Collection::stream).collect(Collectors.toSet());

        Map<Component, ComponentRelationshipsBuilder> map = new HashMap<>();

        // Browse all relationships to complete them with one another
        for (ComponentRelationships rel : relationships) {
            ComponentRelationshipsBuilder builder = getOrCreate(map, rel.getComponent());

            Set<Dependency> dependencies = rel.getDependencies();
            builder.addDependencies(dependencies);
            // Add the current component as a consumer of its dependencies
            for (Dependency dependency : dependencies) {
                ComponentRelationshipsBuilder dependencyBuilder = getOrCreate(map, dependency.getComponent());
                dependencyBuilder.addConsumer(Consumer.by(rel.getComponent()));
            }

            Set<Consumer> consumers = rel.getConsumers();
            builder.addConsumers(consumers);
            // Add the current component as a dependency of its consumers
            for (Consumer consumer : consumers) {
                ComponentRelationshipsBuilder consumerBuilder = getOrCreate(map, consumer.getComponent());
                consumerBuilder.addDependency(Dependency.on(rel.getComponent()));
            }
        }

        // @formatter:off
        Set<ComponentRelationships> bigPicture = map.values().stream()
                .map(ComponentRelationshipsBuilder::build)
                .collect(Collectors.toSet());
        // @formatter:on
        return System.of(systemName, bigPicture);
    }

    /**
     * Create a ComponentRelationshipsBuilder if one didn't already exist in the
     * map.
     * 
     * @param map
     * @param component
     * @return ComponentRelationshipsBuilder
     */
    private ComponentRelationshipsBuilder getOrCreate(Map<Component, ComponentRelationshipsBuilder> map,
            Component component) {
        ComponentRelationshipsBuilder builder = map.get(component);
        if (builder == null) {
            builder = ComponentRelationships.builder().component(component);
            map.put(component, builder);
        }
        return builder;
    }

}
