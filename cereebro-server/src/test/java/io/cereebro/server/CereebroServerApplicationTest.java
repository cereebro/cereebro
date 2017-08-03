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
@SpringBootTest(classes = TestApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
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
                .body("html.body.div.div.h1", Matchers.is("cereebro-system"));
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
                .body("name", Matchers.is("cereebro-system"))
                .body("errors.size()", Matchers.is(0));
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
