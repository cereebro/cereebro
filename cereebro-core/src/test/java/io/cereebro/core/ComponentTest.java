package io.cereebro.core;

import org.junit.Assert;
import org.junit.Test;

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

}
