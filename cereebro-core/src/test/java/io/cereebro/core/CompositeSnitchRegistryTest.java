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
        Snitch s1 = new StaticSnitch(URI.create("fake://1"));
        Snitch s2 = new StaticSnitch(URI.create("fake://2"));
        ArrayList<SnitchRegistry> registries = Lists.newArrayList(StaticSnitchRegistry.of(s1),
                StaticSnitchRegistry.of(s2));
        SnitchRegistry composite = CompositeSnitchRegistry.of(registries);
        List<Snitch> expected = Arrays.asList(s1, s2);
        Assert.assertEquals(expected, composite.getAll());
    }

}
