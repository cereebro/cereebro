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

    /**
     * Snitch actuator Endpoint. Tells everything it knows about the host Spring
     * Boot application and its dependencies.
     * 
     * @param application
     *            Component representing the host Spring Boot application.
     * @param relationshipDetector
     *            Detector providing all the application relationships.
     */
    public SnitchEndpoint(Component application, RelationshipDetector relationshipDetector) {
        this.application = Objects.requireNonNull(application, "Application component required");
        this.relationshipDetector = Objects.requireNonNull(relationshipDetector, "Relationship detector required");
    }

    @Override
    public String getId() {
        return "cereebro";
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isSensitive() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public SystemFragment invoke() {
        return snitch();
    }

    @Override
    public URI getLocation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SystemFragment snitch() {
        Set<Relationship> relations = relationshipDetector.detect();
        ComponentRelationships cr = ComponentRelationships.of(application, relations);
        return SystemFragment.of(Collections.singleton(cr));
    }

}
