package io.cereebro.cloud.autoconfigure.annotation.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;

import io.cereebro.autoconfigure.annotation.AnnotationRelationshipDetector;
import io.cereebro.core.Component;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;

public class FeignClientAnnotationRelationshipDetector extends AnnotationRelationshipDetector<FeignClient> {

    private static final String FEIGN_DEPENDENCY_TYPE = "RESTAPI";

    public FeignClientAnnotationRelationshipDetector() {
        super(FeignClient.class);
    }

    @Override
    protected Relationship extractFromAnnotation(FeignClient annotation) {
        return Dependency.on(Component.of(annotation.name(), FEIGN_DEPENDENCY_TYPE));
    }

    @Override
    protected Relationship extractFromAnnotationAttributes(Map<String, Object> annotationAttributes) {
        return Dependency.on(Component.of(String.class.cast(annotationAttributes.get("name")), FEIGN_DEPENDENCY_TYPE));
    }

}
