package io.cereebro.spring.cloud.autoconfigure.eureka;

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

import io.cereebro.core.Snitch;
import io.cereebro.spring.cloud.autoconfigure.discovery.CereebroDiscoveryClientConstants;

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
    public void getAll() {
        Mockito.when(eurekaServerContextMock.getRegistry()).thenReturn(instanceRegistryMock);
        Map<String, String> metadata = new HashMap<>();
        String uri = "http://cereebro.io";
        metadata.put(CereebroDiscoveryClientConstants.METADATA_KEY_SNITCH_URL, uri);
        // @formatter:off
        InstanceInfo instanceInfo = InstanceInfo.Builder.newBuilder()
                .setInstanceId("id")
                .setAppName("a")
                .setMetadata(metadata)
                .build();
        // @formatter:on
        Application application = new Application("a", Arrays.asList(instanceInfo));
        Mockito.when(instanceRegistryMock.getSortedApplications()).thenReturn(Arrays.asList(application));
        List<Snitch> result = registry.getAll();
        Assertions.assertThat(result).hasSize(1);
        Snitch snitch = result.get(0);
        Assertions.assertThat(snitch.getLocation()).isEqualTo(URI.create(uri));
    }

}
