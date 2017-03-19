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
