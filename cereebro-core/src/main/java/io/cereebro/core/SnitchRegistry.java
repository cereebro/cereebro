package io.cereebro.core;

import java.util.List;

/**
 * Registry of available {@link Snitch}es.
 * 
 * @author michaeltecourt
 */
public interface SnitchRegistry {

    /**
     * All the snitches declared in the registry.
     * 
     * @return a Set of Snitch objects.
     */
    List<Snitch> getAll();

}
