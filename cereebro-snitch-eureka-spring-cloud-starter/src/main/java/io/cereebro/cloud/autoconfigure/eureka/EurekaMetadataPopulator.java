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
        // TODO
        // 1 - absolute path
        // 2 - eurekahostname + relativePath
        // 3 - eurekahostname + snitch.location
        config.getMetadataMap().put(METADATA_KEY, config.getHostName(true) + snitch.getLocation());
    }

}
