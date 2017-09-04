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
package io.cereebro.server.graph.sigma;

import io.cereebro.core.Component;
import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(staticName = "create")
public final class Edge {

    @NonNull
    private final String id;
    @NonNull
    private final String source;
    @NonNull
    private final String target;

    public static Edge to(Component component, Dependency dependency) {
        String from = component.asString().toLowerCase();
        String to = dependency.getComponent().asString().toLowerCase();
        return Edge.create(from + "-to-" + to, from, to);
    }

    public static Edge from(Component component, Consumer consumer) {
        String to = component.asString().toLowerCase();
        String from = consumer.getComponent().asString().toLowerCase();
        return Edge.create(from + "-to-" + to, from, to);
    }

}
