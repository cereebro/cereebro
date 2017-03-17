package io.cereebro.spring.cloud.autoconfigure.eureka;

import static com.netflix.appinfo.InstanceInfo.PortType.SECURE;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
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
     * @param objectMapper
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
            .map(EurekaServiceInstanceCopy::new)
            .filter(ServiceInstanceSnitch::hasCereebroMetadata)
            .map(instance -> new ServiceInstanceSnitch(objectMapper, instance))
            .collect(Collectors.toList());
        // @formatter:on
    }

    /**
     * Copy of {@link EurekaServiceInstance} with an accessible constructor !
     * 
     * @author michaeltecourt
     */
    private static class EurekaServiceInstanceCopy implements ServiceInstance {

        private InstanceInfo instance;

        public EurekaServiceInstanceCopy(InstanceInfo instance) {
            this.instance = instance;
        }

        @Override
        public String getServiceId() {
            return this.instance.getAppName();
        }

        @Override
        public String getHost() {
            return this.instance.getHostName();
        }

        @Override
        public int getPort() {
            if (isSecure()) {
                return this.instance.getSecurePort();
            }
            return this.instance.getPort();
        }

        @Override
        public boolean isSecure() {
            // assume if secure is enabled, that is the default
            return this.instance.isPortEnabled(SECURE);
        }

        @Override
        public URI getUri() {
            return DefaultServiceInstance.getUri(this);
        }

        @Override
        public Map<String, String> getMetadata() {
            return this.instance.getMetadata();
        }
    }

}
