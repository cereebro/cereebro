package io.cereebro.snitch.spring.boot.actuate.endpoint;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentRelationships;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.core.Snitch;
import io.cereebro.core.SystemFragment;

/**
 * TODO
 * 
 * @author michaeltecourt
 */
public class SnitchEndpoint implements Endpoint<SystemFragment>, Snitch, EnvironmentAware {

    private final RelationshipDetector relationshipDetector;

    private Environment environment;

    public SnitchEndpoint(RelationshipDetector relationshipDetector) {
        this.relationshipDetector = relationshipDetector;
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
        return false;
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
        ComponentRelationships cr = ComponentRelationships.of(
                Component.of(environment.getProperty("spring.application.name", "app"), "WEBAPP"),
                ComponentRelationships.filterDependencies(relations),
                ComponentRelationships.filterConsumers(relations));
        return SystemFragment.of(Collections.singleton(cr));
    }

    @Override
    public void setEnvironment(Environment env) {
        this.environment = env;
    }

}
