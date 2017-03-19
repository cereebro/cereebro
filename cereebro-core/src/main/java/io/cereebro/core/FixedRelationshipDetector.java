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
