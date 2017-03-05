package io.cereebro.spring.boot.autoconfigure;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.env.MockEnvironment;

import io.cereebro.spring.boot.autoconfigure.CereebroProperties;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link CereebroProperties} unit tests.
 * 
 * @author michaeltecourt
 */
public class CereebroPropertiesTest {

    @Test
    public void equalsHashcode() {
        EqualsVerifier.forClass(CereebroProperties.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void setEnvironmentWithSpringApplicationNameShouldSetComponentName() {
        CereebroProperties c = new CereebroProperties();
        MockEnvironment env = new MockEnvironment();
        env.setProperty("spring.application.name", "cyclop");
        c.setEnvironment(env);
        Assert.assertEquals("cyclop", c.getApplication().getComponent().getName());
    }

    @Test
    public void setEnvironmentWithoutSpringApplicationNameShouldntChangeComponentName() {
        CereebroProperties c = new CereebroProperties();
        c.getApplication().getComponent().setName("storm");
        c.setEnvironment(new MockEnvironment());
        Assert.assertEquals("storm", c.getApplication().getComponent().getName());
    }

}
