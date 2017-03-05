package io.cereebro.spring.boot.autoconfigure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.Component;
import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.annotation.ConsumerHint;
import io.cereebro.core.annotation.DependencyHint;
import io.cereebro.core.annotation.RelationshipHints;
import io.cereebro.spring.annotation.RelationshipHintsAnnotationRelationshipDetector;
import io.cereebro.spring.boot.autoconfigure.RelationshipHintsTest.RelationshipHintsApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RelationshipHintsApplication.class)
public class RelationshipHintsTest {

    @Autowired
    private RelationshipHintsAnnotationRelationshipDetector detector;

    @Test
    public void test() {
        Set<Relationship> result = detector.detect();
        Dependency d1 = Dependency.on(Component.of("d1", "d1"));
        Dependency d2 = Dependency.on(Component.of("d2", "d2"));
        Dependency d3 = Dependency.on(Component.of("d3", "d3"));
        Consumer c1 = Consumer.by(Component.of("c1", "c1"));
        Consumer c2 = Consumer.by(Component.of("c2", "c2"));
        Consumer c3 = Consumer.by(Component.of("c3", "c3"));
        Set<Relationship> expected = new HashSet<>(Arrays.asList(d1, d2, d3, c1, c2, c3));
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @SpringBootApplication
    static class RelationshipHintsApplication {

        @RelationshipHints(consumers = { @ConsumerHint(name = "c1", type = "c1"),
                @ConsumerHint(name = "c2", type = "c2") })
        @org.springframework.stereotype.Component
        public static class RelationshipHintsWithConsumers {

        }

        @RelationshipHints(dependencies = { @DependencyHint(name = "d1", type = "d1"),
                @DependencyHint(name = "d2", type = "d2") })
        @org.springframework.stereotype.Component
        public static class RelationshipHintsWithDependencies {

        }

        @RelationshipHints(dependencies = { @DependencyHint(name = "d3", type = "d3") }, consumers = {
                @ConsumerHint(name = "c3", type = "c3") })
        @org.springframework.stereotype.Component
        public static class RelationshipHintsWithAll {

        }

    }

}
