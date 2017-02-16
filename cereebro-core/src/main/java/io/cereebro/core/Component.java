package io.cereebro.core;

import java.util.Objects;

import lombok.ToString;

/**
 * Cereebro base unit of work.
 * 
 * @author michaeltecourt
 */
@ToString
public class Component {

    private final String name;

    private final String type;

    /**
     * Cereebro base unit of work.
     * 
     * @param name
     * @param type
     */
    public Component(String name, String type) {
        this.name = Objects.requireNonNull(name, "Component name required");
        this.type = Objects.requireNonNull(type, "Component type required");
    }

    /**
     * Cereebro base unit of work.
     * 
     * @param name
     * @param type
     * @return Component
     */
    public static Component of(String name, String type) {
        return new Component(name, type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), name, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }
        Component that = (Component) o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.type, that.type);
    }

    /**
     * Component name.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Component type.
     * 
     * @return type
     */
    public String getType() {
        return type;
    }

}
