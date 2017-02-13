package io.cereebro.core;

import java.util.HashSet;
import java.util.Set;

public class TestHelper {

    public static Component componentA() {
        return Component.of("a", "a");
    }

    public static Component componentB() {
        return Component.of("b", "b");
    }

    public static Component componentC() {
        return Component.of("c", "c");
    }

    public static Dependency dependencyOnB() {
        return Dependency.on(componentB());
    }

    public static Consumer consumedByC() {
        return Consumer.by(componentC());
    }

    public static Set<Relationship> relationshipSetOfDependencyBAndConsumerC() {
        Set<Relationship> deps = new HashSet<>();
        deps.add(dependencyOnB());
        deps.add(consumedByC());
        return deps;
    }
}
