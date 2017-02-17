package io.cereebro.snitch.spring.boot.actuate.endpoint;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * {@link SnitchEndpointProperties} unit tests
 * 
 * @author lwarrot
 *
 */
public class SnitchEndpointPropertiesTest {

    @Test
    public void testDefaultValues() {
        SnitchEndpointProperties properties = new SnitchEndpointProperties();
        Assertions.assertThat(properties.getId()).isEqualTo("cereebro");
        Assertions.assertThat(properties.isSensitive()).isTrue();
        Assertions.assertThat(properties.isEnabled()).isTrue();
    }

    @Test
    public void testSetId() {
        SnitchEndpointProperties properties = new SnitchEndpointProperties();
        properties.setId("bro");
        Assertions.assertThat(properties.getId()).isEqualTo("bro");
    }

    @Test
    public void testDisableEndpoint() {
        SnitchEndpointProperties properties = new SnitchEndpointProperties();
        properties.setEnabled(false);
        Assertions.assertThat(properties.isEnabled()).isFalse();
    }

    @Test
    public void testUnsensitiveEndpoint() {
        SnitchEndpointProperties properties = new SnitchEndpointProperties();
        properties.setSensitive(false);
        Assertions.assertThat(properties.isSensitive()).isFalse();
    }

    @Test
    public void testDefaultHost() {
        SnitchEndpointProperties properties = new SnitchEndpointProperties();
        Assertions.assertThat(properties.getHost()).isEqualTo("localhost");
    }

    @Test
    public void testDefaultPort() {
        SnitchEndpointProperties properties = new SnitchEndpointProperties();
        Assertions.assertThat(properties.getPort()).isEqualTo("8080");
    }

    @Test
    public void testDefaultContext() {
        SnitchEndpointProperties properties = new SnitchEndpointProperties();
        Assertions.assertThat(properties.getContext()).isEqualTo("");
    }

}
