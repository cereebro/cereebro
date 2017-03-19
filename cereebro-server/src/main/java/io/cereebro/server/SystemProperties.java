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
package io.cereebro.server;

import io.cereebro.core.Snitch;
import lombok.Data;

@Data
public final class SystemProperties {

    public static final String DEFAULT_PATH = "/cereebro/system";
    public static final String DEFAULT_NAME = "cereebro";

    /**
     * System name.
     */
    private String name = DEFAULT_NAME;

    /**
     * Path of the system HTML page and JSON resource.
     */
    private String path = DEFAULT_PATH;

    /**
     * Available {@link Snitch} resources.
     */
    private SnitchProperties snitch = new SnitchProperties();

}
