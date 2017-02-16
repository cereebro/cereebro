package io.cereebro.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Component that is dependent.
 * <p>
 * Nothing more than a typed Relationship at this point, but leaves room to add
 * specific attributes.
 * </p>
 * 
 * @author michaeltecourt
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Consumer extends Relationship {

    public Consumer(Component component) {
        super(component);
    }

    public static Consumer by(Component consumer) {
        return new Consumer(consumer);
    }
}
