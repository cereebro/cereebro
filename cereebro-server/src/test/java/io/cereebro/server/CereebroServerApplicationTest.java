package io.cereebro.server;

import java.net.URI;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentRelationships;
import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import io.cereebro.core.SnitchRegistry;
import io.cereebro.core.SystemFragment;
import io.cereebro.server.CereebroServerApplicationTest.TestApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT, properties = "eureka.client.enabled=false")
@ActiveProfiles("it-static")
public class CereebroServerApplicationTest {

    @Value("http://localhost:${local.server.port}/cereebro/system")
    URI homePageURI;

    @Autowired
    SnitchRegistry registry;

    @Test
    public void testHtml() {
        // @formatter:off
        RestAssured
            .given()
                .accept(ContentType.HTML)
            .when()
                .get(homePageURI)
            .then()
                .statusCode(200)
                .contentType(ContentType.HTML)
                .body("html.body.div.h1", Matchers.is("cereebro-system"));
                // The expression works, but can't guess the order when using sets
                // .body("html.body.div.table.tbody.tr[0].td[0]", Matchers.is("gambit"))
        // @formatter:on
    }

    @Test
    public void testJsonApi() {
        // @formatter:off
        RestAssured
            .given()
                .accept(ContentType.JSON)
            .when()
                .get(homePageURI)
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", Matchers.is("cereebro-system"));
        // @formatter:on
    }

    @Test
    public void fragmentShouldMatch() {
        Assert.assertFalse(registry.getAll().isEmpty());

        // @formatter:off
        ComponentRelationships rel = ComponentRelationships.builder()
                .component(Component.of("gambit", "superhero"))
                .addDependency(Dependency.on(Component.of("rogue", "superhero")))
                .addDependency(Dependency.on(Component.of("cards", "addiction")))
                .addConsumer(Consumer.by(Component.of("apocalypse", "villain")))
                .addConsumer(Consumer.by(Component.of("angel", "superhero")))
                .build();
        // @formatter:on
        SystemFragment expected = SystemFragment.of(rel);
        SystemFragment actual = registry.getAll().iterator().next().snitch();
        Assert.assertEquals(expected, actual);
    }

    @SpringBootApplication
    static class TestApplication {

    }

}
