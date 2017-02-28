package io.cereebro.server.graph.sigma;

import org.junit.Assert;
import org.junit.Test;

import io.cereebro.core.Component;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link Image} unit test
 * 
 * @author lucwarrot
 *
 */
public class ImageTest {

    @Test
    public void verifyHashcodeEquals() {
	// @formatter:off
        EqualsVerifier.forClass(Image.class)
            .usingGetClass()
            .withIgnoredFields("clip", "scale", "w", "h")
            .suppress(Warning.STRICT_INHERITANCE)
            .verify();
        // @formatter:on
    }

    @Test
    public void defaultSize() {
	Image image = Image.create("/deadpool.ico");
	Assert.assertTrue(image.getScale() == 1f);
	Assert.assertTrue(image.getClip() == 1f);
	Assert.assertTrue(image.getW() == 1f);
	Assert.assertTrue(image.getH() == 1f);
    }

    @Test
    public void imageFromComponent() {
	Image image = Image.of(Component.of("cereebro", "cassandra"));
	Assert.assertEquals(Image.CASSANDRA, image);
	Assert.assertNotEquals(Image.REST_API, image);
    }

    @Test
    public void testToString() {
	Image image = Image.CASSANDRA;
	String toString = image.toString();
	Assert.assertTrue(toString.contains("cassandra"));
	Assert.assertTrue(toString.contains("1"));
    }

}
