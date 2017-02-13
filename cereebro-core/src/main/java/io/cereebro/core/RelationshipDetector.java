package io.cereebro.core;

import java.util.Set;

/**
 * Detects relationships from within an application.
 * 
 * @author michaeltecourt
 */
public interface RelationshipDetector {

    /**
     * Get all detected relationships.
     * 
     * @return a Set of Relationship objects.
     */
    Set<Relationship> detect();

}
