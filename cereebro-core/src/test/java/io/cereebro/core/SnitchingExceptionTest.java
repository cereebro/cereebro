package io.cereebro.core;

import java.net.URI;

import org.junit.Assert;
import org.junit.Test;

public class SnitchingExceptionTest {

    @Test
    public void uriAndMessageConstructor() {
        URI uri = URI.create("fake://nope");
        String message = "test";
        SnitchingException e = new SnitchingException(uri, message);
        Assert.assertEquals(uri, e.getSnitchLocation());
        Assert.assertEquals(message, e.getMessage());
    }

    @Test
    public void throwableAndUriConstructor() {
        URI uri = URI.create("fake://nope");
        Throwable cause = new RuntimeException("unit test");
        SnitchingException e = new SnitchingException(uri, cause);
        Assert.assertEquals(uri, e.getSnitchLocation());
        Assert.assertEquals(cause, e.getCause());
    }
}
