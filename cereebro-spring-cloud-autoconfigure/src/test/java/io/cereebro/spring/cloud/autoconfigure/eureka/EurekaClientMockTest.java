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
package io.cereebro.spring.cloud.autoconfigure.eureka;

import java.net.URI;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.Snitch;
import io.cereebro.core.StaticSnitch;
import io.cereebro.core.SystemFragment;
import io.cereebro.spring.cloud.autoconfigure.eureka.EurekaClientMockTest.EurekaClientMockTestConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { EurekaClientMockTestConfiguration.class,
        CereebroEurekaInstanceAutoConfiguration.class }, webEnvironment = WebEnvironment.NONE)
@ActiveProfiles("eureka-client-mock")
public class EurekaClientMockTest {

    @Autowired
    EurekaMetadataPopulator populator;

    @Test
    public void test() {
        Assertions.assertThat(populator).isNotNull();
    }

    @Configuration
    static class EurekaClientMockTestConfiguration {

        @Bean
        public CloudEurekaInstanceConfig cloudEurekaInstanceConfig() {
            return Mockito.mock(CloudEurekaInstanceConfig.class);
        }

        @Bean
        public Snitch snitchMock() {
            return StaticSnitch.of(URI.create("nope://nope"), SystemFragment.empty());
        }

        @MockBean
        ObjectMapper objectMapper;

    }
}
