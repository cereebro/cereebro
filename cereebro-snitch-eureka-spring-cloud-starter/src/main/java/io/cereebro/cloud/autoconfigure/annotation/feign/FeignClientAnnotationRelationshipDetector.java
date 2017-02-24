package io.cereebro.cloud.autoconfigure.annotation.feign;

import java.util.Map;
import java.util.Set;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.google.common.collect.Sets;

import io.cereebro.autoconfigure.annotation.AnnotationRelationshipDetector;
import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;

public class FeignClientAnnotationRelationshipDetector extends AnnotationRelationshipDetector<FeignClient> {

    public FeignClientAnnotationRelationshipDetector() {
        super(FeignClient.class);
    }

    @Override
    protected Set<Relationship> extractFromAnnotation(FeignClient annotation) {
        return Sets.newHashSet(Dependency.on(Component.of(annotation.name(), ComponentType.WEB_APPLICATION)));
    }

    @Override
    protected Set<Relationship> extractFromAnnotationAttributes(Map<String, Object> annotationAttributes) {
        return Sets.newHashSet(Dependency
                .on(Component.of(String.class.cast(annotationAttributes.get("name")), ComponentType.WEB_APPLICATION)));
    }

}
