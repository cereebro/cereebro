package io.cereebro.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Designate a given object as Consumer of a component.
 * 
 * @author michaeltecourt
 * 
 * @see RelationshipHints
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE, ElementType.METHOD })
public @interface ConsumerHint {

    String name();

    String type();

}
