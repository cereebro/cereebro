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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.Relationship;
import io.cereebro.spring.annotation.DependencyHintAnnotationClassRelationshipDetectorTest.AnnotatedClassDependencyTestConfig;
import io.cereebro.spring.annotation.test.DummyAnnotatedDependencyClass;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AnnotatedClassDependencyTestConfig.class)
public class DependencyHintAnnotationClassRelationshipDetectorTest {

    @Autowired
    private DependencyHintAnnotationRelationshipDetector detector;

    @Test
    public void annotationRelationshipWithDeclaredBean() {
        Set<Relationship> relations = detector.detect();
        Assertions.assertThat(relations).isNotEmpty();
        Assertions.assertThat(relations).hasSize(1);
        Relationship dummyRelation = relations.iterator().next();
        Assertions.assertThat(dummyRelation.getComponent().getName()).isEqualTo("dummyClass");
        Assertions.assertThat(dummyRelation.getComponent().getType()).isEqualTo("dummyWs");
    }

    @Configuration
    @Import(DummyAnnotatedDependencyClass.class)
    public static class AnnotatedClassDependencyTestConfig {

        @Bean
        public DependencyHintAnnotationRelationshipDetector detector() {
            return new DependencyHintAnnotationRelationshipDetector();
        }

    }

}
