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
     */
    public static Snitch of(ObjectMapper mapper, ServiceInstance instance) {
        return new ServiceInstanceSnitch(mapper, instance);
    }

    /**
     * Determine if a service instance contains enough Cereebro metadata.
     * 
     * @param instance
     * @return {@code true} if the {@link ServiceInstance} contains Cereebro
     *         metadata, {@code false} otherwise.
     */
    public static boolean hasCereebroMetadata(ServiceInstance instance) {
        return extractSnitchUri(instance).isPresent();
    }

    /**
     * Extract the Cereebro Snitch URI from metadata.
     * 
     * @param instance
     * @return Optional URI.
     */
    private static Optional<URI> extractSnitchUri(ServiceInstance instance) {
        String uri = instance.getMetadata().get(CereebroDiscoveryClientConstants.METADATA_KEY_SNITCH_URL);
        if (StringUtils.hasText(uri)) {
            return Optional.of(URI.create(uri));
        }
        return Optional.empty();
    }

    @Override
    public URI getLocation() {
        return extractSnitchUri(serviceInstance).get();
    }

    @Override
    public SystemFragment snitch() {
        URI endpointLocation = getLocation();
        try {
            if (StringUtils.hasText(getSystemFragmentString())) {
                LOGGER.debug("Using snitched system fragment from discovery client - {}", getLocation());
                SystemFragment frag = objectMapper.readValue(getSystemFragmentString(), SystemFragment.class);
                return StaticSnitch.of(endpointLocation, frag).snitch();
            } else {
                LOGGER.debug("Using Snitch URL from discovery client - {}", endpointLocation);
                return ResourceSnitch.of(objectMapper, new UrlResource(endpointLocation)).snitch();
            }
        } catch (IOException | RuntimeException e) {
            LOGGER.warn("Could not create snitch out of service : {} - meta-data : {} - error : {}",
                    serviceInstance.getServiceId(), serviceInstance.getMetadata(), e.getMessage());
            throw new SnitchingException(getLocation(), "Error while creating snitch for uri : " + endpointLocation
                    + " - service instance : " + serviceInstance.getServiceId(), e);
        }
    }

    /**
     * System fragment as a JSON string extracted from the service instance
     * metadata.
     * 
     * @return SystemFragment as a JSON String.
     */
    private String getSystemFragmentString() {
        return serviceInstance.getMetadata().get(CereebroDiscoveryClientConstants.METADATA_KEY_SNITCH_SYSTEM_FRAGMENT);
    }

}
