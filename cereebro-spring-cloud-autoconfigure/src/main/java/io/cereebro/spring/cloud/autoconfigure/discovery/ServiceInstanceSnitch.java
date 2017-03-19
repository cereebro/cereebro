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
package io.cereebro.spring.cloud.autoconfigure.discovery;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.Snitch;
import io.cereebro.core.SnitchingException;
import io.cereebro.core.StaticSnitch;
import io.cereebro.core.SystemFragment;
import io.cereebro.spring.ResourceSnitch;
import lombok.extern.slf4j.Slf4j;

/**
 * Snitch that uses a service instance's metadata provided by a service registry
 * like Eureka or Consul.
 * 
 * @author michaeltecourt
 */
@Slf4j
public class ServiceInstanceSnitch implements Snitch {

    private final ServiceInstance serviceInstance;
    private final ObjectMapper objectMapper;

    /**
     * * Snitch that uses a service instance's metadata provided by a service
     * registry like Eureka or Consul.
     * 
     * @param mapper
     *            JSON object mapper.
     * @param instance
     *            Service instance from a registry like Consul or Eureka.
     */
    public ServiceInstanceSnitch(ObjectMapper mapper, ServiceInstance instance) {
        serviceInstance = Objects.requireNonNull(instance, "Service instance required");
        objectMapper = Objects.requireNonNull(mapper, "ObjectMapper required");
        if (!hasCereebroMetadata(instance)) {
            throw new IllegalArgumentException("Service instance does not contain cereebro snitch metadata");
        }
    }

    /**
     * * Snitch that uses a service instance's metadata provided by a service
     * registry like Eureka or Consul.
     * 
     * @param mapper
     *            JSON object mapper.
     * @param instance
     *            Service instance from a registry like Consul or Eureka.
     * @return new ServiceInstanceSnitch instance.
     */
    public static Snitch of(ObjectMapper mapper, ServiceInstance instance) {
        return new ServiceInstanceSnitch(mapper, instance);
    }

    /**
     * Determine if a service instance contains enough Cereebro metadata.
     * 
     * @param instance
     *            Service instance.
     * @return {@code true} if the {@link ServiceInstance} contains Cereebro
     *         metadata, {@code false} otherwise.
     */
    public static boolean hasCereebroMetadata(ServiceInstance instance) {
        return extractSnitchURI(instance).isPresent();
    }

    /**
     * Extract the Cereebro Snitch URI from metadata.
     * 
     * @param instance
     *            Service instance.
     * @return Optional URI.
     */
    private static Optional<URI> extractSnitchURI(ServiceInstance instance) {
        String uri = instance.getMetadata().get(CereebroDiscoveryClientConstants.METADATA_KEY_SNITCH_URI);
        if (StringUtils.hasText(uri)) {
            return Optional.of(URI.create(uri));
        }
        return Optional.empty();
    }

    @Override
    public URI getUri() {
        return extractSnitchURI(serviceInstance).orElseThrow(() -> new IllegalStateException(
                "Could not extract Snitch URI from service instance metadata : " + serviceInstance));
    }

    @Override
    public SystemFragment snitch() {
        URI uri = getUri();
        try {
            if (StringUtils.hasText(getSystemFragmentJsonString())) {
                LOGGER.debug("Using snitched system fragment from discovery client - uri : {}", uri);
                SystemFragment frag = objectMapper.readValue(getSystemFragmentJsonString(), SystemFragment.class);
                return StaticSnitch.of(uri, frag).snitch();
            } else {
                LOGGER.debug("Using Snitch URL from discovery client - uri : {}", uri);
                return ResourceSnitch.of(objectMapper, new UrlResource(uri)).snitch();
            }
        } catch (IOException | RuntimeException e) {
            LOGGER.warn("Could not create snitch out of service : {} - meta-data : {} - error : {}",
                    serviceInstance.getServiceId(), serviceInstance.getMetadata(), e.getMessage());
            throw new SnitchingException(uri, "Error while creating snitch for uri : " + uri + " - service instance : "
                    + serviceInstance.getServiceId(), e);
        }
    }

    /**
     * System fragment as a JSON string extracted from the service instance
     * metadata.
     * 
     * @return SystemFragment as a JSON String.
     */
    private String getSystemFragmentJsonString() {
        return serviceInstance.getMetadata()
                .get(CereebroDiscoveryClientConstants.METADATA_KEY_SNITCH_SYSTEM_FRAGMENT_JSON);
    }

}
