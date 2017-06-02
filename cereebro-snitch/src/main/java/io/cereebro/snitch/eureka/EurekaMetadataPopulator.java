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

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Objects;

import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.ApplicationInfoManager;

import io.cereebro.core.Snitch;
import io.cereebro.core.SnitchEndpoint;
import io.cereebro.core.SnitchingException;
import io.cereebro.snitch.discovery.CereebroMetadata;

/**
 * Populate metadata of the eureka instance with the snitch url.
 * 
 * @author lucwarrot
 *
 */
public class EurekaMetadataPopulator {

    private final EurekaInstanceSnitchProperties properties;

    private final Snitch snitch;
    private final ApplicationInfoManager appInfoManager;
    private final ObjectMapper objectMapper;

    public EurekaMetadataPopulator(SnitchEndpoint snitch, ApplicationInfoManager manager,
            EurekaInstanceSnitchProperties props, ObjectMapper mapper) {
        this.snitch = Objects.requireNonNull(snitch, "Snitch required");
        this.appInfoManager = Objects.requireNonNull(manager, "Eureka ApplicationInfoManager required");
        this.objectMapper = Objects.requireNonNull(mapper, "ObjectMapper required");
        this.properties = Objects.requireNonNull(props, "Configuration properties required");
    }

    /**
     * Populate Eureka instance metadata with Snitch info.
     */
    public void populate() {
        try {
            Map<String, String> metadata = appInfoManager.getEurekaInstanceConfig().getMetadataMap();
            metadata.put(CereebroMetadata.KEY_SNITCH_URI, getEndpointUri().toString());
            String frag = objectMapper.writeValueAsString(snitch.snitch());
            metadata.put(CereebroMetadata.KEY_SNITCH_SYSTEM_FRAGMENT_JSON, frag);
            // Force meta-data registration, updating only the meta-data map
            // is not sufficient when the application has already been
            // registered.
            appInfoManager.registerAppMetadata(metadata);
        } catch (IOException e) {
            throw new SnitchingException(snitch.getUri(), "Error while serializing fragment", e);
        }
    }

    /**
     * Get the endpoint location of the current cereebro instance.
     * 
     * @return Absolute Snitch URI.
     */
    protected URI getEndpointUri() {
        if (!StringUtils.isEmpty(properties.getEndpointUrl())) {
            return URI.create(properties.getEndpointUrl());
        }
        // @formatter:off
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(appInfoManager.getEurekaInstanceConfig().getHostName(true))
                .port(appInfoManager.getEurekaInstanceConfig().getNonSecurePort())
                .path(StringUtils.isEmpty(properties.getEndpointUrlPath()) ? snitch.getUri().toString() : properties.getEndpointUrlPath())
                .build()
                .toUri();
        // @formatter:on
    }

}
