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
package io.cereebro.snitch.detect.cassandra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.datastax.driver.core.Session;

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
        if (sessions != null) {
            this.sessions.addAll(sessions);
        }
    }

    @Override
    public Set<Relationship> detect() {
        final Set<Relationship> result = new HashSet<>();
        for (Session s : sessions) {
            result.add(Dependency.on(Component.of(extractName(s), ComponentType.CASSANDRA)));
        }
        return result;
    }

    /**
     * Extract a Cassandra Keyspace or Cluster name, using a default name when
     * none available.
     * 
     * @param session
     *            Cassandra Session.
     * @return a non-null Cassandra Component name.
     */
    protected String extractName(Session session) {
        String name = session.getLoggedKeyspace();
        if (name == null) {
            name = session.getCluster().getClusterName();
        }
        return Optional.ofNullable(name).orElse("default_cluster");
    }

}
