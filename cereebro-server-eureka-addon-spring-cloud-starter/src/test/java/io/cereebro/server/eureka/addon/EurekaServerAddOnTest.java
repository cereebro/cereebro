package io.cereebro.server.eureka.addon;

import java.net.URI;

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
 * Unfortunately the following tests fail when launched on an unpackaged
 * application (i.e. from IDE or maven CLI).
 * <p>
 * The problems come from Thymeleaf and FreeMarker templates contained in other
 * jars in the classpath. Spring Boot's uber jar structure must solve the issue
 * somehow.
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
@Ignore("These tests work on a packaged application only")
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
                .contentType(ContentType.JSON);
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
