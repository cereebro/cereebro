package io.cereebro.spring.boot.autoconfigure.neo4j;

import java.util.List;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(value = { Session.class })
public class Neo4jRelationshipDetectorAutoConfiguration {

    @Autowired(required = false)
    private List<Session> neo4jSessions;

    @Autowired(required = false)
    private Neo4jProperties neo4jProperties;

    public Neo4jRelationshipDetector neo4jDetector() {
        return new Neo4jRelationshipDetector(neo4jSessions, neo4jProperties);
    }
}
