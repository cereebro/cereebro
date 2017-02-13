package io.cereebro.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * RelationshipDetector holding an immutable collection of Relationship objects.
 * 
 * @author michaeltecourt
 */
public class FixedRelationshipDetector implements RelationshipDetector {

    private final Set<Relationship> relationships;

    /**
     * RelationshipDetector holding an immutable collection of Relationship
     * objects.
     * 
     * @param rels
     *            Relationships that will be defensively copied (any change to
     *            the original collection will not be reflected by this object).
     */
    public FixedRelationshipDetector(Collection<Relationship> rels) {
        Objects.requireNonNull(rels, "Relationship collection required");
        this.relationships = Collections.unmodifiableSet(new HashSet<>(rels));
    }

    @Override
    public Set<Relationship> detect() {
        return new HashSet<>(relationships);
    }

}
