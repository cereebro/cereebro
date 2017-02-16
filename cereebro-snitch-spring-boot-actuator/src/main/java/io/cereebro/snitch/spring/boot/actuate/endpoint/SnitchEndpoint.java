package io.cereebro.snitch.spring.boot.actuate.endpoint;

import java.net.URI;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.springframework.boot.actuate.endpoint.Endpoint;

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
public class SnitchEndpoint implements Endpoint<SystemFragment>, Snitch {

    private final Component application;
    private final RelationshipDetector relationshipDetector;
    private final SnitchEndpointProperties properties;

    /**
     * Snitch actuator Endpoint. Tells everything it knows about the host Spring
     * Boot application and its dependencies.
     * 
     * @param application Component representing the host Spring Boot
     *            application.
     * @param relationshipDetector Detector providing all the application
     *            relationships.
     */
    public SnitchEndpoint(Component application, RelationshipDetector relationshipDetector,
            SnitchEndpointProperties properties) {
        this.application = Objects.requireNonNull(application, "Application component required");
        this.relationshipDetector = Objects.requireNonNull(relationshipDetector, "Relationship detector required");
        this.properties = Objects.requireNonNull(properties, "Endpoint properties required");
    }

    @Override
    public String getId() {
        return this.properties.getId();
    }

    @Override
    public boolean isEnabled() {
        return this.properties.isEnabled();
    }

    @Override
    public boolean isSensitive() {
        return this.properties.isSensitive();
    }

    @Override
    public SystemFragment invoke() {
        return snitch();
    }

    @Override
    public URI getLocation() {
        return URI.create(String.format("http://%s:%s%s/%s", properties.getHost(), properties.getPort(),
                properties.getContext(), properties.getId()));
    }

    @Override
    public SystemFragment snitch() {
        Set<Relationship> relations = relationshipDetector.detect();
        ComponentRelationships cr = ComponentRelationships.of(application, relations);
        return SystemFragment.of(Collections.singleton(cr));
    }

}
