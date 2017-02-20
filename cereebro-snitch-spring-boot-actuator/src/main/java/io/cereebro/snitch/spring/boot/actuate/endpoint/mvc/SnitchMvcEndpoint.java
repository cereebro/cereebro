package io.cereebro.snitch.spring.boot.actuate.endpoint.mvc;

import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import io.cereebro.snitch.spring.boot.actuate.endpoint.SnitchEndpoint;

@ConfigurationProperties(prefix = "endpoints.cereebro")
public class SnitchMvcEndpoint extends EndpointMvcAdapter {

    public SnitchMvcEndpoint(SnitchEndpoint delegate) {
        super(delegate);
    }

}
