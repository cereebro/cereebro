package io.cereebro.spring.boot.autoconfigure;

import java.util.UUID;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import lombok.Data;

@Data
public final class ComponentProperties {

    private String name = UUID.randomUUID().toString();
    private String type = ComponentType.HTTP_APPLICATION;

    /**
     * Convert this properties class to a core {@link Component} object.
     * 
     * @return io.cereebro.core.Component
     */
    public Component toComponent() {
        return Component.of(name, type);
    }
}
