package io.cereebro.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.cereebro.core.ComponentRelationships.ComponentRelationshipsBuilder;

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

}
