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
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.ApplicationInfoManager;

import io.cereebro.core.SnitchEndpoint;
import io.cereebro.core.SystemFragment;
import io.cereebro.snitch.eureka.EurekaInstanceAbsoluteUrlSpringBootTest.EurekaClientMockTestConfiguration;

/**
 * {@literal cereebro.eureka.instance.snitch.url} should be the
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { EurekaClientMockTestConfiguration.class,
        CereebroEurekaInstanceAutoConfiguration.class }, webEnvironment = WebEnvironment.NONE, value = {
                "cereebro.snitch.eureka.endpointUrl=" + EurekaInstanceAbsoluteUrlSpringBootTest.SNITCH_URL })
public class EurekaInstanceAbsoluteUrlSpringBootTest {

    static final String SNITCH_URL = "https://example.com/snitch";

    @Autowired
    EurekaMetadataPopulator populator;

    @Test
    public void testSnitchUri() {
        Assertions.assertThat(populator.getEndpointUri()).isEqualTo(URI.create(SNITCH_URL));
    }

    @Configuration
    static class EurekaClientMockTestConfiguration {

        @Bean
        public ApplicationInfoManager applicationInfoManager() {
            CloudEurekaInstanceConfig mock = Mockito.mock(CloudEurekaInstanceConfig.class);
            ApplicationInfoManager applicationInfoManager = Mockito.mock(ApplicationInfoManager.class);
            Mockito.when(applicationInfoManager.getEurekaInstanceConfig()).thenReturn(mock);
            return applicationInfoManager;
        }

        @Bean
        public SnitchEndpoint snitchMock() {
            return new StaticSnitchEndpoint(URI.create("/nope"), SystemFragment.empty());
        }

        @MockBean
        ObjectMapper objectMapper;

    }
}
