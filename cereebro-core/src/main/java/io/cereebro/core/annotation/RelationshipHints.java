package io.cereebro.core.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation group {@link ConsumerHint} and {@link DependencyHint}. It is
 * possible to add many relationships in one annotation.
 * 
 * @author lucwarrot
 * 
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface RelationshipHints {

    ConsumerHint[] consumers() default {};

    DependencyHint[] dependencies() default {};
}
