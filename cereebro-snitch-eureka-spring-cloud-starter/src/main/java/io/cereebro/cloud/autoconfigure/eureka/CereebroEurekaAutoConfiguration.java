package io.cereebro.cloud.autoconfigure.eureka;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.cereebro.core.Snitch;

@Configuration
@ConditionalOnClass(value = CloudEurekaInstanceConfig.class)
public class CereebroEurekaAutoConfiguration {

    @Bean
    public EurekaMetaDataPopulator eurekaPopulator(Snitch registry, CloudEurekaInstanceConfig config) {
	return new EurekaMetaDataPopulator(registry, config);
    }

}
