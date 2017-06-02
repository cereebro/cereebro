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
package io.cereebro.server.eureka;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentRelationships;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Snitch;
import io.cereebro.core.SystemFragment;
import io.cereebro.snitch.discovery.CereebroMetadata;

/**
 * {@link EurekaServerSnitchRegistry} unit tests.
 * 
 * @author michaeltecourt
 */
@RunWith(MockitoJUnitRunner.class)
public class EurekaServerSnitchRegistryTest {

    @Mock
    ObjectMapper objectMapperMock;

    @Mock
    EurekaServerContext eurekaServerContextMock;

    @InjectMocks
    EurekaServerSnitchRegistry registry;

    @Mock
    PeerAwareInstanceRegistry instanceRegistryMock;

    @Test
    public void getAllUsingCereebroMetadata() throws IOException {
        Mockito.when(eurekaServerContextMock.getRegistry()).thenReturn(instanceRegistryMock);
        String uri = "http://not-used.never.nope";
        String json = "{}";
        Map<String, String> metadata = new HashMap<>();
        metadata.put(CereebroMetadata.KEY_SNITCH_URI, uri);
        metadata.put(CereebroMetadata.KEY_SNITCH_SYSTEM_FRAGMENT_JSON, json);
        // @formatter:off
        InstanceInfo instanceInfo = InstanceInfo.Builder.newBuilder()
                .setInstanceId("id")
                .setAppName("a")
                .setMetadata(metadata)
                .build();
        // @formatter:on
        Application application = new Application("a", Arrays.asList(instanceInfo));
        Mockito.when(instanceRegistryMock.getSortedApplications()).thenReturn(Arrays.asList(application));
        Mockito.when(objectMapperMock.readValue(json, SystemFragment.class)).thenReturn(SystemFragment.empty());
        List<Snitch> result = registry.getAll();
        Assertions.assertThat(result).hasSize(1);
        Snitch snitch = result.get(0);
        Assertions.assertThat(snitch.getUri()).isEqualTo(URI.create(uri));
        Assertions.assertThat(snitch.snitch()).isEqualTo(SystemFragment.empty());
    }

    @Test
    public void getAllWithoutCereebroMetadataShouldUseServiceInstanceInfo() {
        Mockito.when(eurekaServerContextMock.getRegistry()).thenReturn(instanceRegistryMock);

        // @formatter:off
        InstanceInfo instanceInfo = InstanceInfo.Builder.newBuilder()
                .setInstanceId("id")
                .setAppName("a")
                .setMetadata(new HashMap<>())
                .setHostName("service-instance")
                .setPort(6090)
                .build();
        // @formatter:on
        Application application = new Application("a", Arrays.asList(instanceInfo));
        Mockito.when(instanceRegistryMock.getSortedApplications()).thenReturn(Arrays.asList(application));
        List<Snitch> result = registry.getAll();
        Assertions.assertThat(result).hasSize(1);
        Snitch snitch = result.get(0);
        Assertions.assertThat(snitch.getUri()).isEqualTo(URI.create("http://service-instance:6090"));
        Component expectedComponent = Component.of("a", ComponentType.HTTP_APPLICATION);
        SystemFragment expected = SystemFragment.of(ComponentRelationships.of(expectedComponent));
        Assertions.assertThat(snitch.snitch()).isEqualTo(expected);
    }

}
