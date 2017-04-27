/*
 * Copyright © 2017 the original authors (http://cereebro.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.cereebro.server;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.CompositeSnitchRegistry;
import io.cereebro.core.DefaultSystemService;
import io.cereebro.core.SimpleSystemResolver;
import io.cereebro.core.Snitch;
import io.cereebro.core.SnitchRegistry;
import io.cereebro.core.StaticSnitchRegistry;
import io.cereebro.core.SystemResolver;
import io.cereebro.core.SystemService;

@Configuration
@EnableConfigurationProperties(CereebroServerProperties.class)
public class CereebroServerConfiguration {

    @Autowired
    private CereebroServerProperties server;

    @Bean
    public SnitchRegistry staticResourceSnitchRegistry(ObjectMapper objectMapper) {
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
    public SystemService systemService(SystemResolver systemResolver, List<SnitchRegistry> snitchRegistries) {
        SnitchRegistry compositeSnitchRegistry = new CompositeSnitchRegistry(snitchRegistries);
        return new DefaultSystemService(server.getSystem().getName(), systemResolver, compositeSnitchRegistry);
    }

    @Bean
    public CereebroSystemController cereebroSystemController(SystemService systemService) {
        return new CereebroSystemController(systemService);
    }

}
