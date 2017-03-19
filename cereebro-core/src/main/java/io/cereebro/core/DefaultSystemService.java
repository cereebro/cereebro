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

import java.util.Objects;

/**
 * Default SystemService that does all the wiring between the SystemResolver and
 * SnitchRegistry. No caching, the System is resolved every time.
 * 
 * @author michaeltecourt
 */
public class DefaultSystemService implements SystemService {

    private final String name;
    private final SystemResolver systemResolver;
    private final SnitchRegistry snitchRegistry;

    /**
     * Default SystemService that does all the wiring between the SystemResolver
     * and SnitchRegistry. No caching, the System is resolved every time.
     * 
     * @param name
     *            System name.
     * @param systemResolver
     *            Used to resolve a System out of fragments.
     * @param snitchRegistry
     *            Provides all the {@link Snitch} instances available.
     */
    public DefaultSystemService(String name, SystemResolver systemResolver, SnitchRegistry snitchRegistry) {
        this.name = Objects.requireNonNull(name);
        this.systemResolver = Objects.requireNonNull(systemResolver, "System resolver required");
        this.snitchRegistry = Objects.requireNonNull(snitchRegistry, "Snitch registry required");
    }

    @Override
    public System get() {
        return systemResolver.resolve(name, snitchRegistry);
    }

}
