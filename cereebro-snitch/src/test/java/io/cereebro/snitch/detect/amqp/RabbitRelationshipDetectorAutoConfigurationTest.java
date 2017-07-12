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
package io.cereebro.snitch.detect.amqp;

import java.util.Arrays;
import java.util.HashSet;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.snitch.detect.amqp.RabbitRelationshipDetectorAutoConfigurationTest.RabbitRelationshipDetectorAutoConfigurationApplication;

/**
 * {@link RabbitRelationshipDetectorAutoConfiguration} Spring Boot test.
 * 
 * @author michaeltecourt
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitRelationshipDetectorAutoConfigurationApplication.class)
@ActiveProfiles("nodb")
public class RabbitRelationshipDetectorAutoConfigurationTest {

    @Autowired
    RabbitRelationshipDetector detector;

    @Test
    public void connectionsShouldReturnDependenciesOnRabbitMqVhost() {
        Dependency dependency = Dependency.on(Component.of("vhost", ComponentType.RABBITMQ));
        Assertions.assertThat(detector.detect()).isEqualTo(new HashSet<>(Arrays.asList(dependency)));
    }

    @SpringBootApplication
    static class RabbitRelationshipDetectorAutoConfigurationApplication {

        @Bean
        public ConnectionFactory connectionFactoryMock() {
            ConnectionFactory factoryMock = Mockito.mock(ConnectionFactory.class);
            Mockito.when(factoryMock.getVirtualHost()).thenReturn("vhost");
            return factoryMock;
        }

    }

}
