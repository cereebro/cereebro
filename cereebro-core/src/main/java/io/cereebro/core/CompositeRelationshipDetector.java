/*
 * Copyright © 2009 the original authors (http://cereebro.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
