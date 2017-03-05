package io.cereebro.spring.boot.autoconfigure.cassandra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;

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
        if (!CollectionUtils.isEmpty(sessions)) {
            this.sessions.addAll(sessions);
        }
    }

    @Override
    public Set<Relationship> detect() {
        final Set<Relationship> result = new HashSet<>();
        for (Session s : sessions) {
            result.add(Dependency.on(Component.of(s.getLoggedKeyspace(), ComponentType.CASSANDRA)));
        }
        return result;
    }

}
