package io.cereebro.cloud.autoconfigure.eureka;

import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;

import io.cereebro.core.Snitch;

public class EurekaMetaDataPopulator {

    public EurekaMetaDataPopulator(Snitch registry, EurekaInstanceConfigBean config) {
        register(registry, config);
    }

    private void register(Snitch registry, EurekaInstanceConfigBean config) {
        config.getMetadataMap().put("io.cereebro.endpoint", registry.getLocation().toString());
    }

}
