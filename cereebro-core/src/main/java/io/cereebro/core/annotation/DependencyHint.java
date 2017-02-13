package io.cereebro.core.annotation;

/**
 * Designate a given object as a component Dependency.
 * 
 * @author michaeltecourt
 *
 */
public @interface DependencyHint {

    String name();

    String type();

}
