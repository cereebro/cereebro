package io.cereebro.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link ComponentRelationships} unit tests.
 * 
 * @author michaeltecourt
 */
public class ComponentRelationshipsTest {

    @Test
    public void filterDependenciesShouldReturnOnlyDependency() {
        Component b = TestHelper.componentB();
        Component c = TestHelper.componentC();
        Dependency dependencyB = Dependency.on(b);
        Consumer consumerC = Consumer.by(c);
        Set<Dependency> expected = new HashSet<>();
        expected.add(dependencyB);
        Set<Dependency> actual = ComponentRelationships.filterDependencies(Arrays.asList(dependencyB, consumerC));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void filterConsumersShouldReturnOnlyConsumer() {
        Component b = TestHelper.componentB();
        Component c = TestHelper.componentC();
        Dependency dependencyB = Dependency.on(b);
        Consumer consumerC = Consumer.by(c);
        Set<Consumer> expected = new HashSet<>();
        expected.add(consumerC);
        Set<Consumer> actual = ComponentRelationships.filterConsumers(Arrays.asList(dependencyB, consumerC));
        Assert.assertEquals(expected, actual);
    }
}
