package io.cereebro.core;

import java.util.Objects;

import lombok.ToString;

/**
 * Base relationship class.
 * 
 * <p>
 * Only a {@link Component} holder at this time, but leaves room to add details
 * about the relationship itself : qualifier, cardinality...
 * </p>
 * 
 * @author michaeltecourt
 */
@ToString
public abstract class Relationship {

    private final Component component;

    protected Relationship(Component component) {
        this.component = Objects.requireNonNull(component, "Component required");
    }

    /**
     * Related component.
     * 
     * @return Component
     */
    public Component getComponent() {
        return component;
    }

}
