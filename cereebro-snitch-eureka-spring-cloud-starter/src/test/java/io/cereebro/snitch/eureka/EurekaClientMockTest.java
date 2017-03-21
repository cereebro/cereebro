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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.snitch.eureka.EurekaClientMockTest.EurekaClientMockTestApplication;
import io.cereebro.spring.cloud.autoconfigure.eureka.EurekaMetadataPopulator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EurekaClientMockTestApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("eureka-client-mock")
public class EurekaClientMockTest {

    @Autowired
    EurekaMetadataPopulator populator;

    @Test
    public void test() {
        Assertions.assertThat(populator).isNotNull();
    }

    @SpringBootApplication
    static class EurekaClientMockTestApplication {

        @MockBean
        CloudEurekaInstanceConfig cloudEurekaInstanceConfig;

    }
}
