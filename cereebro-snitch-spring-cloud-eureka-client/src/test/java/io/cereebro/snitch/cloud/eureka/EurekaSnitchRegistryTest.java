package io.cereebro.snitch.cloud.eureka;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import io.cereebro.core.StaticSnitch;
import io.cereebro.core.SystemFragment;

/**
 * {@link EurekaSnitchRegistry} unit tests.
 * 
 * @author michaeltecourt
 */
@RunWith(MockitoJUnitRunner.class)
public class EurekaSnitchRegistryTest {

    @Mock
    ObjectMapper objectMapperMock;

    @Mock
    DiscoveryClient discoveryClientMock;

    @Mock
    EurekaServiceInstance serviceInstanceMock;

    @Mock
    InstanceInfo instanceInfoMock;

    @InjectMocks
    EurekaSnitchRegistry registry;

    @Test
    public void toSnitchWithoutAnyMetadataShouldReturnOptionalEmpty() {
        Map<String, String> emptyMetadata = new HashMap<>();
        Mockito.when(serviceInstanceMock.getMetadata()).thenReturn(emptyMetadata);
        Optional<Snitch> result = registry.toSnitch(serviceInstanceMock);
        Assertions.assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void toSnitchWithExceptionShouldReturnOptionalEmpty() throws IOException {
        Map<String, String> metadata = new HashMap<>();
        final String badJSON = "{ badJSON }";
        metadata.put(EurekaSnitchRegistry.METADATA_KEY_FRAGMENT, badJSON);
        Mockito.when(serviceInstanceMock.getMetadata()).thenReturn(metadata);
        Mockito.when(serviceInstanceMock.getInstanceInfo()).thenReturn(instanceInfoMock);
        Mockito.when(objectMapperMock.readValue(badJSON, SystemFragment.class)).thenThrow(new IOException("unit test"));
        Optional<Snitch> result = registry.toSnitch(serviceInstanceMock);
        Assertions.assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void getAllWithSystemFragmentStringShouldReturnStaticSnitch() throws IOException {
        String serviceId = "fakeServiceId";
        Mockito.when(discoveryClientMock.getServices()).thenReturn(Arrays.asList(serviceId));
        Mockito.when(discoveryClientMock.getInstances(serviceId)).thenReturn(Arrays.asList(serviceInstanceMock));
        Map<String, String> metadata = new HashMap<>();
        final String fakeJsonThatWorks = "{ \"valid\" : true }";
        final String url = "http://fake";
        metadata.put(EurekaSnitchRegistry.METADATA_KEY_FRAGMENT, fakeJsonThatWorks);
        metadata.put(EurekaSnitchRegistry.METADATA_KEY_URL, url);
        Mockito.when(serviceInstanceMock.getMetadata()).thenReturn(metadata);
        Mockito.when(serviceInstanceMock.getInstanceInfo()).thenReturn(instanceInfoMock);
        Mockito.when(objectMapperMock.readValue(fakeJsonThatWorks, SystemFragment.class))
                .thenReturn(SystemFragment.empty());
        List<Snitch> result = registry.getAll();
        Snitch expected = StaticSnitch.of(URI.create(url), SystemFragment.empty());
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0)).isEqualTo(expected);
    }

    @Test
    public void toSnitchFromEndpointLocationShouldReturnUrlResourceSnitch() throws IOException {
        Map<String, String> metadata = new HashMap<>();
        final String url = "http://fake";
        metadata.put(EurekaSnitchRegistry.METADATA_KEY_URL, url);
        Mockito.when(serviceInstanceMock.getMetadata()).thenReturn(metadata);
        Mockito.when(serviceInstanceMock.getInstanceInfo()).thenReturn(instanceInfoMock);
        Optional<Snitch> result = registry.toSnitch(serviceInstanceMock);
        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get().getLocation()).isEqualTo(URI.create(url));
    }

}
