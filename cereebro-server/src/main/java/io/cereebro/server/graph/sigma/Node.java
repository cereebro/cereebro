package io.cereebro.server.graph.sigma;

import java.util.Objects;
import java.util.Random;

import javax.validation.constraints.NotNull;

import io.cereebro.core.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "create")
@Builder
public class Node {

    @NotNull
    private final String id;
    @NotNull
    private final String label;
    private final int x;
    private final int y;
    private final int size;

    private final Image image;

    /**
     * Create a graph Node with random coordinates and size.
     * 
     * @param id
     * @param label
     * @return Node
     */
    public static Node create(String id, String label) {
	return Node.create(id, label, randomCoordinate(), randomCoordinate(), randomSize(), Image.DEFAULT);
    }

    /**
     * Create a graph Node from Cereebro Component.
     * 
     * @param component
     * @return Node
     */
    public static Node of(Component component) {
	return Node.of(component, randomSize());
    }

    /**
     * Create a graph Node from Cereebro Component.
     * 
     * @param component
     * @param size
     * @return Node
     */
    public static Node of(Component component, int size) {
	// @formatter:off
	String cmp = component.getType() + ":" + component.getName();
	return Node.builder().id(cmp).label(cmp).x(randomCoordinate()).y(randomCoordinate()).size(size)
		.image(Image.of(component)).build();
	// @formatter:on
    }

    /**
     * Equivalent to a poor man's equals method considering only the nodes id
     * and label. Other fields are less interesting as they probably have random
     * values.
     * 
     * @param that
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
