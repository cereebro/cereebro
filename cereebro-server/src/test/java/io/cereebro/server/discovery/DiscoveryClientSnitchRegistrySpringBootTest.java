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
package io.cereebro.server.discovery;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.SnitchRegistry;
import io.cereebro.server.discovery.DiscoveryClientSnitchRegistrySpringBootTest.DiscoveryClientSnitchRegistryTestConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { DiscoveryClientSnitchRegistryTestConfig.class,
        CereebroDiscoveryClientAutoConfiguration.class }, webEnvironment = WebEnvironment.NONE)
public class DiscoveryClientSnitchRegistrySpringBootTest {

    @Autowired
    private SnitchRegistry registry;

    @Test
    public void test() {
        Assertions.assertThat(registry).isInstanceOf(DiscoveryClientSnitchRegistry.class);
    }

    @Configuration
    static class DiscoveryClientSnitchRegistryTestConfig {

        /**
         * Go figure why {@link MockBean} works for {@link ObjectMapper}, but
         * not DiscoveryClient. Maybe because of the {@link ConditionalOnBean}
         * annotation ?
         * 
         * @return DiscoveryClient mock
         */
        @Bean
        public DiscoveryClient discoveryClientMock() {
            return Mockito.mock(DiscoveryClient.class);
        }

        @MockBean
        ObjectMapper objectMapper;

    }

}
