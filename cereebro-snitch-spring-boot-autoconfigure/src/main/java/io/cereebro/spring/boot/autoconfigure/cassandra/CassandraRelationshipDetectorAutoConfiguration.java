package io.cereebro.spring.boot.autoconfigure.cassandra;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.Session;

/**
 * Cassandra detector auto configuration, depending on Cassandra classes being
 * available on the classpath. Requires a separate configuration class to
 * prevent issues with optional classes.
 * 
 * @author michaeltecourt
 */
@Configuration
@ConditionalOnClass(Session.class)
public class CassandraRelationshipDetectorAutoConfiguration {

    @Autowired(required = false)
    private List<Session> sessions;

    @Bean
    public CassandraRelationshipDetector cassandraRelationshipDetector() {
        return new CassandraRelationshipDetector(sessions);
    }

}
