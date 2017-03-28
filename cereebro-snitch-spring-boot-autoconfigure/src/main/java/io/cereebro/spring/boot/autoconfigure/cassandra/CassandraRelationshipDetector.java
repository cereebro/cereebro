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
package io.cereebro.spring.boot.autoconfigure.cassandra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.datastax.driver.core.Session;
import com.google.common.base.Optional;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;

/**
 * Implementation of {@link RelationshipDetector} used to detect all Cassandra
 * dependencies. The class retrieves all {@link Session} available in the
 * context and create a {@link Relationship}
 * 
 * @author lwarrot
 *
 */
public class CassandraRelationshipDetector implements RelationshipDetector {

    private List<Session> sessions;

    public CassandraRelationshipDetector(Collection<Session> sessions) {
        this.sessions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sessions)) {
            this.sessions.addAll(sessions);
        }
    }

    @Override
    public Set<Relationship> detect() {
        final Set<Relationship> result = new HashSet<>();
        for (Session s : sessions) {
            result.add(Dependency.on(Component.of(Optional.fromNullable(s.getLoggedKeyspace()).or("no_keyspace"), ComponentType.CASSANDRA)));
        }
        return result;
    }

}
