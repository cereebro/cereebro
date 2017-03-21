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
package io.cereebro.snitch.eureka;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.snitch.eureka.EurekaClientDisabledTest.EurekaClientDisabledTestApplication;
import io.cereebro.spring.cloud.autoconfigure.eureka.EurekaMetadataPopulator;

/**
 * Make sure that Eureka client features are not auto-configured when eureka
 * client is disabled.
 * 
 * @author michaeltecourt
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EurekaClientDisabledTestApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT, value = {
        "eureka.client.enabled=false" })
public class EurekaClientDisabledTest {

    @Autowired(required = false)
    EurekaMetadataPopulator populator;

    @Test
    public void noMetadataPopulatorAutoConfigured() {
        Assertions.assertThat(populator).isNull();
    }

    @SpringBootApplication
    static class EurekaClientDisabledTestApplication {

    }

}
