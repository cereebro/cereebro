package io.cereebro.spring.annotation.test;

import io.cereebro.core.annotation.ConsumerHint;
import io.cereebro.core.annotation.DependencyHint;
import io.cereebro.core.annotation.RelationshipHints;

@RelationshipHints(dependencies = { @DependencyHint(name = "firstDependency", type = "firstType") }, consumers = {
        @ConsumerHint(name = "secondConsumer", type = "secondType") })
public class RelationshipHintsWithAllClass {

}