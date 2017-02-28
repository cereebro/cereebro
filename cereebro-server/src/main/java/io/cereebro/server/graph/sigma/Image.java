package io.cereebro.server.graph.sigma;

import java.util.Objects;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Representation of an Image used by Linkurious(see:
 * https://github.com/Linkurious/linkurious.js/tree/develop/plugins/sigma.
 * renderers.linkurious#images)
 * 
 * @author lucwarrot
 *
 */
@Data
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Image {

    public static final Image DEFAULT = new Image();

    public static final Image CASSANDRA = Image.create("/images/cassandra.png");
    public static final Image DATABASE = Image.create("/images/database.png");
    public static final Image REST_API = Image.create("/images/rest.png");

    /**
     * mandatory image URL
     */
    @NonNull
    private final String url;

    /**
     * Ratio of image clipping disk compared to node size (def 1.0) - see
     * example to how we adapt this to differenmt shapes
     */
    private final double clip;

    /**
     * Ratio of how to scale the image, compared to node size, default 1.0
     */
    private final double scale;

    /**
     * numeric width - important for correct scaling if w/h ratio is not 1.0
     */
    private final double w;

    /**
     * numeric height - important for correct scaling if w/h ratio is not 1.0
     */
    private final double h;

    @Override
    public boolean equals(Object o) {
	if (o == this) {
	    return true;
	} else if (o == null || !getClass().equals(o.getClass())) {
	    return false;
	}
	Image that = (Image) o;
	return Objects.equals(this.url, that.url);
    }

    @Override
    public int hashCode() {
	return Objects.hash(getClass(), url);
    }

    public static Image create(String url) {
	return Image.create(url, 1f, 1f, 1f, 1f);
    }

    /**
     * Create an {@link Image} from the type of the given component.
     * 
     * @param component
     * @return
     */
    public static Image of(Component component) {
	switch (component.getType().toUpperCase()) {
	case (ComponentType.CASSANDRA):
	    return Image.CASSANDRA;
	case (ComponentType.DATABASE):
	    return Image.DATABASE;
	case (ComponentType.REST_API):
	    return Image.REST_API;
	}
	return Image.DEFAULT;
    }

}
