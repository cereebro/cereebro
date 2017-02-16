package io.cereebro.cloud.autoconfigure.eureka;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.cereebro.core.Snitch;

@Configuration
@ConditionalOnClass(value = EurekaInstanceConfigBean.class) // TODO do better!
public class CereebroEurekaAutoConfiguration {

    @Bean
    public EurekaMetaDataPopulator eurekaPopulator(Snitch registry, EurekaInstanceConfigBean config) {
        return new EurekaMetaDataPopulator(registry, config);
    }

}
