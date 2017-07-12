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

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.Snitch;
import io.cereebro.snitch.CereebroSnitchAutoConfiguration;
import io.cereebro.snitch.actuate.SnitchEndpointPathOverrideTest.SnitchEndpointPathOverrideTestApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SnitchEndpointPathOverrideTestApplication.class,
        CereebroSnitchAutoConfiguration.class }, webEnvironment = WebEnvironment.RANDOM_PORT, value = {
                "spring.application.name=spring-app-name", "endpoints.cereebro.path=/cereebro/snitch/test",
                "management.security.enabled=false" })
@ActiveProfiles("nodb")
public class SnitchEndpointPathOverrideTest {

    @Value("http://localhost:${local.server.port}/cereebro/snitch/test")
    URI snitchURI;

    @Autowired
    private Snitch snitch;

    @Test
    public void snitchEndpointUrlShouldBeOverriden() {
        // @formatter:off
        RestAssured
            .given()
            .when()
                .get(snitchURI)
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("componentRelationships[0].component.name", Matchers.is("spring-app-name"));
        // @formatter:on
    }

    @Test
    public void snitchUriShouldMatchEndpointPathOverride() {
        Assertions.assertThat(snitch.getUri()).isEqualTo(URI.create("/cereebro/snitch/test"));
    }

    @SpringBootApplication(exclude = { RabbitAutoConfiguration.class })
    static class SnitchEndpointPathOverrideTestApplication {

    }

}
