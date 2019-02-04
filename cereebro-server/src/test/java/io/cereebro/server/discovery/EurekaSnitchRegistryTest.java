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

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentRelationships;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Snitch;
import io.cereebro.core.SystemFragment;
import io.cereebro.snitch.discovery.CereebroMetadata;

@RunWith(MockitoJUnitRunner.class)
public class EurekaSnitchRegistryTest {

    @Mock
    private DiscoveryClient discoveryClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    private DiscoveryClientSnitchRegistry snitchR;

    @Before
    public void setUp() {
        snitchR = new DiscoveryClientSnitchRegistry(discoveryClient, objectMapper);
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithoutDiscoveryClient() {
        new DiscoveryClientSnitchRegistry(null, objectMapper);
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithoutObjectMapper() {
        new DiscoveryClientSnitchRegistry(discoveryClient, null);
    }

    @Test
    public void emptyDiscoveryClient() {
        Assertions.assertThat(snitchR.getAll()).isEmpty();
    }

    @Test
    public void discoveryClientWithOneServiceInstanceWithoutMetadataShouldUseServiceInstanceIdAndUri() {
        String serviceId = "serviceId";
        URI uri = URI.create("http://service-instance-uri");
        Mockito.when(discoveryClient.getServices()).thenReturn(Lists.newArrayList(serviceId));
        ServiceInstance serviceInstanceMock = Mockito.mock(ServiceInstance.class);
        Mockito.when(serviceInstanceMock.getUri()).thenReturn(uri);
        Mockito.when(serviceInstanceMock.getServiceId()).thenReturn(serviceId);
        Mockito.when(discoveryClient.getInstances(serviceId)).thenReturn(Lists.newArrayList(serviceInstanceMock));
        DiscoveryClientSnitchRegistry registry = new DiscoveryClientSnitchRegistry(discoveryClient, objectMapper);
        Assertions.assertThat(registry.getAll()).hasSize(1);
        Snitch snitch = registry.getAll().get(0);
        Assertions.assertThat(snitch).isInstanceOf(ServiceInstanceSnitch.class);
        Assertions.assertThat(snitch.getUri()).isEqualTo(uri);
        Component component = Component.of(serviceId, ComponentType.HTTP_APPLICATION);
        SystemFragment expected = SystemFragment.of(ComponentRelationships.of(component, new HashSet<>()));
        Assertions.assertThat(snitch.snitch()).isEqualTo(expected);
    }

    @Test
    public void discoveryClientWithOneEurekaServiceInstance() {
        EurekaServiceInstance serviceInstance = Mockito.mock(EurekaServiceInstance.class);
        Mockito.when(discoveryClient.getServices()).thenReturn(Lists.newArrayList("serviceId"));
        Mockito.when(discoveryClient.getInstances("serviceId")).thenReturn(Lists.newArrayList(serviceInstance));
        DiscoveryClientSnitchRegistry snitchR = new DiscoveryClientSnitchRegistry(discoveryClient, objectMapper);
        List<Snitch> snitches = snitchR.getAll();
        Assertions.assertThat(snitches).isNotEmpty();
        Assertions.assertThat(snitches).hasSize(1);
    }

    @Test
    public void discoveryClientWithMultiveEurekaServiceInstance() {
        EurekaServiceInstance firstServiceInstance = Mockito.mock(EurekaServiceInstance.class);
        EurekaServiceInstance secondServiceInstance = Mockito.mock(EurekaServiceInstance.class);

        Mockito.when(discoveryClient.getServices()).thenReturn(Lists.newArrayList("firstServiceId", "secondServiceId"));

        Mockito.when(discoveryClient.getInstances("firstServiceId"))
                .thenReturn(Lists.newArrayList(firstServiceInstance));
        Mockito.when(discoveryClient.getInstances("secondServiceId"))
                .thenReturn(Lists.newArrayList(secondServiceInstance));

        DiscoveryClientSnitchRegistry snitchR = new DiscoveryClientSnitchRegistry(discoveryClient, objectMapper);
        List<Snitch> snitches = snitchR.getAll();
        Assertions.assertThat(snitches).isNotEmpty();
        Assertions.assertThat(snitches).hasSize(2);
    }

    @Test
    public void discoveryClientWithEmptyInstance() {
        Mockito.when(discoveryClient.getServices()).thenReturn(Lists.newArrayList("serviceId"));
        Mockito.when(discoveryClient.getInstances("serviceId")).thenReturn(Collections.emptyList());
        DiscoveryClientSnitchRegistry snitchR = new DiscoveryClientSnitchRegistry(discoveryClient, objectMapper);
        Assertions.assertThat(snitchR.getAll()).isEmpty();
    }

    @Test
    public void discoveryClientWithWrongUrlShouldUseServiceInstanceInfo() {
        String serviceId = "serviceId";
        URI uri = URI.create("http://service-instance-uri");
        EurekaServiceInstance serviceInstanceMock = Mockito.mock(EurekaServiceInstance.class);
        Mockito.when(serviceInstanceMock.getUri()).thenReturn(uri);
        Mockito.when(serviceInstanceMock.getServiceId()).thenReturn(serviceId);
        Mockito.when(discoveryClient.getServices()).thenReturn(Lists.newArrayList("serviceId"));
        Mockito.when(discoveryClient.getInstances("serviceId")).thenReturn(Lists.newArrayList(serviceInstanceMock));
        Mockito.when(serviceInstanceMock.getMetadata())
                .thenReturn(Maps.newHashMap(CereebroMetadata.KEY_SNITCH_URI, ""));
        DiscoveryClientSnitchRegistry registry = new DiscoveryClientSnitchRegistry(discoveryClient, objectMapper);
        Assertions.assertThat(registry.getAll()).hasSize(1);
        Snitch snitch = registry.getAll().get(0);
        Assertions.assertThat(snitch).isInstanceOf(ServiceInstanceSnitch.class);
        Assertions.assertThat(snitch.getUri()).isEqualTo(uri);
        Component component = Component.of(serviceId, ComponentType.HTTP_APPLICATION);
        SystemFragment expected = SystemFragment.of(ComponentRelationships.of(component, new HashSet<>()));
        Assertions.assertThat(snitch.snitch()).isEqualTo(expected);
    }

}
