package io.cereebro.core;

import java.util.Objects;

/**
 * Default SystemService that does all the wiring between the SystemResolver and
 * SnitchRegistry. No caching, the System is resolved every time.
 * 
 * @author michaeltecourt
 */
public class DefaultSystemService implements SystemService {

    private final String name;
    private final SystemResolver systemResolver;
    private final SnitchRegistry snitchRegistry;

    /**
     * Default SystemService that does all the wiring between the SystemResolver
     * and SnitchRegistry. No caching, the System is resolved every time.
     * 
     * @param name
     *            System name.
     * @param systemResolver
     *            Used to resolve a System out of fragments.
     * @param snitchRegistry
     *            Provides all the {@link Snitch} instances available.
     */
    public DefaultSystemService(String name, SystemResolver systemResolver, SnitchRegistry snitchRegistry) {
        this.name = Objects.requireNonNull(name);
        this.systemResolver = Objects.requireNonNull(systemResolver, "System resolver required");
        this.snitchRegistry = Objects.requireNonNull(snitchRegistry, "Snitch registry required");
    }

    @Override
    public System get() {
        return systemResolver.resolve(name, snitchRegistry);
    }

}
