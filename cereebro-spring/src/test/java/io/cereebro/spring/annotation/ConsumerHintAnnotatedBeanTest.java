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
import io.cereebro.core.annotation.ConsumerHint;
import io.cereebro.spring.annotation.ConsumerHintAnnotatedBeanTest.ConsumerHintBeanFactoryMethodTestConfig;
import io.cereebro.spring.annotation.test.Dummy;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ConsumerHintBeanFactoryMethodTestConfig.class)
public class ConsumerHintAnnotatedBeanTest {

    @Autowired
    private ConsumerHintAnnotationRelationshipDetector detector;

    @Test
    public void test() {
        Set<Relationship> result = detector.detect();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Relationship actual = result.iterator().next();
        Consumer expected = Consumer.by(Component.of("cable", "future"));
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Configuration
    public static class ConsumerHintBeanFactoryMethodTestConfig {

        @Bean
        public ConsumerHintAnnotationRelationshipDetector detector() {
            return new ConsumerHintAnnotationRelationshipDetector();
        }

        @Bean
        @ConsumerHint(name = "cable", type = "future")
        public Dummy dummy() {
            return new Dummy();
        }

    }
}
