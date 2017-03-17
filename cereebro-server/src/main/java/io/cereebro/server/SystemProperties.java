package io.cereebro.server;

import io.cereebro.core.Snitch;
import lombok.Data;

@Data
public final class SystemProperties {

    public static final String DEFAULT_PATH = "/cereebro/system";
    public static final String DEFAULT_NAME = "cereebro";

    /**
     * System name.
     */
    private String name = DEFAULT_NAME;

    /**
     * Path of the system HTML page and JSON resource.
     */
    private String path = DEFAULT_PATH;

    /**
     * Available {@link Snitch} resources.
     */
    private SnitchProperties snitch = new SnitchProperties();

}
