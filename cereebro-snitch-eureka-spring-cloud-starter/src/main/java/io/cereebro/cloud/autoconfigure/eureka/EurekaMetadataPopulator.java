package io.cereebro.cloud.autoconfigure.eureka;

import java.util.Objects;

import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;

import io.cereebro.core.Snitch;

/**
 * Populate metadata of the eureka instance with the snitch url.
 * 
 * @author lucwarrot
 *
 */
public class EurekaMetadataPopulator {

    private static final String METADATA_KEY = "io.cereebro.endpoint";

    public EurekaMetadataPopulator(Snitch snitch, CloudEurekaInstanceConfig config) {
        Objects.requireNonNull(snitch, "Snitch required");
        Objects.requireNonNull(config, "Cloud eureka instance config required");
        register(snitch, config);
    }

    private void register(Snitch snitch, CloudEurekaInstanceConfig config) {
        config.getMetadataMap().put(METADATA_KEY, snitch.getLocation().toString());
    }

}
