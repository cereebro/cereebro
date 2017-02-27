package io.cereebro.snitch.cloud.eureka;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class EurekaSnitchRegistry implements SnitchRegistry {

    public static final String METADATA_KEY_URL = "io.cereebro.snitch.url";
    public static final String METADATA_KEY_FRAGMENT = "io.cereebro.snitch.fragment";

    private final DiscoveryClient discoveryClient;
    private final ObjectMapper objectMapper;

    public EurekaSnitchRegistry(DiscoveryClient discoveryClient, ObjectMapper objectMapper) {
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
            // Convert Eureka instances to Snitch (normally all of them at this point)
            .filter(EurekaServiceInstance.class::isInstance)
            .map(EurekaServiceInstance.class::cast)
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
    protected Optional<Snitch> toSnitch(EurekaServiceInstance instance) {
        String endpointLocation = instance.getMetadata().get(METADATA_KEY_URL);
        String systemFragmentString = instance.getMetadata().get(METADATA_KEY_FRAGMENT);
        try {
            if (StringUtils.hasText(systemFragmentString)) {
                LOGGER.debug("Using snitched system fragment from Eureka - {}", endpointLocation);
                SystemFragment frag = objectMapper.readValue(systemFragmentString, SystemFragment.class);
                Snitch snitch = StaticSnitch.of(URI.create(endpointLocation), frag);
                return Optional.of(snitch);
            } else if (StringUtils.hasText(endpointLocation)) {
                LOGGER.debug("Using Snitch URL from Eureka - {}", endpointLocation);
                ResourceSnitch snitch = ResourceSnitch.of(objectMapper, new UrlResource(endpointLocation));
                return Optional.of(snitch);
            }
        } catch (IOException | RuntimeException e) {
            LOGGER.warn("Could not create snitch out of service : {} - instance : {} - meta-data : {} - error : {}",
                    instance.getServiceId(), instance.getInstanceInfo().getId(), instance.getMetadata(),
                    e.getMessage());
            LOGGER.debug("Exception caught while creating snitch : " + endpointLocation, e);
        }
        return Optional.empty();
    }

}
