/*
 * Copyright © 2017 the original authors (http://cereebro.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
