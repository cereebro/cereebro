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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.snitch.detect.oauth2.AuthorizationServerRelationshipDetector;

/**
 * {@link AuthorizationServerRelationshipDetector} unit tests.
 * 
 * @author michaeltecourt
 */
public class AuthorizationServerRelationshipDetectorTest {

    private AuthorizationServerRelationshipDetector detector;

    @Test
    public void usingRemoteTokenServicesShouldReturnDependency() {
        ResourceServerTokenServices tokenService = new RemoteTokenServices();
        detector = new AuthorizationServerRelationshipDetector(tokenService);
        Set<Relationship> expected = new HashSet<>(Arrays
                .asList(Dependency.on(Component.of("oauth2-authorization-server", ComponentType.HTTP_APPLICATION))));
        Set<Relationship> result = detector.detect();
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void usingRemoteTokenServicesWithCustomName() {
        ResourceServerTokenServices tokenService = new RemoteTokenServices();
        detector = new AuthorizationServerRelationshipDetector(tokenService);
        String customName = "authz";
        detector.setDefaultName(customName);
        Set<Relationship> expected = new HashSet<>(
                Arrays.asList(Dependency.on(Component.of(customName, ComponentType.HTTP_APPLICATION))));
        Set<Relationship> result = detector.detect();
        Assertions.assertThat(result).isEqualTo(expected);

    }

    @Test
    public void usingUserInfoTokenServicesShouldReturnDependency() {
        ResourceServerTokenServices tokenService = new UserInfoTokenServices("/info", "nope");
        detector = new AuthorizationServerRelationshipDetector(tokenService);
        Set<Relationship> expected = new HashSet<>(Arrays
                .asList(Dependency.on(Component.of("oauth2-authorization-server", ComponentType.HTTP_APPLICATION))));
        Set<Relationship> result = detector.detect();
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void nullTokenServicesShouldReturnEmptyRelationships() {
        detector = new AuthorizationServerRelationshipDetector(null);
        Assertions.assertThat(detector.detect()).isEmpty();
    }

}
