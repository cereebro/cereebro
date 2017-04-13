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
package io.cereebro.spring.boot.autoconfigure.amqp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;

/**
 * {@link RabbitRelationshipDetector} unit tests.
 * 
 * @author michaeltecourt
 */
public class RabbitRelationshipDetectorTest {

    @Test
    public void nullConnectionsShouldReturnEmptyRelationships() {
        RabbitRelationshipDetector detector = new RabbitRelationshipDetector(null);
        Assertions.assertThat(detector.detect()).isEmpty();
    }

    @Test
    public void emptyConnectionsShouldReturnEmptyRelationships() {
        RabbitRelationshipDetector detector = new RabbitRelationshipDetector(new ArrayList<>());
        Assertions.assertThat(detector.detect()).isEmpty();
    }

    @Test
    public void connectionsShouldReturnDependenciesOnRabbitMqVhost() {
        ConnectionFactory factory = Mockito.mock(ConnectionFactory.class);
        Mockito.when(factory.getVirtualHost()).thenReturn("vhost");
        RabbitRelationshipDetector detector = new RabbitRelationshipDetector(Arrays.asList(factory));
        Dependency dependency = Dependency.on(Component.of("vhost", ComponentType.RABBITMQ));
        Assertions.assertThat(detector.detect()).isEqualTo(new HashSet<>(Arrays.asList(dependency)));
    }

}
