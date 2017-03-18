package io.cereebro.spring.cloud.autoconfigure.eureka;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.cloud.netflix.eureka.EurekaServiceInstancePublic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.shared.Application;
import com.netflix.eureka.EurekaServerContext;

import io.cereebro.core.Snitch;
import io.cereebro.core.SnitchRegistry;
import io.cereebro.spring.cloud.autoconfigure.discovery.ServiceInstanceSnitch;

/**
 * Snitch registry backed by Eureka server internals.
 * <p>
 * When used as an Eureka server add-on, the service instances can be browsed
 * directly.
 * </p>
 * 
 * @author michaeltecourt
 */
public class EurekaServerSnitchRegistry implements SnitchRegistry {

    private final EurekaServerContext eurekaServerContext;
    private final ObjectMapper objectMapper;

    /**
     * Snitch registry backed by Eureka server internals.
     * <p>
     * When used as an Eureka server add-on, the service instances can be
     * browsed directly.
     * </p>
     * 
     * @param eurekaServerContext
     *            Eureka server context holding service instances.
     * @param objectMapper
     *            JSON object mapper.
     */
    public EurekaServerSnitchRegistry(EurekaServerContext eurekaServerContext, ObjectMapper objectMapper) {
        this.eurekaServerContext = Objects.requireNonNull(eurekaServerContext, "Eureka server context required");
        this.objectMapper = Objects.requireNonNull(objectMapper, "JSON object mapper required");
    }

    @Override
    public List<Snitch> getAll() {
        // @formatter:off
        return eurekaServerContext.getRegistry().getSortedApplications().stream()
            .map(Application::getInstances)
            .flatMap(Collection::stream)
            .map(EurekaServiceInstancePublic::new)
            .filter(ServiceInstanceSnitch::hasCereebroMetadata)
            .map(instance -> new ServiceInstanceSnitch(objectMapper, instance))
            .collect(Collectors.toList());
        // @formatter:on
    }

}
