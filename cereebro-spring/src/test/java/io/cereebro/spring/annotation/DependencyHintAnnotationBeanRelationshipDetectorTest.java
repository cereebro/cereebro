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

import io.cereebro.core.Component;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.annotation.DependencyHint;
import io.cereebro.spring.annotation.DependencyHintAnnotationBeanRelationshipDetectorTest.AnnotatedBeanFactoryMethodDependencyTestConfig;
import io.cereebro.spring.annotation.test.Dummy;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AnnotatedBeanFactoryMethodDependencyTestConfig.class)
public class DependencyHintAnnotationBeanRelationshipDetectorTest {

    @Autowired
    private DependencyHintAnnotationRelationshipDetector detector;

    @Test
    public void annotationRelationshipWithDeclaredBean() {
        Set<Relationship> relations = detector.detect();
        Assertions.assertThat(relations).isNotEmpty();
        Assertions.assertThat(relations).hasSize(1);
        Relationship dummyRelation = relations.iterator().next();
        Dependency expected = Dependency.on(Component.of("dummy", "ws"));
        Assertions.assertThat(dummyRelation).isEqualTo(expected);
    }

    @Configuration
    public static class AnnotatedBeanFactoryMethodDependencyTestConfig {

        @Bean
        public DependencyHintAnnotationRelationshipDetector detector() {
            return new DependencyHintAnnotationRelationshipDetector();
        }

        @Bean
        @DependencyHint(name = "dummy", type = "ws")
        public Dummy myDummyDependency() {
            return new Dummy();
        }

    }

}
