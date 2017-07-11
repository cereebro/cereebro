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

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.snitch.detect.oauth2.OAuth2RelationshipDetectorAutoConfigurationEmptyTest.OAuth2RelationshipDetectorAutoConfigurationEmptyTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { OAuth2RelationshipDetectorAutoConfigurationEmptyTestApplication.class,
        OAuth2RelationshipDetectorAutoConfiguration.class })
@ActiveProfiles("nodb")
public class OAuth2RelationshipDetectorAutoConfigurationEmptyTest {

    @Autowired
    AuthorizationServerRelationshipDetector detector;

    @Test
    public void test() {
        Assertions.assertThat(detector.detect()).isEmpty();
    }

    @SpringBootApplication
    @EnableResourceServer
    static class OAuth2RelationshipDetectorAutoConfigurationEmptyTestApplication {

        @Bean
        public ResourceServerTokenServices mockTokenService() {
            return Mockito.mock(ResourceServerTokenServices.class);
        }

    }

}
