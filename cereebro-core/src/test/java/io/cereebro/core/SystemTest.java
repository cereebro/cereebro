package io.cereebro.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * {@link System} unit tests.
 */
public class SystemTest {

    @Test(expected = NullPointerException.class)
    public void constructorWithNullNameShouldThrowNullPointerException() {
        ComponentRelationships c = ComponentRelationships.of(TestHelper.componentA(),
                TestHelper.relationshipSetOfDependencyBAndConsumerC());
        new System(null, new HashSet<>(Arrays.asList(c)));
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullRelsShouldThrowNullPointerException() {
        new System("name", null);
    }

    @Test
    public void constructor() {
        ComponentRelationships c = ComponentRelationships.of(TestHelper.componentA(),
                TestHelper.relationshipSetOfDependencyBAndConsumerC());
        Set<ComponentRelationships> rels = new HashSet<>(Arrays.asList(c));
        System system = System.of("system", rels);
        Assert.assertEquals("system", system.getName());
        Assert.assertEquals(rels, system.getComponentRelationships());
    }

    @Test
    public void hashcodeEquals() {
        EqualsVerifier.forClass(System.class).usingGetClass().verify();
    }

}
