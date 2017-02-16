package io.cereebro.autoconfigure;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.cereebro.autoconfigure.annotation.ConsumerHintAnnotationRelationshipDetector;
import io.cereebro.autoconfigure.annotation.DependencyHintAnnotationRelationshipDetector;
import io.cereebro.autoconfigure.cassandra.CereebroCassandraRelationshipDetectorAutoConfiguration;
import io.cereebro.autoconfigure.datasource.DataSourceRelationshipDetector;
import io.cereebro.core.Component;
import io.cereebro.core.CompositeRelationshipDetector;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.snitch.spring.boot.actuate.endpoint.SnitchEndpointProperties;
import io.cereebro.snitch.spring.boot.actuate.endpoint.SnitchEndpoint;

/**
 * Autoconfiguration class used to instantiate {@link RelationshipDetector}s and
 * the {@link SnitchEndpoint}
 * 
 * @author lwarrot
 *
 */
@Configuration
@EnableConfigurationProperties({ CereebroProperties.class, SnitchEndpointProperties.class })
@Import({ CereebroCassandraRelationshipDetectorAutoConfiguration.class })
public class CereebroAutoConfiguration {

    @Autowired
    private CereebroProperties cereebroProperties;

    @Autowired
    private SnitchEndpointProperties snitchEndPointProperties;

    public RelationshipDetector compositeRelationshipDetector(Set<RelationshipDetector> detectors) {
        return new CompositeRelationshipDetector(detectors);
    }

    @Bean
    public SnitchEndpoint endpoint(Set<RelationshipDetector> detectors) {
        Component applicationComponent = cereebroProperties.getApplication().getComponent().toComponent();
        return new SnitchEndpoint(applicationComponent, compositeRelationshipDetector(detectors),
                snitchEndPointProperties);
    }

    @Bean
    public ConfigurationPropertiesRelationshipDetector configurationPropertiesRelationshipDetector() {
        return new ConfigurationPropertiesRelationshipDetector(cereebroProperties);
    }

    @Bean
    public DataSourceRelationshipDetector dataSourceRelationshipDetector() {
        return new DataSourceRelationshipDetector();
    }

    @Bean
    public DependencyHintAnnotationRelationshipDetector dependencyHintAnnotationRelationshipDetector() {
        return new DependencyHintAnnotationRelationshipDetector();
    }

    @Bean
    public ConsumerHintAnnotationRelationshipDetector consumerHintAnnotationRelationshipDetector() {
        return new ConsumerHintAnnotationRelationshipDetector();
    }

}
