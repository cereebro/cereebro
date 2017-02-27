package io.cereebro.snitch.spring.boot.actuate.endpoint;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentRelationships;
import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.core.SystemFragment;

/**
 * {@link CereebroEndpoint} unit tests.
 * 
 * @author michaeltecourt
 */
public class CereebroEndpointTest {

    private RelationshipDetector relationshipDetectorMock;
    private Component application;
    private CereebroEndpoint endpoint;

    @Before
    public void setUp() {
        application = Component.of("gambit", "superhero");
        relationshipDetectorMock = Mockito.mock(RelationshipDetector.class);
        endpoint = new CereebroEndpoint(application, relationshipDetectorMock);
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullApplicationComponentShouldThrowNullPointerException() {
        new CereebroEndpoint(null, relationshipDetectorMock);
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullRelationshipDetectorShouldThrowNullPointerException() {
        new CereebroEndpoint(application, null);
    }

    @Test
    public void isSensitive() {
        Assert.assertTrue(endpoint.isSensitive());
    }

    @Test
    public void isEnabled() {
        Assert.assertTrue(endpoint.isEnabled());
    }

    @Test
    public void id() {
        Assert.assertEquals("cereebro", endpoint.getId());
    }

    @Test
    public void location() {
        Assert.assertEquals(URI.create("/cereebro"), endpoint.getLocation());
    }

    @Test
    public void invoke() {
        Dependency d1 = Dependency.on(Component.of("cards", "game"));
        Dependency d2 = Dependency.on(Component.of("rogue", "superhero"));
        Consumer consumer = Consumer.by(Component.of("angel", "superhero"));
        Set<Relationship> rels = new HashSet<>(Arrays.asList(d1, d2, consumer));
        Mockito.when(relationshipDetectorMock.detect()).thenReturn(rels);
        SystemFragment actual = endpoint.invoke();

        Set<Dependency> dependencies = new HashSet<>(Arrays.asList(d1, d2));
        Set<Consumer> consumers = new HashSet<>(Arrays.asList(consumer));
        ComponentRelationships r = ComponentRelationships.of(application, dependencies, consumers);
        SystemFragment expected = SystemFragment.of(r);

        Assert.assertEquals(expected, actual);
    }

}
