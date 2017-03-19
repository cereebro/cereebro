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
package io.cereebro.spring.annotation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.core.annotation.AnnotationAttributes;

import io.cereebro.core.Component;
import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.annotation.ConsumerHint;
import io.cereebro.core.annotation.DependencyHint;
import io.cereebro.core.annotation.RelationshipHints;

/**
 * Detect {@link Dependency} and {@link Consumer} from a
 * {@link RelationshipHints} annotation.
 * 
 * @author lucwarrot
 *
 */
public class RelationshipHintsAnnotationRelationshipDetector extends AnnotationRelationshipDetector<RelationshipHints> {

    private final DependencyHintAnnotationRelationshipDetector dependencyDetector;
    private final ConsumerHintAnnotationRelationshipDetector consumerDetector;

    public RelationshipHintsAnnotationRelationshipDetector(
            DependencyHintAnnotationRelationshipDetector dependencyDetector,
            ConsumerHintAnnotationRelationshipDetector consumerDetector) {
        super(RelationshipHints.class);
        this.dependencyDetector = dependencyDetector;
        this.consumerDetector = consumerDetector;
    }

    @Override
    protected Set<Relationship> extractFromAnnotation(RelationshipHints annotation) {
        Set<Relationship> result = new HashSet<>();
        for (DependencyHint dependency : annotation.dependencies()) {
            result.addAll(dependencyDetector.extractFromAnnotation(dependency));
        }
        for (ConsumerHint consumer : annotation.consumers()) {
            result.addAll(consumerDetector.extractFromAnnotation(consumer));
        }
        return result;
    }

    @Override
    protected Set<Relationship> extractFromAnnotationAttributes(Map<String, Object> annotationAttributes) {
        Set<Relationship> result = new HashSet<>();
        AnnotationAttributes[] dependencies = (AnnotationAttributes[]) annotationAttributes.get("dependencies");
        for (AnnotationAttributes dependency : dependencies) {
            result.add(Dependency.on(component(dependency)));
        }
        AnnotationAttributes[] consumers = (AnnotationAttributes[]) annotationAttributes.get("consumers");
        for (AnnotationAttributes consumer : consumers) {
            result.add(Consumer.by(component(consumer)));
        }
        return result;
    }

    /**
     * Create a {@link Component} from an {@link AnnotationAttributes}
     * 
     * @param annotationAttributes
     *            Annotation attributes to extract metadata from.
     * @return Component
     */
    protected Component component(AnnotationAttributes annotationAttributes) {
        return Component.of((String) annotationAttributes.get("name"), (String) annotationAttributes.get("type"));
    }

}
