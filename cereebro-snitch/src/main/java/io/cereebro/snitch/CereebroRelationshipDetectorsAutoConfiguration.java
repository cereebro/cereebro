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
package io.cereebro.snitch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.cereebro.core.RelationshipDetector;
import io.cereebro.snitch.detect.annotation.ConsumerHintAnnotationRelationshipDetector;
import io.cereebro.snitch.detect.annotation.DependencyHintAnnotationRelationshipDetector;
import io.cereebro.snitch.detect.annotation.RelationshipHintsAnnotationRelationshipDetector;

/**
 * Configures various {@link RelationshipDetector}s.
 * 
 * @author lwarrot
 */
@Configuration
@EnableConfigurationProperties({ CereebroProperties.class })
public class CereebroRelationshipDetectorsAutoConfiguration {

    @Autowired
    private CereebroProperties cereebroProperties;

    @Bean
    public ConfigurationPropertiesRelationshipDetector configurationPropertiesRelationshipDetector() {
        return new ConfigurationPropertiesRelationshipDetector(cereebroProperties);
    }

    @Bean
    public DependencyHintAnnotationRelationshipDetector dependencyHintAnnotationRelationshipDetector() {
        return new DependencyHintAnnotationRelationshipDetector();
    }

    @Bean
    public ConsumerHintAnnotationRelationshipDetector consumerHintAnnotationRelationshipDetector() {
        return new ConsumerHintAnnotationRelationshipDetector();
    }

    @Bean
    public RelationshipHintsAnnotationRelationshipDetector relationshipHintsDetector() {
        return new RelationshipHintsAnnotationRelationshipDetector(dependencyHintAnnotationRelationshipDetector(),
                consumerHintAnnotationRelationshipDetector());
    }
}
