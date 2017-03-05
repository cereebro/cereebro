package io.cereebro.spring.annotation.test;

import io.cereebro.core.annotation.ConsumerHint;
import io.cereebro.core.annotation.RelationshipHints;

@RelationshipHints(consumers = { @ConsumerHint(name = "firstConsumer", type = "firstType"),
        @ConsumerHint(name = "secondConsumer", type = "secondType") })
public class RelationshipHintsWithConsumersClass {

}