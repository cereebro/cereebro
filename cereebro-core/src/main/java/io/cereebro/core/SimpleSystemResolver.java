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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.cereebro.core.ComponentRelationships.ComponentRelationshipsBuilder;
import io.cereebro.core.System.SystemBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * Simple system resolver, adding up all the relationships discovered while
 * browsing a SnitchRegistry.
 * 
 * @author michaeltecourt
 */
@Slf4j
public class SimpleSystemResolver implements SystemResolver {

    private static final String DEFAULT_ERROR_MESSAGE = "Could not access or process Snitch";

    @Override
    public System resolve(String systemName, SnitchRegistry snitchRegistry) {
        Set<SystemFragment> frags = new HashSet<>();
        Set<ResolutionError> errors = new HashSet<>();
        for (Snitch snitch : snitchRegistry.getAll()) {
            try {
                frags.add(snitch.snitch());
            } catch (SnitchingException e) {
                LOGGER.error("Snitch error caught while resolving system at URI : " + e.getSnitchUri(), e);
                errors.add(ResolutionError.translate(e));
            } catch (RuntimeException e) {
                LOGGER.error("Technical error caught while resolving system at URI : " + snitch.getUri(), e);
                errors.add(ResolutionError.of(snitch.getUri(), DEFAULT_ERROR_MESSAGE));
            }
        }
        return resolveBuilder(systemName, frags).errors(errors).build();
    }

    @Override
    public System resolve(String systemName, Collection<SystemFragment> fragments) {
        return resolveBuilder(systemName, fragments).build();
    }

    /**
     * Resolves the system and returns a mutable {@link SystemBuilder} instance.
     * 
     * @param systemName
     *            System name.
     * @param fragments
     *            System fragments.
     * @return mutable SystemBuilder ready to be built into the real System.
     */
    private SystemBuilder resolveBuilder(String systemName, Collection<SystemFragment> fragments) {
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
        return System.builder().name(systemName).componentRelationships(bigPicture);
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
