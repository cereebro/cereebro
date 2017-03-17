package io.cereebro.spring.cloud.autoconfigure.discovery;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.SnitchRegistry;

/**
 * Allows a Cereebro application (normally the server) to access snitch data
 * from a service registry like Eureka or Consul. Snitched information is stored
 * as metadata on the service registry.
 * 
 * @author michaeltecourt
 */
@Configuration
@ConditionalOnClass(value = { DiscoveryClient.class })
public class CereebroDiscoveryClientAutoConfiguration {

    @Bean
    @ConditionalOnBean(DiscoveryClient.class)
    public SnitchRegistry discoveryClientSnitchRegistry(DiscoveryClient discoveryClient, ObjectMapper objectMapper) {
        return new DiscoveryClientSnitchRegistry(discoveryClient, objectMapper);
    }

}
