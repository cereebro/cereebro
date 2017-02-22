package io.cereebro.snitch.cloud.eureka;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;
import org.springframework.core.io.UrlResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;

import io.cereebro.core.Snitch;
import io.cereebro.core.SnitchRegistry;
import io.cereebro.spring.ResourceSnitch;

/**
 * The implementation of a {@link SnitchRegistry} based on Eureka. For each
 * service registered in Eureka, search in metadata instance the cereebro
 * endpoint.
 * 
 * @author lucwarrot
 *
 */
public class EurekaSnitchRegistry implements SnitchRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaSnitchRegistry.class);

    public static final String CEREEBRO_ENDPOINT_KEY = "io.cereebro.endpoint";

    private final DiscoveryClient discoveryClient;
    private final ObjectMapper objectMapper;

    public EurekaSnitchRegistry(DiscoveryClient discoveryClient, ObjectMapper objectMapper) {
        this.discoveryClient = Objects.requireNonNull(discoveryClient, "Eureka discovery client is required");
        this.objectMapper = Objects.requireNonNull(objectMapper, "The rest template object is required");
    }

    @Override
    public List<Snitch> getAll() {
        List<Snitch> result = new ArrayList<>();
        for (String serviceId : discoveryClient.getServices()) {
            ServiceInstance instance = retrieveInstance(serviceId);
            if (instance instanceof EurekaServiceInstance) {
                Snitch eurekaSnitch = toSnitch(EurekaServiceInstance.class.cast(instance));
                if (eurekaSnitch != null) {
                    result.add(eurekaSnitch);
                    LOGGER.info("The service {} found in Eureka implements cereebro endpoint at {}", serviceId,
                            eurekaSnitch.getLocation());
                }
            }
        }
        return result;
    }

    /**
     * Retrieve a {@link ServiceInstance} for the given service id.
     * 
     * @param serviceId
     * @return
     */
    protected ServiceInstance retrieveInstance(String serviceId) {
        List<ServiceInstance> services = discoveryClient.getInstances(serviceId);
        return Iterables.getFirst(services, null);
    }

    /**
     * Create a {@link Snitch} from the {@link EurekaServiceInstance}. Get the
     * URL of the {@link Snitch} from metadata
     * 
     * @param instance
     * @return
     */
    protected Snitch toSnitch(EurekaServiceInstance instance) {
        EurekaServiceInstance eurekaServiceInstance = EurekaServiceInstance.class.cast(instance);
        String endpointLocation = eurekaServiceInstance.getMetadata().get(CEREEBRO_ENDPOINT_KEY);
        if (!Strings.isNullOrEmpty(endpointLocation)) {
            try {
                return ResourceSnitch.of(objectMapper, new UrlResource(endpointLocation));
            } catch (MalformedURLException e) {
                LOGGER.warn("Could not load the resource at the url {} for the service {}", endpointLocation,
                        instance.getServiceId());
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(e.getMessage(), e);
                }
            }
        }
        return null;
    }

}
