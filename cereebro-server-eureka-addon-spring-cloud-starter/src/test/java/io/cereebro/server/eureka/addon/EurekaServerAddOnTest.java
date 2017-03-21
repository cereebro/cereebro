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
package io.cereebro.server.eureka.addon;

import java.net.URI;

import org.hamcrest.Matchers;
import org.junit.Ignore;
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
import io.cereebro.server.eureka.addon.EurekaServerAddOnTest.EurekaServerAddOnTestApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * Unfortunately some of the following tests fail when launched on an unpackaged
 * application (i.e. from IDE or maven CLI).
 * <p>
 * The problems come from Thymeleaf and FreeMarker templates contained in other
 * jars in the classpath (technically just adding a folder named "templates" on
 * the classpath is sufficient to bug the eureka server). Spring Boot's uber jar
 * structure must solve the issue somehow.
 * </p>
 * <p>
 * Replacing local server URLs with a running sample like
 * {@literal cereebro-sample-server-eureka-addon} shows tests pass.
 * </p>
 * 
 * @author michaeltecourt
 */
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

    /**
     * FIXME Test ignored because of template classpath issue.
     */
    @Test
    @Ignore("These tests work on a packaged application only")
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

    /**
     * FIXME Test ignored because of template classpath issue.
     */
    @Test
    @Ignore("These tests work on a packaged application only")
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
