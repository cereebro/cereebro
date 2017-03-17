package io.cereebro.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CompositeRelationshipDetector implements RelationshipDetector {

    private final List<RelationshipDetector> detectors;

    public CompositeRelationshipDetector(Collection<RelationshipDetector> detectors) {
        this.detectors = new ArrayList<>();
        Objects.requireNonNull(detectors, "detectors required");
        this.detectors.addAll(detectors);
    }

    @Override
    public Set<Relationship> detect() {
        // @formatter:off
        return detectors.stream()
                .map(RelationshipDetector::detect)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        // @formatter:on
    }

}
