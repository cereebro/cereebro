package io.cereebro.core;

import java.net.URI;

/**
 * Tells everything about a few known components and their relationships.
 * 
 * @author michaeltecourt
 */
public interface Snitch {

    /**
     * Snitch URI.
     * 
     * @return Snitch URI.
     */
    URI getUri();

    /**
     * Describe a visible fragment of the system.
     * 
     * @return a SystemFragment.
     * @throws SnitchingException
     *             when something goes wrong while snitching.
     */
    SystemFragment snitch();

}
