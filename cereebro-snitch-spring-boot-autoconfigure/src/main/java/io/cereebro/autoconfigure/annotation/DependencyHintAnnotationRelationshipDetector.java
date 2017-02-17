package io.cereebro.autoconfigure.annotation;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import io.cereebro.core.Component;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.core.annotation.DependencyHint;

/**
 * Implementation of {@link RelationshipDetector} used to detect all
 * {@link DependencyHint} declared in the {@link ApplicationContext}. The class
 * retrieves all {@link Bean} annotated with {@link DependencyHint}.
 * {@link Relationship}
 * 
 * @author lwarrot
 *
 */
public class DependencyHintAnnotationRelationshipDetector extends AnnotationRelationshipDetector<DependencyHint> {

    public DependencyHintAnnotationRelationshipDetector() {
        super(DependencyHint.class);
    }

    @Override
    protected Relationship extractFromAnnotation(DependencyHint annotation) {
        return Dependency.on(Component.of(annotation.name(), annotation.type()));
    }

    @Override
    protected Relationship extractFromAnnotationAttributes(Map<String, Object> annotationAttributes) {
        String name = String.class.cast(annotationAttributes.get("name"));
        String type = String.class.cast(annotationAttributes.get("type"));
        return Dependency.on(Component.of(name, type));
    }

}