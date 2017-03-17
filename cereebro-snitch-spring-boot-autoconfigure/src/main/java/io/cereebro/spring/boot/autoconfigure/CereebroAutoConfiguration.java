package io.cereebro.spring.boot.autoconfigure;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.cereebro.core.Component;
import io.cereebro.core.CompositeRelationshipDetector;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.snitch.spring.boot.actuate.endpoint.CereebroSnitchEndpoint;
import io.cereebro.spring.annotation.ConsumerHintAnnotationRelationshipDetector;
import io.cereebro.spring.annotation.DependencyHintAnnotationRelationshipDetector;
import io.cereebro.spring.annotation.RelationshipHintsAnnotationRelationshipDetector;
import io.cereebro.spring.boot.autoconfigure.cassandra.CassandraRelationshipDetectorAutoConfiguration;
import io.cereebro.spring.boot.autoconfigure.jdbc.DataSourceRelationshipDetectorAutoConfiguration;

/**
 * Autoconfiguration class used to instantiate {@link RelationshipDetector}s and
 * the {@link CereebroSnitchEndpoint}
 * 
 * @author lwarrot
 *
 */
@Configuration
@EnableConfigurationProperties({ CereebroProperties.class })
@Import({ CassandraRelationshipDetectorAutoConfiguration.class,
        DataSourceRelationshipDetectorAutoConfiguration.class })
public class CereebroAutoConfiguration {

    @Autowired
    private CereebroProperties cereebroProperties;

    public RelationshipDetector compositeRelationshipDetector(Set<RelationshipDetector> detectors) {
        return new CompositeRelationshipDetector(detectors);
    }

    @Bean
    public CereebroSnitchEndpoint cereebroEndpoint(Set<RelationshipDetector> detectors) {
        Component applicationComponent = cereebroProperties.getApplication().getComponent().toComponent();
        return new CereebroSnitchEndpoint(applicationComponent, compositeRelationshipDetector(detectors));
    }

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
