package io.cereebro.spring.boot.autoconfigure.actuate;

import java.net.URI;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.springframework.boot.actuate.endpoint.mvc.AbstractMvcEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class CereebroSnitchMvcEndpoint extends AbstractMvcEndpoint implements Snitch {

    public static final String DEFAULT_PATH = "/cereebro/snitch";

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
    public CereebroSnitchMvcEndpoint(Component application, RelationshipDetector relationshipDetector) {
        super(DEFAULT_PATH, true, true);
        this.application = Objects.requireNonNull(application, "Application component required");
        this.relationshipDetector = Objects.requireNonNull(relationshipDetector, "Relationship detector required");
    }

    @Override
    public URI getUri() {
        return URI.create(getPath());
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Override
    public SystemFragment snitch() {
        Set<Relationship> relations = relationshipDetector.detect();
        ComponentRelationships cr = ComponentRelationships.of(application, relations);
        return SystemFragment.of(Collections.singleton(cr));
    }

}
