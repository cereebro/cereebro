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
package io.cereebro.spring.boot.autoconfigure;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import lombok.Data;

@Data
public final class ComponentProperties {

    private String name;
    private String type = ComponentType.HTTP_APPLICATION;

    /**
     * Convert this properties class to a core {@link Component} object.
     * 
     * @return io.cereebro.core.Component
     */
    public Component toComponent() {
        return Component.of(name, type);
    }
}
