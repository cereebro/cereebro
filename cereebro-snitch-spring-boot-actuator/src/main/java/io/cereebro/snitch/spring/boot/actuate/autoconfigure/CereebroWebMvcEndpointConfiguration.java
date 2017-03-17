package io.cereebro.snitch.spring.boot.actuate.autoconfigure;

import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.actuate.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

import io.cereebro.snitch.spring.boot.actuate.endpoint.CereebroSnitchEndpoint;
import io.cereebro.snitch.spring.boot.actuate.endpoint.mvc.CereebroSnitchMvcEndpoint;

@ManagementContextConfiguration
public class CereebroWebMvcEndpointConfiguration {

    @Bean
    @ConditionalOnClass(value = CereebroSnitchEndpoint.class)
    @ConditionalOnEnabledEndpoint("cereebro")
    public CereebroSnitchMvcEndpoint snitchMvcEndpoint(CereebroSnitchEndpoint delegate) {
        return new CereebroSnitchMvcEndpoint(delegate);
    }

}
