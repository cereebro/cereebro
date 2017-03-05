package io.cereebro.spring.boot.autoconfigure;

import org.junit.Assert;
import org.junit.Test;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link ComponentProperties} unit tests.
 * 
 * @author michaeltecourt
 *
 */
public class ComponentPropertiesTest {

    @Test
    public void equalsHashcode() {
        EqualsVerifier.forClass(ComponentProperties.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void defaultValues() {
        ComponentProperties c = new ComponentProperties();
        Assert.assertNotNull(c.getName());
        Assert.assertEquals(ComponentType.WEB_APPLICATION, c.getType());
    }

    @Test
    public void toComponent() {
        ComponentProperties c = new ComponentProperties();
        c.setName("wolverine");
        c.setType("superhero");
        Component expected = Component.of("wolverine", "superhero");
        Component actual = c.toComponent();
        Assert.assertEquals(expected, actual);
    }

}
