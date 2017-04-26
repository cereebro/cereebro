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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.server.CereebroServerApplicationTest.TestApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * Test the behavior of the Cereebro server when a Snitching exception occurs
 * while resolving the System. A bad resource URI is used to trigger a
 * ResolutionError.
 * 
 * @author michaeltecourt
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
        "cereebro.server.system.name=error-system", "cereebro.server.system.snitch.resources[0]=http://cereebro.nope" })
public class CereebroServerErrorApplicationTest {

    @Value("http://localhost:${local.server.port}/cereebro/system")
    URI systemURI;

    @Test
    public void badSnitchUriShouldYield200AndResolutionError() {
        // @formatter:off
        RestAssured
            .given()
                .accept(ContentType.JSON)
            .when()
                .get(systemURI)
            .then()
                .statusCode(200)
                .body("name", Matchers.is("error-system"))
                .body("errors.size()", Matchers.is(1))
                .body("errors[0].snitchUri", Matchers.is("http://cereebro.nope"))
                .body("errors[0].message", Matchers.not(Matchers.isEmptyOrNullString()))
                // cause (Throwable) should not be serialized
                .body("errors[0].cause", Matchers.nullValue());
        // @formatter:on
    }

}
