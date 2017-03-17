package io.cereebro.spring.cloud.autoconfigure.eureka;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.Snitch;

/**
 * Adds Cereebro metadata when registering an Eureka service (client
 * application).
 * 
 * @author michaeltecourt
 *
 */
@Configuration
@ConditionalOnClass(value = CloudEurekaInstanceConfig.class)
public class CereebroEurekaInstanceAutoConfiguration {

    @Bean
    @ConditionalOnBean({ Snitch.class, CloudEurekaInstanceConfig.class })
    public EurekaMetadataPopulator eurekaPopulator(Snitch snitch, CloudEurekaInstanceConfig config,
            ObjectMapper mapper) {
        EurekaMetadataPopulator eurekaMetadataPopulator = new EurekaMetadataPopulator(snitch, config, mapper);
        eurekaMetadataPopulator.populate();
        return eurekaMetadataPopulator;
    }

}
