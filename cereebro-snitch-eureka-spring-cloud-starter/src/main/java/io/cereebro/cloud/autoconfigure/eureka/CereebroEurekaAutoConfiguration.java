package io.cereebro.cloud.autoconfigure.eureka;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.cereebro.core.Snitch;

@Configuration
@ConditionalOnClass(value = CloudEurekaInstanceConfig.class)
public class CereebroEurekaAutoConfiguration {

    @Autowired
    private EurekaMetadataPopulator eurekaMetadata;

    @Bean
    public EurekaMetadataPopulator eurekaPopulator(Snitch snitch, CloudEurekaInstanceConfig config) {
        return new EurekaMetadataPopulator(snitch, config);
    }

    @PostConstruct
    public void register() {
        eurekaMetadata.register();
    }

}
