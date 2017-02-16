package io.cereebro.snitch.spring.boot.actuate.endpoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "endpoints.cereebro")
public class SnitchEndpointProperties {

    private boolean enabled = true;

    private String id = "cereebro";

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
