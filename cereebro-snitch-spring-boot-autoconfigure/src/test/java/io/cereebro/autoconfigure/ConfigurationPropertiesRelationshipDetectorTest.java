package io.cereebro.autoconfigure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import io.cereebro.core.Component;
import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;

/**
 * {@link ConfigurationPropertiesRelationshipDetector} unit tests.
 * 
 * @author michaeltecourt
 */
public class ConfigurationPropertiesRelationshipDetectorTest {

    @Test
    public void detectRelationshipsBasedOnPropertiesShouldReturnDependenciesAndConsumers() {
        CereebroProperties properties = new CereebroProperties();
        ComponentRelationshipsProperties application = new ComponentRelationshipsProperties();
        DependencyProperties d = new DependencyProperties();
        ConsumerProperties c = new ConsumerProperties();
        ComponentProperties consumer = new ComponentProperties();
        consumer.setName("apocalypse");
        consumer.setType("villain");
        c.setComponent(consumer);
        application.setConsumers(Arrays.asList(c));
        ComponentProperties dependency = new ComponentProperties();
        dependency.setName("wolverine");
        dependency.setType("superhero");
        d.setComponent(dependency);
        application.setDependencies(Arrays.asList(d));
        properties.setApplication(application);

        Set<Relationship> expected = new HashSet<>(Arrays.asList(Dependency.on(Component.of("wolverine", "superhero")),
                Consumer.by(Component.of("apocalypse", "villain"))));
        ConfigurationPropertiesRelationshipDetector detector = new ConfigurationPropertiesRelationshipDetector(
                properties);
        Set<Relationship> actual = detector.detect();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullArgumentShouldThrowNullPointerException() {
        new ConfigurationPropertiesRelationshipDetector(null);
    }

    @Test
    public void detectPropertiesWithoutRelationshipsShouldReturnEmptySet() {
        Set<Relationship> expected = new HashSet<>();
        ConfigurationPropertiesRelationshipDetector detector = new ConfigurationPropertiesRelationshipDetector(
                new CereebroProperties());
        Set<Relationship> actual = detector.detect();
        Assert.assertEquals(expected, actual);
    }

}
