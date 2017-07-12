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

import io.cereebro.snitch.detect.amqp.RabbitRelationshipDetectorAutoConfigurationDisableTest.RabbitRelationshipDetectorAutoConfigurationApplication;

/**
 * {@link RabbitRelationshipDetectorAutoConfiguration} Spring Boot test.
 * 
 * @author michaeltecourt
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitRelationshipDetectorAutoConfigurationApplication.class, properties = {
        "cereebro.snitch.detect.rabbit.enabled=false" })
@ActiveProfiles("nodb")
public class RabbitRelationshipDetectorAutoConfigurationDisableTest {

    @Autowired(required = false)
    RabbitRelationshipDetector detector;

    @Test
    public void connectionsShouldReturnDependenciesOnRabbitMqVhost() {
        Assertions.assertThat(detector).isNull();
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
