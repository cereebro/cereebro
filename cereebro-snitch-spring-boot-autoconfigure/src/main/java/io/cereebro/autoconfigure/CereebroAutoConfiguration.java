package io.cereebro.autoconfigure;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.Session;

import io.cereebro.autoconfigure.annotation.DependencyHintAnnotationRelationshipDetector;
import io.cereebro.autoconfigure.cassandra.CassandraRelationshipDetector;
import io.cereebro.autoconfigure.datasource.DataSourceRelationshipDetector;
import io.cereebro.core.CompositeRelationshipDetector;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.snitch.spring.boot.actuate.endpoint.SnitchEndpoint;

/**
 * Autoconfiguration class used to instantiate {@link RelationshipDetector}s and
 * the {@link SnitchEndpoint}
 * 
 * @author lwarrot
 *
 */
@Configuration
@EnableConfigurationProperties(CereebroProperties.class)
public class CereebroAutoConfiguration {

    @Autowired
    private CereebroProperties cereebroProperties;

    @Bean
    public ConfigurationPropertiesRelationshipDetector configurationPropertiesRelationshipDetector() {
        return new ConfigurationPropertiesRelationshipDetector(cereebroProperties);
    }

    public RelationshipDetector compositeRelationshipDetector(Set<RelationshipDetector> detectors) {
        return new CompositeRelationshipDetector(detectors);
    }

    @Bean
    public SnitchEndpoint endpoint(Set<RelationshipDetector> detectors) {
        return new SnitchEndpoint(compositeRelationshipDetector(detectors));
    }

    @Bean
    @ConditionalOnClass(Session.class)
    public CassandraRelationshipDetector cassandraRelationshipDetector() {
        return new CassandraRelationshipDetector();
    }

    @Bean
    public DataSourceRelationshipDetector dataSourceRelationshipDetector() {
        return new DataSourceRelationshipDetector();
    }

    @Bean
    public DependencyHintAnnotationRelationshipDetector dependencyHintAnnotationRelationshipDetector() {
        return new DependencyHintAnnotationRelationshipDetector();
    }

}
