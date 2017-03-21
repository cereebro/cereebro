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
package io.cereebro.spring.cloud.autoconfigure.eureka;

import java.net.URI;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.server.EnableCereebroServer;
import io.cereebro.spring.cloud.autoconfigure.eureka.EurekaServerAddOnTest.EurekaServerAddOnTestApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EurekaServerAddOnTestApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("eureka-addon")
public class EurekaServerAddOnTest {

    @Value("http://localhost:${local.server.port}/cereebro/system")
    URI cereebroSystemURI;

    @Value("http://localhost:${local.server.port}/")
    URI eurekaDashboardURI;

    @Value("http://localhost:${local.server.port}/lastn")
    URI eurekaDashboardLastNURI;

    @Test
    public void cereebroSystemPageShouldBeHtml() {
        // @formatter:off
        RestAssured
            .given()
                .accept(ContentType.HTML)
            .when()
                .get(cereebroSystemURI)
            .then()
                .statusCode(200)
                .contentType(ContentType.HTML);
        // @formatter:on
    }

    @Test
    public void cereebroSystemResourceShouldBeJson() {
        // @formatter:off
        RestAssured
            .given()
                .accept(ContentType.JSON)
            .when()
                .get(cereebroSystemURI)
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", Matchers.is("eureka-server-addon"));
        // @formatter:on
    }

    /**
     * <strong>This test does not pass when launched from an IDE !</strong>
     */
    @Test
    public void eurekaDashboardPageShouldBeHtml() {
        // @formatter:off
        RestAssured
            .given()
                .accept(ContentType.HTML)
            .when()
                .get(eurekaDashboardURI)
            .then()
                .statusCode(200)
                .contentType(ContentType.HTML);
        // @formatter:on
    }

    /**
     * <strong>This test does not pass when launched from an IDE !</strong>
     */
    @Test
    public void eurekaDashboardLastNPageShouldBeHtml() {
        // @formatter:off
        RestAssured
            .given()
                .accept(ContentType.HTML)
            .when()
                .get(eurekaDashboardLastNURI)
            .then()
                .statusCode(200)
                .contentType(ContentType.HTML);
        // @formatter:on
    }

    @SpringBootApplication
    @EnableEurekaServer
    @EnableCereebroServer
    static class EurekaServerAddOnTestApplication {

    }

}
