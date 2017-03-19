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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentRelationships;
import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import io.cereebro.core.System;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link Graph} unit tests.
 * 
 * @author michaeltecourt
 */
public class GraphTest {

    @Test
    public void system() {
        // @formatter:off
        ComponentRelationships gambit = ComponentRelationships.builder()
                .component(Component.of("gambit", "superhero"))
                .addDependency(Dependency.on(Component.of("rogue", "superhero")))
                .addConsumer(Consumer.by(Component.of("angel", "superhero")))
                .build();
        
        ComponentRelationships rogue = ComponentRelationships.builder()
                .component(Component.of("rogue", "superhero"))
                .addConsumer(Consumer.by(Component.of("gambit", "superhero")))
                .build();
        
        ComponentRelationships angel = ComponentRelationships.builder()
                .component(Component.of("angel", "superhero"))
                .addDependency(Dependency.on(Component.of("gambit", "superhero")))
                .build();
        // @formatter:on

        System system = System.of("xmen", gambit, angel, rogue);
        Graph graph = Graph.of(system);
        Assert.assertEquals(3, graph.getNodes().size());
        Edge gambitToRogue = Edge.create("superhero:gambit-to-superhero:rogue", "superhero:gambit", "superhero:rogue",
                Edge.TYPE_ARROW);
        Edge angelToGambit = Edge.create("superhero:angel-to-superhero:gambit", "superhero:angel", "superhero:gambit",
                Edge.TYPE_ARROW);
        Set<Edge> expectedEdges = new HashSet<>(Arrays.asList(gambitToRogue, angelToGambit));
        Assert.assertEquals(expectedEdges, graph.getEdges());
    }

    @Test
    public void verifyEqualsAndHashCode() {
        EqualsVerifier.forClass(Graph.class).suppress(Warning.STRICT_INHERITANCE).verify();
    }

    @Test
    public void testToString() {
        Assert.assertNotNull(Graph.create(new HashSet<>(), new HashSet<>()));
    }

}
