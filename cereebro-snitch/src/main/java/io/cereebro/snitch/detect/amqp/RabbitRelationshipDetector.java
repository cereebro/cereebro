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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.util.CollectionUtils;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;

/**
 * RabbitMQ relationship detector based on Spring's {@link ConnectionFactory}
 * abstraction.
 * 
 * @author michaeltecourt
 */
public class RabbitRelationshipDetector implements RelationshipDetector {

    private final List<ConnectionFactory> connectionFactories;

    /**
     * * RabbitMQ relationship detector based on Spring's
     * {@link ConnectionFactory} abstraction. Note that any component connected
     * to RabbitMQ will have a dependency on the broker : Both the "producer"
     * and "consumer" (MQ) applications have a dependency on RabbitMQ.
     * 
     * @param rabbitConnectionFactories
     *            All RabbitMQ connections available.
     */
    public RabbitRelationshipDetector(List<ConnectionFactory> rabbitConnectionFactories) {
        this.connectionFactories = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rabbitConnectionFactories)) {
            connectionFactories.addAll(rabbitConnectionFactories);
        }
    }

    @Override
    public Set<Relationship> detect() {
        if (connectionFactories.isEmpty()) {
            return new HashSet<>();
        }
        return connectionFactories.stream().map(factory -> createRabbitDependency(factory.getVirtualHost()))
                .collect(Collectors.toSet());
    }

    /**
     * Create a dependency on a RabbitMQ component with a given name.
     * 
     * @param name
     *            Component name.
     * @return Dependency on a component with RabbitMQ type.
     */
    private Dependency createRabbitDependency(String name) {
        return Dependency.on(Component.of(name, ComponentType.RABBITMQ));
    }

}
