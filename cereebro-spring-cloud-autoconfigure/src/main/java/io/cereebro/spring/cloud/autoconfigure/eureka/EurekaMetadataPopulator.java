package io.cereebro.spring.cloud.autoconfigure.eureka;

import java.io.IOException;
import java.util.Objects;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.Snitch;
import io.cereebro.core.SnitchingException;
import io.cereebro.spring.cloud.autoconfigure.discovery.CereebroDiscoveryClientConstants;

/**
 * Populate metadata of the eureka instance with the snitch url.
 * 
 * @author lucwarrot
 *
 */
@ConfigurationProperties(prefix = "cereebro.endpoint")
public class EurekaMetadataPopulator {

    /**
     * Absolute URL of the endpoint (ex: "http://localhost:8080/cereebro"). <br>
     * Will take precedence over the {@link #urlPath} if both are set.
     */
    private String url;

    /**
     * Relative path of the endpoint location (ex: "/cereebro").
     */
    private String urlPath;

    private final Snitch snitch;
    private final CloudEurekaInstanceConfig config;
    private final ObjectMapper objectMapper;

    public EurekaMetadataPopulator(Snitch snitch, CloudEurekaInstanceConfig config, ObjectMapper mapper) {
        this.snitch = Objects.requireNonNull(snitch, "Snitch required");
        this.config = Objects.requireNonNull(config, "Cloud eureka instance config required");
        this.objectMapper = Objects.requireNonNull(mapper, "ObjectMapper required");
    }

    public void populate() {
        try {
            this.config.getMetadataMap().put(CereebroDiscoveryClientConstants.METADATA_KEY_SNITCH_URL,
                    getEndpointLocation(this.snitch, this.config));
            String report = objectMapper.writeValueAsString(snitch.snitch());
            this.config.getMetadataMap().put(CereebroDiscoveryClientConstants.METADATA_KEY_SNITCH_SYSTEM_FRAGMENT,
                    report);
        } catch (IOException e) {
            throw new SnitchingException(snitch.getLocation(), "Error while serializing fragment", e);
        }

    }

    /**
     * Absolute URL of the endpoint (ex: "http://localhost:8080/cereebro"). <br>
     * Will take precedence over the {@link #urlPath} if both are set.
     * 
     * @return url
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String absolutePath) {
        this.url = absolutePath;
    }

    /**
     * Relative path of the endpoint location (ex: "/cereebro").
     * 
     * @return urlPath
     */
    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String relativePath) {
        this.urlPath = relativePath;
    }

    /**
     * Get the endpoint location of the current cereebro instance.
     * 
     * @param snitch
     * @param config
     * @return
     */
    protected String getEndpointLocation(Snitch snitch, CloudEurekaInstanceConfig config) {
        if (!StringUtils.isEmpty(url)) {
            return url;
        }
        if (!StringUtils.isEmpty(urlPath)) {
            return httpHostname(config.getHostName(true), config.getNonSecurePort()) + urlPath;
        }
        return httpHostname(config.getHostName(true), config.getNonSecurePort()) + snitch.getLocation();
    }

    private String httpHostname(String hostname, int port) {
        return "http://" + hostname + ":" + port;
    }
}
