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
package io.cereebro.spring.boot.autoconfigure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.ApplicationAnalyzer;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.spring.boot.autoconfigure.actuate.CereebroWebMvcEndpointConfiguration;

/**
 * Cereebro Snitch configuration entry point.
 * 
 * @author michaeltecourt
 */
@Configuration
@EnableConfigurationProperties({ CereebroProperties.class })
@Import({ CereebroRelationshipDetectorsAutoConfiguration.class, CereebroWebMvcEndpointConfiguration.class })
public class CereebroSnitchAutoConfiguration {

    @Autowired
    private CereebroProperties cereebroProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    @ConditionalOnMissingBean
    public ApplicationAnalyzer applicationAnalyzer(List<RelationshipDetector> detectors) {
        return new SpringBootApplicationAnalyzer(cereebroProperties, detectors);
    }

    @Bean
    @ConditionalOnProperty(prefix = "cereebro.snitch", name = "log-on-startup", havingValue = "true", matchIfMissing = false)
    public Slf4jLogSnitch logSnitch(ApplicationAnalyzer analyzer) {
        return new Slf4jLogSnitch(analyzer, objectMapper);
    }

    @Bean
    @ConditionalOnProperty(prefix = "cereebro.snitch.file", name = "enabled", havingValue = "true", matchIfMissing = false)
    public FileWriterSnitch fileSnitch(ApplicationAnalyzer analyzer) {
        return new FileWriterSnitch(analyzer, objectMapper, cereebroProperties);
    }

}
