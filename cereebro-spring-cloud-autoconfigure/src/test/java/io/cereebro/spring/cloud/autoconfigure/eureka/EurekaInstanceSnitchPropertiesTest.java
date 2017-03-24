package io.cereebro.spring.cloud.autoconfigure.eureka;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link EurekaInstanceSnitchProperties} unit tests.
 * 
 * @author michaeltecourt
 */
public class EurekaInstanceSnitchPropertiesTest {

    @Test
    public void verifyEqualsAndHashcode() {
        EqualsVerifier.forClass(EurekaInstanceSnitchProperties.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void testToString() {
        EurekaInstanceSnitchProperties props = new EurekaInstanceSnitchProperties();
        props.setUrl("https://example.org/nope");
        props.setUrlPath("/nope");
        Assertions.assertThat(props.toString()).isNotEmpty();
    }
}
