package io.cereebro.spring.boot.autoconfigure.actuate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.actuate.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import io.cereebro.core.Component;
import io.cereebro.core.CompositeRelationshipDetector;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.spring.boot.autoconfigure.CereebroProperties;

@ManagementContextConfiguration
@EnableConfigurationProperties({ CereebroProperties.class })
@ConditionalOnClass(MvcEndpoint.class)
public class CereebroWebMvcEndpointConfiguration {

    @Autowired
    private CereebroProperties cereebroProperties;

    @Bean
    @ConditionalOnEnabledEndpoint("cereebro")
    public CereebroSnitchMvcEndpoint snitchMvcEndpoint(List<RelationshipDetector> detectors) {
        Component applicationComponent = cereebroProperties.getApplication().getComponent().toComponent();
        return new CereebroSnitchMvcEndpoint(applicationComponent, new CompositeRelationshipDetector(detectors));
    }

}
