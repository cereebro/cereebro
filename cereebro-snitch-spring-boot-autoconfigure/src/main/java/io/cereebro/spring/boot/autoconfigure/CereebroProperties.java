package io.cereebro.spring.boot.autoconfigure;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import lombok.Data;

@ConfigurationProperties(prefix = "cereebro", ignoreUnknownFields = true)
@Data
public final class CereebroProperties implements EnvironmentAware {

    private ComponentRelationshipsProperties application = new ComponentRelationshipsProperties();

    private Environment environment;

    @Override
    public void setEnvironment(Environment env) {
        this.environment = env;
        // set the application name from the environment,
        // but allow the defaults to use relaxed binding
        // (shamelessly copied from Spring Boot)
        RelaxedPropertyResolver springPropertyResolver = new RelaxedPropertyResolver(this.environment,
                "spring.application.");
        String springAppName = springPropertyResolver.getProperty("name");
        if (StringUtils.hasText(springAppName)) {
            application.getComponent().setName(springAppName);
        }
    }

}
