package io.cereebro.cloud.autoconfigure.eureka;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.Snitch;

@Configuration
@ConditionalOnClass(value = CloudEurekaInstanceConfig.class)
public class CereebroEurekaAutoConfiguration {

    @Autowired(required = false)
    private EurekaMetadataPopulator eurekaMetadata;

    @Bean
    @ConditionalOnEnabledEndpoint("cereebro")
    public EurekaMetadataPopulator eurekaPopulator(Snitch snitch, CloudEurekaInstanceConfig config,
            ObjectMapper mapper) {
        return new EurekaMetadataPopulator(snitch, config, mapper);
    }

    @PostConstruct
    public void register() {
        if (eurekaMetadata != null) {
            eurekaMetadata.populate();
        }
    }

}
