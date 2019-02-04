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
package io.cereebro.snitch.actuate;

import java.net.URI;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.snitch.CereebroSnitchAutoConfiguration;
import io.cereebro.snitch.actuate.SnitchEndpointConfigurationPropertiesDetectorTest.SnitchEndpointConfigurationPropertiesDetectorTestApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SnitchEndpointConfigurationPropertiesDetectorTestApplication.class,
        CereebroSnitchAutoConfiguration.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "props-detector", "nodb" })
public class SnitchEndpointConfigurationPropertiesDetectorTest {

    @Value("http://localhost:${local.server.port}/actuator/cereebro/snitch")
    URI snitchURI;

    @Test
    public void testPropertiesRelationships() {
        // @formatter:off
        RestAssured
            .given()
            .when()
                .get(snitchURI)
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("componentRelationships[0].component.name", Matchers.is("app-props-detector"))
                .body("componentRelationships[0].component.type", Matchers.is("properties/application"))
                .body("componentRelationships[0].dependencies", Matchers.hasSize(1))
                .body("componentRelationships[0].dependencies[0].component.name", Matchers.is("dependency"))
                .body("componentRelationships[0].dependencies[0].component.type", Matchers.is("properties/dependency"))
                .body("componentRelationships[0].consumers", Matchers.hasSize(1))
                .body("componentRelationships[0].consumers[0].component.name", Matchers.is("consumer"))
                .body("componentRelationships[0].consumers[0].component.type", Matchers.is("properties/consumer"));
        // @formatter:on
    }

    @SpringBootApplication(exclude = { RabbitAutoConfiguration.class, LdapAutoConfiguration.class, SecurityAutoConfiguration.class })
    static class SnitchEndpointConfigurationPropertiesDetectorTestApplication {

    }

}
