package io.cereebro.spring.boot.autoconfigure.actuate;

import java.net.URI;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.spring.boot.autoconfigure.CereebroAutoConfiguration;
import io.cereebro.spring.boot.autoconfigure.actuate.SnitchEndpointSpringApplicationNameTest.SnitchEndpointSpringApplicationNameTestApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SnitchEndpointSpringApplicationNameTestApplication.class, CereebroAutoConfiguration.class,
        CereebroWebMvcEndpointConfiguration.class }, webEnvironment = WebEnvironment.RANDOM_PORT, value = {
                "spring.application.name=spring-app-name", "cereebro.application.component.name=cereebro-app-name",
                "cereebro.application.component.type=test-override" })
public class SnitchEndpointApplicationNameOverrideTest {

    @Value("http://localhost:${local.server.port}/cereebro/snitch")
    URI snitchURI;

    /**
     * Verify that the component name returned by the snitch endpoint is
     * overriden by {@literal cereebro.application.component.name} (and not the
     * spring app name).
     */
    @Test
    public void applicationComponentNameShouldBeCereebroApplicationComponentName() {
        // @formatter:off
        RestAssured
            .given()
            .when()
                .get(snitchURI)
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("componentRelationships[0].component.name", Matchers.is("cereebro-app-name"))
                .body("componentRelationships[0].component.type", Matchers.is("test-override"));
        // @formatter:on
    }

    @SpringBootApplication
    static class SnitchEndpointApplicationNameOverrideTestApplication {

    }

}
