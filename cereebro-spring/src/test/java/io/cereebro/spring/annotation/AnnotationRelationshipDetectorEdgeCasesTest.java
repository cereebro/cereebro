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
