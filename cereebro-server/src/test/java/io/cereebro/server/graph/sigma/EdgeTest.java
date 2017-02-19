package io.cereebro.server.graph.sigma;

import org.junit.Assert;
import org.junit.Test;

import io.cereebro.core.Component;
import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link Edge} unit tests.
 * 
 * @author michaeltecourt
 */
public class EdgeTest {

    @Test
    public void verifyHashCodeEquals() {
        EqualsVerifier.forClass(Edge.class).suppress(Warning.STRICT_INHERITANCE).verify();
    }

    @Test
    public void consumerToComponent() {
        Edge actual = Edge.from(Component.of("phoenix", "superhero"), Consumer.by(Component.of("cyclop", "superhero")));
        Edge expected = Edge.create("cyclop-to-phoenix", "cyclop", "phoenix");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void componentToDependency() {
        Edge actual = Edge.to(Component.of("phoenix", "superhero"), Dependency.on(Component.of("xavier", "superhero")));
        Edge expected = Edge.create("phoenix-to-xavier", "phoenix", "xavier");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testToString() {
        Edge edge = Edge.create("phoenix-to-xavier", "phoenix", "xavier");
        Assert.assertTrue(edge.toString().contains("phoenix-to-xavier"));
    }

}
