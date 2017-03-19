/*
 * Copyright © 2009 the original authors (http://cereebro.io)
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

import java.util.Optional;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.core.type.MethodMetadata;

import io.cereebro.core.Relationship;
import io.cereebro.core.annotation.ConsumerHint;

/**
 * Edge cases that should not happen within a Spring application.
 * 
 * @author michaeltecourt
 */
public class AnnotationRelationshipDetectorEdgeCasesTest {

    @Test
    public void detectWithNullContextShouldReturnEmptySet() {
        Set<Relationship> result = new ConsumerHintAnnotationRelationshipDetector().detect();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void detectWithNonConfigurableApplicationContextShouldReturnEmptySet() {
        ConsumerHintAnnotationRelationshipDetector detector = new ConsumerHintAnnotationRelationshipDetector();
        ApplicationContext nonConfigurableApplicationContextMock = Mockito.mock(ApplicationContext.class);
        detector.setApplicationContext(nonConfigurableApplicationContextMock);
        Set<Relationship> result = detector.detect();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void detectUnknownClass() {
        ConsumerHintAnnotationRelationshipDetector detector = new ConsumerHintAnnotationRelationshipDetector();
        MethodMetadata metadataMock = Mockito.mock(MethodMetadata.class);
        Mockito.when(metadataMock.getReturnTypeName()).thenReturn("com.n0pe.ISwearThisClassWontExist");
        Optional<ConsumerHint> result = detector.getAnnotation(metadataMock);
        Assertions.assertThat(result.isPresent()).isFalse();
    }

}
