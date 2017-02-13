package io.cereebro.core;

import java.net.URI;

/**
 * Tells everything about a few known components and their relationships.
 * 
 * @author michaeltecourt
 */
public interface Snitch {

    /**
     * Where the snitch is located.
     * 
     * @return Snitch URI.
     */
    URI getLocation();

    /**
     * Describe a visible fragment of the system.
     * 
     * @return a SystemFragment.
     */
    SystemFragment snitch();

}
