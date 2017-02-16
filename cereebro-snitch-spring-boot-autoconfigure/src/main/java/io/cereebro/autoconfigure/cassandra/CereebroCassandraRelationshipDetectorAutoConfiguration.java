package io.cereebro.autoconfigure.cassandra;

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
public class CereebroCassandraRelationshipDetectorAutoConfiguration {

    @Bean
    public CassandraRelationshipDetector cassandraRelationshipDetector() {
        return new CassandraRelationshipDetector();
    }

}
