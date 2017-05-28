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
package io.cereebro.snitch.detect.oauth2;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.snitch.detect.Detectors;
import lombok.Getter;
import lombok.Setter;

/**
 * Detects a relationship to an OAuth 2.0 authorization server from a resource
 * server.
 * 
 * @author michaeltecourt
 */
@ConfigurationProperties(prefix = Detectors.PREFIX + ".oauth2.authorization-server")
public class AuthorizationServerRelationshipDetector implements RelationshipDetector {

    private final ResourceServerTokenServices tokenService;

    @Getter
    @Setter
    private String defaultName = "oauth2-authorization-server";

    public AuthorizationServerRelationshipDetector(ResourceServerTokenServices tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Set<Relationship> detect() {
        if (tokenService instanceof RemoteTokenServices || tokenService instanceof UserInfoTokenServices) {
            return Dependency.on(Component.of(getDefaultName(), ComponentType.HTTP_APPLICATION)).asRelationshipSet();
        }
        return new HashSet<>();
    }

}
