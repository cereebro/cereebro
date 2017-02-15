package io.cereebro.autoconfigure;

import org.junit.Assert;
import org.junit.Test;

import io.cereebro.core.Component;
import io.cereebro.core.Dependency;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link DependencyProperties} unit tests.
 * 
 * @author michaeltecourt
 */
public class DependencyPropertiesTest {

    @Test
    public void equalsHashcode() {
        EqualsVerifier.forClass(DependencyProperties.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void toDependency() {
        DependencyProperties c = new DependencyProperties();
        ComponentProperties cmp = new ComponentProperties();
        c.setComponent(cmp);
        cmp.setName("magneto");
        cmp.setType("villain");
        Assert.assertEquals(Dependency.on(Component.of("magneto", "villain")), c.toDependency());
    }

}
