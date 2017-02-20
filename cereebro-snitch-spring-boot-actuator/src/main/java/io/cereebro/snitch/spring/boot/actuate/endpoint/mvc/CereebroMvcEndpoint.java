package io.cereebro.snitch.spring.boot.actuate.endpoint.mvc;

import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import io.cereebro.snitch.spring.boot.actuate.endpoint.CereebroEndpoint;

@ConfigurationProperties(prefix = "endpoints.cereebro")
public class CereebroMvcEndpoint extends EndpointMvcAdapter {

    public CereebroMvcEndpoint(CereebroEndpoint delegate) {
        super(delegate);
    }

}
