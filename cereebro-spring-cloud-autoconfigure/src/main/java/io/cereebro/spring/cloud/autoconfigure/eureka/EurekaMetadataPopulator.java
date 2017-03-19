/*
 * Copyright © 2009 the original authors (http://cereebro.io)
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
import java.util.Objects;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.Snitch;
import io.cereebro.core.SnitchingException;
import io.cereebro.spring.cloud.autoconfigure.discovery.CereebroDiscoveryClientConstants;

/**
 * Populate metadata of the eureka instance with the snitch url.
 * 
 * @author lucwarrot
 *
 */
@ConfigurationProperties(prefix = "cereebro.eureka.instance.snitch")
public class EurekaMetadataPopulator {

    /**
     * Absolute URL of the endpoint (ex: "http://localhost:8080/cereebro"). <br>
     * Will take precedence over the {@link #urlPath} if both are set.
     */
    private String url;

    /**
     * Relative path of the endpoint location (ex: "/cereebro").
     */
    private String urlPath;

    private final Snitch snitch;
    private final CloudEurekaInstanceConfig config;
    private final ObjectMapper objectMapper;

    public EurekaMetadataPopulator(Snitch snitch, CloudEurekaInstanceConfig config, ObjectMapper mapper) {
        this.snitch = Objects.requireNonNull(snitch, "Snitch required");
        this.config = Objects.requireNonNull(config, "Cloud eureka instance config required");
        this.objectMapper = Objects.requireNonNull(mapper, "ObjectMapper required");
    }

    public void populate() {
        try {
            this.config.getMetadataMap().put(CereebroDiscoveryClientConstants.METADATA_KEY_SNITCH_URI,
                    getEndpointUri(this.snitch, this.config).toString());
            String report = objectMapper.writeValueAsString(snitch.snitch());
            this.config.getMetadataMap().put(CereebroDiscoveryClientConstants.METADATA_KEY_SNITCH_SYSTEM_FRAGMENT_JSON,
                    report);
        } catch (IOException e) {
            throw new SnitchingException(snitch.getUri(), "Error while serializing fragment", e);
        }

    }

    /**
     * Absolute URL of the endpoint (ex: "http://localhost:8080/cereebro"). <br>
     * Will take precedence over the {@link #urlPath} if both are set.
     * 
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Absolute URL of the endpoint (ex: "http://localhost:8080/cereebro"). <br>
     * Will take precedence over the {@link #urlPath} if both are set.
     * 
     * @param url
     *            absolute URL.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Relative path of the endpoint location (ex: "/cereebro").
     * 
     * @return urlPath
     */
    public String getUrlPath() {
        return urlPath;
    }

    /**
     * Relative path of the endpoint location (ex: "/cereebro").
     * 
     * @param urlPath
     *            Relative path.
     */
    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    /**
     * Get the endpoint location of the current cereebro instance.
     * 
     * @param snitch
     *            Cereebro snitch, most probably the snitch endpoint.
     * @param config
     *            Eureka instance configuration.
     * @return Absolute Snitch URI.
     */
    protected URI getEndpointUri(Snitch snitch, CloudEurekaInstanceConfig config) {
        if (!StringUtils.isEmpty(url)) {
            return URI.create(url);
        }
        // @formatter:off
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(config.getHostName(true))
                .port(config.getNonSecurePort())
                .path(StringUtils.isEmpty(urlPath) ? snitch.getUri().toString() : urlPath)
                .build()
                .toUri();
        // @formatter:on
    }

}
