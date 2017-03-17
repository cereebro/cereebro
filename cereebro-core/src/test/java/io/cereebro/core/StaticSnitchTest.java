package io.cereebro.core;

import java.net.URI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class StaticSnitchTest {

    private static final URI SNITCH_URI = URI.create("fake://nope");
    private Snitch snitch;

    @Before
    public void setUp() {
        snitch = StaticSnitch.of(SNITCH_URI);
    }

    @Test
    public void getLocation() {
        Assert.assertEquals(SNITCH_URI, snitch.getUri());
    }

    @Test
    public void snitch() {
        SystemFragment actual = snitch.snitch();
        Assert.assertEquals(SystemFragment.empty(), actual);
    }

    @Test
    public void verifyHashcodeEquals() {
        EqualsVerifier.forClass(StaticSnitch.class).usingGetClass().verify();
    }

    @Test
    public void testToString() {
        Assert.assertTrue(snitch.toString().contains(SNITCH_URI.toString()));
    }

}
