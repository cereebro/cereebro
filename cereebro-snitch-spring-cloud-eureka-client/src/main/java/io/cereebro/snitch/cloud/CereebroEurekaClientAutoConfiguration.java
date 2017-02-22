package io.cereebro.snitch.cloud;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.SnitchRegistry;
import io.cereebro.snitch.cloud.eureka.EurekaSnitchRegistry;

@Configuration
@ConditionalOnClass(value = { EurekaServiceInstance.class, DiscoveryClient.class })
@EnableDiscoveryClient
public class CereebroEurekaClientAutoConfiguration {

    @Bean
    @Primary
    public SnitchRegistry eurekaSnitchRegistry(DiscoveryClient discoveryClient, ObjectMapper objectMapper) {
        return new EurekaSnitchRegistry(discoveryClient, objectMapper);
    }

}
