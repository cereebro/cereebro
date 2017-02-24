package io.cereebro.autoconfigure.cassandra;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired(required = false)
    private Set<Session> sessions;

    @Override
    public Set<Relationship> detect() {
        final Set<Relationship> result = new HashSet<>();
        if (sessions != null) {
            for (Session s : sessions) {
                result.add(Dependency.on(Component.of(s.getLoggedKeyspace(), ComponentType.CASSANDRA)));
            }
        }
        return result;
    }

}
