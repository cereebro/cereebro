package io.cereebro.spring.boot.autoconfigure;

import io.cereebro.core.Dependency;
import lombok.Data;

@Data
public final class DependencyProperties {

    private ComponentProperties component;

    public Dependency toDependency() {
        return Dependency.on(component.toComponent());
    }

}
