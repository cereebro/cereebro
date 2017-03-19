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
