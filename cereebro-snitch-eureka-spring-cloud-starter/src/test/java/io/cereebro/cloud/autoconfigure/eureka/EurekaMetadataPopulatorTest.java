package io.cereebro.cloud.autoconfigure.eureka;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;

import io.cereebro.core.Snitch;

public class EurekaMetadataPopulatorTest {

    private static final String DEFAULT_URL = "http://localhost:8080/cereebro";

    private CloudEurekaInstanceConfig cloudEurekaInstanceConfig;
    private Map<String, String> metadata;
    private Snitch snitch;

    @Before
    public void setup() {
        cloudEurekaInstanceConfig = Mockito.mock(CloudEurekaInstanceConfig.class);
        metadata = new HashMap<String, String>();
        Mockito.when(cloudEurekaInstanceConfig.getMetadataMap()).thenReturn(metadata);
        Mockito.when(cloudEurekaInstanceConfig.getHostName(true)).thenReturn("localhost");
        Mockito.when(cloudEurekaInstanceConfig.getNonSecurePort()).thenReturn(8080);
        snitch = Mockito.mock(Snitch.class);
        Mockito.when(snitch.getLocation()).thenReturn(URI.create("/cereebro"));
    }

    @Test
    public void populateEurekaMetadaFromSnitch() {
        new EurekaMetadataPopulator(snitch, cloudEurekaInstanceConfig).register();
        Assertions.assertThat(metadata).isNotEmpty();
        Assertions.assertThat(metadata).containsEntry("io.cereebro.endpoint", DEFAULT_URL);
    }

    @Test
    public void populateEurekaMetadaFromAbsolutePath() {
        EurekaMetadataPopulator populator = new EurekaMetadataPopulator(snitch, cloudEurekaInstanceConfig);
        populator.setAbsolutePath(DEFAULT_URL);
        populator.register();
        Assertions.assertThat(metadata).isNotEmpty();
        Assertions.assertThat(metadata).containsEntry("io.cereebro.endpoint", DEFAULT_URL);
    }

    @Test
    public void populateEurekaMetadaFromRelativePath() {
        EurekaMetadataPopulator populator = new EurekaMetadataPopulator(snitch, cloudEurekaInstanceConfig);
        populator.setRelativePath("/cereebro");
        populator.register();
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
