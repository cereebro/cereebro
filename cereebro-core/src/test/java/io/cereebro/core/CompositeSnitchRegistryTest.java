package io.cereebro.core;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

public class CompositeSnitchRegistryTest {

    @Test
    public void getAllWithEmptyRegistryShouldReturnEmptySet() {
        Assert.assertTrue(CompositeSnitchRegistry.of().getAll().isEmpty());
    }

    @Test
    public void getAll() {
        Snitch s1 = new FakeSnitch(URI.create("fake://1"));
        Snitch s2 = new FakeSnitch(URI.create("fake://2"));
        ArrayList<SnitchRegistry> registries = Lists.newArrayList(StaticSnitchRegistry.of(s1),
                StaticSnitchRegistry.of(s2));
        SnitchRegistry composite = CompositeSnitchRegistry.of(registries);
        List<Snitch> expected = Arrays.asList(s1, s2);
        Assert.assertEquals(expected, composite.getAll());
    }

}
