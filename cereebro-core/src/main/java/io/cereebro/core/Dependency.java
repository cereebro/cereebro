package io.cereebro.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Dependency to another Component.
 * <p>
 * Nothing more than a typed Relationship at this point, but leaves room to add
 * specific attributes.
 * </p>
 * 
 * @author michaeltecourt
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Dependency extends Relationship {

    public Dependency(Component c) {
        super(c);
    }

    public static Dependency on(Component c) {
        return new Dependency(c);
    }
}
