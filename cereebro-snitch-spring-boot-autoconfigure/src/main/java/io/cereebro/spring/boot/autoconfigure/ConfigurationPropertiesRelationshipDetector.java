package io.cereebro.spring.boot.autoconfigure;

import java.util.Objects;
import java.util.Set;

import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;

/**
 * Detect the application's relationships declared in Cereebro's Spring Boot
 * ConfigurationProperties.
 * 
 * @author michaeltecourt
 */
public class ConfigurationPropertiesRelationshipDetector implements RelationshipDetector {

    private final CereebroProperties properties;

    /**
     * Detect the application's relationships declared in Cereebro's Spring Boot
     * ConfigurationProperties.
     * 
     * @param properties
     *            Cereebro properties that will be used to detect relationships.
     */
    public ConfigurationPropertiesRelationshipDetector(CereebroProperties properties) {
        this.properties = Objects.requireNonNull(properties, "Cereebro properties required");
    }

    @Override
    public Set<Relationship> detect() {
        return properties.getApplication().getRelationships();
    }

}
