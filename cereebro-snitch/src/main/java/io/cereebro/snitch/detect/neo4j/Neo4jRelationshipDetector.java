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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neo4j.ogm.session.Session;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.CollectionUtils;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.snitch.ConditionalOnPropertyDetector;
import lombok.Getter;
import lombok.Setter;

/**
 * Neo4J database relationship detector. Databases don't have names in Neo4j so
 * we use a default value whenever at least one Neo4j {@link Session} is
 * available.
 * 
 * @author lucwarrot
 * @author michaeltecourt
 */
@ConfigurationProperties(prefix = ConditionalOnPropertyDetector.DETECTOR_PREFIX + ".neo4j")
public class Neo4jRelationshipDetector implements RelationshipDetector {

    @Getter
    @Setter
    private String defaultName = "default";

    private final List<Session> sessions;

    /**
     * Neo4J database relationship detector. Databases don't have names in Neo4j
     * so we use a default value whenever at least one Neo4j {@link Session} is
     * available.
     * 
     * @param sessions
     *            Neo4j sessions available in the application context.
     */
    public Neo4jRelationshipDetector(Collection<Session> sessions) {
        this.sessions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sessions)) {
            this.sessions.addAll(sessions);
        }
    }

    @Override
    public Set<Relationship> detect() {
        return sessions.isEmpty() ? new HashSet<>()
                : Dependency.on(Component.of(defaultName, ComponentType.NEO4J)).asRelationshipSet();
    }

}
