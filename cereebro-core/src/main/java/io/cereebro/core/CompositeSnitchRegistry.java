/*
 * Copyright © 2017 the original authors (http://cereebro.io)
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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Snitch registry aggregating multiple registries.
 * 
 * @author michaeltecourt
 */
public class CompositeSnitchRegistry implements SnitchRegistry {

    private List<SnitchRegistry> registries;

    /**
     * Snitch registry aggregating multiple registries.
     * 
     * @param registries
     *            Registries to wrap.
     */
    public CompositeSnitchRegistry(Collection<SnitchRegistry> registries) {
        this.registries = new ArrayList<>(registries);
    }

    /**
     * Snitch registry aggregating multiple registries.
     * 
     * @param registries
     *            Registries to wrap.
     * @return CompositeSnitchRegistry
     */
    public static SnitchRegistry of(SnitchRegistry... registries) {
        return new CompositeSnitchRegistry(Arrays.asList(registries));
    }

    /**
     * Snitch registry aggregating multiple registries.
     * 
     * @param registries
     *            Registries to wrap.
     * @return CompositeSnitchRegistry
     */
    public static SnitchRegistry of(Collection<SnitchRegistry> registries) {
        return new CompositeSnitchRegistry(registries);
    }

    @Override
    public List<Snitch> getAll() {
        // @formatter:off
        return registries.stream()
                .map(SnitchRegistry::getAll)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        // @formatter:on
    }

}
