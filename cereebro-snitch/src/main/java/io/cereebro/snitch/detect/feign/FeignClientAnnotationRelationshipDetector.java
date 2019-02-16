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
package io.cereebro.snitch.detect.feign;

import java.util.Map;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.snitch.detect.annotation.AnnotationRelationshipDetector;

public class FeignClientAnnotationRelationshipDetector extends AnnotationRelationshipDetector<FeignClient> {

    public FeignClientAnnotationRelationshipDetector() {
        super(FeignClient.class);
    }

    @Override
    protected Set<Relationship> extractFromAnnotation(FeignClient annotation) {
        Dependency dependency = Dependency.on(Component.of(annotation.name(), ComponentType.HTTP_APPLICATION));
        return dependency.asRelationshipSet();
    }

    /**
     * @throws UnsupportedOperationException
     *             because {@link FeignClient} cannot be applied on methods.
     */
    @Override
    protected Set<Relationship> extractFromAnnotationAttributes(Map<String, Object> annotationAttributes) {
        throw new UnsupportedOperationException("@FeignClient cannot be used on methods");
    }

}
