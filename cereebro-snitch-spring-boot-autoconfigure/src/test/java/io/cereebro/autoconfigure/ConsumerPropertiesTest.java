package io.cereebro.autoconfigure;

import org.junit.Assert;
import org.junit.Test;

import io.cereebro.core.Component;
import io.cereebro.core.Consumer;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link ConsumerProperties} unit tests.
 * 
 * @author michaeltecourt
 */
public class ConsumerPropertiesTest {

    @Test
    public void equalsHashcode() {
        EqualsVerifier.forClass(ConsumerProperties.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void toConsumerModel() {
        ConsumerProperties c = new ConsumerProperties();
        ComponentProperties cmp = new ComponentProperties();
        c.setComponent(cmp);
        cmp.setName("gambit");
        cmp.setType("superhero");
        Assert.assertEquals(Consumer.by(Component.of("gambit", "superhero")), c.toConsumer());
    }

}
