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
package io.cereebro.snitch.detect.eureka;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.CollectionUtils;

import com.netflix.discovery.EurekaClient;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.snitch.detect.Detectors;
import lombok.Getter;
import lombok.Setter;

/**
 * Eureka Server relationship detector. If some {@link EurekaClient} bean is
 * available in the context, we'll just create one relationship to a Eureka
 * Server component with a default name.
 * 
 * @author michaeltecourt
 *
 */
@ConfigurationProperties(prefix = Detectors.PREFIX + ".eureka")
public class EurekaServerRelationshipDetector implements RelationshipDetector {

    @Getter
    @Setter
    private String defaultName = "eureka-server";

    private final List<EurekaClient> eurekaClients;

    /**
     * Eureka Server relationship detector.
     * 
     * @param eurekaClients
     *            Eureka Client instances available (nullable).
     */
    public EurekaServerRelationshipDetector(List<EurekaClient> eurekaClients) {
        this.eurekaClients = new ArrayList<>();
        if (!CollectionUtils.isEmpty(eurekaClients)) {
            this.eurekaClients.addAll(eurekaClients);
        }
    }

    @Override
    public Set<Relationship> detect() {
        if (eurekaClients.isEmpty()) {
            return new HashSet<>();
        }
        return Dependency.on(Component.of(defaultName, ComponentType.HTTP_APPLICATION_REGISTRY)).asRelationshipSet();
    }

}
