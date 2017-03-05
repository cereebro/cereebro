package io.cereebro.spring.boot.autoconfigure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import io.cereebro.core.Component;
import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.spring.boot.autoconfigure.ComponentProperties;
import io.cereebro.spring.boot.autoconfigure.ComponentRelationshipsProperties;
import io.cereebro.spring.boot.autoconfigure.ConsumerProperties;
import io.cereebro.spring.boot.autoconfigure.DependencyProperties;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link ComponentRelationshipsProperties} unit tests.
 * 
 * @author michaeltecourt
 */
public class ComponentRelationshipsPropertiesTest {

    @Test
    public void equalsHashcode() {
        EqualsVerifier.forClass(ComponentRelationshipsProperties.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void dependenciesEmptyByDefault() {
        ComponentRelationshipsProperties c = new ComponentRelationshipsProperties();
        Assert.assertNotNull(c.dependencies());
        Assert.assertTrue(c.dependencies().isEmpty());
    }

    @Test
    public void dependencies() {
        ComponentRelationshipsProperties c = new ComponentRelationshipsProperties();
        DependencyProperties d = createDependency("iceman", "superhero");
        c.setDependencies(Arrays.asList(d));
        Set<Dependency> expected = new HashSet<>(Arrays.asList(Dependency.on(Component.of("iceman", "superhero"))));
        Set<Dependency> actual = c.dependencies();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void consumers() {
        ComponentRelationshipsProperties c = new ComponentRelationshipsProperties();
        ConsumerProperties consumer = createConsumer("galactus", "villain");
        c.setConsumers(Arrays.asList(consumer));
        Set<Consumer> expected = new HashSet<>(Arrays.asList(Consumer.by(Component.of("galactus", "villain"))));
        Set<Consumer> actual = c.consumers();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void relationships() {
        ComponentRelationshipsProperties c = new ComponentRelationshipsProperties();
        DependencyProperties d = createDependency("iceman", "superhero");
        ConsumerProperties consumer = createConsumer("galactus", "villain");
        c.setConsumers(Arrays.asList(consumer));
        c.setDependencies(Arrays.asList(d));
        Set<Relationship> actual = c.getRelationships();
        Set<Relationship> expected = new HashSet<>(Arrays.asList(Consumer.by(Component.of("galactus", "villain")),
                Dependency.on(Component.of("iceman", "superhero"))));
        Assert.assertEquals(expected, actual);
    }

    private ConsumerProperties createConsumer(String name, String type) {
        ConsumerProperties d = new ConsumerProperties();
        ComponentProperties cmp = new ComponentProperties();
        cmp.setName(name);
        cmp.setType(type);
        d.setComponent(cmp);
        return d;
    }

    private DependencyProperties createDependency(String name, String type) {
        DependencyProperties d = new DependencyProperties();
        ComponentProperties cmp = new ComponentProperties();
        cmp.setName(name);
        cmp.setType(type);
        d.setComponent(cmp);
        return d;
    }

}
