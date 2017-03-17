package io.cereebro.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Snitch registry aggregating multiple registries.
 * 
 * @author michaeltecourt
 */
public class CompositeSnitchRegistry implements SnitchRegistry {

    private List<SnitchRegistry> registries;

    /**
     * Snitch registry aggregating multiple registries.
     * 
     * @param registries
     */
    public CompositeSnitchRegistry(Collection<SnitchRegistry> registries) {
        this.registries = new ArrayList<>(registries);
    }

    /**
     * Snitch registry aggregating multiple registries.
     * 
     * @param registries
     * @return CompositeSnitchRegistry
     */
    public static SnitchRegistry of(SnitchRegistry... registries) {
        return new CompositeSnitchRegistry(Arrays.asList(registries));
    }

    /**
     * Snitch registry aggregating multiple registries.
     * 
     * @param registries
     * @return CompositeSnitchRegistry
     */
    public static SnitchRegistry of(Collection<SnitchRegistry> registries) {
        return new CompositeSnitchRegistry(registries);
    }

    @Override
    public List<Snitch> getAll() {
        // @formatter:off
        return registries.stream()
                .map(SnitchRegistry::getAll)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        // @formatter:on
    }

}
