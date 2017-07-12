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

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.ContextSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.snitch.detect.ldap.LdapRelationshipDetectorAutoConfigurationDisabledTest.LdapRelationshipDetectorAutoConfigurationDisabledTestApplication;

/**
 * Spring Boot auto configures a LDAP connection to a default URL, so a
 * {@link ContextSource} will be available.
 * 
 * @author michaeltecourt
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LdapRelationshipDetectorAutoConfigurationDisabledTestApplication.class, value = {
        "cereebro.snitch.detect.ldap.enabled=false" })
@ActiveProfiles("nodb")
public class LdapRelationshipDetectorAutoConfigurationDisabledTest {

    @Autowired(required = false)
    LdapRelationshipDetector detector;

    @Test
    public void ldapDetectorShouldNotBeConfigured() {
        Assertions.assertThat(detector).isNull();
    }

    @SpringBootApplication
    static class LdapRelationshipDetectorAutoConfigurationDisabledTestApplication {

    }

}
