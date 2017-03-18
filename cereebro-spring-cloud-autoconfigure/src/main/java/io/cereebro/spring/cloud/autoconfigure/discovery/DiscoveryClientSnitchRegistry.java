package io.cereebro.spring.cloud.autoconfigure.discovery;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.cloud.client.discovery.DiscoveryClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.Snitch;
import io.cereebro.core.SnitchRegistry;

/**
 * Snitch registry based on a service registry like Eureka or Consul. Each
 * service instance holds Cereebro metadata populated on the client side.
 * 
 * @author lucwarrot
 * @author michaeltecourt
 */
public class DiscoveryClientSnitchRegistry implements SnitchRegistry {

    private final DiscoveryClient discoveryClient;
    private final ObjectMapper objectMapper;

    /**
     * Snitch registry based on a service registry like Eureka or Consul. Each
     * service instance holds Cereebro metadata populated on the client side.
     * 
     * @param discoveryClient
     *            Service registry client.
     * @param mapper
     *            JSON object mapper.
     */
    public DiscoveryClientSnitchRegistry(DiscoveryClient discoveryClient, ObjectMapper mapper) {
        this.discoveryClient = Objects.requireNonNull(discoveryClient, "Eureka discovery client required");
        this.objectMapper = Objects.requireNonNull(mapper, "JSON object mapper required");
    }

    @Override
    public List<Snitch> getAll() {
        // @formatter:off
        return discoveryClient.getServices().stream()
            .map(discoveryClient::getInstances)
            .flatMap(List::stream)
            .filter(ServiceInstanceSnitch::hasCereebroMetadata)
            .map(instance -> new ServiceInstanceSnitch(objectMapper, instance))
            .collect(Collectors.toList());
        // @formatter:on
    }

}
