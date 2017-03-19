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
package io.cereebro.core;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * {@link StaticSnitchRegistry} unit tests.
 * 
 * @author michaeltecourt
 */
public class StaticSnitchRegistryTest {

    @Test
    public void getAll() {
        Snitch snitch1 = Mockito.mock(Snitch.class);
        Mockito.when(snitch1.getUri()).thenReturn(URI.create("fake://snitch1"));
        Snitch snitch2 = Mockito.mock(Snitch.class);
        Mockito.when(snitch2.getUri()).thenReturn(URI.create("fake://snitch2"));
        Snitch snitchDuplicate2 = Mockito.mock(Snitch.class);
        Mockito.when(snitchDuplicate2.getUri()).thenReturn(URI.create("fake://snitch2"));
        SnitchRegistry registry = StaticSnitchRegistry.of(snitch1, snitch2);
        List<Snitch> actual = registry.getAll();
        List<Snitch> expected = new ArrayList<>(Arrays.asList(snitch1, snitch2));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void filterDuplicateLocationsWithEmptyCollectionShouldReturnEmptySet() {
        List<Snitch> actual = StaticSnitchRegistry.of(Collections.emptyList()).getAll();
        List<Snitch> expected = new ArrayList<>();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testToString() {
        String uri = "fake://nope";
        Snitch fakeSnitch = StaticSnitch.of(URI.create(uri));
        SnitchRegistry registry = StaticSnitchRegistry.of(fakeSnitch);
        Assert.assertTrue(registry.toString().contains(uri));
    }

}
