package io.cereebro.autoconfigure.annotation;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

import io.cereebro.core.Component;
import io.cereebro.core.Consumer;
import io.cereebro.core.Relationship;
import io.cereebro.core.annotation.ConsumerHint;

/**
 * Detects {@link Consumer} relationships based on Spring beans annotated with
 * {@link ConsumerHint}.
 * 
 * @author michaeltecourt
 */
public class ConsumerHintAnnotationRelationshipDetector extends AnnotationRelationshipDetector<ConsumerHint> {

    /**
     * Detects {@link Consumer} relationships based on Spring beans annotated
     * with {@link ConsumerHint}.
     */
    public ConsumerHintAnnotationRelationshipDetector() {
        super(ConsumerHint.class);
    }

    @Override
    protected Set<Relationship> extractFromAnnotation(ConsumerHint annotation) {
        return Sets.newHashSet(Consumer.by(Component.of(annotation.name(), annotation.type())));
    }

    @Override
    protected Set<Relationship> extractFromAnnotationAttributes(Map<String, Object> annotationAttributes) {
        String name = String.class.cast(annotationAttributes.get("name"));
        String type = String.class.cast(annotationAttributes.get("type"));
        return Sets.newHashSet(Consumer.by(Component.of(name, type)));
    }

}
