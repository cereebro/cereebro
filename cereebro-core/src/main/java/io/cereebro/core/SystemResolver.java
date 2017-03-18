package io.cereebro.core;

import java.util.Collection;

/**
 * Builds a System big picture out of partial snapshots.
 * 
 * @author michaeltecourt
 */
public interface SystemResolver {

    /**
     * Resolve a complete System by browsing a SnitchRegistry.
     * 
     * @param systemName
     *            Name of the System.
     * @param snitchRegistry
     *            Snitch registry to be browsed.
     * @return resolved System
     */
    System resolve(String systemName, SnitchRegistry snitchRegistry);

    /**
     * Resolve a complete System out of system fragments.
     * 
     * @param systemName
     *            Name of the system.
     * @param fragments
     *            Sum of system fragments to be assembled.
     * @return resolved System
     */
    System resolve(String systemName, Collection<SystemFragment> fragments);

}
