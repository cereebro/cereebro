package io.cereebro.core;

import org.junit.Assert;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * {@link Component} unit tests.
 * 
 * @author michaeltecourt
 */
public class ComponentTest {

    @Test
    public void constructor() {
        Component c = Component.of("name", "type");
        Assert.assertEquals("name", c.getName());
        Assert.assertEquals("type", c.getType());
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullNameShouldThrowNullPointerException() {
        Component.of(null, "type");
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullTypeShouldThrowNullPointerException() {
        Component.of("name", null);
    }

    @Test
    public void verifyHashcodeEquals() {
        EqualsVerifier.forClass(Component.class).usingGetClass().verify();
    }

    @Test
    public void testToString() {
        String toString = Component.of("cyclop", "superhero").toString();
        java.lang.System.out.println(toString);
        Assert.assertTrue(toString.contains("cyclop"));
        Assert.assertTrue(toString.contains("superhero"));
    }

    @Test
    public void testAsString() {
        Assert.assertEquals("type:name", Component.of("name", "type").asString());
    }

}
