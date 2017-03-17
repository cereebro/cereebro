package io.cereebro.spring;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.Snitch;
import io.cereebro.core.SnitchingException;
import io.cereebro.core.SystemFragment;

/**
 * Snitch backed by a static Spring {@link Resource}. The resource must contain
 * a valid JSON representation of a {@link SystemFragment}.
 * 
 * @author michaeltecourt
 */
public class ResourceSnitch implements Snitch {

    private final ObjectMapper objectMapper;
    private final Resource resource;

    /**
     * Snitch backed by a static Spring {@link Resource}. The resource must
     * contain a valid JSON representation of a {@link SystemFragment}.
     * 
     * @param mapper
     *            Jackson object mapper
     * @param resource
     *            Resource containing a JSON SystemFragment.
     */
    public ResourceSnitch(ObjectMapper mapper, Resource resource) {
        this.resource = Objects.requireNonNull(resource, "Resource required");
        this.objectMapper = Objects.requireNonNull(mapper, "ObjectMapper required");
    }

    /**
     * Snitch backed by a static Spring {@link Resource}. The resource must
     * contain a valid JSON representation of a {@link SystemFragment}.
     * 
     * @param mapper
     *            Jackson object mapper
     * @param resource
     *            Resource containing a JSON SystemFragment.
     * @return ResourceSnitch
     */
    public static ResourceSnitch of(ObjectMapper mapper, Resource resource) {
        return new ResourceSnitch(mapper, resource);
    }

    @Override
    public URI getUri() {
        try {
            return resource.getURI();
        } catch (IOException e) {
            throw new IllegalStateException("Error reading Snitch Resource URI : " + resource.getDescription(), e);
        }
    }

    @Override
    public SystemFragment snitch() {
        try {
            return objectMapper.readValue(resource.getInputStream(), SystemFragment.class);
        } catch (IOException e) {
            throw new SnitchingException(getUri(), "Error while reading resource content", e);
        }
    }

}
