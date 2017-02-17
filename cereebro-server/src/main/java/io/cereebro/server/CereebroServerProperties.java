package io.cereebro.server;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * TODO see how this can be merged within CereebroProperties.
 * 
 * @author michaeltecourt
 */
@ConfigurationProperties("cereebro.server")
@Data
public final class CereebroServerProperties {

    private SnitchProperties snitch = new SnitchProperties();

}
