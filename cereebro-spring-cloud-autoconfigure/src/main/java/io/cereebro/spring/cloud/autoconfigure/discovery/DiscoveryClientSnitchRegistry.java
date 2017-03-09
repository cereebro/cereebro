package io.cereebro.spring.cloud.autoconfigure.discovery;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.Snitch;
import io.cereebro.core.SnitchRegistry;
import io.cereebro.core.StaticSnitch;
import io.cereebro.core.SystemFragment;
import io.cereebro.spring.ResourceSnitch;
import lombok.extern.slf4j.Slf4j;

/**
 * The implementation of a {@link SnitchRegistry} based on Eureka. For each
 * service registered in Eureka, search in metadata instance the cereebro
 * endpoint.
 * 
 * @author lucwarrot
 * @author michaeltecourt
 */
@Slf4j
public class DiscoveryClientSnitchRegistry implements SnitchRegistry {

    private final DiscoveryClient discoveryClient;
    private final ObjectMapper objectMapper;

    public DiscoveryClientSnitchRegistry(DiscoveryClient discoveryClient, ObjectMapper objectMapper) {
        this.discoveryClient = Objects.requireNonNull(discoveryClient, "Eureka discovery client is required");
        this.objectMapper = Objects.requireNonNull(objectMapper, "The rest template object is required");
    }

    @Override
    public List<Snitch> getAll() {
        // @formatter:off
        // Read service IDs
        return discoveryClient.getServices().stream()
            // Find all instances for each service
            .map(discoveryClient::getInstances)
            .flatMap(List::stream)
            // Convert each service instance to Snitch
            .map(this::toSnitch)
            // Exclude absent values
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        // @formatter:on
    }

    /**
     * Create a {@link Snitch} from the {@link EurekaServiceInstance}. Get the
     * URL of the {@link Snitch} from metadata
     * 
     * @param instance
     *            Eureka service instance
     * @return Snitch
     */
    protected Optional<Snitch> toSnitch(ServiceInstance instance) {
        String endpointLocation = instance.getMetadata().get(CereebroDiscoveryClientConstants.METADATA_KEY_SNITCH_URL);
        String systemFragmentString = instance.getMetadata()
                .get(CereebroDiscoveryClientConstants.METADATA_KEY_SNITCH_SYSTEM_FRAGMENT);
        try {
            if (StringUtils.hasText(systemFragmentString)) {
                LOGGER.debug("Using snitched system fragment from discovery client - {}", endpointLocation);
                SystemFragment frag = objectMapper.readValue(systemFragmentString, SystemFragment.class);
                Snitch snitch = StaticSnitch.of(URI.create(endpointLocation), frag);
                return Optional.of(snitch);
            } else if (StringUtils.hasText(endpointLocation)) {
                LOGGER.debug("Using Snitch URL from discovery client - {}", endpointLocation);
                ResourceSnitch snitch = ResourceSnitch.of(objectMapper, new UrlResource(endpointLocation));
                return Optional.of(snitch);
            }
        } catch (IOException | RuntimeException e) {
            LOGGER.warn("Could not create snitch out of service : {} - meta-data : {} - error : {}",
                    instance.getServiceId(), instance.getMetadata(), e.getMessage());
            LOGGER.debug("Exception caught while creating snitch : " + endpointLocation, e);
        }
        return Optional.empty();
    }

}
