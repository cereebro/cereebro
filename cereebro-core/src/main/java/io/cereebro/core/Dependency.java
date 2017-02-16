package io.cereebro.core;

import java.util.Objects;

import lombok.ToString;

/**
 * Dependency to another Component.
 * <p>
 * Nothing more than a typed Relationship at this point, but leaves room to add
 * specific attributes.
 * </p>
 * 
 * @author michaeltecourt
 */
@ToString(callSuper = true)
public class Dependency extends Relationship {

    public Dependency(Component component) {
        super(component);
    }

    public static Dependency on(Component component) {
        return new Dependency(component);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), getComponent());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }
        Dependency that = (Dependency) o;
        return Objects.equals(this.getComponent(), that.getComponent());
    }
}
