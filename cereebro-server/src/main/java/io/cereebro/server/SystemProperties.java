package io.cereebro.server;

import io.cereebro.core.Snitch;
import lombok.Data;

@Data
public final class SystemProperties {

    public static final String DEFAULT_NAME = "cereebro-system";

    /**
     * System name.
     */
    private String name = DEFAULT_NAME;

    /**
     * {@link Snitch}es availables.
     */
    private SnitchProperties snitch = new SnitchProperties();

}
