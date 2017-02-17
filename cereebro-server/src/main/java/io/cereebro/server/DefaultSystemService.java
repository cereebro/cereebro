package io.cereebro.server;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.cereebro.core.Snitch;
import io.cereebro.core.SnitchRegistry;
import io.cereebro.core.System;
import io.cereebro.core.SystemResolver;

/**
 * Default SystemService that does all the wiring between the SystemResolver and
 * SnitchRegistry. No caching, the System is resolved every time.
 * 
 * @author michaeltecourt
 */
@Service
public class DefaultSystemService implements SystemService {

    private final String name;
    private final SystemResolver systemResolver;
    private final SnitchRegistry snitchRegistry;

    /**
     * Default SystemService that does all the wiring between the SystemResolver
     * and SnitchRegistry. No caching, the System is resolved every time.
     * 
     * @param name
     *            using {@code cereebro.server.system.name} property. Subject to
     *            changes.
     * @param systemResolver
     *            Used to resolve a System out of fragments.
     * @param snitchRegistry
     *            Provides all the {@link Snitch} instances available.
     */
    @Autowired
    public DefaultSystemService(@Value("${cereebro.server.system.name}") String name, SystemResolver systemResolver,
            SnitchRegistry snitchRegistry) {
        this.name = Objects.requireNonNull(name);
        this.systemResolver = Objects.requireNonNull(systemResolver);
        this.snitchRegistry = Objects.requireNonNull(snitchRegistry);
    }

    @Override
    public System getSystem() {
        return systemResolver.resolve(name, snitchRegistry);
    }

}
