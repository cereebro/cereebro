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

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.snitch.detect.oauth2.AuthorizationServerRelationshipDetector;
import io.cereebro.snitch.detect.oauth2.OAuth2RelationshipDetectorAutoConfiguration;
import io.cereebro.snitch.detect.oauth2.OAuth2RelationshipDetectorAutoConfigurationTokenInfoTest.OAuth2RelationshipDetectorAutoConfigurationTokenInfoTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { OAuth2RelationshipDetectorAutoConfigurationTokenInfoTestApplication.class,
        OAuth2RelationshipDetectorAutoConfiguration.class })
@ActiveProfiles("oauth2-token-info")
public class OAuth2RelationshipDetectorAutoConfigurationTokenInfoTest {

    @Autowired
    AuthorizationServerRelationshipDetector detector;

    @Test
    public void test() {
        Set<Relationship> expected = Dependency
                .on(Component.of("oauth2-authorization-server", ComponentType.HTTP_APPLICATION)).asRelationshipSet();
        Assertions.assertThat(detector.detect()).isEqualTo(expected);
    }

    @SpringBootApplication(exclude = MongoAutoConfiguration.class)
    @EnableResourceServer
    static class OAuth2RelationshipDetectorAutoConfigurationTokenInfoTestApplication {

    }
}
