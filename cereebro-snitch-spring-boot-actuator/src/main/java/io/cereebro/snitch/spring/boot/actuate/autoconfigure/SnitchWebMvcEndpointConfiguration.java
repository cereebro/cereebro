package io.cereebro.snitch.spring.boot.actuate.autoconfigure;

import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.actuate.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

import io.cereebro.snitch.spring.boot.actuate.endpoint.SnitchEndpoint;
import io.cereebro.snitch.spring.boot.actuate.endpoint.mvc.SnitchMvcEndpoint;

@ManagementContextConfiguration
public class SnitchWebMvcEndpointConfiguration {

    @Bean
    @ConditionalOnClass(value = SnitchEndpoint.class)
    @ConditionalOnEnabledEndpoint("cereebro")
    public SnitchMvcEndpoint snitchMvcEndpoint(SnitchEndpoint delegate) {
        return new SnitchMvcEndpoint(delegate);
    }

}
