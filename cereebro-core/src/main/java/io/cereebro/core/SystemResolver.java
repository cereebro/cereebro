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

import java.util.Collection;

/**
 * Builds a System big picture out of partial snapshots.
 * 
 * @author michaeltecourt
 */
public interface SystemResolver {

    /**
     * Resolve a complete System by browsing a SnitchRegistry.
     * 
     * @param systemName
     *            Name of the System.
     * @param snitchRegistry
     *            Snitch registry to be browsed.
     * @return resolved System
     */
    System resolve(String systemName, SnitchRegistry snitchRegistry);

    /**
     * Resolve a complete System out of system fragments.
     * 
     * @param systemName
     *            Name of the system.
     * @param fragments
     *            Sum of system fragments to be assembled.
     * @return resolved System
     */
    System resolve(String systemName, Collection<SystemFragment> fragments);

}
