package io.cereebro.server;

import io.cereebro.core.Snitch;
import lombok.Data;

@Data
public final class SystemProperties {

    public static final String DEFAULT_NAME = "cereebro";

    /**
     * System name.
     */
    private String name = DEFAULT_NAME;

    /**
     * Available {@link Snitch} resources.
     */
    private SnitchProperties snitch = new SnitchProperties();

}
