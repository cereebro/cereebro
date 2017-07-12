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
package io.cereebro.snitch.detect.elastic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.elasticsearch.client.Client;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;

/**
 * Implementation of {@link RelationshipDetector} used to detect an Elastic
 * Search dependency. The class retrieve all {@link Client} available in the
 * Spring context and create {@link Relationship}s.
 * 
 * @author lwarrot
 *
 */
public class ElasticSearchRelationshipDetector implements RelationshipDetector {

    private final List<Client> clients;

    public ElasticSearchRelationshipDetector(Collection<Client> esClients) {
        this.clients = new ArrayList<>();
        if (esClients != null) {
            this.clients.addAll(esClients);
        }
    }

    @Override
    public Set<Relationship> detect() {
        return clients.stream()
                .map(c -> Dependency.on(Component.of(c.settings().get("cluster.name"), ComponentType.ELASTIC_SEARCH)))
                .collect(Collectors.toSet());
    }

}
