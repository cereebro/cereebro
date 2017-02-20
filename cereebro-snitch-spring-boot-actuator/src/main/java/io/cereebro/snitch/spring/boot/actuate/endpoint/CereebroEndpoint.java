package io.cereebro.snitch.spring.boot.actuate.endpoint;

import java.net.URI;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentRelationships;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.core.Snitch;
import io.cereebro.core.SystemFragment;

/**
 * Snitch actuator Endpoint. Tells everything it knows about the host Spring
 * Boot application and its dependencies.
 * 
 * @author lucwarrot
 * @author michaeltecourt
 */
@ConfigurationProperties(prefix = "endpoints.cereebro")
public class CereebroEndpoint extends AbstractEndpoint<SystemFragment> implements Snitch {

    private final Component application;
    private final RelationshipDetector relationshipDetector;

    /**
     * Snitch actuator Endpoint. Tells everything it knows about the host Spring
     * Boot application and its dependencies.
     * 
     * @param application Component representing the host Spring Boot
     *            application.
     * @param relationshipDetector Detector providing all the application
     *            relationships.
     */
    public CereebroEndpoint(Component application, RelationshipDetector relationshipDetector) {
        super("cereebro");
        this.application = Objects.requireNonNull(application, "Application component required");
        this.relationshipDetector = Objects.requireNonNull(relationshipDetector, "Relationship detector required");
    }

    @Override
    public SystemFragment invoke() {
        return snitch();
    }

    @Override
    public URI getLocation() {
        return URI.create(String.format("/%s", this.getId()));
    }

    @Override
    public SystemFragment snitch() {
        Set<Relationship> relations = relationshipDetector.detect();
        ComponentRelationships cr = ComponentRelationships.of(application, relations);
        return SystemFragment.of(Collections.singleton(cr));
    }

}
