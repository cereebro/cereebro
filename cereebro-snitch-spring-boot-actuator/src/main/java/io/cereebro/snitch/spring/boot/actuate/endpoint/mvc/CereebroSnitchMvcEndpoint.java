package io.cereebro.snitch.spring.boot.actuate.endpoint.mvc;

import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import io.cereebro.snitch.spring.boot.actuate.endpoint.CereebroSnitchEndpoint;

@ConfigurationProperties(prefix = "endpoints.cereebro")
public class CereebroSnitchMvcEndpoint extends EndpointMvcAdapter {

    public CereebroSnitchMvcEndpoint(CereebroSnitchEndpoint delegate) {
        super(delegate);
    }

}
