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

import java.util.Objects;
import java.util.Random;

import io.cereebro.core.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(staticName = "create")
@Builder
public class Node {

    @NonNull
    private final String id;
    @NonNull
    private final String label;
    private final int x;
    private final int y;
    private final int size;

    /**
     * Create a graph Node with random coordinates and size.
     * 
     * @param id
     *            Node unique ID.
     * @param label
     *            Label displayed when hovering the node.
     * @return Node
     */
    public static Node create(String id, String label) {
        return Node.create(id, label, randomCoordinate(), randomCoordinate(), randomSize());
    }

    /**
     * Create a graph Node from Cereebro Component.
     * 
     * @param component
     *            Component to picture as a Node.
     * @return Node
     */
    public static Node of(Component component) {
        return Node.of(component, randomSize());
    }

    /**
     * Create a graph Node from Cereebro Component.
     * 
     * @param component
     *            Component to picture as a Node.
     * @param size
     *            Size of the node.
     * @return Node
     */
    public static Node of(Component component, int size) {
        // @formatter:off
	return Node.builder()
	        .id(component.asString())
	        .label(component.asString())
	        .x(randomCoordinate())
	        .y(randomCoordinate())
	        .size(size)
		// .image(Image.of(component))
		.build();
	// @formatter:on
    }

    /**
     * Equivalent to a poor man's equals method considering only the nodes id
     * and label. Other fields are less interesting as they probably have random
     * values.
     * 
     * @param o
     *            Object to match.
     * @return {@code true} if the Node identifiers are the same, {@code false}
     *         otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }
        Node that = (Node) o;
        return Objects.equals(this.id, that.id) && Objects.equals(this.label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), id, label);
    }

    public static int randomSize() {
        return Math.max(30, new Random().nextInt(60));
    }

    public static int randomCoordinate() {
        return new Random().nextInt(64);
    }

}
