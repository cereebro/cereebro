package io.cereebro.server;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.SimpleSystemResolver;
import io.cereebro.core.Snitch;
import io.cereebro.core.SnitchRegistry;
import io.cereebro.core.StaticSnitchRegistry;
import io.cereebro.core.SystemResolver;
import io.cereebro.server.web.SystemController;
import io.cereebro.spring.ResourceSnitch;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableConfigurationProperties(CereebroServerProperties.class)
@Slf4j
public class CereebroServerConfiguration {

    @Autowired
    private CereebroServerProperties server;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SystemService service;

    @Autowired
    private SystemController ctrl;

    @PostConstruct
    public void fuck() {
        LOGGER.info("Resolved system : {}", service.getSystem());
        LOGGER.info("Damn controller is there : {}", ctrl);
    }

    @Bean
    public SnitchRegistry staticSnitchRegistry() {
        // @formatter:off
        List<Snitch> snitches = server.getSnitch().getResources()
                .stream().map(resource -> new ResourceSnitch(objectMapper, resource))
                .collect(Collectors.toList());
        // @formatter:on
        return new StaticSnitchRegistry(snitches);
    }

    @Bean
    public SystemResolver systemResolver() {
        return new SimpleSystemResolver();
    }

}
