package io.cereebro.core;

import org.junit.Assert;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * {@link Consumer} unit tests.
 * 
 * @author michaeltecourt
 */
public class ConsumerTest {

    @Test
    public void hashcodeEquals() {
        EqualsVerifier.forClass(Consumer.class).usingGetClass().verify();
    }

    @Test
    public void testToString() {
        String toString = Consumer.by(Component.of("wolverine", "xmen")).toString();
        Assert.assertTrue(toString.contains("wolverine"));
        Assert.assertTrue(toString.contains("xmen"));
    }

    @Test
    public void constructor() {
        Component c = TestHelper.componentC();
        Assert.assertEquals(c, new Consumer(c).getComponent());
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullArgumentShouldThrowNullPointerException() {
        new Consumer(null);
    }

    @Test
    public void staticFactoryMethodShouldReturnSameResultAsConstructor() {
        Component a = TestHelper.componentA();
        Consumer expected = new Consumer(a);
        Consumer actual = Consumer.by(a);
        Assert.assertEquals(expected, actual);
    }

}
