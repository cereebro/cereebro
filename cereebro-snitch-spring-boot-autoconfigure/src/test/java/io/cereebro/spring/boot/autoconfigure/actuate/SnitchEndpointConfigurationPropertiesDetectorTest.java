package io.cereebro.spring.boot.autoconfigure.actuate;

import java.net.URI;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.spring.boot.autoconfigure.CereebroAutoConfiguration;
import io.cereebro.spring.boot.autoconfigure.actuate.SnitchEndpointConfigurationPropertiesDetectorTest.SnitchEndpointConfigurationPropertiesDetectorTestApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SnitchEndpointConfigurationPropertiesDetectorTestApplication.class,
        CereebroAutoConfiguration.class,
        CereebroWebMvcEndpointConfiguration.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("props-detector")
public class SnitchEndpointConfigurationPropertiesDetectorTest {

    @Value("http://localhost:${local.server.port}/cereebro/snitch")
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
                .body("componentRelationships[0].dependencies[0].component.name", Matchers.is("dependency"))
                .body("componentRelationships[0].dependencies[0].component.type", Matchers.is("properties/dependency"))
                .body("componentRelationships[0].consumers[0].component.name", Matchers.is("consumer"))
                .body("componentRelationships[0].consumers[0].component.type", Matchers.is("properties/consumer"));
        // @formatter:on
    }

    @SpringBootApplication
    static class SnitchEndpointConfigurationPropertiesDetectorTestApplication {

    }

}
