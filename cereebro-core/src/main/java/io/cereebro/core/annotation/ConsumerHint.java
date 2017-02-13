package io.cereebro.core.annotation;

/**
 * Designate a given object as Consumer of a component.
 * 
 * @author michaeltecourt
 */
public @interface ConsumerHint {

    String name();

    String type();

}
