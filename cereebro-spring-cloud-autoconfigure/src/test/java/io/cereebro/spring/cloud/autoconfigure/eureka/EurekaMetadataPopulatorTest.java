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

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.Snitch;
import io.cereebro.core.SnitchingException;
import io.cereebro.core.SystemFragment;
import io.cereebro.spring.cloud.autoconfigure.discovery.CereebroMetadata;

/**
 * {@link EurekaMetadataPopulator} unit tests.
 */
public class EurekaMetadataPopulatorTest {

    private static final URI TEST_URI = URI.create("/cereebro");
    private static final String DEFAULT_URL = "http://localhost:8080/cereebro";

    private EurekaInstanceSnitchProperties props;
    private CloudEurekaInstanceConfig cloudEurekaInstanceConfig;
    private Map<String, String> metadata;
    private Snitch snitch;
    private EurekaMetadataPopulator populator;

    @Before
    public void setup() {
        cloudEurekaInstanceConfig = Mockito.mock(CloudEurekaInstanceConfig.class);
        metadata = new HashMap<String, String>();
        Mockito.when(cloudEurekaInstanceConfig.getMetadataMap()).thenReturn(metadata);
        Mockito.when(cloudEurekaInstanceConfig.getHostName(true)).thenReturn("localhost");
        Mockito.when(cloudEurekaInstanceConfig.getNonSecurePort()).thenReturn(8080);
        snitch = Mockito.mock(Snitch.class);
        Mockito.when(snitch.getUri()).thenReturn(TEST_URI);
        props = new EurekaInstanceSnitchProperties();
        populator = new EurekaMetadataPopulator(snitch, cloudEurekaInstanceConfig, props, new ObjectMapper());
    }

    @Test
    public void populateEurekaMetadaFromSnitch() {
        populator.populate();
        Assertions.assertThat(metadata).isNotEmpty();
        Assertions.assertThat(metadata).containsEntry(CereebroMetadata.KEY_SNITCH_URI, DEFAULT_URL);
    }

    @Test
    public void populateEurekaMetadaFromAbsolutePath() {
        props.setUrl(DEFAULT_URL);
        populator.populate();
        Assertions.assertThat(metadata).containsEntry(CereebroMetadata.KEY_SNITCH_URI, DEFAULT_URL);
    }

    @Test
    public void populateEurekaMetadaFromRelativePath() {
        final String urlPath = "/cereebro";
        props.setUrlPath(urlPath);
        populator.populate();
        Assertions.assertThat(metadata).containsEntry(CereebroMetadata.KEY_SNITCH_URI, DEFAULT_URL);
    }

    @Test
    public void populateWhenBothPathsShouldPreferAbsoluteUrl() {
        props.setUrl(DEFAULT_URL);
        props.setUrlPath("/whatever");
        populator.populate();
        Assertions.assertThat(metadata).containsEntry(CereebroMetadata.KEY_SNITCH_URI, DEFAULT_URL);
    }

    @Test
    public void ioExceptionWhilePopulatingMetadataShouldBeConvertedToSnitchingException() throws IOException {
        ObjectMapper objectMapperMock = Mockito.mock(ObjectMapper.class);
        SystemFragment frag = SystemFragment.empty();
        Mockito.when(snitch.snitch()).thenReturn(frag);
        EurekaMetadataPopulator pop = new EurekaMetadataPopulator(snitch, cloudEurekaInstanceConfig, props,
                objectMapperMock);
        Mockito.when(objectMapperMock.writeValueAsString(frag))
                .thenThrow(new JsonEOFException(null, null, "unit test"));
        try {
            pop.populate();
            Assert.fail("SnitchingException expected");
        } catch (SnitchingException e) {
            Assertions.assertThat(e.getSnitchUri()).isEqualTo(TEST_URI);
        }
    }

    @Test(expected = NullPointerException.class)
    public void snitchRequired() {
        new EurekaMetadataPopulator(null, Mockito.mock(CloudEurekaInstanceConfig.class), props,
                Mockito.mock(ObjectMapper.class));
    }

    @Test(expected = NullPointerException.class)
    public void configRequired() {
        new EurekaMetadataPopulator(Mockito.mock(Snitch.class), null, props, Mockito.mock(ObjectMapper.class));
    }

    @Test(expected = NullPointerException.class)
    public void objectMpaperRequired() {
        new EurekaMetadataPopulator(Mockito.mock(Snitch.class), Mockito.mock(CloudEurekaInstanceConfig.class), props,
                null);
    }

    @Test(expected = NullPointerException.class)
    public void propertiesRequired() {
        new EurekaMetadataPopulator(Mockito.mock(Snitch.class), Mockito.mock(CloudEurekaInstanceConfig.class), null,
                Mockito.mock(ObjectMapper.class));
    }

}
