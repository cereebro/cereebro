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
package io.cereebro.snitch.eureka;

import java.net.URI;

import io.cereebro.core.SnitchEndpoint;
import io.cereebro.core.StaticSnitch;
import io.cereebro.core.SystemFragment;

/**
 * Just for tests.
 * 
 * @author michaeltecourt
 */
class StaticSnitchEndpoint extends StaticSnitch implements SnitchEndpoint {

    /**
     * Just for tests.
     * 
     * @param uri
     *            Static URI.
     * @param systemFragment
     *            Static Frag.
     */
    StaticSnitchEndpoint(URI uri, SystemFragment systemFragment) {
        super(uri, systemFragment);
    }

    /**
     * Just for tests.
     * 
     * @param uri
     *            Static URI.
     */
    StaticSnitchEndpoint(URI uri) {
        super(uri);
    }

}
