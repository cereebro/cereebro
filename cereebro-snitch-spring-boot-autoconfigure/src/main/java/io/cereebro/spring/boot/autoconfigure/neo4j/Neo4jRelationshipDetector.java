package io.cereebro.spring.boot.autoconfigure.neo4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neo4j.ogm.session.Session;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jProperties;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Sets;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;

public class Neo4jRelationshipDetector implements RelationshipDetector {

    private Neo4jProperties properties;

    private final List<Session> sessions;

    public Neo4jRelationshipDetector(Collection<Session> sessions, Neo4jProperties properties) {
        this.sessions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sessions)) {
            this.sessions.addAll(sessions);
        }
    }

    @Override
    public Set<Relationship> detect() {
        if (sessions.size() == 1 && !StringUtils.isEmpty(properties.getUri())) {
            return Sets.newHashSet(Dependency.on(Component.of(properties.getUri(), ComponentType.NEO4J)));
        }
        final Set<Relationship> result = new HashSet<>(sessions.size());
        for (int i = 0; i < sessions.size(); i++) {
            result.add(Dependency.on(Component.of("default_" + i, ComponentType.NEO4J)));
        }
        return result;
    }

}
