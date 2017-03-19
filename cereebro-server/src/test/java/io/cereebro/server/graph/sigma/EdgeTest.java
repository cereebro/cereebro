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
package io.cereebro.server.graph.sigma;

import org.junit.Assert;
import org.junit.Test;

import io.cereebro.core.Component;
import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link Edge} unit tests.
 * 
 * @author michaeltecourt
 */
public class EdgeTest {

    @Test
    public void verifyHashCodeEquals() {
        EqualsVerifier.forClass(Edge.class).suppress(Warning.STRICT_INHERITANCE).verify();
    }

    @Test
    public void consumerToComponent() {
        Edge actual = Edge.from(Component.of("phoenix", "superhero"), Consumer.by(Component.of("cyclop", "superhero")));
        Edge expected = Edge.create("superhero:cyclop-to-superhero:phoenix", "superhero:cyclop", "superhero:phoenix",
                Edge.TYPE_ARROW);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void componentToDependency() {
        Edge actual = Edge.to(Component.of("phoenix", "superhero"), Dependency.on(Component.of("xavier", "superhero")));
        Edge expected = Edge.create("superhero:phoenix-to-superhero:xavier", "superhero:phoenix", "superhero:xavier",
                Edge.TYPE_ARROW);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testToString() {
        Edge edge = Edge.create("phoenix-to-xavier", "phoenix", "xavier", Edge.TYPE_ARROW);
        Assert.assertTrue(edge.toString().contains("phoenix-to-xavier"));
    }

}
