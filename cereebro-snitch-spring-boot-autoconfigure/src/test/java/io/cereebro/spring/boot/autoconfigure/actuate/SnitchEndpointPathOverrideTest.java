package io.cereebro.spring.boot.autoconfigure.actuate;

import java.net.URI;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.Snitch;
import io.cereebro.spring.boot.autoconfigure.CereebroAutoConfiguration;
import io.cereebro.spring.boot.autoconfigure.actuate.SnitchEndpointPathOverrideTest.SnitchEndpointPathOverrideTestApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SnitchEndpointPathOverrideTestApplication.class, CereebroAutoConfiguration.class,
        CereebroWebMvcEndpointConfiguration.class }, webEnvironment = WebEnvironment.RANDOM_PORT, value = {
                "spring.application.name=spring-app-name", "endpoints.cereebro.path=/cereebro/snitch/test" })
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

    @SpringBootApplication
    static class SnitchEndpointPathOverrideTestApplication {

    }

}
