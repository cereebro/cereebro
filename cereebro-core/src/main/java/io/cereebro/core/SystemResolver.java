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
     * @param snitchRegistry
     * @return System
     */
    System resolve(String systemName, SnitchRegistry snitchRegistry);

    /**
     * Resolve a complete System out of system fragments.
     * 
     * @param systemName
     * @param fragments
     * @return System
     */
    System resolve(String systemName, Collection<SystemFragment> fragments);

}
