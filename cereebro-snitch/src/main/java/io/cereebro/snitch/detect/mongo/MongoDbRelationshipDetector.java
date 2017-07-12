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
package io.cereebro.snitch.detect.mongo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mongodb.MongoClient;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;

/**
 * Implementation of {@link RelationshipDetector} used to detect a MongoDB
 * dependency. The class retrieve all {@link MongoClient} available in the
 * Spring context and create {@link Relationship}s.
 * 
 * @author lwarrot
 *
 */
public class MongoDbRelationshipDetector implements RelationshipDetector {

    private List<MongoClient> clients;

    public MongoDbRelationshipDetector(Collection<MongoClient> clients) {
        this.clients = new ArrayList<>();
        if (clients != null) {
            this.clients.addAll(clients);
        }
    }

    @Override
    public Set<Relationship> detect() {
        Set<Relationship> result = new HashSet<>();
        for (MongoClient client : clients) {
            for (String db : client.listDatabaseNames()) {
                result.add(Dependency.on(Component.of(db, ComponentType.MONGODB)));
            }
        }
        return result;
    }

}
