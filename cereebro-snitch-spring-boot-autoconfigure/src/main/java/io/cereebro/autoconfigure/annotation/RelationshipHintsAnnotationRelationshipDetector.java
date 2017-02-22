package io.cereebro.autoconfigure.annotation;

import java.util.Map;
import java.util.Set;

import org.springframework.core.annotation.AnnotationAttributes;

import com.google.common.collect.Sets;

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
        Set<Relationship> result = Sets.newHashSet();
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
        Set<Relationship> result = Sets.newHashSet();
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
     * @return
     */
    protected Component component(AnnotationAttributes annotationAttributes) {
        return Component.of((String) annotationAttributes.get("name"), (String) annotationAttributes.get("type"));
    }

}
