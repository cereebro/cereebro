package io.cereebro.spring.boot.autoconfigure.neo4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Sets;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;

public class Neo4jRelationshipDetector implements RelationshipDetector {

    @Value("spring.data.neo4j.uri:")
    private String url;

    private final List<Session> sessions;

    public Neo4jRelationshipDetector(Collection<Session> sessions) {
        this.sessions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sessions)) {
            this.sessions.addAll(sessions);
        }
    }

    @Override
    public Set<Relationship> detect() {
        if (sessions.size() == 1 && !StringUtils.isEmpty(url)) {
            return Sets.newHashSet(Dependency.on(Component.of(url, ComponentType.NEO4J)));
        }
        int idx = 0;
        Set<Relationship> result = new HashSet<>(sessions.size());
        for (Session s : sessions) {
            result.add(Dependency.on(Component.of("default_" + idx, ComponentType.NEO4J)));
        }
        return result;
    }

}
