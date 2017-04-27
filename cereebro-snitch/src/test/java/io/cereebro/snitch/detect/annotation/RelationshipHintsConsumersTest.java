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
package io.cereebro.snitch.detect.annotation;

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
import io.cereebro.snitch.detect.annotation.ConsumerHintAnnotationRelationshipDetector;
import io.cereebro.snitch.detect.annotation.DependencyHintAnnotationRelationshipDetector;
import io.cereebro.snitch.detect.annotation.RelationshipHintsAnnotationRelationshipDetector;
import io.cereebro.snitch.detect.annotation.RelationshipHintsConsumersTest.RelationshipHintsConsumerTestConfig;
import io.cereebro.snitch.detect.annotation.test.RelationshipHintsWithConsumersClass;

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
