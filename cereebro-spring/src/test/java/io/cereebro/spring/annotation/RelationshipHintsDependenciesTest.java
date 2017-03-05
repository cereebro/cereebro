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
import io.cereebro.spring.annotation.RelationshipHintsDependenciesTest.AnnotatedClassDependenciesRelationshipHintsTestConfig;
import io.cereebro.spring.annotation.test.RelationshipHintsWithDependenciesClass;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AnnotatedClassDependenciesRelationshipHintsTestConfig.class)
public class RelationshipHintsDependenciesTest {

    private static final Relationship[] EXPECTED_RELATIONSHIPS = new Relationship[] {
            Dependency.on(Component.of("firstDependency", "firstType")),
            Dependency.on(Component.of("secondDependency", "secondType")) };

    @Autowired
    private RelationshipHintsAnnotationRelationshipDetector detector;

    @Test
    public void testTheSize() {
        Set<Relationship> rels = detector.detect();
        Assertions.assertThat(rels).isNotEmpty();
        Assertions.assertThat(rels).hasSize(2);
    }

    @Test
    public void testTheContents() {
        Set<Relationship> rels = detector.detect();
        for (Relationship r : rels) {
            Assertions.assertThat(r).isExactlyInstanceOf(Dependency.class);
        }
        Assertions.assertThat(rels).containsExactlyInAnyOrder(EXPECTED_RELATIONSHIPS);
    }

    @Configuration
    public static class AnnotatedClassDependenciesRelationshipHintsTestConfig {

        @Bean
        public RelationshipHintsAnnotationRelationshipDetector detector() {
            return new RelationshipHintsAnnotationRelationshipDetector(
                    new DependencyHintAnnotationRelationshipDetector(),
                    new ConsumerHintAnnotationRelationshipDetector());
        }

        @Bean
        public RelationshipHintsWithDependenciesClass relationsWithDependencies() {
            return new RelationshipHintsWithDependenciesClass();
        }
    }

}
