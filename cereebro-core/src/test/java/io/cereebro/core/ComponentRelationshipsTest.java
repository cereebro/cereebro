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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.cereebro.core.ComponentRelationships.ComponentRelationshipsBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link ComponentRelationships} unit tests.
 * 
 * @author michaeltecourt
 */
public class ComponentRelationshipsTest {

    private Component component;
    private Component dependencyComponent;
    private Component consumerComponent;
    private Set<Dependency> dependencies;
    private Set<Consumer> consumers;

    @Before
    public void setUp() {
        component = TestHelper.componentA();
        dependencyComponent = TestHelper.componentB();
        consumerComponent = TestHelper.componentC();
        dependencies = new HashSet<>();
        dependencies.add(Dependency.on(dependencyComponent));
        consumers = new HashSet<>();
        consumers.add(Consumer.by(consumerComponent));
    }

    @Test
    public void hashcodeEquals() {
        EqualsVerifier.forClass(ComponentRelationships.class).usingGetClass().verify();
    }

    @Test
    public void testToString() {
        String toString = new ComponentRelationships(component, dependencies, consumers).toString();
        Assert.assertTrue(toString.contains(component.getName()));
        Assert.assertTrue(toString.contains(component.getType()));
    }

    @Test
    public void allArgsConstructor() {
        ComponentRelationships rels = new ComponentRelationships(component, dependencies, consumers);
        Assert.assertEquals(component, rels.getComponent());
        Assert.assertEquals(dependencies, rels.getDependencies());
        Assert.assertEquals(consumers, rels.getConsumers());
    }

    @Test
    public void filterDependenciesShouldReturnOnlyDependency() {
        Set<Dependency> actual = ComponentRelationships
                .filterDependencies(Arrays.asList(Dependency.on(dependencyComponent), Consumer.by(consumerComponent)));
        Assert.assertEquals(dependencies, actual);
    }

    @Test
    public void filterConsumersShouldReturnOnlyConsumer() {
        Dependency dependencyB = Dependency.on(dependencyComponent);
        Consumer consumerC = Consumer.by(consumerComponent);
        Set<Consumer> expected = new HashSet<>();
        expected.add(consumerC);
        Set<Consumer> actual = ComponentRelationships.filterConsumers(Arrays.asList(dependencyB, consumerC));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void staticFactoryMethodUsingRelationshipsShouldReturnSameResultAsConstructor() {
        Set<Dependency> dependencies = new HashSet<>();
        dependencies.add(Dependency.on(dependencyComponent));
        Set<Consumer> consumers = new HashSet<>();
        consumers.add(Consumer.by(consumerComponent));
        ComponentRelationships expected = new ComponentRelationships(component, dependencies, consumers);
        ComponentRelationships actual = ComponentRelationships.of(component,
                Arrays.asList(Dependency.on(dependencyComponent), Consumer.by(consumerComponent)));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void staticFactoryMethodUsingDependenciesAndConsumersShouldReturnSameResultAsConstructor() {

        ComponentRelationships expected = new ComponentRelationships(component, dependencies, consumers);
        ComponentRelationships actual = ComponentRelationships.of(component, dependencies, consumers);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void builderShouldReturnSameResultAsConstructor() {
        ComponentRelationships expected = new ComponentRelationships(component, dependencies, consumers);
        // @formatter:off
        ComponentRelationships actual = ComponentRelationships.builder()
                .component(component)
                .dependencies(dependencies)
                .consumers(consumers)
                .build();
        // @formatter:on
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void builderAddConsumerShouldAppend() {
        ComponentRelationships expected = new ComponentRelationships(component, dependencies, consumers);
        // @formatter:off
        ComponentRelationships actual = ComponentRelationships.builder()
                .component(component)
                .dependencies(dependencies)
                .addConsumer(Consumer.by(consumerComponent))
                .build();
        // @formatter:on
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void builderAddDependencyShouldAppend() {
        ComponentRelationships expected = new ComponentRelationships(component, dependencies, consumers);
        // @formatter:off
        ComponentRelationships actual = ComponentRelationships.builder()
                .component(component)
                .addDependency(Dependency.on(dependencyComponent))
                .consumers(consumers)
                .build();
        // @formatter:on
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void builderAddDependenciesShouldAppendCollection() {
        Dependency dep = Dependency.on(Component.of("newOne", "app"));
        Set<Dependency> completeDependencies = new HashSet<Dependency>(
                Arrays.asList(dep, Dependency.on(dependencyComponent)));
        ComponentRelationships expected = new ComponentRelationships(component, completeDependencies, consumers);
        // @formatter:off
        ComponentRelationships actual = ComponentRelationships.builder()
                .component(component)
                .dependencies(dependencies)
                .addDependencies(Arrays.asList(dep))
                .consumers(consumers)
                .build();
        // @formatter:on
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void builderAddConsumerShouldAppendCollectionWithoutDuplicated() {
        Consumer newConsumer = Consumer.by(Component.of("newOne", "app"));
        Set<Consumer> completeConsumers = new HashSet<Consumer>(
                Arrays.asList(newConsumer, Consumer.by(consumerComponent)));
        ComponentRelationships expected = new ComponentRelationships(component, dependencies, completeConsumers);
        // @formatter:off
        ComponentRelationships actual = ComponentRelationships.builder()
                .component(component)
                .dependencies(dependencies)
                .consumers(consumers)
                // repeat a lot
                .addConsumers(Arrays.asList(newConsumer, newConsumer, Consumer.by(consumerComponent)))
                .build();
        // @formatter:on
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void builderEquivalence() {
        ComponentRelationshipsBuilder a = ComponentRelationships.builder();
        ComponentRelationshipsBuilder b = ComponentRelationships.builder();
        Assert.assertEquals(a, b);
        Assert.assertEquals(a.component(component), b.component(component));
        Assert.assertEquals(a.dependencies(dependencies), b.dependencies(dependencies));
        Assert.assertEquals(a.consumers(consumers), b.consumers(consumers));
        Assert.assertEquals(a.toString(), b.toString());
        Assert.assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void getRelationshipsShouldReturnBothConsumersAndDependencies() {
        ComponentRelationships c = ComponentRelationships.builder().component(component).dependencies(dependencies)
                .consumers(consumers).build();
        List<Relationship> relationships = Arrays.asList(Consumer.by(consumerComponent),
                Dependency.on(dependencyComponent));
        HashSet<Relationship> expected = new HashSet<>(relationships);
        Assert.assertEquals(expected, c.getRelationships());
    }

    @Test
    public void builderHashcodeEquals() {
        EqualsVerifier.forClass(ComponentRelationshipsBuilder.class).suppress(Warning.NONFINAL_FIELDS).usingGetClass()
                .verify();
    }

    @Test
    public void hasRelationshipsShouldReturnTrueWhenConsumerIsPresent() {
        ComponentRelationships c = ComponentRelationships.builder().component(component).consumers(consumers).build();
        Assertions.assertThat(c.hasRelationships()).isTrue();
    }

    @Test
    public void hasRelationshipsShouldReturnTrueWhenDependencyIsPresent() {
        ComponentRelationships c = ComponentRelationships.builder().component(component).dependencies(dependencies)
                .build();
        Assertions.assertThat(c.hasRelationships()).isTrue();
    }

    @Test
    public void hasRelationshipsShouldReturnFalseWithoutRelationships() {
        ComponentRelationships c = ComponentRelationships.builder().component(component).build();
        Assertions.assertThat(c.hasRelationships()).isFalse();
    }

}
