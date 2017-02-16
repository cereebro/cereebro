package io.cereebro.cloud.autoconfigure.eureka;

import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;

import io.cereebro.core.Snitch;

/**
 * Populate metadata of the eureka instance with the snitch url.
 * 
 * @author lucwarrot
 *
 */
public class EurekaMetaDataPopulator {

    private static final String METADATA_KEY = "io.cereebro.endpoint";

    public EurekaMetaDataPopulator(Snitch registry, CloudEurekaInstanceConfig config) {
	register(registry, config);
    }

    private void register(Snitch registry, CloudEurekaInstanceConfig config) {
	config.getMetadataMap().put(METADATA_KEY, registry.getLocation().toString());
    }

}
