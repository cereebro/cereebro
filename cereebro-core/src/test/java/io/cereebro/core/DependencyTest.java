package io.cereebro.core;

import org.junit.Assert;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * {@link Dependency} unit test.
 * 
 * @author michaeltecourt
 */
public class DependencyTest {

    @Test
    public void verifyHashcodeEquals() {
        EqualsVerifier.forClass(Dependency.class).usingGetClass().verify();
    }

    @Test
    public void testToString() {
        String toString = Dependency.on(Component.of("storm", "xmen")).toString();
        Assert.assertTrue(toString.contains("storm"));
        Assert.assertTrue(toString.contains("xmen"));
    }

    @Test
    public void constructor() {
        Component b = TestHelper.componentB();
        Dependency dependency = new Dependency(b);
        Assert.assertEquals(b, dependency.getComponent());
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullArgumentShouldThrowNullPointerException() {
        new Dependency(null);
    }

    @Test
    public void staticFactoryMethodShouldReturnSameAsConstructor() {
        Component b = TestHelper.componentB();
        Dependency expected = new Dependency(b);
        Dependency actual = Dependency.on(b);
        Assert.assertEquals(expected, actual);
    }
}
