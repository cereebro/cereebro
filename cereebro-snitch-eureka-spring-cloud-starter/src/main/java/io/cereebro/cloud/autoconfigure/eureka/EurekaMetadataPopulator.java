package io.cereebro.cloud.autoconfigure.eureka;

import java.util.Objects;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;
import org.springframework.util.StringUtils;

import io.cereebro.core.Snitch;

/**
 * Populate metadata of the eureka instance with the snitch url.
 * 
 * @author lucwarrot
 *
 */
@ConfigurationProperties(prefix = "cereebro.endpoint")
public class EurekaMetadataPopulator {

    private static final String METADATA_KEY = "io.cereebro.endpoint";

    /**
     * absolute path of the endpoint location (ex:
     * "http://localhost:8080/cereebro")
     */
    private String absolutePath;

    /**
     * relative path of the endpoint location (ex: "/cereebro")
     */
    private String relativePath;

    private final Snitch snitch;
    private final CloudEurekaInstanceConfig config;

    public EurekaMetadataPopulator(Snitch snitch, CloudEurekaInstanceConfig config) {
        this.snitch = Objects.requireNonNull(snitch, "Snitch required");
        this.config = Objects.requireNonNull(config, "Cloud eureka instance config required");
    }

    public void register() {
        this.config.getMetadataMap().put(METADATA_KEY, getEndpointLocation(this.snitch, this.config));
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    /**
     * Get the endpoint location of the current cereebro instance.
     * 
     * @param snitch
     * @param config
     * @return
     */
    protected String getEndpointLocation(Snitch snitch, CloudEurekaInstanceConfig config) {
        if (!StringUtils.isEmpty(absolutePath)) {
            return absolutePath;
        }
        if (!StringUtils.isEmpty(relativePath)) {
            return httpHostname(config.getHostName(true), config.getNonSecurePort()) + relativePath;
        }
        return httpHostname(config.getHostName(true), config.getNonSecurePort()) + snitch.getLocation();
    }

    private String httpHostname(String hostname, int port) {
        return "http://" + hostname + ":" + port;
    }
}
