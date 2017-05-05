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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.snitch.detect.neo4j.Neo4jRelationshipDetector;
import io.cereebro.snitch.detect.neo4j.Neo4jRelationshipDetectorAutoConfigurationTest.Neo4jRelationshipDetectorAutoConfigurationTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Neo4jRelationshipDetectorAutoConfigurationTestApplication.class)
public class Neo4jRelationshipDetectorAutoConfigurationTest {

    @Autowired
    Neo4jRelationshipDetector detector;

    @Test
    public void shouldReturnDependencyOnDefaultNeo4jComponent() {
        detector.detect();
    }

    @SpringBootApplication(exclude = { MongoAutoConfiguration.class, RabbitAutoConfiguration.class })
    static class Neo4jRelationshipDetectorAutoConfigurationTestApplication {

        @Bean
        Session session() {
            return Mockito.mock(Session.class);
        }

    }

}
