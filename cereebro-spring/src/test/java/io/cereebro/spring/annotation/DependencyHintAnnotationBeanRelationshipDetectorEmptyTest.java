package io.cereebro.spring.annotation;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.Relationship;
import io.cereebro.spring.annotation.DependencyHintAnnotationBeanRelationshipDetectorEmptyTest.EmptyDependencyTestConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = EmptyDependencyTestConfig.class)
public class DependencyHintAnnotationBeanRelationshipDetectorEmptyTest {

    @Autowired
    private DependencyHintAnnotationRelationshipDetector detector;

    @Test
    public void annotationRelationshipWithoutDeclaredBean() {
        Set<Relationship> relations = detector.detect();
        Assertions.assertThat(relations).isEmpty();
    }

    @Configuration
    public static class EmptyDependencyTestConfig {

        @Bean
        public DependencyHintAnnotationRelationshipDetector detector() {
            return new DependencyHintAnnotationRelationshipDetector();
        }

    }

}
