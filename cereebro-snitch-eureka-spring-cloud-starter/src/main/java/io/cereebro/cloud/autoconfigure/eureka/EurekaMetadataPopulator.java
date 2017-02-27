package io.cereebro.cloud.autoconfigure.eureka;

import java.io.IOException;
import java.util.Objects;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.Snitch;
import io.cereebro.core.SnitchingException;

/**
 * Populate metadata of the eureka instance with the snitch url.
 * 
 * @author lucwarrot
 *
 */
@ConfigurationProperties(prefix = "cereebro.endpoint")
public class EurekaMetadataPopulator {

    private static final String METADATA_KEY_SNITCH_URL = "io.cereebro.snitch.url";
    private static final String METADATA_KEY_SNITCH_SYSTEM_FRAGMENT = "io.cereebro.snitch.fragment";

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
    private final ObjectMapper objectMapper;

    public EurekaMetadataPopulator(Snitch snitch, CloudEurekaInstanceConfig config, ObjectMapper mapper) {
        this.snitch = Objects.requireNonNull(snitch, "Snitch required");
        this.config = Objects.requireNonNull(config, "Cloud eureka instance config required");
        this.objectMapper = Objects.requireNonNull(mapper, "ObjectMapper required");
    }

    public void populate() {
        try {
            this.config.getMetadataMap().put(METADATA_KEY_SNITCH_URL, getEndpointLocation(this.snitch, this.config));
            String report = objectMapper.writeValueAsString(snitch.snitch());
            this.config.getMetadataMap().put(METADATA_KEY_SNITCH_SYSTEM_FRAGMENT, report);
        } catch (IOException e) {
            throw new SnitchingException(snitch.getLocation(), "Error while serializing fragment", e);
        }

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
