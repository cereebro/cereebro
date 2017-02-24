package io.cereebro.server.graph.sigma;

import org.junit.Assert;
import org.junit.Test;

import io.cereebro.core.Component;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link Node} unit tests.
 * 
 * @author michaeltecourt
 */
public class NodeTest {

    @Test
    public void verifyHashcodeEquals() {
        // @formatter:off
        EqualsVerifier.forClass(Node.class)
            .usingGetClass()
            .withIgnoredFields("x", "y", "size")
            .suppress(Warning.STRICT_INHERITANCE)
            .verify();
        // @formatter:on
    }

    @Test
    public void randomCoordinatesAndSize() {
        Node node = Node.create("id", "label");
        Assert.assertTrue(node.getX() >= 0);
        Assert.assertTrue(node.getY() >= 0);
        Assert.assertTrue(node.getSize() > 0);
    }

    @Test
    public void nodeFromComponent() {
        Node node = Node.of(Component.of("apocalypse", "villain"));
        Assert.assertEquals("villain:apocalypse", node.getId());
        Assert.assertEquals("villain:apocalypse", node.getLabel());
        Assert.assertTrue(node.getSize() > 2);
        Assert.assertTrue(node.getX() >= 0);
        Assert.assertTrue(node.getY() >= 0);
    }

    @Test
    public void testToString() {
        Node node = Node.builder().id("wolverine").label("wolverine (xmen)").x(13).y(37).size(99).build();
        String toString = node.toString();
        Assert.assertTrue(toString.contains("wolverine (xmen)"));
        Assert.assertTrue(toString.contains("13"));
        Assert.assertTrue(toString.contains("37"));
        Assert.assertTrue(toString.contains("99"));
    }
}
