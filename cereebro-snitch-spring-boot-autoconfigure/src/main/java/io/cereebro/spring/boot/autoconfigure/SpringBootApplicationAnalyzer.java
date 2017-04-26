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

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import io.cereebro.core.ApplicationAnalyzer;
import io.cereebro.core.Component;
import io.cereebro.core.ComponentRelationships;
import io.cereebro.core.CompositeRelationshipDetector;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.core.SystemFragment;

/**
 * Analyze an application based on Spring Boot configuration properties and all
 * the {@link RelationshipDetector}s available in the Spring Context.
 * 
 * @author michaeltecourt
 *
 */
public class SpringBootApplicationAnalyzer implements ApplicationAnalyzer {

    private final Component application;

    private final RelationshipDetector detector;

    /**
     * Analyze an application based on Spring Boot configuration properties and
     * all the {@link RelationshipDetector}s available in the Spring Context.
     * 
     * @param cereebroProperties
     *            Cereebro Snitch properties.
     * @param detectors
     *            All relationship detectors available.
     */
    public SpringBootApplicationAnalyzer(CereebroProperties cereebroProperties,
            Collection<RelationshipDetector> detectors) {
        Objects.requireNonNull(cereebroProperties, "Cereebro Snitch properties required");
        this.application = cereebroProperties.getApplication().getComponent().toComponent();
        this.detector = new CompositeRelationshipDetector(detectors);
    }

    @Override
    public ComponentRelationships analyzeApplication() {
        Set<Relationship> relations = detector.detect();
        return ComponentRelationships.of(application, relations);
    }

    @Override
    public SystemFragment analyzeSystem() {
        // We only analyze the host application at the moment
        return SystemFragment.of(Collections.singleton(analyzeApplication()));
    }

}
