package io.cereebro.server;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.DefaultSystemService;
import io.cereebro.core.SimpleSystemResolver;
import io.cereebro.core.Snitch;
import io.cereebro.core.SnitchRegistry;
import io.cereebro.core.StaticSnitchRegistry;
import io.cereebro.core.SystemResolver;
import io.cereebro.core.SystemService;
import io.cereebro.spring.ResourceSnitch;

@Configuration
@EnableConfigurationProperties(CereebroServerProperties.class)
public class CereebroServerConfiguration {

    @Autowired
    private CereebroServerProperties server;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    @ConditionalOnMissingBean
    public SnitchRegistry staticSnitchRegistry() {
        // @formatter:off
        List<Snitch> snitches = server.getSystem().getSnitch().getResources()
                .stream().map(resource -> new ResourceSnitch(objectMapper, resource))
                .collect(Collectors.toList());
        // @formatter:on
        return new StaticSnitchRegistry(snitches);
    }

    @Bean
    @ConditionalOnMissingBean
    public SystemResolver systemResolver() {
        return new SimpleSystemResolver();
    }

    @Bean
    @ConditionalOnMissingBean
    public SystemService systemService(SystemResolver systemResolver, SnitchRegistry snitchRegistry) {
        return new DefaultSystemService(server.getSystem().getName(), systemResolver, snitchRegistry);
    }

}
