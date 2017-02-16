package io.cereebro.core;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * {@link SystemFragment} unit tests.
 * 
 * @author michaeltecourt
 */
public class SystemFragmentTest {

    @Test
    public void constructor() {
        ComponentRelationships c = ComponentRelationships.of(TestHelper.componentA(),
                TestHelper.relationshipSetOfDependencyBAndConsumerC());
        HashSet<ComponentRelationships> rels = new HashSet<>(Arrays.asList(c));
        SystemFragment frag = SystemFragment.of(rels);
        Assert.assertEquals(rels, frag.getComponentRelationships());
    }

    @Test
    public void verifyHashcodeEquals() {
        EqualsVerifier.forClass(SystemFragment.class).usingGetClass().verify();
    }

    @Test
    public void testToString() {
        // I'm a lazy slob
        Assert.assertNotNull(SystemFragment.empty().toString());
    }

}
