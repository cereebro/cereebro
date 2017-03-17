package io.cereebro.spring.cloud.autoconfigure.eureka;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.eureka.EurekaServerContext;

import io.cereebro.core.SnitchRegistry;

/**
 * Use Cereebro as a Eureka server add-on instead (or in parallel) of a
 * standalone application consuming the discovery client.
 * <p>
 * Service instances are found using Eureka server internals -- without making
 * HTTP calls through DiscoveryClient.
 * </p>
 * 
 * @author michaeltecourt
 */
@Configuration
public class CereebroEurekaServerAddOnAutoConfiguration {

    @Bean
    @ConditionalOnClass(EurekaServerContext.class)
    public SnitchRegistry eurekaServerSnitchRegistry(EurekaServerContext eurekaServerContext,
            ObjectMapper objectMapper) {
        return new EurekaServerSnitchRegistry(eurekaServerContext, objectMapper);
    }

}
