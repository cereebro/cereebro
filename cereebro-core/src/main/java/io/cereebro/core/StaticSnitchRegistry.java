package io.cereebro.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.ToString;

/**
 * Registry holding a static collection of snitches.
 * 
 * @author michaeltecourt
 */
@ToString
public class StaticSnitchRegistry implements SnitchRegistry {

    private final List<Snitch> registry;

    /**
     * Registry holding a static collection of snitches. Duplicates are filtered
     * based on their location.
     * 
     * @param snitches
     *            Snitch instances to register.
     */
    public StaticSnitchRegistry(Snitch... snitches) {
        this(Arrays.asList(snitches));
    }

    /**
     * Registry holding a static collection of snitches. Duplicates are filtered
     * based on their location.
     * 
     * @param snitches
     *            Snitch instances to register.
     */
    public StaticSnitchRegistry(Collection<Snitch> snitches) {
        this.registry = Collections.unmodifiableList(filterDuplicateLocations(snitches));
    }

    /**
     * Registry holding a static collection of snitches. Duplicates are filtered
     * based on their location.
     * 
     * @param snitches
     *            Snitch instances to register.
     * @return StaticSnitchRegistry instance.
     */
    public static SnitchRegistry of(Snitch... snitches) {
        return new StaticSnitchRegistry(snitches);
    }

    /**
     * Registry holding a static collection of snitches. Duplicates are filtered
     * based on their location.
     * 
     * @param snitches
     *            Snitch instances to register.
     * @return StaticSnitchRegistry instance.
     */
    public static SnitchRegistry of(Collection<Snitch> snitches) {
        return new StaticSnitchRegistry(snitches);
    }

    /**
     * Filter snitch duplicates based on the Snitch URI.
     * 
     * @param snitches
     *            Snitch instances to be filtered.
     * @return a List of Snitch objects without any duplicate URI.
     */
    public static List<Snitch> filterDuplicateLocations(Collection<Snitch> snitches) {
        // @formatter:off
        return snitches.stream()
            .collect(Collectors.groupingBy(Snitch::getUri))
            .values()
            .stream()
            .flatMap(list -> Stream.of(list.get(0)))
            .collect(Collectors.toList());
        // @formatter:on
    }

    @Override
    public List<Snitch> getAll() {
        return new ArrayList<>(registry);
    }

}
