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
package io.cereebro.snitch.detect.neo4j;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.snitch.detect.neo4j.Neo4jRelationshipDetectorAutoConfigurationDefaultNameTest.Neo4jRelationshipDetectorAutoConfigurationTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Neo4jRelationshipDetectorAutoConfigurationTestApplication.class, properties = "cereebro.snitch.detect.neo4j.default-name=my-neo4j")
public class Neo4jRelationshipDetectorAutoConfigurationDefaultNameTest {

    @Autowired
    Neo4jRelationshipDetector detector;

    @Test
    public void shouldReturnDependencyOnDefaultNeo4jComponent() {
        Set<Relationship> rels = Dependency.on(Component.of("my-neo4j", ComponentType.NEO4J)).asRelationshipSet();
        Assertions.assertThat(detector.detect()).isEqualTo(rels);
    }

    @SpringBootApplication
    static class Neo4jRelationshipDetectorAutoConfigurationTestApplication {

        @Bean
        Session session() {
            return Mockito.mock(Session.class);
        }

    }

}
