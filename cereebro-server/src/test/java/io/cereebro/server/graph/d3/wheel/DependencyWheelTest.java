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

import java.util.Arrays;
import java.util.LinkedHashSet;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentRelationships;
import io.cereebro.core.Dependency;
import io.cereebro.core.System;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * {@link DependencyWheel} unit tests.
 * 
 * @author michaeltecourt
 */
public class DependencyWheelTest {

    @Test
    public void verifyHashcodeAndEquals() {
        EqualsVerifier.forClass(DependencyWheel.class).verify();
    }

    @Test
    public void coverToString() {
        Assertions.assertThat(DependencyWheel.builder().name("apocalypse").build().toString()).contains("apocalypse");
    }

    @Test
    public void create() {
        Component a = Component.of("a", "a");
        Component b = Component.of("b", "b");
        Component c = Component.of("c", "c");
        ComponentRelationships aRels = ComponentRelationships.builder().component(a).addDependency(Dependency.on(b))
                .build();
        ComponentRelationships bRels = ComponentRelationships.builder().component(b).addDependency(Dependency.on(c))
                .build();
        ComponentRelationships cRels = ComponentRelationships.builder().component(c).build();

        LinkedHashSet<ComponentRelationships> set = new LinkedHashSet<>();
        set.add(aRels);
        set.add(bRels);
        set.add(cRels);
        System system = System.of("sys", set);

        DependencyWheel result = DependencyWheel.of(system);

        // @formatter:off
        DependencyWheel expected = DependencyWheel.builder()
                .name("a")
                .matrixLine(Arrays.asList(0, 1, 0))
                .name("b")
                .matrixLine(Arrays.asList(0, 0, 1))
                .name("c")
                .matrixLine(Arrays.asList(0, 0, 0))
                .build();
        // @formatter:on

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void createCyclicDependencies() {
        Component a = Component.of("a", "a");
        Component b = Component.of("b", "b");
        ComponentRelationships aRels = ComponentRelationships.builder().component(a).addDependency(Dependency.on(b))
                .addDependency(Dependency.on(a)).build();
        ComponentRelationships bRels = ComponentRelationships.builder().component(b).addDependency(Dependency.on(a))
                .build();

        LinkedHashSet<ComponentRelationships> set = new LinkedHashSet<>();
        set.add(aRels);
        set.add(bRels);
        System system = System.of("sys", set);
        DependencyWheel result = DependencyWheel.of(system);

        // @formatter:off
        DependencyWheel expected = DependencyWheel.builder()
                .name("a")
                .matrixLine(Arrays.asList(1, 1))
                .name("b")
                .matrixLine(Arrays.asList(1, 0))
                .build();
        // @formatter:on

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test(expected = NullPointerException.class)
    public void buildNullNamesShouldThrowException() {
        DependencyWheel.builder().names(null).build();
    }

    @Test(expected = NullPointerException.class)
    public void buildNullMatrixShouldThrowException() {
        DependencyWheel.builder().dependencyMatrix(null).build();
    }

    @Test(expected = NullPointerException.class)
    public void nullNamesShouldThrowException() {
        new DependencyWheel(null, Arrays.asList());
    }

    @Test(expected = NullPointerException.class)
    public void nullMatrixShouldThrowException() {
        new DependencyWheel(Arrays.asList(), null);
    }

}
