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
package io.cereebro.snitch.detect.ldap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.ldap.core.ContextSource;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;
import lombok.Getter;
import lombok.Setter;

/**
 * LDAP Relationship detector.
 * 
 * <p>
 * When one or multiple {@link ContextSource} beans are found in the application
 * context, a single LDAP dependency will be produced with a default name.
 * </p>
 * 
 * @author michaeltecourt
 *
 */
public class LdapRelationshipDetector implements RelationshipDetector {

    private final List<ContextSource> contextSources;

    @Getter
    @Setter
    private String defaultName = "default";

    public LdapRelationshipDetector(List<ContextSource> sources) {
        this.contextSources = new ArrayList<>();
        if (sources != null) {
            contextSources.addAll(sources);
        }
    }

    @Override
    public Set<Relationship> detect() {
        if (contextSources.isEmpty()) {
            return Collections.emptySet();
        }
        return Dependency.on(Component.of(defaultName, ComponentType.LDAP)).asRelationshipSet();
    }

}
