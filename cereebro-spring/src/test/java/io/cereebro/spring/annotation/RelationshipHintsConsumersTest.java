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
import io.cereebro.core.Consumer;
import io.cereebro.core.Relationship;
import io.cereebro.spring.annotation.RelationshipHintsConsumersTest.RelationshipHintsConsumerTestConfig;
import io.cereebro.spring.annotation.test.RelationshipHintsWithConsumersClass;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RelationshipHintsConsumerTestConfig.class)
public class RelationshipHintsConsumersTest {

    private static final Relationship[] EXPECTED_RELATIONSHIPS = new Relationship[] {
            Consumer.by(Component.of("firstConsumer", "firstType")),
            Consumer.by(Component.of("secondConsumer", "secondType")) };

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
        Set<Relationship> actual = detector.detect();
        for (Relationship r : actual) {
            Assertions.assertThat(r).isExactlyInstanceOf(Consumer.class);
        }
        Assertions.assertThat(actual).containsExactlyInAnyOrder(EXPECTED_RELATIONSHIPS);
    }

    @Configuration
    public static class RelationshipHintsConsumerTestConfig {

        @Bean
        public RelationshipHintsAnnotationRelationshipDetector detector() {
            return new RelationshipHintsAnnotationRelationshipDetector(
                    new DependencyHintAnnotationRelationshipDetector(),
                    new ConsumerHintAnnotationRelationshipDetector());
        }

        @Bean
        public RelationshipHintsWithConsumersClass relationsWithConsumers() {
            return new RelationshipHintsWithConsumersClass();
        }

    }

}
