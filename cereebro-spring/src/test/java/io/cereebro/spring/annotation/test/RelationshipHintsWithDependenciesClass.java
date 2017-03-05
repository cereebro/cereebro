package io.cereebro.spring.annotation.test;

import io.cereebro.core.annotation.DependencyHint;
import io.cereebro.core.annotation.RelationshipHints;

@RelationshipHints(dependencies = { @DependencyHint(name = "firstDependency", type = "firstType"),
        @DependencyHint(name = "secondDependency", type = "secondType") })
public class RelationshipHintsWithDependenciesClass {

}