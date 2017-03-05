package io.cereebro.spring.annotation.test.scan;

import org.springframework.stereotype.Component;

import io.cereebro.core.annotation.ConsumerHint;

@Component
@ConsumerHint(name = "wolverine", type = "xmen")
public class ConsumerHintAnnotatedClass {

}
