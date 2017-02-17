package io.cereebro.snitch.spring.boot.actuate.endpoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties used to configure the {@link SnitchEndpoint}
 * 
 * @author lucwarrot
 *
 */
@ConfigurationProperties(prefix = "endpoints.cereebro")
public class SnitchEndpointProperties {

    /**
     * Enable the endpoint
     */
    private boolean enabled = true;

    /**
     * Id of the endpoint, it defines the the path of the endpoint (default:
     * /cereebro)
     */
    private String id = "cereebro";

    /**
     * Set sensitive the endpoint
     */
    private boolean sensitive = true;

    @Value("${management.address:${server.address:localhost}}")
    private String host = "localhost";

    @Value("${management.port:${server.port:8080}}")
    private String port = "8080";

    @Value("${management.contextPath:${server.contextPath:''}}")
    private String context = "";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSensitive() {
        return sensitive;
    }

    public void setSensitive(boolean sensitive) {
        this.sensitive = sensitive;
    }

    public String getHost() {
        return this.host;
    }

    public String getPort() {
        return this.port;
    }

    public String getContext() {
        return this.context;
    }

}
