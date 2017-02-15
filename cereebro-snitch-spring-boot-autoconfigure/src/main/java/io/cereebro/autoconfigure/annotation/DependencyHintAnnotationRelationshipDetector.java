package io.cereebro.autoconfigure.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

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
    protected String getName(DependencyHint annotation) {
        return annotation.name();
    }

    @Override
    protected String getType(DependencyHint annotation) {
        return annotation.type();
    }

    @Override
    protected String getNameKey() {
        return "name";
    }

    @Override
    protected String getTypeKey() {
        return "type";
    }

}
