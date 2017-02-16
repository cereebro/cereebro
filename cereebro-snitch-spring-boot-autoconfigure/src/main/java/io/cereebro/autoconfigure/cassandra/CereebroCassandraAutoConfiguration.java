package io.cereebro.autoconfigure.cassandra;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.Session;

@Configuration
@ConditionalOnClass(Session.class)
public class CereebroCassandraAutoConfiguration {

    @Bean
    public CassandraRelationshipDetector cassandraRelationshipDetector() {
        return new CassandraRelationshipDetector();
    }

}
