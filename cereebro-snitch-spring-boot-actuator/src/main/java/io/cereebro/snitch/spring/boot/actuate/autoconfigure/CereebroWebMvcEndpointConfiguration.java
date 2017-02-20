package io.cereebro.snitch.spring.boot.actuate.autoconfigure;

import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.actuate.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

import io.cereebro.snitch.spring.boot.actuate.endpoint.CereebroEndpoint;
import io.cereebro.snitch.spring.boot.actuate.endpoint.mvc.CereebroMvcEndpoint;

@ManagementContextConfiguration
public class CereebroWebMvcEndpointConfiguration {

    @Bean
    @ConditionalOnClass(value = CereebroEndpoint.class)
    @ConditionalOnEnabledEndpoint("cereebro")
    public CereebroMvcEndpoint snitchMvcEndpoint(CereebroEndpoint delegate) {
        return new CereebroMvcEndpoint(delegate);
    }

}
