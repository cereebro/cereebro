package io.cereebro.server;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Cereebro Server properties.
 * 
 * @author michaeltecourt
 */
@ConfigurationProperties("cereebro.server")
@Data
public final class CereebroServerProperties {

    private SystemProperties system = new SystemProperties();

}
