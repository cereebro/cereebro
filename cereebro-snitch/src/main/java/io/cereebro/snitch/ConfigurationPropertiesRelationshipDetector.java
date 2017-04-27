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

import java.util.Objects;
import java.util.Set;

import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;

/**
 * Detect the application's relationships declared in Cereebro's Spring Boot
 * ConfigurationProperties.
 * 
 * @author michaeltecourt
 */
public class ConfigurationPropertiesRelationshipDetector implements RelationshipDetector {

    private final CereebroProperties properties;

    /**
     * Detect the application's relationships declared in Cereebro's Spring Boot
     * ConfigurationProperties.
     * 
     * @param properties
     *            Cereebro properties that will be used to detect relationships.
     */
    public ConfigurationPropertiesRelationshipDetector(CereebroProperties properties) {
        this.properties = Objects.requireNonNull(properties, "Cereebro properties required");
    }

    @Override
    public Set<Relationship> detect() {
        return properties.getApplication().getRelationships();
    }

}
