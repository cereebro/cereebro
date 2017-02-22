package io.cereebro.autoconfigure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import io.cereebro.core.annotation.ConsumerHint;
import io.cereebro.core.annotation.DependencyHint;
import io.cereebro.core.annotation.RelationshipHints;

@SpringBootApplication
public class RelationshipHintsApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DummyApplication.class, args);
    }

    @RelationshipHints(consumers = { @ConsumerHint(name = "firstConsumer", type = "firstType"),
            @ConsumerHint(name = "secondConsumer", type = "secondType") })
    public static class MyRelationshipHintsWithConsumersClass {

    }

    @RelationshipHints(dependencies = { @DependencyHint(name = "firstDependency", type = "firstType"),
            @DependencyHint(name = "secondDependency", type = "secondType") })
    public static class MyRelationshipHintsWithDependenciesClass {

    }

    @RelationshipHints(dependencies = { @DependencyHint(name = "firstDependency", type = "firstType") }, consumers = {
            @ConsumerHint(name = "secondConsumer", type = "secondType") })
    public static class MyRelationshipHintsWithAllClass {

    }

    public static class MyRelationshipEmptyClass {

    }

    @Bean
    @Profile("consumers")
    public MyRelationshipHintsWithConsumersClass relationsWithConsumers() {
        return new MyRelationshipHintsWithConsumersClass();
    }

    @Bean
    @Profile("dependencies")
    public MyRelationshipHintsWithDependenciesClass relationsWithDependencies() {
        return new MyRelationshipHintsWithDependenciesClass();
    }

    @Bean
    @Profile("consumers-dependencies")
    public MyRelationshipHintsWithAllClass relationsWithMerged() {
        return new MyRelationshipHintsWithAllClass();
    }

    @Bean
    @Profile("bean-consumers-dependencies")
    @RelationshipHints(dependencies = { @DependencyHint(name = "firstDependency", type = "firstType") }, consumers = {
            @ConsumerHint(name = "secondConsumer", type = "secondType") })
    public MyRelationshipEmptyClass emptyRelationshipBean() {
        return new MyRelationshipEmptyClass();
    }

}
