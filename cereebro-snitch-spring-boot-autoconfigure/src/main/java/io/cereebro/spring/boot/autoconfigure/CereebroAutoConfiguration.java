package io.cereebro.spring.boot.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.cereebro.core.RelationshipDetector;
import io.cereebro.spring.annotation.ConsumerHintAnnotationRelationshipDetector;
import io.cereebro.spring.annotation.DependencyHintAnnotationRelationshipDetector;
import io.cereebro.spring.annotation.RelationshipHintsAnnotationRelationshipDetector;
import io.cereebro.spring.boot.autoconfigure.cassandra.CassandraRelationshipDetectorAutoConfiguration;
import io.cereebro.spring.boot.autoconfigure.jdbc.DataSourceRelationshipDetectorAutoConfiguration;

/**
 * Configures various {@link RelationshipDetector}s.
 * 
 * @author lwarrot
 */
@Configuration
@EnableConfigurationProperties({ CereebroProperties.class })
@Import({ CassandraRelationshipDetectorAutoConfiguration.class, DataSourceRelationshipDetectorAutoConfiguration.class })
public class CereebroAutoConfiguration {

    @Autowired
    private CereebroProperties cereebroProperties;

    @Bean
    public ConfigurationPropertiesRelationshipDetector configurationPropertiesRelationshipDetector() {
        return new ConfigurationPropertiesRelationshipDetector(cereebroProperties);
    }

    @Bean
    public DependencyHintAnnotationRelationshipDetector dependencyHintAnnotationRelationshipDetector() {
        return new DependencyHintAnnotationRelationshipDetector();
    }

    @Bean
    public ConsumerHintAnnotationRelationshipDetector consumerHintAnnotationRelationshipDetector() {
        return new ConsumerHintAnnotationRelationshipDetector();
    }

    @Bean
    public RelationshipHintsAnnotationRelationshipDetector relationshipHintsDetector() {
        return new RelationshipHintsAnnotationRelationshipDetector(dependencyHintAnnotationRelationshipDetector(),
                consumerHintAnnotationRelationshipDetector());
    }

}
