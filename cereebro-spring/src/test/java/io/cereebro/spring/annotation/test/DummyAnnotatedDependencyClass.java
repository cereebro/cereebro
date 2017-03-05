package io.cereebro.spring.annotation.test;

import org.springframework.stereotype.Component;

import io.cereebro.core.annotation.DependencyHint;

@DependencyHint(name = "dummyClass", type = "dummyWs")
@Component
public class DummyAnnotatedDependencyClass {

}