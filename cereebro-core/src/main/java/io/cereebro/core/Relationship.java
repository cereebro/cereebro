package io.cereebro.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
@AllArgsConstructor
@Getter
public abstract class Relationship {

    private final Component component;

}
