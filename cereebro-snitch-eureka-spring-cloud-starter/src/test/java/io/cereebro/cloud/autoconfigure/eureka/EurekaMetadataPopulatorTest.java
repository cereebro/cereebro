package io.cereebro.cloud.autoconfigure.eureka;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;

import io.cereebro.core.Snitch;

public class EurekaMetadataPopulatorTest {

    private static final String DEFAULT_URL = "http://localhost:8080/cereebro";

    @Test
    public void populateEurekaMetada() {
        Snitch snitch = Mockito.mock(Snitch.class);
        CloudEurekaInstanceConfig cloudEurekaInstanceConfig = Mockito.mock(CloudEurekaInstanceConfig.class);
        Map<String, String> metadata = new HashMap<String, String>();
        Mockito.when(snitch.getLocation()).thenReturn(URI.create(DEFAULT_URL));
        Mockito.when(cloudEurekaInstanceConfig.getMetadataMap()).thenReturn(metadata);
        new EurekaMetadataPopulator(snitch, cloudEurekaInstanceConfig);
        Assertions.assertThat(metadata).isNotEmpty();
        Assertions.assertThat(metadata).containsEntry("io.cereebro.endpoint", DEFAULT_URL);
    }

    @Test(expected = NullPointerException.class)
    public void snitchRequired() {
        new EurekaMetadataPopulator(null, Mockito.mock(CloudEurekaInstanceConfig.class));
    }

    @Test(expected = NullPointerException.class)
    public void configRequired() {
        new EurekaMetadataPopulator(Mockito.mock(Snitch.class), null);
    }

}
