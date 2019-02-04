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

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;

import io.cereebro.core.Snitch;
import io.cereebro.snitch.discovery.CereebroMetadata;

/**
 * {@link DiscoveryClientSnitchRegistry} unit tests.
 * 
 * @author michaeltecourt
 */
@RunWith(MockitoJUnitRunner.class)
public class DiscoveryClientSnitchRegistryTest {

    @Mock
    ObjectMapper objectMapperMock;

    @Mock
    DiscoveryClient discoveryClientMock;

    @Mock
    EurekaServiceInstance serviceInstanceMock;

    @Mock
    InstanceInfo instanceInfoMock;

    @InjectMocks
    DiscoveryClientSnitchRegistry registry;

    @Test
    public void getAllWithSystemFragmentStringShouldReturnStaticSnitch() throws IOException {
        String serviceId = "fakeServiceId";
        Mockito.when(discoveryClientMock.getServices()).thenReturn(Arrays.asList(serviceId));
        Mockito.when(discoveryClientMock.getInstances(serviceId)).thenReturn(Arrays.asList(serviceInstanceMock));
        Map<String, String> metadata = new HashMap<>();
        final String fakeJsonThatWorks = "{ \"valid\" : true }";
        final String url = "http://fake";
        metadata.put(CereebroMetadata.KEY_SNITCH_SYSTEM_FRAGMENT_JSON, fakeJsonThatWorks);
        metadata.put(CereebroMetadata.KEY_SNITCH_URI, url);
        Mockito.when(serviceInstanceMock.getMetadata()).thenReturn(metadata);
        List<Snitch> result = registry.getAll();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Snitch snitch = result.get(0);
        Assertions.assertThat(snitch).isInstanceOf(ServiceInstanceSnitch.class);
        Assertions.assertThat(URI.create("http://fake")).isEqualTo(snitch.getUri());
    }

}
