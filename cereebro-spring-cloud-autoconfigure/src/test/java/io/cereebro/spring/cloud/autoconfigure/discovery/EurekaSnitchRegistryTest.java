package io.cereebro.spring.cloud.autoconfigure.discovery;

import java.util.Collections;
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

import io.cereebro.core.Snitch;

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
    public void discoveryClientWithOneGenericInstance() {
        Mockito.when(discoveryClient.getServices()).thenReturn(Lists.newArrayList("serviceId"));
        Mockito.when(discoveryClient.getInstances("serviceId"))
                .thenReturn(Lists.newArrayList(Mockito.mock(ServiceInstance.class)));
        DiscoveryClientSnitchRegistry snitchR = new DiscoveryClientSnitchRegistry(discoveryClient, objectMapper);
        Assertions.assertThat(snitchR.getAll()).isEmpty();
    }

    @Test
    public void discoveryClientWithOneEurekaServiceInstance() {
        EurekaServiceInstance serviceInstance = Mockito.mock(EurekaServiceInstance.class);
        Mockito.when(discoveryClient.getServices()).thenReturn(Lists.newArrayList("serviceId"));
        Mockito.when(discoveryClient.getInstances("serviceId")).thenReturn(Lists.newArrayList(serviceInstance));
        Mockito.when(serviceInstance.getMetadata()).thenReturn(Maps.newHashMap(
                CereebroDiscoveryClientConstants.METADATA_KEY_SNITCH_URL, "http://localhost:8080/cereebro"));
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

        Mockito.when(firstServiceInstance.getMetadata()).thenReturn(Maps.newHashMap(
                CereebroDiscoveryClientConstants.METADATA_KEY_SNITCH_URL, "http://localhost:8081/cereebro"));
        Mockito.when(secondServiceInstance.getMetadata()).thenReturn(Maps.newHashMap(
                CereebroDiscoveryClientConstants.METADATA_KEY_SNITCH_URL, "http://localhost:8082/cereebro"));

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
    public void discoveryClientWithWrongUrl() {
        EurekaServiceInstance serviceInstance = Mockito.mock(EurekaServiceInstance.class);
        Mockito.when(discoveryClient.getServices()).thenReturn(Lists.newArrayList("serviceId"));
        Mockito.when(discoveryClient.getInstances("serviceId")).thenReturn(Lists.newArrayList(serviceInstance));
        Mockito.when(serviceInstance.getMetadata())
                .thenReturn(Maps.newHashMap(CereebroDiscoveryClientConstants.METADATA_KEY_SNITCH_URL, ""));
        DiscoveryClientSnitchRegistry snitchR = new DiscoveryClientSnitchRegistry(discoveryClient, objectMapper);
        List<Snitch> snitches = snitchR.getAll();
        Assertions.assertThat(snitches).isEmpty();
    }

    @Test
    public void discoveryClientWithoutMetada() {
        EurekaServiceInstance serviceInstance = Mockito.mock(EurekaServiceInstance.class);
        Mockito.when(discoveryClient.getServices()).thenReturn(Lists.newArrayList("serviceId"));
        Mockito.when(discoveryClient.getInstances("serviceId")).thenReturn(Lists.newArrayList(serviceInstance));
        Mockito.when(serviceInstance.getMetadata()).thenReturn(Collections.emptyMap());
        DiscoveryClientSnitchRegistry snitchR = new DiscoveryClientSnitchRegistry(discoveryClient, objectMapper);
        List<Snitch> snitches = snitchR.getAll();
        Assertions.assertThat(snitches).isEmpty();
    }

}
